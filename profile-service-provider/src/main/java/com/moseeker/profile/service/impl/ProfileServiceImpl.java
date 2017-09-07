package com.moseeker.profile.service.impl;

import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.exception.CommonException;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.ProfileEntity;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.moseeker.baseorm.db.userdb.tables.UserUser.USER_USER;
import static com.moseeker.profile.exception.ProfileException.PROFILE_USER_NOTEXIST;

/**
 * 简历服务
 * Created by jack on 07/09/2017.
 */
@Service
@CounterIface
public class ProfileServiceImpl implements com.moseeker.profile.service.ProfileService {

    @Autowired
    ProfileEntity profileEntity;

    @Autowired
    UserUserDao userUserDao;

    @Override
    public int upsertProfile(int userId, String profileParameter) throws CommonException {
        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(USER_USER.ID.getName(), userId);
        UserUserDO userUserDO = userUserDao.getData(queryBuilder.buildQuery());
        if (userUserDO == null) {
            throw PROFILE_USER_NOTEXIST;
        }
        profileEntity.upsertProfile(userId, profileParameter);
        return 0;
    }
}
