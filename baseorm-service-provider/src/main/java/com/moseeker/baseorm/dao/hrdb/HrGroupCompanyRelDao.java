package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrGroupCompanyRel;
import com.moseeker.baseorm.db.hrdb.tables.records.HrGroupCompanyRelRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrGroupCompanyRelDO;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by YYF
 *
 * Date: 2017/6/28
 *
 * Project_name :alphadog
 */
@Repository
public class HrGroupCompanyRelDao extends JooqCrudImpl<HrGroupCompanyRelDO, HrGroupCompanyRelRecord> {

    public HrGroupCompanyRelDao() {
        super(HrGroupCompanyRel.HR_GROUP_COMPANY_REL, HrGroupCompanyRelDO.class);
    }

    public HrGroupCompanyRelDao(TableImpl<HrGroupCompanyRelRecord> table, Class<HrGroupCompanyRelDO> hrGroupCompanyRelDOClass) {
        super(table, hrGroupCompanyRelDOClass);
    }
}
