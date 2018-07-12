package com.moseeker.application.infrastructure;


import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import org.jooq.Configuration;
import static org.jooq.impl.DSL.using;

/**
 * 由于历史遗留问题导致 UserHrAccountDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 * Created by jack on 16/01/2018.
 */
public class UserEmployeeJOOQDao extends com.moseeker.baseorm.db.userdb.tables.daos.UserEmployeeDao {

    public UserEmployeeJOOQDao(Configuration configuration) {
        super(configuration);
    }

    public boolean isCompanyEmployee(int companyId, int userId){
        com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee employee = using(configuration()).selectFrom(UserEmployee.USER_EMPLOYEE)
                .where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId))
                .and(UserEmployee.USER_EMPLOYEE.SYSUSER_ID.eq(userId))
                .and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte)0))
                .and(UserEmployee.USER_EMPLOYEE.ACTIVATION.eq((byte)0))
                .fetchOneInto(com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee .class);
        if(employee != null ){
            return true;
        }
        return false;
    }


}
