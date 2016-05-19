package com.moseeker.dict.dao.impl;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.dict.dao.CityDao;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.db.dictdb.tables.DictCity;
import org.springframework.stereotype.Repository;

@Repository
public class CityDaoImpl extends BaseDaoImpl<DictCityRecord, DictCity> implements CityDao {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = DictCity.DICT_CITY;
        System.out.println(this.tableLike);
    }

}