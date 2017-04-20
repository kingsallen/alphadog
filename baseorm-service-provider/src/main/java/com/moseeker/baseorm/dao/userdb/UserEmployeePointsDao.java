package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.UserEmployeePointsRecordDO;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 11/04/2017.
 */
@Repository
public class UserEmployeePointsDao extends StructDaoImpl<UserEmployeePointsRecordDO, UserEmployeePointsRecordRecord, UserEmployeePointsRecord> {
    @Override
    protected void initJOOQEntity() {
        this.tableLike = UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD;
    }
}
