package com.moseeker.useraccounts.infrastructure;

import com.moseeker.baseorm.constant.EmployeeActiveState;
import com.moseeker.baseorm.db.userdb.tables.daos.UserEmployeeDao;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.common.constants.AbleFlag;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.moseeker.baseorm.db.userdb.tables.UserEmployee.USER_EMPLOYEE;
import static org.jooq.impl.DSL.using;

/**
 *
 * 由于历史遗留问题导致 UserEmployeeDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 *
 * Created by jack on 22/01/2018.
 */
public class UserEmployeeJOOQDao extends UserEmployeeDao {

    public UserEmployeeJOOQDao(Configuration configuration) {
        super(configuration);
    }

    /**
     * 根据C端用户编号和职位所处的公司的编号查找未被禁用且激活的员工集合
     * @param employeeParam C端用户编号和职位所处的公司的编号
     * @return 员工集合
     */
    public List<UserEmployee> fetchByUserIdAndCompanyId(Map<Integer, Integer> employeeParam) {

        if (employeeParam != null && employeeParam.size() > 0) {
            Condition condition = null;
            for (Map.Entry<Integer, Integer> entry : employeeParam.entrySet()) {
                if (condition == null) {
                    condition = USER_EMPLOYEE.SYSUSER_ID.eq(entry.getKey()).and(USER_EMPLOYEE.COMPANY_ID.eq(entry.getValue()));
                } else {
                    condition = condition.or(USER_EMPLOYEE.SYSUSER_ID.eq(entry.getKey()).and(USER_EMPLOYEE.COMPANY_ID.eq(entry.getValue())));
                }
            }
            Result<UserEmployeeRecord> result = using(configuration())
                    .selectFrom(USER_EMPLOYEE)
                    .where(condition)
                    .and(USER_EMPLOYEE.DISABLE.eq((byte) AbleFlag.OLDENABLE.getValue()))
                    .and(USER_EMPLOYEE.ACTIVATION.eq(EmployeeActiveState.Actived.getState()))
                    .fetch();
            if (result != null) {
                return result.map(mapper());
            }
        }

        return new ArrayList<>();
    }

    /**
     * 根据员工编号查找员工当前的积分信息
     * @param idList 员工编号
     * @return 员工当前信息
     */
    public List<UserEmployee> fetchIdAndAwardListById(List<Integer> idList) {
        if (idList != null && idList.size() > 0) {
            Result<Record2<Integer, Integer>> result = using(configuration())
                    .select(USER_EMPLOYEE.ID, USER_EMPLOYEE.AWARD)
                    .from(USER_EMPLOYEE)
                    .where(USER_EMPLOYEE.ID.in(idList))
                    .fetch();
            if (result != null) {
                return result.into(UserEmployee.class);
            }
        }
        return new ArrayList<>();
    }
}
