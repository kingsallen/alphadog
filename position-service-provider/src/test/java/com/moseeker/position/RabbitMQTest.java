package com.moseeker.position;

import com.alibaba.fastjson.JSON;
import com.moseeker.common.constants.PositionSyncVerify;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class RabbitMQTest {

    @Test
    public void publish() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

//        channel.queueDeclare(PositionSyncVerify.MOBILE_VERIFY_QUEUE, false, false, false, null);
        Map<String,Object> map=new HashMap<>();
        map.put("accountId",574);
        map.put("channel",3);
        map.put("mobile",110);
        map.put("positionId",1910844);

        String message = JSON.toJSONString(map);
        channel.basicPublish(PositionSyncVerify.MOBILE_VERIFY_EXCHANGE, PositionSyncVerify.MOBILE_VERIFY_ROUTING_KEY, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
