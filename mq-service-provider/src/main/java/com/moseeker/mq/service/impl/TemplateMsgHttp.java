package com.moseeker.mq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.logdb.LogWxMessageRecordDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.referraldb.ReferralLogDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageLibraryRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatRecord;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.entity.SensorSend;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.DecodeUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.Constant.BonusStage;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxNoticeMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxHrChatDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxNoticeMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMessageRecordDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import java.net.ConnectException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.*;

import static com.moseeker.common.constants.Constant.*;

/**
 * 简历投递时发送模板消息
 * Created by moseeker on 2017/12/18.
 */

@Service
public class TemplateMsgHttp {

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
    private HrWxTemplateMessageDao wxTemplateMessageDao;

    @Autowired
    private JobApplicationDao applicationDao;

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private UserUserDao userDao;

    @Autowired
    HrCompanyDao companyDao;

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private HrOperationRecordDao operationRecordDao;

    @Autowired
    private UserAccountEntity userAccountEntity;

    @Autowired
    private UserHrAccountDao accountDao;

    @Autowired
    private Environment env;

    @Autowired
    private ConfigSysTemplateMessageLibraryDao templateMessageLibraryDao;

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    private SensorSend sensorSend;

    private static String NoticeEmployeeVerifyFirst = "您尚未完成员工认证，请尽快验证邮箱完成认证，若未收到邮件，请检查垃圾邮箱~";
    //private static String NoticeEmployeeVerifyFirstTemplateId = "oYQlRvzkZX1p01HS-XefLvuy17ZOpEPZEt0CNzl52nM";

    private static String NoticeEmployeeVerifyFirstTemplateId = "I0r7v2HKg4-flc6IaPVwoT8wud6hX2l_w4BUGIAAUSM";

    private static String NoticeEmployeeReferralBonusFirst = "恭喜你获得内推入职奖励";
    private static String NoticeEmployeeReferralBonusRemark = "请点击查看详情";
    private static String NoticeEmployeeReferralBonusTemplateId = "OPENTM411613026";
    private static String NoticeEmployeeReferralBonusTitle = "简历推荐成功提醒";
    private static String PositionSyncFailFirst = "抱歉，您的职位同步到渠道失败";
    private static String PositionSyncFailKeyword3 = "如有疑问请联系您的客户成功经理";
    private static String SeekReferralFirst = "人脉无敌！有一位朋友求推荐，快去看看吧~\n";
    private static String ReferralEvaluateFirst = "恭喜您！內推大使【{0}】已成功帮您投递了简历，耐心等待好消息吧！";
    private static String ReferralEvaluateRemark = "请点击查看最新进度~";
    private static String RedpacketChargeAmountFrist = "充值用于创建新红包活动的金额已通过确认，请到管理后台检查可用余额并开始创建红包活动吧~\n";
    private static Logger logger = LoggerFactory.getLogger(EmailProducer.class);

