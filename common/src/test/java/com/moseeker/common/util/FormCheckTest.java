package com.moseeker.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jack on 01/09/2017.
 */
public class FormCheckTest {
    @Test
    public void isEmail() throws Exception {
        String email = "wjf**@moseeker.com";
        assertEquals(false, FormCheck.isEmail(email));

        String email1 = "wjf@moseeker.com";
        assertEquals(true, FormCheck.isEmail(email1));
    }

    @Test
    public void testIsChineseAndCharacter() throws Exception {
        String str = "测试11";

        String str1 = "wjf wjf";

        String str2 = "测试华为";

        assertEquals(false, FormCheck.isCharacter(str));
        assertEquals(true, FormCheck.isCharacter(str1));
        assertEquals(true, FormCheck.isCharacter(str2));

    }
}