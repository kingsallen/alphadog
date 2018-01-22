package com.moseeker.useraccounts.context;

import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecord;
import com.moseeker.useraccounts.context.aggregate.ApplicationsAggregateId;
import com.moseeker.useraccounts.infrastructure.DaoManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 积分上下文管理
 * Created by jack on 22/01/2018.
 */
@Component
public class AwardContext {

    @Autowired
    DaoManagement daoManagement;

    public void addEmployeeAward(ApplicationsAggregateId applicationsAggregateId) {

        List<UserEmployeePointsRecord> userEmployeePointsRecordList
                = daoManagement.fetchEmployeePointsByApplicationIdList(applicationsAggregateId.getApplicationIdList(),
                applicationsAggregateId.getAwardEvent());


    }
}
