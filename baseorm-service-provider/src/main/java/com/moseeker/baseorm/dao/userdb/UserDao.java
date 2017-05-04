package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.dao.struct.UserUserDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
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
@Repository
public class UserDao extends JooqCrudImpl<UserUserDO, UserUserRecord> {

	public UserDao(TableImpl<UserUserRecord> table, Class<UserUserDO> userUserDOClass) {
		super(table, userUserDOClass);
	}
}
