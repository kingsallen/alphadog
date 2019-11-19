package com.moseeker.servicemanager.web.controller.dict;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.annotation.iface.CounterIface;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.IndustryService;

@Controller
@CounterIface
public class DictIndustryController {

	Logger logger = org.slf4j.LoggerFactory.getLogger(DictIndustryController.class);
	IndustryService.Iface service = ServiceManager.SERVICE_MANAGER
			.getService(IndustryService.Iface.class);
	
	@RequestMapping(value = "/dict/industry", method = RequestMethod.GET)
    @ResponseBody
    public String getIndustriesByType(HttpServletRequest request, HttpServletResponse response) {
        try {
            // GET方法 通用参数解析并赋值
        	 Map<String,Object> params = ParamUtils.parseRequestParam(request);
            Response result = service.getIndustriesByCode((String)params.get("parent"));

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }

    @RequestMapping(value = "/dict/mars/industry", method = RequestMethod.GET)
    @ResponseBody
    public String getMarsIndustriesByType(HttpServletRequest request, HttpServletResponse response) {
        try {
            // GET方法 通用参数解析并赋值
            Map<String,Object> params = ParamUtils.parseRequestParam(request);
            Response result = service.getMarsIndustriesByCode((String)params.get("parent"));

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e);
        }
    }
}
