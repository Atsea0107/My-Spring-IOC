package com.zpf.service;

import com.zpf.springframework.annotation.Component;
import com.zpf.springframework.annotation.Scope;
import com.zpf.springframework.annotation.Value;

/**
 * @author zpf
 * @create 2021-05-09 18:44
 */
@Component(name = "helloWorldService")
@Scope("prototype")
public class HelloWorldServiceImpl implements HelloWorldService {
    @Value("hello, world, java")
    private String text;

    @Override
    public void saySomething() {
        System.out.println(text);
    }

    @Override
    public String getString() {
        return text;
    }
}
