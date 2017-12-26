package com.moseeker.mq.service.impl;

import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrWxNoticeMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxTemplateMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCcmailDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.logdb.LogEmailSendrecordDao;
import com.moseeker.baseorm.dao.profiledb.ProfileEducationDao;
import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.dao.profiledb.ProfileWorkexpDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.dao.userdb.UserWxUserDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCcmailRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.DateUtils;
import com.moseeker.common.util.MandrillMailSend;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Order;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.MessageTemplateEntity;
import com.moseeker.mq.service.sms.SmsService;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxNoticeMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.logdb.LogEmailSendrecordDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileWorkexpDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import com.moseeker.thrift.gen.mq.struct.SmsType;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * 简历投递消息服务
 * Created by moseeker on 2017/12/18.
 */
@Service
public class ResumeDeliveryService {

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
    private TemlateMsgHttp msgHttp;
    @Autowired
    private SmsService smsService;

    private static Logger logger = LoggerFactory.getLogger(EmailProducer.class);
    private static ConfigPropertiesUtil propertiesReader = ConfigPropertiesUtil.getInstance();
    private static final String mandrillApikey = propertiesReader.get("mandrill.apikey", String.class);

    /**
     * 职位申请发送信息
     * @param application_id 申请编号
     * @return
     */
    public Response sendMessageAndEmail(int application_id) {
        Query query=new Query.QueryBuilder().where("id",
                String.valueOf(application_id)).buildQuery();
        JobApplicationDO applicationDo = applicationDao.getData(query);
        if(applicationDo != null && applicationDo.getPositionId() >0 && applicationDo.getApplierId()>0){
            if(applicationDo.getApplyType() == 1 && applicationDo.getEmailStatus() != 0){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
            }
            JobPositionDO positionDo = positionDao.getData(new Query.QueryBuilder().where("id",
                    String.valueOf(applicationDo.getPositionId())).buildQuery());
            if(positionDo == null){
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
            HrCompanyDO companyDO = companyDao.getData(new Query.QueryBuilder().where("id",
                    String.valueOf(positionDo.getCompanyId())).buildQuery());
            if(companyDO == null ) return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            UserHrAccountDO accountDo = accountDao.getData(new Query.QueryBuilder().where("id",
                    String.valueOf(positionDo.getPublisher())).buildQuery());
            UserWxUserDO hrWxUserDo = null;

            List<HrWxNoticeMessageDO> wxNoticeMessageDO = null;
            //申请者用户
            UserUserDO userUserDO = userDao.getData(new Query.QueryBuilder().where("id",
                    String.valueOf(applicationDo.getApplierId())).buildQuery());
            if(userUserDO == null)
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);

            //投递者关注的微信公众号
            HrWxWechatDO hrChatDO = hrWxWechatDao.getData(new Query.QueryBuilder().where("company_id",
                    String.valueOf(companyDO.getId())).and("authorized",1).buildQuery());
            if(hrChatDO == null) return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);

            //关注的微信公众号
            HrWxWechatDO hrWxWechatDO = hrWxWechatDao.getData(new Query.QueryBuilder().where("signature",
                    env.getProperty("wechat.helper.signature")).buildQuery());
            if(accountDo != null)
                //获取hr的微信号
                hrWxUserDo = wxUserDao.getData(new Query.QueryBuilder().where("id",
                        String.valueOf(accountDo.getWxuserId())).and("wechat_id", hrWxWechatDO.getId()).buildQuery());
            //关注的聚合号
            HrWxWechatDO aggregationChatDO = hrWxWechatDao.getData(new Query.QueryBuilder().where("signature",
                    env.getProperty("wechat.qx.signature")).buildQuery());
            //向求职者发送的模板
            HrWxTemplateMessageDO templateMessageDO = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                    String.valueOf(hrChatDO.getId())).and("sys_template_id", Constant.TEMPLATES_APPLY_NOTICE_TPL).and("disable","0").buildQuery());

            //向推荐者和HR发送的模板
            HrWxTemplateMessageDO templateMessageDOForRecom = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                    String.valueOf(hrChatDO.getId())).and("sys_template_id", Constant.TEMPLATES_NEW_RESUME_TPL).and("disable","0").buildQuery());

            HrWxTemplateMessageDO templateMessageDOForHr = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                    hrWxWechatDO.getId()).and("sys_template_id", Constant.TEMPLATES_NEW_RESUME_TPL).and("disable","0").buildQuery());
            //工作年限
            String workExp = calculate_workyears(applicationDo.getApplierId()+"");
            String lastWorkName = lastWorkName(applicationDo.getApplierId()+"");
            switch (applicationDo.getOrigin()){
                case 1:{
                    Response sendResponse = sendTemplateMessageToApplier(templateMessageDO, hrChatDO, userUserDO, application_id, companyDO, positionDo);
                    if(sendResponse.getStatus()!=0) {
                        sendTemplateMessageToApplierByQX(templateMessageDO, aggregationChatDO, userUserDO, application_id, companyDO, positionDo);
                    }
                    sendSMSToApplier(companyDO, positionDo, applicationDo, userUserDO, "4");
                    sendTemplateMessageToHr(templateMessageDOForHr, hrWxWechatDO, userUserDO ,hrWxUserDo,accountDo, positionDo,
                            workExp, lastWorkName);
                    sendSMSToHr(accountDo, positionDo, applicationDo, "4");
                    sendEmailToHr(accountDo, companyDO, positionDo, applicationDo, userUserDO);
                }
                break;
                //企业号
                case 2:{

                    Response sendResponse = sendTemplateMessageToApplier(templateMessageDO, hrChatDO, userUserDO, application_id, companyDO, positionDo);
                    if(sendResponse.getStatus()!=0) {
                    sendTemplateMessageToApplierByQX(templateMessageDO, aggregationChatDO, userUserDO, application_id, companyDO, positionDo);
                    }
                    sendSMSToApplier(companyDO, positionDo, applicationDo, userUserDO, "1");
                    sendResponse = sendTemplateMessageToRecom(templateMessageDOForRecom, hrChatDO, userUserDO, positionDo, applicationDo,  workExp, lastWorkName);
                    if(sendResponse.getStatus() !=0) {
                    sendTemplateMessageToRecomByQX(aggregationChatDO, positionDo, applicationDo,  workExp, lastWorkName);
                    }
                    sendTemplateMessageToHr(templateMessageDOForHr, hrWxWechatDO, userUserDO ,hrWxUserDo,accountDo, positionDo,
                            workExp, lastWorkName);
                    sendSMSToHr(accountDo, positionDo, applicationDo,"1");
                    sendEmailToHr(accountDo, companyDO, positionDo, applicationDo, userUserDO);
                }
                break;
                //聚合号
                case 4:{
                    sendTemplateMessageToApplierByQX(templateMessageDO, aggregationChatDO, userUserDO, application_id, companyDO, positionDo);
                    sendSMSToApplier(companyDO, positionDo, applicationDo, userUserDO, "2");
                    sendTemplateMessageToRecomByQX(aggregationChatDO,positionDo, applicationDo, workExp, lastWorkName);

                    sendTemplateMessageToHr(templateMessageDOForHr, hrWxWechatDO, userUserDO ,hrWxUserDo,accountDo, positionDo,
                            workExp, lastWorkName);
                    sendSMSToHr(accountDo, positionDo, applicationDo, "2");
                    sendEmailToHr(accountDo, companyDO, positionDo, applicationDo, userUserDO);
                }
                break;
                //简历回流
                default:{
                    sendTemplateMessageToHr(templateMessageDOForHr, hrWxWechatDO, userUserDO ,hrWxUserDo,accountDo, positionDo,
                            workExp, lastWorkName);
                    sendSMSToHr(accountDo, positionDo, applicationDo, "0");
                    sendEmailToHr(accountDo, companyDO, positionDo, applicationDo, userUserDO);
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
        String url = handlerUrl().replace("{}", hrChatDO.getAccessToken());
        if(templateMessageDO != null && hrChatDO != null){
            UserWxUserDO userWxDO = wxUserDao.getData(new Query.QueryBuilder().where("sysuser_id",
                    String.valueOf(userUserDO.getId())).and("wechat_id", hrChatDO.getId()).buildQuery());
            if(userWxDO == null) return response;
            //获取企业号消息模板发送开关
            wxNoticeMessageDO = wxNoticeMessageDao.getDatas(new Query.QueryBuilder().where("wechat_id",
                    String.valueOf(hrChatDO.getId())).and("notice_id", Constant.TEMPLATES_SWITCH_APPLY_NOTICE_TPL).and("status", "1").buildQuery());
            boolean send_applier = true;
            if(wxNoticeMessageDO == null || wxNoticeMessageDO.size()==0)
                send_applier = false;
            if(send_applier) {
                String link = handlerLink("applier").replace("{}", application_id + "");
                link += "?wechat_signature=";
                link = link + hrChatDO.getSignature();
                return msgHttp.handleApplierTemplate(positionDO, companyDO, hrChatDO, userWxDO.getOpenid(), url, link, templateMessageDO);
            }
        }
        return response;
    }

    /**
     * 给求职者发送聚合号模板消息
     * @param templateMessageDO 聚合号模板
     * @param hrChatDO          仟寻聚合号
     * @param userUserDO        申请者对象
     * @param application_id    申请编号
     * @param companyDO         公司信息
     * @param positionDO        职位信息
     * @return                  发送结果
     */
    public Response sendTemplateMessageToApplierByQX(HrWxTemplateMessageDO templateMessageDO, HrWxWechatDO hrChatDO, UserUserDO userUserDO,
                                                     int application_id, HrCompanyDO companyDO, JobPositionDO positionDO) {
        Response response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);

        if(templateMessageDO != null && hrChatDO != null){
            String url = handlerUrl().replace("{}", hrChatDO.getAccessToken());
            UserWxUserDO userWxDO = wxUserDao.getData(new Query.QueryBuilder().where("sysuser_id",
                    String.valueOf(userUserDO.getId())).and("wechat_id", hrChatDO.getId()).buildQuery());
            //获取申请者聚合号编号
            UserWxUserDO qx_userWxDO = wxUserDao.getData(new Query.QueryBuilder().where("unionid",
                    String.valueOf(userWxDO.getUnionid())).and("wechat_id", hrChatDO.getId()).buildQuery());
            //向求职者发送的模板
            HrWxTemplateMessageDO templateMessageDOQX = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                    hrChatDO.getId()).and("sys_template_id", Constant.TEMPLATES_APPLY_NOTICE_TPL).and("disable","0").buildQuery());
            if( qx_userWxDO != null) {
                String link = handlerLink("applier").replace("{}", application_id + "");
                return msgHttp.handleApplierTemplate(positionDO, companyDO, hrChatDO, qx_userWxDO.getOpenid(), url, link, templateMessageDOQX);
            }
        }
        return response;
    }

    /**
     * 向申请者发送短信
     * @param companyDO     公司对象
     * @param positionDO    职位对象
     * @param applicationDO 职位申请信息
     * @param userUserDO    申请者对象
     * @param sys           来源
     * @return  短信发送状态
     */
    public Response sendSMSToApplier(HrCompanyDO companyDO, JobPositionDO positionDO, JobApplicationDO applicationDO, UserUserDO userUserDO, String sys){

        if(companyDO != null && positionDO != null && applicationDO != null && userUserDO != null) {
            Map<String, String> data = new HashMap<>();
            data.put("company", companyDO.getAbbreviation());
            data.put("position", positionDO.getTitle());
            return smsService.sendSMS(SmsType.NEW_APPLIACATION_TO_APPLIER_SMS, userUserDO.getMobile() + "", data, sys, "");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }

    /**
     * 向推荐人发送模板消息
     * @param templateMessageDO 推荐人的模板对象
     * @param hrChatDO          公司企业号
     * @param positionDO        职位信息
     * @param applicationDO     职位申请信息
     * @param workExp           工作年限
     * @param lastWorkName      上一份工作的公司
     * @return
     */
    public Response sendTemplateMessageToRecom(HrWxTemplateMessageDO templateMessageDO, HrWxWechatDO hrChatDO, UserUserDO userDO,
                                               JobPositionDO positionDO, JobApplicationDO applicationDO, String workExp, String lastWorkName){
        List<HrWxNoticeMessageDO> wxNoticeMessageDO = null;
        Response response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        if(hrChatDO != null && applicationDO != null){
            //判断模板消息是否开启
            String url = handlerUrl().replace("{}", hrChatDO.getAccessToken());
            wxNoticeMessageDO = wxNoticeMessageDao.getDatas(new Query.QueryBuilder().where("wechat_id",
                    String.valueOf(hrChatDO.getId())).and("notice_id", Constant.TEMPLATES_SWITCH_NEW_RESUME_TPL).and("status", "1").buildQuery());
            boolean send_applier = true;
            if(wxNoticeMessageDO == null || wxNoticeMessageDO.size()==0)
                send_applier = false;
            if(send_applier) {
                UserUserDO userRecomDO = userDao.getData(new Query.QueryBuilder().where("id",
                        String.valueOf(applicationDO.getRecommenderUserId())).buildQuery());
                if(userRecomDO == null) return response;
                UserWxUserDO userWxDO = wxUserDao.getData(new Query.QueryBuilder().where("sysuser_id",
                        String.valueOf(userRecomDO.getId())).and("wechat_id", hrChatDO.getId()).buildQuery());
                if(userWxDO == null) return response;
                String link = handlerLink("recom") + "?wechat_signature="+ hrChatDO.getSignature();
                return msgHttp.handleRecomTemplate(positionDO, hrChatDO, templateMessageDO, userDO, workExp, lastWorkName, userWxDO.getOpenid(), url, link);
            }
        }
        return  response;
    }

    /**
     * 向推荐者发送聚合号信息
     * @param hrChatDO      仟寻聚合号
     * @param positionDO    职位信息
     * @param applicationDO 职位申请信息
     * @param workExp       工作年限
     * @param lastWorkName  上一个公司名称
     * @return
     */
    public Response sendTemplateMessageToRecomByQX( HrWxWechatDO hrChatDO, JobPositionDO positionDO, JobApplicationDO applicationDO,
                                                    String workExp, String lastWorkName){
        List<HrWxNoticeMessageDO> wxNoticeMessageDO = null;
        Response response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);

        if(hrChatDO != null && applicationDO != null) {
            String url = handlerUrl().replace("{}", hrChatDO.getAccessToken());
            HrWxNoticeMessageDO noticeMessageDO = wxNoticeMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                    String.valueOf(hrChatDO.getId())).and("notice_id", Constant.TEMPLATES_SWITCH_NEW_RESUME_TPL).and("status", "1").buildQuery());
            boolean send_applier = true;
            if (wxNoticeMessageDO == null || wxNoticeMessageDO.size() == 0)
                send_applier = false;
            if (send_applier) {
                UserUserDO userRecomDO = userDao.getData(new Query.QueryBuilder().where("id",
                        String.valueOf(applicationDO.getRecommenderUserId())).buildQuery());
                if(userRecomDO == null) return response;
                UserWxUserDO userWxDO = wxUserDao.getData(new Query.QueryBuilder().where("sysuser_id",
                        String.valueOf(userRecomDO.getId())).and("wechat_id", hrChatDO.getId()).buildQuery());
                if(userWxDO == null) return response;
                UserWxUserDO qx_userWxDO = wxUserDao.getData(new Query.QueryBuilder().where("unionid",
                        String.valueOf(userWxDO.getUnionid())).and("wechat_id", hrChatDO.getId()).buildQuery());
                if(qx_userWxDO == null) return response;

                //向推荐者发送的模板
                HrWxTemplateMessageDO templateMessageDOQX = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                        hrChatDO.getId()).and("sys_template_id", Constant.TEMPLATES_APPLY_NOTICE_TPL).and("disable", "0").buildQuery());
                String link = handlerLink("recom")+ "?wechat_signature="+ hrChatDO.getSignature();;
                return msgHttp.handleRecomTemplate(positionDO, hrChatDO, templateMessageDOQX, userRecomDO, workExp, lastWorkName, qx_userWxDO.getOpenid(), url, link);
            }
        }
        return response;
    }


    /**
     * 向HR发送模板消息
     * @param templateMessageDO 模板信心
     * @param hrChatDO          仟寻HR公众号
     * @param userUserDO        申请者信息
     * @param hrWxUserDo        hr微信号信息
     * @param accountDO         hr账号信息
     * @param positionDO        职位信息
     * @param workExp           工作年限
     * @param lastWorkName      上一个公司名称
     * @return
     */
    public Response sendTemplateMessageToHr(HrWxTemplateMessageDO templateMessageDO, HrWxWechatDO hrChatDO, UserUserDO userUserDO,
                                            UserWxUserDO hrWxUserDo, UserHrAccountDO accountDO, JobPositionDO positionDO,
                                            String workExp, String lastWorkName) {
        Response response = ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
        String url = handlerUrl().replace("{}", hrChatDO.getAccessToken());
        if(hrWxUserDo != null && templateMessageDO != null){
            String link ="";
            return msgHttp.handleHrTemplate(accountDO, positionDO, hrChatDO, templateMessageDO, userUserDO, workExp, lastWorkName , hrWxUserDo.getOpenid(), url, link);
        }
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
//                    String.valueOf(hrWxUserDo.getUnionid())).and("wechat_id", hrChatDO.getId()).buildQuery());
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
     * @param applicationDO 职位申请信息
     * @param sys           来源
     * @return
     */
    public Response sendSMSToHr(UserHrAccountDO accountDO, JobPositionDO positionDO, JobApplicationDO applicationDO, String sys) {
        if (accountDO != null && positionDO != null && applicationDO != null) {
            Map<String, String> data = new HashMap<>();
            data.put("position", positionDO.getTitle());
            return smsService.sendSMS(SmsType.NEW_APPLICATION_TO_HR_SMS, accountDO.getMobile(), data, sys, "");
        }
        return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
    }

    /**
     * 给Hr发送邮件
     * @param accountDO     hr账号信息
     * @param companyDO     公司信息
     * @param positionDO    职位信心
     * @param applicationDO 职位申请信息
     * @param userUserDO    申请者信息
     */
    public void sendEmailToHr(UserHrAccountDO accountDO,HrCompanyDO companyDO, JobPositionDO positionDO, JobApplicationDO applicationDO, UserUserDO userUserDO){
        //获取邮件信息
        Map<String, Object>  emailStruct = new HashMap<>();
        if(applicationDO.getApplyType() == 1 && applicationDO.getEmailStatus() == 0){
            emailStruct.put("templateName", Constant.ANNEX_RESUME_INFORM_HR);
            Map<String, Object> params = deliveryEmailToHr.annexEmailBody(companyDO, positionDO, userUserDO);
            emailStruct.put("mergeVars", params);
        }else if(applicationDO.getApplyType() == 0){
            emailStruct.put("templateName", Constant.RESUME_INFORM_HR);
            Map<String, Object> params = deliveryEmailToHr.emailBady(companyDO, positionDO, userUserDO);
            emailStruct.put("mergeVars", params);
        }else{
            return ;
        }
        //邮件发送的名称，邮箱
        String subject = positionDO.getTitle()+"-"+userUserDO.getName()+"-职位申请通知";
        emailStruct.put("subject", subject);
        emailStruct.put("to_name", accountDO.getUsername());
        emailStruct.put("to_email", accountDO.getEmail());
        //发送邮件
        Response sendEmail = MandrillMailSend.sendEmail(emailStruct, mandrillApikey);
        //记录发送邮件的结果
        LogEmailSendrecordDO emailrecord = new LogEmailSendrecordDO();
        emailrecord.setEmail("accountDO.getEmail()");
        emailrecord.setContent(sendEmail.getMessage());
        emailSendrecordDao.addData(emailrecord);
        logger.info("是否启用抄送邮箱："+positionDO.getProfile_cc_mail_enabled());
        //判断是否启用抄送邮箱
        if(positionDO.getProfile_cc_mail_enabled() == 1){
            List<JobPositionCcmailRecord> ccmailList = ccmailDao.getRecords(new Query.QueryBuilder().where("position_id",
                    String.valueOf(positionDO.getId())).buildQuery());
            logger.info("抄送邮箱长度："+ccmailList.size());
            if(ccmailList != null && ccmailList.size()>0){
                //遍历抄送邮箱发送邮件
                for(JobPositionCcmailRecord ccmail : ccmailList){
                    emailStruct.put("to_email", ccmail.getToEmail());
                    MandrillMailSend.sendEmail(emailStruct, mandrillApikey);
                    LogEmailSendrecordDO emailrecord1 = new LogEmailSendrecordDO();
                    emailrecord1.setEmail("accountDO.getEmail()");
                    emailrecord1.setContent(sendEmail.getMessage());
                    emailSendrecordDao.addData(emailrecord1);
                    logger.info("抄送邮箱："+ccmail.getToEmail());
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
            return (end - start)+"";
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
                            return companyDO.getName();
                        }else{
                            return companyDO.getAbbreviation();
                        }
                    }
                }
            }
            HrCompanyDO companyDO = companyDao.getData(new Query.QueryBuilder().where("id",
                    workexpDOList.get(workexpDOList.size() - 1).getCompanyId()).buildQuery());
            if (companyDO != null) {
                return companyDO.getAbbreviation();
            }
        }
        return "";
    }
}

