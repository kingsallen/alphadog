package com.moseeker.application.service.annotation;

import java.lang.annotation.*;

/**
 * @author: huangwenjian
 * @desc:
 * @since: 2019-11-25 22:38
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HandleChannelApplication {
}
