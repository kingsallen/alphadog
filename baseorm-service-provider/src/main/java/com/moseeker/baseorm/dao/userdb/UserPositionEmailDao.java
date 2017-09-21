package com.moseeker.baseorm.dao.userdb;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.thrift.gen.dao.struct.userdb.UserPositionEmailDO;
import com.moseeker.baseorm.db.userdb.tables.UserPositionEmail;
import com.moseeker.baseorm.db.userdb.tables.records.UserPositionEmailRecord;
@Repository
public class UserPositionEmailDao extends JooqCrudImpl<UserPositionEmailDO,UserPositionEmailRecord>{
	
	public UserPositionEmailDao(){
		super(UserPositionEmail.USER_POSITION_EMAIL,UserPositionEmailDO.class);
	}
	public UserPositionEmailDao(TableImpl<UserPositionEmailRecord> table, Class<UserPositionEmailDO> sClass) {
		super(table, sClass);
	}
	
	public int insertOrUpdateData(int userId,String conditions){
		int result=create.insertInto(UserPositionEmail.USER_POSITION_EMAIL, UserPositionEmail.USER_POSITION_EMAIL.USER_ID, UserPositionEmail.USER_POSITION_EMAIL.CONDITIONS)
		.values(userId, conditions)
		.onDuplicateKeyUpdate()
		.set(UserPositionEmail.USER_POSITION_EMAIL.USER_ID, userId)
		.set(UserPositionEmail.USER_POSITION_EMAIL.CONDITIONS,conditions)
		.execute();
		return result;
	}

}
