package com.moseeker.useraccounts.receiver;

import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.useraccounts.service.ReferralRadarService;
import com.moseeker.useraccounts.service.constant.ReferralApplyHandleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RabbitReceivers {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobApplicationDao jobApplicationDao;
    @Autowired
    private ReferralRadarService radarService;

//    @RabbitListener(queues = "handle_share_chain", containerFactory = "rabbitListenerContainerFactoryAutoAck")
//    @RabbitHandler
    public void handleReferralApply(Message message){
        String msgBody = new String(message.getBody());
        logger.info("msgBody:{}", msgBody);
        if(StringUtils.isEmpty(msgBody)){
            return;
        }
        int applyId = Integer.parseInt(msgBody);
        if(applyId == 0){
            return;
        }
        JobApplication jobApplication = jobApplicationDao.fetchOneById(applyId);
        if(jobApplication.getRecommenderUserId() == null || jobApplication.getRecommenderUserId() == 0){
            return;
        }
        radarService.updateShareChainHandleType(jobApplication.getRecommenderUserId(), jobApplication.getApplierId(),
                jobApplication.getPositionId(), ReferralApplyHandleEnum.selfApply.getType());
    }
}
