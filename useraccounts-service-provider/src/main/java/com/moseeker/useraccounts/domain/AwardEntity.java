package com.moseeker.useraccounts.domain;

import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecord;
import com.moseeker.useraccounts.infrastructure.DaoManagement;
import org.jooq.Record2;
import org.jooq.Result;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * 积分实体
 *
 * Created by jack on 20/01/2018.
 */
public class AwardEntity {

    private List<UserEmployeePointsRecord> userEmployeePointsRecordList;    //积分记录

    private DaoManagement daoManagement;

    public AwardEntity(DaoManagement daoManagement, List<UserEmployeePointsRecord> userEmployeePointsRecordList) {
        this.daoManagement = daoManagement;
        this.userEmployeePointsRecordList = userEmployeePointsRecordList;
    }

    /**
     * 添加积分
     * 由于不能讲员工的积分加成负数，所以在修改前需要判断是否造成最后为负数的情况
     */
    public void addAward() {

        //员工现在的积分
        Result<Record2<Integer, Integer>> result
                = daoManagement.fetchEmployeeAwardByEmployeeId(userEmployeePointsRecordList
                .stream()
                .map(userEmployeePointsRecord -> userEmployeePointsRecord.getEmployeeId().intValue())
                .collect(Collectors.toList()));

        if (result != null && result.size() > 0) {

            result.forEach(employee -> {

                int award = employee.value2();

                List<UserEmployeePointsRecord> pointsRecordList
                        = userEmployeePointsRecordList
                        .stream()
                        .filter(p -> p.getEmployeeId().intValue() == employee.value1().intValue())
                        .collect(Collectors.toList());
                if (pointsRecordList != null && pointsRecordList.size() > 0) {

                }
            });
        }
    }
}
