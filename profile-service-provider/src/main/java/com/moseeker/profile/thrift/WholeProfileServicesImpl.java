package com.moseeker.profile.thrift;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.service.ProfileOtherService;
import com.moseeker.profile.service.impl.WholeProfileService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices.Iface;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WholeProfileServicesImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(WholeProfileServicesImpl.class);

    @Autowired
    private WholeProfileService service;

    @Autowired
    private ProfileOtherService otherService;

    @Override
    public Response getResource(int userId, int profileId, String uuid) throws TException {
        try {
            return service.getResource(userId, profileId, uuid);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response postResource(String profile, int userId) throws TException {
        try {
            return service.postResource(profile, userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response importCV(String profile, int userId) throws TException {
        try {
            return service.importCV(profile, userId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response verifyRequires(int userId, int positionId) throws TException {
        try {
            return service.verifyRequires(userId, positionId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response createProfile(String profile) throws TException {
        try {
            return service.createProfile(profile);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response improveProfile(String profile) throws TException {
        try {
            return service.improveProfile(profile);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response moveProfile(int destUserId, int originUserId)
            throws TException {
        try {
            return service.improveProfile(destUserId, originUserId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public boolean retrieveProfile(String parameter) throws BIZException, TException {
        try {
            logger.info("retrieveProfile parameter{}：", parameter);
            return service.retrieveProfile(parameter);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response getProfileInfo(int userId, int profileId) throws BIZException, TException {
        Response  response = null;
        try {
            response = service.getResource(userId, profileId, "");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
        return response;
    }

    @Override
    public Response combinationProfile(String params, int companyId) throws BIZException, TException {
        try {
            logger.info("preserveProfile parameter{}：", params);
            return service.combinationProfile(params,companyId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response preserveProfile(String params, int hrId, int companyId, String fileName,int userId) throws BIZException, TException {
        try {
            logger.info("preserveProfile parameter{}：", params);
            return service.preserveProfile(params,fileName,hrId,companyId,userId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response validateHrAndUploaduser(int hrId, int companyId, int userId) throws BIZException, TException {
        try {
            return service.validateUpLoadHr(companyId,hrId,userId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response getUploadProfile( int userId) throws BIZException, TException {
        try {
            return service.getProfileUpload(userId);
        } catch (CommonException e) {
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }


}
