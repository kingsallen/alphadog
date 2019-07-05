package com.moseeker.demo.thriftservice;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.demo.service.DemoThriftService;

/**
 * Created by jack on 13/04/2017.
 */
public class DemoThriftServiceImplTest {

    DemoThriftService.Iface demoService = ServiceManager.SERVICE_MANAGER.getService(DemoThriftService.Iface.class);

    ////@Test
    /*public void testGetData() {
        CommonQuery query = new CommonQuery();
        DemoStruct demoStruct = new DemoStruct();
        demoStruct.setId(1);
        demoStruct.setName("name");
        try {
            System.out.println(demoService.postData(demoStruct));
        } catch (TException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(demoService.getData(query));
        } catch (TException e) {
            e.printStackTrace();
        }

        try {
            CommonUpdate commonUpdate = new CommonUpdate();
            System.out.println(demoService.putData(commonUpdate));
        } catch (TException e) {
            e.printStackTrace();
        }
    }*/
}