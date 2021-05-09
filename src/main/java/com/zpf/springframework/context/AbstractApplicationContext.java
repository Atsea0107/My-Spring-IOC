package com.zpf.springframework.context;

import com.zpf.springframework.factory.BeanFactory;

/**
 * @author zpf
 * @create 2021-05-09 16:52
 */
public abstract class AbstractApplicationContext implements ApplicationContext{

    BeanFactory beanFactory;

    public Object getBean(Class clazz) throws Exception {
        return beanFactory.getBean(clazz);
    }

    public Object getBean(String beanName) throws Exception {
        return beanFactory.getBean(beanName);
    }
}
