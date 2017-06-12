package com.moseeker.dict.service.impl;

import com.moseeker.dict.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by lucky8987 on 17/5/9.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class CityServicesTest {

//    @Autowired
    private CityServices services;

    //@Test
    public void getResources() throws Exception {
        Response response = services.getResources(new CommonQuery());
        System.out.println(response);
    }

    //@Test
//    @Transactional(readOnly = true)
    public void getCitiesResponse() throws Exception {
        Response response = services.getCitiesResponse(true, 1);
        System.out.println(response);
    }

    //@Test
    public void getAllCities() throws Exception {
        Response response = services.getAllCities(1);
        System.out.println(response);
    }

    //@Test
    public void getCitiesById() throws Exception {
        Response response = services.getCitiesById(2767);
        System.out.println(response);
    }

    //@Test
    public void getCitiesResponseById() throws Exception {
        Response response = services.getCitiesResponseById(1);
        System.out.println(response);
    }

}