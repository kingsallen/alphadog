package com.moseeker.useraccounts.dao.impl;

import java.sql.Connection;

import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.moseeker.common.constants.Constant;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.hrdb.tables.HrCompany;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.db.userdb.tables.UserHrAccount;
import com.moseeker.db.userdb.tables.records.UserHrAccountRecord;
import com.moseeker.useraccounts.dao.UserHrAccountDao;

/**
 * HR账号
 * <p>
 *
 * Created by zzh on 16/5/31.
 */
@Repository
public class UserHrAccountDaoImpl extends BaseDaoImpl<UserHrAccountRecord, UserHrAccount> implements UserHrAccountDao {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserHrAccount.USER_HR_ACCOUNT;
	}

	@Override
	public int createHRAccount(UserHrAccountRecord userHrAccountRecord, HrCompanyRecord companyRecord)
			throws Exception {
		int result = 0;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

			Result<Record1<Integer>> repeatAccount = create.selectCount().from(UserHrAccount.USER_HR_ACCOUNT)
					.where(UserHrAccount.USER_HR_ACCOUNT.MOBILE.equal(userHrAccountRecord.getMobile())).fetch();
			if (repeatAccount != null && repeatAccount.size() > 0
					&& repeatAccount.get(0).value1() != null
					&& repeatAccount.get(0).value1() > 0) {
			} else {
				create.attach(userHrAccountRecord);
				int insertResult = userHrAccountRecord.insert();
				if (insertResult > 0) {
					result = userHrAccountRecord.getId().intValue();
					Result<Record1<Integer>> verifyCompanyNameResult = create.selectCount().from(HrCompany.HR_COMPANY)
							.join(UserHrAccount.USER_HR_ACCOUNT)
							.on(HrCompany.HR_COMPANY.HRACCOUNT_ID.equal(UserHrAccount.USER_HR_ACCOUNT.ID))
							.where(UserHrAccount.USER_HR_ACCOUNT.ACCOUNT_TYPE.equal(Constant.ACCOUNT_TYPE_SUPERACCOUNT))
							.and(HrCompany.HR_COMPANY.NAME.equal(companyRecord.getName())).fetch();
					if (verifyCompanyNameResult != null && verifyCompanyNameResult.size() > 0
							&& verifyCompanyNameResult.get(0).value1() != null
							&& verifyCompanyNameResult.get(0).value1() > 0) {
					} else {
						create.attach(companyRecord);
						companyRecord.setHraccountId(userHrAccountRecord.getId());
						companyRecord.insert();
						userHrAccountRecord.setCompanyId(companyRecord.getId().intValue());
						userHrAccountRecord.update();
					}
				}
			}
		} catch (Exception e) {
			conn.rollback();
			logger.error(e.getMessage(), e);
		} finally {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
		return result;
	}

	@Override
	public boolean verifyCompanyName(String company_name) throws Exception {
		boolean verifyCompany = false;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			Result<Record1<Integer>> result = create.selectCount().from(HrCompany.HR_COMPANY)
					.join(UserHrAccount.USER_HR_ACCOUNT)
					.on(HrCompany.HR_COMPANY.HRACCOUNT_ID.equal(UserHrAccount.USER_HR_ACCOUNT.ID))
					.where(UserHrAccount.USER_HR_ACCOUNT.ACCOUNT_TYPE.equal(Constant.ACCOUNT_TYPE_SUPERACCOUNT))
					.and(HrCompany.HR_COMPANY.NAME.equal(company_name)).fetch();
			if (result != null && result.size() > 0 && result.get(0).value1() != null && result.get(0).value1() > 0) {
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			conn.rollback();
		} finally {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
		return verifyCompany;
	}

	@Override
	public boolean verifyRepeatMobile(String mobile) throws Exception {
		boolean repeatMobile = false;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
	
			Result<Record1<Integer>> repeatAccount = create.selectCount().from(UserHrAccount.USER_HR_ACCOUNT)
					.where(UserHrAccount.USER_HR_ACCOUNT.MOBILE.equal(mobile)).fetch();
			if (repeatAccount != null && repeatAccount.size() > 0
					&& repeatAccount.get(0).value1() != null
					&& repeatAccount.get(0).value1() > 0) {
				repeatMobile = true;
			}
		}  catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
		return repeatMobile;
	}
}
