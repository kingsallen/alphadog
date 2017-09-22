package com.moseeker.profile.thrift;

import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.Pagination;
import com.moseeker.profile.service.impl.ProfileAttachmentService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.AttachmentServices;
import com.moseeker.thrift.gen.profile.struct.Attachment;

import org.apache.commons.lang.ArrayUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProfileAttachmentServicesImpl implements AttachmentServices.Iface {

    Logger logger = LoggerFactory.getLogger(ProfileAttachmentServicesImpl.class);

    @Autowired
    private ProfileAttachmentService service;

    @Override
    public Response getResources(CommonQuery query) throws TException {
        try {
            List<Attachment> datas = service.getResources(QueryConvert.commonQueryConvertToQuery(query));
            if (datas != null && datas.size() > 0) {
                return ResponseUtils.success(datas);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                return ResponseUtils.fail(((BIZException) e).getCode(), e.getMessage());
            }
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response postResources(List<Attachment> structs) throws TException {
        try {
            List<Attachment> result = service.postResources(structs);
            return ResponseUtils.success("1");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                return ResponseUtils.fail(((BIZException) e).getCode(), e.getMessage());
            }
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response putResources(List<Attachment> structs) throws TException {
        try {
            int[] result = service.putResources(structs);
            if (result != null && ArrayUtils.contains(result, 1)) {
                return ResponseUtils.success("1");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                return ResponseUtils.fail(((BIZException) e).getCode(), e.getMessage());
            }
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response delResources(List<Attachment> structs) throws TException {
        try {
            int[] result = service.delResources(structs);
            if (result != null && ArrayUtils.contains(result, 1)) {
                return ResponseUtils.success("1");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DEL_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                return ResponseUtils.fail(((BIZException) e).getCode(), e.getMessage());
            }
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response postResource(Attachment struct) throws TException {
        try {
            Attachment result = service.postResource(struct);
            if (result != null) {
                return ResponseUtils.success(String.valueOf(result.getId()));
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                return ResponseUtils.fail(((BIZException) e).getCode(), e.getMessage());
            }
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response putResource(Attachment struct) throws TException {
        try {
            int result = service.putResource(struct);
            if (result > 0) {
                return ResponseUtils.success("1");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                return ResponseUtils.fail(((BIZException) e).getCode(), e.getMessage());
            }
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response delResource(Attachment struct) throws TException {
        try {
            int result = service.delResource(struct);
            if (result > 0) {
                return ResponseUtils.success("1");
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PUT_FAILED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                return ResponseUtils.fail(((BIZException) e).getCode(), e.getMessage());
            }
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response getPagination(CommonQuery query) throws TException {
        try {
            Pagination pagination = service.getPagination(QueryConvert.commonQueryConvertToQuery(query));
            return ResponseUtils.success(pagination);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                return ResponseUtils.fail(((BIZException) e).getCode(), e.getMessage());
            }
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response getResource(CommonQuery query) throws TException {
        try {
            Attachment data = service.getResource(QueryConvert.commonQueryConvertToQuery(query));
            if (data != null) {
                return ResponseUtils.success(data);
            } else {
                return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            if (e instanceof BIZException) {
                return ResponseUtils.fail(((BIZException) e).getCode(), e.getMessage());
            }
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }


    @Override
    public Response delPcResource(int id) throws BIZException, TException {
        try{
            Response res=service.delPcAttachment(id);
            return res;
        }catch(Exception e){
            logger.info(e.getMessage(),e);
            if (e instanceof BIZException) {
                return ResponseUtils.fail(((BIZException) e).getCode(), e.getMessage());
            }
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }
}
