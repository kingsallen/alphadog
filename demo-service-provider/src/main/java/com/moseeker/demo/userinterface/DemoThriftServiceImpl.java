package com.moseeker.demo.userinterface;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.demo.application.DemoApplication;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.demo.service.DemoThriftService;
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
    DemoApplication demoService;


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