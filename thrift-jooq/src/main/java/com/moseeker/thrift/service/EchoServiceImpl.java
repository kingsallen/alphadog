package com.moseeker.thrift.service;

import org.apache.thrift.TException;

/**
 * Created by zzh on 16/3/31.
 */
public class EchoServiceImpl implements com.moseeker.thrift.gen.echo.EchoService.Iface{

    @Override
    public String echo(String msg) throws TException {
        return "hello " + msg;
    }
}
