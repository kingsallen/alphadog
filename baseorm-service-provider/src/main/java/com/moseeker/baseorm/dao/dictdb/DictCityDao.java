package com.moseeker.baseorm.dao.dictdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;

/**
* @author xxx
* DictCityDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictCityDao extends StructDaoImpl<DictCityDO, DictCityRecord, DictCity> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = DictCity.DICT_CITY;
   }
}
