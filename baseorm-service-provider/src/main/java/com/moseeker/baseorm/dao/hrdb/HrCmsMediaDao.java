package com.moseeker.baseorm.dao.hrdb;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrCmsMedia;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCmsMediaRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCmsMediaDO;
@Service
public class HrCmsMediaDao extends JooqCrudImpl<HrCmsMediaDO, HrCmsMediaRecord> {
	
	public HrCmsMediaDao() {
		super(HrCmsMedia.HR_CMS_MEDIA, HrCmsMediaDO.class);
		// TODO Auto-generated constructor stub
	}
	public HrCmsMediaDao(TableImpl<HrCmsMediaRecord> table, Class<HrCmsMediaDO> sClass) {
		super(table, sClass);
		// TODO Auto-generated constructor stub
	}

}
