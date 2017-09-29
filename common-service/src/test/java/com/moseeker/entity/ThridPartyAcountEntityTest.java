package com.moseeker.entity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jack on 27/09/2017.
 */
public class ThridPartyAcountEntityTest {

    @Test
    public void lastIndexOf() throws Exception {
        String area = "北京市";
        String area1 = "北京市北京";

        assertEquals(true, area.lastIndexOf("市"));
        assertEquals(false, area1.lastIndexOf("市"));
    }
}