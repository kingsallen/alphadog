package com.moseeker.servicemanager.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.common.Spring;
import com.moseeker.thrift.gen.companyfollowers.Companyfollower;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerQuery;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerServices.Iface;

@Controller
public class CompanyfollowersController extends BaseController<Iface> {

	Logger logger = LoggerFactory.getLogger(CompanyfollowersController.class);

	Iface companyfollowersService = getService(Iface.class.getEnclosingClass().getName(), Iface.class.getName());
	
	@RequestMapping(value = "/companyfollowers", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		//PrintWriter writer = null;
		String jsonStringResponse = null;
		try {
			// GET方法 通用参数解析并赋值
			CompanyfollowerQuery query = Spring.initCommonQuery(request, CompanyfollowerQuery.class);

			// 特有参数解析并赋值
			if (request.getParameter("userid") != null) {
				int userid = Integer.parseInt(request.getParameter("userid"));
				if (userid > 0) {
					query.setUserid(userid);
				}
			}

			if (request.getParameter("companyid") != null) {
				int companyid = Integer.parseInt(request
						.getParameter("companyid"));
				if (companyid > 0) {
					query.setCompanyid(companyid);
				}
			}
			
			List<Companyfollower> companyfollowers = companyfollowersService.getCompanyfollowers(query);
			jsonStringResponse = JSON.toJSONString(companyfollowers);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/companyfollowers", method = RequestMethod.POST)
	@ResponseBody
	public void post(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@RequestMapping(value = "/companyfollowers", method = RequestMethod.PUT)
	@ResponseBody
	public void put(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@RequestMapping(value = "/companyfollowers", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}





}
