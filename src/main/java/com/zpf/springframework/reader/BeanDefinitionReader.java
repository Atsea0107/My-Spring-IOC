package com.zpf.springframework.reader;

/**
 * @author zpf
 * @create 2021-05-09 17:58
 * Bean定义的读取，从xml文件中
 */
public interface BeanDefinitionReader {
    void loadBeanDefinitions(String location) throws Exception;
}
