package com.moseeker.servicemanager.web.controller.dict;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.annotation.iface.CounterIface;
import org.jooq.tools.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictOccupationService;

@Controller
@CounterIface
public class DictOccupationController {
	Logger logger = org.slf4j.LoggerFactory.getLogger(DictOccupationController.class);
	DictOccupationService.Iface dictOccupationService = ServiceManager.SERVICE_MANAGER
			.getService(DictOccupationService.Iface.class);
	@RequestMapping(value ="/thirdparty/occupation", method = RequestMethod.GET)
	@ResponseBody
	public String occupation(HttpServletRequest request, HttpServletResponse response) {
		try {
			 Map<String,Object> map = ParamUtils.parseRequestParam(request);
			 String params=JSONObject.toJSONString(map);
			 Response result=dictOccupationService.getDictOccupation(params);
			 return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
