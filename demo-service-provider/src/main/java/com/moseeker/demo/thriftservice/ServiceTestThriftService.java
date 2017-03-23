package com.moseeker.demo.thriftservice;

import com.moseeker.thrift.gen.demo.service.ServiceTest;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jack on 23/03/2017.
 */
@Service
public class ServiceTestThriftService implements ServiceTest.Iface {

    @Autowired
    com.moseeker.demo.service.ServiceTest serviceTest;

    @Override
    public void serviceTest() throws TException {
        serviceTest.testTransactionRolback();
    }
}
