package com.zpf.springframework.entity;

/**
 * @author zpf
 * @create 2021-05-09 17:57
 * 引用类型的Bean
 * 属性名：属性的引用对象
 */
public class BeanReference {
    private String name;
    private Object bean;

    public BeanReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
