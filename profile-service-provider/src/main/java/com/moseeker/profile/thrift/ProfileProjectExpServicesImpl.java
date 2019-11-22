package com.moseeker.profile.thrift;

import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.profile.service.impl.ProfileProjectExpService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProjectExpServices.Iface;
import com.moseeker.thrift.gen.profile.struct.ProjectExp;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileProjectExpServicesImpl implements Iface {

    @Autowired
    private ProfileProjectExpService service;

    @Override
    public Response postResources(List<ProjectExp> structs) throws TException {
        try {
            return service.postResources(structs);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response putResources(List<ProjectExp> structs) throws TException {
        try {
            return service.putResources(structs);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response delResources(List<ProjectExp> structs) throws TException {
        try {
            return service.delResources(structs);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response postResource(ProjectExp struct) throws TException {
        try {
            return service.postResource(struct);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response putResource(ProjectExp struct) throws TException {
        try {
            return service.putResource(struct);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response delResource(ProjectExp struct) throws TException {
        try {
            return service.delResource(struct);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getResources(CommonQuery query) throws TException {
        try {
            return service.getResources(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getPagination(CommonQuery query) throws TException {
        try {
            return service.getPagination(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getResource(CommonQuery query) throws TException {
        try {
            return service.getResource(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }
}
