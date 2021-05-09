package com.zpf.service;

import com.zpf.springframework.annotation.Autowired;
import com.zpf.springframework.annotation.Component;

/**
 * @author zpf
 * @create 2021-05-09 18:43
 */
@Component(name = "wrapService")
public class WrapService {
    @Autowired
    private HelloWorldService helloWorldService;

    public void say() {
        helloWorldService.saySomething();
    }
}
