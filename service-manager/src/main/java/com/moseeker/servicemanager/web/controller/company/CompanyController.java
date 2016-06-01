package com.moseeker.servicemanager.web.controller.company;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.common.ServiceUtil;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.company.service.CompanyServices;

@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class CompanyController {

	Logger logger = LoggerFactory.getLogger(CompanyController.class);

	CompanyServices.Iface companyServices = ServiceUtil.getService(CompanyServices.Iface.class);

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
}
