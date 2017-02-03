package com.moseeker.baseorm.dao.hr;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.company.struct.Hrcompany;

import java.util.List;
import java.util.ArrayList;

@Service
public class CompanyDao extends BaseDaoImpl<HrCompanyRecord, HrCompany> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = HrCompany.HR_COMPANY;
	}

	public Hrcompany getCompany(CommonQuery query) {
		Hrcompany company = new Hrcompany();
		try {
			HrCompanyRecord record = getResource(query);
			record.into(company);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return company;
	}

	public List<Hrcompany> getCompanies(CommonQuery query) {
		List<Hrcompany> companies = new ArrayList<Hrcompany>();
		try {
			List<HrCompanyRecord> records = getResources(query);
			for (HrCompanyRecord r : records) {
				Hrcompany e = new Hrcompany();
				companies.add(r.into(e));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return companies;
	}
}
