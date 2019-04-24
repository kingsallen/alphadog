package com.moseeker.profile.domain;

import com.moseeker.baseorm.dao.logdb.LogResumeDao;
import com.moseeker.baseorm.dao.profiledb.entity.ProfileWorkexpEntity;
import com.moseeker.baseorm.db.logdb.tables.records.LogResumeRecordRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.pojo.profile.ProfileObj;
import com.moseeker.entity.pojo.resume.ResumeObj;
import com.moseeker.profile.constants.ProfileDefaultValues;
import com.moseeker.profile.service.impl.resumesdk.ProfileObjRepository;
import com.moseeker.profile.service.impl.resumesdk.iface.IResumeParser;
import com.moseeker.entity.pojo.resume.ResumeParseException;
import com.moseeker.profile.service.impl.resumesdk.iface.ResumeParserHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: jack
 * @Date: 2018/8/2
 */
@Service
@CounterIface
public class ResumeEntity {

    @Autowired
    private List<IResumeParser> resumeParsers;

    @Autowired
    private ProfileObjRepository profileObjRepository;

    @Autowired
    private LogResumeDao resumeDao;

    Logger logger = LoggerFactory.getLogger(ResumeEntity.class);

    /**
     * 将resumeSDK数据转成ProfileObj
     * @param resumeObj 第三方服务解析后的数据
     * @param uid 用户编号
     * @param fileName 文件名称
     * @return 简历数据
     */
    public ProfileObj handlerParseData(ResumeObj resumeObj, int uid, String fileName ){
        ProfileObj profileObj = new ProfileObj();
        logger.info("============resumeObjResult:{}", resumeObj.getResult());
        if (resumeObj.getStatus().getCode() == 200) {
            List<ResumeParseException> exceptions = new ArrayList<>();
            resumeParsers.forEach(p-> {
                p.parseResume(profileObj,resumeObj);
            });

            if (profileObj.getExceptions() != null && profileObj.getExceptions().size() > 0) {
                exceptions.addAll(profileObj.getExceptions());
                profileObj.setExceptions(null);         //异常不返回
            }

            exceptions.addAll(profileObjRepository.fillProfile(profileObj, uid));
            if (exceptions.size() > 0) {
                List<LogResumeRecordRecord> logResumeRecordRecordList = exceptions
                        .stream()
                        .map(exception -> ResumeParserHelper.buildLogResumeRecord(exception, resumeObj, uid, fileName, null))
                        .collect(Collectors.toList());
                if (logResumeRecordRecordList != null && logResumeRecordRecordList.size() > 0) {
                    resumeDao.addRecords(logResumeRecordRecordList);
                }
            }
        }

        return profileObj;
    }

    /**
     * 简历数据填充一些常量信息。如果有异常信息，则将异常信息持久化
     * @param profileObj 简历数据
     * @param resumeObj ResumeSDK解析的结果
     * @param userId 用户编号
     * @param fileName 文件名称
     * @param profileText 简历原文
     */
    public void fillProfileObj(ProfileObj profileObj, ResumeObj resumeObj, int userId, String fileName, String profileText) {
        List<ResumeParseException> exceptions = new ArrayList<>();
        exceptions.addAll(profileObjRepository.fillProfile(profileObj));
        if (exceptions.size() > 0) {
            List<LogResumeRecordRecord> logResumeRecordRecordList = exceptions
                    .stream()
                    .map(exception -> ResumeParserHelper.buildLogResumeRecord(exception, resumeObj, userId, fileName, profileText))
                    .collect(Collectors.toList());
            if (logResumeRecordRecordList != null && logResumeRecordRecordList.size() > 0) {
                resumeDao.addRecords(logResumeRecordRecordList);
            }
        }
    }

    /**
     * 解析的数据填充必要字段的默认值
     * @param profilePojo 简历数据
     */
    public void fillDefault(ProfilePojo profilePojo) {

        if (profilePojo.getUserRecord() != null && StringUtils.isBlank(profilePojo.getUserRecord().getName())) {
            profilePojo.getUserRecord().setName(ProfileDefaultValues.defaultName);
        }

        if (profilePojo.getWorkexpRecords() == null || profilePojo.getWorkexpRecords().size() == 0) {

            ProfileWorkexpEntity workexpEntity = new ProfileWorkexpEntity();
            workexpEntity.setJob(ProfileDefaultValues.defaultWorkExpJob);
            workexpEntity.setStart(new Date(ProfileDefaultValues.defaultWorkExpStartDate));
            workexpEntity.setEndUntilNow(ProfileDefaultValues.defaultWorkExpUntilNow);
            workexpEntity.setDescription(ProfileDefaultValues.defaultWorkExpDestription);
            profilePojo.setWorkexpRecords(new ArrayList<ProfileWorkexpEntity>(){{add(workexpEntity);}});
        }

        if (profilePojo.getEducationRecords() == null || profilePojo.getEducationRecords().size() == 0) {

            ProfileEducationRecord educationRecord = new ProfileEducationRecord();
            educationRecord.setCollegeName(ProfileDefaultValues.defaultEducationSchoolName);
            educationRecord.setDegree((byte) ProfileDefaultValues.defaultEducationDegree.getValue());
            educationRecord.setMajorName(ProfileDefaultValues.defaultEducationMajorName);
            educationRecord.setStart(new Date(ProfileDefaultValues.defaultEducationStartDate));
            educationRecord.setEndUntilNow(ProfileDefaultValues.defaultEducationUntilNow);
            educationRecord.setDescription(ProfileDefaultValues.defaultEducationDescription);
            profilePojo.setEducationRecords(new ArrayList<ProfileEducationRecord>(){{add(educationRecord);}});
        }

        if (profilePojo.getProjectExps() == null || profilePojo.getProjectExps().size() == 0) {

            ProfileProjectexpRecord profileProjectexpRecord = new ProfileProjectexpRecord();
            profileProjectexpRecord.setName(ProfileDefaultValues.defaultProjectName);
            profileProjectexpRecord.setStart(new Date(ProfileDefaultValues.defaultProjectStartDate));
            profileProjectexpRecord.setEndUntilNow(ProfileDefaultValues.defaultProjectUntilNow);
            profilePojo.setProjectExps(new ArrayList<ProfileProjectexpRecord>(){{add(profileProjectexpRecord);}});
        }
    }
}
