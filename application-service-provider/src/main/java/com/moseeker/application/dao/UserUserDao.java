package com.moseeker.application.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.userdb.tables.records.UserUserRecord;

/**
 * 用户数据
 *
 * Created by zzh on 16/8/16.
 */
public interface UserUserDao extends BaseDao<UserUserRecord> {
    public UserUserRecord getUserUserRecord(long userId) throws Exception;
}
