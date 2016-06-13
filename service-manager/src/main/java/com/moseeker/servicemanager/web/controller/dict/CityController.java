package com.moseeker.servicemanager.web.controller.dict;

import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.CityServices;
import org.slf4j.Logger;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class CityController {

    Logger logger = org.slf4j.LoggerFactory.getLogger(CityController.class);

    CityServices.Iface cityServices = ServiceUtil.getService(CityServices.Iface.class);

    @RequestMapping(value = "/dict/cities", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) {
        try {
            String parameterLevel = request.getParameter("level");
            int level = parameterLevel == null ? 0 : Integer.parseInt(parameterLevel);
            Response result = cityServices.getAllCities(level);
            return ResponseLogNotification.successWithParse(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

    @RequestMapping(value = "/dict/cities/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getById(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) {
        try {
            Response result = cityServices.getCitiesById((int)id);
            return ResponseLogNotification.successWithParse(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }

}
