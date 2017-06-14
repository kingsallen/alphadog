package com.moseeker.profile.thrift;

import java.util.List;

import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.thrift.gen.common.struct.BIZException;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.profile.service.impl.ProfileCompletenessImpl;
import com.moseeker.profile.service.impl.ProfileWorkExpService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.WorkExpServices.Iface;
import com.moseeker.thrift.gen.profile.struct.WorkExp;

@Service
public class ProfileWorkExpServicesImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(ProfileWorkExpServicesImpl.class);

    @Autowired
    private ProfileWorkExpService service;

    @Override
    public Response getResources(CommonQuery query) throws TException {
        try {
            return service.getResources(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response getResource(CommonQuery query) throws TException {
        try {
            return service.getResource(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response postResource(WorkExp struct) throws TException {
        try {
            return service.postResource(struct);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response putResource(WorkExp struct) throws TException {
        try {
            return service.putResource(struct);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response postResources(List<WorkExp> structs) throws TException {
        try {
            return service.postResources(structs);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response putResources(List<WorkExp> structs) throws TException {
        try {
            return service.putResources(structs);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response delResources(List<WorkExp> structs) throws TException {
        try {
            return service.delResources(structs);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response delResource(WorkExp struct) throws TException {
        try {
            return service.delResource(struct);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response getPagination(CommonQuery query) throws TException {
        try {
            return service.getPagination(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }
}
