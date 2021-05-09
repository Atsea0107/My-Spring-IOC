package com.zpf.springframework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zpf
 * @createTime 2021-05-09 20:37
 * Component注解类
 * 主要用于注解  类，表示这个类是一个需要被注册进容器的bean
 *
 * @interface 表明这个是一个注解类
 * Retention：维持；@Retention表示该注解类型的声明周期
 * @Target：可以被作用的位置
 */
@Retention(RetentionPolicy.RUNTIME)
// TYPE  类、接口、枚举类
@Target(ElementType.TYPE)
public @interface Component {
    // 该注解的属性
    String name() default "";
}
