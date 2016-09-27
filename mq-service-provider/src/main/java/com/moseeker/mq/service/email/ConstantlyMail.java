package com.moseeker.mq.service.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.config.EmailSessionConfig;
import com.moseeker.common.email.mail.Mail;
import com.moseeker.common.email.mail.Mail.MailBuilder;
import com.moseeker.common.email.mail.MailCallback;
import com.moseeker.common.email.mail.Message;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.mq.server.MqServer;

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
public class ConstantlyMail implements MailCallback {
	
	private static Logger logger = LoggerFactory.getLogger(MqServer.class);

	private HashMap<Integer, String> templates = new HashMap<>(); // 模版信息
	private ExecutorService executorService;

	/**
	 * 从redis业务邮件队列中获取邮件信息
	 * 
	 * @return
	 */
	private String fetchConstantlyMessage() {
		RedisClient redisClient = RedisClientFactory.getCacheClient();
		return redisClient.brpop(Constant.APPID_ALPHADOG, Constant.MQ_MESSAGE_EMAIL_BIZ).get(1);
	}

	/**
	 * 初始化业务邮件处理工具
	 * 
	 * @throws IOException
	 * @throws MessagingException
	 */
	private void initConstantlyMail() throws IOException, MessagingException {
		// 加载模版文件
		//URL fileURL=this.getClass().getResource("/resource/res.txt");
		InputStreamReader is= new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("email_verifier_template.html"), "UTF-8");    
       //InputStream is=当前类.class.getResourceAsStream("XX.config");     
        BufferedReader br = new BufferedReader(is);    
        StringBuffer sb = new StringBuffer(); 
        String s = "";
        while((s=br.readLine())!=null) {
        	sb.append(s);
        }
		templates.put(Constant.EVENT_TYPE_EMAIL_VERIFIED, sb.toString());
	}

	/**
	 * 发送邮件
	 * @param mail
	 * @return
	 * @throws Exception 
	 */
	private String sendMail() throws Exception {
		String redisMsg = fetchConstantlyMessage();
		
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
				MailBuilder mailBuilder = new MailBuilder();
				EmailSessionConfig sessionConfig = new EmailSessionConfig(true, "smtp");
				Mail mail = mailBuilder.buildSessionConfig(sessionConfig).build(message.getEmailContent());
				logger.info("redisMsg:"+redisMsg);
				mail.send();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		if (StringUtils.isNotNullOrEmpty(redisMsg)) {
			sendMail();
		}
		return redisMsg;
	}

	/**
	 * 开启 业务邮件处理工具 处理业务邮件
	 * @throws IOException
	 * @throws MessagingException
	 */
	public void start() throws IOException, MessagingException {
		initConstantlyMail();
		executorService = new ThreadPoolExecutor(3, 10,
                60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
		//new Thread(() -> {
			try {
				sendMail();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		//}).start();
	}

	/**
	 * 根据消息队列中的数据，生成邮件内容
	 * @param redisMsg 消息队列中的邮件内容
	 * @return EmailContent 邮件内容
	 */
	@Override
	public EmailContent buildContent(String redisMsg) {
		Message message = JSON.parseObject(redisMsg, Message.class);
		for (Entry<Integer, String> entry : templates.entrySet()) {
			if (message.getEventType() == entry.getKey().intValue()) {
				EmailContent content = message.getEmailContent();
				if (message.getParams() != null) {
					for (Entry<String, String> param : message.getParams().entrySet()) {
						entry.setValue(entry.getValue().replaceAll(param.getKey(), param.getValue()));
					}
				}
				content.setContent(entry.getValue());
			}
		}
		return message.getEmailContent();
	}
}
