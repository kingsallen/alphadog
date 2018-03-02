package com.moseeker.demo.userinterface;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.util.query.Update;
import com.moseeker.demo.application.DemoApplicaiton;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.CommonUpdate;
import com.moseeker.thrift.gen.common.struct.Condition;
import com.moseeker.thrift.gen.demo.service.DemoThriftService;
import com.moseeker.thrift.gen.demo.struct.DemoStruct;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by moseeker on 2017/4/11.
 */
@Service
public class DemoThriftServiceImpl implements DemoThriftService.Iface{

    @Autowired
    DemoApplicaiton demoService;


    @Override
    public String viewApplication(int hrId, List<Integer> applicationIds) throws BIZException, TException {
        try {
            return demoService.viewApplication(hrId, applicationIds);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }
}
