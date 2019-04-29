package com.moseeker.mq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.moseeker.baseorm.dao.hrdb.*;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCcmailDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.logdb.LogEmailProfileSendLogDao;
import com.moseeker.baseorm.dao.logdb.LogEmailSendrecordDao;
import com.moseeker.baseorm.dao.profiledb.ProfileEducationDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.profiledb.ProfileWorkexpDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentpoolEmailDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.hrdb.tables.HrWxNoticeMessage;
import com.moseeker.baseorm.db.hrdb.tables.HrWxTemplateMessage;
import com.moseeker.baseorm.db.hrdb.tables.HrWxWechat;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCcmailRecord;
import com.moseeker.baseorm.db.logdb.tables.records.LogEmailProfileSendLogRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail;
import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.SendEmailTypeEnum;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.MandrillMailSend;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.MessageTemplateEntity;
import com.moseeker.entity.TalentPoolEmailEntity;
import com.moseeker.entity.biz.CommonUtils;
import com.moseeker.entity.pojo.profile.info.ProfileEmailInfo;
import com.moseeker.mq.service.sms.SmsService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.*;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.logdb.LogEmailSendrecordDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileWorkexpDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.MessageEmailStruct;
import com.moseeker.thrift.gen.mq.struct.SmsType;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简历投递消息服务
 * Created by moseeker on 2017/12/18.
 */
@Service
public class ResumeDeliveryService {

    public static final Map<String, Object> EMAIL_STRUCT = new HashMap<>();
    @Autowired
    private UserUserDao userDao;
    @Autowired
    private JobApplicationDao applicationDao;
    @Autowired
    private JobPositionDao positionDao;
    @Autowired
    private UserHrAccountDao accountDao;
    @Autowired
    private UserWxUserDao wxUserDao;
    @Autowired
    private Environment env;
    @Autowired
    private HrCompanyDao companyDao;
    @Autowired
    private HrCompanyAccountDao companyAccountDao;
    @Autowired
    private ProfileProfileDao profileDao;
    @Autowired
    private ProfileEducationDao educationDao;
    @Autowired
    private ProfileWorkexpDao workexpDao;
    @Autowired
    private HrWxNoticeMessageDao wxNoticeMessageDao;
    @Autowired
    private HrWxWechatDao hrWxWechatDao;
    @Autowired
    private MessageTemplateEntity messageTemplateEntity;
    @Autowired
    private HrWxTemplateMessageDao wxTemplateMessageDao;
    @Autowired
    private DeliveryEmailProducer deliveryEmailToHr;
    @Autowired
    private LogEmailSendrecordDao emailSendrecordDao;
    @Autowired
    private JobPositionCcmailDao ccmailDao;
    @Autowired
    private TemplateMsgHttp msgHttp;
    @Autowired
    private SmsService smsService;
    @Autowired
    private TalentpoolEmailDao talentpoolEmailDao;
    @Autowired
    private HrCompanyConfDao hrCompanyConfDao;
    @Autowired
    private TalentPoolEmailEntity emailEntity;
    @Autowired
    private LogEmailProfileSendLogDao logEmailProfileSendLogDao;

    private final int company_id_mars = 1134362;//玛氏校招 硬编码，产品经理的紧急需求
    private final String email_from_mars="marscampus@moseeker.com";
    private final String email_name_mars="玛氏中国(校招)";

    private static Logger logger = LoggerFactory.getLogger(EmailProducer.class);
    private static ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
    private static final String mandrillApikey = propertiesReader.get("mandrill.apikey", String.class);

    private SerializeConfig serializeConfig = new SerializeConfig(); // 生产环境中，parserConfig要做singleton处理，要不然会存在性能问题

