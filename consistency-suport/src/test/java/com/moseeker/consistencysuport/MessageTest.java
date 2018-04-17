package com.moseeker.consistencysuport;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by jack on 2018/4/17.
 */
public class MessageTest {

    @Test
    public void jsonTest() {
        Message message = new Message();
        message.setBusinessName("businessName");
        message.setMessageName("messageName");
        message.setMessageType(MessageType.HeartBeat);

        System.out.println(JSONObject.toJSONString(message));
    }

    @Test
    public void jsonToMessage() {
        String jsonStr = "{\"businessName\":\"businessName\",\"messageName\":\"messageName\",\"messageType\":\"HeartBeat\"}";
        Message message = JSONObject.parseObject(jsonStr, Message.class);
        assertEquals("businessName", message.getBusinessName());
        assertEquals("messageName", message.getMessageName());
        assertEquals(MessageType.HeartBeat, message.getMessageType());
    }
}