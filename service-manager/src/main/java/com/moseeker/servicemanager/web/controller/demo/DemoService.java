package com.moseeker.servicemanager.web.controller.demo;

import com.moseeker.consistencysuport.common.MessageRepository;
import com.moseeker.consistencysuport.producer.ProducerEntry;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.demo1.service.Demo1ThriftService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jack on 2018/4/19.
 */
@Component
public class DemoService {
    Demo1ThriftService.Iface demoService = ServiceManager.SERVICEMANAGER
            .getService(Demo1ThriftService.Iface.class);

    @Autowired
    MessageRepository messageRepository;

    @ProducerEntry(name = "test", index = 0)
    public String test(String messageId, int id) throws TException {
        return demoService.comsumerTest(messageId, id);
    }
}
