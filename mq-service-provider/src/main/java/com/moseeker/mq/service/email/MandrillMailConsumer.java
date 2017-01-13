package com.moseeker.mq.service.email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.MergeVar;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.MergeVarBucket;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.Recipient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.email.config.EmailContent;
import com.moseeker.common.email.config.EmailSessionConfig;
import com.moseeker.common.email.mail.Mail;
import com.moseeker.common.email.mail.Mail.MailBuilder;
import com.moseeker.common.email.mail.Message;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.mq.server.MqServer;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailStruct;

/**
 * 
 * 业务邮件处理工具
 * <p>
 * Company: MoSeeker
 * </P>
 * <p>
 * date: Nov 26, 2016
 * </p>
 * <p>
 * Email: wangyaofeng@moseeker.com
 * </p>
 * 
 * @author wangyaofeng
 * @version
 */
public class MandrillMailConsumer {

	private static Logger logger = LoggerFactory.getLogger(MandrillMailConsumer.class);

	private static ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
	private static final String mandrillApikey = propertiesReader.get("mandrill.apikey", String.class);
    //private static final String mandrillFrom_email = propertiesReader.get("mandrill.from_email", String.class);
    //private static final String mandrillFrom_name = propertiesReader.get("mandrill.from_name", String.class);
    
	private HashMap<Integer, String> templates = new HashMap<>(); // 模版信息
	private ExecutorService executorService;
	private MandrillApi mandrillApi;

	/**
	 * 从redis业务邮件队列中获取邮件信息
	 * 
	 * @return
	 */
	private String fetchConstantlyMessage() {
		RedisClient redisClient = RedisClientFactory.getCacheClient();
		List<String> el = redisClient.brpop(Constant.APPID_ALPHADOG, Constant.MQ_MESSAGE_EMAIL_MANDRILL);
		if (el != null){
			return el.get(1);
		}
		return null;
	}

	/**
	 * 初始化业务邮件处理工具
	 * 
	 * @throws IOException
	 * @throws MessagingException
	 */
	private void initConstantlyMail() throws IOException, MessagingException {
		mandrillApi = new MandrillApi(mandrillApikey);
	}

	/**
	 * 发送邮件
	 * 
	 * @param mail
	 * @return
	 * @throws Exception
	 */
	private void sendMail() throws Exception {
		String redisMsg = fetchConstantlyMessage();
		if (StringUtils.isNotNullOrEmpty(redisMsg)) {
			executorService.submit(() -> {
				try {
					MandrillMessage message = new MandrillMessage();

					MandrillEmailStruct mandrillEmailStruct = JSON.parseObject(redisMsg, MandrillEmailStruct.class);

					List<Recipient> recipients = new ArrayList<Recipient>();
					Recipient recipient = new Recipient();
					recipient.setEmail(mandrillEmailStruct.getTo_email());
					if (StringUtils.isNotNullOrEmpty(mandrillEmailStruct.getTo_name())){
						recipient.setName(mandrillEmailStruct.getTo_name());
					}
					recipients.add(recipient);						
					message.setTo(recipients);

					List<MergeVarBucket> mergeVars = new ArrayList<MergeVarBucket>();
					MergeVarBucket mergeVar = new MergeVarBucket();
					MergeVar[] vars = new MergeVar[mandrillEmailStruct.getMergeVarsSize()];
					int vars_i = 0;
					for (Entry<String, String> entry : mandrillEmailStruct.getMergeVars().entrySet()) {
						vars[vars_i] = new MergeVar();
						vars[vars_i].setName(entry.getKey());
						vars[vars_i].setContent(entry.getValue());
						vars_i++;
					}					

					if (vars_i>0){
						mergeVar.setVars(vars);
						mergeVar.setRcpt(mandrillEmailStruct.getTo_email());
						mergeVars.add(mergeVar);	
						message.setMergeVars(mergeVars);
					}	
					
					if (StringUtils.isNotNullOrEmpty(mandrillEmailStruct.getSubject())){
						message.setSubject(mandrillEmailStruct.getSubject());
					}
					
					if (StringUtils.isNotNullOrEmpty(mandrillEmailStruct.getFrom_email())){
						message.setFromEmail(mandrillEmailStruct.getFrom_email());
					}					

					if (StringUtils.isNotNullOrEmpty(mandrillEmailStruct.getFrom_name())){
						message.setFromName(mandrillEmailStruct.getFrom_name());
					}	
					
					message.setTo(recipients);
					message.setMerge(true);
					message.setInlineCss(true);
					message.setMergeLanguage("handlebars");
					message.setImportant(false);
					message.setTrackingDomain("moseeker.com");
					message.setTrackClicks(true);
					message.setTrackOpens(true);
					message.setViewContentLink(true);
					
					MandrillMessageStatus[] messageStatus = mandrillApi.messages().sendTemplate(mandrillEmailStruct.getTemplateName(), 
							null,message, false);
					if (messageStatus.length == 0){
						logger.error("mandrill send failed: " + mandrillEmailStruct.getTo_email());
					}else{
						logger.debug(messageStatus[0].getEmail() +" "+ messageStatus[0].getStatus());
					}
									
					
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}
			});
		}

		sendMail();
	}

	/**
	 * 开启 业务邮件处理工具 处理业务邮件
	 * 
	 * @throws IOException
	 * @throws MessagingException
	 */
	public void start() throws IOException, MessagingException {
		initConstantlyMail();
		executorService = new ThreadPoolExecutor(3, 10, 60L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		new Thread(() -> {
			try {
				sendMail();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}).start();
	}
}
