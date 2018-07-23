package com.moseeker.dict.thrift;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.dict.service.impl.CollegeServices;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dict.service.CollegeServices.Iface;

@Service
public class CollegeServicesImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CollegeServices service;
    
    @Override
    public Response getResources(CommonQuery query) throws TException {
        return service.getResources(query);
    }

    @Override
    public Response getCollegeByDomestic() throws TException {
        return service.getCollegeByDomestic();
    }

    @Override
    public Response getCollegeByAbroad(int countryCode) throws TException {
        return service.getCollegeByAbroad(countryCode);
    }

}

