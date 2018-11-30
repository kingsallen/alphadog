package com.moseeker.mall.annotation;

import java.lang.annotation.*;

/**
 * @author cjm
 * @date 2018-10-17 10:37
 **/
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OnlySuperAccount {

}
