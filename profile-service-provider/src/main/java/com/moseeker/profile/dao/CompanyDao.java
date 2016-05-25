package com.moseeker.profile.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;



public interface CompanyDao extends BaseDao<HrCompanyRecord> {

	List<HrCompanyRecord> getCompaniesByIds(List<Integer> companyIds);

	HrCompanyRecord getCompanyById(int intValue);

}
