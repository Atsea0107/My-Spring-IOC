package com.zpf.springframework.factory;

import com.zpf.springframework.entity.BeanDefinition;

/**
 * @author zpf
 * @create 2021-05-09 16:53
 * 实例工厂
 * Bean接口工厂
 * 作为Bean的接口工厂，
 * 1、要提供给外界获取Bean的功能
 * 2、而且还可以向工厂中注册Bean
 */
public interface BeanFactory {
    // 获取bean的方法
    Object getBean(Class clazz) throws Exception;
    Object getBean(String beanName) throws Exception;
    // 向工厂中注册bean的定义
    void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception;

}
