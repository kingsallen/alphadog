package com.moseeker.useraccounts.service;


import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.config.AppConfig;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DictCityTest {

    @Autowired
    DictCityDao cityDao;

    HashMap<Integer,Set<Integer>> allCityChain=new HashMap<>(3536);

    HashMap<Integer,DictCityDO> quickSearchMap=new HashMap<>(3536);

    @Test
    public void test(){
        List<DictCityDO> list = cityDao.getFullCity();

        list.stream().forEach(d->quickSearchMap.put(d.getCode(),d));

        for(DictCityDO d:list){
            if(allCityChain.containsKey(d)){
                continue;
            }
            Set<Integer> vSet=new LinkedHashSet<>();
            vSet.add(d.getCode());
            searchUpperCity(d.getCode(),vSet,1,d.getLevel());
        }

    }

    public void searchUpperCity(int code, Set<Integer> set, int count ,int level){
        if(hasFindWholeChain(code,count)){
            allCityChain.put(code,set);
            System.out.println(code+""+ JSON.toJSONString(set));
            return;
        }

        int temp=code/count*count;


        if( isFather(temp,level)){
            set.add(temp);
//            level=d.getLevel();
        }

        count*=10;
        searchUpperCity(code,set,count,level);
    }

    private boolean hasFindWholeChain(int code,int count){
        return code/count==1;
    }

    private boolean isFather(int testFatherCode,int sameLevel){
        DictCityDO d=quickSearchMap.get(testFatherCode);
        if(d==null || d.getLevel()==sameLevel){
            return false;
        }
        return true;
    }


}
