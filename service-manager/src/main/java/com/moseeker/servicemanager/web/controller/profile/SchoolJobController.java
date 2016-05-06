package com.moseeker.servicemanager.web.controller.profile;

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
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.util.ServiceUtil;
import com.moseeker.thrift.gen.profile.service.SchoolJobServices;
import com.moseeker.thrift.gen.profile.struct.CommonQuery;
import com.moseeker.thrift.gen.profile.struct.SchoolJob;

@Controller
public class SchoolJobController {

	Logger logger = LoggerFactory.getLogger(SchoolJobController.class);

	SchoolJobServices.Iface schoolJobService = ServiceUtil.getService(SchoolJobServices.Iface.class);
	
	@RequestMapping(value = "/profile/schooljob", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		String jsonStringResponse = null;
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);

			List<SchoolJob> schoolJob = schoolJobService.getResources(query);
			jsonStringResponse = JSON.toJSONString(schoolJob);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/schooljob", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		String jsonStringResponse = null;
		try {
			SchoolJob schoolJob = ParamUtils.initModelForm(request, SchoolJob.class);
			int result = schoolJobService.postResource(schoolJob);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			e.printStackTrace();
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/schooljob", method = RequestMethod.PUT)
	@ResponseBody
	public String put(HttpServletRequest request, HttpServletResponse response) {
		String jsonStringResponse = null;
		try {
			SchoolJob schoolJob = ParamUtils.initModelForm(request, SchoolJob.class);
			int result = schoolJobService.putResource(schoolJob);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/schooljob", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		String jsonStringResponse = null;
		try {
			SchoolJob schoolJob = ParamUtils.initModelForm(request, SchoolJob.class);
			int result = schoolJobService.delResource(schoolJob);
			jsonStringResponse = JSON.toJSONString(result);
			
			return ResponseLogNotification.success(request, jsonStringResponse);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
