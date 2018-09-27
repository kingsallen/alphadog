package com.moseeker.useraccounts.service.impl.biztools;

import com.moseeker.baseorm.db.hrdb.tables.records.HrHbConfigRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsRecord;
import org.junit.Test;

import static org.junit.Assert.*;

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

        assertEquals(false, HBBizTool.isOpen((byte)0));
        assertEquals(false, HBBizTool.isOpen((byte)1));
        assertEquals(false, HBBizTool.isOpen((byte)2));
        assertEquals(false, HBBizTool.isOpen((byte)3));
        assertEquals(true, HBBizTool.isOpen((byte)4));
        assertEquals(true, HBBizTool.isOpen((byte)5));
        assertEquals(true, HBBizTool.isOpen((byte)6));
        assertEquals(true, HBBizTool.isOpen((byte)7));
        assertEquals(true, HBBizTool.isOpen((byte)100));
        assertEquals(true, HBBizTool.isOpen((byte)-1));
        assertEquals(true, HBBizTool.isOpen((byte)101));
        assertEquals(false, HBBizTool.isOpen((byte)102));
    }
}