# file: profile_service.thrift

include "../struct/profile_struct.thrift"
include "../../dao/struct/db/profiledb_struct.thrift"
include "../../common/struct/common_struct.thrift"
include "../../config/struct/config_struct.thrift"
namespace java com.moseeker.thrift.gen.profile.service

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */

service ProfileOtherThriftService {
    list<profiledb_struct.ProfileOtherDO> getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    profiledb_struct.ProfileOtherDO getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);

    list<profiledb_struct.ProfileOtherDO> postResources(1: list<profiledb_struct.ProfileOtherDO> Others) throws (1: common_struct.BIZException e);
    profiledb_struct.ProfileOtherDO postResource(1: profiledb_struct.ProfileOtherDO Other) throws (1: common_struct.BIZException e);
    list<i32> putResources(1: list<profiledb_struct.ProfileOtherDO> Others) throws (1: common_struct.BIZException e);
    i32 putResource(1: profiledb_struct.ProfileOtherDO Other) throws (1: common_struct.BIZException e);
    list<i32> delResources(1: list<profiledb_struct.ProfileOtherDO> Others) throws (1: common_struct.BIZException e);
    i32 delResource(1: profiledb_struct.ProfileOtherDO Other) throws (1: common_struct.BIZException e);
    common_struct.Response getCustomMetaData(1: i32 companyId, 2: bool selectAll) throws (1: common_struct.BIZException e);
    common_struct.Response checkProfileOther(1: i32 userId, 2: i32 positionId) throws (1: common_struct.BIZException e);
    common_struct.Response getProfileOther(1: string params) throws (1: common_struct.BIZException e);
    common_struct.Response otherFieldsCheck(1: i32 profileId, 2: string fields) throws(1: common_struct.BIZException e);
    common_struct.Response getProfileOtherByPosition(1:i32 userId, 2:i32 accountId, 3:i32 positionId) throws (1: common_struct.BIZException e);
    common_struct.Response getProfileOtherByPositionNotViewApplication(1:i32 userId, 2:i32 accountId, 3:i32 positionId) throws (1: common_struct.BIZException e);
    common_struct.Response updateSpecificResource(1:string otherParams);
    //获取关键词推荐必填项
    profile_struct.RequiredFieldInfo fetchRequireField(1: i32 positionId) throws (1: common_struct.BIZException e);
}

service WholeProfileServices {
    common_struct.Response getResource(1:i32 userId, 2:i32 profileId, 3:string uuid) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1:string profile, 2:i32 user_id) throws (1: common_struct.BIZException e);
    common_struct.Response importCV(1:string profile, 2:i32 user_id) throws (1: common_struct.BIZException e);
    common_struct.Response verifyRequires(1:i32 userId, 2:i32 positionId) throws (1: common_struct.BIZException e);
    //创建简历
    common_struct.Response createProfile(1:string profile) throws (1: common_struct.BIZException e);
    //更新简历
    common_struct.Response improveProfile(1:string profile) throws (1: common_struct.BIZException e);
    common_struct.Response moveProfile(1:i32 destUserId, 2:i32 originUserId) throws (1: common_struct.BIZException e);
    bool retrieveProfile(1:string parameter)throws (1: common_struct.BIZException e);
    common_struct.Response getProfileInfo(1:i32 userId, 2:i32 accountId, 3:i32 positionId) throws (1: common_struct.BIZException e);
    common_struct.Response getProfileMiniList(1:map<string,string> params);
    common_struct.Response combinationProfile(1:string params, 2:i32 companyId) throws (1: common_struct.BIZException e);
    common_struct.Response preserveProfile(1:string params, 2:i32 hrId,3:i32 companyId,4:string fileName,5: i32 userId) throws (1: common_struct.BIZException e);
    common_struct.Response validateHrAndUploaduser(1:i32 hrId,2:i32 companyId,3: i32 userId) throws (1: common_struct.BIZException e);
    common_struct.Response getUploadProfile(1: i32 userId) throws (1: common_struct.BIZException e);
    common_struct.Response getMiniProfileSuggest(1:i32 accountId,2:string keyword,3:i32 page,4: i32 pageSize);

}

service ProfileServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(2:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.Profile> resources) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.Profile> resources) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.Profile> resources) throws (1: common_struct.BIZException e);
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.Profile profile);
    common_struct.Response putResource(1: profile_struct.Profile profile);
    common_struct.Response delResource(1: profile_struct.Profile profile);
    common_struct.Response getCompleteness(1:i32 user_id, 2: string uuid, 3: i32 profile_id);
    common_struct.Response reCalculateUserCompleteness(1:i32 userId, 2:string mobile);
    common_struct.Response reCalculateUserCompletenessBySettingId(1:i32 id);
    common_struct.Response parseProfileAttachment(1:string fileName, 2:string file)throws (1: common_struct.BIZException e);
    common_struct.Response getProfileByApplication(1:profile_struct.ProfileApplicationForm profileForm);
    common_struct.Response resumeProfile(1:i32 uid,2:string fileName,3:string file)throws (1: common_struct.BIZException e);
    i32 upsertProfile(1:i32 userId, 2:string profile)throws (1: common_struct.BIZException e);
    common_struct.Response resumeTalentProfile(1:string fileName,2:string file,3: i32 companyId)throws (1: common_struct.BIZException e);
    list<profile_struct.UserProfile> fetchUserProfile(1: list<i32> userIdList) throws (1: common_struct.BIZException e);
    common_struct.Response getProfileTokenDecrypt(1:string token);
    i32 parseText(1: string profile, 2: i32 reference, 3: i32 appid) throws (1: common_struct.BIZException e);
    //员工简历解析
    profile_struct.ProfileParseResult parseFileProfile(1:i32 employeeId, 2:string fileName, 3:binary fileData)throws (1: common_struct.BIZException e)
    profile_struct.ProfileParseResult parseFileProfileByFilePath(1: string filePath, 2:i32 userId, 3: string syncId)throws (1: common_struct.BIZException e)
     //用户简历解析
     profile_struct.ProfileParseResult parseUserFileProfile(1:i32 employeeId, 2:string fileName, 3:binary fileData)throws (1: common_struct.BIZException e)
    //简历解析
    profile_struct.ProfileParseResult parseFileStreamProfile(1:i32 employeeId, 2: string fileOriginName, 3: string fileName, 4: string absoluteName, 5: string fileData)throws (1: common_struct.BIZException e)
    //员工推荐简历
    i32 employeeReferralProfile(1:i32 employeeId, 2:string name, 3:string mobile, 4: list<string> referralReasons, 5: i32 position, 6: i8 relationship, 7: string recomReasonText, 8: i8 referralType)throws (1: common_struct.BIZException e)
        //用户上传简历
     i32 updateUserProfile(1:i32 employeeId, 2:string name, 3:string mobile)throws (1: common_struct.BIZException e)

    //删除上传的简历数据
    void employeeDeleteReferralProfile(1:i32 employeeId)throws (1: common_struct.BIZException e)
    //员工提交被推荐人关键信息
    i32 postCandidateInfo(1:i32 employeeId, 2: profile_struct.CandidateInfo candidateInfo)throws (1: common_struct.BIZException e)
    // mobot 提交推荐申请
    map<string, string> saveMobotReferralProfile(1:i32 employeeId, 2:list<i32> ids)throws (1: common_struct.BIZException e)
    // mobot 提交推荐信息缓存
    i32 saveMobotReferralProfileCache(1:i32 employeeId, 2:string mobile, 3: string name, 4:list<string> referralReasons, 5: i8 referralType, 6: string fileName, 7: i32 relationship, 8: string recomReasonText)throws (1: common_struct.BIZException e)
    // 点击告诉ta时回填推荐信息，从缓存中取
    string getMobotReferralCache(1:i32 employeeId)throws (1: common_struct.BIZException e)
    //猎头简历解析上传
    profile_struct.ProfileParseResult parseHunterFileProfile(1:i32 headhunterId, 2:string fileName, 3:binary fileData)throws (1: common_struct.BIZException e)

    //人员上传文件时，调用此接口返回上传记录
    profile_struct.ReferralUploadFiles uploadFiles(1:string sceneId, 2: string unionid, 3:string fileName, 4:binary fileData) throws (1:common_struct.BIZException e);
    //上传文件分页列表
    list<profile_struct.ReferralUploadFiles> getUploadFiles(1:string unionId,2:i32 pageSize,3:i32 pageNo ) throws (1:common_struct.BIZException e);
    //下载文件
    string downLoadFiles(1:string sceneId) throws (1:common_struct.BIZException e);
    //解析上传文件，返回结果
    profile_struct.ReferralUploadFiles referralResumeInfo(1:string sceneId) throws (1:common_struct.BIZException e);
    //查找选择简历做内推的操作是否结束
    bool getSpecifyProfileResult(1: i32 employeeId,2:string syncId) throws (1:common_struct.BIZException e);
    //解析结果返回并确认
    profile_struct.ProfileParseResult checkResult(1: i32 employeeId) throws (1:common_struct.BIZException e);
}

service AttachmentServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.Attachment> attachments) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.Attachment> attachments) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.Attachment> attachments) throws (1: common_struct.BIZException e);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1: profile_struct.Attachment attachment) throws (1: common_struct.BIZException e);
    common_struct.Response putResource(1: profile_struct.Attachment attachment) throws (1: common_struct.BIZException e);
    common_struct.Response delResource(1: profile_struct.Attachment attachment) throws (1: common_struct.BIZException e);
    common_struct.Response delPcResource(1:i32 id ) throws (1: common_struct.BIZException e);
}

service AwardsServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.Awards> awards) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.Awards> awards) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.Awards> awards) throws (1: common_struct.BIZException e);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1: profile_struct.Awards awards) throws (1: common_struct.BIZException e);
    common_struct.Response putResource(1: profile_struct.Awards awards) throws (1: common_struct.BIZException e);
    common_struct.Response delResource(1: profile_struct.Awards awards) throws (1: common_struct.BIZException e);
}

service BasicServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.Basic> basics) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.Basic> basics) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.Basic> basics) throws (1: common_struct.BIZException e);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1: profile_struct.Basic basic) throws (1: common_struct.BIZException e);
    common_struct.Response putResource(1: profile_struct.Basic basic) throws (1: common_struct.BIZException e);
    common_struct.Response delResource(1: profile_struct.Basic basic) throws (1: common_struct.BIZException e);
    common_struct.Response reCalculateBasicCompleteness(1: i32 userId) throws (1: common_struct.BIZException e);
}

service CredentialsServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.Credentials> credentials) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.Credentials> credentials) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.Credentials> credentials) throws (1: common_struct.BIZException e);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1: profile_struct.Credentials credential) throws (1: common_struct.BIZException e);
    common_struct.Response putResource(1: profile_struct.Credentials credential) throws (1: common_struct.BIZException e);
    common_struct.Response delResource(1: profile_struct.Credentials credential) throws (1: common_struct.BIZException e);
}

service EducationServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.Education> educations) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.Education> educations) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.Education> educations) throws (1: common_struct.BIZException e);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1: profile_struct.Education education) throws (1: common_struct.BIZException e);
    common_struct.Response putResource(1: profile_struct.Education education) throws (1: common_struct.BIZException e);
    common_struct.Response delResource(1: profile_struct.Education education) throws (1: common_struct.BIZException e);
}

service ProfileImportServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.ProfileImport> profileImports) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.ProfileImport> profileImports) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.ProfileImport> profileImports) throws (1: common_struct.BIZException e);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1: profile_struct.ProfileImport profileImport) throws (1: common_struct.BIZException e);
    common_struct.Response putResource(1: profile_struct.ProfileImport profileImport) throws (1: common_struct.BIZException e);
    common_struct.Response delResource(1: profile_struct.ProfileImport profileImport) throws (1: common_struct.BIZException e);
}

service IntentionServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.Intention> intentions) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.Intention> intentions) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.Intention> intentions) throws (1: common_struct.BIZException e);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1: profile_struct.Intention intention) throws (1: common_struct.BIZException e);
    common_struct.Response putResource(1: profile_struct.Intention intention) throws (1: common_struct.BIZException e);
    common_struct.Response delResource(1: profile_struct.Intention intention) throws (1: common_struct.BIZException e);
}

service LanguageServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.Language> languages) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.Language> languages) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.Language> languages) throws (1: common_struct.BIZException e);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1: profile_struct.Language language) throws (1: common_struct.BIZException e);
    common_struct.Response putResource(1: profile_struct.Language language) throws (1: common_struct.BIZException e);
    common_struct.Response delResource(1: profile_struct.Language language) throws (1: common_struct.BIZException e);
}

service CustomizeResumeServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.CustomizeResume> Others) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.CustomizeResume> Others) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.CustomizeResume> Others) throws (1: common_struct.BIZException e);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1: profile_struct.CustomizeResume Other) throws (1: common_struct.BIZException e);
    common_struct.Response putResource(1: profile_struct.CustomizeResume Other) throws (1: common_struct.BIZException e);
    common_struct.Response delResource(1: profile_struct.CustomizeResume Other) throws (1: common_struct.BIZException e);
}

service ProjectExpServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.ProjectExp> projectExps) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.ProjectExp> projectExps) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.ProjectExp> projectExps) throws (1: common_struct.BIZException e);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1: profile_struct.ProjectExp projectExp) throws (1: common_struct.BIZException e);
    common_struct.Response putResource(1: profile_struct.ProjectExp projectExp) throws (1: common_struct.BIZException e);
    common_struct.Response delResource(1: profile_struct.ProjectExp projectExp) throws (1: common_struct.BIZException e);
}

service SkillServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.Skill> skills) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.Skill> skills) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.Skill> skills) throws (1: common_struct.BIZException e);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1: profile_struct.Skill skill) throws (1: common_struct.BIZException e);
    common_struct.Response putResource(1: profile_struct.Skill skill) throws (1: common_struct.BIZException e);
    common_struct.Response delResource(1: profile_struct.Skill skill) throws (1: common_struct.BIZException e);
}

service WorkExpServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.WorkExp> workExps) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.WorkExp> workExps) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.WorkExp> workExps) throws (1: common_struct.BIZException e);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1: profile_struct.WorkExp workExp) throws (1: common_struct.BIZException e);
    common_struct.Response putResource(1: profile_struct.WorkExp workExp) throws (1: common_struct.BIZException e);
    common_struct.Response delResource(1: profile_struct.WorkExp workExp) throws (1: common_struct.BIZException e);
}

service WorksServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response getPagination(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResources(1: list<profile_struct.Works> works) throws (1: common_struct.BIZException e);
    common_struct.Response putResources(1: list<profile_struct.Works> works) throws (1: common_struct.BIZException e);
    common_struct.Response delResources(1: list<profile_struct.Works> works) throws (1: common_struct.BIZException e);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query) throws (1: common_struct.BIZException e);
    common_struct.Response postResource(1: profile_struct.Works works) throws (1: common_struct.BIZException e);
    common_struct.Response putResource(1: profile_struct.Works works) throws (1: common_struct.BIZException e);
    common_struct.Response delResource(1: profile_struct.Works works) throws (1: common_struct.BIZException e);
}
