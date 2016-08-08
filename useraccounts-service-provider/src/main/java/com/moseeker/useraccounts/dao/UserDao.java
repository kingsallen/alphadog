package com.moseeker.useraccounts.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.useraccounts.pojo.User;

/**
 * 用户接口类
 *
 * */
public interface UserDao extends BaseDao<UserUserRecord> {

    public void combineAccount(int orig, int dest) throws Exception;

    public User getUserById(long userId) throws Exception;

    public int createUser(com.moseeker.thrift.gen.useraccounts.struct.User user) throws Exception;

}
