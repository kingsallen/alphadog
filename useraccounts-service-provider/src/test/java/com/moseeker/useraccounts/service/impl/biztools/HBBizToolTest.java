package com.moseeker.useraccounts.service.impl.biztools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @Author: jack
 * @Date: 2018/9/27
 */
public class HBBizToolTest {

    @Test
    public void getConfigName() throws Exception {

        assertEquals("员工认证红包", HBBizTool.getConfigName(0));
        assertEquals("推荐评价红包", HBBizTool.getConfigName(1));
        assertEquals("转发被点击红包", HBBizTool.getConfigName(2));
        assertEquals("转发被申请红包", HBBizTool.getConfigName(3));
        assertEquals("", HBBizTool.getConfigName(-1));
        assertEquals("", HBBizTool.getConfigName(4));
    }

    @Test
    public void isOpen() throws Exception {

        assertEquals(false, HBBizTool.isOpen(0));
        assertEquals(false, HBBizTool.isOpen(2));
        assertEquals(true, HBBizTool.isOpen(1));
    }
}