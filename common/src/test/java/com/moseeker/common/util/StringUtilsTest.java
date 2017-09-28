package com.moseeker.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jack on 12/07/2017.
 */
public class StringUtilsTest {
    @Test
    public void isEmptyObject() throws Exception {

        assertEquals(true, StringUtils.isEmptyObject(null));
    }

    @Test
    public void lastContain() {

        String context = "北京市";
        String context1 = "北京市北京北";
        String context2 = "北京市北";
        String context3 = "北京市市";
        String c = "市";

        assertEquals(true, StringUtils.lastContain(context, c));
        assertEquals(false, StringUtils.lastContain(context1, c));
        assertEquals(false, StringUtils.lastContain(context2, c));
        assertEquals(true, StringUtils.lastContain(context3, c));
    }

    @Test
    public void subStringTest() {
        String context = "北京市";
        assertEquals("北京", context.substring(0, context.length()-1));
    }
}