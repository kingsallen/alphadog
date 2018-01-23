package com.moseeker.useraccounts.domain;

import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecord;
import com.moseeker.entity.SearchengineEntity;
import com.moseeker.useraccounts.infrastructure.DaoManagement;
import org.jooq.Record2;
import org.jooq.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * 积分实体
 *
 * Created by jack on 20/01/2018.
 */
@Component
public class AwardEntity {

    private Logger logger = LoggerFactory.getLogger(AwardEntity.class);

    private List<UserEmployeePointsRecord> userEmployeePointsRecordList;    //积分记录
    private DaoManagement daoManagement;
    private SearchengineEntity searchengineEntity;

    public AwardEntity(DaoManagement daoManagement, SearchengineEntity searchengineEntity, List<UserEmployeePointsRecord> userEmployeePointsRecordList) {
        this.daoManagement = daoManagement;
        this.userEmployeePointsRecordList = userEmployeePointsRecordList;
        this.searchengineEntity = searchengineEntity;
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

            Map<Integer, Integer> employeeAward = new HashMap<>();
            result.forEach(employee -> {
                int award = userEmployeePointsRecordList
                        .stream()
                        .filter(p -> p.getEmployeeId().intValue() == employee.value1().intValue())
                        .mapToInt(p -> p.getAward())
                        .sum();
                if (award + employee.value2() < 0) {
                    logger.error(" 员工 (employeeId:{}) 不让添加，因为加过之后成为负分！", employee.value1().intValue());
                    userEmployeePointsRecordList = userEmployeePointsRecordList
                            .stream()
                            .filter(p -> p.getEmployeeId().intValue() == employee.value1().intValue())
                            .collect(Collectors.toList());
                } else {
                    employeeAward.put(employee.value1(), award+employee.value2());
                }
            });

            //添加员工积分记录和修改员工数据的积分值 user_employee.award 以及更新ES索引
            if (employeeAward.size() > 0) {
                daoManagement.updateEmployeeAwards(employeeAward);
                List<UserEmployeePointsRecord> pointsRecordList = daoManagement.addEmployeeAwards(userEmployeePointsRecordList);

                if (pointsRecordList != null && pointsRecordList.size() > 0) {
                    pointsRecordList.forEach(point ->
                        searchengineEntity.updateEmployeeAwards(point.getEmployeeId().intValue(), point.getId()));
                }
            }
        }
    }
}
