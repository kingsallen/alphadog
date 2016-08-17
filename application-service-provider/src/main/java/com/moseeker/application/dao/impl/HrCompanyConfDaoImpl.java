package com.moseeker.application.dao.impl;

import com.moseeker.application.dao.HrCompanyConfDao;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.hrdb.tables.HrCompanyConf;
import com.moseeker.db.hrdb.tables.records.HrCompanyConfRecord;
import org.jooq.DSLContext;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 获取公司配置信息
 * <p>
 *
 * Created by zzh on 16/5/24.
 */
@Repository
public class HrCompanyConfDaoImpl extends
		BaseDaoImpl<HrCompanyConfRecord, HrCompanyConf> implements
		HrCompanyConfDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = HrCompanyConf.HR_COMPANY_CONF;
	}

	/**
	 * 获取公司配置信息
	 *
	 * @param companyId 公司ID
	 * @return
     */
	@Override
	public HrCompanyConfRecord getHrCompanyConfRecordByCompanyId(int companyId) {
		HrCompanyConfRecord record = null;
		Connection conn = null;
		try {
			if(companyId > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				record = create.selectFrom(HrCompanyConf.HR_COMPANY_CONF)
						.where(HrCompanyConf.HR_COMPANY_CONF.COMPANY_ID.equal(UInteger.valueOf(companyId)))
						.limit(1).fetchOne();
			}
		} catch (Exception e) {
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
