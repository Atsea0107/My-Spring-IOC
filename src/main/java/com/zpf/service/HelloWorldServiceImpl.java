package com.zpf.service;

/**
 * @author zpf
 * @create 2021-05-09 18:44
 */
public class HelloWorldServiceImpl implements HelloWorldService {
    private String text;
    @Override
    public void saySomething() {
        System.out.println(text);
    }
}
