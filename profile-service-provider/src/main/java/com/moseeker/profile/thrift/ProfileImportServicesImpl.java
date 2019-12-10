package com.moseeker.profile.thrift;

import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.profile.service.impl.ProfileImportService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProfileImportServices.Iface;
import com.moseeker.thrift.gen.profile.struct.ProfileImport;
import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileImportServicesImpl implements Iface {

    @Autowired
    private ProfileImportService service;

    @Override
    public Response getResources(CommonQuery query) throws TException {
        try {
            List<ProfileImport> datas = service.getResources(QueryConvert.commonQueryConvertToQuery(query));
            if (datas != null && datas.size() > 0) {
                return ResponseUtils.success(datas);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response postResources(List<ProfileImport> structs) throws TException {
        try {
            List<ProfileImport> result = service.postResources(structs);
            return ResponseUtils.success("1");
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response putResources(List<ProfileImport> structs) throws TException {
        try {
            int[] result = service.putResources(structs);
            if (result != null && ArrayUtils.contains(result, 1)) {
                return ResponseUtils.success("1");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
            }
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response delResources(List<ProfileImport> structs) throws TException {
        try {
            int[] result = service.delResources(structs);
            if (result != null && ArrayUtils.contains(result, 1)) {
                return ResponseUtils.success("1");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
            }
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response postResource(ProfileImport struct) throws TException {
        try {
            ProfileImport result = service.postResource(struct);
            if (result != null) {
                return ResponseUtils.success(String.valueOf(result.getProfile_id()));
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
            }
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response putResource(ProfileImport struct) throws TException {
        try {
            int result = service.putResource(struct);
            if (result > 0) {
                return ResponseUtils.success("1");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
            }
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response delResource(ProfileImport struct) throws TException {
        try {
            int result = service.delResource(struct);
            if (result > 0) {
                return ResponseUtils.success("1");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
            }
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getPagination(CommonQuery query) throws TException {
        try {
            Pagination pagination = service.getPagination(QueryConvert.commonQueryConvertToQuery(query));
            return ResponseUtils.success(pagination);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getResource(CommonQuery query) throws TException {
        try {
            ProfileImport data = service.getResource(QueryConvert.commonQueryConvertToQuery(query));
            if (data != null) {
                return ResponseUtils.success(data);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }
}
