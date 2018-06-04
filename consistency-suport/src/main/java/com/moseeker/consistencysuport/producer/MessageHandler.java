package com.moseeker.consistencysuport.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * Created by jack on 2018/4/20.
 */
@Component
public class MessageHandler {

    @Autowired
    ProducerManagerSpringProxy producerManagerSpringProxy;

    public void receiveMessage(byte[] message) {
        try {
            String str = new String(message, "utf-8");
            producerManagerSpringProxy.buildManager().handlerMessage(str);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
