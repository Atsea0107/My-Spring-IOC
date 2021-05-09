package com.zpf.springframework.factory;

import com.zpf.springframework.entity.BeanDefinition;
import com.zpf.springframework.entity.BeanReference;
import com.zpf.springframework.entity.PropertyValue;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @author zpf
 * @create 2021-05-09 17:10
 * 可以自动注入属性的BeanFactory
 */
public class AutowiredCapableBeanFactory extends AbstractBeanFactory{
    @Override
    protected Object doCreateBean(BeanDefinition beanDefinition) throws Exception{
        if(beanDefinition.isSingleton() && beanDefinition.getBean() != null){
            return beanDefinition.getBean();
        }
        Object bean = beanDefinition.getBeanClass().newInstance();
        if(beanDefinition.isSingleton()){
            beanDefinition.setBean(bean);
        }
        applyPropertyValues(bean, beanDefinition);
        return bean;
    }

    /**
     * 为bean注入属性，反射注入
     * @param bean
     * @param beanDefinition
     * @throws Exception
     */
    private void applyPropertyValues(Object bean, BeanDefinition beanDefinition) throws Exception{
        for(PropertyValue propertyValue : beanDefinition.getPropertyValueList().getPropertyValueList()){
            Field field = bean.getClass().getDeclaredField(propertyValue.getName());
            Object value = propertyValue.getValue();
            // BeanReference 引用类型
            if(value instanceof BeanReference){
                BeanReference beanReference = (BeanReference) propertyValue.getValue();
                BeanDefinition refDefinition = beanDefinitionMap.get(beanReference.getName());
                // 引用属性，则递归调用
                if(refDefinition.getBean() == null) {
                    value = doCreateBean(refDefinition);
                }
            }
            field.setAccessible(true);
            field.set(bean, value);
        }
    }
}
