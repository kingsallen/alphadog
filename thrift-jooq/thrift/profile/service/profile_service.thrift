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
    common_struct.Response getProfileByApplication(1:profile_struct.ProfileApplicationForm profileForm);
    common_struct.Response resumeProfile(1:i32 uid,2:string fileName,3:string file)throws (1: common_struct.BIZException e);
    i32 upsertProfile(1:i32 userId, 2:string profile)throws (1: common_struct.BIZException e);
    common_struct.Response resumeTalentProfile(1:string fileName,2:string file,3: i32 companyId)throws (1: common_struct.BIZException e);
    list<profile_struct.UserProfile> fetchUserProfile(1: list<i32> userIdList) throws (1: common_struct.BIZException e);
    common_struct.Response getProfileTokenEcrypt(1:string token);
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
