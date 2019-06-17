package com.moseeker.baseorm.dao.employeedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.employeedb.tables.pojos.EmployeeOptionValue;
import com.moseeker.baseorm.db.employeedb.tables.records.EmployeeOptionValueRecord;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @Autowired
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

    /**
     * 根据ID和自定义信息ID查找下拉项
     * @param customId 自定义信息编号
     * @param optionIdList 编号集合
     * @return 下拉项信息
     */
    public List<EmployeeOptionValue> listByCustomIdAndIdList(Integer customId, Set<Integer> optionIdList) {
        Result<EmployeeOptionValueRecord> result = create
                .selectFrom(EMPLOYEE_OPTION_VALUE)
                .where(EMPLOYEE_OPTION_VALUE.ID.in(optionIdList))
                .and(EMPLOYEE_OPTION_VALUE.CUSTOM_FIELD_ID.eq(customId))
                .orderBy(EMPLOYEE_OPTION_VALUE.PRIORITY)
                .fetch();
        if (result != null && result.size() > 0) {
            return result.into(EmployeeOptionValue.class);
        } else {
            return new ArrayList<>(0);
        }
    }
}
