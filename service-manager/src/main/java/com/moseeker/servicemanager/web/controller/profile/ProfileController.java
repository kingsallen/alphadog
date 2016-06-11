package com.moseeker.servicemanager.web.controller.profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class ProfileController {

	Logger logger = LoggerFactory.getLogger(ProfileController.class);

	WholeProfileServices.Iface profileService = ServiceUtil.getService(WholeProfileServices.Iface.class);
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			// GET方法 通用参数解析并赋值
			ImportCVForm form = ParamUtils.initModelForm(request, ImportCVForm.class);
			Response result = profileService.getResource(form.getUser_id(), form.getId(), form.getUuid());
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		} finally {
			//do nothing
		}
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			
			ImportCVForm form = ParamUtils.initModelForm(request, ImportCVForm.class);
			Response result = profileService.postResource(form.getProfile(), form.getUser_id());
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		} finally {
			//do nothing
		}
	}
}
