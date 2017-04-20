package com.moseeker.baseorm.dao.dictdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.dictdb.tables.DictMajor;
import com.moseeker.baseorm.db.dictdb.tables.records.DictMajorRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictMajorDO;

/**
* @author xxx
* DictMajorDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictMajorDao extends StructDaoImpl<DictMajorDO, DictMajorRecord, DictMajor> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = DictMajor.DICT_MAJOR;
   }
}
