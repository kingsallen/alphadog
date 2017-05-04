package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityMapRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class DictCityMapDao extends JooqCrudImpl<DictCityMapDO, DictCityMapRecord> {

	public DictCityMapDao(TableImpl<DictCityMapRecord> table, Class<DictCityMapDO> dictCityMapDOClass) {
		super(table, dictCityMapDOClass);
	}
}
