package com.moseeker.servicemanager.web.controller.useraccounts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.foundation.wordpress.struct.NewsletterForm;
import com.moseeker.thrift.gen.useraccounts.service.UserCommonService;

@Controller
public class UserCommonController {

	Logger logger = LoggerFactory.getLogger(UseraccountsController.class);

	UserCommonService.Iface userCommonService = ServiceManager.SERVICEMANAGER
			.getService(UserCommonService.Iface.class);
	
	@RequestMapping(value = "/user/platform/newsletter", method = RequestMethod.POST)
	@ResponseBody
	public String newsletter(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			NewsletterForm form = ParamUtils.initModelForm(request, NewsletterForm.class);
			
			Response result = userCommonService.newsletter(form);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		} finally {
			//do nothing
		}
	}
}
