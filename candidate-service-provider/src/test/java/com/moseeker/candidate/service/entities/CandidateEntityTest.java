package com.moseeker.candidate.service.entities;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.moseeker.candidate.config.AppConfig;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.candidate.struct.CandidateList;
import com.moseeker.thrift.gen.candidate.struct.CandidateListParam;
import com.moseeker.thrift.gen.candidate.struct.SortResult;
import com.moseeker.thrift.gen.common.struct.BIZException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        SortResult sortResult = candidateEntity.getRecommendatorySorting(19811221,16);
        System.out.println(sortResult);
    }

    @Test
    public void testGetCandidateInfo(){
        Map<String,Object> data = candidateEntity.getCandidateInfo(82690,676242,134129);
        System.out.println(JSON.toJSONString(data, SerializerFeature.WriteDateUseDateFormat));
    }

    @Test
    public void  candiateListTest(){
        int postUserId=2193143;
        String clickTime="2018-06-26 13:48:50";
        List<Integer> recoms=new ArrayList<>();
        recoms.add(1);
        int companyId=39978;
        CandidateListParam param=new CandidateListParam();
        param.setClickTime(clickTime);
        param.setCompanyId(companyId);
        param.setRecoms(recoms);
        param.setPostUserId(postUserId);
        List<CandidateList> result=candidateEntity.candidateList(param);
        System.out.println(JSON.toJSONString(result));
    }
}