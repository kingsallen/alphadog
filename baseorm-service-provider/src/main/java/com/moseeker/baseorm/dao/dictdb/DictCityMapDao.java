package com.moseeker.baseorm.dao.dictdb;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.dictdb.tables.DictCityMap;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityMapRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;

@Service
public class DictCityMapDao extends BaseDaoImpl<DictCityMapRecord, DictCityMap>{

	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=DictCityMap.DICT_CITY_MAP;
	}

}
