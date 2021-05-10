package com.zpf.controller;

import com.zpf.service.HelloWorldService;
import com.zpf.service.HelloWorldServiceImpl;
import com.zpf.springframework.annotation.Autowired;
import com.zpf.springframework.annotation.Controller;
import com.zpf.springframework.annotation.RequestMapping;
import com.zpf.springframework.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zpf
 * @createTime 2021-05-10 13:21
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private HelloWorldService helloWorldService;

    @RequestMapping("/test1")
    public void test1(HttpServletRequest request, HttpServletResponse response,
                      @RequestParam("param") String param){
        try {
            String text = helloWorldService.getString();
            response.getWriter().write(text + "and the param is " + param);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
