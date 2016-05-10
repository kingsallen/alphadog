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
import com.moseeker.thrift.gen.profile.service.ProjectExpServices;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.struct.ProjectExp;
import com.moseeker.thrift.gen.profile.struct.ProviderResult;

@Controller
public class ProjectExpController {

	Logger logger = LoggerFactory.getLogger(ProjectExpController.class);

	ProjectExpServices.Iface projectExpService = ServiceUtil.getService(ProjectExpServices.Iface.class);
	
	@RequestMapping(value = "/profile/projectexp", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		String jsonStringResponse = null;
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);

			ProviderResult result = projectExpService.getResources(query);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/projectexp", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		String jsonStringResponse = null;
		try {
			ProjectExp projectExp = ParamUtils.initModelForm(request, ProjectExp.class);
			ProviderResult result = projectExpService.postResource(projectExp);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			e.printStackTrace();
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/projectexp", method = RequestMethod.PUT)
	@ResponseBody
	public String put(HttpServletRequest request, HttpServletResponse response) {
		String jsonStringResponse = null;
		try {
			ProjectExp profileimport = ParamUtils.initModelForm(request, ProjectExp.class);
			ProviderResult result = projectExpService.putResource(profileimport);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/projectexp", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		String jsonStringResponse = null;
		try {
			ProjectExp profileimport = ParamUtils.initModelForm(request, ProjectExp.class);
			ProviderResult result = projectExpService.delResource(profileimport);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
