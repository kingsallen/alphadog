package com.moseeker.common.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Author: jack
 * @Date: 2018/10/11
 */
public class BonusToolsTest {
    @Test
    public void convertToBonus() throws Exception {
        assertEquals("1.01", BonusTools.convertToBonus(1.01));
        assertEquals("1.1", BonusTools.convertToBonus(1.1));
        assertEquals("1", BonusTools.convertToBonus(1));
        assertEquals("100", BonusTools.convertToBonus(100));
    }

}