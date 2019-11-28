package com.moseeker.servicemanager.exception;


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
 * Created by YYF
 *
 * Date: 2017/7/25
 *
 * Project_name :alphadog
 */
@ControllerAdvice
public class ExceptionHandle {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String processException(HttpServletRequest request, Exception ex) {
        return ResponseLogNotification.fail(request, ResponseUtils.fail(99999, ex.getMessage() != null ? ex.getMessage() : "系统异常!"), ex);
    }


    @ExceptionHandler(BIZException.class)
    @ResponseBody
    public String processBIZException(HttpServletRequest request, BIZException ex) {
        return ResponseLogNotification.fail(request, ResponseUtils.fail(ex.getCode(), ex.getMessage()), ex);
    }

}
