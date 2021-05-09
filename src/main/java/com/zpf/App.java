package com.zpf;

import com.zpf.service.HelloWorldService;
import com.zpf.service.WrapService;
import com.zpf.springframework.context.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        // xml配置文件读取
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application.xml");
        // 获取bean实例 beanName为xml中bean的id（严格相同）
        WrapService wrapService = (WrapService) applicationContext.getBean("wrapService");
        wrapService.say();
        // 单例的验证
        HelloWorldService helloWorldService = (HelloWorldService) applicationContext.getBean("helloWorldService");
        HelloWorldService helloWorldService2 = (HelloWorldService) applicationContext.getBean("helloWorldService");
        System.out.println("prototype验证：" + (helloWorldService == helloWorldService2));
        WrapService wrapService2 = (WrapService) applicationContext.getBean("wrapService");
        System.out.println("singleton验证：" + (wrapService == wrapService2));
    }
}
