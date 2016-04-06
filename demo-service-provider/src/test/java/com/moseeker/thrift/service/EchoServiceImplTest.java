package com.moseeker.thrift.service;

import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by zzh on 16/4/5.
 */

public class EchoServiceImplTest extends Assert {

    EchoServiceImpl echoService = new EchoServiceImpl();

    @Test
    public void echo() throws TException {
        String msg = echoService.echo("world");
        assertEquals("hello world", msg);
    }

}
