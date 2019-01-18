package com.moseeker.useraccounts.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.DateUtils;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.useraccounts.pojo.neo4j.Connection;
import com.moseeker.useraccounts.service.impl.pojos.KafkaBaseDto;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSender {

    Logger logger = LoggerFactory.getLogger(KafkaSender.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private JobPositionDao positionDao;

    private final static String SOCIAL_GRAPH_CHANGE = "social_graph_change";

    private final static String CONNECTION_CHANGE = "radar_link_game";

    public void sendMessage(String topic, String data){
        logger.info("topic:{}, data:{}", topic, data);
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


}
