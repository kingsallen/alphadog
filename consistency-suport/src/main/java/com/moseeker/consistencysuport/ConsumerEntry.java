package com.moseeker.consistencysuport;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 数据一致性支持的消费方代理入口
 *
 * Created by jack on 02/04/2018.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsumerEntry {

    String name() default "";   //业务名称

    int period() default 5*60;  //方法检查周期

    int index() default 0;      //messageId默认所处的参数的位置
}