    public ResumeDeliveryService(){
        serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
    }
    /**
     * 职位申请发送信息
     * @param  messageEmailStruct 申请信息
     * @return
     */
    public Response sendMessageAndEmail(MessageEmailStruct messageEmailStruct) {

        logger.info("sendMessageAndEmail messageEmailStruct :{}", messageEmailStruct);

        if(messageEmailStruct != null && messageEmailStruct.getPosition_id() >0 && messageEmailStruct.getApplier_id()>0 && messageEmailStruct.getOrigin() != 128){
            if(messageEmailStruct.getApply_type() == 1 && messageEmailStruct.getEmail_status() != 0){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
            }
            JobPositionDO positionDo = positionDao.getData(new Query.QueryBuilder().where(JobPosition.JOB_POSITION.ID.getName(),
                    messageEmailStruct.getPosition_id()).buildQuery());
            if(positionDo == null){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
            HrCompanyDO companyDO = companyAccountDao.getHrCompany(positionDo.getPublisher());
            if(companyDO == null ) return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            int companyId = companyDO.getId();
            if(companyDO.getParentId() != 0)
                companyId = companyDO.getParentId();
            UserHrAccountDO accountDo = accountDao.getData(new Query.QueryBuilder().where(UserHrAccount.USER_HR_ACCOUNT.ID.getName(),
                    positionDo.getPublisher()).buildQuery());
            UserWxUserDO hrWxUserDo = null;

            List<HrWxNoticeMessageDO> wxNoticeMessageDO = null;
            //申请者用户
            UserUserDO userUserDO = userDao.getData(new Query.QueryBuilder().where(UserUser.USER_USER.ID.getName(),
                    messageEmailStruct.getApplier_id()).buildQuery());
            if(userUserDO == null)
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);

            //投递者关注的微信公众号
            HrWxWechatDO hrChatDO = hrWxWechatDao.getData(new Query.QueryBuilder().where(HrWxWechat.HR_WX_WECHAT.COMPANY_ID.getName(),
                    companyId).buildQuery());


            //仟寻招聘助手
            HrWxWechatDO hrWxWechatDO = hrWxWechatDao.getData(new Query.QueryBuilder().where(HrWxWechat.HR_WX_WECHAT.SIGNATURE.getName(),
                    env.getProperty("wechat.helper.signature")).buildQuery());

            if(accountDo != null) {
                //获取hr的微信号
                hrWxUserDo = wxUserDao.getData(new Query.QueryBuilder().where(UserWxUser.USER_WX_USER.ID.getName(),
                        accountDo.getWxuserId()).and(UserWxUser.USER_WX_USER.WECHAT_ID.getName(), hrWxWechatDO.getId()).buildQuery());
            }
            //关注的聚合号
            HrWxWechatDO aggregationChatDO = hrWxWechatDao.getData(new Query.QueryBuilder().where(HrWxWechat.HR_WX_WECHAT.SIGNATURE.getName(),
                    env.getProperty("wechat.qx.signature")).buildQuery());
            //向求职者发送的模板

            HrWxTemplateMessageDO templateMessageDO = null;
            HrWxTemplateMessageDO templateMessageDOForRecom = null;
            if(hrChatDO != null) {
                templateMessageDO = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                        hrChatDO.getId()).and("sys_template_id", Constant.TEMPLATES_APPLY_NOTICE_TPL).and("disable", "0").buildQuery());
                //向推荐者
                templateMessageDOForRecom = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                        hrChatDO.getId()).and("sys_template_id", Constant.TEMPLATES_SWITCH_NEW_RESUME_TPL).and("disable", "0").buildQuery());
            }
            HrWxTemplateMessageDO templateMessageDOForHr = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                    hrWxWechatDO.getId()).and("sys_template_id", Constant.TEMPLATES_NEW_RESUME_TPL).and("disable","0").buildQuery());
            //工作年限
            String workExp = calculate_workyears(messageEmailStruct.getApplier_id()+"");
            String lastWorkName = lastWorkName(messageEmailStruct.getApplier_id()+"");

            logger.info("sendMessageAndEmail origin :{}, workExp:{}, lastWorkName:{}", messageEmailStruct.getOrigin(), workExp, lastWorkName);

            switch (messageEmailStruct.getOrigin()){
                case 1:{
                    Response sendResponse = sendTemplateMessageToApplier(templateMessageDO, hrChatDO, userUserDO, messageEmailStruct.getApplication_id(), companyDO, positionDo);
                    if(sendResponse.getStatus()!=0) {
                        sendResponse = sendTemplateMessageToApplierByQX(hrChatDO, aggregationChatDO, userUserDO, messageEmailStruct.getApplication_id(), companyDO, positionDo);
                    }
                    sendEmailToApplier(accountDo,companyDO,positionDo,userUserDO,hrChatDO);
                    if(sendResponse.getStatus()!=0) {
                        sendSMSToApplier(companyDO, positionDo, userUserDO, "4");
                    }
                    sendTemplateMessageToHr(templateMessageDOForHr, hrChatDO, hrWxWechatDO, userUserDO ,hrWxUserDo,accountDo, positionDo,
                            workExp, lastWorkName, companyDO);
                    sendEmailToHr(accountDo, companyDO, positionDo, userUserDO, messageEmailStruct.getApply_type(), messageEmailStruct.getEmail_status());
                }
                break;

                case 524288: // 邀请投递申请
                case 1048576 :// 员工转发申
                //企业号
                case 2:{

                    Response sendResponse = sendTemplateMessageToApplier(templateMessageDO, hrChatDO, userUserDO, messageEmailStruct.getApplication_id(), companyDO, positionDo);
                    if(sendResponse.getStatus()!=0) {
                        sendResponse = sendTemplateMessageToApplierByQX(hrChatDO, aggregationChatDO, userUserDO, messageEmailStruct.getApplication_id(), companyDO, positionDo);
                    }
                    if(sendResponse.getStatus()!=0) {
                        sendSMSToApplier(companyDO, positionDo, userUserDO, "1");
                    }
                    sendEmailToApplier(accountDo,companyDO,positionDo,userUserDO,hrChatDO);


                    logger.info("向推荐人发送模板消息 被调用开始------------》");

                    sendResponse = sendTemplateMessageToRecom(templateMessageDOForRecom, hrChatDO, userUserDO, positionDo, messageEmailStruct.getRecommender_user_id(),  workExp, lastWorkName);

                    logger.info("向推荐人发送模板消息 被调用结束------------》");

                    if(sendResponse.getStatus() !=0) {
                    sendTemplateMessageToRecomByQX(hrChatDO, aggregationChatDO, positionDo, messageEmailStruct.getRecommender_user_id(),  workExp, lastWorkName);
                    }
                    sendTemplateMessageToHr(templateMessageDOForHr, hrChatDO, hrWxWechatDO, userUserDO ,hrWxUserDo,accountDo, positionDo,
                            workExp, lastWorkName, companyDO);
                    sendEmailToHr(accountDo, companyDO, positionDo, userUserDO, messageEmailStruct.getApply_type(), messageEmailStruct.getEmail_status());
                }
                break;
                //聚合号
                case 4:{
                    sendEmailToApplier(accountDo,companyDO,positionDo,userUserDO,hrChatDO);
                    Response sendResponse = sendTemplateMessageToApplierByQX(hrChatDO, aggregationChatDO, userUserDO, messageEmailStruct.getApplication_id(), companyDO, positionDo);
                    if(sendResponse.getStatus()!=0) {
                        sendSMSToApplier(companyDO, positionDo, userUserDO, "2");
                    }
                    sendTemplateMessageToRecomByQX(hrChatDO, aggregationChatDO,positionDo, messageEmailStruct.getRecommender_user_id(), workExp, lastWorkName);

                    sendResponse = sendTemplateMessageToHr(templateMessageDOForHr, hrChatDO, hrWxWechatDO, userUserDO ,hrWxUserDo,accountDo, positionDo,
                            workExp, lastWorkName, companyDO);
                    sendEmailToHr(accountDo, companyDO, positionDo, userUserDO, messageEmailStruct.getApply_type(), messageEmailStruct.getEmail_status());
                }
                break;
                //内推推荐评价
                case 262144: {
                    Response sendResponse = sendTemplateMessageToHr(templateMessageDOForHr, hrChatDO, hrWxWechatDO, userUserDO ,hrWxUserDo,accountDo, positionDo,
                            workExp, lastWorkName, companyDO);
                    sendEmailToHr(accountDo, companyDO, positionDo, userUserDO, messageEmailStruct.getApply_type(), messageEmailStruct.getEmail_status());
                }
                break;
                //简历回流
                default:{
                    sendEmailToApplier(accountDo,companyDO,positionDo,userUserDO,hrChatDO);
                    Response sendResponse = sendTemplateMessageToHr(templateMessageDOForHr, hrChatDO, hrWxWechatDO, userUserDO ,hrWxUserDo,accountDo, positionDo,
                            workExp, lastWorkName, companyDO);
                    sendEmailToHr(accountDo, companyDO, positionDo, userUserDO, messageEmailStruct.getApply_type(), messageEmailStruct.getEmail_status());
                }
            }

        }else{
            return  ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        return  ResponseUtils.success("success");
    }



    /**
     * 给求职者发送企业号模板消息
     * @param templateMessageDO 模板对象
     * @param hrChatDO          求职者关注的企业号
     * @param userUserDO        求职者对象
     * @param application_id    申请编号
     * @param companyDO         公司对象
     * @param positionDO        职位编号
     * @return                  发送状态
     */
    public Response sendTemplateMessageToApplier(HrWxTemplateMessageDO templateMessageDO, HrWxWechatDO hrChatDO, UserUserDO userUserDO,
                                                 int application_id, HrCompanyDO companyDO, JobPositionDO positionDO) {
        List<HrWxNoticeMessageDO> wxNoticeMessageDO = null;
        Response response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        if(templateMessageDO != null && hrChatDO != null){
            String url = handlerUrl().replace("{}", hrChatDO.getAccessToken());
            UserWxUserDO userWxDO = wxUserDao.getData(new Query.QueryBuilder().where(UserWxUser.USER_WX_USER.SYSUSER_ID.getName(),
                    userUserDO.getId()).and(UserWxUser.USER_WX_USER.WECHAT_ID.getName(), hrChatDO.getId()).buildQuery());
            if(userWxDO == null) return response;
            //获取企业号消息模板发送开关
            wxNoticeMessageDO = wxNoticeMessageDao.getDatas(new Query.QueryBuilder().where(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.WECHAT_ID.getName(),
                    hrChatDO.getId()).and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.NOTICE_ID.getName(), Constant.TEMPLATES_APPLY_NOTICE_TPL)
                    .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.STATUS.getName(), "1")
                    .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.DISABLE.getName(),"0").buildQuery());
            boolean send_applier = true;
            if(wxNoticeMessageDO == null || wxNoticeMessageDO.size()==0)
                send_applier = false;
            if(send_applier) {
                String link = handlerLink("applier").replace("{}", application_id + "");
                link = link+"?wechat_signature="+hrChatDO.getSignature()+"&from_template_message="+Constant.TEMPLATES_APPLY_NOTICE_TPL+"&send_time=" + new Date().getTime();
                response =  msgHttp.handleApplierTemplate(positionDO, companyDO, hrChatDO, userWxDO.getOpenid(), url, link, templateMessageDO);
            }else {
                return  ResponseUtils.fail(ConstantErrorCodeMessage.MQ_TEMPLATE_NOTICE_CLOSE);
            }
        }
        logger.info("sendEmailToHr sendTemplateMessageToApplier:{}", response);
        return response;
    }



    /**
     * 给求职者发送聚合号模板消息
     * @
     * @param hrChatDO          仟寻聚合号
     * @param userUserDO        申请者对象
     * @param application_id    申请编号
     * @param companyDO         公司信息
     * @param positionDO        职位信息
     * @return                  发送结果
     */
    public Response sendTemplateMessageToApplierByQX(HrWxWechatDO hrChatDO, HrWxWechatDO qxChatDO, UserUserDO userUserDO,
                                                     int application_id, HrCompanyDO companyDO, JobPositionDO positionDO) {
        Response response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        List<HrWxNoticeMessageDO> wxNoticeMessageDO = null;
        boolean send_applier = true;
        if(hrChatDO != null) {
            wxNoticeMessageDO = wxNoticeMessageDao.getDatas(new Query.QueryBuilder().where(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.WECHAT_ID.getName(),
                    hrChatDO.getId()).and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.NOTICE_ID.getName(), Constant.TEMPLATES_APPLY_NOTICE_TPL)
                    .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.STATUS.getName(), "1")
                    .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.DISABLE.getName(), "0").buildQuery());
            if (wxNoticeMessageDO == null || wxNoticeMessageDO.size() == 0)
                send_applier = false;
        }
        if(!send_applier){
            return   ResponseUtils.fail(ConstantErrorCodeMessage.MQ_TEMPLATE_NOTICE_CLOSE);
        }
        if(qxChatDO != null){
            String url = handlerUrl().replace("{}", qxChatDO.getAccessToken());
            UserWxUserDO userWxDO = wxUserDao.getData(new Query.QueryBuilder().where(UserWxUser.USER_WX_USER.SYSUSER_ID.getName(),
                    userUserDO.getId()).and(UserWxUser.USER_WX_USER.WECHAT_ID.getName(), qxChatDO.getId()).buildQuery());
            //获取申请者聚合号编号
            UserWxUserDO qx_userWxDO = wxUserDao.getData(new Query.QueryBuilder().where(UserWxUser.USER_WX_USER.UNIONID.getName(),
                    userWxDO.getUnionid()).and(UserWxUser.USER_WX_USER.WECHAT_ID.getName(), qxChatDO.getId()).buildQuery());
            //向求职者发送的模板
            HrWxTemplateMessageDO templateMessageDOQX = wxTemplateMessageDao.getData(new Query.QueryBuilder().where(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.WECHAT_ID.getName(),
                    qxChatDO.getId()).and(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.SYS_TEMPLATE_ID.getName(), Constant.TEMPLATES_APPLY_NOTICE_TPL)
                    .and(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.DISABLE.getName(),"0").buildQuery());
            if( qx_userWxDO != null) {
                String link = handlerLink("applier").replace("{}", application_id + "");
                link = link+"?wechat_signature="+ qxChatDO.getSignature()+"&from_template_message="+Constant.TEMPLATES_APPLY_NOTICE_TPL +"&send_time=" + new Date().getTime();;
                response = msgHttp.handleApplierTemplate(positionDO, companyDO, qxChatDO, qx_userWxDO.getOpenid(), url, link, templateMessageDOQX);
            }
        }

        logger.info("sendMessageAndEmail sendTemplateMessageToApplierByQX response:{}", response);

        return response;
    }

    /**
     * 向申请者发送短信
     * @param companyDO     公司对象
     * @param positionDO    职位对象
     * @param userUserDO    申请者对象
     * @param sys           来源
     * @return  短信发送状态
     */
    public Response sendSMSToApplier(HrCompanyDO companyDO, JobPositionDO positionDO,  UserUserDO userUserDO, String sys){

        Response response;
        if(companyDO != null && positionDO != null  && userUserDO != null) {
            Map<String, String> data = new HashMap<>();
            data.put("company", companyDO.getAbbreviation());
            data.put("position", positionDO.getTitle());
            response = smsService.sendSMS(SmsType.NEW_APPLIACATION_TO_APPLIER_SMS, userUserDO.getMobile() + "", data, sys, "");
        } else {
            response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        }
        logger.info("sendMessageAndEmail sendSMSToApplier response:{}", response);
        return response;
    }

    public void sendEmailToApplier(UserHrAccountDO accountDO,HrCompanyDO companyDO, JobPositionDO positionDO, UserUserDO userUserDO, HrWxWechatDO hrWxWechatDO){
        int company_id =  companyDO.getId();
        if(companyDO.getParentId()>0){
            company_id = companyDO.getParentId();
        }
        HrCompanyConfDO confDO = hrCompanyConfDao.getHrCompanyConfByCompanyId(company_id);
        if(confDO.getTalentpoolStatus() == 2) {
            //获取邮件信息
            List<TalentpoolEmail> emailList = talentpoolEmailDao.getTalentpoolEmailByCompanyIdAndConfigId(company_id, Constant.TALENTPOOL_EMAIL_PROFILE_SEND);
            if(emailList != null && emailList.size()>0 && emailList.get(0).getDisable()==1){
                TalentpoolEmail email = emailList.get(0);
                Map<String, Object> EMAIL_STRUCT = new HashMap<>();
                EMAIL_STRUCT.put("templateName", Constant.DELIVERY_SUCCESS);
                Map<String, Object> params = new HashMap<>();
                params.put("send_time", DateUtils.dateToNormalDate(new Date()));

                String comapny_logo  = CommonUtils.appendUrl(companyDO.getLogo(),env.getProperty("http.cdn.url"));
                params.put("company_logo", comapny_logo);
                String username = "";
                if (userUserDO.getName() != null && !userUserDO.getName().isEmpty()) {
                    username = userUserDO.getName();
                } else {
                    username = userUserDO.getNickname();
                }
                params.put("employee_name", username);
                String context = CommonUtils.replaceUtil(email.getContext(), companyDO.getAbbreviation(),positionDO.getTitle(),username, accountDO.getUsername(), hrWxWechatDO.getName());
                logger.info("context :{}",context);
                params.put("custom_text", context);
                String inscribe  = CommonUtils.replaceUtil(email.getInscribe(), companyDO.getAbbreviation(),positionDO.getTitle(),username, accountDO.getUsername(), hrWxWechatDO.getName());
                params.put("company_sign", inscribe);
                String qrcodeUrl = CommonUtils.appendUrl(hrWxWechatDO.getQrcode(), env.getProperty("http.cdn.url"));
                params.put("weixin_qrcode", qrcodeUrl);
                params.put("official_account_name", hrWxWechatDO.getName());
                params.put("company_abbr", companyDO.getAbbreviation());
                EMAIL_STRUCT.put("mergeVars", params);
                //邮件发送的名称，邮箱
                EMAIL_STRUCT.put("to_name", username);
                EMAIL_STRUCT.put("to_email", userUserDO.getEmail());

                logger.info("sendEmailToHr emailStruct:{}", EMAIL_STRUCT);
                int id = emailEntity.handerTalentpoolEmailLogAndBalance(1,1,company_id,accountDO.getId());
                if(id > 0) {
                    //发送邮件给候选人
                    Response sendEmail = new Response();
                    handleMarsEmailAddress(EMAIL_STRUCT,company_id);
                    sendEmail = MandrillMailSend.sendEmail(EMAIL_STRUCT, mandrillApikey);
                    logger.info("sendEmailToHr sendEmailResponse:{}", sendEmail);

                    //记录发送邮件的结果
                    LogEmailSendrecordDO emailrecord = new LogEmailSendrecordDO();
                    emailrecord.setEmail(accountDO.getEmail());
                    emailrecord.setContent(sendEmail.getMessage());
                    emailSendrecordDao.addData(emailrecord);
                }else{
                    logger.info("邮件额度不足！");
                }
            }
        }else{
            logger.info("没有开启智能人才库");
        }
    }

    /**
     * 向推荐人发送模板消息
     * @param templateMessageDO 推荐人的模板对象
     * @param hrChatDO          公司企业号
     * @param positionDO        职位信息
     * @param recommenderUserId     职位申请推荐人信息
     * @param workExp           工作年限
     * @param lastWorkName      上一份工作的公司
     * @return
     */
    public Response sendTemplateMessageToRecom(HrWxTemplateMessageDO templateMessageDO, HrWxWechatDO hrChatDO, UserUserDO userDO,
                                               JobPositionDO positionDO, int  recommenderUserId, String workExp, String lastWorkName){
        List<HrWxNoticeMessageDO> wxNoticeMessageDO = null;
        Response response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        if(hrChatDO != null && recommenderUserId>0 && templateMessageDO != null){
            //判断模板消息是否开启
            String url = handlerUrl().replace("{}", hrChatDO.getAccessToken());
            wxNoticeMessageDO = wxNoticeMessageDao.getDatas(new Query.QueryBuilder().where(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.WECHAT_ID.getName(),
                    hrChatDO.getId()).and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.NOTICE_ID.getName(), Constant.TEMPLATES_SWITCH_NEW_RESUME_TPL)
                    .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.STATUS.getName(), "1")
                    .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.DISABLE.getName(), "0").buildQuery());
            boolean send_applier = true;
            if(wxNoticeMessageDO == null || wxNoticeMessageDO.size()==0)
                send_applier = false;
            if(send_applier) {
                UserUserDO userRecomDO = userDao.getData(new Query.QueryBuilder().where(UserUser.USER_USER.ID.getName(),
                        recommenderUserId).buildQuery());
                if(userRecomDO == null) return response;
                UserWxUserDO userWxDO = wxUserDao.getData(new Query.QueryBuilder().where(UserWxUser.USER_WX_USER.SYSUSER_ID.getName(),
                        userRecomDO.getId()).and(UserWxUser.USER_WX_USER.WECHAT_ID.getName(), hrChatDO.getId()).buildQuery());
                if(userWxDO == null) return response;
                logger.info("向推荐人发送模板消息 开始------------》");
                String link = handlerLink("recom") + "?wechat_signature="+ hrChatDO.getSignature()+"&from_template_message="+Constant.TEMPLATES_SWITCH_NEW_RESUME_TPL+"&send_time=" + new Date().getTime();;
                response = msgHttp.handleRecomTemplate(positionDO, hrChatDO, templateMessageDO, userDO, workExp, lastWorkName, userWxDO.getOpenid(), url, link);

                logger.info("向推荐人发送模板消息 结束------------》");
            }else{
                return   ResponseUtils.fail(ConstantErrorCodeMessage.MQ_TEMPLATE_NOTICE_CLOSE);
            }
        }
        logger.info("sendMessageAndEmail sendTemplateMessageToRecom response:{}", response);
        return  response;
    }

    /**
     * 向推荐者发送聚合号信息
     * @param hrChatDO      仟寻聚合号
     * @param positionDO    职位信息
     * @param recommender_user_id 职位推荐人编号
     * @param workExp       工作年限
     * @param lastWorkName  上一个公司名称
     * @return
     */
    public Response sendTemplateMessageToRecomByQX( HrWxWechatDO hrChatDO, HrWxWechatDO qxChatDO, JobPositionDO positionDO, int recommender_user_id,
                                                    String workExp, String lastWorkName){
        List<HrWxNoticeMessageDO> wxNoticeMessageDO = null;
        Response response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);

        if(qxChatDO != null && recommender_user_id > 0 ) {
            String url = handlerUrl().replace("{}", hrChatDO.getAccessToken());
            boolean send_applier = true;
            if(hrChatDO != null) {
                wxNoticeMessageDO = wxNoticeMessageDao.getDatas(new Query.QueryBuilder().where(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.WECHAT_ID.getName(),
                        hrChatDO.getId()).and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.NOTICE_ID.getName(), Constant.TEMPLATES_SWITCH_NEW_RESUME_TPL)
                        .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.STATUS.getName(), "1")
                        .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.DISABLE.getName(), "0").buildQuery());
                if (wxNoticeMessageDO == null || wxNoticeMessageDO.size() == 0)
                    send_applier = false;
            }
            if (send_applier) {
                UserUserDO userRecomDO = userDao.getData(new Query.QueryBuilder().where(UserUser.USER_USER.ID.getName(),
                        recommender_user_id).buildQuery());
                if(userRecomDO == null) return response;
                UserWxUserDO userWxDO = wxUserDao.getData(new Query.QueryBuilder().where(UserWxUser.USER_WX_USER.SYSUSER_ID.getName(),
                        userRecomDO.getId()).and(UserWxUser.USER_WX_USER.WECHAT_ID.getName(), qxChatDO.getId()).buildQuery());
                if(userWxDO == null) return response;
                UserWxUserDO qx_userWxDO = wxUserDao.getData(new Query.QueryBuilder().where(UserWxUser.USER_WX_USER.UNIONID.getName(),
                        userWxDO.getUnionid()).and(UserWxUser.USER_WX_USER.WECHAT_ID.getName(), qxChatDO.getId()).buildQuery());
                if(qx_userWxDO == null) return response;

                //向推荐者发送的模板
                HrWxTemplateMessageDO templateMessageDOQX = wxTemplateMessageDao.getData(new Query.QueryBuilder().where(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.WECHAT_ID.getName(),
                        qxChatDO.getId()).and(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.SYS_TEMPLATE_ID.getName(), Constant.TEMPLATES_NEW_RESUME_TPL)
                        .and(HrWxTemplateMessage.HR_WX_TEMPLATE_MESSAGE.DISABLE.getName(), "0").buildQuery());

                logger.info("向推荐者发送的模板 开始");
                String link = handlerLink("recom")+ "?wechat_signature="+ qxChatDO.getSignature()+"&from_template_message="+Constant.TEMPLATES_SWITCH_NEW_RESUME_TPL+"&send_time=" + new Date().getTime();
                response = msgHttp.handleRecomTemplate(positionDO, qxChatDO, templateMessageDOQX, userRecomDO, workExp, lastWorkName, qx_userWxDO.getOpenid(), url, link);
                logger.info("向推荐者发送的模板 结束");

            }else {
                return   ResponseUtils.fail(ConstantErrorCodeMessage.MQ_TEMPLATE_NOTICE_CLOSE);
            }
        }
        logger.info("sendMessageAndEmail sendTemplateMessageToRecomByQX response:{}", response);
        return response;
    }




    /**
     * 向HR发送模板消息
     * @param templateMessageDO 模板信心
     * @param hrWxWechatDO      HR所在母公司公众号
     * @param hrWxWechatDO      仟寻HR公众号
     * @param userUserDO        申请者信息
     * @param hrWxUserDo        hr微信号信息
     * @param accountDO         hr账号信息
     * @param positionDO        职位信息
     * @param workExp           工作年限
     * @param lastWorkName      上一个公司名称
     * @return
     */
    public Response sendTemplateMessageToHr(HrWxTemplateMessageDO templateMessageDO, HrWxWechatDO hrChatDO, HrWxWechatDO hrWxWechatDO, UserUserDO userUserDO,
                                            UserWxUserDO hrWxUserDo, UserHrAccountDO accountDO, JobPositionDO positionDO,
                                            String workExp, String lastWorkName, HrCompanyDO companyDO) {
        List<HrWxNoticeMessageDO> wxNoticeMessageDO = null;
        Response response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        String url = handlerUrl().replace("{}", hrWxWechatDO.getAccessToken());
        boolean send_applier = true;
        if(hrChatDO != null) {
            wxNoticeMessageDO = wxNoticeMessageDao.getDatas(new Query.QueryBuilder().where(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.WECHAT_ID.getName(),
                    hrChatDO.getId()).and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.NOTICE_ID.getName(), Constant.TEMPLATES_NEW_RESUME_TPL)
                    .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.STATUS.getName(), "1")
                    .and(HrWxNoticeMessage.HR_WX_NOTICE_MESSAGE.DISABLE.getName(), "0").buildQuery());
            if (wxNoticeMessageDO == null || wxNoticeMessageDO.size() == 0)
                send_applier = false;
        }
        if(!send_applier && companyDO.getType() == 0){
            return   ResponseUtils.fail(ConstantErrorCodeMessage.MQ_TEMPLATE_NOTICE_CLOSE);
        }
        if(hrWxUserDo != null && templateMessageDO != null ){
            String appid = handlerMiniappId();
            response = msgHttp.handleHrTemplate(accountDO, positionDO, hrWxWechatDO, templateMessageDO, userUserDO, workExp, lastWorkName , hrWxUserDo.getOpenid(), url, appid);
        }
        logger.info("sendMessageAndEmail sendTemplateMessageToHr:{}", response);
        return  response;
    }

