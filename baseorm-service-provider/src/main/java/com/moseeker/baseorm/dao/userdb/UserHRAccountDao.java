package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.db.userdb.tables.UserHrAccount;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.thrift.gen.dao.struct.UserHrAccountDO;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 
 * HR帐号数据库持久类 
 * <p>Company: MoSeeker</P>  
 * <p>date: Nov 9, 2016</p>  
 * <p>Email: wjf2255@gmail.com</p>
 * @author wjf
 * @version
 */
@Component
public class UserHRAccountDao extends StructDaoImpl<UserHrAccountDO, UserHrAccountRecord, UserHrAccount> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserHrAccount.USER_HR_ACCOUNT;
	}


	public List<UserHrAccountDO> listHRFromCompany(int comanyId) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("company_id", String.valueOf(comanyId));
		return this.listResources(qu);
	}

    public int deleteUserHrAccount(int id) {
		UserHrAccountRecord record = new UserHrAccountRecord();
		record.setId(id);
		try {
			return this.delResource(record);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}
}
