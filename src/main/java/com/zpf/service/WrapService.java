package com.zpf.service;

/**
 * @author zpf
 * @create 2021-05-09 18:43
 */
public class WrapService {
    private HelloWorldService helloWorldService;

    public void say() {
        helloWorldService.saySomething();
    }
}
