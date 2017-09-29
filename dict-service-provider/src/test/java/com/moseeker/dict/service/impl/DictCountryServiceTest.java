package com.moseeker.dict.service.impl;

import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.util.query.Query;
import com.moseeker.dict.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
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
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DictCountryServiceTest {

    @Autowired
    private DictCountryService service;

    @Test
    public void getDictCountry() throws Exception {
        CommonQuery query=new CommonQuery();
        Query query1= QueryConvert.commonQueryConvertToQuery(query);
        Response response = service.getDictCountry(query1);
        System.out.println(response);
    }

}