package com.moseeker.useraccounts.service.impl;

import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.useraccounts.service.UseraccountsServices;
import com.moseeker.thrift.gen.useraccounts.struct.UserCollectPositionVO;
import com.moseeker.thrift.gen.useraccounts.struct.UserSearchConditionVO;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by lucky8987 on 17/4/17.
 */
public class UserCollectPositionTest {

    private UseraccountsServices.Iface service = null;

    @Before
    public void init(){
        service = ServiceManager.SERVICEMANAGER.getService(UseraccountsServices.Iface.class);
    }

    @Test
    public void getUserCollectPositionTest() {
        try {
            UserCollectPositionVO ucpv = service.getUserCollectPosition(1122611, 380);
            System.out.println(ucpv);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postUserCollectPositionTest() {
        try {
            UserCollectPositionVO ucpv = service.postUserCollectPosition(1122611, 380);
            System.out.println(ucpv);
        } catch (TException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void delUserCollectPositionTest() {
        try {
            UserCollectPositionVO ucpv = service.delUserCollectPosition(1122611, 380);
            System.out.println(ucpv);
        } catch (TException e) {
            e.printStackTrace();
        }
    }

}
