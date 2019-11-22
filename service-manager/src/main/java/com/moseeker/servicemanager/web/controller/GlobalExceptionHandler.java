package com.moseeker.servicemanager.web.controller;

import com.moseeker.common.exception.ParamNullException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 * Created by jack on 19/12/2017.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String defaultErrorHandler(HttpServletRequest request, Exception e) {

        if (e instanceof BIZException) {
            try {
                return ResponseLogNotification.fail(request, ResponseUtils.fail(((BIZException)e).getCode(), e.getMessage()), e);
            } catch (ParamNullException e1) {
                return ResponseLogNotification.fail(request, e);
            }
        } else {
            return ResponseLogNotification.fail(request, e);
        }
    }
}
