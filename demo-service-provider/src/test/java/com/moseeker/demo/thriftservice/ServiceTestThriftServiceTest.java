package com.moseeker.demo.thriftservice;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.demo.service.ServiceTest;
import org.apache.thrift.TException;
import org.junit.Test;

/**
 * Created by jack on 23/03/2017.
 */
public class ServiceTestThriftServiceTest {

    ServiceTest.Iface service = ServiceManager.SERVICEMANAGER.getService(ServiceTest.Iface.class);

    @Test
    public void testServiceTest() {
        try {
            service.serviceTest();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

}