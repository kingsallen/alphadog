package com.moseeker.search;

import com.moseeker.searchengine.config.AppConfig;
import com.moseeker.searchengine.service.impl.TalentpoolSearchengine;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.ScriptQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void testQuery(){
        Map<String,String> map=new HashMap<>();
        map.put("publisher_ids","1,2,3,4");
        map.put("candidate_source","0");
        map.put("only_recommend","1");
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
        QueryBuilder builder= talentpoolSearchengine.query(map);
        System.out.println(builder.toString());
    }
}
