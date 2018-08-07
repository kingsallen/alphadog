package com.moseeker.entity.biz;

import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryTypeRecord;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCollegeDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictIndustryTypeDO;

import java.util.List;
import java.util.Map;

public class ProfileExtParam {

    private List<DictCountryDO> countryDOList;

    private List<DictIndustryTypeRecord> dictIndustryTypeDOList;

    private Map<String, DictCollegeDO> collegeMap;

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

    public Map<String, DictCollegeDO> getCollegeMap() {
        return collegeMap;
    }

    public void setCollegeMap(Map<String, DictCollegeDO> collegeMap) {
        this.collegeMap = collegeMap;
    }
}
