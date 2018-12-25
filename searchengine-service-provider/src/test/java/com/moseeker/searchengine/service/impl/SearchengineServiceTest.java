package com.moseeker.searchengine.service.impl;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.searchengine.config.AppConfig;
import com.moseeker.searchengine.thrift.SearchengineServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class SearchengineServiceTest {

//    @Autowired
//    private TalentpoolSearchengine talentpoolSearchengine;
    @Autowired
    private SearchengineServiceImpl searchengineServiceImpl;

    @Test
    public void getUserListByCompanyTagCountTest() throws Exception {
        String str = "{\"account_type\":\"1\",\"city_code\":\"13200,652901,659002,152900\",\"city_name\"" +
                ":\"阿坝,阿拉善盟,阿勒泰\",\"color\":\"#2CD6B1\",\"company_name\":\"阿里巴巴\",\"contain_any_key\"" +
                ":\"0\",\"create_time\":1541001600000,\"degree\":\"1\",\"disable\":\"1\",\"id\":\"1\",\"" +
                "in_last_job_search_company\":\"1\",\"in_last_job_search_position\":\"1\",\"intention_city_code\":" +
                "\"1\",\"intention_city_name\":\"上海\",\"intention_salary_code\":\"1\",\"is_recommend\":\"0\",\"" +
                "max_age\":\"30\",\"min_age\":\"20\",\"name\":\"测试标签添加name\",\"origins\":\"1\",\"past_position\":\"架构师\"," +
                "\"sex\":\"1\",\"size\":\"0\",\"update_time\":1541001600000,\"user_id\":\"5289289\",\"work_years\":\"1\"}";
        Map<String, String> params = JSON.parseObject(str,Map.class);
//        int result=talentpoolSearchengine.getUserListByCompanyTagCount(params);
        int result = searchengineServiceImpl.queryCompanyTagUserIdListCount(params);
        System.out.println(JSON.toJSONString(result));
        Assert.assertNotNull(result);
//        String str1 = "2018-11-01 00:00:00";
//        String str2 = talentpoolSearchengine.getLongTime(str1);
//        System.out.println(str1);
//        System.out.println(str2);
    }

    @Test
    public void queryProfileFilterUserIdListTest() throws Exception {
        String str = "{\"page_number\":\"0\",\"appid\":\"11025\",\"filter_map_list\":[{\"account_type\":\"2\",\"intention_salary_code\":\"\",\"company_id\":\"66667\",\"create_time\":1537191853000,\"sex\":\"0\",\"degree\":\"\",\"city_code\":\"\",\"past_position\":\"\",\"work_years\":\"\",\"max_age\":\"0\",\"city_name\":\"\",\"update_time\":1537240233000,\"disable\":\"1\",\"in_last_job_search_position\":\"0\",\"company_name\":\"\",\"is_recommend\":\"1\",\"name\":\"内推筛选规则（0917）-来自仟寻只看内推\",\"intention_city_name\":\"\",\"origins\":\"\",\"in_last_job_search_company\":\"0\",\"id\":\"237\",\"min_age\":\"0\",\"hr_id\":\"66667\",\"intention_city_code\":\"\"}],\"page_size\":\"0\"}";

    }

}