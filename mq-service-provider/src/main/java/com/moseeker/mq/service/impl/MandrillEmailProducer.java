package com.moseeker.mq.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.moseeker.common.annotation.iface.CounterIface;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.mail.Message;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailStruct;

@Service
public class MandrillEmailProducer {

	private static Logger logger = LoggerFactory.getLogger(MandrillEmailProducer.class);

	/**
	 * 往mandrill邮件队列发送一条信息
	 * 
	 * @param userId
	 * @param email
	 * @param url
	 * @param eventType
	 * @return
	 */
	@CounterIface
	public Response queueEmail(MandrillEmailStruct mandrillEmailStruct) {
		try {
			// 参数校验
			ValidateUtil vu = new ValidateUtil();
			vu.addRequiredStringValidate("邮箱", mandrillEmailStruct.getTo_email(), null, null);
			vu.addRequiredStringValidate("邮件模板", mandrillEmailStruct.getTemplateName(), null, null);
			String vuResult = vu.validate();
			if (StringUtils.isNullOrEmpty(vuResult)) {
				RedisClient redisClient = RedisClientFactory.getCacheClient();
				redisClient.lpush(Constant.APPID_ALPHADOG, Constant.MQ_MESSAGE_EMAIL_MANDRILL,
						JSON.toJSONString(mandrillEmailStruct));
				return ResponseUtils.success("success");
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		} finally {
			// do nothing
		}
	}
}
