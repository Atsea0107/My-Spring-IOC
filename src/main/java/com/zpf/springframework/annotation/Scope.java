package com.zpf.springframework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zpf
 * @createTime 2021-05-09 20:37
 * Scope：这个注解用于类，默认是singleton，表明这个类是单例还是多例
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Scope {
    String value() default "singleton";
}
