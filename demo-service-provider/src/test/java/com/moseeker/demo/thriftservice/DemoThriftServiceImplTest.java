package com.moseeker.demo.thriftservice;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.demo.service.DemoThriftService;
import org.apache.thrift.TException;
import org.junit.Test;

/**
 * Created by jack on 13/04/2017.
 */
public class DemoThriftServiceImplTest {

    DemoThriftService.Iface demoService = ServiceManager.SERVICEMANAGER.getService(DemoThriftService.Iface.class);

    @Test
    public void testGetData() {
        CommonQuery query = new CommonQuery();
        try {
            demoService.getData(query);
        } catch (TException e) {
            e.printStackTrace();
        }
    }
}