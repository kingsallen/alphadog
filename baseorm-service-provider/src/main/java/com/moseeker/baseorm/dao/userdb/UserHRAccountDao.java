package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.apache.thrift.TException;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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
@Repository
public class UserHRAccountDao extends JooqCrudImpl<UserHrAccountDO, UserHrAccountRecord> {

	public UserHRAccountDao(TableImpl<UserHrAccountRecord> table, Class<UserHrAccountDO> userHrAccountDOClass) {
		super(table, userHrAccountDOClass);
	}

	public List<UserHrAccountDO> listHRFromCompany(int comanyId) throws TException {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("company_id", String.valueOf(comanyId));
		return this.getDatas(qu);
	}

    public int deleteUserHrAccount(int id) {
		UserHrAccountRecord record = new UserHrAccountRecord();
		record.setId(id);
		try {
			return this.deleteRecord(record);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}
}
