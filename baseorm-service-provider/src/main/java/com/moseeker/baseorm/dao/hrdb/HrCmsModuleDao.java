package com.moseeker.baseorm.dao.hrdb;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCmsModule;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCmsModuleRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCmsModuleDO;
@Service
public class HrCmsModuleDao extends JooqCrudImpl<HrCmsModuleDO, HrCmsModuleRecord> {
	
	public HrCmsModuleDao() {
		super(HrCmsModule.HR_CMS_MODULE, HrCmsModuleDO.class);
		// TODO Auto-generated constructor stub
	}
	public HrCmsModuleDao(TableImpl<HrCmsModuleRecord> table, Class<HrCmsModuleDO> sClass) {
		super(table, sClass);
		// TODO Auto-generated constructor stub
	}

}
