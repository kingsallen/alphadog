package com.moseeker.position.service.position.liepin;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.position.liepin.LiePinReceiverHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;

/**
 * @author cjm
 * @date 2018-06-06 17:23
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class LiepinReceiverTest {


    @Autowired
    LiePinReceiverHandler receiverHandler;

    @Test
    public void testEdit(){

    }

    @Test
    public void testDel(){

    }

    @Test
    public void testDownShelf() throws UnsupportedEncodingException {
        JSONObject liePinJsonObject = new JSONObject();
        liePinJsonObject.put("id", "77875");
        String requestStr = JSONObject.toJSONString(liePinJsonObject);
        Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
        receiverHandler.handlerPositionLiepinDownShelfOperation(requestMsg, null);
    }

    @Test
    public void testRePublish() throws UnsupportedEncodingException {
        JSONObject liePinJsonObject = new JSONObject();
        liePinJsonObject.put("id", "77875");
        String requestStr = JSONObject.toJSONString(liePinJsonObject);
        Message requestMsg = new Message(requestStr.getBytes("UTF-8"), null);
        receiverHandler.handlerPositionLiepinReSyncOperation(requestMsg, null);
    }

}
