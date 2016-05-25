package com.moseeker.servicemanager.web.controller.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.application.service.ApplicationServices;
import com.moseeker.thrift.gen.application.struct.Application;
import com.moseeker.thrift.gen.common.struct.Response;

/**
 * 申请服务
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class ApplicationController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(ApplicationController.class);

    ApplicationServices.Iface applicationService = ServiceUtil.getService(ApplicationServices.Iface.class);

    @RequestMapping(value = "/application", method = RequestMethod.POST)
    @ResponseBody
    public String post(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 获取application实体对象
            Application application = ParamUtils.initModelForm(request, Application.class);

            // 创建申请记录
            Response result = applicationService.postResource(application);
            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
