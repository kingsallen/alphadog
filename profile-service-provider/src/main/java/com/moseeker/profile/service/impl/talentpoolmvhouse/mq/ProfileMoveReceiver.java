package com.moseeker.profile.service.impl.talentpoolmvhouse.mq;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.talentpooldb.TalentPoolProfileMoveDetailDao;
import com.moseeker.baseorm.dao.talentpooldb.TalentPoolProfileMoveRecordDao;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecordRecord;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.entity.biz.ProfileMailUtil;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.CrawlTypeEnum;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.ProfileMoveConstant;
import com.moseeker.profile.service.impl.talentpoolmvhouse.constant.ProfileMoveStateEnum;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 简历搬家mq消费者
 * @author cjm
 * @date 2018-07-19 11:40
 **/
@Component
public class ProfileMoveReceiver {

    private Logger logger = LoggerFactory.getLogger(ProfileMoveReceiver.class);

    private final TalentPoolProfileMoveRecordDao profileMoveRecordDao;

    private final ProfileMailUtil mailUtil;

    private final TalentPoolProfileMoveDetailDao profileMoveDetailDao;

    @Autowired
    public ProfileMoveReceiver(ProfileMailUtil mailUtil, TalentPoolProfileMoveRecordDao profileMoveRecordDao, TalentPoolProfileMoveDetailDao profileMoveDetailDao) {
        this.mailUtil = mailUtil;
        this.profileMoveRecordDao = profileMoveRecordDao;
        this.profileMoveDetailDao = profileMoveDetailDao;
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
            List<TalentpoolProfileMoveRecordRecord> records = profileMoveRecordDao.getProfileMoveRecordById(operationId);
            if(response.getStatus() != 0){
                logger.info("=====================简历搬家失败:{}", json);
                List<Integer> failedIdList = records.stream().map(TalentpoolProfileMoveRecordRecord::getId).collect(Collectors.toList());
                profileMoveRecordDao.batchUpdateStatus(failedIdList, ProfileMoveStateEnum.FAILED.getValue());
                profileMoveDetailDao.batchUpdateStatus(failedIdList, ProfileMoveStateEnum.FAILED.getValue());
                return;
            }
            int applySuccessNum = params.getIntValue("apply_success_num");
            int downloadSuccessNum = params.getIntValue("download_success_num");
            if(records == null || records.size() == 0){
                throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROFILE_MOVE_DATA_NOT_EXIST);
            }
            for(TalentpoolProfileMoveRecordRecord recordRecord : records){
                if(recordRecord.getCrawlType() == CrawlTypeEnum.DOWNLOAD_CRAWL.getStatus()){
                    recordRecord.setTotalEmailNum(downloadSuccessNum);
                }else if(recordRecord.getCrawlType() == CrawlTypeEnum.APPLY_CRAWL.getStatus()){
                    recordRecord.setTotalEmailNum(applySuccessNum);
                }
                recordRecord.setUpdateTime(null);
            }
            if(applySuccessNum == 0 && downloadSuccessNum == 0){
                for(TalentpoolProfileMoveRecordRecord recordRecord : records){
                    recordRecord.setStatus(ProfileMoveStateEnum.SUCCESS.getValue());
                }
            }
            profileMoveRecordDao.updateRecords(records);
        } catch (BIZException e){
            logger.info("handle profile move email num Error : {}, message :{}",e.getMessage(),json);
        } catch (Exception e){
            mailUtil.sendMvHouseFailedEmail(e, "rabbitmq接收邮件总数时发生异常" + json);
            logger.error("handle profile move email num Error : {}, message :{}",e.getMessage(),json);
        }
    }
}
