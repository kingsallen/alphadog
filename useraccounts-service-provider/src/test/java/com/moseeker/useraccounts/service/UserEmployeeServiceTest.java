package com.moseeker.useraccounts.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.entity.UserWxEntity;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeVOPageVO;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.service.impl.UserEmployeeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zztaiwll on 18/4/27.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserEmployeeServiceTest {
    @Autowired
    private UserEmployeeServiceImpl userEmployeeService;

    @Autowired
    private UserWxEntity userWxEntity;

    @Test
    public void userEmployeetest(){
        int companyId=39978;
        int page=1;
        int pageSize=20;
        String email="1";
        UserEmployeeVOPageVO userEmployeeVOPageVO=userEmployeeService.getUserEmployeeEmailValidate(companyId,email,page,pageSize);
        System.out.println(JSON.toJSONString(userEmployeeVOPageVO));
    }


    @Test
    public void UserEmployeeIdTest(){
        List<Integer> idList=new ArrayList<>();
        idList.add(43687);
        idList.add(782672);
        idList.add(782675);
        idList.add(782676);
        idList.add(880957);
        idList.add(880958);
        List<UserEmployeeDO> list=userWxEntity.getForWordEmployeeInfo(idList);
        System.out.println(JSON.toJSONString(list));
    }
}
