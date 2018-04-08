package com.moseeker.consistencysuport;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 数据一致性支持的调用方代理入口
 *
 * Created by jack on 02/04/2018.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProducerEntry {

    String name() default "";

    String className() default "";

    String method() default "";

    String params() default "";

    int period() default 5*60;
}
