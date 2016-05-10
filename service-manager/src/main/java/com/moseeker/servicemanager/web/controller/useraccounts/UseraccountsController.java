package com.moseeker.servicemanager.web.controller.useraccounts;

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
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.userloginreq;

@Controller
public class UseraccountsController {

	Logger logger = LoggerFactory.getLogger(UseraccountsController.class);

	UseraccountsServices.Iface useraccountsServices = ServiceUtil.getService(UseraccountsServices.Iface.class);
	
	@RequestMapping(value = "/profile/profile", method = RequestMethod.POST)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		String jsonStringResponse = null;
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
			userloginreq userloginreqs = ParamUtils.initModelForm(request, userloginreq.class);
			
			Response result = useraccountsServices.postuserlogin(userloginreqs);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

}
