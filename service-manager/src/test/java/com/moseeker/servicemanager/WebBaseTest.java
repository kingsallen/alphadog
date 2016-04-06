package com.moseeker.servicemanager;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by zzh on 16/4/6.
 */
@WebAppConfiguration(value = "WebContent")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:controllers.xml")
public class WebBaseTest {

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;


    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }
}
