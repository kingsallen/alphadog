package com.moseeker.baseorm.dao.employeedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.employeedb.tables.pojos.EmployeeOptionValue;
import com.moseeker.baseorm.db.employeedb.tables.records.EmployeeOptionValueRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.employeedb.tables.EmployeeOptionValue.EMPLOYEE_OPTION_VALUE;

/**
 * TODO
 *
 * @Author jack
 * @Date 2019/6/13 7:00 PM
 * @Version 1.0
 */
@Repository
public class EmployeeCustomOptionJooqDao extends JooqCrudImpl<EmployeeOptionValue, EmployeeOptionValueRecord> {

    public EmployeeCustomOptionJooqDao() {
        super(EMPLOYEE_OPTION_VALUE, EmployeeOptionValue.class);
    }

    public EmployeeCustomOptionJooqDao(TableImpl<EmployeeOptionValueRecord> table, Class<EmployeeOptionValue> employeeOptionValueClass) {
        super(table, employeeOptionValueClass);
    }

    public int count(Integer fieldId, List<Integer> optionIdList) {
        if (fieldId == null || fieldId <= 0 || optionIdList == null || optionIdList.size() == 0) {
            return 0;
        }
        return create
                .selectCount()
                .from(EMPLOYEE_OPTION_VALUE)
                .where(EMPLOYEE_OPTION_VALUE.ID.in(optionIdList))
                .and(EMPLOYEE_OPTION_VALUE.CUSTOM_FIELD_ID.eq(fieldId))
                .fetchOne()
                .value1();
    }
}
