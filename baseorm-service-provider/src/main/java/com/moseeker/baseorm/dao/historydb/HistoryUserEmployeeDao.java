package com.moseeker.baseorm.dao.historydb;

import static com.moseeker.baseorm.db.historydb.tables.HistoryUserEmployee.HISTORY_USER_EMPLOYEE;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.historydb.HistoryUserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import java.util.ArrayList;
import java.util.List;
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
        super(HISTORY_USER_EMPLOYEE, UserEmployeeDO.class);
   }


   public List<HistoryUserEmployeeDO> getHistoryEmployeeByCompanyIds(List<Integer> compantIds){
        List<HistoryUserEmployeeDO> historyUserEmployees=  create.selectFrom(HISTORY_USER_EMPLOYEE)
                .where(HISTORY_USER_EMPLOYEE.COMPANY_ID.in(compantIds))
                .fetchInto(HistoryUserEmployeeDO.class);
        if(StringUtils.isEmptyList(historyUserEmployees)){
            return new ArrayList<>();
        }
        return historyUserEmployees;

   }

    public List<HistoryUserEmployeeDO> getHistoryEmployeeByIds(List<Integer> ids){
        List<HistoryUserEmployeeDO> historyUserEmployees=  create.selectFrom(HISTORY_USER_EMPLOYEE)
                .where(HISTORY_USER_EMPLOYEE.ID.in(ids))
                .fetchInto(HistoryUserEmployeeDO.class);
        if(StringUtils.isEmptyList(historyUserEmployees)){
            return new ArrayList<>();
        }
        return historyUserEmployees;

    }
}
