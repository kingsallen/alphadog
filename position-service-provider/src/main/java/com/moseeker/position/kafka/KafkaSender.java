package com.moseeker.position.kafka;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.DateUtils;
import com.moseeker.position.pojo.KafkaBindDto;
import com.moseeker.position.pojo.PositionStatus;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private JobPositionDao positionDao;

    private final static String SOCIAL_GRAPH_CHANGE = "position_status";

    public void sendMessage(String topic, String data){
        logger.info("kafkaMessage date:{}",data);
        kafkaTemplate.send(topic, data);
    }

    public void sendPositionStatus(int positionId, int status, int companyId){
        KafkaBindDto dto = new KafkaBindDto();
        dto.setEvent_time(DateUtils.dateToShortTime(new Date()));
        dto.setEvent(SOCIAL_GRAPH_CHANGE);
        List<PositionStatus> list = new ArrayList<>();
        PositionStatus position = new PositionStatus();
        position.setPosition_id(positionId);
        position.setStatus(status);
        list.add(position);
        dto.setData(list);
        dto.setCompany_id(companyId);
        sendMessage(Constant.KAFKA_TOPIC_POSITION_STATUS, JSON.toJSONString(dto));
    }



}
