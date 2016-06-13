package com.moseeker.servicemanager.web.controller.dict;

import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.CollegeServices;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class CollegeController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(CollegeController.class);

    CollegeServices.Iface collegeServices = ServiceUtil.getService(CollegeServices.Iface.class);

    @RequestMapping(value = "/dict/college", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) {
        try {

            // GET方法 通用参数解析并赋值
            CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
            Response result = collegeServices.getResources(query);
            return ResponseLogNotification.successWithParse(request, result);

        } catch (Exception e) {

            return ResponseLogNotification.fail(request, e.getMessage());

        }
    }

}
