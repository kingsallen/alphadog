package com.moseeker.dict;

import com.moseeker.baseorm.config.AppConfig;
import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityMapDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DictCityMapTest {

    @Autowired
    DictCityMapDao dictCityMapDao;


    @Test
    public void testGetOtherCityFunllLevel() {

        List<Integer> cityCodes = new ArrayList<>();
        cityCodes.add(320200);
        cityCodes.add(440300);

        List<List<String>> result = dictCityMapDao.getOtherCityFunllLevel(ChannelType.LIEPIN, cityCodes);

        System.out.println("result:"+result.toString());
    }
}