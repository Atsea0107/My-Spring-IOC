package com.zpf.springframework.io;

import java.net.URL;

/**
 * @author zpf
 * @create 2021-05-09 18:04
 * 资源加载器，通过路径加载资源
 */
public class ResourceLoader {
    public Resource getResource(String location){
        // 通过类加载器加载资源，返回URL对象
        URL url = this.getClass().getClassLoader().getResource(location);
        return new UrlResource(url);
    }
}
