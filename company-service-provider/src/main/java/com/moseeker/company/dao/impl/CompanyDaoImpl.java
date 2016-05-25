package com.moseeker.company.dao.impl;

import org.springframework.stereotype.Repository;

import com.moseeker.common.providerutils.daoutils.BaseJooqDaoImpl;
import com.moseeker.company.dao.CompanyDao;
import com.moseeker.db.hrdb.tables.HrCompany;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;;

@Repository
public class CompanyDaoImpl extends BaseJooqDaoImpl<HrCompanyRecord, HrCompany> implements CompanyDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = HrCompany.HR_COMPANY;
	}
}
