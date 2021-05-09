package com.zpf.springframework.context;

/**
 * @author zpf
 * @create 2021-05-09 16:49
 * 应用程序上下文接口
 * Spring最为核心的类，也是入口类s
 */
public interface ApplicationContext {
    // 这个接口表示程序有两种从 ApplicationContext 获取 bean 实例的方式：通过 Class 对象和通过实例名
    Object getBean(Class clazz) throws Exception;
    Object getBean(String beanName) throws Exception;
}
