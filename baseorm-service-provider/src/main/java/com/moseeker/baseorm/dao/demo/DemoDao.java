package com.moseeker.baseorm.dao.demo;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.dao.demo.DemoDao;
import com.moseeker.baseorm.db.test.tables.Demo;
import com.moseeker.baseorm.db.test.tables.records.DemoRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.CommonUpdate;
import com.moseeker.thrift.gen.common.struct.Condition;
import com.moseeker.thrift.gen.demo.service.DemoThriftService;
import com.moseeker.thrift.gen.demo.struct.DemoStruct;
import org.apache.thrift.TException;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangdi on 2017/4/11.
 */
@Service
public class DemoDao extends JooqCrudImpl<DemoStruct, DemoRecord>{

    public DemoDao() {
        super(Demo.DEMO, DemoStruct.class);
    }
}
