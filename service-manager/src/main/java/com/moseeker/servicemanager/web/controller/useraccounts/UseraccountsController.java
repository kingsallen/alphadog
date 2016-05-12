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

import com.moseeker.common.util.BeanUtils;
import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.userloginreq;

@Controller
public class UseraccountsController {

	Logger logger = LoggerFactory.getLogger(UseraccountsController.class);

	UseraccountsServices.Iface useraccountsServices = ServiceUtil.getService(UseraccountsServices.Iface.class);
	
	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public String postuserlogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			userloginreq userloginreqs = ParamUtils.initModelForm(request, userloginreq.class);
			
			Response result = useraccountsServices.postuserlogin(userloginreqs);
			if (result.getStatus() == 0){
				return ResponseLogNotification.success(request, result);
			}else{
				return ResponseLogNotification.fail(request, result);
			}
			
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
	
	@RequestMapping(value = "/user/logout", method = RequestMethod.POST)
	@ResponseBody
	public String postuserlogout(HttpServletRequest request, HttpServletResponse response) {
		try {
			Object user_id = ParamUtils.mergeRequestParameters(request).get("user_id");
			Response result = useraccountsServices.postuserlogout(BeanUtils.converToInteger(user_id));
			if (result.getStatus() == 0){
				return ResponseLogNotification.success(request, result);
			}else{
				return ResponseLogNotification.fail(request, result);
			}
			
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}	
	
	
	@RequestMapping(value = "/user/mobilesignup", method = RequestMethod.POST)
	@ResponseBody
	public String postusermobilesignup(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map reqParams = ParamUtils.mergeRequestParameters(request);
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			String code = BeanUtils.converToString(reqParams.get("code"));
			String password = BeanUtils.converToString(reqParams.get("password"));

			Response result = useraccountsServices.postusermobilesignup(mobile, code, password);
			if (result.getStatus() == 0){
				return ResponseLogNotification.success(request, result);
			}else{
				return ResponseLogNotification.fail(request, result);
			}
			
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}	
	
	
	@RequestMapping(value = "/user/sendsignupcode", method = RequestMethod.POST)
	@ResponseBody
	public String postsendsignupcode(HttpServletRequest request, HttpServletResponse response) {
		try {
			Object mobile = ParamUtils.mergeRequestParameters(request).get("mobile");
			Response result = useraccountsServices.postsendsignupcode(BeanUtils.converToString(mobile));
			if (result.getStatus() == 0){
				return ResponseLogNotification.success(request, result);
			}else{
				return ResponseLogNotification.fail(request, result);
			}
			
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}	

	@RequestMapping(value = "/user/wxbindmobile", method = RequestMethod.POST)
	@ResponseBody
	public String postuserwxbindmobile(HttpServletRequest request, HttpServletResponse response) {
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
			Map reqParams = ParamUtils.mergeRequestParameters(request);
			String unionid = BeanUtils.converToString(reqParams.get("unionid"));
			String code = BeanUtils.converToString(reqParams.get("code"));
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));

			Response result = useraccountsServices.postuserwxbindmobile(query.getAppid(), unionid, code, mobile);
			if (result.getStatus() == 0){
				return ResponseLogNotification.success(request, result);
			}else{
				return ResponseLogNotification.fail(request, result);
			}
			
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}	
	
	@RequestMapping(value = "/user/changepassword", method = RequestMethod.POST)
	@ResponseBody
	public String postuserchangepassword(HttpServletRequest request, HttpServletResponse response) {
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
			Map reqParams = ParamUtils.mergeRequestParameters(request);
			int user_id = BeanUtils.converToInteger(reqParams.get("user_id"));
			String old_password = BeanUtils.converToString(reqParams.get("old_password"));
			String password = BeanUtils.converToString(reqParams.get("password"));

			Response result = useraccountsServices.postuserchangepassword(user_id, old_password, password);
			if (result.getStatus() == 0){
				return ResponseLogNotification.success(request, result);
			}else{
				return ResponseLogNotification.fail(request, result);
			}
			
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}	
	
	
	@RequestMapping(value = "/user/sendpasswordforgotcode", method = RequestMethod.POST)
	@ResponseBody
	public String postusersendpasswordforgotcode(HttpServletRequest request, HttpServletResponse response) {
		try {
			Object mobile = ParamUtils.mergeRequestParameters(request).get("mobile");
			Response result = useraccountsServices.postusersendpasswordforgotcode(BeanUtils.converToString(mobile));
			if (result.getStatus() == 0){
				return ResponseLogNotification.success(request, result);
			}else{
				return ResponseLogNotification.fail(request, result);
			}
			
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}	
	
	@RequestMapping(value = "/user/resetpassword", method = RequestMethod.POST)
	@ResponseBody
	public String postuserresetpassword(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map reqParams = ParamUtils.mergeRequestParameters(request);
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));
			String code = BeanUtils.converToString(reqParams.get("code"));
			String password = BeanUtils.converToString(reqParams.get("password"));

			Response result = useraccountsServices.postuserresetpassword(mobile, code, password);
			if (result.getStatus() == 0){
				return ResponseLogNotification.success(request, result);
			}else{
				return ResponseLogNotification.fail(request, result);
			}
			
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}	
	
	@RequestMapping(value = "/user/mergebymobile", method = RequestMethod.POST)
	@ResponseBody
	public String postusermergebymobile(HttpServletRequest request, HttpServletResponse response) {
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
			Map reqParams = ParamUtils.mergeRequestParameters(request);
			String mobile = BeanUtils.converToString(reqParams.get("mobile"));

			Response result = useraccountsServices.postusermergebymobile(query.getAppid(), mobile);
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
