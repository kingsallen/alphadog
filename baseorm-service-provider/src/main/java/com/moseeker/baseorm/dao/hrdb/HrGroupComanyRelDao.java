package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrGroupCompanyRel;
import com.moseeker.baseorm.db.hrdb.tables.records.HrGroupCompanyRelRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrGroupCompanyRelDO;

import org.jooq.impl.TableImpl;

/**
 * Created by YYF
 *
 * Date: 2017/6/28
 *
 * Project_name :alphadog
 */
public class HrGroupComanyRelDao extends JooqCrudImpl<HrGroupCompanyRelDO, HrGroupCompanyRelRecord> {

    public HrGroupComanyRelDao() {
        super(HrGroupCompanyRel.HR_GROUP_COMPANY_REL, HrGroupCompanyRelDO.class);
    }

    public HrGroupComanyRelDao(TableImpl<HrGroupCompanyRelRecord> table, Class<HrGroupCompanyRelDO> hrGroupCompanyRelDOClass) {
        super(table, hrGroupCompanyRelDOClass);
    }
}
