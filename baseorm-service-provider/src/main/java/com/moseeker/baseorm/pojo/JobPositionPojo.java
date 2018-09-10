package com.moseeker.baseorm.pojo;

import com.moseeker.common.util.DateUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 职位表Pojo类
 *
 * Created by zzh on 16/7/12.
 */
public class JobPositionPojo {

    public int id;
    public String  jobnumber; // '职位编号'
    public int company_id; // 'hr_company.id'
    public String title; // '职位标题',
    public String province; // '所在省',
    public String city; // '所在城市',
    public String department; // '所在部门'
    public int l_jobid; // 'jobid from ATS or other channel',
    public String accountabilities; // 'Job responsibilities',
    public String experience; // '工作经验',
    public String requirement; // '职位要求',
    public String salary; //'薪水',
    public String language; // '外语要求',
    public int job_grade; // '优先级',
    public int status; //'0 有效, 1 无效, 2 撤销',
    public int visitnum;
    public String lastvisit; // openid of last visiter',
    public int source_id; // 职位来源 0：Moseeker',
    public String business_group; //'事业群',
    public int employment_type; // '0:全职，1：兼职：2：合同工 3:实习 9:其他',
    public String hr_email; // 'HR联系人邮箱，申请通知',
    public String benefits; // '职位福利',
    public int degree; // '0:无 1:大专 2:本科 3:硕士 4:MBA 5:博士 6:中专 7:高中 8: 博士后 9:初中',
    public String feature; // '职位特色',
    public int email_notice; // 'application after email notice hr, 0:yes 1:no',
    public int candidate_source; // '0:社招 1：校招 2:定向招聘',
    public String occupation; // '职位职能',
    public int is_recom; // '是否需要推荐0：需要 1：不需要',
    public String industry; // '所属行业',
    public int hongbao_config_id;
    public int hongbao_config_recom_id;
    public int hongbao_config_app_id;
    public int email_resume_conf; // '0:允许使用email简历进行投递；1:不允许使用email简历投递',
    public int l_PostingTargetId; // 'lumesse每一个职位会生成一个PostingTargetId,用来生成每个职位的投递邮箱地址',
    public int priority; // '是否置顶',
    public int share_tpl_id; // '分享分类0:无1:高大上2：小清新3：逗比',
    public String district; // '添加区(省市区的区)',
    public int count; // '添加招聘人数, 0：不限',
    public int salary_top; // '薪资上限（k）',
    public int salary_bottom; // '薪资下限（k）',
    public int experience_above; // '及以上 1：需要， 0：不需要',
    public int degree_above; //'及以上 1：需要， 0：不需要',
    public int management_experience; //'是否要求管理经验0：需要1：不需要',
    public int gender; // '0-> female, 1->male, 2->all',
    public int publisher; // 'hr_account.id',
    public int app_cv_config_id; //'职位开启并配置自定义模板 hr_app_cv_conf.id',
    public int source; // '0:手动创建, 1:导入, 9:ATS导入',
    public int hb_status; //'是否正参加活动：0=未参加  1=正参加点击红包活动  2=正参加被申请红包活动  3=正参加1+2红包活动',
    public int age; // '年龄要求, 0：无要求',
    public String major_required; //'专业要求',
    public String work_address; //'上班地址',
    public String keyword; // '职位关键词',
    public String reporting_to; // '汇报对象',
    public int is_hiring; //'是否急招, 1:是 0:否',
    public int underlings; // '下属人数， 0:没有下属',
    public int language_required; // '语言要求，1:是 0:否',
    public int target_industry; // 期望人选所在行业',
    public int current_status; // '0:招募中, 1: 未发布, 2:暂停, 3:撤下, 4:关闭',
    public int position_code; // '职能字典code, dict_position.code',
    public Date publish_date; // 'Default: now, set by js',
    public Date stop_date; // '截止日期',
    public Timestamp update_time;
    public byte is_referral; //是否是内推职位

    // 派生字段 非表中字段
    public int publisher_company_id; //'hr_child_company.id'
    public String publish_date_view = DateUtils.dateToPattern(this.publish_date, DateUtils.SHOT_TIME);
    public String update_time_view = DateUtils.dateToPattern(this.update_time, DateUtils.SHOT_TIME);
    public String degree_name = "";
    public String employment_type_name = "";
    public String gender_name = "";
    public String candidate_source_name = "";

    // 自定义字段
    public String custom = "";
    //团队的id
    public int team_id;
    //团队名称
    public String  team_name="";
    public int city_flag=0;//1代表只是全国,0代表还含有其他城市

    public SearchData search_data;
    public List<Map<String,Object>> position_feature;
    public String city_ename;

    public int is_referral;//是否是内推职位 1 是 0不是
}
