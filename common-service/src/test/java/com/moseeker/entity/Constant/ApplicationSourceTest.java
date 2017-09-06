package com.moseeker.entity.Constant;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jack on 20/08/2017.
 */
public class ApplicationSourceTest {

    @Test
    public void exist() throws Exception {
        assertEquals(true, ApplicationSource.ENTERPRISE.exist(2));
        assertEquals(false, ApplicationSource.ENTERPRISE.exist(1));
        assertEquals(true, ApplicationSource.ENTERPRISE.exist(3));

        assertEquals(true, ApplicationSource.ALIPAY.exist(64));
        assertEquals(false, ApplicationSource.ALIPAY.exist(63));
        assertEquals(true, ApplicationSource.ALIPAY.exist(65));

        assertEquals(true, ApplicationSource.DELEGATE.exist(256));
        assertEquals(true, ApplicationSource.DELEGATE.exist(257));
        assertEquals(false, ApplicationSource.DELEGATE.exist(1));

        assertEquals(false, ApplicationSource.GATHER.exist(1));
        assertEquals(true, ApplicationSource.GATHER.exist(4));
        assertEquals(true, ApplicationSource.GATHER.exist(5));

    }

    @Test
    public void andSource() throws Exception {
        assertEquals(3, ApplicationSource.ENTERPRISE.andSource(1));
        assertEquals(3, ApplicationSource.ENTERPRISE.andSource(1));
        assertEquals(3, ApplicationSource.ENTERPRISE.andSource(ApplicationSource.PC));
    }
}