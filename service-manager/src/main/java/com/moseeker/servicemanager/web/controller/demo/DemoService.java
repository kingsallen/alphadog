package com.moseeker.servicemanager.web.controller.demo;

import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.consistencysuport.producer.ProducerEntry;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import com.moseeker.thrift.gen.demo1.service.Demo1ThriftService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jack on 2018/4/19.
 */
@Component
public class DemoService {
    Demo1ThriftService.Iface demoService = ServiceManager.SERVICEMANAGER
            .getService(Demo1ThriftService.Iface.class);

    @Autowired
    private UserUserDao userUserDao;

    @Transactional
    @ProducerEntry(name = "test", index = 0)
    public String test(String messageId, int id) throws TException {
        UserUserDO userUserDO = new UserUserDO();
        userUserDO.setId(676237);
        userUserDO.setName("wjf3");
        //int a = 5/0;   //用于测试消息和当前业务操作是否在一个事务中
        userUserDao.updateData(userUserDO);
        return demoService.comsumerTest(messageId, id);
    }
}
