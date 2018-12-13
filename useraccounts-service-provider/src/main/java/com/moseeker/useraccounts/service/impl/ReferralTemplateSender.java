package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HrOperationRecordDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import static com.moseeker.common.constants.Constant.EMPLOYEE_REFERRAL_EVALUATE;
import static com.moseeker.common.constants.Constant.EMPLOYEE_SEEK_REFERRAL_TEMPLATE;
import com.moseeker.common.exception.CommonException;
import com.moseeker.entity.exception.ApplicationException;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
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
}
