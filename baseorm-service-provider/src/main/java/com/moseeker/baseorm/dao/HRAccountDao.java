package com.moseeker.baseorm.dao;

import org.springframework.stereotype.Service;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.userdb.tables.UserHrAccount;
import com.moseeker.db.userdb.tables.records.UserHrAccountRecord;
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
public class HRAccountDao extends BaseDaoImpl<UserHrAccountRecord, UserHrAccount> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserHrAccount.USER_HR_ACCOUNT;
	}

}
