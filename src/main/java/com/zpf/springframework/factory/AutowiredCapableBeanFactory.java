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
        // 单例，且已经创建好了，直接返回
        if(beanDefinition.isSingleton() && beanDefinition.getBean() != null){
            return beanDefinition.getBean();
        }
        // 根据Class对象 创建一个实例
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
        // 获取属性List，根据反射对各个成员变量初始化
        for(PropertyValue propertyValue : beanDefinition.getPropertyValueList().getPropertyValueList()){
            // getDeclaredField() 类本身的成员属性，不包括父类，但是包括private protected
            Field field = bean.getClass().getDeclaredField(propertyValue.getName());
            Object value = propertyValue.getValue();
            // BeanReference 引用类型
            if(value instanceof BeanReference){
                BeanReference beanReference = (BeanReference) propertyValue.getValue();
                BeanDefinition refDefinition = beanDefinitionMap.get(beanReference.getName());
                // 引用属性，则递归调用
                if(refDefinition != null) {
                    if(!refDefinition.isSingleton() || refDefinition.getBean() == null) {
                        value = doCreateBean(refDefinition);
                    } else {
                        value = refDefinition.getBean();
                    }
                } else {
                    // 按照类型匹配，返回第一个匹配的
                    Class clazz = Class.forName(beanReference.getName());
                    for(BeanDefinition definition : beanDefinitionMap.values()) {
                        if(clazz.isAssignableFrom(definition.getBeanClass())) {
                            if(!definition.isSingleton() || definition.getBean() == null) {
                                value = doCreateBean(definition);
                            } else {
                                value = definition.getBean();
                            }
                        }
                    }
                }

            }
            if(value == null) {
                throw new RuntimeException("无法注入");
            }
            field.setAccessible(true);
            field.set(bean, value);
        }
    }
}
