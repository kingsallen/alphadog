package com.moseeker.servicemanager.web.controller.function;

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
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.function.service.FunctionServices;
import com.moseeker.thrift.gen.function.struct.SensitiveWord;

@Controller
@CounterIface
public class FunctionController {

	Logger logger = LoggerFactory.getLogger(FunctionController.class);

	FunctionServices.Iface functionService = ServiceManager.SERVICE_MANAGER
			.getService(FunctionServices.Iface.class);

	@RequestMapping(value = "/function/sensitiveword", method = RequestMethod.POST)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		// PrintWriter writer = null;
		try {
			// GET方法 通用参数解析并赋值
			SensitiveWord sensitive = ParamUtils.initModelForm(request, SensitiveWord.class);

			Response result = functionService.verifySensitiveWords(sensitive.getContents());

			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}
}
