package com.moseeker.dict.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.dict.pojo.DictCountryPojo;

import java.util.List;

/**
 * 国家字典
 * <p>
 *
 * Created by zzh on 16/5/25.
 */
public interface DictCountryDao extends BaseDao<DictCountryRecord> {

    public List<DictCountryPojo> getDictCountry() throws Exception;

}
