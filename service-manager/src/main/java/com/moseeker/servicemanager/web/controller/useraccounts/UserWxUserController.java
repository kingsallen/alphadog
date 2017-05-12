package com.moseeker.servicemanager.web.controller.useraccounts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.annotation.iface.CounterIface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.WxUserDao;

@Controller
@CounterIface
public class UserWxUserController {

	Logger log = LoggerFactory.getLogger(getClass());
	
	WxUserDao.Iface wxuser = ServiceManager.SERVICEMANAGER.getService(WxUserDao.Iface.class);
	
	@RequestMapping(value = "user/wxuser", method=RequestMethod.GET)
	@ResponseBody
	public String getUserWxUser(HttpServletRequest request, HttpServletResponse response) {
		// PrintWriter writer = null;
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);

			Response result = wxuser.getResource(query);
			if (result.getStatus() != 0) {
				return ResponseLogNotification.fail(request, result.getMessage());
			}
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
