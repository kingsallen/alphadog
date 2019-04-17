package com.moseeker.entity;/**
 * Created by zztaiwll on 19/3/8.
 */

import com.alibaba.fastjson.JSON;
import com.moseeker.entity.pojo.company.HrOperationAllRecord;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @className RabbitMQOperationRecord
 * @Description TODO
 * @Author zztaiwll
 * @DATE 19/3/8 下午1:44
 **/
@Service
public class RabbitMQOperationRecord {
    @Autowired
    private AmqpTemplate amqpTemplate;
    private static String QUEUE="hr_opertor_all_record_queue";
    private static String EXCHANGE="hr_opertor_all_record_exchange";
    private static String ROUTEKEY="hr_opertor_all_record_routekey";

    public void sendMQForOperationRecord(HrOperationAllRecord data){
        String message= JSON.toJSONString(data);
        MessageProperties msp = new MessageProperties();
//        amqpTemplate.send(EXCHANGE, ROUTEKEY, MessageBuilder.withBody(message.getBytes()).andProperties(msp).build());
    }
    public void sendMQForOperationRecordList(List<HrOperationAllRecord> dataList){
        for(HrOperationAllRecord data: dataList){
            sendMQForOperationRecord(data);
        }
    }
}
