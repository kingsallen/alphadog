//package com.moseeker.useraccounts.service.impl;
//
//import com.moseeker.rpccenter.client.ServiceManager;
//import com.moseeker.thrift.gen.dao.struct.userdb.UserSearchConditionDO;
//import com.moseeker.thrift.gen.useraccounts.service.UserQxService;
//import com.moseeker.thrift.gen.useraccounts.struct.UserSearchConditionListVO;
//import com.moseeker.thrift.gen.useraccounts.struct.UserSearchConditionVO;
//import org.apache.thrift.TException;
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * Created by lucky8987 on 17/4/17.
// */
//public class UserSearchConditionTest {
//
//    private UserQxService.Iface service = null;
//
//    @Before
//    public void init(){
//        service = ServiceManager.SERVICEMANAGER.getService(UserQxService.Iface.class);
//    }
//
//
//    @Test
//    public void postSearchConditionTest() {
//        UserSearchConditionDO entity = new UserSearchConditionDO();
//        entity.setUserId(1122611);
//        entity.setCityName("[\"上海\", \"深圳\"]");
//        entity.setKeywords("[\"java\", \"python\"]");
//        entity.setName("我的备用筛选项1");
//        entity.setSalaryBottom(2000);
//        entity.setSalaryTop(20000);
//        entity.setIndustry("互联网");
//        try {
//            UserSearchConditionVO result = service.postUserSearchCondition(entity);
//            System.out.println(result);
//        } catch (TException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void searchConditionListTest() {
//        try {
//            UserSearchConditionListVO list = service.userSearchConditionList(1122611);
//            System.out.println(list);
//        } catch (TException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void delSearchConditionTest() {
//        try {
//            UserSearchConditionVO result = service.delUserSearchCondition(1122611, 1);
//            System.out.println(result);
//        } catch (TException e) {
//            e.printStackTrace();
//        }
//    }
//}
