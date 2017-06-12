package com.moseeker.useraccounts.service.impl;

import com.moseeker.common.util.AopTargetUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.foundation.wordpress.service.WordpressService;
import com.moseeker.thrift.gen.foundation.wordpress.struct.NewsletterData;
import com.moseeker.thrift.gen.foundation.wordpress.struct.NewsletterForm;
import com.moseeker.useraccounts.config.AppConfig;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Created by lucky8987 on 17/5/17.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class UserCommonServiceTest {

    @Autowired
    private UserCommonService service;

    @Mock
    private WordpressService.Iface wordpressService;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(service), "wordpressService", wordpressService);
        NewsletterData newsletterData = new NewsletterData();
        newsletterData.setTitle("仟寻招聘新版本");
        newsletterData.setUrl("http://www.baidu.com");
        newsletterData.setVersion("gamma0.9");
        Mockito.when(wordpressService.getNewsletter(Mockito.any())).thenReturn(newsletterData);
    }

    //@Test
    public void newsletter() throws Exception {
        NewsletterForm form = new NewsletterForm();
        form.setAccount_id(1122611);
        Response response = service.newsletter(form);
        System.out.println(response);
    }

}