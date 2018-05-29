package com.moseeker.entity.biz;

import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryTypeRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictIndustryTypeDO;

import java.util.List;

public class ProfileExtParam {

    private List<DictCountryDO> countryDOList;

    private List<DictIndustryTypeRecord> dictIndustryTypeDOList;

    public List<DictCountryDO> getCountryDOList() {
        return countryDOList;
    }

    public void setCountryDOList(List<DictCountryDO> countryDOList) {
        this.countryDOList = countryDOList;
    }

    public List<DictIndustryTypeRecord> getDictIndustryTypeDOList() {
        return dictIndustryTypeDOList;
    }

    public void setDictIndustryTypeDOList(List<DictIndustryTypeRecord> dictIndustryTypeDOList) {
        this.dictIndustryTypeDOList = dictIndustryTypeDOList;
    }
}
