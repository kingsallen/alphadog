package com.moseeker.servicemanager.web.controller.useraccounts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.useraccounts.service.UserHrAccountService;
import com.moseeker.thrift.gen.useraccounts.struct.BindAccountStruct;
import com.moseeker.thrift.gen.useraccounts.struct.DownloadReport;

/**
 * HR账号服务
 * <p>
 *
 * Created by zzh on 16/6/1.
 */
// @Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class UserHrAccountController {

	Logger logger = LoggerFactory.getLogger(UseraccountsController.class);

	UserHrAccountService.Iface userHrAccountService = ServiceManager.SERVICEMANAGER
			.getService(UserHrAccountService.Iface.class);

	/**
	 * 注册HR发送验证码
	 *
	 */
	@RequestMapping(value = "/hraccount/sendsignupcode", method = RequestMethod.POST)
	@ResponseBody
	public String sendMobileVerifiyCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取HR用户实体对象
			String mobile = request.getParameter("mobile");
			String code = request.getParameter("code");
			int source = Integer.valueOf(request.getParameter("source"));

			Response result = userHrAccountService.sendMobileVerifiyCode(mobile, code, source);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}

	/**
	 * 添加HR账号
	 * <p>
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/hraccount", method = RequestMethod.POST)
	@ResponseBody
	public String postUserHrAccount(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取HR用户实体对象
			DownloadReport downloadReport = ParamUtils.initModelForm(request, DownloadReport.class);
			ValidateUtil vu = new ValidateUtil();
			vu.addRequiredStringValidate("验证码", downloadReport.getCode(), null, null);
			vu.addRequiredStringValidate("公司名称", downloadReport.getCompany_name(), null, null);
			String message = vu.validate();
			if (StringUtils.isNullOrEmpty(message)) {
				Response result = userHrAccountService.postResource(downloadReport);
				return ResponseLogNotification.success(request, result);
			} else {
				return ResponseLogNotification.fail(request, message);
			}
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		}
	}
	
	@RequestMapping(value = "/hraccount/bind", method = RequestMethod.POST)
	@ResponseBody
	public String bindThirdPartyAccount(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			BindAccountStruct struct = ParamUtils.initModelForm(request, BindAccountStruct.class);
			
			Response result = userHrAccountService.bind(struct);
			return ResponseLogNotification.success(request, result);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e.getMessage());
		} finally {
			//do nothing
		}
	}
}
