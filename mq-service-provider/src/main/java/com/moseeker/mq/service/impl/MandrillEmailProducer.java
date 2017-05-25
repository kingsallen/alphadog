package com.moseeker.mq.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailStruct;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MandrillEmailProducer {

	private static Logger logger = LoggerFactory.getLogger(MandrillEmailProducer.class);

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

	/**
	 * 往mandrill邮件队列发送一条信息
	 * 
	 * @return
	 */
	public Response queueEmail(MandrillEmailStruct mandrillEmailStruct) {
		try {
			// 参数校验
			ValidateUtil vu = new ValidateUtil();
			vu.addRequiredStringValidate("邮箱", mandrillEmailStruct.getTo_email(), null, null);
			vu.addRequiredStringValidate("邮件模板", mandrillEmailStruct.getTemplateName(), null, null);
			String vuResult = vu.validate();
			if (StringUtils.isNullOrEmpty(vuResult)) {
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
