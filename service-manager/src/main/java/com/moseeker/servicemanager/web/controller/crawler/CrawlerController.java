package com.moseeker.servicemanager.web.controller.crawler;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.apps.positionbs.service.PositionBS;
import com.moseeker.thrift.gen.apps.positionbs.struct.ScraperHtmlParam;
import com.moseeker.thrift.gen.common.struct.BIZException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@CounterIface
public class CrawlerController {

	Logger logger = LoggerFactory.getLogger(CrawlerController.class);

	@Autowired
	CrawlerUtils crawlerUtils;

	WholeProfileServices.Iface profileService = ServiceManager.SERVICE_MANAGER
			.getService(WholeProfileServices.Iface.class);

	PositionBS.Iface positionbs = ServiceManager.SERVICE_MANAGER
			.getService(PositionBS.Iface.class);

	private ParserConfig parserConfig = new ParserConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题


	public CrawlerController(){
		parserConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
	}


	@RequestMapping(value = "/crawler", method = RequestMethod.POST)
	@ResponseBody
	public String get(HttpServletRequest request, HttpServletResponse response) {
		// PrintWriter writer = null;
		try {
			ImportCVForm form = ParamUtils.initModelForm(request, ImportCVForm.class);
			// GET方法 通用参数解析并赋值
			ValidateUtil vu = new ValidateUtil();
			vu.addIntTypeValidate("导入方式", form.getType(), null, null, 1, 20);
			vu.addIntTypeValidate("用户编号", form.getUser_id(), null, null, 1, Integer.MAX_VALUE);
			vu.addIntTypeValidate("项目编号", form.getAppid(), null, null, 0, 100);
			if(form.getType() == ChannelType.MAIMAI.getValue()) {
				vu.addRequiredStringValidate("token", form.getToken(), null, null);
				vu.addRequiredStringValidate("version", form.getVersion(), null, null);
				vu.addRequiredStringValidate("maimai_appid", form.getMaimai_appid(), null, null);
				vu.addRequiredStringValidate("unionid", form.getUnionid(), null, null);
			} else if(form.getType() == ChannelType.ZHILIAN.getValue()){
				vu.addRequiredStringValidate("username",form.getUsername());
			} else if(form.getType() == ChannelType.LIEPIN.getValue()){
				if(StringUtils.isNullOrEmpty(form.getToken())){
					vu.addRequiredStringValidate("账号", form.getUsername(), null, null);
					vu.addRequiredStringValidate("密码", form.getPassword(), null, null);
				} else {
					vu.addRequiredStringValidate("token", form.getToken(), null, null);
				}
			} else if(form.getType() != ChannelType.LINKEDIN.getValue()){
				vu.addRequiredStringValidate("账号", form.getUsername(), null, null);
				vu.addRequiredStringValidate("密码", form.getPassword(), null, null);
			}

			String result = vu.validate();
			if (StringUtils.isNullOrEmpty(result)) {
				logger.info("/crawler");
				Response res = crawlerUtils.fetchFirstResume(form);
				logger.info("crawler crawlerUtils.fetchFirstResume:{}",res);
				if (res != null && res.getStatus() == 0) {
					logger.info("/crawler profile:"+res.getData());
					res = profileService.importCV(res.getData(), form.getUser_id());
					return ResponseLogNotification.success(request, res);
				} else {
					return ResponseLogNotification.fail(request, res);
				}

			} else {
				return ResponseLogNotification.fail(request, result);
			}
		} catch (ConnectException e) {
			logger.error("CrawlerController.get error:{}, url:{}, method:{}, reason:{}", e.getMessage(), "/crawler", "POST", e);
			return ResponseLogNotification.fail(request,
					ResponseUtils.fail(ConstantErrorCodeMessage.CRAWLER_SERVICE_TIMEOUT), e);
		} catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		} finally {
			// do nothing
		}
	}

	/**
	 * 调用scraper获取html
	 * 根据传入的position_id和channel找到第三方账号，
	 * 推送 查询出的第三方账号和密码加上传入的url 给scraper
	 * scraper获取html返回
	 * {
		 "appid":4,
		 "position_id":1,
		 "url":"http://www.moseeker.com",
		 "channel":8
	 	}
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getThirdPartyHtml", method = RequestMethod.POST)
	@ResponseBody
	public String getThirdPartyHtml(HttpServletRequest request, HttpServletResponse response) {
		try {
			String paramJson = ParamUtils.parseJsonParam(request);
			ScraperHtmlParam param = JSON.parseObject(paramJson,ScraperHtmlParam.class,parserConfig);

			Map<String,String> result=new HashMap<>();

			result.put("html",positionbs.getThirdPartyHtml(param));

			return ResponseLogNotification.successJson(request,result);
		} catch (BIZException e) {
			return ResponseLogNotification.failJson(request,e);
		}  catch (Exception e) {
			return ResponseLogNotification.fail(request, e);
		}
	}
}
