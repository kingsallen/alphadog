package com.moseeker.mq.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.mail.Message;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.mq.dao.UserDao;
import com.moseeker.thrift.gen.common.struct.Response;

@Service
public class EmailProvider {
	
	private static Logger logger = LoggerFactory.getLogger(EmailProvider.class);
	
	@Autowired
	private UserDao userDao;
	/**
	 * 往业务邮件队列发送一条信息
	 * @param userId
	 * @param email
	 * @param url
	 * @param eventType
	 * @return
	 */
	public Response sendBizEmail(int userId, String email, String url, String subject) {
		try {
			//参数校验
			ValidateUtil vu = new ValidateUtil();
			vu.addRequiredStringValidate("邮箱", email, null, null);
			vu.addRequiredStringValidate("认证地址", url, null, null);
			vu.addIntTypeValidate("用户编号", userId, null, null, 1, null);
			String vuResult = vu.validate();
			
			if(StringUtils.isNullOrEmpty(vuResult)) {
				QueryUtil qu = new QueryUtil();
				qu.addEqualFilter("id", String.valueOf(userId));
				UserUserRecord user = userDao.getResource(qu);
				if(user != null) {
					String username = genUsername(user);
					
					Message message = new Message();
					message.setAppId(Constant.APPID_ALPHADOG);
					message.setEventType(Constant.EVENT_TYPE_EMAIL_VERIFIED);
					message.getParams().put("# username #", username);
					message.getParams().put("# send_date #", new DateTime().toString("yyyy-MM-dd"));
					message.getParams().put("# verified_url #", url);
					
					EmailContent mailContent = new EmailContent();
					mailContent.setCharset(StandardCharsets.UTF_8.toString());
					if(StringUtils.isNullOrEmpty(subject)) {
						mailContent.setSubject(subject);
					} else {
						mailContent.setSubject(Constant.EMAIL_VERIFIED_SUBJECT);
					}
					List<String> recipients = new ArrayList<>();
					recipients.add(email);
					mailContent.setRecipients(recipients);
					mailContent.setSender("仟寻MoSeeker<info@moseeker.net>");
					mailContent.setSubject("请验证邮箱--仟寻招聘");
					
					message.setEmailContent(mailContent);
					
					String constantlyMsg = JSON.toJSONString(message);
					
					RedisClient redisClient = RedisClientFactory.getCacheClient();
					redisClient.lpush(Constant.APPID_ALPHADOG, Constant.MQ_MESSAGE_EMAIL_BIZ, constantlyMsg);
					return ResponseUtils.success("success");
				} else {
					return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
				}
			} else {
				return ResponseUtils.fail(ConstantErrorCodeMessage.VALIDATE_FAILED.replace("{MESSAGE}", vuResult));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return ResponseUtils.fail(99999, e.getMessage());
			
		} finally {
			//do nothing
		}
	}
	/**
	 * 获取用户的称呼
	 * @param user 用户信息
	 * @return
	 */
	private String genUsername(UserUserRecord user) {
		String username = null;
		if(user != null) {
			if(StringUtils.isNotNullOrEmpty(user.getName())) {
				username = user.getName();
			} else if(StringUtils.isNotNullOrEmpty(user.getNickname())) {
				username = user.getNickname();
			} else {
				username = user.getUsername();
			}
		}
		return username;
	}
}
