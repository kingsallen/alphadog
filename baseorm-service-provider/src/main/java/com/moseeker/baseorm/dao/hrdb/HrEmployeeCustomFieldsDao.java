package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCustomFields;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCustomFieldsRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCustomFieldsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yiliangt5 on 09/02/2017.
 */

@Repository
public class HrEmployeeCustomFieldsDao extends JooqCrudImpl<HrEmployeeCustomFieldsDO, HrEmployeeCustomFieldsRecord> {

    public HrEmployeeCustomFieldsDao() {
        super(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS, HrEmployeeCustomFieldsDO.class);
    }

    public HrEmployeeCustomFieldsDao(TableImpl<HrEmployeeCustomFieldsRecord> table, Class<HrEmployeeCustomFieldsDO> hrEmployeeCustomFieldsDOClass) {
        super(table, hrEmployeeCustomFieldsDOClass);
    }

    public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields> getEmployeeExtConfByCompanyId(int companyId) {

        return create.selectFrom(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS)
                .where(HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS.COMPANY_ID.eq(companyId))
                .fetchInto(com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields.class);
    }
}
