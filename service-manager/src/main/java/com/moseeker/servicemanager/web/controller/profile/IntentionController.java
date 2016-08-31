package com.moseeker.servicemanager.web.controller.profile;

import java.util.HashMap;
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

import com.moseeker.common.util.BeanUtils;
import com.moseeker.rpccenter.client.ServiceManager;
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

	IntentionServices.Iface intentionService = ServiceManager.SERVICEMANAGER.getService(IntentionServices.Iface.class);

	@RequestMapping(value = "/profile/intention", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		// PrintWriter writer = null;
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
		// PrintWriter writer = null;
		try {
			Map<String, Object> data = ParamUtils.mergeRequestParameters(request);
			Intention intention = ParamUtils.initModelForm(data, Intention.class);
			buildIntention(data, intention);
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
			Map<String, Object> data = ParamUtils.mergeRequestParameters(request);
			Intention intention = ParamUtils.initModelForm(data, Intention.class);
			buildIntention(data, intention);

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
			Map<String, Object> data = ParamUtils.mergeRequestParameters(request);
			Intention intention = ParamUtils.initModelForm(data, Intention.class);
			buildIntention(data, intention);
			Response result = intentionService.delResource(intention);

			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
	
	private void buildIntention(Map<String, Object> reqParams, Intention intention) {
		Map<Integer, Integer> industryCode = new HashMap<>();
		Map<String, Integer> industryName= new HashMap<>();
		
		Map<Integer, Integer> positionCode = new HashMap<>();
		Map<String, Integer> positionName= new HashMap<>();
		
		Map<Integer, Integer> cityCode = new HashMap<>();
		Map<String, Integer> cityName= new HashMap<>();
		if (reqParams != null) {
			for (Entry<String, Object> entry : reqParams.entrySet()) {
				if(entry.getKey().startsWith("industries[")) {
					if(entry.getKey().contains("industry_code")) {
						industryCode.put(Integer.valueOf(entry.getKey().charAt(11)), BeanUtils.converToInteger(entry.getValue()));
					}
					if(entry.getKey().contains("industry_name")) {
						industryName.put((String)entry.getValue(), Integer.valueOf(entry.getKey().charAt(11)));
					}
				}
				
				if(entry.getKey().startsWith("cities[")) {
					if(entry.getKey().contains("city_code")) {
						cityCode.put(Integer.valueOf(entry.getKey().charAt(7)), BeanUtils.converToInteger(entry.getValue()));
					}
					if(entry.getKey().contains("city_name")) {
						cityName.put((String)entry.getValue(), Integer.valueOf(entry.getKey().charAt(7)));
					}
				}
				
				if(entry.getKey().startsWith("positions[")) {
					if(entry.getKey().contains("position_code")) {
						positionCode.put(Integer.valueOf(entry.getKey().charAt(10)), BeanUtils.converToInteger(entry.getValue()));
					}
					if(entry.getKey().contains("position_name")) {
						positionName.put((String)entry.getValue(), Integer.valueOf(entry.getKey().charAt(10)));
					}
				}
			}
		}
		if(industryName.size() > 0) {
			for(Entry<String, Integer> entry : industryName.entrySet()) {
				if(intention.getIndustries() == null) {
					intention.setIndustries(new HashMap<String, Integer>());
				}
				int code = 0;
				if(industryCode.size() > 0) {
					if(industryCode.get(entry.getValue()) != null) {
						code = industryCode.get(entry.getValue());
					}
				}
				intention.getIndustries().put(entry.getKey(), code);
			}
		}
		if(positionName.size() > 0) {
			for(Entry<String, Integer> entry : positionName.entrySet()) {
				if(intention.positions == null) {
					intention.setPositions(new HashMap<String, Integer>());
				}
				int code = 0;
				if(positionCode.size() > 0) {
					if(positionCode.get(entry.getValue()) != null) {
						code = positionCode.get(entry.getValue());
					}
				}
				intention.getPositions().put(entry.getKey(), code);
			}
		}
		if(cityName.size() > 0) {
			for(Entry<String, Integer> entry : cityName.entrySet()) {
				if(intention.cities == null) {
					intention.setCities(new HashMap<String, Integer>());
				}
				int code = 0;
				if(cityCode.get(entry.getValue()) != null) {
					code = cityCode.get(entry.getValue());
				}
				intention.getCities().put(entry.getKey(), code);
			}
		}
	}

}
