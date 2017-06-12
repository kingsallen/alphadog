package com.moseeker.demo.thriftservice;

import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.util.query.Update;
import com.moseeker.demo.service.DemoService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.CommonUpdate;
import com.moseeker.thrift.gen.common.struct.Condition;
import com.moseeker.thrift.gen.demo.service.DemoThriftService;
import com.moseeker.thrift.gen.demo.struct.DemoStruct;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by moseeker on 2017/4/11.
 */
@Service
public class DemoThriftServiceImpl implements DemoThriftService.Iface{

    @Autowired
    DemoService demoService;

    @Override
    public String getData(CommonQuery query) throws TException {
        return demoService.getData(QueryConvert.commonQueryConvertToQuery(query));
    }

    @Override
    public String postData(DemoStruct data) throws TException {
        return demoService.postData(data);
    }

    @Override
    public String putData(CommonUpdate data) throws TException {
        Update.UpdateBuilder updateBuilder = new Update.UpdateBuilder();
        return demoService.putData(updateBuilder.buildUpdate());
    }

    @Override
    public String deleteData(Condition condition) throws TException {
        return demoService.deleteData(new com.moseeker.common.util.query.Condition("id", 1));
    }
}
