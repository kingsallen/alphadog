package com.moseeker.profile.dao.impl;

import org.springframework.stereotype.Repository;

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
}
