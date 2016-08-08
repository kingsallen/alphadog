package com.moseeker.application.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.hrdb.tables.records.HrCompanyConfRecord;

public interface HrCompanyConfDao extends BaseDao<HrCompanyConfRecord> {
	HrCompanyConfRecord getHrCompanyConfRecordByCompanyId(int companyId);
}
