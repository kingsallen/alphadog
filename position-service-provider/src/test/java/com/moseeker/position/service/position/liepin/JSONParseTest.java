package com.moseeker.position.service.position.liepin;

import com.alibaba.fastjson.JSON;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobPositionDO;

import org.junit.Test;
/**
 * @author cjm
 * @date 2018-07-09 15:58
 **/
public class JSONParseTest {
    @Test
    public void testParse(){
        String str = "{\"benefits\":[{\"company_id\":4,\"id\":4},{\"company_id\":4,\"id\":10}],\"employment_type\":0,\"filter_ids\":[],\"occupation\":\"\",\"gender\":2,\"city\":\"北京，长春，长沙\",\"procince\":\"\",\"email_notice\":0,\"language\":\"英语六级\",\"industry\":\"电子商务\",\"opreator\":82690,\"team_id\":0,\"title\":\"delay16\",\"experience\":\"1\",\"emails\":[],\"feature\":\"\",\"occupation_code\":\"\",\"hraccount_id\":82690,\"degree_above\":false,\"profile_filter\":[],\"company\":\"仟寻招聘DEV1\",\"id\":1923212,\"department\":\"\",\"emails_conf\":false,\"salary_bottom\":12,\"email_resume_conf\":0,\"company_id\":4,\"salary_top\":13,\"degree\":0,\"count\":1,\"profile_cc_mail_enabled\":0,\"requirement\":\"《我不是药神》是由文牧野执导，宁浩、徐峥共同监制的剧情片，徐峥、周一围、王传君、谭卓、章宇、杨新鸣等主演 。影片讲述了神油店老板程勇从一个交不起房租的男性保健品商贩程勇，一跃成为印度仿制药“格列宁”独家代理商的故事。该片于2018年7月5日在中国上映。\",\"candidate_source\":0,\"accountabilities\":\"《我不是药神》是由文牧野执导，宁浩、徐峥共同监制的剧情片，徐峥、周一围、王传君、谭卓、章宇、杨新鸣等主演 。影片讲述了神油店老板程勇从一个交不起房租的男性保健品商贩程勇，一跃成为印度仿制药“格列宁”独家代理商的故事。该片于2018年7月5日在中国上映。\",\"district\":\"\",\"management_experience\":1,\"locations\":[{\"code\":110000,\"name\":\"北京\"},{\"code\":220100,\"name\":\"长春\"},{\"code\":430100,\"name\":\"长沙\"}],\"experience_above\":false,\"status\":0}";

        JobPositionDO jobPositionDO = JSON.parseObject(str, JobPositionDO.class);
        System.out.println(jobPositionDO);
    }
}
