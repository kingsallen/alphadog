package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.Select;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;

import org.joda.time.DateTimeFieldType;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.SelectField;
import org.jooq.SelectJoinStep;
import org.jooq.impl.TableImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserEmployeeDao extends JooqCrudImpl<UserEmployeeDO, UserEmployeeRecord> {

    public UserEmployeeDao() {
        super(UserEmployee.USER_EMPLOYEE, UserEmployeeDO.class);
    }

    public UserEmployeeDao(TableImpl<UserEmployeeRecord> table, Class<UserEmployeeDO> userEmployeeDOClass) {
        super(table, userEmployeeDOClass);
    }

    public UserEmployeeDO getEmployee(com.moseeker.common.util.query.Query query) {
        UserEmployeeDO employee = new UserEmployeeDO();

        try {
            UserEmployeeRecord record = this.getRecord(query);
            if (record != null) {
                employee = BeanUtils.DBToStruct(UserEmployeeDO.class, record);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }

        return employee;
    }

    public List<UserEmployeeRecord> getEmployeeByWeChat(Integer companyId, List<Integer> weChatIds) throws Exception {
        List<UserEmployeeRecord> list = new ArrayList<UserEmployeeRecord>();
        SelectJoinStep<Record> table = create.select().from(UserEmployee.USER_EMPLOYEE);
        table.where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte) 0))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte) 0));
        Result<Record> result = table.fetch();
        if (result != null && result.size() > 0) {
            for (Record r : result) {
                list.add((UserEmployeeRecord) r);
            }
        }
        return list;
    }

    public int delResource(com.moseeker.common.util.query.Query query) throws Exception {
        if (query != null && query.getConditions() != null) {
            List<UserEmployeeRecord> records = getRecords(query);
            if (records.size() > 0) {
                return deleteRecords(records).length;
            }
        }
        return 0;
    }

    public UserEmployeeDO getUserEmployeeForUpdate(int id) {
        UserEmployeeRecord record = create.selectFrom(table).where(UserEmployee.USER_EMPLOYEE.ID.eq(id)).and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte)0)).
                and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte)0)).forUpdate().fetchOne();
        return BeanUtils.DBToStruct(UserEmployeeDO.class, record);
    }

    public int addAward(Integer employeeId, int award, int oldAward){
        return create.update(table).set(UserEmployee.USER_EMPLOYEE.AWARD, award).where(UserEmployee.USER_EMPLOYEE.ID.eq(employeeId)).and(UserEmployee.USER_EMPLOYEE.AWARD.eq(oldAward)).execute();
    }

}
