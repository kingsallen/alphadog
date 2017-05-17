package com.moseeker.baseorm.dao.dictdb;

import org.jooq.impl.TableImpl;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCityPostcode;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityPostcodeRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityPostcodeDO;

public class DictCityPostcodeDao extends JooqCrudImpl<DictCityPostcodeDO, DictCityPostcodeRecord> {

    public DictCityPostcodeDao() {
        super(DictCityPostcode.DICT_CITY_POSTCODE, DictCityPostcodeDO.class);
    }

	public DictCityPostcodeDao(TableImpl<DictCityPostcodeRecord> table, Class<DictCityPostcodeDO> dictCityPostcodeDOClass) {
		super(table, dictCityPostcodeDOClass);
	}

}
