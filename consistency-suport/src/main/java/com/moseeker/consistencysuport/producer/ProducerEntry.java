package com.moseeker.consistencysuport.producer;

import java.lang.annotation.*;

/**
 *
 * 数据一致性支持的调用方代理入口
 *
 * Created by jack on 02/04/2018.
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ProducerEntry {

    String name() default "";   //业务名称

    int period() default 5*60;  //方法检查周期

    int index() default 0;      //messageId默认所处的参数的位置
}
