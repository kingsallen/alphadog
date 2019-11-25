package com.moseeker.servicemanager.web.controller.useraccounts;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UserQxService;

@Controller
@CounterIface
public class UserEmailPositionController {
	Logger logger = LoggerFactory.getLogger(getClass());
	UserQxService.Iface userQxService = ServiceManager.SERVICE_MANAGER.getService(UserQxService.Iface.class);
	@RequestMapping(value = "/user/email/recommendposition", method=RequestMethod.GET)
	@ResponseBody
	public String sendEmailPosition(HttpServletRequest request, HttpServletResponse response) {
		 Map<String, Object> reqParams = null;
    	 try{
    		 reqParams = ParamUtils.parseRequestParam(request);
    		 logger.info(JSON.toJSONString(reqParams)+"++++++++++++++++++");
    		 int userId=Integer.parseInt(reqParams.get("userId")+"");
    		 logger.info("向user_id为{}的用户发送职位推荐邮件",userId);
    		 Response res=userQxService.sendRecommendPosition(userId);
    		 return ResponseLogNotification.success(request,res);
    	 }catch(Exception e){
    		 return ResponseLogNotification.fail(request, e);
    	 }
	}
	
	@RequestMapping(value = "/user/email/validate", method=RequestMethod.GET)
	@ResponseBody
	public String sendEmailvalidate(HttpServletRequest request, HttpServletResponse response) {
		 Map<String, Object> reqParams = null;
	   	 try{
	   		 reqParams = ParamUtils.parseRequestParam(request);
	   		 logger.info(reqParams+"+++++++++++++++++++/user/email/validate");
			 int userId=Integer.parseInt(reqParams.get("userId")+"");
	   		 String email= (String) reqParams.get("email");
			 String conditions=(String)reqParams.get("conditions");
			 logger.info("conditions==={}==",conditions);
			 if(!StringUtils.isNotNullOrEmpty(conditions)){
				 conditions="";
			 }
			 String urls=(String) reqParams.get("url");
	   		 logger.info("向user_id为{}的用户发送邮箱验证邮件",userId);
	   		 Response res=userQxService.sendValiddateEmail(email, userId, conditions,urls);
	   		 return ResponseLogNotification.success(request,res);
	   	 }catch(Exception e){
	   		 return ResponseLogNotification.fail(request, e);
	   	 }
	}
	
	@RequestMapping(value = "/emailposition", method=RequestMethod.POST)
	@ResponseBody
	public String PostUserEmailPosition(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> reqParams = null;
	   	 try{
	   		 reqParams = ParamUtils.parseRequestParam(request);
	   		 logger.info("参数reqParams===={}",reqParams);
			 int userId=Integer.parseInt(reqParams.get("userId")+"");
			 String conditions=(String)reqParams.get("conditions");
			 logger.info("conditions==={}==",conditions);
			 if(!StringUtils.isNotNullOrEmpty(conditions)){
				 conditions="";
			 }
	   		 logger.info("将user_id为{}的用户查询条件为{"+conditions+"}保存或者更新到数据库",userId);
	   		 Response res=userQxService.postUserEmailPosition(userId, conditions);
	   		 return ResponseLogNotification.success(request,res);
	   	 }catch(Exception e){
	   		 return ResponseLogNotification.fail(request, e);
	   	 }
	}
}
