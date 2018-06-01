package com.moseeker.demo1.userinterface;

import com.moseeker.demo1.service.Demo1Service;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.demo1.service.Demo1ThriftService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jack on 2018/4/18.
 */
@Service
public class Demo1ThriftServiceImpl implements Demo1ThriftService.Iface {

    @Autowired
    Demo1Service demo1Service;

    @Override
    public String comsumerTest(String messageId, int id) throws BIZException, TException {
        return demo1Service.comsumerTest(messageId, id);
    }
}
