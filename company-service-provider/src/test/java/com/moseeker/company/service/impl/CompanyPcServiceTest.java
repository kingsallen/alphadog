package com.moseeker.company.service.impl;

import com.moseeker.company.config.AppConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * Created by zztaiwll on 17/8/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CompanyPcServiceTest {
    @Autowired
    private CompanyPcService companyPcService;
    @Test
    public void getJdCompanyInfoTest()throws Exception{
        Map<String,Object> result=companyPcService.getCompanyInfo(39978);
        System.out.println(result+"======");
    }
    @Test
    public void getNoJdCompanyInfoTest() throws Exception {
        Map<String,Object> result=companyPcService.getCompanyInfo(2878);
        System.out.println(result+"======");
    }
    @Test
    public void getJdSubCompanyInfoTest() throws Exception {
        Map<String,Object> result=companyPcService.getCompanyInfo(41305);
        System.out.println(result+"======");
    }
    @Test
    public void getNoJdSubCompanyInfoTest() throws Exception {
        Map<String,Object> result=companyPcService.getCompanyInfo(39785);
        System.out.println(result+"======");
    }
}
