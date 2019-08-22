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
import com.moseeker.thrift.gen.dict.service.PositionService;

@Controller
@CounterIface
public class DictPositionController {

	Logger logger = org.slf4j.LoggerFactory.getLogger(DictPositionController.class);
	PositionService.Iface sercie = ServiceManager.SERVICE_MANAGER
			.getService(PositionService.Iface.class);
	
	@RequestMapping(value = "/dict/position", method = RequestMethod.GET)
    @ResponseBody
    public String getIndustriesByType(HttpServletRequest request, HttpServletResponse response) {
        try {
            // GET方法 通用参数解析并赋值
        	 Map<String,Object> params = ParamUtils.parseRequestParam(request);
            Response result = sercie.getPositionsByCode((String)params.get("parent"));

            return ResponseLogNotification.success(request, result);
        } catch (Exception e) {
            return ResponseLogNotification.fail(request, e.getMessage());
        }
    }
}
