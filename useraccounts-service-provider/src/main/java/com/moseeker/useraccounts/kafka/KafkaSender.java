package com.moseeker.useraccounts.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.DateUtils;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.useraccounts.pojo.neo4j.Connection;
import com.moseeker.useraccounts.service.impl.pojos.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class KafkaSender {
    Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private JobPositionDao positionDao;

    private final static String SOCIAL_GRAPH_CHANGE = "social_graph_change";

    private final static String EMPLOYEE_CERTIFICATION = "employee_certification";

    private final static String CONNECTION_CHANGE = "radar_link_game";

    private final static String RADAR_BUTTON_STATUS = "button_status";

    public void sendMessage(String topic, String data){
        logger.info("kafka topic:{},data:{}",topic, data);
        logger.info("KafkaSender sendMessage kafkaTemplate:{}",kafkaTemplate);
        kafkaTemplate.send(topic, data);
    }

    public void sendForwardView(CandidateShareChainDO chain){
        KafkaBaseDto dto = new KafkaBaseDto();
        dto.setEvent_time(chain.getClickTime());
        dto.setEvent(SOCIAL_GRAPH_CHANGE);
        dto.setPosition_id(chain.getPositionId());
        JobPositionDO position = positionDao.getJobPositionById(chain.getPositionId());
        if(position != null){
            dto.setCompany_id(position.getCompanyId());
        }
        dto.setUser_id(chain.getPresenteeUserId());
        sendMessage(Constant.KAFKA_TOPIC_FORWARD_VIEW, JSON.toJSONString(dto));
    }

    public void sendConnectionLink(Connection conn, int endUserId){
        KafkaBaseDto dto = new KafkaBaseDto();
        dto.setEvent_time(DateUtils.dateToShortTime(new Date()));
        dto.setEvent(CONNECTION_CHANGE);
        dto.setPosition_id(conn.getPosition_id());
        JobPositionDO position = positionDao.getJobPositionById(conn.getPosition_id());
        if(position != null){
            dto.setCompany_id(position.getCompanyId());
        }
        dto.setUser_id(endUserId);
        sendMessage(Constant.KAFKA_TOPIC_FORWARD_VIEW, JSON.toJSONString(dto));
    }

    public void sendEmployeeCertification(UserEmployeeDO employeeDO){
        List<Integer> userIds = new ArrayList<>();
        userIds.add(employeeDO.getSysuserId());
        KafkaBindDto dto = new KafkaBindDto();
        dto.setEvent_time(employeeDO.getBindingTime());
        dto.setEvent(EMPLOYEE_CERTIFICATION);
        dto.setCompany_id(employeeDO.getCompanyId());
        dto.setUser_id(userIds);
        sendMessage(Constant.KAFKA_TOPIC_EMPLOYEE_CERTIFICATION, JSON.toJSONString(dto));
    }

    public void sendEmployeeCertification(List<UserEmployeeDO> employeeDOs){
        if(employeeDOs == null || employeeDOs.size() == 0){
            return;
        }
        KafkaBindDto dto = new KafkaBindDto();
        long current = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date(current));
        dto.setEvent_time(currentTime);
        dto.setEvent(EMPLOYEE_CERTIFICATION);
        dto.setCompany_id(employeeDOs.get(0).getCompanyId());
        List<Integer> userIds = employeeDOs.stream().map(UserEmployeeDO::getSysuserId).distinct().collect(Collectors.toList());
        dto.setUser_id(userIds);
        sendMessage(Constant.KAFKA_TOPIC_EMPLOYEE_CERTIFICATION, JSON.toJSONString(dto));
    }

    public void sendRadarSwitchToKafka(byte switchState, int companyId) {
        KafkaSwitchDto switchDto = new KafkaSwitchDto();
        long current = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = sdf.format(new Date(current));
        switchDto.setCompany_id(companyId);
        switchDto.setEvent(RADAR_BUTTON_STATUS);
        switchDto.setEvent_time(currentTime);
        switchDto.setStatus(switchState);
        sendMessage(Constant.KAFKA_TOPIC_RADAR_STATUS, JSON.toJSONString(switchDto));
    }

    public void sendUserClaimToKafka(int userId,  int positionId) {
        logger.info("KafkaSender sendUserClaimToKafka");
        logger.info("KafkaSender sendUserClaimToKafka userId:{}, positionId:{}", userId, positionId);
        KafkaSwitchDto switchDto = new KafkaSwitchDto();
        long current = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        KafkaApplyPojo kafkaApplyPojo = new KafkaApplyPojo();
        kafkaApplyPojo.setApplied(1);
        JobPositionDO position = positionDao.getJobPositionById(positionId);
        if(position != null){
            kafkaApplyPojo.setCompany_id(position.getCompanyId());
        }
        kafkaApplyPojo.setEvent("application");
        kafkaApplyPojo.setEvent_time(sdf.format(new Date(current)));
        kafkaApplyPojo.setPosition_id(positionId);
        kafkaApplyPojo.setUser_id(userId);
        logger.info("KafkaSender sendUserClaimToKafka kafkaApplyPojo:{}", JSONObject.toJSONString(kafkaApplyPojo));
        sendMessage(Constant.KAFKA_TOPIC_APPLICATION, JSON.toJSONString(kafkaApplyPojo));
    }


    public void sendUserImKafkaMsg(KafkaUserImDto dto) {
        sendMessage(Constant.KAFKA_TOPIC_IM_TPL_MSG, JSON.toJSONString(dto));
    }

}
