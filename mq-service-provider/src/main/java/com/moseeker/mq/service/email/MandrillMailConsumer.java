package com.moseeker.mq.service.email;

import com.alibaba.fastjson.JSON;
import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.MergeVar;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.MergeVarBucket;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.Recipient;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.moseeker.baseorm.dao.logdb.LogEmailSendrecordDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.RedisException;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.logdb.LogEmailSendrecordDO;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailStruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.security.x509.IPAddressName;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
@Component
public class MandrillMailConsumer {

	private static Logger logger = LoggerFactory.getLogger(MandrillMailConsumer.class);

	private static ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
	private static final String mandrillApikey = propertiesReader.get("mandrill.apikey", String.class);

	private HashMap<Integer, String> templates = new HashMap<>(); // 模版信息
	private ExecutorService executorService;
	private MandrillApi mandrillApi;

	@Autowired
	private LogEmailSendrecordDao logEmailSendrecordDao;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

	/**
	 * 从redis业务邮件队列中获取邮件信息
	 *
	 * @return
	 */
	private String fetchConstantlyMessage() {
		try {
			List<String> el = redisClient.brpop(Constant.APPID_ALPHADOG, Constant.MQ_MESSAGE_EMAIL_MANDRILL);
			logger.info("fetchConstantlyMessage el:{}", el);
			if (el != null && el.size() > 1){
                return el.get(1);
            }
		} catch (RedisException e) {
			logger.error(e.getMessage(), e);
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
	 * @param redisMsg
	 * @return
	 * @throws Exception
	 */
	private void sendMail(String redisMsg) throws Exception {
		if (StringUtils.isNotNullOrEmpty(redisMsg)) {
			executorService.submit(() -> {
				try {

                    MandrillEmailStruct mandrillEmailStruct = JSON.parseObject(redisMsg, MandrillEmailStruct.class);

					MandrillMessage message = new MandrillMessage();

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
					logger.info(" MandrillMailConsumer sendMail message body is: {} ",JSON.toJSONString(message));

					MandrillMessageStatus[] messageStatus = mandrillApi.messages().sendTemplate(mandrillEmailStruct.getTemplateName(),
							null,message, false);
					LogEmailSendrecordDO emailrecord = new LogEmailSendrecordDO();
					logger.info(" MandrillMailConsumer sendMail messageStatus is: {} ",JSON.toJSONString(messageStatus));

					if (messageStatus.length == 0){
						logger.error("mandrill send failed: " + mandrillEmailStruct.getTo_email());
						emailrecord.setEmail(recipient.getEmail());
						emailrecord.setContent("failed," + mandrillEmailStruct.getTemplateName() + "," + message.getSubject());
						logEmailSendrecordDao.addData(emailrecord);
					}else{
						emailrecord.setEmail(recipient.getEmail());
						emailrecord.setContent(messageStatus[0].getStatus()+","+ mandrillEmailStruct.getTemplateName() + "," + message.getSubject());
						logEmailSendrecordDao.addData(emailrecord);

						logger.debug(messageStatus[0].getEmail() +" "+ messageStatus[0].getStatus());
					}

				} catch (Exception e) {
					e.printStackTrace();
					try {
						InetAddress ia = InetAddress.getLocalHost();
						String host = ia.getHostName();//获取计算机主机名
						String ip= ia.getHostAddress();//获取计算机IP
						logger.error("MandrillMailConsumer sendmail failed server hostname {} ip {} error {}", host,ip , e.getMessage());
					}catch (Exception e1){
						logger.error("MandrillMailConsumer sendmail failed ",  e1.getMessage());
					}

				}
			});
		}
	}

	/**
	 * 开启 业务邮件处理工具 处理业务邮件
	 *
	 * @throws IOException
	 * @throws MessagingException
	 */
	@PostConstruct
	public void start() throws IOException, MessagingException {
		initConstantlyMail();
		executorService = new ThreadPoolExecutor(3, 10, 60L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());
		new Thread(() -> {
			try {
				while (true) {
					String redisMsg = fetchConstantlyMessage();
					if(StringUtils.isNotNullOrEmpty(redisMsg)) {
						logger.info("MandrillMailConsumer redisMsg is {}",redisMsg);
					}
					sendMail(redisMsg);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
		}).start();
	}
}
