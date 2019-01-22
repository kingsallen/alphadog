package com.moseeker.useraccounts.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RadarSwitchLimit {

    boolean status() default false;
}