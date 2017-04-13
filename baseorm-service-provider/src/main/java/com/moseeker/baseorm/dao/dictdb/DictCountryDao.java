package com.moseeker.baseorm.dao.dictdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.dictdb.tables.DictCountry;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCountryRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCountryDO;

/**
* @author xxx
* DictCountryDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictCountryDao extends StructDaoImpl<DictCountryDO, DictCountryRecord, DictCountry> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = DictCountry.DICT_COUNTRY;
   }
}
