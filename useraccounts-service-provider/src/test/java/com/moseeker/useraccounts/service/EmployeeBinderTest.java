package com.moseeker.useraccounts.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.useraccounts.config.AppConfig;
import com.moseeker.useraccounts.service.impl.EmployeeBindByCustomfield;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class EmployeeBinderTest {

    @Autowired
    EmployeeBindByCustomfield employeeBindByCustomfield;

    @Test
    public void doneBind() throws Exception {
        UserEmployeeDO useremployee = JSON.parseObject("{id:0,employeeid:18917124545,companyId:248355,roleId:0,wxuserId:8166,sex:0.0,cname:'陈雨婷',isAdmin:0,status:0,mobile:18917124545,award:0,bindingTime:'2018-08-29 11:55:48',email:'coffeyz@126.com',activation:0.0,activationCode:'f30c68fe2e5c951468f7fb8e304cda4afee5d9ca',disable:0.0,createTime:'2018-08-29 11:55:48',authLevel:0.0,loginCount:0.0,source:0.0,hrWxuserId:0,isRpSent:0,sysuserId:2192148,positionId:0,sectionId:0,emailIsvalid:0,authMethod:0,customFieldValues:[{\"47\":[\"主题里就会里芦荟胶\"]}]}",UserEmployeeDO.class);
        employeeBindByCustomfield.doneBind(useremployee,1);

    }

}