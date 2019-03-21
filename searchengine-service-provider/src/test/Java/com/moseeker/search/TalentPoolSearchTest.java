package com.moseeker.search;

import com.alibaba.fastjson.JSON;
import com.moseeker.searchengine.config.AppConfig;
import com.moseeker.searchengine.domain.PastPOJO;
import com.moseeker.searchengine.domain.SearchPast;
import com.moseeker.searchengine.service.impl.TalentpoolSearchengine;
import com.moseeker.searchengine.util.SearchUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.ScriptQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zztaiwll on 17/12/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =AppConfig.class)
public class TalentPoolSearchTest {
    @Autowired
    private TalentpoolSearchengine talentpoolSearchengine;
    @Autowired
    private SearchUtil searchUtil;
    @Test
    public void testQueryNest(){
        Map<String,String> map=new HashMap<>();
        map.put("tag_ids","1,2,3,4");
        map.put("hr_ids","1,2,3,4");
        QueryBuilder builder= talentpoolSearchengine.queryNest(map);
        System.out.println(builder.toString());
    }

    @Test
    public void testScript(){
        Map<String,String> map=new HashMap<>();
        map.put("publisher_ids","1,2,3,4");
        map.put("candidate_source","0");
        map.put("only_recommend","1");
        map.put("origins","2");
        map.put("submit_time","2010-12-12");
        map.put("progress_status","1");
        ScriptQueryBuilder scriptQueryBuilder=talentpoolSearchengine.queryScript(map);
        System.out.println(scriptQueryBuilder.toString());
    }

    @Test
    public void testQuery() throws Exception{
        Map<String,String> map=new HashMap<>();
        map.put("publisher","1,2,3,4");
        map.put("hr_account_id","82752");
        map.put("candidate_source","0");
        map.put("is_recommend","1");
        map.put("origins","2");
        map.put("progress_status","1");
        map.put("tag_ids","1,2,3,4");
        map.put("hr_ids","1,2,3,4");
        map.put("keyword","111,222,333");
        map.put("in_last_job_company","xxxxx");
        map.put("in_last_job_position","111,222,333");
        map.put("city_name","xxxxx");
        map.put("company_name","111,222,333");
        map.put("past_position","xxxxx");
        QueryBuilder builder= talentpoolSearchengine.query(map, null);
        System.out.println(builder.toString());
    }

    /*

    {tag_ids=talent, company_id=39978, all_publisher=1, hr_account_id=82752, page_number=1, appid=4,
    publisher=87757,90915,91318,91337,91342,91363,91364,91366,91367,82752, hr_account_id=82752, page_size=20}

     */

    @Test
    public void testalentSearch(){
        Map<String,String> map=new HashMap<>();
        map.put("publisher","87757,90915,91318,91337,91342,91363,91364,91366,91367,82752");
        map.put("hr_account_id","82752");
        map.put("position_id","1909491");
        map.put("company_id","39978");
        map.put("all_publisher","0");
        map.put("page_number","1");
        map.put("page_size","20");
        map.put("hr_id","82752");
        map.put("submit_time","1");
        map.put("origins","1000000,100000000");
        ScriptQueryBuilder result= talentpoolSearchengine.queryScript(map);
        System.out.println(result.toString());
    }

    @Test
    public void testOriginQuery(){
        String conditiond="10000000000000000000000,100000000000000000000000,1000000000000000000000,10000000000000000000,100000000000000000000,4096,64,2048,32,8,16";
        QueryBuilder defaultquery = QueryBuilders.matchAllQuery();
        QueryBuilder query = QueryBuilders.boolQuery().must(defaultquery);
        searchUtil.handleOrigins(conditiond,"39978",query);
    }

    @Test
    public void testPastPosition(){
        SearchPast past=new SearchPast();
        past.setCompanyId("39978");
        past.setKeyword("测试");
        past.setPageNumber("1");
        past.setPageSize("10");
        past.setPublisher("82752");
        PastPOJO pastPOJO=talentpoolSearchengine.searchPastPosition(past);
        System.out.println(JSON.toJSONString(pastPOJO));
    }



}
