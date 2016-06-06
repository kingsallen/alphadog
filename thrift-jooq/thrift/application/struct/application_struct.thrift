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