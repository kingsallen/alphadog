package com.moseeker.entity;

import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.common.annotation.iface.CounterIface;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: jack
 * @Date: 2018/7/18
 */
@Service
@CounterIface
public class ApplicationEntity {

    @Autowired
    JobApplicationDao applicationDao;

    /**
     * 计算给定时间内的员工投递次数
     * @param userIdList 用户编号集合
     * @param positionIdList 职位编号集合
     * @param lastFriday 开始时间（>开始时间）
     * @param currentFriday 结束时间（<=结束时间）
     * @return 计算给定时间内的员工投递次数
     */
    public Map<Integer,Integer> countEmployeeApply(List<Integer> userIdList, List<Integer> positionIdList, LocalDateTime lastFriday,
                                                   LocalDateTime currentFriday) {

        Result<Record2<Integer, Integer>> result = applicationDao.countEmployeeApply(userIdList, positionIdList, lastFriday,
                currentFriday);

        if (result != null && result.size() > 0) {
            return result.stream().collect(Collectors.toMap(Record2::value1, Record2::value2));
        } else {
            return new HashMap<>();
        }
    }
}
