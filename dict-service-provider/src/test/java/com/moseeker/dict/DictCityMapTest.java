package com.moseeker.dict;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.moseeker.baseorm.dao.dictdb.DictCityMapDao;
import com.moseeker.common.constants.ChannelType;
import com.moseeker.dict.config.AppConfig;
import com.moseeker.dict.service.impl.DictOccupationService;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        List<List<String>> result = dictCityMapDao.getOtherCityByLastCodes(ChannelType.LIEPIN, cityCodes);

        System.out.println("result:"+result.toString());
    }

    @Autowired
    DictOccupationService occupationService;

    @Test
    public void testGetOccupation() throws BIZException {
        Map<String,Object> param = new HashMap<>();
        param.put("channel",3);
        param.put("single_layer",1);
        param.put("code",110208);
        JSON response = occupationService.queryOccupation(JSON.toJSONString(param));

        response.toString();
    }

    @Test
    public void testGetOtherCityByLastCodes() {

        List<Integer> cityCodes = new ArrayList<>();
        cityCodes.add(110000);
        cityCodes.add(130100);

        List<List<String>> result = dictCityMapDao.getOtherCityByLastCodes(ChannelType.JOB58, cityCodes);

        System.out.println("result:"+result.toString());
    }
}