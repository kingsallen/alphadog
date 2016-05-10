package com.moseeker.useraccounts.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseJooqDaoImpl;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.records.UserUserRecord;

@Repository
public class UserDaoImpl extends BaseJooqDaoImpl<UserUserRecord, UserUser> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserUser.USER_USER;
	}
}
