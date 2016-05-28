package com.moseeker.servicemanager.web.controller.crawler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;

@Controller
public class CrawlerController {

	Logger logger = LoggerFactory.getLogger(CrawlerController.class);

	CrawlerUtils crawlerUtils = new CrawlerUtils();
	
	@RequestMapping(value = "/crawler", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			// GET方法 通用参数解析并赋值
			String userName = request.getParameter("username");
			String password = request.getParameter("password");
			String type = request.getParameter("type");
			String userId = request.getParameter("user_id");
			String appid = request.getParameter("appid");
			ValidateUtil vu = new ValidateUtil();
			vu.addRequiredStringValidate("用户账号", userName, null, null);
			vu.addRequiredStringValidate("密码", password, null, null);
			vu.addIntTypeValidate("导入方式", type, null, null, 1, 5);
			vu.addIntTypeValidate("用户编号", userId, null, null, 1, Integer.MAX_VALUE);
			vu.addIntTypeValidate("项目编号", appid, null, null, 1, Integer.MAX_VALUE);
			String result = vu.toString();
			if(StringUtils.isNullOrEmpty(result)) {
				crawlerUtils.fetchFirstResume(userName, password, Integer.valueOf(type), Integer.valueOf(userId));
				
			} else {
				return ResponseLogNotification.fail(request, result);
			}
			
			return ResponseLogNotification.success(request, new Response());
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
