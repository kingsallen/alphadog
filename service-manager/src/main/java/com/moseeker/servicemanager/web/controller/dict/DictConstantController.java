package com.moseeker.servicemanager.web.controller.dict;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.moseeker.common.annotation.iface.CounterIface;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.DictConstanService;

/**
 * 字典常量服务
 * <p>
 *
 * Created by zzh on 16/5/27.
 */
// @Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
@CounterIface
public class DictConstantController {


	Logger logger = org.slf4j.LoggerFactory.getLogger(CityController.class);

	DictConstanService.Iface dictConstanService = ServiceManager.SERVICE_MANAGER
			.getService(DictConstanService.Iface.class);

	@RequestMapping(value = "/dict/constant", method = RequestMethod.GET)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<Integer> parentCodeList = null;
			String[] parentCodes = request.getParameterValues("parent_code");
			if (parentCodes != null) {
				parentCodeList = new ArrayList<Integer>();
				for (String parentCode : parentCodes) {
					parentCodeList.add(Integer.valueOf(parentCode));
				}
			}
			Response result = dictConstanService.getDictConstantJsonByParentCode(parentCodeList);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}
}
