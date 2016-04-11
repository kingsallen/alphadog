package com.moseeker.servicemanager.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.servicemanager.util.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.thrift.gen.echo.EchoService.Iface;

/**
 * Created by zzh on 16/3/31.
 */
@Controller
public class DemoController{

	Logger logger = LoggerFactory.getLogger(DemoController.class);

	Iface demoService = ServiceUtil.getService(Iface.class);

	@RequestMapping(value = "/demo", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {

		System.out.print(Iface.class.getName());
		String msg = request.getParameter("msg");
		String res = "";
		try {
			res = demoService.echo(msg);
		} catch (Exception e){
			e.printStackTrace();
		}
		logger.info("hello world!");
		return res;
	}
}
