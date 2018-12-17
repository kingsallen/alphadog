package com.moseeker.entity;

import com.alibaba.fastjson.JSON;
import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.MergeVar;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.MergeVarBucket;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.Recipient;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.moseeker.baseorm.dao.logdb.LogEmailProfileSendLogDao;
import com.moseeker.baseorm.dao.logdb.LogEmailSendrecordDao;
import com.moseeker.baseorm.db.logdb.tables.records.LogEmailProfileSendLogRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.mq.struct.MandrillEmailListStruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;

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

    @Autowired
    private LogEmailProfileSendLogDao logEmailProfileSendLogDao;


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

                List<Map<String,String>> toInfoList = mandrillEmailListStruct.getTo();
                for(Map<String,String> toinfo : toInfoList){
                    Recipient recipient = new Recipient();
                    for (Entry<String, String> entry : toinfo.entrySet()){
                        if("to_email".equals(entry.getKey())){
                            recipient.setEmail(entry.getValue());
                        }
                        if("to_name".equals(entry.getKey())){
                            if (StringUtils.isNotNullOrEmpty(entry.getValue())){
                                recipient.setName(entry.getValue());
                            }
                        }
                    }
                    recipients.add(recipient);
                }

                message.setTo(recipients);

                List<MergeVarBucket> mergeVars = new ArrayList<MergeVarBucket>();

                List<Map<String,Object>> varList = (List<Map<String,Object>>)JSON.parse(mandrillEmailListStruct.getMergeVars());
                List<Integer> userIdList=new ArrayList<>();
                for (Map<String, Object> var : varList) {
                    String rcpt = "";
                    MergeVarBucket mergeVar = new MergeVarBucket();
                    MergeVar[] vars = new MergeVar[var.size()];
                    int vars_i = 0;
                    for (Entry<String, Object> entry : var.entrySet()) {
                        vars[vars_i] = new MergeVar();
                        vars[vars_i].setName(entry.getKey());
                        vars[vars_i].setContent(entry.getValue());
                        vars_i++;
                        if("rcpt".equals(entry.getKey())){
                            rcpt = (String)entry.getValue();
                        }
                        if("user_id".equals(entry.getKey())){
                            int userId=(int)entry.getValue();
                            if(!userIdList.contains(userId)){
                                userIdList.add(userId);
                            }
                        }
                    }

                    if (vars.length>0) {
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
                logger.info("sendMailList mergeVar:=========================");
                logger.info("sendMailList mergeVar:{}", JSON.toJSONString(message.getMergeVars()));
                MandrillMessageStatus[] messageStatus = mandrillApi.messages().sendTemplate(mandrillEmailListStruct.getTemplateName(),
                        null,message, false);
                logger.info("messageStatus :{}",messageStatus);
                /*
                 * @Author zztaiwll
                 * @Description  添加关于转发的日志
                 * @Date 下午3:08 18/12/4
                 * @Param [mandrillEmailListStruct]
                 * @return void
                 **/
                if(mandrillEmailListStruct.getType()>0){
                    List<LogEmailProfileSendLogRecord> recordList=new ArrayList<>();
                    for(Recipient recipient:recipients){
                        for(Integer userId:userIdList){
                            LogEmailProfileSendLogRecord record=new LogEmailProfileSendLogRecord();
                            record.setEmail(recipient.getEmail());
                            record.setUserId(userId);
                            record.setType(mandrillEmailListStruct.getType());
                            record.setCompanyId(mandrillEmailListStruct.getCompany_id());
                            recordList.add(record);
                            logger.info("LogEmailProfileSendLogRecord record :{}",record.toString());
                        }
                    }
                    logger.info("List<LogEmailProfileSendLogRecord> recordList :{}",recordList.toString());
                    logEmailProfileSendLogDao.addAllRecord(recordList);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

	}

    /*
     *
     * 发邮件时把\n换成<br>
     *
     * */
    public String replaceHTMLEnterToBr(String oldString){
        String newString = oldString.replaceAll("\\n","<br>");
        return newString;
    }


}
