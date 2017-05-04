package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.thrift.gen.dao.struct.UserEmployeePointsRecordDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 11/04/2017.
 */
@Repository
public class UserEmployeePointsDao extends JooqCrudImpl<UserEmployeePointsRecordDO, UserEmployeePointsRecordRecord> {

    public UserEmployeePointsDao(TableImpl<UserEmployeePointsRecordRecord> table, Class<UserEmployeePointsRecordDO> userEmployeePointsRecordDOClass) {
        super(table, userEmployeePointsRecordDOClass);
    }
}
