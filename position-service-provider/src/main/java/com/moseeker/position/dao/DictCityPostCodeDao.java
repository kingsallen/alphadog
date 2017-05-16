package com.moseeker.position.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictCityPostcodeRecord;

/**
 * Created by yuyunfeng on 2017/3/16.
 */
public interface DictCityPostCodeDao extends BaseDao<DictCityPostcodeRecord> {

    /**
     * 通过邮编的前四位查询城市
     *
     * @param postCode
     * @return
     */
    DictCityPostcodeRecord fuzzyGetCityPostCode(String postCode);
}
