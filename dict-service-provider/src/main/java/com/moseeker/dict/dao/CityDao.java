package com.moseeker.dict.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.dict.pojo.CityPojo;

import java.util.List;

public interface CityDao extends BaseDao<DictCityRecord> {
    List<CityPojo> getCities(int level);
    List<CityPojo> getCitiesById(int id);
}