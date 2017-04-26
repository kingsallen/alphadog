package com.moseeker.servicemanager.web.controller.crawler;

import java.net.ConnectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.servicemanager.common.ParamUtils;
import com.moseeker.servicemanager.common.ResponseLogNotification;
import com.moseeker.servicemanager.web.controller.profile.ImportCVForm;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices;

//@Scope("prototype") // 多例模式, 单例模式无法发现新注册的服务节点
@Controller
public class CrawlerController {

	Logger logger = LoggerFactory.getLogger(CrawlerController.class);

	CrawlerUtils crawlerUtils = new CrawlerUtils();
	WholeProfileServices.Iface profileService = ServiceManager.SERVICEMANAGER
			.getService(WholeProfileServices.Iface.class);

	@RequestMapping(value = "/crawler", method = RequestMethod.POST)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		// PrintWriter writer = null;
		try {
			ImportCVForm form = ParamUtils.initModelForm(request, ImportCVForm.class);
			// GET方法 通用参数解析并赋值
			ValidateUtil vu = new ValidateUtil();
			vu.addIntTypeValidate("导入方式", form.getType(), null, null, 1, 5);
			vu.addIntTypeValidate("用户编号", form.getUser_id(), null, null, 1, Integer.MAX_VALUE);
			vu.addIntTypeValidate("项目编号", form.getAppid(), null, null, 0, 100);
			if (form.getType() == 4) {
				vu.addRequiredStringValidate("token", form.getToken(), null, null);
			} else {
				vu.addRequiredStringValidate("账号", form.getUsername(), null, null);
				vu.addRequiredStringValidate("密码", form.getPassword(), null, null);
			}
			String result = vu.validate();
			if (StringUtils.isNullOrEmpty(result)) {
				logger.info("/crawler");
				Response res = crawlerUtils.fetchFirstResume(form.getUsername(), form.getPassword(), form.getToken(),
						form.getType(), form.getLang(), form.getSource(), form.getCompleteness(), form.getAppid(),
						form.getUser_id(), form.getUa());
				if (res != null && res.getStatus() == 0) {
					logger.info("/crawler    profile:"+res.getData());
					res = profileService.importCV(res.getData(), form.getUser_id());
					return ResponseLogNotification.success(request, res);
				} else {
					return ResponseLogNotification.fail(request, res);
				}

			} else {
				return ResponseLogNotification.fail(request, result);
			}
		} catch (ConnectException e) {
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request,
					ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_SERVICE_TIMEOUT));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseLogNotification.fail(request, e.getMessage());
		} finally {
			// do nothing
		}
	}
}
