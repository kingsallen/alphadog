package com.moseeker.servicemanager.web.controller.function;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.thirdpart.service.BindThirdPartService;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdPartParamer;

@Controller
@CounterIface
public class ChaOsController {

	Logger logger = LoggerFactory.getLogger(FunctionController.class);
	BindThirdPartService.Iface ChaosThriftService = ServiceManager.SERVICEMANAGER
			.getService(BindThirdPartService.Iface.class);
	
	@RequestMapping(value = "/thirdparty/account/couple", method = RequestMethod.POST)
	@ResponseBody
	public String couple(HttpServletRequest request, HttpServletResponse response) {
		try {
			ThirdPartParamer param=ParamUtils.initModelForm(request, ThirdPartParamer.class);
			Response result=ChaosThriftService.sendParamForChaos(param);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
	@RequestMapping(value = "/thirdparty/account/member_name", method = RequestMethod.POST)
	@ResponseBody
	public String member_name(HttpServletRequest request, HttpServletResponse response) {
		try {
			ThirdPartParamer param=ParamUtils.initModelForm(request, ThirdPartParamer.class);
			Response result=ChaosThriftService.sendParamForChaos(param);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

}
