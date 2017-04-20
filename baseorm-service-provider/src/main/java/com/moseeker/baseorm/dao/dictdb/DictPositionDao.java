package com.moseeker.baseorm.dao.dictdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.dictdb.tables.DictPosition;
import com.moseeker.baseorm.db.dictdb.tables.records.DictPositionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictPositionDO;

/**
* @author xxx
* DictPositionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictPositionDao extends StructDaoImpl<DictPositionDO, DictPositionRecord, DictPosition> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = DictPosition.DICT_POSITION;
   }
}
