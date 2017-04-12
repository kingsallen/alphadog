package com.moseeker.demo.service.impl;

import com.moseeker.baseorm.dao.demo.DemoDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.demo.service.DemoService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.CommonUpdate;
import com.moseeker.thrift.gen.common.struct.Condition;
import com.moseeker.thrift.gen.demo.struct.DemoStruct;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by moseeker on 2017/4/11.
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    DemoDao demoDao;

    //此处我们只处理我们知道的异常，其它Runtime异常都直接抛给上面统一处理
    @Override
    public String getData(CommonQuery query) throws TException {
        DemoStruct demoStruct = demoDao.getData(query);
        return ResponseUtils.successStructJson(demoStruct);
    }

    @Override
    public String postData(Map<String, String> data) throws TException {
        try {
            int id = demoDao.add(data);
            if (id > 0) {
                return ResponseUtils.successJson();
            }
            return ResponseUtils.failJson(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
        }catch (Exception e){
            throw new TException(e);
        }
    }

    @Override
    public String putData(CommonUpdate data) throws TException {
        int success = demoDao.update(data);
        if(success>0){
            return ResponseUtils.successJson();
        }
        return ResponseUtils.failJson(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
    }

    @Override
    public String deleteData(Condition condition) throws TException {
        int success = demoDao.delete(condition);
        if(success>0){
            return ResponseUtils.successJson();
        }
        return ResponseUtils.failJson(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
    }
}
