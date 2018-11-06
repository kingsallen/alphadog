package com.moseeker.useraccounts.service.impl;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.useraccounts.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;

/**
 * Created by lucky8987 on 17/5/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserEmployeeServiceImplTest {

    @Autowired
    private UserEmployeeServiceImpl service;

    //@Test
    public void getUserEmployee() throws Exception {
        CommonQuery query = new CommonQuery();
        query.setEqualFilter(new HashMap<>());
        query.getEqualFilter().put("company_id", "39978");
        Response userEmployee = service.getUserEmployee(query);
        System.out.println(userEmployee);
    }

    //@Test
    public void getUserEmployees() throws Exception {

    }

    @Test
    public void delUserEmployee() throws Exception {
        CommonQuery commonQuery = new CommonQuery();
        commonQuery.setEqualFilter(new HashMap<>());
        commonQuery.getEqualFilter().put("company_id","3");
        commonQuery.getEqualFilter().put("custom_field","[1]");
        service.delUserEmployee(commonQuery);
    }

    //@Test
    public void postPutUserEmployeeBatch() throws Exception {

    }

    //@Test
    public void getResource() throws Exception {

    }

    //@Test
    public void getResources() throws Exception {

    }

    //@Test
    public void getResourceCount() throws Exception {

    }

    //@Test
    public void postResource() throws Exception {

    }

    //@Test
    public void postResources() throws Exception {

    }

    //@Test
    public void putResource() throws Exception {

    }

    //@Test
    public void putResources() throws Exception {

    }

    //@Test
    public void delResource() throws Exception {

    }


}