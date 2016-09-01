package com.moseeker.servicemanager.web.controller.profile.form;

import java.net.ConnectException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.servicemanager.common.UrlUtil;
import com.moseeker.thrift.gen.common.struct.Response;

public class OutPutResumeUtil {
	
	Logger logger = LoggerFactory.getLogger(OutPutResumeUtil.class);
	
	private static String GENERATE_USER_ID = "http://download1.dqprism.com/generatebyuserid";
	private static String GENERATE_APP_ID = "http://download1.dqprism.com/generatebyappid";

	public Response outPutResume(OutPutResumeForm form) {
		String url = null;
		String result = null;
		JSONObject params = new JSONObject();
		if(form.getUser_id() > 0) {
			url = GENERATE_USER_ID;
			params.put("user_id", form.getUser_id());
			params.put("password", form.getPassword());
		} else if(form.getApp_id() > 0) {
			url = GENERATE_APP_ID;
			params.put("app_id", form.getApp_id());
			params.put("password", form.getPassword());
		}
		try {
			result = UrlUtil.sendPost(url, params.toJSONString());
		} catch (ConnectException e) {
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
		}
		if(result != null) {
			DownloadResult downloadResult = JSON.parseObject(result, DownloadResult.class);
			if(downloadResult != null && downloadResult.getStatus() == 0) {
				return ResponseUtils.success(downloadResult.getDownloadlink());
			}
		}
		return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_OUTPUT_FAILED);
	}

}
