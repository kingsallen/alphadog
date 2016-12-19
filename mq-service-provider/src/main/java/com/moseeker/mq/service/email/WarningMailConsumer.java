package com.moseeker.mq.service.email;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.email.config.EmailSessionConfig;
import com.moseeker.common.email.mail.Mail;
import com.moseeker.common.email.mail.Mail.MailBuilder;
import com.moseeker.common.email.mail.Message;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.StringUtils;

/**
 * 
 * 报警邮件处理工具
 * <p>Company: MoSeeker</P>  
 * <p>date: Sep 27, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
public class WarningMailConsumer {
	
	private static Logger logger = LoggerFactory.getLogger(WarningMailConsumer.class);
	
	private HashMap<String, Long> sendLogs = new HashMap<>();			//报警邮件按照类型和位置分类记录发送时间
	private ExecutorService executorService;							//线程池
	private ReentrantReadWriteLock lock;								//用来维护告警邮件发送记录
	private int interval = 5;											//时间间隔（m）

	/**
	 * 从redis业务邮件队列中获取邮件信息
	 * 
	 * @return
	 */
	private String fetchConstantlyMessage() {
		RedisClient redisClient = RedisClientFactory.getCacheClient();
		return redisClient.brpop(Constant.APPID_ALPHADOG, Constant.MQ_MESSAGE_EMAIL_WARNING).get(1);
	}

	/**
	 * 初始化业务邮件处理工具
	 * 
	 * @throws IOException
	 * @throws MessagingException
	 */
	private void initConstantlyMail() throws IOException, MessagingException {
		executorService = new ThreadPoolExecutor(3, 10,
                60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
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
				long lastSendTime = readLong(message.getEventType(), message.getLocation());
				long now = System.currentTimeMillis();
				if(now - lastSendTime > interval * 60 * 1000) {
					MailBuilder mailBuilder = new MailBuilder();
					EmailSessionConfig sessionConfig = new EmailSessionConfig(true, "smtp");
					Mail mail = mailBuilder.buildSessionConfig(sessionConfig).build(message.getEmailContent());
					logger.info("redisMsg:"+redisMsg);
					mail.send();
					logSendOperation(message.getEventType(), message.getLocation());
				}
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
		new Thread(() -> {
			try {
				sendMail();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}).start();
	}
	
	/**
	 * 记录告警邮件发送时间
	 * @param evetType 告警邮件类型
	 * @param location 告警邮件发送地点
	 */
	private void logSendOperation(int evetType, int location) {
		lock.writeLock().lock();
		String key = evetType+"_"+location;
		if(sendLogs != null) {
			boolean exist = false;
			for(Entry<String, Long> log : sendLogs.entrySet()) {
				if(log.getKey().equals(key)) {
					log.setValue(System.currentTimeMillis());
					exist = true;
					break;
				}
			}
			if(!exist) {
				sendLogs.put(key, System.currentTimeMillis());
			}
		} else {
			sendLogs = new HashMap<>();
			sendLogs.put(key, System.currentTimeMillis());
		}
		lock.writeLock().unlock();
	}
	
	/**
	 * 根据告警邮件类型和地址查询告警邮件发送时间
	 * @param evetType 告警邮件类型
	 * @param location 告警邮件发送地点
	 * @return 返回查询的发送时间（毫秒）
	 */
	private long readLong(int evetType, int location) {
		lock.readLock().lock();
		long logTime = 0;
		if(sendLogs != null) {
			String key = evetType+"_"+location;
			if(sendLogs.containsKey(key)) {
				logTime = sendLogs.get(key);
			}
		}
		lock.readLock().unlock();
		return logTime;
	}
}