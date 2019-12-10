package com.moseeker.servicemanager.web.controller.profile;

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
import com.moseeker.thrift.gen.profile.service.SkillServices;
import com.moseeker.thrift.gen.profile.struct.Skill;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class SkillController {

	Logger logger = LoggerFactory.getLogger(SkillController.class);

	SkillServices.Iface skillService = ServiceManager.SERVICE_MANAGER.getService(SkillServices.Iface.class);
	
	@RequestMapping(value = "/profile/skill", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);

			Response result = skillService.getResources(query);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e);
		}
	}

	@RequestMapping(value = "/profile/skill", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			Skill skill = ParamUtils.initModelForm(request, Skill.class);
			Response result = skillService.postResource(skill);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}

	@RequestMapping(value = "/profile/skill", method = RequestMethod.PUT)
	@ResponseBody
	public String put(HttpServletRequest request, HttpServletResponse response) {
		try {
			Skill skill = ParamUtils.initModelForm(request, Skill.class);
			Response result = skillService.putResource(skill);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e);
		}
	}

	@RequestMapping(value = "/profile/skill", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			Skill skill = ParamUtils.initModelForm(request, Skill.class);
			Response result = skillService.delResource(skill);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e);
		}
	}
}
