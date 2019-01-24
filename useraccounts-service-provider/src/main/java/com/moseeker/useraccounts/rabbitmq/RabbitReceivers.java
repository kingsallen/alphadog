package com.moseeker.useraccounts.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.candidatedb.CandidateShareChainDao;
import com.moseeker.baseorm.dao.candidatedb.CandidateTemplateShareChainDao;
import com.moseeker.baseorm.dao.hrdb.HrWxNoticeMessageDao;
import com.moseeker.baseorm.dao.hrdb.HrWxWechatDao;
import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
import com.moseeker.common.constants.Constant;
import com.moseeker.thrift.gen.company.struct.CompanySwitchVO;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxNoticeMessageDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxWechatDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.useraccounts.aspect.RadarSwitchAspect;
import com.moseeker.useraccounts.kafka.KafkaSender;
import com.moseeker.useraccounts.service.constant.ReferralApplyHandleEnum;
import com.moseeker.useraccounts.service.impl.pojos.KafkaApplyPojo;
import com.moseeker.useraccounts.service.impl.pojos.KafkaBaseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RabbitReceivers {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaSender kafkaSender;
    @Autowired
    private JobApplicationDao jobApplicationDao;
    @Autowired
    private CandidateShareChainDao shareChainDao;
    @Autowired
    private JobPositionDao jobPositionDao;
    @Autowired
    private HrWxWechatDao hrWxWechatDao;
    @Autowired
    private UserEmployeeDao userEmployeeDao;
    @Autowired
    private HrWxNoticeMessageDao wxNoticeMessageDao;
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
        if(jobApplication == null){
            return;
        }
        sendApplyToKafka(jobApplication);
        // 获取申请来源对应的shareChain处理类型
        int type = ReferralApplyHandleEnum.getByApplicationSource(jobApplication.getOrigin());
        if(type == 0){
            return;
        }
        List<CandidateShareChainDO> shareChainDOS = shareChainDao.getShareChainsByPresenteeAndPosition(jobApplication.getApplierId(), jobApplication.getPositionId());
        List<Integer> shareChainIds = shareChainDOS.stream().map(CandidateShareChainDO::getId).collect(Collectors.toList());
        if(shareChainIds.size() == 0){
            return;
        }
        shareChainDao.updateTypeByIds(shareChainIds, type);
        templateShareChainDao.updateHandledTypeByChainIds(shareChainIds, type);
    }

//    @RabbitListener(queues = "handle_switch_radar", containerFactory = "rabbitListenerContainerFactoryAutoAck")
//    @RabbitHandler
    public void handleRadarSwitch(Message message){
        String msgBody = new String(message.getBody());
        logger.info("msgBody:{}", msgBody);
        int templateId = Constant.POSITION_VIEW_TPL;
        if(StringUtils.isEmpty(msgBody)){
            return;
        }
        CompanySwitchVO switchVO = JSONObject.parseObject(msgBody, CompanySwitchVO.class);
        // 仅处理雷达开关
        if(!(switchVO.getKeyword().equals(RadarSwitchAspect.RADAR_LANAGUE))){
            return;
        }
        HrWxWechatDO hrWxWechatDO = hrWxWechatDao.getHrWxWechatByCompanyId(switchVO.getCompanyId());
        if(hrWxWechatDO == null){
            return;
        }
        if(switchVO.getValid()==1){
            // 将消息模板开关打开
            openTemMiniteTemplateSwitch(hrWxWechatDO.getId(), templateId);
            sendEmployeeToKafka(switchVO.getCompanyId());
        }else if(switchVO.getValid() == 0){
            // 将开关关闭
            closeTemMiniteTemplateSwitch(hrWxWechatDO.getId(), templateId);
            handleEmployeeNetwork(switchVO.getCompanyId());
        }
    }

    private void handleEmployeeNetwork(int companyId) {

    }

    private void sendEmployeeToKafka(int companyId) {
        List<UserEmployeeDO> userEmployeeDOS = userEmployeeDao.getEmployeeBycompanyId(companyId);
        kafkaSender.sendEmployeeCertification(userEmployeeDOS);
    }

    private void closeTemMiniteTemplateSwitch(int wechatId, int templateId) {
        HrWxNoticeMessageDO messageDO = wxNoticeMessageDao.getHrWxNoticeMessageDOByWechatId(wechatId, templateId);
        if(messageDO != null && messageDO.getStatus() == 1){
            messageDO.setStatus(0);
            wxNoticeMessageDao.updateData(messageDO);
        }
    }

    private void openTemMiniteTemplateSwitch(int wechatId, int templateId) {
        HrWxNoticeMessageDO messageDO = wxNoticeMessageDao.getHrWxNoticeMessageDOByWechatId(wechatId, templateId);
        if(messageDO == null){
            messageDO = new HrWxNoticeMessageDO();
            messageDO.setNoticeId(templateId);
            messageDO.setStatus(1);
            messageDO.setWechatId(wechatId);
        }else if(messageDO.getStatus() == 0){
            messageDO.setStatus(1);
            wxNoticeMessageDao.updateData(messageDO);
        }
    }

    private void sendApplyToKafka(JobApplication jobApplication) {
        long current = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int positionId = jobApplication.getPositionId();
        JobPositionDO jobPositionDO = jobPositionDao.getJobPositionById(positionId);
        KafkaApplyPojo kafkaApplyPojo = new KafkaApplyPojo();
        kafkaApplyPojo.setApplied(1);
        kafkaApplyPojo.setCompany_id(jobPositionDO.getCompanyId());
        kafkaApplyPojo.setEvent("application");
        kafkaApplyPojo.setEvent_time(sdf.format(new Date(current)));
        kafkaApplyPojo.setPosition_id(jobApplication.getPositionId());
        kafkaApplyPojo.setUser_id(jobApplication.getApplierId());
        kafkaSender.sendMessage(Constant.KAFKA_TOPIC_APPLICATION, JSON.toJSONString(kafkaApplyPojo));
    }
}
