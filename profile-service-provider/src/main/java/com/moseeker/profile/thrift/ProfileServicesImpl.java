package com.moseeker.profile.thrift;

import com.moseeker.baseorm.exception.ExceptionConvertUtil;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.profile.service.ReferralService;
import com.moseeker.profile.service.impl.ProfileCompanyTagService;
import com.moseeker.profile.service.impl.ProfileService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.common.struct.SysBIZException;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.List;

@Service
public class ProfileServicesImpl implements Iface {

    Logger logger = LoggerFactory.getLogger(ProfileProjectExpServicesImpl.class);

    @Autowired
    private ProfileService service;

    @Autowired
    private ReferralService referralService;

    @Autowired
    com.moseeker.profile.service.ProfileService profileService;

    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;

    @Override
    public Response getResource(CommonQuery query) throws TException {
        try {
            return service.getResource(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response postResource(Profile struct) throws TException {
        try {
            return service.postResource(struct);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response getCompleteness(int userId, String uuid, int profileId) throws TException {
        try {
            return service.getCompleteness(userId, uuid, profileId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response reCalculateUserCompleteness(int userId, String mobile) throws TException {
        try {
            return service.reCalculateUserCompleteness(userId, mobile);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response reCalculateUserCompletenessBySettingId(int id) throws TException {
        try {
            return service.reCalculateUserCompletenessBySettingId(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response getProfileByApplication(ProfileApplicationForm profileApplicationForm) throws TException {
        try {
            return service.getProfileByApplication(profileApplicationForm);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response resumeProfile(int uid, String fileName, String file) throws BIZException, TException {
        try {
            return service.profileParser(uid, fileName, file);
        } catch (TException e) {
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response parseProfileAttachment(String fileName, String file) throws BIZException, TException {
        try {
            return service.profileParser(fileName, file);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public int employeeReferralProfile(int employeeId, String name, String mobile, List<String> referralReasons,
                                       int position, byte referralType)
            throws BIZException, TException {
        try {

            return referralService.employeeReferralProfile(employeeId, name, mobile, referralReasons, position,
                    referralType);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public ProfileParseResult parseFileProfile(int employeeId, String fileName, ByteBuffer fileData)
            throws BIZException, TException {
        try {
            com.moseeker.profile.service.impl.vo.ProfileDocParseResult result =
                    referralService.parseFileProfile(employeeId, fileName, fileData);
            ProfileParseResult profileParseResult = new ProfileParseResult();
            BeanUtils.copyProperties(result, profileParseResult);
            return profileParseResult;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public void employeeDeleteReferralProfile(int employeeId) throws BIZException, TException {
        try {
            referralService.employeeDeleteReferralProfile(employeeId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public int postCandidateInfo(int employeeId, CandidateInfo candidateInfo) throws BIZException, TException {
        try {
            com.moseeker.profile.service.impl.vo.CandidateInfo candidate = new com.moseeker.profile.service.impl.vo.CandidateInfo();
            BeanUtils.copyProperties(candidateInfo, candidate);
            candidate.setPosition(candidateInfo.getPositionId());
            return referralService.postCandidateInfo(employeeId, candidate);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public int upsertProfile(int userId, String profile) throws BIZException, TException {
        try {
            int result= profileService.upsertProfile(userId, profile);
            profileCompanyTagService.handlerProfileCompanyTag(0,userId);
            return result;
        } catch (CommonException e) {
            logger.error(e.getMessage(), e);
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SysBIZException();
        }
    }

    @Override
    public Response resumeTalentProfile(String fileName, String file, int companyId) throws BIZException, TException {
        try {
            return service.talentpoolUploadParse(fileName,file,companyId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public List<UserProfile> fetchUserProfile(List<Integer> userIdList) throws BIZException, TException {
        try {
            return service.fetchUserProfile(userIdList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response getProfileTokenDecrypt(String token) throws BIZException, TException {
        try {
            return service.getProfileTokenDecrypt(token);
        } catch (CommonException e) {
            logger.error(e.getMessage(), e);
            throw ExceptionConvertUtil.convertCommonException(e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
    }

    @Override
    public int parseText(String profile, int referenceId) throws BIZException, TException {
        try {
            return service.parseText(profile, referenceId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getResources(CommonQuery query) throws TException {
        try {
            return service.getResources(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response getPagination(CommonQuery query) throws TException {
        try {
            return service.getPagination(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response postResources(List<Profile> resources) throws TException {
        try {
            return service.postResources(resources);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response putResources(List<Profile> resources) throws TException {
        try {
            return service.putResources(resources);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response delResources(List<Profile> resources) throws TException {
        try {
            return service.delResources(resources);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response putResource(Profile profile) throws TException {
        try {
            return service.putResource(profile);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }

    @Override
    public Response delResource(Profile profile) throws TException {
        try {
            return service.delResource(profile);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            throw new BIZException(ConstantErrorCodeMessage.PROGRAM_EXCEPTION_STATUS, e.getMessage());
        }
    }
}