    public void noticeEmployeeVerify(int userId, int companyId, String companyName) {
        UserEmployeeRecord userEmployeeRecord = employeeDao.getActiveEmployee(userId, companyId);

        logger.info("noticeEmployeeVerify userEmployeeRecord:{}", userEmployeeRecord);
        if (userEmployeeRecord == null) {

            logger.info("noticeEmployeeVerify userEmployeeRecord != null");
            String first;
            String remark;

            ConfigSysTemplateMessageLibraryRecord record =
                    templateMessageLibraryDao.getByTemplateIdAndTitle("OPENTM204875750", "员工认证提醒通知");
            if (record != null) {
                first = record.getFirst();
                remark = record.getRemark();
            } else {
                 first = NoticeEmployeeVerifyFirst;
                remark = "";
            }
            logger.info("noticeEmployeeVerify first:{}", first);
            logger.info("noticeEmployeeVerify remark:{}", first);
            //公司公众号
            HrWxWechatDO hrChatDO = hrWxWechatDao.getData(new Query.QueryBuilder().where(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.getName(),
                    companyId).buildQuery());

            if (hrChatDO != null) {
                String templateId;
                HrWxTemplateMessageDO hrWxTemplateMessage = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                        hrChatDO.getId()).and("sys_template_id", Constant.EMPLOYEE_EMAILVERIFY_NOT_VERIFY_NOTICE_TPL).and("disable", "0").buildQuery());
                if (hrWxTemplateMessage == null) {
                    templateId = NoticeEmployeeVerifyFirstTemplateId;
                } else {
                    templateId = hrWxTemplateMessage.getWxTemplateId();
                }

                logger.info("noticeEmployeeVerify templateId:{}", templateId);
                UserWxUserDO userWxUserDO = userWxUserDao.getData(new Query.QueryBuilder().where(UserWxUser.USER_WX_USER.SYSUSER_ID.getName(),
                        userId).and(UserWxUser.USER_WX_USER.WECHAT_ID.getName(), hrChatDO.getId()).buildQuery());

                if (userWxUserDO != null) {

                    logger.info("noticeEmployeeVerify openid:{}", userWxUserDO.getOpenid());

                    JSONObject colMap = new JSONObject();

                    JSONObject firstJson = new JSONObject();
                    firstJson.put("color", "#173177");
                    firstJson.put("value", first);
                    colMap.put("first", firstJson);

                    JSONObject keywords1 = new JSONObject();
                    keywords1.put("color", "#173177");
                    keywords1.put("value", "尚未完成认证");
                    colMap.put("keyword1", keywords1);

                    JSONObject keywords2 = new JSONObject();
                    keywords2.put("color", "#173177");
                    keywords2.put("value", "员工认证");
                    colMap.put("keyword2", keywords2);

                    JSONObject keywords3 = new JSONObject();
                    keywords3.put("color", "#173177");
                    keywords3.put("value", companyName);
                    colMap.put("keyword3", keywords3);

                    JSONObject keywords4 = new JSONObject();
                    keywords4.put("color", "#173177");
                    keywords4.put("value", new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
                    colMap.put("keyword4", keywords4);

                    JSONObject remarkJson = new JSONObject();
                    remarkJson.put("color", "#173177");
                    remarkJson.put("value", remark);
                    colMap.put("remark", remarkJson);

                    Map<String, Object> applierTemplate = new HashMap<>();
                    applierTemplate.put("data", colMap);
                    applierTemplate.put("touser", userWxUserDO.getOpenid());
                    applierTemplate.put("template_id", templateId);
                    applierTemplate.put("topcolor", "#FF0000");


                    logger.info("noticeEmployeeVerify applierTemplate:{}", applierTemplate);

                    String url=env.getProperty("message.template.delivery.url").replace("{}", hrChatDO.getAccessToken());
                    logger.info("noticeEmployeeVerify url : {}", url);

                    try {
                        String result = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
                        logger.info("noticeEmployeeVerify result:{}", result);
                    } catch (ConnectException e) {
                        logger.error(e.getMessage(), e);
                    }
                } else {
                    logger.error("微信账号不存在！userId:{}, companyId:{}", userId, companyId);
                }

            } else {
                logger.error("微信公众号不存在！userId:{}, companyId:{}", userId, companyId);
            }
        }
    }

    public void referralEvaluateTemplate(int positionId, int userId, int applicationId, int referralId, int employeeId,long  dateTime) {
        JobPositionDO position = positionDao.getJobPositionById(positionId);

        UserUserDO user = userDao.getUser(userId);
        if(user == null){
            logger.info("求内推候选人数据为空");
            return;
        }
        String username = user.getNickname();
        if(StringUtils.isNullOrEmpty(username)) {
            UserWxUserRecord userWxUser = userWxUserDao.getWXUserByUserId(userId);
            if(userWxUser != null) {
                username = userWxUser.getNickname();
            }
        }
        if(position == null){
            logger.info("职位为空");
            return;
        }
        HrCompanyDO companyDO = companyDao.getCompanyById(position.getCompanyId());
        if(companyDO == null){
            logger.info("公司信息为空");
            return;
        }
        UserEmployeeDO employee = employeeDao.getEmployeeById(employeeId);
        if(employee == null){
            logger.info("员工信息为空");
            return ;
        }
        HrWxWechatDO wxWechatDO = hrWxWechatDao.getHrWxWechatByCompanyId(position.getCompanyId());
        if(wxWechatDO == null){
            logger.info("公众号信息为空");
            return;
        }
        UserWxUserRecord postWxUser = userWxUserDao.getWxUserByUserIdAndWechatIdAndSubscribe(userId, wxWechatDO.getId());
        if(postWxUser == null){
            logger.info("候选人微信信息为空");
            return;
        }
        HrWxTemplateMessageDO templateMessageDO = wxTemplateMessageDao.getHrWxTemplateMessageDOByWechatId(wxWechatDO.getId(), REFERRA_RECOMMEND_EVALUATE);
        if(templateMessageDO == null){
            logger.info("公众号没有配置此消息模板");
            return;
        }
        logger.info("==========================="+employee.getCname());
        String cname = employee.getCname() == null ? "":employee.getCname();
        String first = MessageFormat.format(ReferralEvaluateFirst,cname);
        String firstColor = "#2CD6B1";
        String keyword1Color = "#66A4F9";
        ConfigSysTemplateMessageLibraryRecord record =
                templateMessageLibraryDao.getConfigSysTemplateMessageLibraryDOByidListAndDisable(REFERRA_RECOMMEND_EVALUATE);
        if (record != null) {
            if(StringUtils.isNotNullOrEmpty(record.getColorJson())) {
                Map<String, Object> color = (Map<String, Object>) JSON.parse(record.getColorJson());
                if(color.get("first") != null) {
                    firstColor = (String) color.get("first");
                }
                if(color.get("keyword1") != null) {
                    keyword1Color = (String) color.get("keyword1");
                }
            }
        }
        String time =  DateUtils.dateToMinuteCNDate(new Date());
        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        MessageTplDataCol firstJson = new MessageTplDataCol();
        firstJson.setColor(firstColor);
        firstJson.setValue(first);
        colMap.put("first", firstJson);

        MessageTplDataCol keywords1 = new MessageTplDataCol();
        keywords1.setColor(keyword1Color);
        keywords1.setValue(position.getTitle());
        colMap.put("job", keywords1);

        MessageTplDataCol keywords2 = new MessageTplDataCol();
        keywords2.setColor("#173177");
        keywords2.setValue(companyDO.getAbbreviation());
        colMap.put("company", keywords2);

        MessageTplDataCol keywords3 = new MessageTplDataCol();
        keywords3.setColor("#173177");
        keywords3.setValue(time);
        colMap.put("time", keywords3);

        MessageTplDataCol remarkJson = new MessageTplDataCol();
        remarkJson.setColor("#173177");
        remarkJson.setValue(ReferralEvaluateRemark);
        colMap.put("remark", remarkJson);

        Map<String, Object> applierTemplate = new HashMap<>();
        applierTemplate.put("data", colMap);
        applierTemplate.put("touser", postWxUser.getOpenid());
        applierTemplate.put("template_id", templateMessageDO.getWxTemplateId());
        applierTemplate.put("topcolor", "#FF0000");
        String link = env.getProperty("message.template.delivery.applier.link")
                .replace("{}", String.valueOf(applicationId))+"?wechat_signature="+wxWechatDO.getSignature()
                +"&from_template_message="+Constant.REFERRA_RECOMMEND_EVALUATE+"&send_time=" + System.currentTimeMillis();
        applierTemplate.put("url",link);
        logger.info("noticeEmployeeVerify applierTemplate:{}", applierTemplate);

        String url=env.getProperty("message.template.delivery.url").replace("{}", wxWechatDO.getAccessToken());
        logger.info("noticeEmployeeVerify url : {}", url);
        try {
            String result = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
            Map<String, Object> params = JSON.parseObject(result);
            insertLogWxMessageRecord(wxWechatDO, templateMessageDO,  postWxUser.getOpenid(), link, colMap ,params);
            logger.info("noticeEmployeeVerify result:{}", result);
        } catch (ConnectException e) {
            logger.error(e.getMessage(), e);
        }

    }

    public void seekReferralTemplate(int positionId, int userId, int postUserId, int referralId,  long sendTime) {
        JobPositionDO position = positionDao.getJobPositionById(positionId);
        UserUserDO user = userDao.getUser(userId);
        if(user == null){
            logger.info("求内推候选人数据为空");
            return;
        }
        String username = user.getName();
        if(StringUtils.isNullOrEmpty(username)) {
            UserWxUserRecord userWxUser = userWxUserDao.getWXUserByUserId(userId);
            if(userWxUser != null) {
                username = userWxUser.getNickname();
            }
        }
        if(position == null){
            logger.info("职位为空");
            return;
        }
        HrWxWechatDO wxWechatDO = hrWxWechatDao.getHrWxWechatByCompanyId(position.getCompanyId());
        if(wxWechatDO == null){
            logger.info("公众号信息为空");
            return;
        }
        UserWxUserRecord postWxUser = userWxUserDao.getWxUserByUserIdAndWechatIdAndSubscribe(postUserId, wxWechatDO.getId());
        if(postWxUser == null){
            logger.info("员工微信信息为空");
            return;
        }
        HrWxTemplateMessageDO templateMessageDO = wxTemplateMessageDao.getHrWxTemplateMessageDOByWechatId(wxWechatDO.getId(), REFERRAL_SEEK_REFERRAL);
        if(templateMessageDO == null){
            logger.info("公众号没有配置此消息模板");
            return;
        }
        String first = SeekReferralFirst;
        String firstColor = "#2CD6B1";
        String keyword1Color = "#66A4F9";
        String keyword2Color = "#66A4F9";
        ConfigSysTemplateMessageLibraryRecord record =
                templateMessageLibraryDao.getConfigSysTemplateMessageLibraryDOByidListAndDisable(REFERRAL_SEEK_REFERRAL);
        if (record != null) {
            if(StringUtils.isNotNullOrEmpty(record.getColorJson())) {
                Map<String, Object> color = (Map<String, Object>) JSON.parse(record.getColorJson());
                if(color.get("first") != null) {
                    firstColor = (String) color.get("first");
                }
                if(color.get("keyword1") != null) {
                    keyword1Color = (String) color.get("keyword1");
                }
                if(color.get("keyword2") != null) {
                    keyword2Color = (String) color.get("keyword2");
                }
            }
        }
        String time =  DateUtils.dateToMinuteCNDate(new Date());


        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        MessageTplDataCol firstJson = new MessageTplDataCol();
        firstJson.setColor(firstColor);
        firstJson.setValue(first);
        colMap.put("first", firstJson);

        MessageTplDataCol keywords1 = new MessageTplDataCol();
        keywords1.setColor(keyword1Color);
        keywords1.setValue(position.getTitle());
        colMap.put("keyword1", keywords1);

        MessageTplDataCol keywords2 = new MessageTplDataCol();
        keywords2.setColor(keyword2Color);
        keywords2.setValue(username);
        colMap.put("keyword2", keywords2);

        MessageTplDataCol keywords3 = new MessageTplDataCol();
        keywords3.setColor("#171717");
        keywords3.setValue(String.valueOf(user.getMobile()));
        colMap.put("keyword3", keywords3);

        MessageTplDataCol remarkJson = new MessageTplDataCol();
        remarkJson.setColor("#171717");
        remarkJson.setValue("求推荐时间："+time);
        colMap.put("remark", remarkJson);

        Map<String, Object> applierTemplate = new HashMap<>();
        applierTemplate.put("data", colMap);
        applierTemplate.put("touser", postWxUser.getOpenid());
        applierTemplate.put("template_id", templateMessageDO.getWxTemplateId());
        applierTemplate.put("topcolor", "#FF0000");
        String link = env.getProperty("message.template.employee.recommend")
                .replace("{}", String.valueOf(referralId))+"&wechat_signature="+wxWechatDO.getSignature()
            //    +"&from_template_message="+Constant.REFERRAL_SEEK_REFERRAL+"&send_time=" + new Date().getTime();
            //new Date().getTime() 改成 now 神策埋点为了保持时间是一个唯一的uuid
            +"&from_template_message="+Constant.REFERRAL_SEEK_REFERRAL+"&send_time=" + sendTime;
        applierTemplate.put("url", link);
        logger.info("noticeEmployeeVerify applierTemplate:{}", applierTemplate);

        String url=env.getProperty("message.template.delivery.url").replace("{}", wxWechatDO.getAccessToken());
        logger.info("noticeEmployeeVerify url : {}", url);
        try {
            String result = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
            Map<String, Object> params = JSON.parseObject(result);
            insertLogWxMessageRecord(wxWechatDO, templateMessageDO,  postWxUser.getOpenid(), link, colMap ,params);

            logger.info("noticeEmployeeVerify result:{}", result);
        } catch (ConnectException e) {
            logger.error(e.getMessage(), e);
        }

    }

    public void redpacketAmountTemplate(int companyId, int amount) throws TException {
        HrCompanyDO companyDO =companyDao.getCompanyById(companyId);
        if(companyDO == null){
            logger.info("公司信息为空");
            return;
        }
        String companyName = companyDO.getAbbreviation();
        if(StringUtils.isNullOrEmpty(companyName)){
            companyName = companyDO.getName();
        }
        UserHrAccountRecord accountRecord = accountDao.fetchSuperHR(companyId);
        if(accountRecord == null){
            logger.info("hr账号为空");
            return;
        }
        UserWxUserDO  wxUserDO = userWxUserDao.getWXUserById(accountRecord.getWxuserId());
        //仟寻招聘助手
        HrWxWechatDO hrWxWechatDO = hrWxWechatDao.getData(new Query.QueryBuilder().where(HrWxWechat.HR_WX_WECHAT.SIGNATURE.getName(),
                env.getProperty("wechat.helper.signature")).buildQuery());
        if(hrWxWechatDO == null){
            logger.info("公众号信息为空");
            return;
        }
        if(wxUserDO == null || hrWxWechatDO.getId() != wxUserDO.getWechatId()){
            logger.info("hr微信信息不存在或没有关注仟寻招聘助手");
            return;
        }
        HrWxTemplateMessageDO templateMessageDO = wxTemplateMessageDao.getHrWxTemplateMessageDOByWechatId(hrWxWechatDO.getId(), REDPACKET_CHARGE_AMOUNT);
        if(templateMessageDO == null){
            logger.info("公众号没有配置此消息模板");
            return;
        }
        String first = RedpacketChargeAmountFrist;
        Map<String, String> colorMap = new HashMap<>();
        ConfigSysTemplateMessageLibraryRecord record =
                templateMessageLibraryDao.getConfigSysTemplateMessageLibraryDOByidListAndDisable(REDPACKET_CHARGE_AMOUNT);
        if (record == null || StringUtils.isNullOrEmpty(record.getColorJson())) {
            logger.info("config没有配置此模版消息");
            return;
        }else{
            colorMap = (Map<String, String>) JSON.parse(record.getColorJson());
        }
        String time =  DateUtils.dateToNormalDate(new Date());

        Map<String,MessageTplDataCol> colMap =new HashMap<>();
        MessageTplDataCol firstJson = new MessageTplDataCol();
        firstJson.setColor(colorMap.get("first"));
        firstJson.setValue(first);
        colMap.put("first", firstJson);

        MessageTplDataCol keywords1 = new MessageTplDataCol();
        keywords1.setColor(colorMap.get("keyword1"));
        keywords1.setValue(companyName);
        colMap.put("keyword1", keywords1);

        MessageTplDataCol keywords2 = new MessageTplDataCol();
        keywords2.setColor(colorMap.get("keyword2"));
        keywords2.setValue(amount+"元");
        colMap.put("keyword2", keywords2);

        MessageTplDataCol keywords3 = new MessageTplDataCol();
        keywords3.setColor("#171717");
        keywords3.setValue("红包充值");
        colMap.put("keyword3", keywords3);

       MessageTplDataCol keywords4 = new MessageTplDataCol();
        keywords4.setColor("#171717");
        keywords4.setValue(time);
        colMap.put("keyword4", keywords4);

    //    您充值用于红包活动的金额已到账，请到管理后台

        /*MessageTplDataCol remarkJson = new MessageTplDataCol();
        remarkJson.setColor("#171717");
       remarkJson.setValue("求推荐时间 ---》》："+time);
      colMap.put("remark", remarkJson);*/

        Map<String, Object> applierTemplate = new HashMap<>();
        applierTemplate.put("data", colMap);
        applierTemplate.put("touser", wxUserDO.getOpenid());
        applierTemplate.put("template_id", templateMessageDO.getWxTemplateId());
        applierTemplate.put("topcolor", "#FF0000");
//        String link = env.getProperty("message.template.employee.recommend")
//                .replace("{}", String.valueOf(referralId))+"&wechat_signature="+wxWechatDO.getSignature()
//                +"&from_template_message="+Constant.REFERRAL_SEEK_REFERRAL+"&send_time=" + new Date().getTime();
//        applierTemplate.put("url", link);
        logger.info("noticeEmployeeVerify applierTemplate:{}", applierTemplate);

        String url=env.getProperty("message.template.delivery.url").replace("{}", hrWxWechatDO.getAccessToken());
        logger.info("noticeEmployeeVerify url : {}", url);
        try {
            String result = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
            Map<String, Object> params = JSON.parseObject(result);
            insertLogWxMessageRecord(hrWxWechatDO, templateMessageDO,  wxUserDO.getOpenid(), "", colMap ,params);

            logger.info("noticeEmployeeVerify result:{}", result);
        } catch (ConnectException e) {
            logger.error(e.getMessage(), e);
        }

    }

    public void positionSyncFailTemplate(int positionId, String message, int channal) {
        JobPositionDO position = positionDao.getJobPositionById(positionId);
        if (position == null) {
            logger.info("职位信息为空");
            return;
        }
        HrWxWechatDO wechatDO = hrWxWechatDao.getHrWxWechatByCompanyId(position.getCompanyId());
        if(wechatDO == null || wechatDO.getType() == 0){
            logger.info("公众号信息为空或者是订阅号");
            return;
        }

        UserHrAccountDO account = accountDao.getValidAccount(position.getPublisher());
        if(account == null){
            logger.info("Hr信息为空");
            return;
        }

        //仟寻招聘助手
        HrWxWechatDO hrWxWechatDO = hrWxWechatDao.getData(new Query.QueryBuilder().where(HrWxWechat.HR_WX_WECHAT.SIGNATURE.getName(),
                env.getProperty("wechat.helper.signature")).buildQuery());
        HrWxTemplateMessageDO hrWxTemplateMessage = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                hrWxWechatDO.getId()).and("sys_template_id", Constant.POSITION_SYNC_FAIL_NOTICE_TPL).and("disable", "0").buildQuery());
        HrWxNoticeMessageDO messageDO = wxNoticeMessageDao.getHrWxNoticeMessageDOByWechatId(wechatDO.getId(),  Constant.POSITION_SYNC_FAIL_NOTICE_TPL);

        if (hrWxTemplateMessage == null){
            logger.info("仟寻招聘助手没有配置该模板消息");
        }
        if(messageDO == null){
            logger.info("该模板消息开关没有开启！");
            return;
        }

        UserWxUserDO wxUser  = userWxUserDao.getData(new Query.QueryBuilder().where(UserWxUser.USER_WX_USER.ID.getName(),
                account.getWxuserId()).and(UserWxUser.USER_WX_USER.WECHAT_ID.getName(), hrWxWechatDO.getId()).buildQuery());
        if(wxUser == null){
            logger.info("hr账号没有绑定微信");
            return;
        }
        JSONObject colMap = new JSONObject();

        JSONObject firstJson = new JSONObject();
        firstJson.put("color", "#173177");
        firstJson.put("value", PositionSyncFailFirst);
        colMap.put("first", firstJson);

        JSONObject keywords1 = new JSONObject();
        keywords1.put("color", "#173177");
        keywords1.put("value", position.getTitle()+" - "+ ChannelType.instaceFromInteger(channal).getAlias());
        colMap.put("keyword1", keywords1);

        JSONObject keywords2 = new JSONObject();
        keywords2.put("color", "#173177");
        keywords2.put("value", message);
        colMap.put("keyword2", keywords2);

        JSONObject keywords3 = new JSONObject();
        keywords3.put("color", "#173177");
        keywords3.put("value", PositionSyncFailKeyword3);
        colMap.put("keyword3", keywords3);

        Map<String, Object> applierTemplate = new HashMap<>();
        applierTemplate.put("data", colMap);
        applierTemplate.put("touser", wxUser.getOpenid());
        applierTemplate.put("template_id", hrWxTemplateMessage.getWxTemplateId());
        applierTemplate.put("topcolor", "#FF0000");

        logger.info("noticeEmployeeVerify applierTemplate:{}", applierTemplate);

        String url=env.getProperty("message.template.delivery.url").replace("{}", hrWxWechatDO.getAccessToken());
        logger.info("noticeEmployeeVerify url : {}", url);

        try {
            String result = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
            logger.info("noticeEmployeeVerify result:{}", result);
        } catch (ConnectException e) {
            logger.error(e.getMessage(), e);
        }

    }
    public void noticeEmployeeReferralBonus(int applicationId, long operationTIme, Integer nowStage) {
        logger.info("TemplateMsgHttp noticeEmployeeRererralBonus applicationId:{}, operationTIme:{}, nowStage:{}",
                applicationId, operationTIme, nowStage);
        JobApplication application = applicationDao.fetchOneById(applicationId);
        logger.info("TemplateMsgHttp noticeEmployeeRererralBonus application:{}", application);

        logger.info("TemplateMsgHttp noticeEmployeeRererralBonus BonusStage.Hired:{}, result:{}",
                BonusStage.Hired.getValue(), nowStage == BonusStage.Hired.getValue());
        if (application != null && nowStage == BonusStage.Hired.getValue()
                && application.getRecommenderUserId() != null && application.getRecommenderUserId() > 0) {
            UserEmployeeDO employeeDO = employeeEntity.getActiveEmployeeDOByUserId(application.getRecommenderUserId());
            if (employeeDO == null) {
                logger.info("noticeEmployeeReferralBonus 员工信息不存在！");
                return;
            }

            String first;
            String remark;
            ConfigSysTemplateMessageLibraryRecord record =
                    templateMessageLibraryDao.getConfigSysTemplateMessageLibraryDOByidListAndDisable(TEMPLATES_REFERRAL_BONUS_NOTICE_TPL);
            if (record != null) {
                first = record.getFirst();
                remark = record.getRemark();
            } else {
                first = NoticeEmployeeReferralBonusFirst;
                remark = NoticeEmployeeReferralBonusRemark;
            }

            //公司公众号
            HrWxWechatDO hrChatDO = hrWxWechatDao.getData(new Query.QueryBuilder().where(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.getName(),
                    employeeDO.getCompanyId()).buildQuery());

            if (hrChatDO != null) {
                String templateId;
                HrWxTemplateMessageDO hrWxTemplateMessage = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                        hrChatDO.getId()).and("sys_template_id", TEMPLATES_REFERRAL_BONUS_NOTICE_TPL).and("disable", "0").buildQuery());
                if (hrWxTemplateMessage == null) {
                    templateId = NoticeEmployeeReferralBonusTemplateId;
                } else {
                    templateId = hrWxTemplateMessage.getWxTemplateId();
                }

                UserWxUserDO userWxUserDO = userWxUserDao.getData(new Query.QueryBuilder().where(UserWxUser.USER_WX_USER.SYSUSER_ID.getName(),
                        employeeDO.getSysuserId()).and(UserWxUser.USER_WX_USER.WECHAT_ID.getName(), hrChatDO.getId()).buildQuery());
                if (userWxUserDO != null) {

                    String name = userAccountEntity.genUsername(application.getApplierId());
                    List<JobPosition> positionList = positionDao.fetchPosition(new ArrayList<Integer>(){{add(application.getPositionId());}});
                    String title = "";
                    if (positionList != null && positionList.size() > 0) {
                        title = positionList.get(0).getTitle();
                    }
                    DateTime handlerTime = new DateTime(operationTIme);
                    HrOperationRecord hrOperationRecord = operationRecordDao.getCurentOperation(applicationId);
                    if (hrOperationRecord.getOperateTplId() == BonusStage.Hired.getValue()) {
                        handlerTime = new DateTime(hrOperationRecord.getOptTime().getTime());
                    }

                    Map<String,MessageTplDataCol> colMap =new HashMap<>();
                    MessageTplDataCol firstJson = new MessageTplDataCol();
                    firstJson.setColor("#173177");
                    firstJson.setValue(first);
                    colMap.put("first", firstJson);

                    MessageTplDataCol keywords1 = new MessageTplDataCol();
                    keywords1.setColor("#173177");
                    keywords1.setValue(name);
                    colMap.put("keyword1", keywords1);

                    MessageTplDataCol keywords2 = new MessageTplDataCol();
                    keywords2.setColor("#173177");
                    keywords2.setValue(title);
                    colMap.put("keyword2", keywords2);

                    MessageTplDataCol keywords3 = new MessageTplDataCol();
                    keywords3.setColor("#173177");
                    keywords3.setValue(handlerTime.toString("yyyy-MM-dd HH:mm:ss"));
                    colMap.put("keyword3", keywords3);

                    MessageTplDataCol remarkJson = new MessageTplDataCol();
                    remarkJson.setColor("#173177");
                    remarkJson.setValue(remark);
                    colMap.put("remark", remarkJson);

                    Map<String, Object> applierTemplate = new HashMap<>();
                    applierTemplate.put("data", colMap);
                    applierTemplate.put("touser", userWxUserDO.getOpenid());
                    applierTemplate.put("template_id", templateId);
                    applierTemplate.put("topcolor", "#FF0000");
                    String link = env.getProperty("message.template.referral.employee.bonus.url").replace("{signature}", hrChatDO.getSignature());
                    link = link+"&from_template_message="+TEMPLATES_REFERRAL_BONUS_NOTICE_TPL+"&send_time=" + new Date().getTime();
                    applierTemplate.put("url", link);

                    logger.info("noticeEmployeeVerify applierTemplate:{}", applierTemplate);

                    String url=env.getProperty("message.template.delivery.url").replace("{}", hrChatDO.getAccessToken());
                    logger.info("noticeEmployeeVerify url : {}", url);

                    try {
                        String result = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
                        Map<String, Object> params = JSON.parseObject(result);
                        insertLogWxMessageRecord(hrChatDO, hrWxTemplateMessage, userWxUserDO.getOpenid(), link, colMap ,params);
                        logger.info("noticeEmployeeVerify result:{}", result);

                    } catch (ConnectException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }

        } else {
            logger.error("TemplateMsgHttp noticeEmployeeRererralBonus 申请信息不存在!");
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

    public void sendTenMinuteTemplate(JSONObject jsonObject) throws BIZException, ConnectException, InvalidArgumentException {
        int employeeId = jsonObject.getIntValue("employeeId");
        List<Integer> positionIds = JSONArray.parseArray(jsonObject.getString("pids")).toJavaList(Integer.class);
        int visitNum = jsonObject.getIntValue("visitNum");
        int companyId = jsonObject.getIntValue("companyId");
        long timestamp = jsonObject.getLong("timestamp");
        UserEmployeeDO employee = employeeEntity.getEmployeeByID(employeeId);
        if(employee == null){
            logger.info("======无员工信息");
            return;
        }
        List<JobPositionDO> positionDOS = positionDao.getPositionList(positionIds);
        HrWxWechatDO hrWxWechatDO = hrWxWechatDao.getHrWxWechatByCompanyId(companyId);
        if(hrWxWechatDO == null){
            return;
        }
        HrWxNoticeMessageDO messageDO = wxNoticeMessageDao.getHrWxNoticeMessageDOByWechatId(hrWxWechatDO.getId(), Constant.POSITION_VIEW_TPL);
        if(messageDO == null || messageDO.getStatus() == 0){
            return;
        }
        UserWxUserRecord userWxUserRecord = userWxUserDao.getWxUserByUserIdAndWechatId(employee.getSysuserId(), hrWxWechatDO.getId());
        JSONObject inviteTemplateVO = new JSONObject();
        DateTime dateTime = DateTime.now();
        DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        String current = dateFormat.format(dateTime.toDate());
        String title = "太棒了！您分享的职位在过去10分钟内已被%s个朋友浏览，快去看看吧~\n\n" +
                "\t\t\t\toooO      \n" +
                "\t\t\t\t (      )      Oooo\n" +
                "\t\t\t\t   \\   (         (     )\n" +
                "\t\t\t\t　 \\_)         )   /\n" +
                "\t\t\t\t                  (_/\n";
        String templateTile = String.format(title, String.valueOf(visitNum));
        List<String> positionNameList = positionDOS.stream().map(JobPositionDO::getTitle).collect(Collectors.toList());
        String positionsName = String.join(",", positionNameList);
        if(positionsName.length() > 18){
            positionsName = positionsName.substring(0, 18) + "...";
        }else {
            positionsName = positionsName + "...";
        }
        inviteTemplateVO.put("first", templateTile);
        inviteTemplateVO.put("keyWord1", String.format("已有%s人浏览该职位", visitNum));
        inviteTemplateVO.put("keyWord2", positionsName);
        inviteTemplateVO.put("keyWord3", "薪资面议");
        inviteTemplateVO.put("keyWord4", current);
        inviteTemplateVO.put("templateId", Constant.POSITION_VIEW_TPL);
        String redirectUrl = env.getProperty("message.template.delivery.radar.tenminute") + "?send_time=" +
                timestamp + "&page_size=10&page_number=1&wechat_signature=" + hrWxWechatDO.getSignature()
                + "&from_template_message="+Constant.POSITION_VIEW_TPL;
        String requestUrl = env.getProperty("message.template.delivery.url").replace("{}", hrWxWechatDO.getAccessToken());
        // 发送十分钟消息模板
        logger.info(" 1.开始发送十分钟消息模板");

        HrWxTemplateMessageDO hrWxTemplateMessageDO = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                hrWxWechatDO.getId()).and("sys_template_id", inviteTemplateVO.getIntValue("templateId")).and("disable","0").buildQuery());

        logger.info("2. hrWxTemplateMessageDO{}---》" + hrWxTemplateMessageDO);

        logger.info("3 hrWxWechatDO.getId{}---》" +hrWxWechatDO.getId());

        logger.info("4 inviteTemplateVO.getIntValue(\"templateId\")" +inviteTemplateVO.getIntValue("templateId"));
        if(hrWxTemplateMessageDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MQ_TEMPLATE_NOTICE_CLOSE);
        }
        Map<String, Object> requestMap = new HashMap<>(1 << 4);
        Map<String, JSONObject> dataMap = createDataMap(inviteTemplateVO);
        requestMap.put("data", dataMap);
        requestMap.put("touser", userWxUserRecord.getOpenid());
        requestMap.put("template_id", hrWxTemplateMessageDO.getWxTemplateId());
        requestMap.put("url", redirectUrl);
        requestMap.put("topcolor", hrWxTemplateMessageDO.getTopcolor());
        String result = HttpClient.sendPost(requestUrl, JSON.toJSONString(requestMap));
        Map<String, Object> params = JSON.parseObject(result);
        requestMap.put("response", params);
        requestMap.put("accessToken", hrWxWechatDO.getAccessToken());
        logger.info("====================requestMap:{}", requestMap);
        // 插入模板消息发送记录
        //神策生成埋点时间
        Date now = new Date();
        long sendTime=  now.getTime();
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("templateId", hrWxTemplateMessageDO.getSysTemplateId());
        properties.put("companyId", hrWxWechatDO.getId());
        properties.put("employeeId", employeeId);
        properties.put("companyName", hrWxWechatDO.getName());
        properties.put("sendTime", sendTime);

        logger.info("神策--sendTemplateMessage---》》sendtime"+sendTime);

        wxMessageRecordDao.insertLogWxMessageRecord(hrWxTemplateMessageDO.getId(), hrWxWechatDO.getId(), requestMap);
        //String templateId=inviteTemplateVO.getString("templateId");
        String distinctId = String.valueOf(userWxUserRecord.getSysuserId());
        sensorSend.send(distinctId,"sendTemplateMessage",properties);
    }

    private Map<String,JSONObject> createDataMap(JSONObject templateVO) {
        ConfigSysTemplateMessageLibraryRecord record =
                templateMessageLibraryDao.getConfigSysTemplateMessageLibraryDOByidListAndDisable(templateVO.getIntValue("templateId"));
        String colorJson = record.getColorJson();
        if(StringUtils.isNullOrEmpty(colorJson)){
            colorJson = initColor();
        }
        JSONObject color = JSONObject.parseObject(colorJson);
        Map<String, JSONObject> dataMap = new HashMap<>(1 >> 4);
        String first = templateVO.getString("first");
        if(StringUtils.isNotNullOrEmpty(first)){
            dataMap.put("first", createTplVO(color.getString("first"), first));
        }
        String keyWord1 = templateVO.getString("keyWord1");
        if(StringUtils.isNotNullOrEmpty(keyWord1)){
            dataMap.put("keyword1", createTplVO(color.getString("keyWord1"), keyWord1));
        }
        String keyWord2 = templateVO.getString("keyWord2");
        if(StringUtils.isNotNullOrEmpty(keyWord2)){
            dataMap.put("keyword2", createTplVO(color.getString("keyWord2"), keyWord2));
        }
        String keyWord3 = templateVO.getString("keyWord3");
        if(StringUtils.isNotNullOrEmpty(keyWord3)){
            dataMap.put("keyword3", createTplVO(color.getString("keyWord3"), keyWord3));
        }
        String keyWord4 = templateVO.getString("keyWord4");
        if(StringUtils.isNotNullOrEmpty(keyWord4)){
            dataMap.put("keyword4", createTplVO(color.getString("keyWord4"), keyWord4));
        }
        String remark = templateVO.getString("remark");
        if(StringUtils.isNotNullOrEmpty(remark)){
            dataMap.put("remark", createTplVO(color.getString("remark"), remark));
        }
        return dataMap;
    }

    private String initColor() {
        return "{\"first\":\"#173177\",\"keyword1\":\"#173177\",\"keyword2\":\"#173177\",\"keyword3\":\"#173177\",\"keyword3\":\"#173177\",\"remark\":\"#173177\"}";
    }

    private JSONObject createTplVO(String color, String value){
        JSONObject templateBaseVO = new JSONObject();
        templateBaseVO.put("color", color);
        templateBaseVO.put("value", value);
        return templateBaseVO;
    }

    public void demonstrationFollowWechat(int userId, String wechatId, String companyIdStr, String positionIdStr, int delay, RedisClient redisClient, Environment env) {

        int companyIdss = Integer.valueOf(companyIdStr);
        HrWxWechatRecord record = hrWxWechatDao.getById(Integer.valueOf(wechatId));
        if (record != null && companyIdss == record.getCompanyId()) {
            UserEmployeeRecord employeeRecord = employeeDao.getActiveEmployee(userId, companyIdss);
            if (employeeRecord == null) {
                JSONObject params = new JSONObject();
                params.put("ai_template_type", 0);
                params.put("company_id", companyIdss);
                params.put("position_ids", positionIdStr);
                params.put("user_id", userId);

                ProfileProfileRecord profileProfileRecord = profileDao.getProfileByUserId(userId);
                if (profileProfileRecord == null || profileProfileRecord.getDisable() == AbleFlag.DISABLE.getValue()) {
                    params.put("type", "1");
                    params.put("template_id", Constant.FANS_PROFILE_COMPLETION);
                    params.put("url", env.getProperty("demonstration.improve_profile.url"));
                    params.put("algorithm_name","");
                } else {
                    params.put("type", "2");
                    params.put("template_id", Constant.FANS_RECOM_POSITION);
                    params.put("algorithm_name","feihualing_recom");
                    params.put("url", env.getProperty("demonstration.fans_referral.url"));
                }

                redisClient.zadd(AppId.APPID_ALPHADOG.getValue(),
                        KeyIdentifier.MQ_MESSAGE_NOTICE_TEMPLATE_DEMONSTRATION_DELAY.toString(),
                        delay*60*1000+System.currentTimeMillis(), params.toJSONString());
            }


        }
    }
}
