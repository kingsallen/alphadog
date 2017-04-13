package com.moseeker.baseorm.dao.dictdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.dictdb.tables.DictIndustry;
import com.moseeker.baseorm.db.dictdb.tables.records.DictIndustryRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictIndustryDO;

/**
* @author xxx
* DictIndustryDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictIndustryDao extends StructDaoImpl<DictIndustryDO, DictIndustryRecord, DictIndustry> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = DictIndustry.DICT_INDUSTRY;
   }
}
