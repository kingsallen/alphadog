package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.configdb.ConfigSysTemplateMessageLibraryDao;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.hrdb.HrWxTemplateMessageDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.logdb.LogWxMessageRecordDao;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageLibraryRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import static com.moseeker.common.constants.Constant.EMPLOYEE_REFERRAL_EVALUATE;
import static com.moseeker.common.constants.Constant.EMPLOYEE_SEEK_REFERRAL_TEMPLATE;

import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.thread.ScheduledThread;
import com.moseeker.common.util.HttpClient;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.exception.ApplicationException;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxTemplateMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.referral.struct.ReferralCardInfo;
import com.moseeker.useraccounts.service.impl.vo.InviteTemplateVO;
import com.moseeker.useraccounts.service.impl.vo.TemplateBaseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 目前该类中发消息模板有两种方式，一种是异步的不关心发送结果，一种是同步的需要知道是否发送成功
 * Created by moseeker on 2018/12/10.
 */
@Component
public class ReferralTemplateSender {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SEEK_REFERRAL_EXCHNAGE = "referral_seek_exchange";

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    JobApplicationDao applicationDao;

    @Autowired
    HrOperationRecordDao operationRecordDao;

    @Autowired
    private ConfigSysTemplateMessageLibraryDao templateDao;

    @Autowired
    private LogWxMessageRecordDao wxMessageRecordDao;

    @Autowired
    private HrWxTemplateMessageDao wxTemplateMessageDao;

    ScheduledThread scheduledThread = ScheduledThread.Instance;

    public void publishSeekReferralEvent(int postUserId, int referralId, int userId, int positionId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("position_id", positionId);
        jsonObject.put("post_user_id", postUserId);
        jsonObject.put("referral_id", referralId);
        jsonObject.put("user_id", userId);
        logger.info("publishSeekReferralEvent json:{}", jsonObject);
        amqpTemplate.sendAndReceive(SEEK_REFERRAL_EXCHNAGE,
                EMPLOYEE_SEEK_REFERRAL_TEMPLATE, MessageBuilder.withBody(jsonObject.toJSONString().getBytes())
                        .build());
    }

    public void publishReferralEvaluateEvent( int referralId, int userId, int positionId, int applicationId, int employeeId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("position_id", positionId);
        jsonObject.put("referral_id", referralId);
        jsonObject.put("application_id", applicationId);
        jsonObject.put("user_id", userId);
        logger.info("publishReferralEvaluateEvent json:{}", jsonObject);
        amqpTemplate.sendAndReceive(SEEK_REFERRAL_EXCHNAGE,
                EMPLOYEE_REFERRAL_EVALUATE, MessageBuilder.withBody(jsonObject.toJSONString().getBytes())
                        .build());
    }

