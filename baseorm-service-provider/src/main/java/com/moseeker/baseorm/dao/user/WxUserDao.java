package com.moseeker.baseorm.dao.user;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;

@Service
public class WxUserDao extends BaseDaoImpl<UserWxUserRecord, UserWxUser>{

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserWxUser.USER_WX_USER;
	}
}
