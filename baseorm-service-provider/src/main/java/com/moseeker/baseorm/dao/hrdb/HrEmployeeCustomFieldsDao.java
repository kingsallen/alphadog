package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCustomFields;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCustomFieldsRecord;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCustomFieldsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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
}
