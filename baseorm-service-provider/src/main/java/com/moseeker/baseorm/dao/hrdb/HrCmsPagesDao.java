package com.moseeker.baseorm.dao.hrdb;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCmsPages;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCmsPagesRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCmsPagesDO;

@Service
public class HrCmsPagesDao extends JooqCrudImpl<HrCmsPagesDO, HrCmsPagesRecord> {
	
	public HrCmsPagesDao() {
		super(HrCmsPages.HR_CMS_PAGES, HrCmsPagesDO.class);
		// TODO Auto-generated constructor stub
	}
	public HrCmsPagesDao(TableImpl<HrCmsPagesRecord> table, Class<HrCmsPagesDO> sClass) {
		super(table, sClass);
		// TODO Auto-generated constructor stub
	}

}
