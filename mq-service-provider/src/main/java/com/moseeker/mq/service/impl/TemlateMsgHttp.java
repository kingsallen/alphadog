package com.moseeker.mq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.HrWxNoticeMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.logdb.LogWxMessageRecordDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageLibraryRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
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

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

    @Autowired
    private UserEmployeeDao employeeDao;

    @Autowired
    private UserWxUserDao userWxUserDao;

    @Autowired
    private HrWxWechatDao hrWxWechatDao;

    @Autowired
    private Environment env;

    @Autowired
    private ConfigSysTemplateMessageLibraryDao templateMessageLibraryDao;

    private static String NoticeEmployeeVerifyContent = "您尚未完成员工认证，请尽快验证邮箱完成认证，若未收到邮件，请检查垃圾邮箱~\n" +
            "认证状态：{{keyword1.DATA}}\n" +
            "认证类型：{{keyword2.DATA}}\n" +
            "认证信息：{{keyword3.DATA}}\n" +
            "处理时间：{{keyword4.DATA}}";
    private static String NoticeEmployeeVerifyFirst = "您尚未完成员工认证，请尽快验证邮箱完成认证，若未收到邮件，请检查垃圾邮箱~";

    private static Logger logger = LoggerFactory.getLogger(EmailProducer.class);

    public void noticeEmployeeVerify(int userId, int companyId, String companyName) {
        UserEmployeeRecord userEmployeeRecord = employeeDao.getActiveEmployee(userId, companyId);

        logger.info("noticeEmployeeVerify userEmployeeRecord:{}", userEmployeeRecord);
        if (userEmployeeRecord == null) {

            logger.info("noticeEmployeeVerify userEmployeeRecord != null");
            String content = NoticeEmployeeVerifyContent;
            String first = NoticeEmployeeVerifyFirst;

            ConfigSysTemplateMessageLibraryRecord record =
                    templateMessageLibraryDao.getByTemplateIdAndTitle("OPENTM204875750", "员工认证提醒通知");
            if (record != null && org.apache.commons.lang.StringUtils.isNotBlank(record.getContent())) {
                content = record.getContent();
                first = record.getFirst();
            }

            UserWxUserRecord wxUserRecord = userWxUserDao.getWxUserByUserIdAndWechatId(userId, 279);
            HrWxWechatRecord wechatRecord = hrWxWechatDao.getById(279);
            if (wxUserRecord == null) {
                logger.error("微信账号不存在！userId:{}, companyId:{}", userId, companyId);
                return;
            }

            content.replace("{{keyword1.DATA}}", "尚未完成认证").replace("{{keyword2.DATA}}", "员工认证")
                    .replace("{{keyword3.DATA}}", companyName)
                    .replace("{{keyword4.DATA}}", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));

            JSONObject colMap = new JSONObject();

           /* JSONObject firstBody = new JSONObject();
            firstBody.put("color", "#173177");
            firstBody.put("value", first);

            colMap.put("first",firstBody);*/

            JSONObject body = new JSONObject();
            body.put("color", "#173177");
            body.put("value", content);
            colMap.put("first", body);

            Map<String, Object> applierTemplate = new HashMap<>();
            applierTemplate.put("data", colMap);
            applierTemplate.put("touser", wxUserRecord.getOpenid());
            applierTemplate.put("template_id", "oYQlRvzkZX1p01HS-XefLvuy17ZOpEPZEt0CNzl52nM");
            applierTemplate.put("topcolor", "#FF0000");

            logger.info("noticeEmployeeVerify applierTemplate:{}", applierTemplate);

            String result = null;
            String url=env.getProperty("message.template.delivery.url").replace("{}", wechatRecord.getAccessToken());
            logger.info("noticeEmployeeVerify url : {}", url);
            try {
                result = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
            } catch (ConnectException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

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
        String companyName = companyDO.getAbbreviation();
        if(StringUtils.isNullOrEmpty(companyName)){
            companyName = companyDO.getName();
        }
        try {
            Map<String, Object> applierTemplate = new HashMap<>();
            Map<String,MessageTplDataCol> colMap =new HashMap<>();
            MessageTplDataCol first = new MessageTplDataCol();
            first.setColor("#173177");
            first.setValue("您的简历已成功投递，坐等好消息吧！");
            colMap.put("first",first);
            MessageTplDataCol remark = new MessageTplDataCol();
            remark.setColor("#173177");
            remark.setValue("点击查看求职进度详情");
            colMap.put("remark",remark);
            MessageTplDataCol job = new MessageTplDataCol();
            job.setColor("#173177");
            job.setValue(position.getTitle());
            colMap.put("job",job);
            MessageTplDataCol company=new MessageTplDataCol();
            company.setColor("#173177");
            company.setValue(companyName);
            colMap.put("company",company);
            String dateTime = DateUtils.dateToPattern(new Date(), "yyyy年MM月dd日 HH:mm");
            MessageTplDataCol time=new MessageTplDataCol();
            time.setColor("#173177");
            time.setValue(dateTime);
            colMap.put("time",time);
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
            first.setValue(String.format("您好，感谢您推荐候选人应聘职位，%s已投递简历，详情如下：", user.getName()));
            colMap.put("first",first);
            MessageTplDataCol remark = new MessageTplDataCol();
            remark.setColor("#173177");
            remark.setValue("感谢您对公司人才招聘的支持，欢迎继续推荐！");
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
            logger.info("向推荐者发送模板消息结果："+params.get("errcode")+";提示信息："+params.get("errcmsg"));
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
     * @param appid          模板消息点击链接
     * @return
     */
    public Response handleHrTemplate(UserHrAccountDO hrAccount, JobPositionDO position, HrWxWechatDO hrWxWechatDO, HrWxTemplateMessageDO template,
                                                UserUserDO user, String workExp, String lastWork, String openId, String url,
                                                String appid)  {
        if( hrAccount == null || position == null || !StringUtils.isNotNullOrEmpty(openId) || template== null
                || user == null || !StringUtils.isNotNullOrEmpty(url)){
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        try {
            Map<String, Object> applierTemplate = new HashMap<>();
            Map<String,MessageTplDataCol> colMap =new HashMap<>();
            MessageTplDataCol first = new MessageTplDataCol();
            first.setColor("#173177");
            first.setValue( String.format("%s,您收到了一份新简历",hrAccount.getUsername()));
            colMap.put("first",first);
            MessageTplDataCol remark = new MessageTplDataCol();
            remark.setColor("#173177");
            remark.setValue("请及时登录hr.moseeker.com查阅并处理");
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
            applierTemplate.put("topcolor", template.getTopcolor());
            Map<String, String> miniprogram = new HashMap<>();
            miniprogram.put("appid", appid);
            String page = Constant.WX_APP_PROFILE_INFO_URL.replace("{}", user.getId()+"")+"&from_template_message="+template.getSysTemplateId();
            page = page + "&send_time=" + new Date().getTime();
            miniprogram.put("pagepath", page);
            logger.info("minprogram 参数 ：{}", miniprogram);
            applierTemplate.put("miniprogram", miniprogram);
            String result = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
            Map<String, Object> params = JSON.parseObject(result);
            insertLogWxMessageRecord(hrWxWechatDO, template, openId, "", colMap ,params);
            logger.info("向HR发送模板消息结果："+params.get("errcode")+";提示信息："+params.get("errcmsg"));
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
