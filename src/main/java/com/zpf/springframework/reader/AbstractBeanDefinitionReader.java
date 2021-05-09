package com.zpf.springframework.reader;


import com.zpf.springframework.entity.BeanDefinition;
import com.zpf.springframework.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zpf
 * @create 2021-05-09 18:00
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{
    private Map<String, BeanDefinition> registry;
    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(ResourceLoader resourceLoader) {
        this.registry = new HashMap<String, BeanDefinition>();
        this.resourceLoader = resourceLoader;
    }

    public Map<String, BeanDefinition> getRegistry() {
        return registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
