package com.moseeker.search;

import com.moseeker.searchengine.config.AppConfig;
import com.moseeker.searchengine.service.impl.TalentpoolSearchengine;
import org.elasticsearch.index.query.QueryBuilder;
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
}
