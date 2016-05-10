package com.moseeker.servicemanager.web.controller.profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.util.ServiceUtil;
import com.moseeker.thrift.gen.profile.service.WorkExpServices;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.struct.ProviderResult;
import com.moseeker.thrift.gen.profile.struct.WorkExp;

@Controller
public class WorkController {

	Logger logger = LoggerFactory.getLogger(WorkController.class);

	WorkExpServices.Iface workExpService = ServiceUtil.getService(WorkExpServices.Iface.class);
	
	@RequestMapping(value = "/profile/workexp", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		String jsonStringResponse = null;
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);

			ProviderResult result = workExpService.getResources(query);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/workexp", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		String jsonStringResponse = null;
		try {
			WorkExp workexp = ParamUtils.initModelForm(request, WorkExp.class);
			ProviderResult result = workExpService.postResource(workexp);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			e.printStackTrace();
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/workexp", method = RequestMethod.PUT)
	@ResponseBody
	public String put(HttpServletRequest request, HttpServletResponse response) {
		String jsonStringResponse = null;
		try {
			WorkExp workexp = ParamUtils.initModelForm(request, WorkExp.class);
			ProviderResult result = workExpService.putResource(workexp);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/workexp", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		String jsonStringResponse = null;
		try {
			WorkExp workexp = ParamUtils.initModelForm(request, WorkExp.class);
			ProviderResult result = workExpService.delResource(workexp);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
