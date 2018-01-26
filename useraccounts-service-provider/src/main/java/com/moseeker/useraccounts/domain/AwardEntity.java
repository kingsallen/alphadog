package com.moseeker.useraccounts.domain;

import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployeePointsRecord;
import com.moseeker.entity.SearchengineEntity;
import com.moseeker.useraccounts.infrastructure.AwardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 *
 * 积分实体
 *
 * Created by jack on 20/01/2018.
 */
public class AwardEntity {

    private Logger logger = LoggerFactory.getLogger(AwardEntity.class);

    private List<UserEmployeePointsRecord> userEmployeePointsRecordList;    //积分记录
    private List<UserEmployee> employeeList;                                //员工积分
    private AwardRepository awardRepository;
    private SearchengineEntity searchengineEntity;

    public AwardEntity(AwardRepository awardRepository, SearchengineEntity searchengineEntity,
                       List<UserEmployeePointsRecord> userEmployeePointsRecordList, List<UserEmployee> employeeList) {
        this.awardRepository = awardRepository;
        this.userEmployeePointsRecordList = userEmployeePointsRecordList;
        this.searchengineEntity = searchengineEntity;
        this.employeeList = employeeList;
    }

    /**
     * 添加积分
     * 由于不能讲员工的积分加成负数，所以在修改前需要判断是否造成最后为负数的情况
     */
    public void addAward() {

        //员工现在的积分
        if (employeeList != null && employeeList.size() > 0) {

            employeeList.forEach(employee -> {
                int award = userEmployeePointsRecordList
                        .stream()
                        .filter(p -> p.getEmployeeId().intValue() == employee.getId().intValue())
                        .mapToInt(p -> p.getAward())
                        .sum();
                if (award + employee.getAward() < 0) {
                    logger.error(" 员工 (employeeId:{}) 不让添加，因为加过之后成为负分！", employee.getId().intValue());
                    Iterator<UserEmployeePointsRecord> iterator = userEmployeePointsRecordList.iterator();
                    while (iterator.hasNext()) {
                        UserEmployeePointsRecord record = iterator.next();
                        if (record.getEmployeeId().intValue() == employee.getId().intValue()) {
                            iterator.remove();
                        }
                    }
                } else {
                    employee.setAward(award+employee.getAward());
                }
            });

            //添加员工积分记录和修改员工数据的积分值 user_employee.award 以及更新ES索引
            awardRepository.updateEmployeeAwards(employeeList);
            List<UserEmployeePointsRecord> pointsRecordList = awardRepository.addEmployeeAwards(userEmployeePointsRecordList);

            if (pointsRecordList != null && pointsRecordList.size() > 0) {
                pointsRecordList.forEach(point ->
                    searchengineEntity.updateEmployeeAwards(point.getEmployeeId().intValue(), point.getId()));
            }
        }
    }
}
