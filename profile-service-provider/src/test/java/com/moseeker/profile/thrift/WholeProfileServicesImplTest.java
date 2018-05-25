package com.moseeker.profile.thrift;

import com.moseeker.baseorm.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.entity.biz.ProfileParseUtil;
import com.moseeker.entity.biz.ProfileUtils;
import com.moseeker.profile.config.AppConfig;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by moseeker on 2018/3/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class WholeProfileServicesImplTest {
    @Autowired
    private WholeProfileServicesImpl profileServices;

    @Test
    public void getResource() throws Exception {
       String profile = "{\"other\":{\"workstate\":\"应届毕业生\"},\"imports\":{\"user_name\":\"15884547494\",\"source\":3},\"profile\":{\"user_id\":5228495,\"source\":0,\"completeness\":0,\"lang\":0},\"educations\":[{\"end_date\":\"2019-07-01\",\"college_name\":\"电子科技大学\",\"edu_degree\":\"本科\",\"degree\":\"5\",\"major_name\":\"通信工程\",\"is_unified\":true,\"start_date\":\"2014-09-01\"}],\"intentions\":[{\"cities\":[{\"city_name\":\"广州\",\"city_code\":440100},{\"city_name\":\"深圳\",\"city_code\":440300}],\"industries\":[{\"industry_name\":\"其他\"}],\"salary_code\":\"5\",\"workstate\":\"5\",\"worktype\":\"1\",\"positions\":[{\"position_name\":\"其他\"}]}],\"basic\":{\"name\":\"周颖佳\",\"birth\":\"0-0-01\"},\"user\":{\"headimg\":\"https://img00.zhaopin.cn/2012/img/my/v5/lookResumes.jpg\",\"mobile\":\"E-mail：\",\"name\":\"周颖佳\"}}";
       int userId = 5228495;
        profileServices.importCV(profile, userId);
    }

    @Autowired
    ProfileParseUtil profileParseUtil;

    @Test
    public void mapToBasicRecord() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("current_industry","计算机");
        map.put("iso_code_2","AF");
        ProfileBasicRecord result = new ProfileUtils().mapToBasicRecord(map, profileParseUtil.initParseProfileParam());
        assert result.getNationalityCode() != null && result.getNationalityCode() != 0;
        assert result.getCurrentIndustry() != null && result.getCurrentIndustry() != 0;
    }
}
