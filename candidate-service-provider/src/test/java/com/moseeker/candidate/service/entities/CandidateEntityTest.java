package com.moseeker.candidate.service.entities;

import com.moseeker.candidate.config.AppConfig;
import com.moseeker.thrift.gen.candidate.struct.SortResult;
import com.moseeker.thrift.gen.common.struct.BIZException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by YYF
 *
 * Date: 2017/5/8
 *
 * Project_name :alphadog
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class CandidateEntityTest {


    @Autowired
    private CandidateEntity candidateEntity;

    /**
     * 查询员工在公司的推荐排名
     */
    @Test
    public void getRecommendatorySorting() throws BIZException {
        SortResult sortResult = candidateEntity.getRecommendatorySorting(29145,39978);
        System.out.println(sortResult);
    }
}