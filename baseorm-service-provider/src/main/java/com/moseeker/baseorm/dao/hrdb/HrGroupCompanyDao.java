package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrGroupCompany;
import com.moseeker.baseorm.db.hrdb.tables.records.HrGroupCompanyRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrGroupCompanyDO;

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
public class HrGroupCompanyDao extends JooqCrudImpl<HrGroupCompanyDO, HrGroupCompanyRecord> {

    public HrGroupCompanyDao() {
        super(HrGroupCompany.HR_GROUP_COMPANY, HrGroupCompanyDO.class);
    }

    public HrGroupCompanyDao(TableImpl<HrGroupCompanyRecord> table, Class<HrGroupCompanyDO> hrGroupCompanyDOClass) {
        super(table, hrGroupCompanyDOClass);
    }
}
