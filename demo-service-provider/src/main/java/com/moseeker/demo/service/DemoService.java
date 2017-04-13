package com.moseeker.demo.service;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.CommonUpdate;
import com.moseeker.thrift.gen.common.struct.Condition;
import com.moseeker.thrift.gen.demo.struct.DemoStruct;
import org.apache.thrift.TException;

import java.util.Map;

/**
 * Created by moseeker on 2017/4/11.
 */
public interface DemoService {
    String getData(CommonQuery query) throws TException;

    String postData(DemoStruct data) throws TException;

    String putData(CommonUpdate data) throws TException;

    String deleteData(Condition condition) throws TException;
}
