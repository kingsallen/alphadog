package com.moseeker.servicemanager.web.controller.referral.tools;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: jack
 * @Date: 2018/9/4
 */
public class ProfileDocCheckToolTest {



    @Test
    public void checkFileLength() throws Exception {
        assertEquals(true, ProfileDocCheckTool.checkFileLength(1024));
        assertEquals(false, ProfileDocCheckTool.checkFileLength(1024*1024*3));
    }

    @Test
    public void checkFileLength1() throws Exception {
        assertEquals(false, ProfileDocCheckTool.checkFileLength(1024, 1000l));
        assertEquals(true, ProfileDocCheckTool.checkFileLength(1024*1024*3, 1014*1024*1024l));
    }

    @Test
    public void checkFileName() throws Exception {
        assertEquals(true, ProfileDocCheckTool.checkFileName("wjf.doc"));
        assertEquals(true, ProfileDocCheckTool.checkFileName("wjf.pdF"));
        assertEquals(false, ProfileDocCheckTool.checkFileName("wjf.doc1"));
    }

    @Test
    public void checkFileName1() throws Exception {

        List<String> supportFiles = new ArrayList<>();
        supportFiles.add(".hello");
        assertEquals(false, ProfileDocCheckTool.checkFileName("wjf.doc", supportFiles));
        assertEquals(false, ProfileDocCheckTool.checkFileName("wjf.pdF", supportFiles));
        assertEquals(true, ProfileDocCheckTool.checkFileName("wjf.hello", supportFiles));
    }

}