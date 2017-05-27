package com.moseeker.servicemanager.web.controller.company;

import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class CompanyController {

	Logger logger = LoggerFactory.getLogger(CompanyController.class);

	CompanyServices.Iface companyServices = ServiceManager.SERVICEMANAGER.getService(CompanyServices.Iface.class);

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	@ResponseBody
	public String getcompany(HttpServletRequest request, HttpServletResponse response) {
		try {
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
			Response result = companyServices.getResources(query);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		} finally {
			// do nothing
		}
	}

	@RequestMapping(value = "/company/all", method = RequestMethod.GET)
	@ResponseBody
	public String getAllCompany(HttpServletRequest request, HttpServletResponse response) {
		try {
			CommonQuery query = ParamUtils.initCommonQuery(request, CommonQuery.class);
			Response result = companyServices.getAllCompanies(query);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
	
	@RequestMapping(value = "/company", method = RequestMethod.POST)
	@ResponseBody
	public String addCompany(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, Object> data = ParamUtils.parseRequestParam(request);
			Hrcompany company = ParamUtils.initModelForm(data, Hrcompany.class);
			if(company.getSource() == 0) {
				Integer appid = BeanUtils.converToInteger(data.get("appid"));
				if(appid == null) {
					appid = 0;
				}
				if(appid.intValue() == Constant.APPID_C) {
					company.setSource(Constant.COMPANY_SOURCE_PC_EDITING);
				}
				if(appid.intValue() == Constant.APPID_QX || appid.intValue() == Constant.APPID_PLATFORM) {
					company.setSource(Constant.COMPANY_SOURCE_PC_EDITING);
				}
			}
			Response result = companyServices.add(company);
			if (result.getStatus() == 0) {
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, result);
			}

		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		} finally {
			// do nothing
		}
	}
}
