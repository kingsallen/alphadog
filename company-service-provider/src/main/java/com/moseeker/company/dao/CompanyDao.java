package com.moseeker.company.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;

import java.util.List;

public interface CompanyDao extends BaseDao<HrCompanyRecord> {

    List<HrCompanyRecord> getAllCompanies(CommonQuery query) throws Exception;

	boolean checkRepeatNameWithSuperCompany(String name);

	void transactionTest();

}
