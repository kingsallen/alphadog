package com.moseeker.servicemanager.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.util.StringUtils;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.util.ServiceUtil;
import com.moseeker.thrift.gen.profile.service.ProfileServices;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.struct.Profile;

@Controller
public class ProfileController {

	Logger logger = LoggerFactory.getLogger(ProfileController.class);

	ProfileServices.Iface profileService = ServiceUtil.getService(ProfileServices.Iface.class);
	
	@RequestMapping(value = "/profile/profile", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		String jsonStringResponse = null;
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);

			List<Profile> profiles = profileService.getResources(query);
			jsonStringResponse = JSON.toJSONString(profiles);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/profile", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		String jsonStringResponse = null;
		try {
			Profile profile = ParamUtils.initModelForm(request, Profile.class);
			int result = profileService.postResource(profile);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			e.printStackTrace();
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/profile", method = RequestMethod.PUT)
	@ResponseBody
	public String put(HttpServletRequest request, HttpServletResponse response) {
		String jsonStringResponse = null;
		try {
			Profile profile = new Profile();
			if(!StringUtils.isNullOrEmpty(request.getParameter("id"))) {
				profile.setId(Integer.parseInt(request.getParameter("id")));
			}
			if(!StringUtils.isNullOrEmpty(request.getParameter("user_id"))) {
				profile.setUser_id(Integer.parseInt(request.getParameter("user_id")));
			}
			if(!StringUtils.isNullOrEmpty(request.getParameter("completeness"))) {
				profile.setCompleteness(10);
			}
			int result = profileService.putResource(profile);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/profile", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		String jsonStringResponse = null;
		try {
			Profile profile = new Profile();
			if(!StringUtils.isNullOrEmpty(request.getParameter("id"))) {
				profile.setId(Integer.parseInt(request.getParameter("id")));
			}
			if(!StringUtils.isNullOrEmpty(request.getParameter("user_id"))) {
				profile.setUser_id(Integer.parseInt(request.getParameter("user_id")));
			}
			if(!StringUtils.isNullOrEmpty(request.getParameter("completeness"))) {
				profile.setCompleteness(10);
			}
			int result = profileService.delResource(profile);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
