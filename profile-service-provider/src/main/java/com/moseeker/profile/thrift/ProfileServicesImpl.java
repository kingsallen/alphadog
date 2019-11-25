package com.moseeker.profile.thrift;

import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.profile.service.ReferralService;
import com.moseeker.profile.service.UploadFilesService;
import com.moseeker.profile.service.impl.ProfileCompanyTagService;
import com.moseeker.profile.service.impl.ProfileService;
import com.moseeker.profile.service.impl.resumefileupload.ResumeFileParserFactory;
import com.moseeker.profile.service.impl.vo.MobotReferralResultVO;
import com.moseeker.profile.service.impl.vo.UploadFilesResult;
import com.moseeker.profile.utils.OfficeUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.profile.service.ProfileServices.Iface;
import com.moseeker.thrift.gen.profile.struct.*;
import org.apache.thrift.TException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProfileServicesImpl implements Iface {

    @Autowired
    private ResumeFileParserFactory resumeFileParserFactory;

    @Autowired
    private ProfileService service;

    @Autowired
    private ReferralService referralService;

    @Autowired
    com.moseeker.profile.service.ProfileService profileService;

    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;

    @Autowired
    private UploadFilesService uploadFilesService;

    @Resource(name = "cacheClient")
    private RedisClient redisClient;

    @Override
    public Response getResource(CommonQuery query) throws TException {
        try {
            return service.getResource(QueryConvert.commonQueryConvertToQuery(query));
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response postResource(Profile struct) throws TException {
        try {
            return service.postResource(struct);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getCompleteness(int userId, String uuid, int profileId) throws TException {
        try {
            return service.getCompleteness(userId, uuid, profileId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response reCalculateUserCompleteness(int userId, String mobile) throws TException {
        try {
            return service.reCalculateUserCompleteness(userId, mobile);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response reCalculateUserCompletenessBySettingId(int id) throws TException {
        try {
            return service.reCalculateUserCompletenessBySettingId(id);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getProfileByApplication(ProfileApplicationForm profileApplicationForm) throws TException {
        try {
            return service.getProfileByApplication(profileApplicationForm);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response resumeProfile(int uid, String fileName, String file) throws BIZException, TException {
        try {
            return service.profileParser(uid, fileName, file);
        } catch (TException e) {
            throw ExceptionUtils.convertException(e);
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
    public ProfileParseResult parseFileProfileByFilePath(String filePath, int userId, String syncId) throws BIZException, TException {
        // 如果是从word转化而得的pdf，获得转化为pdf前的word文件。如果是用户直接上传的pdf，获取pdf文件名。其他格式，文件名不变。
        filePath = OfficeUtils.getOriginFile(filePath);
        try {
            com.moseeker.profile.service.impl.vo.ProfileDocParseResult result =
                    referralService.parseFileProfileByFilePath(filePath, userId);
            uploadFilesService.setRedisKey(String.valueOf(userId), syncId);
            ProfileParseResult profileParseResult = new ProfileParseResult();
            BeanUtils.copyProperties(result, profileParseResult);

            //uploadFilesService.setRedisKey(userId,sceneId);
            return profileParseResult;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public ProfileParseResult parseUserFileProfile(int userId, String fileName, ByteBuffer fileData) throws BIZException, TException {
        try {
            com.moseeker.profile.service.impl.vo.ProfileDocParseResult result =
                    profileService.parseFileProfile(userId, fileName, fileData);
            ProfileParseResult profileParseResult = new ProfileParseResult();
            BeanUtils.copyProperties(result, profileParseResult);
            return profileParseResult;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }


    @Override
    public ProfileParseResult parseFileStreamProfile(int employeeId, String fileOriginName, String fileName,
                                                     String fileAbsoluteName, String fileData)
            throws BIZException, TException {
        try {
            com.moseeker.profile.service.impl.vo.ProfileDocParseResult result =
                    referralService.parseFileStreamProfile(employeeId, fileOriginName, fileName, fileAbsoluteName,
                            fileData);
            ProfileParseResult profileParseResult = new ProfileParseResult();
            BeanUtils.copyProperties(result, profileParseResult);
            return profileParseResult;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }



    @Override
    public int employeeReferralProfile(int employeeId, String name, String mobile,Map<String,String> otherFields, List<String> referralReasons,
                                       int position, byte relationship, String referralText, byte referralType) throws BIZException, TException {
        try {
            return referralService.employeeReferralProfile(employeeId, name, mobile, otherFields,referralReasons, position,
                    relationship,  referralText, referralType);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public List<MobotReferralResult> employeeReferralProfiles(int employeeId, String name, String mobile,Map<String,String> otherFields,
                                                              List<String> referralReasons, List<Integer> positions,
                                                              byte relationship, String recomReasonText,
                                                              byte referralType) throws BIZException, TException {
        try {
            List<MobotReferralResultVO> referralResultVOS =
                    referralService.employeeReferralProfile(employeeId, name, mobile, otherFields,referralReasons, positions,
                        relationship,  recomReasonText, referralType);

            List<MobotReferralResult> mobotReferralResults = new ArrayList<>(10);
            referralResultVOS.stream().forEach(resultVO->{
                MobotReferralResult result = new MobotReferralResult();
                if(resultVO.getId()!=null){
                    result.setId(resultVO.getId());
                }
                result.setErrorCode(resultVO.getErrorCode());
                if(resultVO.getPosition_id()!=null){
                    result.setPosition_id(resultVO.getPosition_id());
                }
                if(resultVO.getReason()!=null){
                    result.setReason(resultVO.getReason());
                }
                result.setReward(resultVO.getReward());
                if(resultVO.getSuccess()!=null){
                    result.setSuccess(resultVO.getSuccess());

                }
                if(resultVO.getTitle()!=null){
                    result.setTitle(resultVO.getTitle());

                }
                mobotReferralResults.add(result);
            });
            return mobotReferralResults;
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
            return referralService.postCandidateInfo(employeeId, candidate);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }




    @Override
    public Map<String, String> saveMobotReferralProfile(int employeeId, List<Integer> ids) throws BIZException, TException {
        try {
            return referralService.saveMobotReferralProfile(employeeId, ids);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public int saveMobotReferralProfileCache(int employeeId, String name, String mobile, List<String> referralReasons,
                                             byte referralType, String fileName, int relationship, String referralReasonText) throws BIZException, TException {
        try {
            return referralService.saveMobotReferralProfileCache(employeeId, name, mobile, referralReasons, referralType, fileName, relationship, referralReasonText);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public String getMobotReferralCache(int employeeId) throws BIZException, TException {
        try {
            return referralService.getMobotReferralCache(employeeId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public ProfileParseResult parseHunterFileProfile(int headhunterId, String fileName, ByteBuffer fileData) throws BIZException, TException {
        try {
            com.moseeker.profile.service.impl.vo.ProfileDocParseResult result =
                    profileService.parseHunterFileProfile(headhunterId, fileName, fileData);
            ProfileParseResult profileParseResult = new ProfileParseResult();
            BeanUtils.copyProperties(result, profileParseResult);
            return profileParseResult;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public ReferralUploadFiles uploadFiles(String sceneId, String unionId, String fileName, ByteBuffer fileData) throws BIZException, TException {
        ReferralUploadFiles referralUploadFiles = new ReferralUploadFiles();
        try {
            UploadFilesResult uploadFilesResult = uploadFilesService.uploadFiles(fileName, fileData);
            uploadFilesResult.setFileID(sceneId);
            uploadFilesResult.setUnionId(unionId);
            UploadFilesResult uploadResult = uploadFilesService.insertUpFiles(uploadFilesResult);
            referralUploadFiles.setUrl(uploadFilesResult.getSaveUrl());
            referralUploadFiles.setCreate_time(uploadFilesResult.getCreateTime());
            referralUploadFiles.setFilename(uploadFilesResult.getName());
            referralUploadFiles.setId(uploadResult.getId());
            return referralUploadFiles;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public List<ReferralUploadFiles> getUploadFiles(String unionId, int pageSize, int pageNo) throws BIZException, TException {
        try {
            List<UploadFilesResult> list = uploadFilesService.getUploadFiles(unionId, pageSize, pageNo);
            List<ReferralUploadFiles> referralUploadFilesList = new ArrayList<>();
            if (list != null && list.size() >0){
                for (UploadFilesResult uploadFilesResult : list){
                    ReferralUploadFiles referralUploadFiles = new ReferralUploadFiles();
                    referralUploadFiles.setFilename(uploadFilesResult.getFileName());
                    referralUploadFiles.setCreate_time(uploadFilesResult.getCreateTime());
                    referralUploadFiles.setUrl(uploadFilesResult.getSaveUrl());
                    referralUploadFiles.setFileId(uploadFilesResult.getFileID());
                    referralUploadFiles.setId(uploadFilesResult.getId());
                    referralUploadFilesList.add(referralUploadFiles);
                }
            }
            return referralUploadFilesList;
        } catch (BIZException e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public String downLoadFiles(String fileId) throws BIZException, TException {
        try {
            return uploadFilesService.downLoadFiles(fileId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public ReferralUploadFiles referralResumeInfo(String fileId) throws BIZException, TException {
        try {
            UploadFilesResult uploadFilesResult = uploadFilesService.resumeInfo(fileId);
            ReferralUploadFiles referralUploadFiles = new ReferralUploadFiles();
            referralUploadFiles.setUrl(uploadFilesResult.getSaveUrl());
            referralUploadFiles.setCreate_time(uploadFilesResult.getCreateTime());
            referralUploadFiles.setFilename(uploadFilesResult.getFileName());
            if (uploadFilesResult.getUserId() != null){
                referralUploadFiles.setId(uploadFilesResult.getUserId());
            }
            return referralUploadFiles;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public boolean getSpecifyProfileResult(int employeeId,String syncId) throws BIZException, TException {
        try {
            return uploadFilesService.getSpecifyProfileResult(employeeId,syncId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public ProfileParseResult checkResult(int employeeId) throws BIZException, TException {
        ProfileParseResult profileParseResult = new ProfileParseResult();
        try {
            UploadFilesResult uploadFilesResult = uploadFilesService.checkResult(employeeId);
            profileParseResult.setMobile(uploadFilesResult.getMobile());
            profileParseResult.setFile(uploadFilesResult.getFileName());
            profileParseResult.setName(uploadFilesResult.getName());
            profileParseResult.setEmail(uploadFilesResult.getEmail());
            profileParseResult.setPinyinName(uploadFilesResult.getPinyinName());
            profileParseResult.setPinyinSurname(uploadFilesResult.getPinyinSurname());
        }catch (Exception e){
            throw ExceptionUtils.convertException(e);
        }
        return profileParseResult;
    }

    @Override
    public String wordToPdf(String source_name, String target_name) throws BIZException, TException {
        int i = OfficeUtils.Word2Pdf(source_name, target_name);
        if (i ==0) {
            com.moseeker.common.util.OfficeUtils.Word2Pdf(source_name, target_name);
        }
        return target_name;
    }

    @Override
    public int upsertProfile(int userId, String profile) throws BIZException, TException {
        try {
            int result= profileService.upsertProfile(userId, profile);
            profileCompanyTagService.handlerProfileCompanyTag(0,userId);
            return result;
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response resumeTalentProfile(String fileName, String file, int companyId) throws BIZException, TException {
        try {
            return service.talentpoolUploadParse(fileName,file,companyId);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public List<UserProfile> fetchUserProfile(List<Integer> userIdList) throws BIZException, TException {
        try {
            return service.fetchUserProfile(userIdList);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response getProfileTokenDecrypt(String token) throws BIZException, TException {
        try {
            return service.getProfileTokenDecrypt(token);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public int parseText(String profile, int referenceId, int appid) throws BIZException, TException {
        try {
            return service.parseText(profile, referenceId,appid);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public boolean healthCheck() throws TException {
        return false;
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
    public Response postResources(List<Profile> resources) throws TException {
        try {
            return service.postResources(resources);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response putResources(List<Profile> resources) throws TException {
        try {
            return service.putResources(resources);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response delResources(List<Profile> resources) throws TException {
        try {
            return service.delResources(resources);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response putResource(Profile profile) throws TException {
        try {
            return service.putResource(profile);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public Response delResource(Profile profile) throws TException {
        try {
            return service.delResource(profile);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public int updateUserProfile(int id, String name, String mobile) throws BIZException, TException {
        try {
            return profileService.updateUserProfile(id, name, mobile);
        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }
}
