package com.moseeker.baseorm.dao.dictdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.dictdb.tables.DictIndustryType;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryTypeRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictIndustryTypeDO;

/**
* @author xxx
* DictIndustryTypeDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictIndustryTypeDao extends StructDaoImpl<DictIndustryTypeDO, DictIndustryTypeRecord, DictIndustryType> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = DictIndustryType.DICT_INDUSTRY_TYPE;
   }
}
