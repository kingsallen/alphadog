package com.moseeker.servicemanager.web.controller.application;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.application.service.OperatorThirdPartService;
import com.moseeker.thrift.gen.application.struct.JobApplication;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.thirdpart.struct.ThirdpartToredis;

@Controller
public class ThirdPartOperatorController {

	Logger logger = org.slf4j.LoggerFactory.getLogger(JobApplicationController.class);

	OperatorThirdPartService.Iface thirdPartService = ServiceManager.SERVICEMANAGER
			.getService(OperatorThirdPartService.Iface.class);
	@RequestMapping(value = "/positions/updates", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			ThirdpartToredis params=ParamUtils.initModelForm(request, ThirdpartToredis.class);
			Response result=thirdPartService.updatePositionToRedis(params);
			return null;
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
	@RequestMapping(value = "/position/synchronization", method = RequestMethod.POST)
	@ResponseBody
	public String synchronization(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			ThirdpartToredis params=ParamUtils.initModelForm(request, ThirdpartToredis.class);
			Response result=thirdPartService.addPositionToRedis(params);
			return null;
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
	
}
