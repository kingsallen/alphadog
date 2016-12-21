package com.moseeker.baseorm.dao.hr;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.company.struct.Hrcompany;

@Service
public class CompanyDao extends BaseDaoImpl<HrCompanyRecord, HrCompany> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = HrCompany.HR_COMPANY;
	}

	public Hrcompany getCompany(CommonQuery query) {
		Hrcompany company = new Hrcompany();
		try {
			HrCompanyRecord record = this.getResource(query);
			record.into(company);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return company;
	}


}