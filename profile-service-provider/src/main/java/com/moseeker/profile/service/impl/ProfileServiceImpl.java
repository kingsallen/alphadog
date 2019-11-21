package com.moseeker.profile.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.Position.PositionStatus;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.FormCheck;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.validation.ValidateUtil;
import com.moseeker.entity.PositionEntity;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.entity.biz.ProfileParseUtil;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.entity.exception.ApplicationException;
import com.moseeker.profile.constants.ProfileSource;
import com.moseeker.profile.exception.ProfileException;
import com.moseeker.profile.service.impl.resumefileupload.ReferralProfileParser;
import com.moseeker.profile.service.impl.resumefileupload.UserProfileParser;
import com.moseeker.profile.service.impl.resumefileupload.iface.AbstractResumeFileParser;
import com.moseeker.profile.service.impl.vo.ProfileDocParseResult;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.util.UUID;

import static com.moseeker.baseorm.db.userdb.tables.UserUser.USER_USER;
import static com.moseeker.profile.exception.ProfileException.PROFILE_USER_NOTEXIST;

/**
 * 简历服务
 * Created by jack on 07/09/2017.
 */
@Service
@CounterIface
public class ProfileServiceImpl implements com.moseeker.profile.service.ProfileService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ProfileEntity profileEntity;
    @Autowired
    UserUserDao userUserDao;
    @Autowired
    private ProfileCompanyTagService profileCompanyTagService;
    @Resource(type=UserProfileParser.class)
    AbstractResumeFileParser abstractResumeFileParser;
    @Autowired
    PositionEntity positionEntity;
    @Autowired
    private ProfileParseUtil profileParseUtil;
    @Autowired
    UserAccountEntity userAccountEntity;
    @Resource(name = "cacheClient")
    private RedisClient client;



    @Override
    public int upsertProfile(int userId, String profileParameter) throws CommonException {

        logger.info("ProfileServiceImpl.upsertProfile:{}", profileParameter);

        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(USER_USER.ID.getName(), userId);
        UserUserDO userUserDO = userUserDao.getData(queryBuilder.buildQuery());
        if (userUserDO == null) {
            throw PROFILE_USER_NOTEXIST;
        }
        ProfileProfileDO profileProfileDO = profileEntity.getProfileByUserId(userId);
        ProfilePojo profilePojo = profileEntity.parseProfile(profileParameter);
        logger.info("开始执行插入操作================");
        if (profileProfileDO == null) {
            ProfileProfileRecord profileProfileRecord = new ProfileProfileRecord();
            profileProfileRecord.setUserId(userUserDO.getId());
            profileProfileRecord.setDisable((byte)(Constant.ENABLE));
            profileProfileRecord.setUuid(UUID.randomUUID().toString());
            int source = 220;               //兼容简历上传  该接口最早给"邮件上传简历附件"使用
            if (profilePojo.getProfileRecord() != null) {
                if (profilePojo.getProfileRecord() .getSource() != null) {
                    source = profilePojo.getProfileRecord().getSource();
                }
                if (StringUtils.isNotBlank(profilePojo.getProfileRecord().getOrigin())) {
                    profileProfileRecord.setOrigin(profilePojo.getProfileRecord().getOrigin());
                }
            }
            profileProfileRecord.setSource(source);
            profilePojo.setProfileRecord(profileProfileRecord);
            logger.info("开始保存的参数=====");
            int profileId= profileEntity.createProfile(profilePojo, userUserDO);
            //处理打企业标签
            profileCompanyTagService.handlerProfileCompanyTag(profileId,userUserDO.getId());
            return profileId;
        } else {
            profileEntity.updateProfile(profilePojo, profileProfileDO);
            profileCompanyTagService.handlerProfileCompanyTag(profileProfileDO.getId(),userUserDO.getId());
            return profileProfileDO.getId();
        }
    }
    @Override
    @Deprecated
    public ProfileDocParseResult parseFileProfile(int id, String fileName, ByteBuffer fileData) throws ProfileException {
        return abstractResumeFileParser.parseResume(id,fileName,fileData);
    }


    /**
     * 产生简历、申请记录
     * @param id 用户id
     * @param mobile 手机号码
     * @return 简历ID
     * @throws ProfileException
     */
    @Override
    public int updateUserProfile(int id, String name, String mobile) throws ProfileException {

        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(USER_USER.ID.getName(), id);
        UserUserDO userUserDO = userUserDao.getData(queryBuilder.buildQuery());
        if (userUserDO == null) {
            throw PROFILE_USER_NOTEXIST;
        }
        userUserDO.setName(name);
        userUserDO.setMobile(Long.parseLong(mobile));
        userUserDao.updateData(userUserDO);
        String profilePojoStr = client.get(AppId.APPID_ALPHADOG.getValue(),
                KeyIdentifier.WECHAT_UPLOAD_RESUME_FILE.toString(), String.valueOf(id));

        if (StringUtils.isBlank(profilePojoStr)) {
            throw ProfileException.REFERRAL_PROFILE_NOT_EXIST;
        } else {
            client.del(AppId.APPID_ALPHADOG.getValue(),
                    KeyIdentifier.EMPLOYEE_REFERRAL_PROFILE.toString(), String.valueOf(id));
        }

        JSONObject jsonObject = JSONObject.parseObject(profilePojoStr);

        logger.info("updateUserProfile,redis profilePojoStr:{}", profilePojoStr);
        ProfilePojo profilePojo = ProfilePojo.parseProfile(jsonObject, profileParseUtil.initParseProfileParam());
        logger.info("updateUserProfile profilePojo:{}", profilePojo);

        profilePojo.getUserRecord().setName(name);
        profilePojo.getUserRecord().setMobile(Long.parseLong(mobile));
        ProfileProfileDO profileProfileDO = profileEntity.getProfileByUserId(id);
        logger.info("开始执行插入操作================");
        if (profileProfileDO == null) {
            ProfileProfileRecord profileProfileRecord = new ProfileProfileRecord();
            profileProfileRecord.setUserId(id);
            profileProfileRecord.setDisable((byte) (Constant.ENABLE));
            profileProfileRecord.setUuid(UUID.randomUUID().toString());
            int source = ProfileSource.MOBILEReferral.getValue();
            if (profilePojo.getProfileRecord() != null) {
                if (profilePojo.getProfileRecord().getSource() != null) {
                    source = profilePojo.getProfileRecord().getSource();
                }
            }
            profileProfileRecord.setSource(source);
            profilePojo.setProfileRecord(profileProfileRecord);
            logger.info("开始保存的参数=====");
            int profileId = profileEntity.createProfile(profilePojo, userUserDO);
            return profileId;
        } else {
            profileEntity.updateProfile(profilePojo, profileProfileDO);
            return profileProfileDO.getId();
        }
    }

    @Override
    public ProfileDocParseResult parseHunterFileProfile(int headhunterId, String fileName, ByteBuffer fileData) {
        return abstractResumeFileParser.parseHunterResume(headhunterId,fileName,fileData);
    }
}
