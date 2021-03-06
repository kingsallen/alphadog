package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrSuperaccountApply;
import com.moseeker.baseorm.db.hrdb.tables.records.HrSuperaccountApplyRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrSuperaccountApplyDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* HrSuperaccountApplyDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class HrSuperaccountApplyDao extends JooqCrudImpl<HrSuperaccountApplyDO, HrSuperaccountApplyRecord> {

    public HrSuperaccountApplyDao() {
        super(HrSuperaccountApply.HR_SUPERACCOUNT_APPLY, HrSuperaccountApplyDO.class);
    }

    public HrSuperaccountApplyDao(TableImpl<HrSuperaccountApplyRecord> table, Class<HrSuperaccountApplyDO> hrSuperaccountApplyDOClass) {
        super(table, hrSuperaccountApplyDOClass);
    }

    public HrSuperaccountApplyDO getByCompanyId(int companyId) {

        return create
                .selectFrom(HrSuperaccountApply.HR_SUPERACCOUNT_APPLY)
                .where(HrSuperaccountApply.HR_SUPERACCOUNT_APPLY.COMPANY_ID.eq(companyId))
                .limit(1)
                .fetchOneInto(HrSuperaccountApplyDO.class);
    }
}
