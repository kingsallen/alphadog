//package com.moseeker.useraccounts.service.impl;
//
//import com.moseeker.rpccenter.client.ServiceManager;
//import com.moseeker.thrift.gen.useraccounts.service.UserQxService;
//import com.moseeker.thrift.gen.useraccounts.struct.UserCollectPositionListVO;
//import com.moseeker.thrift.gen.useraccounts.struct.UserCollectPositionVO;
//import org.apache.thrift.TException;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * Created by lucky8987 on 17/4/17.
// */
//public class UserCollectPositionTest {
//
//    private UserQxService.Iface service = null;
//
//    @Before
//    public void init(){
//        service = ServiceManager.SERVICEMANAGER.getService(UserQxService.Iface.class);
//    }
//
//    //@Test
//    public void getUserCollectPositionTest() {
//        try {
//            UserCollectPositionVO ucpv = service.getUserCollectPosition(1122611, 380);
//            System.out.println(ucpv);
//        } catch (TException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //@Test
//    public void getUserCollectPositionsTest() {
//        try {
//            UserCollectPositionListVO collectPositions = service.getUserCollectPositions(3870);
//            System.out.println(collectPositions);
//        } catch (TException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //@Test
//    public void postUserCollectPositionTest() {
//        try {
//            UserCollectPositionVO ucpv = service.postUserCollectPosition(1122611, 380);
//            System.out.println(ucpv);
//        } catch (TException e) {
//            e.printStackTrace();
//        }
//    }
//
//    //@Test
//    public void delUserCollectPositionTest() {
//        try {
//            UserCollectPositionVO ucpv = service.delUserCollectPosition(1122611, 380);
//            System.out.println(ucpv);
//        } catch (TException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
