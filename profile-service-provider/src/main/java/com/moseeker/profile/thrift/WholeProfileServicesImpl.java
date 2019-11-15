package com.moseeker.profile.thrift;

import com.moseeker.common.constants.UserSource;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.JsonToMap;
import com.moseeker.common.util.StringUtils;
import com.moseeker.profile.service.impl.ProfileMiniService;
import com.moseeker.profile.service.impl.ProfileService;
import com.moseeker.profile.service.impl.WholeProfileService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.profile.service.WholeProfileServices.Iface;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WholeProfileServicesImpl implements Iface {

    @Autowired
    private WholeProfileService service;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileMiniService profileMiniService;



    @Override
    public Response
    getResource(int userId, int profileId, String uuid) throws TException {
        try {
            return service.getResource(userId, profileId, uuid);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response postResource(String profile, int userId) throws TException {
        try {
            return service.postResource(profile, userId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response importCV(String profile, int userId) throws TException {
        try {
            return service.importCV(profile, userId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response verifyRequires(int userId, int positionId) throws TException {
        try {
            return service.verifyRequires(userId, positionId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response createProfile(String profile) throws TException {
        try {
            return service.createProfile(profile);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response improveProfile(String profile) throws TException {
        try {
            return service.improveProfile(profile);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response moveProfile(int destUserId, int originUserId)
            throws TException {
        try {
            return service.improveProfile(destUserId, originUserId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public boolean retrieveProfile(String parameter) throws BIZException, TException {
        try {
            return service.retrieveProfile(parameter);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
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
            throw ExceptionUtils.convertException(e);
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
            throw ExceptionUtils.convertException(e);
        }

    }

    @Override
    public Response combinationProfile(String params, int companyId) throws BIZException, TException {
        try {
            return service.combinationProfile(params,companyId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response preserveProfile(String params, int hrId, int companyId, String fileName,int userId) throws BIZException, TException {
        try {
            return service.preserveProfile(params,fileName,hrId,companyId,userId, UserSource.TALENT_UPLOAD.getValue());
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response validateHrAndUploaduser(int hrId, int companyId, int userId) throws BIZException, TException {
        try {
            return service.validateUpLoadHr(companyId,hrId,userId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getUploadProfile( int userId) throws BIZException, TException {
        try {
            return service.getProfileUpload(userId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
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
            throw ExceptionUtils.convertException(e);
        }
    }
}
