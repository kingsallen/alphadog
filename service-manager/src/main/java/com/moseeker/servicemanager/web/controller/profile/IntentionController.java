package com.moseeker.servicemanager.web.controller.profile;

import java.util.Map;

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
import com.moseeker.servicemanager.web.controller.util.IntentionParamUtil;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.IntentionServices;
import com.moseeker.thrift.gen.profile.struct.Intention;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class IntentionController {

	Logger logger = LoggerFactory.getLogger(IntentionController.class);

	IntentionServices.Iface intentionService = ServiceManager.SERVICE_MANAGER.getService(IntentionServices.Iface.class);

	@RequestMapping(value = "/profile/intention", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		// PrintWriter writer = null;
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);

			Response result = intentionService.getResources(query);

			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/intention", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request, HttpServletResponse response) {
		// PrintWriter writer = null;
		try {
			Map<String, Object> data = ParamUtils.parseRequestParam(request);
			Intention intention = ParamUtils.initModelForm(data, Intention.class);
			IntentionParamUtil.buildIntention(data, intention);
			Response result = intentionService.postResource(intention);

			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/intention", method = RequestMethod.PUT)
	@ResponseBody
	public String put(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> data = ParamUtils.parseRequestParam(request);
			Intention intention = ParamUtils.initModelForm(data, Intention.class);
			IntentionParamUtil.buildIntention(data, intention);
			Response result = intentionService.putResource(intention);

			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}


	@RequestMapping(value = "/profile/intention", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> data = ParamUtils.parseRequestParam(request);
			Intention intention = ParamUtils.initModelForm(data, Intention.class);
			IntentionParamUtil.buildIntention(data, intention);
			Response result = intentionService.delResource(intention);

			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
