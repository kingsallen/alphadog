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
import com.moseeker.thrift.gen.mq.struct.MandrillEmailListStruct;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailStruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
public class MandrillMailListConsumer {

	private static Logger logger = LoggerFactory.getLogger(MandrillMailListConsumer.class);

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
	 * 发送邮件
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	public void sendMailList(MandrillEmailListStruct mandrillEmailListStruct) throws Exception {
        mandrillApi = new MandrillApi(mandrillApikey);
            try {

                MandrillMessage message = new MandrillMessage();

                List<Recipient> recipients = new ArrayList<Recipient>();
                Recipient recipient = new Recipient();
                recipient.setEmail(mandrillEmailListStruct.getTo_email());
                if (StringUtils.isNotNullOrEmpty(mandrillEmailListStruct.getTo_name())){
                    recipient.setName(mandrillEmailListStruct.getTo_name());
                }
                recipients.add(recipient);
                message.setTo(recipients);

                List<MergeVarBucket> mergeVars = new ArrayList<MergeVarBucket>();

                List<Map<String,String>> varList = mandrillEmailListStruct.getMergeVars();
                for (Map<String, String> var : varList) {
                    String rcpt = "";
                    MergeVarBucket mergeVar = new MergeVarBucket();
                    MergeVar[] vars = new MergeVar[var.size()];
                    int vars_i = 0;
                    for (Entry<String, String> entry : var.entrySet()){
                        vars[vars_i] = new MergeVar();
                        vars[vars_i].setName(entry.getKey());
                        vars[vars_i].setContent(entry.getValue());
                        vars_i++;
                        if("rcpt".equals(entry.getKey())){
                            rcpt = entry.getValue();
                        }
                    }

                    if (vars_i > 0) {
                        mergeVar.setVars(vars);
                        mergeVar.setRcpt(rcpt);
                        mergeVars.add(mergeVar);
                        message.setMergeVars(mergeVars);
                    }
                }

                if (StringUtils.isNotNullOrEmpty(mandrillEmailListStruct.getSubject())){
                    message.setSubject(mandrillEmailListStruct.getSubject());
                }

                if (StringUtils.isNotNullOrEmpty(mandrillEmailListStruct.getFrom_email())){
                    message.setFromEmail(mandrillEmailListStruct.getFrom_email());
                }

                if (StringUtils.isNotNullOrEmpty(mandrillEmailListStruct.getFrom_name())){
                    message.setFromName(mandrillEmailListStruct.getFrom_name());
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

                MandrillMessageStatus[] messageStatus = mandrillApi.messages().sendTemplate(mandrillEmailListStruct.getTemplateName(),
                        null,message, false);
                LogEmailSendrecordDO emailrecord = new LogEmailSendrecordDO();

                if (messageStatus.length == 0){
                    logger.error("mandrill send failed: " + mandrillEmailListStruct.getTo_email());
                    emailrecord.setEmail(recipient.getEmail());
                    emailrecord.setContent("failed," + mandrillEmailListStruct.getTemplateName() + "," + message.getSubject());
                    logEmailSendrecordDao.addData(emailrecord);
                }else{
                    emailrecord.setEmail(recipient.getEmail());
                    emailrecord.setContent(messageStatus[0].getStatus()+","+ mandrillEmailListStruct.getTemplateName() + "," + message.getSubject());
                    logEmailSendrecordDao.addData(emailrecord);

                    logger.debug(messageStatus[0].getEmail() +" "+ messageStatus[0].getStatus());
                }

            } catch (Exception e) {
                e.printStackTrace();
                logger.error(e.getMessage());
            }

	}


}
