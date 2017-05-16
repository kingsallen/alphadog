package com.moseeker.useraccounts.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.userdb.tables.records.UserHrAccountRecord;

/**
 * HR账号
 * <p>
 *
 * Created by zzh on 16/5/31.
 */
public interface UserHrDao extends BaseDao<UserHrAccountRecord> {

	/**
	 * 创建hr帐号，需要创建user_hr_account, hr_company, hr_points_conf
	 * @param userHrAccountRecord
	 * @param companyRecord
	 * @return
	 * @throws Exception
	 */
	int createHRAccount(UserHrAccountRecord userHrAccountRecord, HrCompanyRecord companyRecord) throws Exception;

	boolean verifyCompanyName(String company_name) throws Exception;

	boolean verifyRepeatMobile(String mobile) throws Exception;
}
