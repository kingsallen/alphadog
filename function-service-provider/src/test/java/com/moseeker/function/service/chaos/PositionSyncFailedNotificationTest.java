package com.moseeker.function.service.chaos;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.dictdb.DictCityDao;
import com.moseeker.baseorm.dao.hrdb.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionCityDao;
import com.moseeker.baseorm.dao.jobdb.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.HrThirdPartyPosition;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.pojo.TwoParam;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.RefreshConstant;
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

import javax.annotation.Resource;
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

    @Resource(name = "cacheClient")
    protected RedisClient redisClient;

    @Test
    public void test() throws BIZException {
        int positionId=0;
        int thirdPartyPositionId=0;

//        String json="{\"languageType\":[{\"code\":\"1\",\"text\":\"\\xe8\\x8b\\xb1\\xe8\\xaf\\xad\"},{\"code\":\"2\",\"text\":\"\\xe6\\x97\\xa5\\xe8\\xaf\\xad\"},{\"code\":\"3\",\"text\":\"\\xe6\\xb3\\x95\\xe8\\xaf\\xad\"},{\"code\":\"4\",\"text\":\"\\xe5\\xbe\\xb7\\xe8\\xaf\\xad\"},{\"code\":\"5\",\"text\":\"\\xe4\\xbf\\x84\\xe8\\xaf\\xad\"},{\"code\":\"6\",\"text\":\"\\xe8\\xa5\\xbf\\xe7\\x8f\\xad\\xe7\\x89\\x99\\xe8\\xaf\\xad\"},{\"code\":\"7\",\"text\":\"\\xe9\\x9f\\xa9\\xe8\\xaf\\xad\"},{\"code\":\"8\",\"text\":\"\\xe9\\x98\\xbf\\xe6\\x8b\\x89\\xe4\\xbc\\xaf\\xe8\\xaf\\xad\"},{\"code\":\"9\",\"text\":\"\\xe8\\x91\\xa1\\xe8\\x90\\x84\\xe7\\x89\\x99\\xe8\\xaf\\xad\"},{\"code\":\"10\",\"text\":\"\\xe6\\x84\\x8f\\xe5\\xa4\\xa7\\xe5\\x88\\xa9\\xe8\\xaf\\xad\"},{\"code\":\"11\",\"text\":\"\\xe4\\xb8\\xad\\xe5\\x9b\\xbd\\xe6\\x99\\xae\\xe9\\x80\\x9a\\xe8\\xaf\\x9d\"},{\"code\":\"12\",\"text\":\"\\xe7\\xb2\\xa4\\xe8\\xaf\\xad\"},{\"code\":\"13\",\"text\":\"\\xe4\\xb8\\x8a\\xe6\\xb5\\xb7\\xe8\\xaf\\x9d\"},{\"code\":\"14\",\"text\":\"\\xe9\\x97\\xbd\\xe5\\x8d\\x97\\xe8\\xaf\\x9d\"},{\"code\":\"15\",\"text\":\"\\xe5\\x85\\xb6\\xe5\\xae\\x83\"},{\"code\":\"16\",\"text\":\"\\xe5\\x8c\\x97\\xe6\\x96\\xb9\\xe6\\x96\\xb9\\xe8\\xa8\\x80\"},{\"code\":\"17\",\"text\":\"\\xe5\\x90\\xb4\\xe6\\x96\\xb9\\xe8\\xa8\\x80\"},{\"code\":\"18\",\"text\":\"\\xe6\\xb9\\x98\\xe6\\x96\\xb9\\xe8\\xa8\\x80\"},{\"code\":\"19\",\"text\":\"\\xe8\\xb5\\xa3\\xe6\\x96\\xb9\\xe8\\xa8\\x80\"},{\"code\":\"20\",\"text\":\"\\xe5\\xae\\xa2\\xe5\\xae\\xb6\\xe6\\x96\\xb9\\xe8\\xa8\\x80\"}],\"workMode\":[{\"code\":\"1\",\"text\":\"\\xe5\\x85\\xa8\\xe8\\x81\\x8c\"},{\"code\":\"2\",\"text\":\"\\xe5\\x85\\xbc\\xe8\\x81\\x8c\"},{\"code\":\"3\",\"text\":\"\\xe5\\xae\\x9e\\xe4\\xb9\\xa0\"},{\"code\":\"4\",\"text\":\"\\xe4\\xb8\\xb4\\xe6\\x97\\xb6\"}],\"computerLevel\":[{\"code\":\"0\",\"text\":\"\\xe4\\xb8\\x8d\\xe9\\x99\\x90\"},{\"code\":\"1\",\"text\":\"\\xe8\\xbe\\x83\\xe5\\xb7\\xae\"},{\"code\":\"2\",\"text\":\"\\xe4\\xb8\\x80\\xe8\\x88\\xac\"},{\"code\":\"3\",\"text\":\"\\xe8\\x89\\xaf\\xe5\\xa5\\xbd\"},{\"code\":\"4\",\"text\":\"\\xe7\\x86\\x9f\\xe7\\xbb\\x83\"},{\"code\":\"5\",\"text\":\"\\xe7\\xb2\\xbe\\xe9\\x80\\x9a\"}],\"languageLevel\":[{\"code\":\"1\",\"text\":\"\\xe8\\xbe\\x83\\xe5\\xb7\\xae\"},{\"code\":\"2\",\"text\":\"\\xe4\\xb8\\x80\\xe8\\x88\\xac\"},{\"code\":\"3\",\"text\":\"\\xe8\\x89\\xaf\\xe5\\xa5\\xbd\"},{\"code\":\"4\",\"text\":\"\\xe7\\x86\\x9f\\xe7\\xbb\\x83\"},{\"code\":\"5\",\"text\":\"\\xe7\\xb2\\xbe\\xe9\\x80\\x9a\"}],\"accommodation\":[{\"code\":\"1\",\"text\":\"\\xe6\\x8f\\x90\\xe4\\xbe\\x9b\"},{\"code\":\"2\",\"text\":\"\\xe4\\xb8\\x8d\\xe6\\x8f\\x90\\xe4\\xbe\\x9b\"},{\"code\":\"3\",\"text\":\"\\xe5\\x8f\\xaf\\xe6\\x8f\\x90\\xe4\\xbe\\x9b\\xe5\\x90\\x83\"},{\"code\":\"4\",\"text\":\"\\xe5\\x8f\\xaf\\xe6\\x8f\\x90\\xe4\\xbe\\x9b\\xe4\\xbd\\x8f\"},{\"code\":\"5\",\"text\":\"\\xe9\\x9d\\xa2\\xe8\\xae\\xae\"}],\"degree\":[{\"code\":\"0\",\"text\":\"\\xe4\\xb8\\x8d\\xe9\\x99\\x90\"},{\"code\":\"1\",\"text\":\"\\xe5\\x88\\x9d\\xe4\\xb8\\xad\"},{\"code\":\"2\",\"text\":\"\\xe9\\xab\\x98\\xe4\\xb8\\xad \"},{\"code\":\"3\",\"text\":\"\\xe4\\xb8\\xad\\xe6\\x8a\\x80 \"},{\"code\":\"4\",\"text\":\"\\xe4\\xb8\\xad\\xe4\\xb8\\x93 \"},{\"code\":\"5\",\"text\":\"\\xe5\\xa4\\xa7\\xe4\\xb8\\x93\"},{\"code\":\"6\",\"text\":\"\\xe6\\x9c\\xac\\xe7\\xa7\\x91\"},{\"code\":\"7\",\"text\":\"\\xe7\\xa1\\x95\\xe5\\xa3\\xab\"},{\"code\":\"8\",\"text\":\"\\xe5\\x8d\\x9a\\xe5\\xa3\\xab \"}],\"indate\":[{\"code\":\"7\",\"text\":\"7\\xe5\\xa4\\xa9\"},{\"code\":\"15\",\"text\":\"15\\xe5\\xa4\\xa9\"},{\"code\":\"30\",\"text\":\"30\\xe5\\xa4\\xa9\"},{\"code\":\"60\",\"text\":\"60\\xe5\\xa4\\xa9\"},{\"code\":\"90\",\"text\":\"90\\xe5\\xa4\\xa9\"}],\"salary\":[{\"code\":\"0\",\"text\":\"\\xe9\\x9d\\xa2\\xe8\\xae\\xae\"},{\"code\":\"1\",\"text\":\"1000 -\"},{\"code\":\"2\",\"text\":\"1001-2000\"},{\"code\":\"3\",\"text\":\"2001-3000\"},{\"code\":\"4\",\"text\":\"3001-4000\"},{\"code\":\"5\",\"text\":\"4001-5000\"},{\"code\":\"6\",\"text\":\"5001-6000\"},{\"code\":\"7\",\"text\":\"6001-8000\"},{\"code\":\"8\",\"text\":\"8001-10000\"},{\"code\":\"9\",\"text\":\"10001-15000\"},{\"code\":\"10\",\"text\":\"15001-20000\"},{\"code\":\"11\",\"text\":\"20001-30000\"},{\"code\":\"12\",\"text\":\"30001-50000\"},{\"code\":\"13\",\"text\":\"50000-80000\"},{\"code\":\"14\",\"text\":\"80000-100000\"},{\"code\":\"15\",\"text\":\"100000+\"}],\"experience\":[{\"code\":\"0\",\"text\":\"\\xe4\\xb8\\x8d\\xe9\\x99\\x90\"},{\"code\":\"1\",\"text\":\"1\\xe5\\xb9\\xb4\\xe4\\xbb\\xa5\\xe4\\xb8\\x8a\"},{\"code\":\"2\",\"text\":\"2\\xe5\\xb9\\xb4\\xe4\\xbb\\xa5\\xe4\\xb8\\x8a\"},{\"code\":\"3\",\"text\":\"3\\xe5\\xb9\\xb4\\xe4\\xbb\\xa5\\xe4\\xb8\\x8a\"},{\"code\":\"5\",\"text\":\"5\\xe5\\xb9\\xb4\\xe4\\xbb\\xa5\\xe4\\xb8\\x8a\"},{\"code\":\"8\",\"text\":\"8\\xe5\\xb9\\xb4\\xe4\\xbb\\xa5\\xe4\\xb8\\x8a\"},{\"code\":\"10\",\"text\":\"10\\xe5\\xb9\\xb4\\xe4\\xbb\\xa5\\xe4\\xb8\\x8a\"}]}";
//        redisClient.set(RefreshConstant.APP_ID, KeyIdentifier.THIRD_PARTY_ENVIRON_PARAM.toString(),6+"",json);
//
//        String json = "{\"salary\":[{\"salary_bottom\":{\"code\":\"0\",\"text\":\"0\"},\"salary_top\":[{\"code\":\"7999\",\"text\":\"7,999\"},{\"code\":\"10999\",\"text\":\"10,999\"},{\"code\":\"13999\",\"text\":\"13,999\"},{\"code\":\"16999\",\"text\":\"16,999\"},{\"code\":\"19999\",\"text\":\"19,999\"}]},{\"salary_bottom\":{\"code\":\"8000\",\"text\":\"8,000\"},\"salary_top\":[{\"code\":\"10999\",\"text\":\"10,999\"},{\"code\":\"13999\",\"text\":\"13,999\"},{\"code\":\"16999\",\"text\":\"16,999\"},{\"code\":\"19999\",\"text\":\"19,999\"},{\"code\":\"24999\",\"text\":\"24,999\"}]},{\"salary_bottom\":{\"code\":\"11000\",\"text\":\"11,000\"},\"salary_top\":[{\"code\":\"13999\",\"text\":\"13,999\"},{\"code\":\"16999\",\"text\":\"16,999\"},{\"code\":\"19999\",\"text\":\"19,999\"},{\"code\":\"24999\",\"text\":\"24,999\"},{\"code\":\"29999\",\"text\":\"29,999\"}]},{\"salary_bottom\":{\"code\":\"14000\",\"text\":\"14,000\"},\"salary_top\":[{\"code\":\"16999\",\"text\":\"16,999\"},{\"code\":\"19999\",\"text\":\"19,999\"},{\"code\":\"24999\",\"text\":\"24,999\"},{\"code\":\"29999\",\"text\":\"29,999\"},{\"code\":\"34999\",\"text\":\"34,999\"}]},{\"salary_bottom\":{\"code\":\"17000\",\"text\":\"17,000\"},\"salary_top\":[{\"code\":\"19999\",\"text\":\"19,999\"},{\"code\":\"24999\",\"text\":\"24,999\"},{\"code\":\"29999\",\"text\":\"29,999\"},{\"code\":\"34999\",\"text\":\"34,999\"},{\"code\":\"44999\",\"text\":\"44,999\"}]},{\"salary_bottom\":{\"code\":\"20000\",\"text\":\"20,000\"},\"salary_top\":[{\"code\":\"24999\",\"text\":\"24,999\"},{\"code\":\"29999\",\"text\":\"29,999\"},{\"code\":\"34999\",\"text\":\"34,999\"},{\"code\":\"44999\",\"text\":\"44,999\"},{\"code\":\"54999\",\"text\":\"54,999\"}]},{\"salary_bottom\":{\"code\":\"25000\",\"text\":\"25,000\"},\"salary_top\":[{\"code\":\"29999\",\"text\":\"29,999\"},{\"code\":\"34999\",\"text\":\"34,999\"},{\"code\":\"44999\",\"text\":\"44,999\"},{\"code\":\"54999\",\"text\":\"54,999\"},{\"code\":\"69999\",\"text\":\"69,999\"}]},{\"salary_bottom\":{\"code\":\"30000\",\"text\":\"30,000\"},\"salary_top\":[{\"code\":\"34999\",\"text\":\"34,999\"},{\"code\":\"44999\",\"text\":\"44,999\"},{\"code\":\"54999\",\"text\":\"54,999\"},{\"code\":\"69999\",\"text\":\"69,999\"},{\"code\":\"89999\",\"text\":\"89,999\"}]},{\"salary_bottom\":{\"code\":\"35000\",\"text\":\"35,000\"},\"salary_top\":[{\"code\":\"44999\",\"text\":\"44,999\"},{\"code\":\"54999\",\"text\":\"54,999\"},{\"code\":\"69999\",\"text\":\"69,999\"},{\"code\":\"89999\",\"text\":\"89,999\"},{\"code\":\"119999\",\"text\":\"119,999\"}]},{\"salary_bottom\":{\"code\":\"45000\",\"text\":\"45,000\"},\"salary_top\":[{\"code\":\"54999\",\"text\":\"54,999\"},{\"code\":\"69999\",\"text\":\"69,999\"},{\"code\":\"89999\",\"text\":\"89,999\"},{\"code\":\"119999\",\"text\":\"119,999\"},{\"code\":\"159999\",\"text\":\"159,999\"}]},{\"salary_bottom\":{\"code\":\"55000\",\"text\":\"55,000\"},\"salary_top\":[{\"code\":\"69999\",\"text\":\"69,999\"},{\"code\":\"89999\",\"text\":\"89,999\"},{\"code\":\"119999\",\"text\":\"119,999\"},{\"code\":\"159999\",\"text\":\"159,999\"},{\"code\":\"2147483647\",\"text\":\"160,000+\"}]},{\"salary_bottom\":{\"code\":\"70000\",\"text\":\"70,000\"},\"salary_top\":[{\"code\":\"89999\",\"text\":\"89,999\"},{\"code\":\"119999\",\"text\":\"119,999\"},{\"code\":\"159999\",\"text\":\"159,999\"},{\"code\":\"2147483647\",\"text\":\"160,000+\"}]},{\"salary_bottom\":{\"code\":\"90000\",\"text\":\"90,000\"},\"salary_top\":[{\"code\":\"119999\",\"text\":\"119,999\"},{\"code\":\"159999\",\"text\":\"159,999\"},{\"code\":\"2147483647\",\"text\":\"160,000+\"}]},{\"salary_bottom\":{\"code\":\"120000\",\"text\":\"120,000\"},\"salary_top\":[{\"code\":\"159999\",\"text\":\"159,999\"},{\"code\":\"2147483647\",\"text\":\"160,000+\"}]},{\"salary_bottom\":{\"code\":\"160000\",\"text\":\"160,000\"},\"salary_top\":[{\"code\":\"2147483647\",\"text\":\"160,000+\"}]}],\"work_location\":[{\"child_work_location\":[],\"work_location\":{\"code\":\"0\",\"text\":\"----\"}},{\"child_work_location\":[{\"code\":\"1001\",\"text\":\"Admiralty\"},{\"code\":\"1002\",\"text\":\"Central\"},{\"code\":\"1003\",\"text\":\"Sai Wan\"},{\"code\":\"1004\",\"text\":\"Sai Ying Pun\"},{\"code\":\"1005\",\"text\":\"Sheung Wan\"}],\"work_location\":{\"code\":\"1000\",\"text\":\"Central &amp; Western Area\"}},{\"child_work_location\":[],\"work_location\":{\"code\":\"2000\",\"text\":\"Cheung Chau Area\"}},{\"child_work_location\":[{\"code\":\"3001\",\"text\":\"Chai Wan\"},{\"code\":\"3002\",\"text\":\"North Point\"},{\"code\":\"3003\",\"text\":\"Quarry Bay\"},{\"code\":\"3004\",\"text\":\"Sai Wan Ho\"},{\"code\":\"3005\",\"text\":\"Shau Kei Wan\"},{\"code\":\"3006\",\"text\":\"Siu Sai Wan\"},{\"code\":\"3007\",\"text\":\"Tai Koo\"},{\"code\":\"3008\",\"text\":\"Tin Hau\"}],\"work_location\":{\"code\":\"3000\",\"text\":\"Eastern Area\"}},{\"child_work_location\":[{\"code\":\"4001\",\"text\":\"Hung Hom\"},{\"code\":\"4002\",\"text\":\"Kowloon City\"},{\"code\":\"4003\",\"text\":\"To Kwa Wan\"}],\"work_location\":{\"code\":\"4000\",\"text\":\"Kowloon City Area\"}},{\"child_work_location\":[{\"code\":\"5001\",\"text\":\"Kwai Fong\"},{\"code\":\"5002\",\"text\":\"Kwai Hing\"},{\"code\":\"5003\",\"text\":\"Tsing Yi\"}],\"work_location\":{\"code\":\"5000\",\"text\":\"Kwai Tsing Area\"}},{\"child_work_location\":[{\"code\":\"6001\",\"text\":\"Kowloon Bay\"},{\"code\":\"6002\",\"text\":\"Kwun Tong\"},{\"code\":\"6003\",\"text\":\"Ngau Tau Kok\"}],\"work_location\":{\"code\":\"6000\",\"text\":\"Kwun Tong Area\"}},{\"child_work_location\":[{\"code\":\"7001\",\"text\":\"Airport Area\"},{\"code\":\"7002\",\"text\":\"Tung Chung\"}],\"work_location\":{\"code\":\"7000\",\"text\":\"Lantau Island\"}},{\"child_work_location\":[{\"code\":\"8001\",\"text\":\"Fanling\"},{\"code\":\"8002\",\"text\":\"Sheung Shui\"}],\"work_location\":{\"code\":\"8000\",\"text\":\"Northern NT Area\"}},{\"child_work_location\":[{\"code\":\"9001\",\"text\":\"Sai Kung\"},{\"code\":\"9002\",\"text\":\"Tseung Kwan O\"}],\"work_location\":{\"code\":\"9000\",\"text\":\"Sai Kung Area\"}},{\"child_work_location\":[{\"code\":\"10001\",\"text\":\"Cheung Sha Wan\"},{\"code\":\"10002\",\"text\":\"Lai Chi Kok\"},{\"code\":\"10003\",\"text\":\"Sham Shui Po\"}],\"work_location\":{\"code\":\"10000\",\"text\":\"Sham Shui Po Area\"}},{\"child_work_location\":[],\"work_location\":{\"code\":\"11000\",\"text\":\"Shatin Area\"}},{\"child_work_location\":[{\"code\":\"12001\",\"text\":\"Aberdeen\"},{\"code\":\"12002\",\"text\":\"Pok Fu Lam\"}],\"work_location\":{\"code\":\"12000\",\"text\":\"Southern Area\"}},{\"child_work_location\":[],\"work_location\":{\"code\":\"13000\",\"text\":\"Tai Po Area\"}},{\"child_work_location\":[],\"work_location\":{\"code\":\"14000\",\"text\":\"Tsuen Wan Area\"}},{\"child_work_location\":[],\"work_location\":{\"code\":\"15000\",\"text\":\"Tuen Mun Area\"}},{\"child_work_location\":[{\"code\":\"16001\",\"text\":\"Causeway Bay\"},{\"code\":\"16002\",\"text\":\"Wan Chai\"}],\"work_location\":{\"code\":\"16000\",\"text\":\"Wan Chai Area\"}},{\"child_work_location\":[],\"work_location\":{\"code\":\"17000\",\"text\":\"Wong Tai Sin Area\"}},{\"child_work_location\":[{\"code\":\"18001\",\"text\":\"Mong Kok\"},{\"code\":\"18002\",\"text\":\"Tsim Sha Tsui\"},{\"code\":\"18003\",\"text\":\"Yau Ma Tei\"}],\"work_location\":{\"code\":\"18000\",\"text\":\"Yau Tsim Mong Area\"}},{\"child_work_location\":[{\"code\":\"19001\",\"text\":\"Tin Shui Wai\"},{\"code\":\"19002\",\"text\":\"Yuen Long\"}],\"work_location\":{\"code\":\"19000\",\"text\":\"Yuen Long Area\"}},{\"child_work_location\":[],\"work_location\":{\"code\":\"20000\",\"text\":\"Others\"}},{\"child_work_location\":[],\"work_location\":{\"code\":\"21000\",\"text\":\"Others - China\"}},{\"child_work_location\":[],\"work_location\":{\"code\":\"22000\",\"text\":\"Others - Macau\"}},{\"child_work_location\":[],\"work_location\":{\"code\":\"23000\",\"text\":\"Overseas\"}},{\"child_work_location\":[],\"work_location\":{\"code\":\"24000\",\"text\":\"Not Specified\"}}]}";
//        redisClient.set(RefreshConstant.APP_ID, KeyIdentifier.THIRD_PARTY_ENVIRON_PARAM.toString(),8+"",json);
        //51
//        positionId=1913159;
//        thirdPartyPositionId=582;
//        testEmail(positionId,thirdPartyPositionId);
//
//        //猎聘
//        positionId=1914334;
//        thirdPartyPositionId=589;
//        testEmail(positionId,thirdPartyPositionId);
//
//        //智联
//        positionId=1913182;
//        thirdPartyPositionId=588;
//        testEmail(positionId,thirdPartyPositionId);

//        positionId=1912885;
//        thirdPartyPositionId=561;
//        testEmail(positionId,thirdPartyPositionId);

        //JobsDB
        positionId=1914923;
        thirdPartyPositionId=592;
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
                        "    'positionId': '1914923'," +
                        "    'accountId': '712'" +
                        "  }," +
                        "  'status': 2" +
                        "}";
        PositionForSyncResultPojo pojo= JSON.parseObject(json,PositionForSyncResultPojo.class);

        pojo.getData().setChannel(String.valueOf(twoParam.getR1().getChannel()));
        pojo.getData().setPositionId(String.valueOf(positionId));

        failedNotification.sendUnKnowResultMail(position, twoParam.getR1(),twoParam.getR2(),pojo);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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