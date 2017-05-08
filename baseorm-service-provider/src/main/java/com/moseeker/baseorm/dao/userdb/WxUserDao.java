package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.thrift.gen.dao.struct.UserWxUserDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class WxUserDao extends JooqCrudImpl<UserWxUserDO, UserWxUserRecord> {

	public WxUserDao() {
		super(UserWxUser.USER_WX_USER, UserWxUserDO.class);
	}

	public WxUserDao(TableImpl<UserWxUserRecord> table, Class<UserWxUserDO> userWxUserDOClass) {
		super(table, userWxUserDOClass);
	}
}
