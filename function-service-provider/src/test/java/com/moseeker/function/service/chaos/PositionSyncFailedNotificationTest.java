package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.CityEntity;
import com.moseeker.function.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.dictdb.DictCityDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrThirdPartyPositionDO;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;
import com.moseeker.thrift.gen.position.service.PositionDao;
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

    @Autowired
    PositionSyncFailedNotification failedNotification;

    @Autowired
    JobPositionDao positionDao;

    @Autowired
    HRThirdPartyPositionDao thirdPartyPositionDao;


    @Test
    public void test() throws BIZException {
        //51
//        int positionId=1913159;
//        int thirdPartyPositionId=582;
//        testEmail(positionId,thirdPartyPositionId);

        //猎聘
        int positionId=1913182;
        int thirdPartyPositionId=588;
        testEmail(positionId,thirdPartyPositionId);

    }

    public void testEmail(int positionId,int thirdPartyPositionId) throws BIZException {
        Query query=new Query.QueryBuilder().where(JobPosition.JOB_POSITION.ID.getName(),positionId).buildQuery();
        JobPositionDO position=positionDao.getData(query);

        query=new Query.QueryBuilder().where(HrThirdPartyPosition.HR_THIRD_PARTY_POSITION.ID.getName(),thirdPartyPositionId).buildQuery();
        TwoParam<HrThirdPartyPositionDO,Object> twoParam=thirdPartyPositionDao.getData(query);

        String json =
                "{" +
                        "  'message': ['第三方验证码识别服务失败']," +
                        "  'operation': 'publish'," +
                        "  'data': {" +
                        "    'channel': '1'," +
                        "    'positionId': '1935423'," +
                        "    'accountId': '769'" +
                        "  }," +
                        "  'status': 2" +
                        "}";
        PositionForSyncResultPojo pojo= JSON.parseObject(json,PositionForSyncResultPojo.class);

        pojo.getData().setChannel(String.valueOf(twoParam.getR1().getChannel()));
        pojo.getData().setPositionId(String.valueOf(positionId));

        failedNotification.sendUnKnowResultMail(position, twoParam.getR1(),twoParam.getR2(),pojo);
    }

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