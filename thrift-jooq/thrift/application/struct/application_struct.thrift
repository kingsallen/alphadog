namespace java com.moseeker.thrift.gen.application.struct

typedef string Timestamp;

/*
* 申请记录实体
*
*/
struct JobApplication {
     1: optional i64 id,                     // 申请ID
     2: optional i64 wechat_id,              // 公众号ID
     3: optional i64 position_id,            // 职位ID
     4: optional i64 recommender_id,         // 推荐者的微信ID,wx_user.id
     5: optional Timestamp submit_time,      // 申请提交时间
     6: optional i64 status_id,              // hr_award_config.id,	申请状态ID
     7: optional i64 l_application_id,       // ATS的申请ID
     8: optional i64 reward,                 // 当前申请的积分记录
     9: optional i64 source_id,              // job_source.id,	对应的ATS
    10: optional i64 applier_id,             // user_user.id,	用户ID
    11: optional i32 ats_status,             // 0:unuse,	1:waiting,	2:failed
    12: optional string applier_name,        // 申请人名称
    13: optional i32 disable,                // 是否有效，0：有效，1：无效
    14: optional i32 routine,                // 判断申请来自客户公众号还是聚合平台
    15: optional i32 is_viewed,              // 该申请是否被浏览，0：已浏览，1：未浏览
    16: optional i32 not_suitable,           // 是否不合适，0：合适，1：不合适
    17: optional i64 company_id,             // hr_company.id，公司表ID
    18: optional i32 app_tpl_id,             // 申请状态,hr_award_config_template.id
    19: optional Timestamp _create_time,     // 表记录创建时间
    20: optional Timestamp update_time,      // 最新更新时间
    21: optional i32 proxy                   // 是否是代理投递	0：正常数据，1：代理假投递
}

struct JobResumeBasic{
	
	 1: optional i64 id,                     // 申请ID
     2: optional i64 app_id,              	 // 公众号ID
     3: optional i64 position_id,            // 职位ID
     4: optional string lastname,            // 姓名
     5: optional i32 age,            		 // 年龄
     6: optional i32 work_exp_years,         // 工作年限
     7: optional string job_position,        // 工作经历－职位
     8: optional string job_company,         // 工作经历－公司
     9: optional string job_start,           // 工作经历-开始时间
    10: optional string job_end,             // 工作经历-结束时间
    11: optional string edu_grade,           // 教育经历-学历
    12: optional string edu_school,          // 教育经历-学校
    13: optional string edu_start,           // 教育经历-开始时间
    14: optional string edu_end,             // 教育经历-结束时间
    15: optional i32 resume_type,            // 简历类型 0：仟寻简历，1：附件简历
    16: optional i32 cv_type,            	 // 简历类型 0：PC简历，1：仟寻手机填写的简历，2：前程无忧 3：猎聘 4：智联 5:linkedin 13：email简历
    17: optional i32 email_resume_status,    // 简历状态,（0，有效；1,未收到回复邮件；2，文件格式不支持；3，附件超过10M；9，提取邮件失败）
    18: optional i32 view_count,    		 // 浏览次数
}

/*
* 自定义简历 申请副本实体
*
*/
struct JobResumeOther {
    1:  optional i64 app_id,                  // 申请ID
    2:  optional string other,                // 自定义字段json数据
    3:  optional Timestamp create_time,       // 表记录创建时间
    4:  optional Timestamp update_time        // 最新更新时间
}