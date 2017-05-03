package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyDao extends StructDaoImpl<HrCompanyDO, HrCompanyRecord, HrCompany> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = HrCompany.HR_COMPANY;
	}

	public HrCompanyDO getCompany(Query query) {
		HrCompanyDO company = new HrCompanyDO();
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

	public List<Hrcompany> getCompanies(Query query) {
		List<Hrcompany> companies = new ArrayList<>();
		
		try {
			List<HrCompanyRecord> records = getResources(query);
			if(records != null && records.size() > 0) {
				companies = BeanUtils.DBToStruct(Hrcompany.class, records);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		
		return companies;
	}


}
