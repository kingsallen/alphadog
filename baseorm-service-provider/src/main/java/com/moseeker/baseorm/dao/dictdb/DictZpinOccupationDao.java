package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.records.DictZhilianOccupationRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictZhilianOccupationDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class DictZpinOccupationDao extends JooqCrudImpl<DictZhilianOccupationDO, DictZhilianOccupationRecord> {

	public DictZpinOccupationDao(TableImpl<DictZhilianOccupationRecord> table, Class<DictZhilianOccupationDO> dictZhilianOccupationDOClass) {
		super(table, dictZhilianOccupationDOClass);
	}
}
