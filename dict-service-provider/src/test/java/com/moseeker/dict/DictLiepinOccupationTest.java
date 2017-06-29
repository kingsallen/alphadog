package com.moseeker.dict;

import com.moseeker.baseorm.config.AppConfig;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by zhangdi on 2017/6/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class DictLiepinOccupationTest {


    @Autowired
    DictLiepinOccupationDao liepinOccupationDao;

    @Test
    public void testGetFullOccupation() {
        List<DictLiepinOccupationDO> liepinOccupationDOList = liepinOccupationDao.getFullOccupations("100100");
        liepinOccupationDOList.toString();
    }

    @Autowired
    JobPositionCityDao positionCityDao;

    @Test
    public void getCityNames(){
        List<DictCityDO> cityDOS = positionCityDao.getPositionCitys(378);

        cityDOS.toString();

    }
}
