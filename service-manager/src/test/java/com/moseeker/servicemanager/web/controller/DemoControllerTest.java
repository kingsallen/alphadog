package com.moseeker.servicemanager.web.controller;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.moseeker.servicemanager.web.WebBaseTest;

/**
 * Created by zzh on 16/4/6.
 */
public class DemoControllerTest extends WebBaseTest{

    @Test
    public void echo() throws Exception {

        mockMvc.perform(get("/demo").param("msg", "nihao"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
    }
}