    public void addRecommandReward(UserEmployeeDO employeeDO, int userId, int applicationId,
                                    int positionId) throws CommonException {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("employeeId", employeeDO.getId());
            jsonObject.put("companyId", employeeDO.getCompanyId());
            jsonObject.put("positionId", positionId);
            jsonObject.put("berecomUserId", userId);
            jsonObject.put("applicationId", applicationId);
            jsonObject.put("appid", AppId.APPID_ALPHADOG.getValue());
            MessageProperties mp = new MessageProperties();
            mp.setAppId(String.valueOf(AppId.APPID_ALPHADOG.getValue()));
            mp.setReceivedExchange("user_action_topic_exchange");
            jsonObject.put("templateId", Constant.RECRUIT_STATUS_EMPLOYEE_RECOMMEND);
            amqpTemplate.send("user_action_topic_exchange", "sharejd.jd_clicked",
                    MessageBuilder.withBody(jsonObject.toJSONString().getBytes()).andProperties(mp).build());
            com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication application = applicationDao.fetchOneById(applicationId);
            operationRecordDao.addRecord(application.getId(), application.getSubmitTime().getTime(),
                    Constant.RECRUIT_STATUS_EMPLOYEE_RECOMMEND, employeeDO.getCompanyId(), 0);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ApplicationException.APPLICATION_REFERRAL_REWARD_CREATE_FAILED;
        }

    }

    public void sendTenMinuteTemplate(List<Integer> positionIds, ReferralCardInfo cardInfo, int visitNum, int employeeId) throws BIZException, ConnectException {

        List<Integer> newPositionIds = new ArrayList<>();
        if(positionIds.size() > 2){
            newPositionIds.add(positionIds.get(0));
            newPositionIds.add(positionIds.get(1));
        }else {
            newPositionIds = positionIds;
        }
        JSONObject request = new JSONObject();
        request.put("pids", JSON.toJSONString(newPositionIds));
        request.put("employeeId", employeeId);
        request.put("visitNum", visitNum);
        request.put("companyId", cardInfo.getCompanyId());
        scheduledThread.startTast(new Runnable(){
            @Override
            public void run() {
                amqpTemplate.sendAndReceive(SEEK_REFERRAL_EXCHNAGE,
                        EMPLOYEE_SEEK_REFERRAL_TEMPLATE, MessageBuilder.withBody(request.toJSONString().getBytes())
                                .build());
            }
        },10*60*1000);


    }


    /**
     * 邀请投递时发送消息模板
     * @param   hrWxWechatDO 公众号信息
     * @param   openId 要发送消息模板人的openid
     * @param   templateVO 模板填充属性
     * @param   requestUrl 消息模板请求url
     * @param   redirectUrl 点击消息模板转向的地址
     * @author  cjm
     * @date  2018/12/19
     * @return   返回微信响应结果
     */
    public Map<String, Object> sendTemplate(HrWxWechatDO hrWxWechatDO, String openId, JSONObject templateVO, String requestUrl, String redirectUrl) throws ConnectException, BIZException {
        HrWxTemplateMessageDO hrWxTemplateMessageDO = getHrWxTemplateMessageByWechatIdAndSysTemplateId(hrWxWechatDO, templateVO.getIntValue("templateId"));
        Map<String, Object> requestMap = new HashMap<>(1 >> 4);
        Map<String, TemplateBaseVO> dataMap = createDataMap(templateVO);
        requestMap.put("data", dataMap);
        requestMap.put("touser", openId);
        requestMap.put("template_id", hrWxTemplateMessageDO.getWxTemplateId());
        requestMap.put("url", redirectUrl);
        requestMap.put("topcolor", hrWxTemplateMessageDO.getTopcolor());
        String result = HttpClient.sendPost(requestUrl, JSON.toJSONString(requestMap));
        Map<String, Object> params = JSON.parseObject(result);
        requestMap.put("response", params);
        requestMap.put("accessToken", hrWxWechatDO.getAccessToken());
        logger.info("====================requestMap:{}", requestMap);
        // 插入模板消息发送记录
        wxMessageRecordDao.insertLogWxMessageRecord(templateVO.getIntValue("templateId"), hrWxWechatDO.getId(), requestMap);
        return params;
    }

    private Map<String,TemplateBaseVO> createDataMap(JSONObject templateVO) {
        ConfigSysTemplateMessageLibraryRecord record = templateDao.getConfigSysTemplateMessageLibraryDOByidListAndDisable(templateVO.getIntValue("templateId"));
        JSONObject color = JSONObject.parseObject(record.getColorJson());
        Map<String, TemplateBaseVO> dataMap = new HashMap<>(1 >> 4);
        String first = templateVO.getString("first");
        if(StringUtils.isNotNullOrEmpty(first)){
            dataMap.put("first", createTplVO(first, color.getString("first")));
        }
        String keyWord1 = templateVO.getString("keyWord1");
        if(StringUtils.isNotNullOrEmpty(keyWord1)){
            dataMap.put("keyword1", createTplVO(keyWord1, color.getString("keyWord1")));
        }
        String keyWord2 = templateVO.getString("keyWord2");
        if(StringUtils.isNotNullOrEmpty(keyWord2)){
            dataMap.put("keyword2", createTplVO(keyWord2, color.getString("keyWord2")));
        }
        String keyWord3 = templateVO.getString("keyWord3");
        if(StringUtils.isNotNullOrEmpty(keyWord3)){
            dataMap.put("keyword3", createTplVO(keyWord3, color.getString("keyWord3")));
        }
        String keyWord4 = templateVO.getString("keyWord4");
        if(StringUtils.isNotNullOrEmpty(keyWord4)){
            dataMap.put("keyword4", createTplVO(keyWord4, color.getString("keyWord4")));
        }
        String remark = templateVO.getString("remark");
        if(StringUtils.isNotNullOrEmpty(remark)){
            dataMap.put("remark", createTplVO(remark, color.getString("remark")));
        }
        return dataMap;
    }

    private TemplateBaseVO createTplVO(String color, String value){
        TemplateBaseVO templateBaseVO = new TemplateBaseVO();
        templateBaseVO.setColor(color);
        templateBaseVO.setValue(value);
        return templateBaseVO;
    }

    private HrWxTemplateMessageDO getHrWxTemplateMessageByWechatIdAndSysTemplateId(HrWxWechatDO hrWxWechatDO, int sysTemplateId) throws BIZException {
        HrWxTemplateMessageDO hrWxTemplateMessageDO = wxTemplateMessageDao.getData(new Query.QueryBuilder().where("wechat_id",
                hrWxWechatDO.getId()).and("sys_template_id", sysTemplateId).and("disable","0").buildQuery());
        if(hrWxTemplateMessageDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MQ_TEMPLATE_NOTICE_CLOSE);
        }
        return hrWxTemplateMessageDO;
    }
}
