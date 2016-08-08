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

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;


@Controller
public class SearchengineController {

	Logger logger = LoggerFactory.getLogger(SearchengineController.class);

	SearchengineServices.Iface searchengineServices = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);
	
	@RequestMapping(value = "/search/position", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String postuserlogin(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> reqParams = ParamUtils.mergeRequestParameters(request);
			String keywords = BeanUtils.converToString(reqParams.get("keywords"));
			String cities = BeanUtils.converToString(reqParams.get("cities"));
			String industries = BeanUtils.converToString(reqParams.get("industries"));
			String occupations = BeanUtils.converToString(reqParams.get("occupations"));
			String scale = BeanUtils.converToString(reqParams.get("scale"));
			String employment_type = BeanUtils.converToString(reqParams.get("employment_type"));
			String candidate_source = BeanUtils.converToString(reqParams.get("candidate_source"));
			String experience = BeanUtils.converToString(reqParams.get("experience"));
			String degree = BeanUtils.converToString(reqParams.get("degree"));
			String salary = BeanUtils.converToString(reqParams.get("salary"));
			String company_id = BeanUtils.converToString(reqParams.get("company_id"));
			int page_from = BeanUtils.converToInteger(reqParams.get("page_from"));
			int page_size = BeanUtils.converToInteger(reqParams.get("page_size"));
			Integer  child_company_id =  BeanUtils.converToInteger(reqParams.get("child_company_id"));
			String child_company_id_str = child_company_id.toString();
			System.out.println(keywords);
			Response result = searchengineServices.query(
							keywords,
							cities,
							industries,
							occupations,
							scale,
							employment_type,
							candidate_source,
							experience,
							degree,
							salary,
							company_id,
							page_from,
							page_size,
							child_company_id_str);
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
