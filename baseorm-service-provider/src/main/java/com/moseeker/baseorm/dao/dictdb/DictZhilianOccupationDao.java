package com.moseeker.baseorm.dao.dictdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.dictdb.tables.DictZhilianOccupation;
import com.moseeker.baseorm.db.dictdb.tables.records.DictZhilianOccupationRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictZhilianOccupationDO;

/**
* @author xxx
* DictZhilianOccupationDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictZhilianOccupationDao extends StructDaoImpl<DictZhilianOccupationDO, DictZhilianOccupationRecord, DictZhilianOccupation> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = DictZhilianOccupation.DICT_ZHILIAN_OCCUPATION;
   }
}
