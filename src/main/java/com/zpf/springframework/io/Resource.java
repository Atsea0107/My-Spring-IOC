package com.zpf.springframework.io;

import java.io.InputStream;

/**
 * @author zpf
 * @create 2021-05-09 18:02
 * 获取资源（配置文件）的接口
 */
public interface Resource {
    InputStream getInputStream() throws Exception;
}
