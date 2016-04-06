package com.moseeker.servicemanager.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.servicemanager.web.service.DemoServiceClient;


/**
 * Created by zzh on 16/3/31.
 */
@Controller
public class DemoController {
	
	Logger logger = LoggerFactory.getLogger(DemoController.class);

	@Autowired
    private DemoServiceClient demoService;

    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    @ResponseBody
    public String get(HttpServletRequest request, HttpServletResponse response) {

            String msg = request.getParameter("msg");
            String res = "";
			try {
				res = demoService.echo(msg);
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("hello world!");
            return res;
    }

	public DemoServiceClient getDemoService() {
		return demoService;
	}

	public void setDemoService(DemoServiceClient demoService) {
		this.demoService = demoService;
	}
}
