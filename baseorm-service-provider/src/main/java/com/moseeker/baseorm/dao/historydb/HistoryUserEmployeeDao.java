package com.moseeker.baseorm.dao.historydb;

import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.historydb.tables.HistoryUserEmployee;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryUserEmployeeRecord;
import com.moseeker.baseorm.crud.JooqCrudImpl;

/**
* @author xxx
* HistoryUserEmployeeDao 实现类 （groovy 生成）
* 2017-07-03
*/
@Repository
public class HistoryUserEmployeeDao extends JooqCrudImpl<UserEmployeeDO, HistoryUserEmployeeRecord> {


   public HistoryUserEmployeeDao() {
        super(HistoryUserEmployee.HISTORY_USER_EMPLOYEE, UserEmployeeDO.class);
   }

   public UserEmployeeDO getUserEmployeeById(int employeeId){
       return create.selectFrom(HistoryUserEmployee.HISTORY_USER_EMPLOYEE)
               .where(HistoryUserEmployee.HISTORY_USER_EMPLOYEE.ID.eq(employeeId))
               .fetchOneInto(UserEmployeeDO.class);
   }
}
