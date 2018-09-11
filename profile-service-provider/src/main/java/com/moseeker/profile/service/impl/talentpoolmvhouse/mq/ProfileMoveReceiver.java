package com.moseeker.profile.service.impl.talentpoolmvhouse.mq;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.talentpooldb.TalentPoolProfileMoveDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentPoolProfileMoveRecordDao;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecordRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.ProfileMoveConstant;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.ProfileMoveStateEnum;
import com.moseeker.profile.utils.ProfileMailUtil;
import com.moseeker.thrift.gen.common.struct.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 简历搬家mq消费者
 * @author cjm
 * @date 2018-07-19 11:40
 **/
@Component
public class ProfileMoveReceiver {

    private Logger logger = LoggerFactory.getLogger(ProfileMoveReceiver.class);

    private final TalentPoolProfileMoveDao profileMoveDao;

    private final TalentPoolProfileMoveRecordDao profileMoveRecordDao;

    private final ProfileMailUtil mailUtil;

    @Autowired
    public ProfileMoveReceiver(TalentPoolProfileMoveDao profileMoveDao, ProfileMailUtil mailUtil, TalentPoolProfileMoveRecordDao profileMoveRecordDao) {
        this.profileMoveDao = profileMoveDao;
        this.mailUtil = mailUtil;
        this.profileMoveRecordDao = profileMoveRecordDao;
    }

    @RabbitHandler
    @RabbitListener(queues = {ProfileMoveConstant.PROFILE_MOVE_QUEUE}, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    public void receiveProfileMoveResult(Message message) {
        String json = "";
        try{
            logger.info("==============================接受邮件总数rabbitmq开始消费，message:{}", message);
            json=new String(message.getBody(), "UTF-8");
            Response response = JSONObject.parseObject(json, Response.class);
            if(response == null){
                return;
            }
            JSONObject params = JSONObject.parseObject(response.getData());
            int operationId = params.getIntValue("operation_id");
            TalentpoolProfileMoveRecordRecord record = profileMoveRecordDao.getProfileMoveRecordById(operationId);
            int totalEmailNum = params.getIntValue("success_email_num");
            if(record == null){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_MOVE_DATA_NOT_EXIST);
            }
            record.setTotalEmailNum(totalEmailNum);
            profileMoveRecordDao.updateRecord(record);
        } catch (Exception e){
            mailUtil.sendMvHouseFailedEmail(e, "rabbitmq接收邮件总数时发生异常" + json);
            logger.error("handle profile move email num Error : {}, message :{}",e.getMessage(),json);
        }
    }
}
