package com.moseeker.mq.dao;

import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.records.UserUserRecord;

@Service
public class UserDao extends BaseDaoImpl<UserUserRecord, UserUser> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserUser.USER_USER;
	}

}
