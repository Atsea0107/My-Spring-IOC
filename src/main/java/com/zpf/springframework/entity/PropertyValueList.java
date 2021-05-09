package com.zpf.springframework.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zpf
 * @create 2021-05-09 17:56
 * 键值对map，表示注入对象的属性
 */
public class PropertyValueList {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public PropertyValueList() {
    }

    public void addPropertyValue(PropertyValue propertyValue){
        propertyValueList.add(propertyValue);
    }

    public List<PropertyValue> getPropertyValueList() {
        return propertyValueList;
    }
}

