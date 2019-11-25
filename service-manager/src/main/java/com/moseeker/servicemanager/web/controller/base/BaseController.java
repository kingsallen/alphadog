package com.moseeker.servicemanager.web.controller.base;

import com.moseeker.rpccenter.exception.RpcException;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    /**
     * 基于@ExceptionHandler异常处理
     * 处理
     */
    @ExceptionHandler
    @ResponseBody
    public String exp(HttpServletRequest request, Exception ex) {

        ex.printStackTrace();

        request.setAttribute("ex", ex);

        if (ex instanceof TException | ex instanceof RpcException) {
            return ResponseLogNotification.fail(request, "无法连接到服务器");
        }
        return ResponseLogNotification.fail(request, ex);
    }
}  