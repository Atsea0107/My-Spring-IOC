package com.zpf.springframework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zpf
 * @createTime 2021-05-10 11:19
 * RequestMapping：处理请求地址映射的注解
 * 用于类或者方法上
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RequestMapping {
    String value() default "";
}
