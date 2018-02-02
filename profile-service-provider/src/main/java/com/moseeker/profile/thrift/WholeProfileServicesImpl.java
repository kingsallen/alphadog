package com.moseeker.profile.thrift;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.JsonToMap;
import com.moseeker.common.util.StringUtils;
import com.moseeker.profile.service.ProfileOtherService;
import com.moseeker.profile.service.impl.ProfileMiniService;
import com.moseeker.profile.service.impl.ProfileService;
import com.moseeker.profile.service.impl.WholeProfileService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices.Iface;
import java.util.List;
import java.util.Map;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WholeProfileServicesImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(WholeProfileServicesImpl.class);

    @Autowired
    private WholeProfileService service;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileMiniService profileMiniService;

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
    public Response getProfileInfo(int userId, int accountId) throws BIZException, TException {
        Response  response = null;
        try {

            response = service.getResource(userId, -1, "");
            List<Map<String, Object>> others = profileService.getApplicationOther(userId, accountId);
            if(response != null && response.getData() != null) {
                Map<String, Object> profile = (Map<String, Object>) JsonToMap.parseJSON2Map(response.getData());
                if(profile != null ){
                    profile.put("others", others);
                }
                Map<String, Object> profilrCamle = StringUtils.convertUnderKeyToCamel(profile);
                return ResponseUtils.success(profilrCamle);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
        return response;
    }

    @Override
    public Response getProfileMiniList(Map<String, String> params) throws TException {
        try{
            Map<String,Object> result=profileMiniService.getProfileMini(params);
            if(result==null||result.isEmpty()){
                return ResponseUtils.fail("查找失败");
            }
            return ResponseUtils.success(result);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }

    }

}
