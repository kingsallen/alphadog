package com.moseeker.servicemanager.web.controller.searchengine;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.common.util.BeanUtils;
import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.userloginreq;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;


@Controller
public class SearchengineController {

	Logger logger = LoggerFactory.getLogger(SearchengineController.class);

	SearchengineServices.Iface searchengineServices = ServiceUtil.getService(SearchengineServices.Iface.class);
	
	@RequestMapping(value = "/search/position", method = RequestMethod.GET)
	@ResponseBody
	public String postuserlogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			userloginreq userloginreqs = ParamUtils.initModelForm(request, userloginreq.class);
			
			Response result = searchengineServices.query("java", null);
			if (result.getStatus() == 0){
				return ResponseLogNotification.success(request, result);
			}else{
				return ResponseLogNotification.fail(request, result);
			}
			
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
