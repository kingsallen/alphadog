package com.moseeker.servicemanager.web.controller.profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.annotation.iface.CounterIface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.CredentialsServices;
import com.moseeker.thrift.gen.profile.struct.Credentials;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class CredentialsController {

	Logger logger = LoggerFactory.getLogger(CredentialsController.class);

	CredentialsServices.Iface credentialsService = ServiceManager.SERVICE_MANAGER
			.getService(CredentialsServices.Iface.class);
	
	@RequestMapping(value = "/profile/credentials", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);

			Response result = credentialsService.getResources(query);
			//jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e);
		}
	}

	@RequestMapping(value = "/profile/credentials", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			Credentials credentials = ParamUtils.initModelForm(request, Credentials.class);
			Response result = credentialsService.postResource(credentials);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			e.printStackTrace();
			return ResponseLogNotification.fail(request, e);
		}
	}

	@RequestMapping(value = "/profile/credentials", method = RequestMethod.PUT)
	@ResponseBody
	public String put(HttpServletRequest request, HttpServletResponse response) {
		try {
			Credentials credentials = ParamUtils.initModelForm(request, Credentials.class);
			Response result = credentialsService.putResource(credentials);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e);
		}
	}

	@RequestMapping(value = "/profile/credentials", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			Credentials credentials = ParamUtils.initModelForm(request, Credentials.class);
			Response result = credentialsService.delResource(credentials);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e);
		}
	}
}
