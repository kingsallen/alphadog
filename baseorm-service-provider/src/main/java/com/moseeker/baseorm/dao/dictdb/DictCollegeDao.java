package com.moseeker.baseorm.dao.dictdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.dictdb.tables.DictCollege;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCollegeDO;

/**
* @author xxx
* DictCollegeDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class DictCollegeDao extends StructDaoImpl<DictCollegeDO, DictCollegeRecord, DictCollege> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = DictCollege.DICT_COLLEGE;
   }
}
