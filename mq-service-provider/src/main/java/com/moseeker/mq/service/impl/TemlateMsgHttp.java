package com.moseeker.mq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.hrdb.HrWxNoticeMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxTemplateMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.logdb.LogWxMessageRecordDao;
import com.moseeker.baseorm.dao.referraldb.ReferralLogDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageLibraryRecord;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobPosition;
import com.moseeker.baseorm.db.referraldb.tables.pojos.ReferralLog;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.Constant.BonusStage;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.logdb.LogWxMessageRecordDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageTplDataCol;
import java.net.ConnectException;
import java.util.*;

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
    private HrWxTemplateMessageDao wxTemplateMessageDao;

    @Autowired
    private JobApplicationDao applicationDao;

    @Autowired
    private JobPositionDao positionDao;

    @Autowired
    private ReferralLogDao referralLogDao;

    @Autowired
    private EmployeeEntity employeeEntity;

    @Autowired
    private HrOperationRecordDao operationRecordDao;

    @Autowired
    private UserAccountEntity userAccountEntity;

    @Autowired
    private Environment env;

    @Autowired
    private ConfigSysTemplateMessageLibraryDao templateMessageLibraryDao;

    private static String NoticeEmployeeVerifyFirst = "您尚未完成员工认证，请尽快验证邮箱完成认证，若未收到邮件，请检查垃圾邮箱~";
    //private static String NoticeEmployeeVerifyFirstTemplateId = "oYQlRvzkZX1p01HS-XefLvuy17ZOpEPZEt0CNzl52nM";

    private static String NoticeEmployeeVerifyFirstTemplateId = "I0r7v2HKg4-flc6IaPVwoT8wud6hX2l_w4BUGIAAUSM";

    private static String NoticeEmployeeReferralBonusFirst = "恭喜你获得内推入职奖励";
    private static String NoticeEmployeeReferralBonusRemark = "请点击查看详情";
    private static String NoticeEmployeeReferralBonusTemplateId = "OPENTM411613026";

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

    public void noticeEmployeeRererralBonus(int applicationId, long operationTIme, Integer nowStage) {
        logger.info("TemplateMsgHttp noticeEmployeeRererralBonus applicationId:{}, operationTIme:{}, nowStage:{}", applicationId, operationTIme, nowStage);
        JobApplication application = applicationDao.fetchOneById(applicationId);
        logger.info("TemplateMsgHttp noticeEmployeeRererralBonus application:{}", application);

        logger.info("TemplateMsgHttp noticeEmployeeRererralBonus BonusStage.Hired:{}, result:{}", BonusStage.Hired.getValue(), nowStage == BonusStage.Hired.getValue());
        if (application != null && nowStage == BonusStage.Hired.getValue()) {
            UserEmployeeDO employeeDO = employeeEntity.getActiveEmployeeDOByUserId(application.getRecommenderUserId());
            if (employeeDO == null) {
                logger.info("noticeEmployeeReferralBonus 员工信息不存在！");
                return;
            }
            ReferralLog referralLog = referralLogDao.fetchByEmployeeIdReferenceIdUserId(employeeDO.getId(),
                    application.getApplierId(), application.getPositionId());
            if (referralLog == null) {
                logger.info("TemplateMsgHttp noticeEmployeeReferralBonus 内推记录不存在！");
                return;
            }

            String first;
            String remark;
            ConfigSysTemplateMessageLibraryRecord record =
                    templateMessageLibraryDao.getByTemplateIdAndTitle("OPENTM204875750", "员工认证提醒通知");
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
                        hrChatDO.getId()).and("sys_template_id", Constant.TEMPLATES_REFERRAL_BONUS_NOTICE_TPL).and("disable", "0").buildQuery());
                if (hrWxTemplateMessage == null) {
                    templateId = NoticeEmployeeReferralBonusTemplateId;
                } else {
                    templateId = hrWxTemplateMessage.getWxTemplateId();
                }

                UserWxUserDO userWxUserDO = userWxUserDao.getData(new Query.QueryBuilder().where(UserWxUser.USER_WX_USER.SYSUSER_ID.getName(),
                        employeeDO.getSysuserId()).and(UserWxUser.USER_WX_USER.WECHAT_ID.getName(), hrChatDO.getId()).buildQuery());
                if (userWxUserDO != null) {

                    String name = userAccountEntity.genUsername(referralLog.getReferenceId());
                    List<JobPosition> positionList = positionDao.fetchPosition(new ArrayList<Integer>(){{add(application.getId());}});
                    String title = "";
                    if (positionList != null && positionList.size() > 0) {
                        title = positionList.get(0).getTitle();
                    }
                    DateTime handlerTime = new DateTime(operationTIme);
                    HrOperationRecord hrOperationRecord = operationRecordDao.getCurentOperation(applicationId);
                    if (hrOperationRecord.getOperateTplId() == BonusStage.Hired.getValue()) {
                        handlerTime = new DateTime(hrOperationRecord.getOptTime().getTime());
                    }

                    JSONObject colMap = new JSONObject();

                    JSONObject firstJson = new JSONObject();
                    firstJson.put("color", "#173177");
                    firstJson.put("value", first);
                    colMap.put("first", firstJson);

                    JSONObject keywords1 = new JSONObject();
                    keywords1.put("color", "#173177");
                    keywords1.put("value", name);
                    colMap.put("keyword1", keywords1);

                    JSONObject keywords2 = new JSONObject();
                    keywords2.put("color", "#173177");
                    keywords2.put("value", title);
                    colMap.put("keyword2", keywords2);

                    JSONObject keywords3 = new JSONObject();
                    keywords3.put("color", "#173177");
                    keywords3.put("value", handlerTime.toString("yyyy-MM-dd HH:mm:ss"));
                    colMap.put("keyword3", keywords3);

                    JSONObject remarkJson = new JSONObject();
                    remarkJson.put("color", "#173177");
                    remarkJson.put("value", remark);
                    colMap.put("remark", remarkJson);

                    Map<String, Object> applierTemplate = new HashMap<>();
                    applierTemplate.put("data", colMap);
                    applierTemplate.put("touser", userWxUserDO.getOpenid());
                    applierTemplate.put("template_id", templateId);
                    applierTemplate.put("topcolor", "#FF0000");
                    applierTemplate.put("url", env.getProperty("message.template.referral.employee.bonus.url").replace("{signature}", hrChatDO.getAccessToken()));

                    logger.info("noticeEmployeeVerify applierTemplate:{}", applierTemplate);

                    String url=env.getProperty("message.template.delivery.url").replace("{}", hrChatDO.getAccessToken());
                    logger.info("noticeEmployeeVerify url : {}", url);

                    try {
                        String result = HttpClient.sendPost(url, JSON.toJSONString(applierTemplate));
                        logger.info("TemlateMsgHttp noticeEmployeeVerify result:{}", result);
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

}
