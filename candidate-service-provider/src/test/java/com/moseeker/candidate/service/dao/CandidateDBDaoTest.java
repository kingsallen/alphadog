package com.moseeker.candidate.service.dao;

import com.moseeker.baseorm.dao.jobdb.JobApplicationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println(candidateDBDao.getPositionByID(378));
    }


    @Test
    public void getCandidateCompanyByUserIDCompanyID() throws Exception {
        AnnotationConfigApplicationContext acac = initSpring();
        CandidateDBDao candidateDBDao = acac.getBean(CandidateDBDao.class);
        System.out.println(candidateDBDao.getCandidateCompanyByUserIDCompanyID(0, 2878));
    }


    @Test
    public void getCandidateRemarks() throws Exception {
        AnnotationConfigApplicationContext acac = initSpring();
        CandidateDBDao candidateDBDao = acac.getBean(CandidateDBDao.class);
        System.out.println(candidateDBDao.getCandidateRemarks(0, 2878));
    }


    /**
     * 查找职位标题
     *
     * @return 职位信息集合
     */
    @Test
    public void getPositionByIdList() throws Exception {
        AnnotationConfigApplicationContext acac = initSpring();
        CandidateDBDao candidateDBDao = acac.getBean(CandidateDBDao.class);
        List<Integer> ids = new ArrayList<>();
        ids.add(378);
        ids.add(380);
        ids.add(388);
        System.out.println(candidateDBDao.getPositionByIdList(ids));
    }

    /**
     * 查找用户的基本信息
     *
     * @return 用户信息集合
     */
    @Test
    public void getUserByIDList() throws Exception {
        AnnotationConfigApplicationContext acac = initSpring();
        CandidateDBDao candidateDBDao = acac.getBean(CandidateDBDao.class);
        List<Integer> ids = new ArrayList<>();
        ids.add(1842);
        ids.add(2064);
        System.out.println(candidateDBDao.getUserByIDList(ids));
    }


    @Test
    public void Test() throws Exception {
        AnnotationConfigApplicationContext acac = initSpring();
        JobPositionDao jobPositionDao = acac.getBean(JobPositionDao.class);
        List<Integer> list = new ArrayList<>();
        list.add(378);
        list.add(379);
        Condition condition = new Condition("id", list, ValueOp.IN);
        Query query = new Query.QueryBuilder().and(condition).buildQuery();
        List<JobPositionDO> test = jobPositionDao.getPositions(query);
        System.out.println(test);

    }


}