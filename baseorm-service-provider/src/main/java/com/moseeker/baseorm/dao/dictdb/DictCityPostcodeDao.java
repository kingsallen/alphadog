package com.moseeker.baseorm.dao.dictdb;

import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCityPostcode;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityPostcodeRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityPostcodeDO;
@Repository
public class DictCityPostcodeDao extends JooqCrudImpl<DictCityPostcodeDO, DictCityPostcodeRecord> {

    public DictCityPostcodeDao() {
        super(DictCityPostcode.DICT_CITY_POSTCODE, DictCityPostcodeDO.class);
    }

	public DictCityPostcodeDao(TableImpl<DictCityPostcodeRecord> table, Class<DictCityPostcodeDO> dictCityPostcodeDOClass) {
		super(table, dictCityPostcodeDOClass);
	}
	public DictCityPostcodeRecord fuzzyGetCityPostCode(String postCode) {
        DictCityPostcodeRecord dictCityPostcodeRecord = null;
        if (postCode != null) {
                Condition condition = DictCityPostcode.DICT_CITY_POSTCODE.field(DictCityPostcode.DICT_CITY_POSTCODE.POSTCODE).like(postCode + "%");
                Result<Record> result = create.select().from(DictCityPostcode.DICT_CITY_POSTCODE).where(condition).fetch();
                if (result != null && result.size() > 0) {
                    dictCityPostcodeRecord = (DictCityPostcodeRecord) result.get(0);
                }
            } 
               
        return dictCityPostcodeRecord;
    }

}
