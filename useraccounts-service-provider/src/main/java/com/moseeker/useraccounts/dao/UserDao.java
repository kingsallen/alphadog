package com.moseeker.useraccounts.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.userdb.tables.records.UserUserRecord;

public interface UserDao extends BaseDao<UserUserRecord> {

	public void combineAccount(List<String> tables, String column, int orig, int dest) throws Exception;
}
