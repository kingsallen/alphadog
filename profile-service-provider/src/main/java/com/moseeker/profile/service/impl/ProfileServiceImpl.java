package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.entity.biz.ProfilePojo;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.moseeker.baseorm.db.userdb.tables.UserUser.USER_USER;
import static com.moseeker.profile.exception.ProfileException.PROFILE_NOT_EXIST;
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
        if (profileProfileDO == null) {
            ProfileProfileRecord profileProfileRecord = new ProfileProfileRecord();
            profileProfileRecord.setUserId(userUserDO.getId());
            profileProfileRecord.setDisable((byte)(Constant.ENABLE));
            profileProfileRecord.setUuid(UUID.randomUUID().toString());
            profileProfileRecord.setSource(220);
            profilePojo.setProfileRecord(profileProfileRecord);
            return profileEntity.createProfile(profilePojo, userUserDO);
        } else {
            profileEntity.updateProfile(profilePojo, profileProfileDO);
            return profileProfileDO.getId();
        }
    }
}
