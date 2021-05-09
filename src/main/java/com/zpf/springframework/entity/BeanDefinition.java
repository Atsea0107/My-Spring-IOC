package com.zpf.springframework.entity;


/**
 * @author zpf
 * @create 2021-05-09 17:54
 * BeanDefinition：就是bean的具体信息
 * 一个实例应该具有的信息：
 * 0、对外提供该bean
 * 1、Class对象
 * 2、类命
 * 3、是否是单例
 * 4、属性List
 * bean一般为单例，但可能不是，所以并没有私有化构造器
 */
public class BeanDefinition {
    private Object bean;
    private Class beanClass;
    private String beanClassName;
    private Boolean singleton;
    private PropertyValueList propertyValueList;


    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
        try {
            this.beanClass = Class.forName(beanClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Boolean isSingleton() {
        return singleton;
    }

    public void setSingleton(Boolean singleton) {
        this.singleton = singleton;
    }

    public PropertyValueList getPropertyValueList() {
        if(propertyValueList == null){
            propertyValueList = new PropertyValueList();
        }
        return propertyValueList;
    }

    public void setPropertyValueList(PropertyValueList propertyValueList) {
        this.propertyValueList = propertyValueList;
    }
}
