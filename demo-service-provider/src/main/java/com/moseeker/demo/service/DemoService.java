package com.moseeker.demo.service;

import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Update;
import com.moseeker.thrift.gen.demo.struct.DemoStruct;
import org.apache.thrift.TException;

/**
 * Created by moseeker on 2017/4/11.
 */
public interface    DemoService {

    String getData(Query query) throws TException;

    String postData(DemoStruct data) throws TException;

    String putData(Update data) throws TException;

    String deleteData(Condition condition) throws TException;
}
