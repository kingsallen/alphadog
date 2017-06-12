package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrWxRule;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxRuleRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrWxRuleDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrWxRuleDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrWxRuleDao extends JooqCrudImpl<HrWxRuleDO, HrWxRuleRecord> {

    public HrWxRuleDao() {
        super(HrWxRule.HR_WX_RULE, HrWxRuleDO.class);
    }

    public HrWxRuleDao(TableImpl<HrWxRuleRecord> table, Class<HrWxRuleDO> hrWxRuleDOClass) {
        super(table, hrWxRuleDOClass);
    }
}
