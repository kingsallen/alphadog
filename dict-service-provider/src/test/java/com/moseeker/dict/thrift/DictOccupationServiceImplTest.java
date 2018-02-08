package com.moseeker.dict.thrift;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.dict.config.AppConfig;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DictOccupationServiceImplTest {

    /*@Autowired
    DictOccupationServiceImpl service;

    @Test
    public void test() throws TException {
        print(service.getDictOccupation("{channel:1}"));
    }*/

    @Autowired
    DictCityDao dictCityDao;

    @Test
    public void test(){
        DictCityDO cityDO=new DictCityDO();
        cityDO.setCode(429004);
        print(dictCityDao.getMoseekerLevels(cityDO));

    }

    public static void print(Object object){
        System.out.println(JSON.toJSONString(object));
    }
}