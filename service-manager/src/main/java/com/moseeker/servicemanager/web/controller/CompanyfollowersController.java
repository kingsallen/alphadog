package com.moseeker.servicemanager.web.controller;

import java.io.PrintWriter;
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
import com.moseeker.servicemanager.common.JsonResponse;
import com.moseeker.servicemanager.common.RestfulController;
import com.moseeker.servicemanager.common.Spring;
import com.moseeker.thrift.client.BaseThriftClient;
import com.moseeker.thrift.client.CompanyfollowersClient;
import com.moseeker.thrift.gen.companyfollowers.Companyfollower;
import com.moseeker.thrift.gen.companyfollowers.CompanyfollowerQuery;

@Controller
public class CompanyfollowersController implements RestfulController {

	Logger logger = LoggerFactory.getLogger(CompanyfollowersController.class);
	
	BaseThriftClient thriftclient = new CompanyfollowersClient();
	@Override
	@RequestMapping(value = "/companyfollowers", method = RequestMethod.GET)
	@ResponseBody
	public void get(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		PrintWriter writer = null;
		String jsonStringResponse = null;
		try {
			writer = response.getWriter();
			CompanyfollowerQuery query = new CompanyfollowerQuery();
			// GET方法 通用参数解析并赋值
			query = (CompanyfollowerQuery) Spring.initCommonQuery(query,
					request);

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
			
			List<Companyfollower> companyfollowers = thriftclient.callThriftServerGet(query);
			jsonStringResponse = JSON.toJSONString(companyfollowers);
			writer.write(JsonResponse.success(jsonStringResponse));
			writer.flush();
		} catch (Exception e) {	
			writer.write(JsonResponse.fail(e.getMessage()));
			writer.flush();
		} finally {
			Spring.logRequestResponse(request.getParameterMap(), jsonStringResponse);
			if (writer != null) {
				writer.close();
			}
		}

	}

	@Override
	@RequestMapping(value = "/companyfollowers", method = RequestMethod.POST)
	@ResponseBody
	public void post(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	@RequestMapping(value = "/companyfollowers", method = RequestMethod.PUT)
	@ResponseBody
	public void put(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}

	@Override
	@RequestMapping(value = "/companyfollowers", method = RequestMethod.DELETE)
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub

	}





}
