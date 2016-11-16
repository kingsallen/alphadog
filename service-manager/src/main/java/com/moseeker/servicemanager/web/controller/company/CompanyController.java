package com.moseeker.servicemanager.web.controller.company;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;
import com.moseeker.thrift.gen.company.struct.Hrcompany;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
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
	
	@RequestMapping(value = "/company/{id}/thirdpartyaccount", method = RequestMethod.GET)
	@ResponseBody
	public String getAllCompany(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {
		try {
			HashMap<String, Object> map = ParamUtils.parseRequestParam(request);
			byte channel = 0;
			if(map.get("channel") != null) {
				try {
					channel = ((Integer)map.get("channel")).byteValue();
				} catch (Exception e) {
					return ResponseLogNotification.fail(request, "渠道参数不正确!");
				}
			}
			Response result = companyServices.synchronizeThirdpartyAccount(id, channel);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
}
