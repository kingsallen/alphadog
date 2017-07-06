package com.moseeker.dict;

import com.moseeker.baseorm.config.AppConfig;
import com.moseeker.baseorm.dao.dictdb.Dict51OccupationDao;
import com.moseeker.baseorm.dao.dictdb.DictLiepinOccupationDao;
import com.moseeker.baseorm.dao.dictdb.DictZhilianOccupationDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.thrift.gen.dao.struct.dictdb.Dict51jobOccupationDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictLiepinOccupationDO;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictZhilianOccupationDO;
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

    @Autowired
    Dict51OccupationDao dict51OccupationDao;

    @Autowired
    DictZhilianOccupationDao dictZhilianOccupationDao;

    @Test
    public void testGetFullOccupation() {
        List<DictLiepinOccupationDO> liepinOccupationDOList = liepinOccupationDao.getFullOccupations("100100");
        liepinOccupationDOList.toString();

        List<Dict51jobOccupationDO> dict51jobOccupationDOS = dict51OccupationDao.getFullOccupations("412");
        dict51jobOccupationDOS.toString();

        List<DictZhilianOccupationDO> zhilianOccupationDOS = dictZhilianOccupationDao.getFullOccupations("9");
        zhilianOccupationDOS.toString();
    }

    @Autowired
    JobPositionCityDao positionCityDao;

    @Test
    public void getCityNames(){
        List<DictCityDO> cityDOS = positionCityDao.getPositionCitys(378);

        cityDOS.toString();

    }
}
