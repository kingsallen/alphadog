package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrSuperaccountApply;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSuperaccountApplyRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrSuperaccountApplyDO;

/**
* @author xxx
* HrSuperaccountApplyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrSuperaccountApplyDao extends StructDaoImpl<HrSuperaccountApplyDO, HrSuperaccountApplyRecord, HrSuperaccountApply> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrSuperaccountApply.HR_SUPERACCOUNT_APPLY;
   }
}
