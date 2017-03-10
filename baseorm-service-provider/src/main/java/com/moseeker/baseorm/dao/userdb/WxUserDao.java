package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.UserWxUserDO;
import org.springframework.stereotype.Repository;

@Repository
public class WxUserDao extends StructDaoImpl<UserWxUserDO, UserWxUserRecord, UserWxUser> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserWxUser.USER_WX_USER;
	}
}
