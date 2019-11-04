package com.moseeker.servicemanager.web.controller.profile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.BasicServices;
import com.moseeker.thrift.gen.profile.struct.Basic;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class BasicController {

	Logger logger = LoggerFactory.getLogger(BasicController.class);

	BasicServices.Iface basicService = ServiceManager.SERVICE_MANAGER.getService(BasicServices.Iface.class);
	
	@RequestMapping(value = "/profile/basic", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request) {
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
	public String post(HttpServletRequest request) {
		//PrintWriter writer = null;
		try {
			Basic basic = ParamUtils.initModelForm(request, Basic.class);
			String name=basic.getName();
			/*
			校验简历里面的姓名的长度不能超过100个字符，在服务方法里也作了限制，为的是避免将来service-manager不用了，而没有注意成为bug
			 */
			if(StringUtils.isNotNullOrEmpty(name)&&name.length()>100){
				return ResponseLogNotification.fail(request, "不能超过100个英文字母或者50个汉字");
			}

			String motto = basic.getMotto();
			String qq = basic.getQq();
			String weixin = basic.getWeixin();
			if ( (StringUtils.isNotNullOrEmpty(motto)&&motto.length()>50)
					||(StringUtils.isNotNullOrEmpty(qq)&&qq.length()>50)
					||(StringUtils.isNotNullOrEmpty(weixin)&&weixin.length()>50)) {
				return ResponseLogNotification.fail(request, "不能超过50个字符！");
			}
			Response result = basicService.postResource(basic);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			e.printStackTrace();
			if (e instanceof DataIntegrityViolationException) {
				return ResponseLogNotification.fail(request, "数据输入异常！");
			}
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/basic", method = RequestMethod.PUT)
	@ResponseBody
	public String put(HttpServletRequest request, HttpServletResponse response) {
		try {
			Basic basic = ParamUtils.initModelForm(request, Basic.class);
			String name=basic.getName();
			/*
			校验简历里面的姓名的长度不能超过100个字符，在服务方法里也作了限制，为的是避免将来service-manager不用了，而没有注意成为bug
			 */
			if(StringUtils.isNotNullOrEmpty(name)&&name.length()>100){
				return ResponseLogNotification.fail(request, "不能超过100个英文字母或者50个汉字");
			}

			String motto = basic.getMotto();
			String qq = basic.getQq();
			String weixin = basic.getWeixin();
			if ( (StringUtils.isNotNullOrEmpty(motto)&&motto.length()>50)
					||(StringUtils.isNotNullOrEmpty(qq)&&qq.length()>50)
					||(StringUtils.isNotNullOrEmpty(weixin)&&weixin.length()>50)) {
				return ResponseLogNotification.fail(request, "不能超过50个字符！");
			}

			String city_name = basic.getCity_name();
			if(StringUtils.isNotNullOrEmpty(city_name) && city_name.matches("^\\d+$")) {
				return ResponseLogNotification.fail(request, "请输入正确的居住地！");
			}
			Response result = basicService.putResource(basic);
			logger.info("BasicController.put basic:{}", basic);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				return ResponseLogNotification.fail(request, "数据输入异常！");
			}
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/basic/", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			//System.out.println(id);
			Basic basic = ParamUtils.initModelForm(request, Basic.class);
			Response result = basicService.delResource(basic);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
