package com.moseeker.baseorm.dao.hrdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.hrdb.tables.HrWxRule;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxRuleRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxRuleDO;

/**
* @author xxx
* HrWxRuleDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxRuleDao extends StructDaoImpl<HrWxRuleDO, HrWxRuleRecord, HrWxRule> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = HrWxRule.HR_WX_RULE;
   }
}
