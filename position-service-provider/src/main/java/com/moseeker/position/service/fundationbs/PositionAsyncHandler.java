package com.moseeker.position.service.fundationbs;

import com.alibaba.fastjson.JSON;
import com.moseeker.position.pojo.JobPostionResponse;
import com.moseeker.position.thrift.PositionServicesImpl;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.struct.BatchHandlerJobPostion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.moseeker.position.constants.position.PositionAsyncConstant.POSITION_BATCH_HANDLE_EXCHANGE;
import static com.moseeker.position.constants.position.PositionAsyncConstant.POSITION_BATCH_HANDLE_REQUEST_QUEUE;
import static com.moseeker.position.constants.position.PositionAsyncConstant.POSITION_BATCH_HANDLE_RESPONSE_ROUTING_KEY;

@Component
public class PositionAsyncHandler {
    private Logger logger= LoggerFactory.getLogger(PositionAsyncHandler.class);

    @Autowired
    private PositionServicesImpl positionService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RabbitListener(queues = {POSITION_BATCH_HANDLE_REQUEST_QUEUE}, containerFactory = "rabbitListenerContainerFactoryAutoAck")
    @RabbitHandler
    public void asyncBatchHandlerJobPostion(Message message){
        String json="";

        try {
            json = new String(message.getBody(), "UTF-8");
            logger.info("async batch handle position receive json {}", json);

            BatchHandlerJobPostion batchHandlerJobPosition = JSON.parseObject(json,BatchHandlerJobPostion.class);
            Response response = positionService.saveAndSync(batchHandlerJobPosition);

            String jsonResponse = JSON.toJSONString(response);
            try {
                amqpTemplate.send(POSITION_BATCH_HANDLE_EXCHANGE, POSITION_BATCH_HANDLE_RESPONSE_ROUTING_KEY, MessageBuilder.withBody(jsonResponse.getBytes()).build());
            } catch (Exception e) {
                logger.error("send async batch handle position result failed {}", jsonResponse);
            }
        } catch (Exception e) {
            logger.error("async batch handle position error: "+e.getMessage()+"json: "+json,e);
        }
    }
}
