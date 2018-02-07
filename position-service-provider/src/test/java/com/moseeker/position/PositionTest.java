package com.moseeker.position;

import com.moseeker.position.config.AppConfig;
import com.moseeker.position.service.position.liepin.LiepinPositionTransfer;
import com.moseeker.position.service.position.liepin.pojo.PositionLiepin;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.position.struct.JobPostrionObj;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = AppConfig.class)
public class PositionTest {

    @Autowired
    LiepinPositionTransfer transfer;

    @Test
    public void test(){
        ThirdPartyPosition positionForm =new ThirdPartyPosition();
        PositionLiepin position=new PositionLiepin();
        positionForm.setOccupation(new ArrayList<>(Arrays.asList("cate-01","cate-01-05","662","360331")));
        transfer.setOccupation(positionForm,position);
    }


    @Test
    public void test2(){
        String strs[]=str.split("\n");
        Field fields[]=JobPostrionObj.class.getFields();
        out: for(String temp:strs) {
            String temps[]=temp.replace("'","").replace("_","").split(",");
            String dbstr=temps[0];
            for (Field field : fields) {
                if(field.getName().replace("_","").equalsIgnoreCase(dbstr)){
                    continue out;
                }
            }
            System.out.println(dbstr + "\t\t"+ (temps.length==2?temps[1]:""));
        }
    }

    String str="'id,'\n" +
            "'jobnumber,职位编号'\n" +
            "'company_id,company.id'\n" +
            "'title,职位标题'\n" +
            "'province,所在省'\n" +
            "'city,所在城市，多城市使用中文逗号分割'\n" +
            "'department,所在部门'\n" +
            "'l_jobid,jobid from ATS or other channel'\n" +
            "'publish_date,Default: now, set by js'\n" +
            "'stop_date,截止日期'\n" +
            "'accountabilities,Job responsibilities'\n" +
            "'experience,工作经验'\n" +
            "'requirement,职位要求'\n" +
            "'salary,薪水'\n" +
            "'language,外语要求'\n" +
            "'job_grade,优先级'\n" +
            "'status,0 有效, 1 删除, 2 撤下'\n" +
            "'visitnum,'\n" +
            "'lastvisit,openid of last visiter'\n" +
            "'source_id,职位来源 0：Moseeker'\n" +
            "'update_time,'\n" +
            "'business_group,事业群'\n" +
            "'employment_type,0:全职，1：兼职：2：合同工 3:实习 9:其他'\n" +
            "'hr_email,HR联系人邮箱，申请通知'\n" +
            "'benefits,职位福利'\n" +
            "'degree,0:无 1:大专 2:本科 3:硕士 4:MBA 5:博士 6:中专 7:高中 8: 博士后 9:初中'\n" +
            "'feature,职位特色，多福利特色使用#分割'\n" +
            "'email_notice,申请后是否给 HR 发送邮件 0:发送 1:不发送'\n" +
            "'candidate_source,0:社招 1：校招'\n" +
            "'occupation,职位职能'\n" +
            "'is_recom,是否需要推荐0：需要 1：不需要'\n" +
            "'industry,所属行业'\n" +
            "'hongbao_config_id,'\n" +
            "'hongbao_config_recom_id,'\n" +
            "'hongbao_config_app_id,'\n" +
            "'email_resume_conf,0:允许使用email简历进行投递；1:不允许使用email简历投递'\n" +
            "'l_PostingTargetId,lumesse每一个职位会生成一个PostingTargetId,用来生成每个职位的投递邮箱地址'\n" +
            "'priority,是否置顶'\n" +
            "'share_tpl_id,'\n" +
            "'district,添加区(省市区的区)'\n" +
            "'count,添加招聘人数, 0：不限'\n" +
            "'salary_top,薪资上限（k）'\n" +
            "'salary_bottom,薪资下限（k）'\n" +
            "'experience_above,及以上 1：需要， 0：不需要'\n" +
            "'degree_above,及以上 1：需要， 0：不需要'\n" +
            "'management_experience,是否要求管理经验0：需要1：不需要'\n" +
            "'gender,0-> female, 1->male, 2->all'\n" +
            "'publisher,hr_account.id'\n" +
            "'app_cv_config_id,职位开启并配置自定义模板 hr_app_cv_conf.id'\n" +
            "'source,0:手动创建, 1:导入, 9:ATS导入'\n" +
            "'hb_status,是否正参加活动：0=未参加  1=正参加点击红包活动  2=正参加被申请红包活动  3=正参加1+2红包活动'\n" +
            "'child_company_id,hr_child_company.id'\n" +
            "'age,年龄要求, 0：无要求'\n" +
            "'major_required,专业要求'\n" +
            "'work_address,上班地址'\n" +
            "'keyword,职位关键词'\n" +
            "'reporting_to,汇报对象'\n" +
            "'is_hiring,是否急招, 1:是 0:否'\n" +
            "'underlings,下属人数， 0:没有下属'\n" +
            "'language_required,语言要求，1:是 0:否'\n" +
            "'target_industry,期望人选所在行业'\n" +
            "'current_status,已经弃用，0:招募中, 1: 未发布, 2:暂停, 3:撤下, 4:关闭'\n" +
            "'position_code,职能字典code, dict_position.code'\n" +
            "'team_id,职位所属团队'\n" +
            "'profile_cc_mail_enabled,简历申请是否抄送邮箱，0 否；1 是'\n";
}
