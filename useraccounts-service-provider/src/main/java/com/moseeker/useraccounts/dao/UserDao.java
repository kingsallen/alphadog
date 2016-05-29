package com.moseeker.useraccounts.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.useraccounts.pojo.User;

import java.util.List;

/**
 * 用户接口类
 *
 * */
public interface UserDao extends BaseDao<UserUserRecord> {

    public void combineAccount(List<String> tables, String column, int orig, int dest) throws Exception;
    public User getUserById(long userId) throws Exception;
}
