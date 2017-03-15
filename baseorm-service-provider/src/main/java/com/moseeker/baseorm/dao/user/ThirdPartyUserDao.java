package com.moseeker.baseorm.dao.user;

import com.moseeker.baseorm.db.userdb.tables.UserThirdpartyUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserThirdpartyUserRecord;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import org.springframework.stereotype.Service;

/**
 * Created by eddie on 2017/3/7.
 */
@Service
public class ThirdPartyUserDao extends BaseDaoImpl<UserThirdpartyUserRecord, UserThirdpartyUser> {
    @Override
    protected void initJOOQEntity() {
        tableLike = UserThirdpartyUser.USER_THIRDPARTY_USER;
    }
}
