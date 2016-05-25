package com.moseeker.servicemanager.web.controller.profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;

@Controller
public class ProfileController {

	Logger logger = LoggerFactory.getLogger(ProfileController.class);

	WholeProfileServices.Iface profileService = ServiceUtil.getService(WholeProfileServices.Iface.class);
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			// GET方法 通用参数解析并赋值
			int id = 0;
			int userId = 0;
			if(!StringUtils.isNullOrEmpty(request.getParameter("id"))) {
				id = Integer.valueOf(request.getParameter("id"));
			}
			if(!StringUtils.isNullOrEmpty(request.getParameter("user_id"))) {
				userId = Integer.valueOf(request.getParameter("user_id"));
			}
			Response result = profileService.getResource(userId, id);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
