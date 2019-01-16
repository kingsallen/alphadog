package com.moseeker.useraccounts.receiver;

import com.moseeker.baseorm.dao.candidatedb.CandidateShareChainDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateTemplateShareChainDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
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

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RabbitReceivers {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobApplicationDao jobApplicationDao;
    @Autowired
    private CandidateShareChainDao shareChainDao;
    @Autowired
    private CandidateTemplateShareChainDao templateShareChainDao;

    @RabbitListener(queues = "handle_share_chain", containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
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
        List<CandidateShareChainDO> shareChainDOS = shareChainDao.getShareChainsByPresenteeAndPosition(jobApplication.getApplierId(), jobApplication.getPositionId());
        List<Integer> shareChainIds = shareChainDOS.stream().map(CandidateShareChainDO::getId).collect(Collectors.toList());
        shareChainDao.updateTypeByIds(shareChainIds, ReferralApplyHandleEnum.selfApply.getType());
        templateShareChainDao.updateHandledTypeByChainIds(shareChainIds, ReferralApplyHandleEnum.selfApply.getType());
    }
}
