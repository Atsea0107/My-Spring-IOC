package com.zpf.springframework.factory;

import com.zpf.springframework.entity.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zpf
 * @create 2021-05-09 16:58
 */
public abstract class AbstractBeanFactory implements BeanFactory {
    ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public Object getBean(Class clazz) throws Exception {
        BeanDefinition beanDefinition = null;
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            Class tmpClazz = entry.getValue().getBeanClass();
            if (tmpClazz == clazz || clazz.isAssignableFrom(tmpClazz)) {
                beanDefinition = entry.getValue();
            }
        }
        if (beanDefinition == null) {
            return null;
        }
        // 单例，则直接获取；非单例，则check是否已经存在
        if (!beanDefinition.isSingleton() || beanDefinition.getBean() == null) {
            return doCreateBean(beanDefinition);
        }
        return beanDefinition.getBean();
    }

    /**
     * 创建bean实例
     * @param beanDefinition bean定义对象
     * @return bean实例对象
     */
    protected abstract Object doCreateBean(BeanDefinition beanDefinition) throws Exception;

    public Object getBean(String beanName) throws Exception {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) return null;
        if (!beanDefinition.isSingleton() || beanDefinition.getBean() == null) {
            return doCreateBean(beanDefinition);
        }
        return beanDefinition.getBean();
    }

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception {
        beanDefinitionMap.put(name, beanDefinition);
    }

    public void populateBeans() throws Exception{
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            doCreateBean(entry.getValue());
        }
    }

}
