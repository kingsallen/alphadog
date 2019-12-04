package com.moseeker.profile.thrift;

import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.common.util.StringUtils;
import com.moseeker.profile.service.impl.ProfileBasicService;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.BasicServices.Iface;
import com.moseeker.thrift.gen.profile.struct.Basic;
import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileBasicServicesImpl implements Iface {

    @Autowired
    private ProfileBasicService service;

    @Override
    public Response getResources(CommonQuery query) throws TException {
        try {
            List<Basic> datas = service.getResources(QueryConvert.commonQueryConvertToQuery(query));
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
    public Response postResources(List<Basic> structs) throws TException {
        try {
            List<Basic> result = service.postResources(structs);
            return ResponseUtils.success("1");
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response putResources(List<Basic> structs) throws TException {
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
    public Response delResources(List<Basic> structs) throws TException {
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
    public Response postResource(Basic struct) throws TException {
        try {
            String name = struct.getName();
            if (StringUtils.isNotNullOrEmpty(name) && name.length() > 100) {
                String message = ConstantErrorCodeMessage.VALIDATE_FAILED;
                message = message.replace("{MESSAGE}", "不能超过100个英文字母或者50个汉字");
                return ResponseUtils.fail(message);
            }
            Basic result = service.postResource(struct);
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
    public Response putResource(Basic struct) throws TException {
        try {
            String name=struct.getName();
            if(StringUtils.isNotNullOrEmpty(name)&&name.length()>100){
                String message=ConstantErrorCodeMessage.VALIDATE_FAILED;
                message.replace("{MESSAGE}","不能超过100个英文字母或者50个汉字");
                return ResponseUtils.fail(message);
            }
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
    public Response delResource(Basic struct) throws TException {
        try {
            int result = service.delResource(struct);
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
            Basic data = service.getResource(QueryConvert.commonQueryConvertToQuery(query));
            if (data != null) {
                return ResponseUtils.success(data);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response reCalculateBasicCompleteness(int userId) throws TException {
        try {
            return ResponseUtils.success("1");
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }
}
