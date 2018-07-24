package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecordCompanyRel;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordCompanyRelRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordCompanyRelDO;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;
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

    public Map<Integer, Integer> handerEmployeeAwards(Date date){
        logger.info("=============date:{}",date);
        Result<Record2<Long, Integer>> result = create.select(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.EMPLOYEE_ID, DSL.count(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.EMPLOYEE_ID))
                .from(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD)
                .where(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD._CREATE_TIME.ge(new Timestamp(date.getTime())))
                .and(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.AWARD.gt(0))
                .groupBy(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.EMPLOYEE_ID)
                .fetch();
        if(!result.isEmpty()){
            Map<Integer, Integer> params = new HashMap<>();
            for (Record2<Long, Integer> record : result) {
                params.put(Integer.valueOf(record.value1().toString()),record.value2());
            }
            return params;
        }
        return new HashMap<>();
    }


}
