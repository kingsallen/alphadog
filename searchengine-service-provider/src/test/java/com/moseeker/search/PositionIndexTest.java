package com.moseeker.search;

import com.alibaba.fastjson.JSON;
import com.moseeker.searchengine.config.AppConfig;
import com.moseeker.searchengine.service.impl.PositionSearchEngine;
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
 * Created by zztaiwll on 17/8/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes =AppConfig.class)
public class PositionIndexTest {
    @Autowired
    private PositionSearchEngine positionSearchEngine;
    @Test
    public void searchTest(){
        //String keyWord,String industry,String salaryCode,int page,int pageSize,String cityCode,String startTime,String endTime
        List<Map<String,Integer>> list=new ArrayList<Map<String,Integer>>();
        Map<String,Integer> map=new HashMap<String,Integer>();
        map.put("salaryTop",3);
        map.put("salaryBottom",1);
        list.add(map);
        Map<String,Integer> map1=new HashMap<String,Integer>();
        map1.put("salaryTop",6);
        map1.put("salaryBottom",4);
        list.add(map1);
        String ss=JSON.toJSONString(list);
        Map<String,Object> result=positionSearchEngine.search(null, null, null, 1, 18, null, null, null, 1424, 24, 0, 0);
        System.out.println(result);
    }
    @Test
    public void searchPositionByCompany(){
        Map<String,Object> result=positionSearchEngine.search("","",null,1,10,"","","",39978,0,0,1);
        System.out.println(result);
    }
    @Test
    public void searchPositionByTeam(){
        Map<String,Object> result=positionSearchEngine.search("","",null,1,10,"","","",39978,188350558,0,1);
        System.out.println(result);
    }
}
