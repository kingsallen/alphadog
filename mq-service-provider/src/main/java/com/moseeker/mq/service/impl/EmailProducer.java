package com.moseeker.mq.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.mail.Message;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.mq.dao.UserDao;
import com.moseeker.mq.service.WarnService;
import com.moseeker.thrift.gen.common.struct.Response;

@Service
@CounterIface
public class EmailProducer {
	
	private static Logger logger = LoggerFactory.getLogger(EmailProducer.class);
	
	@Autowired
	private UserDao userDao;
	/**
	 * 往业务邮件队列发送一条信息
	 * @param params 消息体
	 * @param email 收件人
	 * @param eventType
	 * @return
	 */
	public Response sendBizEmail(Map<String, String> params, int eventType, String email, String subject, String senderName, String senderDisplay) {
		try {
			Message message = new Message();
			message.setAppId(Constant.APPID_ALPHADOG);
			message.setEventType(eventType);
			message.getParams().putAll(params);
			EmailContent mailContent = new EmailContent();
			mailContent.setCharset(StandardCharsets.UTF_8.toString());
			List<String> recipients = new ArrayList<>();
            recipients.add(email);
            mailContent.setRecipients(recipients);
            mailContent.setSenderName(senderName);
            mailContent.setSenderDisplay(senderDisplay);
            mailContent.setSubject(subject);

			message.setEmailContent(mailContent);
			
			String constantlyMsg = JSON.toJSONString(message);
			
			RedisClient redisClient = RedisClientFactory.getCacheClient();
			redisClient.lpush(Constant.APPID_ALPHADOG, Constant.MQ_MESSAGE_EMAIL_BIZ, constantlyMsg);
			return ResponseUtils.success("success");
		} catch (RedisException e) {
			WarnService.notify(e);
			return ResponseUtils.fail(99999, e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(99999, e.getMessage());
		} finally {
			//do nothing
		}
	}
	
	/**
	 * 获取用户的称呼
	 * @param userId 用户id
	 * @return
	 */
	public String genUsername(int userId) {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(userId));
		UserUserRecord user;
		String username = null;
		try {
			user = userDao.getResource(qu);
			if(user != null) {
				if(StringUtils.isNotNullOrEmpty(user.getName())) {
					username = user.getName();
				} else if(StringUtils.isNotNullOrEmpty(user.getNickname())) {
					username = user.getNickname();
				} else {
					username = user.getUsername();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return username;
	}
}
