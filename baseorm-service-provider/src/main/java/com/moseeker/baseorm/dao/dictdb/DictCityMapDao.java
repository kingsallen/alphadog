package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.dictdb.tables.DictCityMap;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityMapRecord;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import com.moseeker.thrift.gen.dict.struct.CityMap;

import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class DictCityMapDao extends JooqCrudImpl<DictCityMapDO, DictCityMapRecord> {

    public DictCityMapDao() {
        super(DictCityMap.DICT_CITY_MAP, DictCityMapDO.class);
    }

	public DictCityMapDao(TableImpl<DictCityMapRecord> table, Class<DictCityMapDO> dictCityMapDOClass) {
		super(table, dictCityMapDOClass);
	}
	/*
	 * 
	 */
	public CityMap getDictMap(Query query) throws TException {
		CityMap cityMap = new CityMap();
		try {
			DictCityMapRecord record = this.getRecord(query);
			if(record != null) {
				cityMap.setId(record.getId());
				if(record.getChannel() != null) {
					cityMap.setChannel(record.getChannel());
				}
				if(record.getCode() != null)
					cityMap.setCode(record.getCode());
				if(record.getCodeOther() != null)
					cityMap.setCode_other(record.getCodeOther());
				if(record.getCreateTime() != null)
					cityMap.setCreate_time((new DateTime(record.getCreateTime().getTime())).toString("yyyy-MM-dd HH:mm:ss"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//do nothing
		}
		return cityMap;
	}
}
