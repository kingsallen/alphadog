namespace java com.moseeker.thrift.gen.application.struct

typedef string Timestamp;

struct Application {
     1: i64 id,                     // 申请ID
     2: i64 wechat_id,              // 公众号ID
     3: i64 position_id,            // 职位ID
     4: i64 recommender_id,         // 推荐者的微信ID,wx_user.id
     5: Timestamp submit_time,      // 申请提交时间
     6: i64 status_id,              // hr_award_config.id,	申请状态ID
     7: i64 l_application_id,       // ATS的申请ID
     8: i64 reward,                 // 当前申请的积分记录
     9: i64 source_id,              // job_source.id,	对应的ATS
    10: i64 applier_id,             // user_user.id,	用户ID
    11: i32 ats_status,             // 0:unuse,	1:waiting,	2:failed
    12: string applier_name,        // 申请人名称
    13: i32 disable,                // 是否有效，0：有效，1：无效
    14: i32 routine,                // 判断申请来自客户公众号还是聚合平台
    15: i32 is_viewed,              // 该申请是否被浏览，0：已浏览，1：未浏览
    16: i32 not_suitable,           // 是否不合适，0：合适，1：不合适
    17: i64 company_id,             // hr_company.id，公司表ID
    18: i32 app_tpl_id,             // 申请状态,hr_award_config_template.id
    19: Timestamp _create_time,     // 表记录创建时间
    20: Timestamp update_time,      // 最新更新时间
    21: i32 proxy                   // 是否是代理投递	0：正常数据，1：代理假投递
}