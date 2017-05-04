package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyConfRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyConfDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class HRCompanyConfDao extends JooqCrudImpl<HrCompanyConfDO, HrCompanyConfRecord> {

	public HRCompanyConfDao(TableImpl<HrCompanyConfRecord> table, Class<HrCompanyConfDO> hrCompanyConfDOClass) {
		super(table, hrCompanyConfDOClass);
	}
}
