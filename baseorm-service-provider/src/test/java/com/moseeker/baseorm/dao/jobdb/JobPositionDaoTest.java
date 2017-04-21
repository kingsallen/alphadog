package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.position.struct.PositionDetails;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.HashMap;

/**
 * Created by YYF
 *
 * Date: 2017/4/19
 *
 * Project_name :alphadog
 */
public class JobPositionDaoTest {

    public static AnnotationConfigApplicationContext initSpring() {
        AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
        annConfig.scan("com.moseeker.baseorm");
        annConfig.refresh();
        return annConfig;
    }

    @Test
    public void positionDetails() throws Exception {
        AnnotationConfigApplicationContext acac = initSpring();
        try {
            JobPositionDao jobPositionDao = acac.getBean(JobPositionDao.class);
            jobPositionDao.positionDetails(164175);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void positionDetailsList() throws Exception {
        AnnotationConfigApplicationContext acac = initSpring();
        try {
            JobPositionDao jobPositionDao = acac.getBean(JobPositionDao.class);
            CommonQuery commonQuery = new CommonQuery();
            HashMap hashMap = new HashMap();
            hashMap.put("company_id", "157");
            commonQuery.setEqualFilter(hashMap);
            jobPositionDao.positionDetailsList(commonQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}