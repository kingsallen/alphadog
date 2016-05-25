package com.moseeker.profile.dao;

import java.util.List;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;

public interface CityDao extends BaseDao<DictCityRecord> {

	List<DictCityRecord> getCitiesByCodes(List<Integer> cityCodes);

	DictCityRecord getCityByCode(int city_code);

}
