package com.moseeker.useraccounts.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.userdb.tables.UserBdUser;
import com.moseeker.db.userdb.tables.records.UserBdUserRecord;

@Repository
public class UserBdUserDaoImpl extends BaseDaoImpl<UserBdUserRecord, UserBdUser> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserBdUser.USER_BD_USER;
	}
}
