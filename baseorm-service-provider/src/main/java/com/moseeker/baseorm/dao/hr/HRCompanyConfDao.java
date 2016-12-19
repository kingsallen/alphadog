package com.moseeker.baseorm.dao.hr;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrCompanyConf;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
@Service
public class HRCompanyConfDao extends BaseDaoImpl<HrCompanyConfRecord, HrCompanyConf>{

	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=HrCompanyConf.HR_COMPANY_CONF;
	}


}
