package com.moseeker.mq.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.hrdb.HrWxNoticeMessageDao;
import com.moseeker.baseorm.dao.logdb.LogWxMessageRecordDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMessageRecordDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import java.net.ConnectException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 简历投递时发送模板消息
 * Created by moseeker on 2017/12/18.
 */

@Service
public class TemlateMsgHttp {
    @Autowired
    private HrWxNoticeMessageDao wxNoticeMessageDao;
    @Autowired
    private LogWxMessageRecordDao wxMessageRecordDao;

    private static Logger logger = LoggerFactory.getLogger(EmailProducer.class);

    /**
     * 向申请者发送微信模板消息
     * @param position 申请职位
     * @param companyDO   职位所属公司
     * @param hrWxWechatDO  hr微信公众号
     * @param openId    申请者的openId
     * @param url       模板发送请求链接
     * @param link      模板点击链接
     * @param template  模板对象
     * @return
     */
    public Response handleApplierTemplate(JobPositionDO position, HrCompanyDO companyDO,
                                         HrWxWechatDO hrWxWechatDO, String openId, String url, String link,
                                         HrWxTemplateMessageDO template)  {

        if( position == null || companyDO == null || openId == null || openId.isEmpty() || template== null
                 || url== null || url.isEmpty()){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        try {
            Map<String, Object> applierTemplate = new HashMap<>();
            Map<String,MessageTplDataCol> colMap =new HashMap<>();
            MessageTplDataCol first = new MessageTplDataCol();
            first.setColor("#173177");
            first.setValue("感谢您抽出时间申请该职位，我们将尽快查阅您的简历");
            colMap.put("first",first);
            MessageTplDataCol remark = new MessageTplDataCol();
            remark.setColor("#173177");
            remark.setValue("");
            colMap.put("remark",remark);
            MessageTplDataCol job = new MessageTplDataCol();
            job.setColor("#173177");
            job.setValue(position.getTitle());
            colMap.put("job",job);
            MessageTplDataCol company=new MessageTplDataCol();
            company.setColor("#173177");
            company.setValue(companyDO.getName());
            colMap.put("company",company);
            String dateTime = DateUtils.dateToPattern(new Date(), "yyyy年MM月dd日 HH：mm");
            MessageTplDataCol time=new MessageTplDataCol();
            time.setColor("#173177");
            time.setValue(dateTime);
            colMap.put("time",time);;
            applierTemplate.put("data", colMap);
            applierTemplate.put("touser", openId);
            applierTemplate.put("template_id", template.getWxTemplateId());
            applierTemplate.put("url", link);
            applierTemplate.put("topcolor", template.getTopcolor());
            String result = null;
            result = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
            Map<String, Object> params = JSON.parseObject(result);
            insertLogWxMessageRecord(hrWxWechatDO, template, openId, link, colMap ,params);
            if(params!= null && "0".equals(params.get("errcode"))){
                return ResponseUtils.success("success");
            }
            return ResponseUtils.fail((int)params.get("errcode"), (String)params.get("errmsg"));
        }catch (ConnectException e) {
            return ResponseUtils.fail(99999, e.getMessage());
        }
    }

    /**
     * 给推荐者发送模板消息
     * @param position      职位信息
     * @param hrWxWechatDO  hr公众号
     * @param template      模板对象
     * @param user          申请者信息
     * @param workExp       申请者工作年限
     * @param lastWork      最后一份工作公司
     * @param openId        推荐者openId
     * @param url           模板发送请求链接
     * @param link          模板消息点击链接
     * @return
     */
    public Response handleRecomTemplate(JobPositionDO position, HrWxWechatDO hrWxWechatDO, HrWxTemplateMessageDO template,
                                                   UserUserDO user, String workExp, String lastWork,  String openId, String url,
                                                   String link)  {
        if( position == null || openId== null || openId.isEmpty() || template== null
                || user == null || url == null || url.isEmpty()){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        try {
            Map<String, Object> applierTemplate = new HashMap<>();
            Map<String,MessageTplDataCol> colMap =new HashMap<>();
            MessageTplDataCol first = new MessageTplDataCol();
            first.setColor("#173177");
            first.setValue(String.format("你好！你推荐的%s投递了简历", user.getName()));
            colMap.put("first",first);
            MessageTplDataCol remark = new MessageTplDataCol();
            remark.setColor("#173177");
            remark.setValue("");
            colMap.put("remark",remark);
            MessageTplDataCol job = new MessageTplDataCol();
            job.setColor("#173177");
            job.setValue(position.getTitle());
            colMap.put("job",job);
            MessageTplDataCol resuname=new MessageTplDataCol();
            resuname.setColor("#173177");
            resuname.setValue(user.getName());
            colMap.put("resuname",resuname);
            MessageTplDataCol realname=new MessageTplDataCol();
            realname.setColor("#173177");
            realname.setValue(user.getName());
            colMap.put("realname",realname);
            MessageTplDataCol exp=new MessageTplDataCol();
            exp.setColor("#173177");
            exp.setValue(workExp);
            colMap.put("exp",exp);
            MessageTplDataCol lastjob=new MessageTplDataCol();
            lastjob.setColor("#173177");
            lastjob.setValue(lastWork);
            colMap.put("lastjob",lastjob);
            applierTemplate.put("data", colMap);
            applierTemplate.put("touser", openId);
            applierTemplate.put("template_id", template.getWxTemplateId());
            applierTemplate.put("url", link);
            applierTemplate.put("topcolor", template.getTopcolor());
            String result = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
            Map<String, Object> params = JSON.parseObject(result);
            insertLogWxMessageRecord(hrWxWechatDO, template, openId, link, colMap ,params);
            if(params!= null && "0".equals(params.get("errcode"))){
                return ResponseUtils.success("success");
            }
            return ResponseUtils.fail((int)params.get("errcode"), (String)params.get("errmsg"));
        }catch (ConnectException e) {
            return ResponseUtils.fail(99999, e.getMessage());
        }
    }

    /**
     * 简历投递向HR发送模板消息
     * @param hrAccount     hr账户信息
     * @param position      投递职位信息
     * @param hrWxWechatDO  hr微信公众号
     * @param template      模板对象
     * @param user          申请者信息
     * @param workExp       申请者工作年限
     * @param lastWork      申请者上一份工作公司名
     * @param openId        HR微信openId
     * @param url           模板发送请求链接
     * @param link          模板消息点击链接
     * @return
     */
    public Response handleHrTemplate(UserHrAccountDO hrAccount, JobPositionDO position, HrWxWechatDO hrWxWechatDO, HrWxTemplateMessageDO template,
                                                UserUserDO user, String workExp, String lastWork, String openId, String url,
                                                String link)  {
        if( hrAccount == null || position == null || !StringUtils.isNotNullOrEmpty(openId) || template== null
                || user == null || !StringUtils.isNotNullOrEmpty(url)){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        try {
            Map<String, Object> applierTemplate = new HashMap<>();
            Map<String,MessageTplDataCol> colMap =new HashMap<>();
            MessageTplDataCol first = new MessageTplDataCol();
            first.setColor("#173177");
            first.setValue( String.format("%s,你好！\n您刚发布的%s职位收到了一份新简历，请及时登录hr.moseeker.com查阅并处理",hrAccount.getUsername(), position.getTitle()));
            colMap.put("first",first);
            MessageTplDataCol remark = new MessageTplDataCol();
            remark.setColor("#173177");
            remark.setValue("");
            colMap.put("remark",remark);
            MessageTplDataCol job = new MessageTplDataCol();
            job.setColor("#173177");
            job.setValue(position.getTitle());
            colMap.put("job",job);
            MessageTplDataCol resuname=new MessageTplDataCol();
            resuname.setColor("#173177");
            resuname.setValue("仟寻简历");
            colMap.put("resuname",resuname);
            MessageTplDataCol realname=new MessageTplDataCol();
            realname.setColor("#173177");
            realname.setValue(user.getName());
            colMap.put("realname",realname);
            MessageTplDataCol exp=new MessageTplDataCol();
            exp.setColor("#173177");
            exp.setValue(workExp);
            colMap.put("exp",exp);
            MessageTplDataCol lastjob=new MessageTplDataCol();
            lastjob.setColor("#173177");
            lastjob.setValue(lastWork);
            colMap.put("lastjob",lastjob);
            applierTemplate.put("data", colMap);
            applierTemplate.put("touser", openId);
            applierTemplate.put("template_id", template.getWxTemplateId());
            applierTemplate.put("url", link);
            applierTemplate.put("topcolor", template.getTopcolor());
            String result = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
            Map<String, Object> params = JSON.parseObject(result);
            insertLogWxMessageRecord(hrWxWechatDO, template, openId, link, colMap ,params);
            if(params!= null && "0".equals(params.get("errcode"))){
                return ResponseUtils.success("success");
            }
            return ResponseUtils.fail((int)params.get("errcode"), (String)params.get("errmsg"));
        }catch (ConnectException e) {
            return ResponseUtils.fail(99999, e.getMessage());
        }
    }

    /**
     * 插入模板消息发送结果日志
     * @param hrWxWechatDO
     * @param template
     * @param openId
     * @param link
     * @param colMap
     * @param result
     * @return
     */
    private int insertLogWxMessageRecord(HrWxWechatDO hrWxWechatDO, HrWxTemplateMessageDO template,
                                          String openId, String link, Map<String, MessageTplDataCol> colMap, Map<String, Object> result){
        LogWxMessageRecordDO messageRecord = new LogWxMessageRecordDO();
        messageRecord.setTemplateId(template.getId());
        if(hrWxWechatDO != null) {
            messageRecord.setWechatId(hrWxWechatDO.getId());
        }
        if(result != null && result.get("msgid") != null) {
            messageRecord.setMsgid((long)result.get("msgid"));
        }else{
            messageRecord.setMsgid(0);
        }
        messageRecord.setOpenId(openId);
        messageRecord.setUrl(link);
        messageRecord.setTopcolor(template.getTopcolor());
        messageRecord.setJsondata(JSON.toJSONString(colMap));
        if(result != null && result.get("errcode") != null) {
            messageRecord.setErrcode((int) result.get("errcode"));
        }else{
            messageRecord.setErrcode(-3);
        }
        if(result != null && result.get("errmsg") != null) {
            messageRecord.setErrmsg((String)result.get("errmsg"));
        }else{
            messageRecord.setErrmsg("");
        }
        messageRecord.setSendtime(DateUtils.dateToShortTime(new Date()));
        messageRecord.setUpdatetime(DateUtils.dateToShortTime(new Date()));
        messageRecord.setSendtype(0);
        if(hrWxWechatDO != null) {
            messageRecord.setAccessToken(hrWxWechatDO.getAccessToken());
        }
        return wxMessageRecordDao.addData(messageRecord).getId();
    }

}