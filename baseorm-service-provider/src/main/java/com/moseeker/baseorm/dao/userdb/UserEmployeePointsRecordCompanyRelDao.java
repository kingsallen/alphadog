package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecordCompanyRel;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordCompanyRelRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordCompanyRelDO;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

@Repository
public class UserEmployeePointsRecordCompanyRelDao extends JooqCrudImpl<UserEmployeePointsRecordCompanyRelDO, UserEmployeePointsRecordCompanyRelRecord> {

    public UserEmployeePointsRecordCompanyRelDao() {
        super(UserEmployeePointsRecordCompanyRel.USER_EMPLOYEE_POINTS_RECORD_COMPANY_REL, UserEmployeePointsRecordCompanyRelDO.class);
    }

    public UserEmployeePointsRecordCompanyRelDao(TableImpl<UserEmployeePointsRecordCompanyRelRecord> table, Class<UserEmployeePointsRecordCompanyRelDO> userEmployeePointsRecordCompanyRelDOClass) {
        super(table, userEmployeePointsRecordCompanyRelDOClass);
    }


}
