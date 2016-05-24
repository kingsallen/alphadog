package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.hrdb.tables.HrCompany;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.profile.dao.CompanyDao;

@Repository
public class HrCompanyDaoImpl extends
		BaseDaoImpl<HrCompanyRecord, HrCompany> implements
		CompanyDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = HrCompany.HR_COMPANY;
	}

	@Override
	public List<HrCompanyRecord> getCompaniesByIds(List<Integer> companyIds) {
		List<HrCompanyRecord> records = new ArrayList<>();
		Connection conn = null;
		try {
			if(companyIds != null && companyIds.size() > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				SelectWhereStep<HrCompanyRecord> select = create.selectFrom(HrCompany.HR_COMPANY);
				SelectConditionStep<HrCompanyRecord> selectCondition = null;
				for(int i=0; i<companyIds.size(); i++) {
					if(i == 0) {
						selectCondition = select.where(HrCompany.HR_COMPANY.ID.equal(UInteger.valueOf(companyIds.get(i))));
					} else {
						selectCondition.or(HrCompany.HR_COMPANY.ID.equal(UInteger.valueOf(companyIds.get(i))));
					}
				}
				records = selectCondition.fetch();
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
		return records;
	}

	@Override
	public HrCompanyRecord getCompanyById(int companyId) {
		HrCompanyRecord record = null;
		Connection conn = null;
		try {
			if(companyId > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Result<HrCompanyRecord> result = create.selectFrom(HrCompany.HR_COMPANY)
						.where(HrCompany.HR_COMPANY.ID.equal(UInteger.valueOf(companyId)))
						.limit(1).fetch();
				if(result != null && result.size() > 0) {
					record = result.get(0);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
		return record;
	}
}
