package com.moseeker.dict.service.impl;

import com.moseeker.dict.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by lucky8987 on 17/5/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DictConstantServiceTest {

    @Autowired
    private DictConstantService service;

    @Test
    public void getDictConstantJsonByParentCode() throws Exception {
        Response response = service.getDictConstantJsonByParentCode(Arrays.asList(3113, 9103, 9102));
        System.out.println(response);
    }

}