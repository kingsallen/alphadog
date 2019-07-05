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

import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.profile.struct.Profile;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class ProfileProfileController {

	Logger logger = LoggerFactory.getLogger(ProfileProfileController.class);

	ProfileServices.Iface profileService = ServiceManager.SERVICE_MANAGER.getService(ProfileServices.Iface.class);
	
	@RequestMapping(value = "/profile/profile", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
			
			Response result = profileService.getResources(query);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/profile", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			Profile profile = ParamUtils.initModelForm(request, Profile.class);
			Response result = profileService.postResource(profile);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			e.printStackTrace();
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/profile", method = RequestMethod.PUT)
	@ResponseBody
	public String put(HttpServletRequest request, HttpServletResponse response) {
		try {
			Profile profile = ParamUtils.initModelForm(request, Profile.class);
			Response result = profileService.putResource(profile);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/profile", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			Profile profile = ParamUtils.initModelForm(request, Profile.class);
			Response result = profileService.delResource(profile);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
	
	@RequestMapping(value = "/profile/completeness", method = RequestMethod.GET)
	@ResponseBody
	public String completeness(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			Map<String,Object> params = ParamUtils.parseRequestParam(request);
			int userId = 0;
			if(params.get("user_id") != null) {
				userId = BeanUtils.converToInteger(params.get("user_id"));
			}
			int profileId = 0;
			if(params.get("profile_id") != null) {
				profileId = BeanUtils.converToInteger(params.get("profile_id"));
			}
			Response result = profileService.getCompleteness(userId, (String)params.get("uuid"), profileId);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		} finally {
			//do nothing
		}
	}
}
