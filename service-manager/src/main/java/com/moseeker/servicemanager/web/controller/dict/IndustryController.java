package com.moseeker.servicemanager.web.controller.dict;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.IndustryService;

public class IndustryController {

	Logger logger = org.slf4j.LoggerFactory.getLogger(IndustryController.class);
	IndustryService.Iface service = ServiceUtil.getService(IndustryService.Iface.class);
	
	@RequestMapping(value = "/dict/industry", method = RequestMethod.GET)
    @ResponseBody
    public String getIndustriesByType(HttpServletRequest request, HttpServletResponse response) {
        try {
            // GET方法 通用参数解析并赋值
        	 Map<String,Object> params = ParamUtils.mergeRequestParameters(request);
            Response result = service.getIndustriesByCode((Integer)params.get("code"));

            return ResponseLogNotification.successWithParse(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
