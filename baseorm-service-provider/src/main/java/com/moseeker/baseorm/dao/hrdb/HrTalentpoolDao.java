package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrTalentpool;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTalentpoolRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrTalentpoolDO;

/**
* @author xxx
* HrTalentpoolDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrTalentpoolDao extends StructDaoImpl<HrTalentpoolDO, HrTalentpoolRecord, HrTalentpool> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrTalentpool.HR_TALENTPOOL;
   }
}
