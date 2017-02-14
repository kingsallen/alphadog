package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrEmployeeCustomFields;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCustomFieldsRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import org.springframework.stereotype.Service;

/**
 * Created by yiliangt5 on 09/02/2017.
 */

@Service
public class HrEmployeeCustomFieldsDao extends BaseDaoImpl<HrEmployeeCustomFieldsRecord, HrEmployeeCustomFields> {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = HrEmployeeCustomFields.HR_EMPLOYEE_CUSTOM_FIELDS;
    }
}
