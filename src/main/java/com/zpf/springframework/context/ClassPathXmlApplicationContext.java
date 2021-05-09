package com.zpf.springframework.context;

import com.zpf.springframework.entity.BeanDefinition;
import com.zpf.springframework.factory.AbstractBeanFactory;
import com.zpf.springframework.factory.AutowiredCapableBeanFactory;
import com.zpf.springframework.io.ResourceLoader;
import com.zpf.springframework.reader.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * @author zpf
 * @create 2021-05-09 18:13
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {
    private final Object startupShutdownMonitor = new Object();
    private String location;

    public ClassPathXmlApplicationContext(String location) throws Exception{
        super();
        this.location = location;
        refresh();
    }

    public void refresh() throws Exception{
        synchronized (startupShutdownMonitor){
            AbstractBeanFactory beanFactory = obtainBeanFactory();
            prepareBeanFactory(beanFactory);
            this.beanFactory = beanFactory;
        }
    }

    private void prepareBeanFactory(AbstractBeanFactory beanFactory) throws Exception{
        beanFactory.populateBeans();
    }

    private AbstractBeanFactory obtainBeanFactory() throws Exception{
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new ResourceLoader());
        xmlBeanDefinitionReader.loadBeanDefinitions(location);
        AbstractBeanFactory beanFactory = new AutowiredCapableBeanFactory();
        for(Map.Entry<String, BeanDefinition> beanDefinitionEntry : xmlBeanDefinitionReader.getRegistry().entrySet()){
            beanFactory.registerBeanDefinition(beanDefinitionEntry.getKey(), beanDefinitionEntry.getValue());
        }
        return beanFactory;
    }
}
