package com.moseeker.baseorm.dao.dictdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.dictdb.tables.DictConstant;
import com.moseeker.baseorm.db.dictdb.tables.records.DictConstantRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictConstantDO;

/**
* @author xxx
* DictConstantDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictConstantDao extends StructDaoImpl<DictConstantDO, DictConstantRecord, DictConstant> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = DictConstant.DICT_CONSTANT;
   }
}
