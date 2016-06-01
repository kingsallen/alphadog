package com.moseeker.position.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.userdb.tables.records.UserUserRecord;

public interface UserDao extends BaseDao<UserUserRecord> {

	List<UserUserRecord> getUserByIds(List<Integer> ids);

	UserUserRecord getUserById(int intValue);

}
