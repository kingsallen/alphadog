package com.moseeker.dict.service.impl;

import com.moseeker.dict.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by lucky8987 on 17/5/10.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class PositionServiceTest {

//    @Autowired
    private PositionService service;

    //@Test
    public void getPositionsByCode() throws Exception {
        Response response = service.getPositionsByCode("110000");
        System.out.println(response);
    }

}