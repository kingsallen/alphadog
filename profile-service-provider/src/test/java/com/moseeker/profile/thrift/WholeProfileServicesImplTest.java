package com.moseeker.profile.thrift;

import com.moseeker.baseorm.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileOtherRecord;
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
       String profile = "{\"skills\":[{\"month\":\"12\",\"level\":3,\"name\":\"计算机\"}],\"other\":{\"workyears\":\"4\",\"PoliticalStatus\":\"群众\",\"marriage\":\"已婚\",\"workstate\":\"在职，急寻新工作\",\"residence\":\"山东-临沂\"},\"imports\":{\"user_name\":\"18217206525\",\"source\":3},\"languages\":[{\"level\":1,\"name\":\"日语\"},{\"level\":1,\"name\":\"英语\"}],\"credentials\":[{\"name\":\"雅思\",\"get_date\":\"2018-01-01\"}],\"profile\":{\"user_id\":2198120,\"source\":0,\"completeness\":0,\"lang\":0},\"educations\":[{\"end_date\":\"2002-07-01\",\"college_name\":\"山东大学\",\"edu_degree\":\"本科\",\"degree\":\"5\",\"major_name\":\"电气工程及其自动化\",\"is_unified\":true,\"start_date\":\"1998-01-01\"}],\"intentions\":[{\"cities\":[{\"city_name\":\"上海\",\"city_code\":310000}],\"industries\":[{\"industry_name\":\"互联网/电子商务\"}],\"salary_code\":\"2\",\"workstate\":\"2\",\"worktype\":\"1\",\"positions\":[{\"position_name\":\"质量管理/安全防护\"}]}],\"workexps\":[{\"end_date\":\"2008-12-01\",\"scale\":\"100-499人\",\"description\":\"上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所上海第二研究所\",\"company\":{\"company_name\":\"上海第二研究所\",\"company_property\":3,\"company_scale\":4},\"salary\":\"15000-25000元/月\",\"job\":\"软件测试工程师\",\"start_date\":\"2008-01-01\"},{\"end_date\":\"2007-12-01\",\"scale\":\"500-999人\",\"description\":\"上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所上海第一研究所\",\"company\":{\"company_name\":\"上海第一研究所\",\"company_property\":3,\"company_scale\":5},\"salary\":\"10001-15000元/月\",\"job\":\"高级过工程师\",\"start_date\":\"2007-01-01\"}],\"basic\":{\"city_name\":\"上海\",\"marital_status\":\"已婚\",\"self_introduction\":\"我性格热情开朗，待人友好，具有亲和力，乐于与人沟通。具有良好的团队精神，能适应团队工作，适应力强。 具有高度责任心，工作态度认真，严谨踏实，积极主动，能在较大的压力下保持良好的工作状态。\",\"gender\":\"1\",\"name\":\"裴厚广\",\"politics_status\":\"群众\",\"city_code\":310000,\"birth\":\"1936-1-01\"},\"projectexps\":[{\"end_date\":\"2007-05-01\",\"responsibility\":\"项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始\",\"name\":\"上海瑞能保利项目\",\"description\":\"项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始项目开始\",\"start_date\":\"2007-01-01\"}],\"user\":{\"headimg\":\"https://mypics.zhaopin.cn/avatar/2018/2/13/c4125f99-8261-4d36-9007-924c44b243a2.jpg\",\"mobile\":\"182****6525\",\"name\":\"裴厚广\",\"emial\":\"peihoug****@moseeker.com\"}}";
       int userId = 5228495;
        profileServices.importCV(profile, userId);
    }

    @Autowired
    ProfileParseUtil profileParseUtil;

    @Test
    public void mapToBasicRecord() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("iso_code_2","AF");
        ProfileBasicRecord result = new ProfileUtils().mapToBasicRecord(map, profileParseUtil.initParseProfileParam());
        assert result.getNationalityCode() != null && result.getNationalityCode() != 0;
    }

    @Test
    public void mapToOtherRecord() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("current_industry","计算机");
        ProfileOtherRecord result = new ProfileUtils().mapToOtherRecord(map, profileParseUtil.initParseProfileParam());
        assert result.getOther() != null && "{\"current_industry\":1100}".equals(result.getOther());
    }

}
