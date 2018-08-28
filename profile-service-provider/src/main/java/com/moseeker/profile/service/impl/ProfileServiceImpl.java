package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
