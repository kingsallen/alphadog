package com.moseeker.mq.service.email;

import com.moseeker.baseorm.redis.RedisClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.config.EmailSessionConfig;
import com.moseeker.common.email.mail.Mail;
import com.moseeker.common.email.mail.Mail.MailBuilder;
import com.moseeker.common.email.mail.Message;
import com.moseeker.common.exception.RedisException;
import com.moseeker.mq.MqServer;
import com.moseeker.mq.service.WarnService;
import org.springframework.stereotype.Component;

/**
 *
 * 业务邮件处理工具
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Sep 26, 2016
 * </p>
 * <p>
 * Email: wjf2255@gmail.com
 * </p>
 *
 * @author wjf
 * @version
 */
@Component
public class ConstantlyMailConsumer {

	private static Logger logger = LoggerFactory.getLogger(MqServer.class);

	private HashMap<Integer, String> templates = new HashMap<>(); // 模版信息
	private ExecutorService executorService;

	@Resource(name = "cacheClient")
	private RedisClient redisClient;

	/**
	 * 从redis业务邮件队列中获取邮件信息
	 *
	 * @return
	 */
	private String fetchConstantlyMessage() {
		try {
			List<String> el =  redisClient.brpop(Constant.APPID_ALPHADOG,
					Constant.MQ_MESSAGE_EMAIL_BIZ);
			if (el != null && el.size() >= 1){
				return el.get(1);
			}
			return null;
		} catch (RedisException e) {
			WarnService.notify(e);
			return null;
		}
	}

	/**
	 * 初始化业务邮件处理工具
	 *
	 * @throws IOException
	 * @throws MessagingException
	 */
	private void initConstantlyMail() throws IOException, MessagingException {
		// 加载模版文件
		templates.putAll(Stream.of(Constant.EVENT_TYPE_EMAIL_VERIFIED, Constant.EVENT_TYPE_EMPLOYEE_AUTH,Constant.EVENT_TYPE_RECOMMEND_VALID_EMAIL,Constant.EVENT_TYPE_RECOMMEND_POSITION_EMAIL).collect(Collectors.toMap(k -> k, v -> {
			String templateName = "";
			if (v == 1) {
				templateName = "email_verifier_template.html";
			} else if (v == 2) {
				templateName = "employee_auth_template.html";
			}else if(v==3){
				templateName = "position_subscribe_activation_template.html";
			}else if(v==4 ){
				templateName = "position_subscribe_weekly_template.html";
			}
			StringBuffer sb = new StringBuffer();
			try (InputStreamReader is = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(templateName), "UTF-8")) {
				BufferedReader br = new BufferedReader(is);
				for (String s = ""; (s = br.readLine()) != null; sb.append(s));
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}
			return sb.toString();
		})));
	}

	/**
	 * 发送邮件
	 * @return
	 * @throws Exception
	 */
	private String sendMail() throws Exception {
		String redisMsg = fetchConstantlyMessage();

		if(redisMsg != null) {
			executorService.submit(() -> {
				try {
					Message message = JSON.parseObject(redisMsg, Message.class);
					String html = null;
					for (Entry<Integer, String> entry : templates.entrySet()) {
						if (message.getEventType() == entry.getKey().intValue()) {
							html = entry.getValue();
							EmailContent content = message.getEmailContent();
							if (message.getParams() != null) {
								for (Entry<String, String> param : message.getParams().entrySet()) {
									html = html.replaceAll(param.getKey(), param.getValue());
								}
							}
							content.setContent(html);
						}
					}
					logger.info("redisMsg:"+redisMsg);
					MailBuilder mailBuilder = new MailBuilder();
					EmailSessionConfig sessionConfig = new EmailSessionConfig(true, "smtp");
					Mail mail = mailBuilder.buildSessionConfig(sessionConfig).build(message.getEmailContent());
					logger.info("redisMsg:"+redisMsg);
					mail.send();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("redisMsg: "+ redisMsg +e.getMessage(),e);
				}
			});
		}
		return redisMsg;
	}

	/**
	 * 开启 业务邮件处理工具 处理业务邮件
	 * @throws IOException
	 * @throws MessagingException
	 */
	@PostConstruct
	public void start() throws IOException, MessagingException {
		initConstantlyMail();
		executorService = new ThreadPoolExecutor(3, 10,
				60L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());

		new Thread(() -> {
			try {
				while(true) {
					sendMail();
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}).start();
	}
}
