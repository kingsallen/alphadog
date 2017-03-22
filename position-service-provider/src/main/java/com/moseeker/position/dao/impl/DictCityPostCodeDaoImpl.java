package com.moseeker.position.dao.impl;

import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.dictdb.tables.DictCityPostcode;
import com.moseeker.db.dictdb.tables.records.DictCityPostcodeRecord;
import com.moseeker.position.dao.DictCityPostCodeDao;
import org.springframework.stereotype.Repository;

/**
 * Created by yuyunfeng on 2017/3/16.
 */
@Repository
public class DictCityPostCodeDaoImpl extends BaseDaoImpl<DictCityPostcodeRecord, DictCityPostcode> implements DictCityPostCodeDao {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = DictCityPostcode.DICT_CITY_POSTCODE;
    }
}
