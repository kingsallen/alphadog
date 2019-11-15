package com.moseeker.profile.thrift;

import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.profile.service.impl.ProfileSkillService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.SkillServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Skill;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileSkillServicesImpl implements Iface {

    @Autowired
    private ProfileSkillService service;

    @Override
    public Response postResources(List<Skill> structs) throws TException {
        try {
            return service.postResources(structs);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response putResources(List<Skill> structs) throws TException {
        try {
            return service.putResources(structs);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response delResources(List<Skill> structs) throws TException {
        try {
            return service.delResources(structs);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response postResource(Skill struct) throws TException {
        try {
            return service.postResource(struct);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response putResource(Skill struct) throws TException {
        try {
            return service.putResource(struct);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response delResource(Skill struct) throws TException {
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
