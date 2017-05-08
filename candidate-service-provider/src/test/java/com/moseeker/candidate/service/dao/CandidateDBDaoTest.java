package com.moseeker.candidate.service.dao;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by YYF
 *
 * Date: 2017/5/8
 *
 * Project_name :alphadog
 */
public class CandidateDBDaoTest {

    public static AnnotationConfigApplicationContext initSpring() {
        AnnotationConfigApplicationContext annConfig = new AnnotationConfigApplicationContext();
        annConfig.scan("com.moseeker.candidate");
        annConfig.scan("com.moseeker.baseorm");
        annConfig.refresh();
        return annConfig;
    }

    @Test
    public void getUserByID() throws Exception {
        AnnotationConfigApplicationContext acac = initSpring();
        CandidateDBDao candidateDBDao = acac.getBean(CandidateDBDao.class);
        System.out.println(candidateDBDao.getPositionByID(1));
    }

}