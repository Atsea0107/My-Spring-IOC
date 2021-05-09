package com.zpf.springframework.entity;

/**
 * @author zpf
 * @create 2021-05-09 17:56
 * 单个键值，表示注入对象的属性
 * 属性名：属性值
 */
public class PropertyValue {
    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
