package com.moseeker.function.service.chaos;

import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.entity.CityEntity;
import com.moseeker.function.config.AppConfig;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by jack on 28/09/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PositionSyncFailedNotificationTest {

    @Autowired
    CityEntity cityEntity;

    @Autowired
    JobPositionCityDao jobPositionCityDao;

    @Test
    public void getCities() {
        /*int positionId = 1;
        String city = getCityName(positionId);

        Assert.assertEquals("【江苏省,镇江】", city);*/

        String city1 = getCityName(13113);
        System.out.println(city1);
    }

    private String getCityName(int positionId) {
        String city;
        List<DictCityDO> dictCityDOS = jobPositionCityDao.getPositionCitys(positionId);
        if (dictCityDOS == null || dictCityDOS.size() == 0) {
            city =  "无";
        } else {
            List<List<DictCityDO>> fullCities = cityEntity.getFullCities(dictCityDOS);

            StringBuilder cityBuilder = new StringBuilder();
            StringBuilder innerBuilder = new StringBuilder();
            for (List<DictCityDO> cityDOS : fullCities) {
                cityBuilder.append("【");
                innerBuilder.delete(0, innerBuilder.length());
                for (DictCityDO cityDO : cityDOS) {
                    innerBuilder.append(',').append(cityDO.getName());
                }
                if (innerBuilder.length() > 0) {
                    innerBuilder.delete(0, 1);
                }
                cityBuilder.append(innerBuilder);
                cityBuilder.append("】");
            }
            city = cityBuilder.toString();
        }
        return city;
    }
}