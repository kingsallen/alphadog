package com.moseeker.servicemanager.web.controller.profile;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.IntentionServices;
import com.moseeker.thrift.gen.profile.struct.Intention;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class IntentionController {

	Logger logger = LoggerFactory.getLogger(IntentionController.class);

	IntentionServices.Iface intentionService = ServiceUtil.getService(IntentionServices.Iface.class);
	
	@RequestMapping(value = "/profile/intention", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			// GET方法 通用参数解析并赋值
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);

			Response result = intentionService.getResources(query);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/intention", method = RequestMethod.POST)
	@ResponseBody
	public String post(HttpServletRequest request, HttpServletResponse response) {
		//PrintWriter writer = null;
		try {
			Intention intention = ParamUtils.initModelForm(request, Intention.class);
			ParamUtils.buildIntention(request, intention);
			if(intention != null && intention.getCities() != null) {
				Map<String ,Integer> city = intention.getCities();
				for(Entry<String, Integer> entry : city.entrySet()) {
					logger.debug("city:"+entry.getKey()+" city_code:"+entry.getValue());
				}
			} else {
				logger.info("city is null");
			}
			if(intention != null && intention.getCities() != null) {
				Map<String ,Integer> position = intention.getPositions();
				for(Entry<String, Integer> entry : position.entrySet()) {
					logger.debug("position_name:"+entry.getKey()+" position_code:"+entry.getValue());
				}
			} else {
				logger.info("position is null");
			}
			if(intention != null && intention.getIndustries() != null) {
				Map<String ,Integer> industry = intention.getIndustries();
				for(Entry<String, Integer> entry : industry.entrySet()) {
					logger.debug("industry_name:"+entry.getKey()+" industry_code:"+entry.getValue());
				}
			} else {
				logger.info("industry is null");
			}
			Response result = intentionService.postResource(intention);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			e.printStackTrace();
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/intention", method = RequestMethod.PUT)
	@ResponseBody
	public String put(HttpServletRequest request, HttpServletResponse response) {
		try {
			Intention intention = ParamUtils.initModelForm(request, Intention.class);
			ParamUtils.buildIntention(request, intention);
			
			Response result = intentionService.putResource(intention);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	@RequestMapping(value = "/profile/intention", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(HttpServletRequest request, HttpServletResponse response) {
		try {
			Intention education = ParamUtils.initModelForm(request, Intention.class);
			ParamUtils.buildIntention(request, education);
			Response result = intentionService.delResource(education);
			
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {	
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
