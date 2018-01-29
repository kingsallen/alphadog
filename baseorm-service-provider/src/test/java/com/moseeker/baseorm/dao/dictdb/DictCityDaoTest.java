package com.moseeker.baseorm.dao.dictdb;

import com.moseeker.baseorm.config.AppConfig;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DictCityDaoTest {
    @Autowired
    DictCityDao dictCityDao;

    @Test
    public void test(){
        DictCityDO cityDO=new DictCityDO();
        cityDO.setCode(429004);
        dictCityDao.getMoseekerLevels(cityDO);
    }
}