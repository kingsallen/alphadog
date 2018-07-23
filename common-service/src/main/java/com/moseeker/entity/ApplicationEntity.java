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

    public Map<Integer,Integer> countEmployeeApply(List<Integer> userIdList, LocalDateTime lastFriday,
                                                   LocalDateTime currentFriday) {

        Result<Record2<Integer, Integer>> result = applicationDao.countEmployeeApply(userIdList, lastFriday,
                currentFriday);

        if (result != null && result.size() > 0) {
            return result.stream().collect(Collectors.toMap(Record2::value1, Record2::value2));
        } else {
            return new HashMap<>();
        }
    }
}
