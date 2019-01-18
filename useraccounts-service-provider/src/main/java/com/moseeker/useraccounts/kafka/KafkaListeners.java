package com.moseeker.useraccounts.kafka;

import com.moseeker.common.constants.Constant;
import com.moseeker.entity.ReferralEntity;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    private Logger logger = LoggerFactory.getLogger(KafkaListeners.class);

    @Autowired
    ReferralEntity referralEntity;

    @KafkaListener(id = Constant.KAFKA_GROUP_RADAR_TOPN, containerFactory = "kafkaListenerContainerFactory", topics  =  {"test1"})
    public void kafkaGroupRadar(ConsumerRecord<?,  ?> record)  {
        Object  message  =  record.value();
        logger.info( Constant.KAFKA_GROUP_RADAR_TOPN+":" + message);
        referralEntity.fetchEmployeeNetworkResource(message);
    }

}
