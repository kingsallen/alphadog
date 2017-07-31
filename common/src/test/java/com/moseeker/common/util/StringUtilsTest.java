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

}