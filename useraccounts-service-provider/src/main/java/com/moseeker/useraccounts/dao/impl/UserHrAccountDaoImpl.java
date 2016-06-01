package com.moseeker.useraccounts.dao.impl;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.userdb.tables.UserHrAccount;
import com.moseeker.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.useraccounts.dao.UserHrAccountDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * HR账号
 * <p>
 *
 * Created by zzh on 16/5/31.
 */
@Repository
public class UserHrAccountDaoImpl extends BaseDaoImpl<UserHrAccountRecord, UserHrAccount> implements UserHrAccountDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = UserHrAccount.USER_HR_ACCOUNT;
    }
}
