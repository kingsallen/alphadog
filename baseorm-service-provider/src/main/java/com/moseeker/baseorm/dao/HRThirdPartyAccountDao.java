package com.moseeker.baseorm.dao;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartAccount;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartAccountRecord;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
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
public class HRThirdPartyAccountDao extends BaseDaoImpl<HrThirdPartAccountRecord, HrThirdPartAccount> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = HrThirdPartAccount.HR_THIRD_PART_ACCOUNT;
	}

}
