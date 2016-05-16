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
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.BasicServices;
import com.moseeker.thrift.gen.profile.struct.Basic;

@Controller
public class BasicController {

	Logger logger = LoggerFactory.getLogger(BasicController.class);

	BasicServices.Iface basicService = ServiceUtil.getService(BasicServices.Iface.class);
	
	@RequestMapping(value = "/profile/basic", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);

			Response result = basicService.getResources(query);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/basic", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			System.out.println("request.address:"+request.getParameter("address"));
			String address = request.getParameter("address");
			System.out.println("utf8:"+new String(address.getBytes(), "utf8"));
			System.out.println("ISO8859-1:"+new String(address.getBytes(), "ISO8859-1"));
			System.out.println("gbk:"+new String(address.getBytes(), "gbk"));
			System.out.println("gb2312:"+new String(address.getBytes(), "gb2312"));
			System.out.println("gb18030:"+new String(address.getBytes(), "gb18030"));
			System.out.println("big5:"+new String(address.getBytes(), "big5"));
			System.out.println("utf16:"+new String(address.getBytes(), "utf16"));
			System.out.println("utf32:"+new String(address.getBytes(), "utf32"));
			Basic basic = ParamUtils.initModelForm(request, Basic.class);
			basic.setAddress("乐山路33号");
			System.out.println("basic.address:"+basic.getAddress());
			Response result = basicService.postResource(basic);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			e.printStackTrace();
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/basic", method = RequestMethod.PUT)
	@ResponseBody
	public String put(HttpServletRequest request, HttpServletResponse response) {
		try {
			Basic basic = ParamUtils.initModelForm(request, Basic.class);
			Response result = basicService.putResource(basic);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/basic", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			Basic basic = ParamUtils.initModelForm(request, Basic.class);
			Response result = basicService.delResource(basic);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
