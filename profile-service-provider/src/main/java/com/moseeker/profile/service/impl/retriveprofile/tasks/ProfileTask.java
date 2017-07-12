package com.moseeker.profile.service.impl.retriveprofile.tasks;

import com.moseeker.baseorm.dao.profiledb.ProfileProfileDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.common.exception.CommonException;
import com.moseeker.profile.constants.StatisticsForChannelmportVO;
import com.moseeker.profile.entity.ProfileEntity;
import com.moseeker.profile.service.impl.ProfileCompletenessImpl;
import com.moseeker.profile.service.impl.retriveprofile.RetrieveParam;
import com.moseeker.profile.service.impl.retriveprofile.Task;
import com.moseeker.profile.service.impl.serviceutils.ProfilePojo;
import com.moseeker.profile.service.impl.serviceutils.ProfileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * profile业务处理。
 * Created by jack on 09/07/2017.
 */
@Component
public class ProfileTask extends Task {

    @Autowired
    private ProfileProfileDao profileDao;

    @Autowired
    ProfileUtils profileUtils;

    @Autowired
    ProfileEntity profileEntity;

    @Autowired
    private ProfileCompletenessImpl completenessImpl;

    @Override
    protected void handler(RetrieveParam param) throws CommonException {
        if (param.getProfilePojo() != null) {

            System.out.println(profileDao);
            System.out.println(param);
            System.out.println(param.getUserUserRecord());
            ProfileProfileRecord profileProfileRecord = profileDao.getProfileByUserId(param.getUserUserRecord().getId());

            ProfilePojo profilePojo = param.getProfilePojo();
            if (profilePojo.getProfileRecord().getUserId() == null) {
                profilePojo.getProfileRecord().setUserId(param.getUserUserRecord().getId());
            }
            if (profileProfileRecord == null) {
                profileDao.saveProfile(profilePojo.getProfileRecord(), profilePojo.getBasicRecord(),
                        profilePojo.getAttachmentRecords(), profilePojo.getAwardsRecords(), profilePojo.getCredentialsRecords(),
                        profilePojo.getEducationRecords(), profilePojo.getImportRecords(), profilePojo.getIntentionRecords(),
                        profilePojo.getLanguageRecords(), profilePojo.getOtherRecord(), profilePojo.getProjectExps(),
                        profilePojo.getSkillRecords(), profilePojo.getWorkexpRecords(), profilePojo.getWorksRecords(),
                        profilePojo.getUserRecord(), null);
                profileProfileRecord = profilePojo.getProfileRecord();
                logger.info("ProfileTask create profile. profile:{}", profilePojo.getProfileRecord());
            } else {
                updateProfile(profilePojo, profileProfileRecord);
            }

            int profileId = profileProfileRecord.getId();
            int userId = param.getProfilePojo().getUserRecord().getId();
            Timestamp createTime = null;
            if (param.getProfilePojo().getImportRecords() != null && param.getProfilePojo().getImportRecords().getCreateTime() != null) {
                createTime = param.getProfilePojo().getImportRecords().getCreateTime();
            }
            Byte channelType = null;
            if (param.getProfilePojo().getImportRecords() != null && param.getProfilePojo().getImportRecords().getSource() != null) {
                channelType = param.getProfilePojo().getImportRecords().getSource();
            }
            logForStatistics(profileId, userId, createTime, channelType, param.getProfilePojo());
        }
    }

    /**
     * 添加失败，重新进行刷新，避免重复添加导致的bug。
     * @param profilePojo
     * @param userId
     */
    private void reUpdate(ProfilePojo profilePojo, int userId) {
        ProfileProfileRecord profileProfileRecord = profileDao.getProfileByUserId(userId);
        updateProfile(profilePojo, profileProfileRecord);
    }

    /**
     * 更新简历
     * @param profilePojo profile信息
     * @param profileProfileRecord 原来的profile
     */
    private void updateProfile(ProfilePojo profilePojo, ProfileProfileRecord profileProfileRecord) {
        int profileId = profileProfileRecord.getId();
        profileEntity.improveProfile(profilePojo.getProfileRecord(), profileProfileRecord);
        profileEntity.improveBasic(profilePojo.getBasicRecord(), profileId);
        profileEntity.improveAttachment(profilePojo.getAttachmentRecords(), profileId);
        profileEntity.improveAwards(profilePojo.getAwardsRecords(), profileId);
        profileEntity.improveCredentials(profilePojo.getCredentialsRecords(), profileId);
        profileEntity.improveEducation(profilePojo.getEducationRecords(), profileId);
        profileEntity.improveIntention(profilePojo.getIntentionRecords(), profileId);
        profileEntity.improveLanguage(profilePojo.getLanguageRecords(), profileId);
        profileEntity.improveOther(profilePojo.getOtherRecord(), profileId);
        profileEntity.improveProjectexp(profilePojo.getProjectExps(), profileId);
        profileEntity.improveSkill(profilePojo.getSkillRecords(), profileId);
        profileEntity.improveWorkexp(profilePojo.getWorkexpRecords(), profileId);
        profileEntity.improveWorks(profilePojo.getWorksRecords(), profileId);
        completenessImpl.getCompleteness1(0, null, profileId);
    }

    /**
     * 统计日志
     * @param profileId 简历编号
     * @param userId 用户编号
     * @param createTime 创建时间
     * @param channelType 导入渠道
     * @param profilePojo profile数据
     */
    private void logForStatistics(int profileId, int userId, Timestamp createTime, Byte channelType, ProfilePojo profilePojo) {
        StatisticsForChannelmportVO statisticsForChannelmportVO = new StatisticsForChannelmportVO();
        statisticsForChannelmportVO.setProfile_operation((byte) 0);
        statisticsForChannelmportVO.setProfile_id(profileId);
        statisticsForChannelmportVO.setUser_id(userId);
        if (createTime != null) {
            statisticsForChannelmportVO.setImport_time(createTime.getTime());
        }
        if (channelType != null) {
            statisticsForChannelmportVO.setImport_channel(channelType);
        }

        //String param = JSON.toJSONString(new HashMap<String, Object>(){{put("profile", profilePojo);}});

        profileUtils.logForStatistics("importCV", null, statisticsForChannelmportVO);
    }
}
