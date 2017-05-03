package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.dao.struct.UserUserDO;
import org.springframework.stereotype.Service;

/**
 * 
 * HR帐号数据库持久类 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 9, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Service
public class UserDao extends StructDaoImpl<UserUserDO, UserUserRecord, UserUser> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserUser.USER_USER;
	}
}
