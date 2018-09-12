package com.moseeker.profile.thrift;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.UserSource;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.JsonToMap;
import com.moseeker.common.util.StringUtils;
import com.moseeker.profile.service.impl.ProfileCompanyTagService;
import com.moseeker.profile.service.impl.ProfileMiniService;
import com.moseeker.profile.service.impl.ProfileService;
import com.moseeker.profile.service.impl.WholeProfileService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices.Iface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            Response res= service.postResource(profile, userId);

            return res;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response importCV(String profile, int userId) throws TException {
        try {
            Response res= service.importCV(profile, userId);

            return res;
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
            Response res= service.createProfile(profile);

            return res;
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
    public Response getProfileInfo(int userId, int accountId, int positionId) throws BIZException, TException {
        Response  response = null;
        try {

            response = service.getResource(userId, -1, "");

            if(response != null && response.getStatus() ==0 && response.getData() != null) {

                Map<String, Object> profile = (Map<String, Object>) JsonToMap.parseJSON2Map(response.getData());
                if(positionId > 0) {
                    JobPositionDO positionDO = service.getPositionById(accountId, positionId);
                    JobApplicationDO applicationDO = service.getApplicationByposition(userId, positionId);
                    Map<String, Object> applicationMap = new HashMap<>();
                    if (positionDO != null) {
                        applicationMap.put("position_name", positionDO.getTitle() + "（" + positionDO.getCity() + "）");
                    }
                    if (applicationDO != null) {
                        applicationMap.put("status", applicationDO.getAppTplId());
                    }
                    applicationMap.put("positionId", positionId);
                    profile.put("applicationPosition", applicationMap);
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
                return ResponseUtils.success(new HashMap<>());
            }
            return ResponseUtils.success(result);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }

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
            return service.preserveProfile(params,fileName,hrId,companyId,userId, UserSource.TALENT_UPLOAD.getValue(), 1);
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

    @Override
    public Response getMiniProfileSuggest(int accountId, String keyword, int page, int pageSize) throws TException {
        try {
            Map<String,Object>  result=profileMiniService.getProfileMiniSug(accountId,keyword,page,pageSize);
            if(result==null||result.isEmpty()){
                return  ResponseUtils.success(new HashMap<>());
            }
            return  ResponseUtils.success(result);
        }catch (Exception e){
            logger.info(e.getMessage(),e);
            throw ExceptionUtils.convertException(e);
        }
    }




}
