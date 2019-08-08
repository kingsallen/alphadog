package com.moseeker.useraccounts.service.impl;

import org.junit.Test;
import static com.moseeker.useraccounts.service.impl.EmployeeBindByWorkWechat.matchCity ;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by huangxia on 2019/8/7.
 */
public class EmployeeBindByWorkWechatTest {
    @Test
    public void matchCityTest() throws Exception {
        assertEquals("北京",matchCity("北京", Arrays.asList("北京","上海")));
        assertEquals("上海",matchCity("上海", Arrays.asList("北京","上海")));
        assertEquals("北京",matchCity("北京市", Arrays.asList("北京","上海")));
        assertEquals("上海",matchCity("上海市", Arrays.asList("北京","上海")));
        assertEquals("北京市",matchCity("北京", Arrays.asList("北京市","上海市")));
        assertEquals("上海市",matchCity("上海", Arrays.asList("北京市","上海市")));

        assertEquals(null,matchCity("重庆", Arrays.asList("北京市","上海市")));

        assertEquals("上海市",matchCity("上海黄埔区人民广场", Arrays.asList("北京市","上海市")));
    }

}