//    /**
//     * 简历投递通过聚合号给HR发送消息
//     * @param hrChatDO      hr注册聚合号信息
//     * @param userUserDO    申请者信息
//     * @param hrWxUserDo    hr微信号信息
//     * @param accountDO     hr账号
//     * @param positionDO    职位信息
//     * @param workExp       工作年限
//     * @param lastWorkName  上一公司名称
//     * @return
//     */
//    public Response sendTemplateMessageToHrQX( HrWxWechatDO hrChatDO, UserUserDO userUserDO,
//                                               UserWxUserDO hrWxUserDo, UserHrAccountDO accountDO, JobPositionDO positionDO,
//                                               String workExp, String lastWorkName){
//        Response response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
//        if(hrWxUserDo != null ){
//            String url = handlerUrl().replace("{}", hrChatDO.getAccessToken());
//            UserWxUserDO qx_userWxDO = wxUserDao.getData(new Query.QueryBuilder().where("unionid",
//                    hrWxUserDo.getUnionid()).and("wechat_id", hrChatDO.getId()).buildQuery());
//            if(qx_userWxDO==null) return response;
//            //向Hr发送的模板
//            HrWxTemplateMessageDO templateMessageDOQX = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
//                    env.getProperty("wechat.qx.signature")).and("sys_template_id", Constant.TEMPLATES_NEW_RESUME_TPL).and("disable","0").buildQuery());
//            String link ="";
//            logger.info("给HR发送聚合号模板消息编号："+templateMessageDOQX.id+";Template_id:"+templateMessageDOQX.getWxTemplateId()+";openid:"+qx_userWxDO.getOpenid());
//            return  msgHttp.handleHrTemplate(accountDO, positionDO, hrChatDO, templateMessageDOQX,
//                    userUserDO, workExp, lastWorkName , qx_userWxDO.getOpenid(), url, link);
//        }
//        return response;
//    }


    /**
     * 向HR发送短信
     * @param accountDO     hr账号信息
     * @param positionDO    职位信息
     * @param sys           来源
     * @return
     */
    public Response sendSMSToHr(UserHrAccountDO accountDO, JobPositionDO positionDO,  String sys) {
        Response response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);;
        if (accountDO != null && positionDO != null ) {
            Map<String, String> data = new HashMap<>();
            data.put("position", positionDO.getTitle());
            response = smsService.sendSMS(SmsType.NEW_APPLICATION_TO_HR_SMS, accountDO.getMobile(), data, sys, "");
        }
        logger.info("sendMessageAndEmail smsToHrResponse:{}", response);
        return response;
    }

    /**
     * 给Hr发送邮件
     * @param accountDO     hr账号信息
     * @param companyDO     公司信息
     * @param positionDO    职位信心
     * @param applt_type    职位申请来源
     * @param email_status  邮件申请状态
     * @param userUserDO    申请者信息
     */
    public void sendEmailToHr(UserHrAccountDO accountDO,HrCompanyDO companyDO, JobPositionDO positionDO, UserUserDO userUserDO, int applt_type, int email_status)  {
        //获取邮件信息
        Map<String, Object>  emailStruct = new HashMap<>();
        if(applt_type == 1 && email_status == 0){
            emailStruct.put("templateName", Constant.ANNEX_RESUME_INFORM_HR);
            Map<String, Object> params = deliveryEmailToHr.annexEmailBody(companyDO, positionDO, userUserDO);
            emailStruct.put("mergeVars", params);
        }else if(applt_type == 0){
            emailStruct.put("templateName", Constant.RESUME_INFORM_HR);
            ProfileEmailInfo params = null;
            try {
                params = deliveryEmailToHr.emailBadyV2(companyDO, positionDO, userUserDO);
            } catch (TException e) {
                e.printStackTrace();
                logger.info(e.getMessage(), e);
                logger.info("Hr邮件===================报错了");
                return;
            }
            String res= JSON.toJSONString(params,serializeConfig);
            Map<String, Object> data = JSON.parseObject(res, Map.class);
            emailStruct.put("mergeVars", data);
        }else{
            return ;
        }
        //邮件发送的名称，邮箱
        String subject = positionDO.getTitle()+"-"+userUserDO.getName()+"-职位申请通知";
        emailStruct.put("subject", subject);
        emailStruct.put("to_name", accountDO.getUsername());
        emailStruct.put("to_email", accountDO.getEmail());

        logger.info("sendEmailToHr emailStruct:{}", emailStruct);

        //发送邮件给HR
        Response sendEmail = new Response();
        if(positionDO.getEmailNotice() == 0) {
            sendEmail = MandrillMailSend.sendEmail(emailStruct, mandrillApikey);
            logger.info("sendEmailToHr sendEmailResponse:{}", sendEmail);

            //记录发送邮件的结果
            LogEmailSendrecordDO emailrecord = new LogEmailSendrecordDO();
            emailrecord.setEmail(accountDO.getEmail());
            emailrecord.setContent(sendEmail.getMessage());
            emailSendrecordDao.addData(emailrecord);

            LogEmailProfileSendLogRecord record=new LogEmailProfileSendLogRecord();
            record.setEmail(accountDO.getEmail());
            record.setUserId(userUserDO.getId());
            record.setType(SendEmailTypeEnum.POSITION_INVATE_EMAIL.getValue());
            record.setCompanyId(accountDO.getCompanyId());
            logEmailProfileSendLogDao.addRecord(record);
        }

        logger.info("是否启用抄送邮箱："+positionDO.getProfile_cc_mail_enabled());
        //判断是否启用抄送邮箱
        if(positionDO.getProfile_cc_mail_enabled() == 1){
            List<JobPositionCcmailRecord> ccmailList = ccmailDao.getRecords(new Query.QueryBuilder().where("position_id",
                    positionDO.getId()).buildQuery());
            logger.info("抄送邮箱长度："+ccmailList.size());
            if(ccmailList != null && ccmailList.size()>0){
                //添加逻辑：1，查询邮件额度是否满足发送 2，扣除邮件额度 3，发送邮件
                int id = 1;//emailEntity.handerTalentpoolEmailLogAndBalanceNew(1,1,companyDO.getId(),accountDO.getId());
                //遍历抄送邮箱发送邮件
                if(id>0){
                    for (JobPositionCcmailRecord ccmail : ccmailList) {
                        emailStruct.put("to_email", ccmail.getToEmail());
                        sendEmail = MandrillMailSend.sendEmail(emailStruct, mandrillApikey);
                        try {
                            LogEmailSendrecordDO emailrecord1 = new LogEmailSendrecordDO();
                            emailrecord1.setEmail(ccmail.getToEmail());
                            emailrecord1.setContent(sendEmail.getMessage());
                            emailSendrecordDao.addData(emailrecord1);
                            LogEmailProfileSendLogRecord record1 = new LogEmailProfileSendLogRecord();
                            record1.setEmail(ccmail.getToEmail());
                            record1.setUserId(userUserDO.getId());
                            record1.setType(SendEmailTypeEnum.POSITION_INVATE_EMAIL.getValue());
                            record1.setCompanyId(accountDO.getCompanyId());
                            logEmailProfileSendLogDao.addRecord(record1);
                        } catch (Exception e) {
                            logger.error("简历抄送邮箱日记记录失败：{}", e.getMessage());
                        }
                        logger.info("抄送邮箱：" + ccmail.getToEmail());
                    }
                }
            }
        }
    }


    /*
  处理url
  */
    private String handlerUrl(){
        String url="";
        url=env.getProperty("message.template.delivery.url");
        return url;

    }

    private String handlerLink(String type){
        String url="";
        if("applier".equals(type)){
            url=env.getProperty("message.template.delivery.applier.link");
        }else if("recom".equals(type)){
            url=env.getProperty("message.template.delivery.recom.link");
        }
        return url;

    }

    private String handlerMiniappId(){
        String appid="";
        appid=env.getProperty("miniapp.appid");
        return appid;

    }

    private String calculate_workyears(String user_id ){
        ProfileProfileDO profileDO = profileDao.getData(new Query.QueryBuilder().where("user_id",
                user_id).buildQuery());
        if(profileDO == null )
            return "";
        List<ProfileWorkexpDO> workexpDOList = workexpDao.getDatas(new Query.QueryBuilder().where("profile_id",
                profileDO.getId()).orderBy("end",Order.ASC).buildQuery());
        if(workexpDOList != null && workexpDOList.size()>0) {
            int start = 2099;
            int end = 0;
            for (ProfileWorkexpDO workexpDO : workexpDOList) {
                if ((Integer.parseInt(workexpDO.getStartTime().substring(0, 4))) < start) {
                    start = Integer.parseInt(workexpDO.getStartTime().substring(0, 4));
                }
                if(1==workexpDO.getEndUntilNow() || workexpDO.getEndTime() == null){
                    end = Integer.parseInt(DateUtils.formatDate(new Date(), "yyyy"));
                }else if ((Integer.parseInt(workexpDO.getEndTime().substring(0, 4))) > end) {
                    end = Integer.parseInt(workexpDO.getEndTime().substring(0, 4));
                }
            }
            return (end - start)+"年";
        }
        return "";
    }

    //获取上一份工作公司名称
    private String lastWorkName(String user_id ) {
        ProfileProfileDO profileDO = profileDao.getData(new Query.QueryBuilder().where("user_id",
                user_id).buildQuery());
        if (profileDO == null)
            return "";
        List<ProfileWorkexpDO> workexpDOList = workexpDao.getDatas(new Query.QueryBuilder().where("profile_id",
                profileDO.getId()).orderBy("end", Order.ASC).buildQuery());
        if (workexpDOList != null && workexpDOList.size() > 0) {
            for (ProfileWorkexpDO workexpDO : workexpDOList) {
                if (1 == workexpDO.getEndUntilNow()) {
                    HrCompanyDO companyDO = companyDao.getData(new Query.QueryBuilder().where("id",
                            workexpDO.getCompanyId()).buildQuery());
                    if (companyDO != null) {
                        if(StringUtils.isNotNullOrEmpty(companyDO.getName())){
                            return companyDO.getName()+" | "+workexpDO.getJob();
                        }else{
                            return companyDO.getAbbreviation()+" | "+workexpDO.getJob();
                        }
                    }
                }
            }
            HrCompanyDO companyDO = companyDao.getData(new Query.QueryBuilder().where("id",
                    workexpDOList.get(workexpDOList.size() - 1).getCompanyId()).buildQuery());
            if (companyDO != null) {
                if(StringUtils.isNotNullOrEmpty(companyDO.getName())){
                    return companyDO.getName()+" | "+ workexpDOList.get(workexpDOList.size() - 1).getJob();
                }else{
                    return companyDO.getAbbreviation()+" | "+ workexpDOList.get(workexpDOList.size() - 1).getJob();
                }
            }
        }
        return "";
    }

    /**
     * 功能描述 : 玛氏校招，要求发送给学生的确认邮件通过「marscampus@moseeker.com」发送。
     * @author : JackYang
     * @date : 2019-04-11 16:09
     * @param emailStruct :
     * @param companyId :
     * @return : void
     */
    private void handleMarsEmailAddress(Map<String,Object> emailStruct,Integer companyId) {
        if(companyId.equals(company_id_mars)) {
            emailStruct.put("from_email",email_from_mars);
            emailStruct.put("from_name",email_name_mars);
        }
    }
}

