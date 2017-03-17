# file: profile.thrift

namespace java com.moseeker.thrift.gen.position.struct

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
typedef string Timestamp;

struct Position {
    1: optional i32 id,
    2: optional string jobnumber, // 职位编号
    3: optional i32 company_id,
    4: optional string title, // 职位标题
    5: optional string city,
    6: optional string department,
    7: optional i32 l_jobid,
    8: optional string publish_date,
    9: optional string stop_date, // 截止日期
    10: optional string accountabilities, // 职位描述
    11: optional string experience, // 经验要求
    12: optional string requirement, // 任职条件
    13: optional string language,
    14: optional i32 status,
    15: optional i32 visitnum,
    16: optional i32 source_id,
    17: optional string update_time,
    18: optional i8 employment_type,
    19: optional string hr_email,
    20: optional i32 degree,
    21: optional string feature,
    22: optional i8 candidate_source,
    23: optional string occupation,
    24: optional string industry,
    25: optional i8 email_resume_conf,
    26: optional i32 l_PostingTargetId,
    27: optional i32 priority,
    28: optional i32 share_tpl_id,
    29: optional i32 count,
    30: optional i32 salary_top,
    31: optional i32 salary_bottom,
    32: optional i8 experience_above,
    33: optional i8 degree_above,
    34: optional i8 management_experience,
    35: optional i8 gender,
    36: optional i32 publisher,
    37: optional i32 app_cv_config_id,
    38: optional i32 source,
    39: optional i8 hb_status,
    40: optional i32 age,
    41: optional string major_required,
    42: optional string work_address,
    43: optional string keyword,
    44: optional string reporting_to,
    45: optional i32 is_hiring,
    46: optional i32 underlings,
    47: optional i8 language_required,
    48: optional i32 target_industry,
    49: optional i32 current_status,
    50: optional map<i32, string> cities
}
/*
	第三方自定义职能
*/
struct JobOccupationCustom{
  1: optional i32 id ,
  2: optional string name
}

/*
 * 第三方渠道职位，用于职位同步。
 */
struct ThirdPartyPositionForSynchronization {
    1: string title,
    2: string category_main_code,
    3: string category_main,
    4: string category_sub_code,
    5: string category_sub,
    6: string quantity,
    7: string degree_code,
    8: string degree,
    9: string experience_code,
    10: string experience,
    11: string salary_low,
    12: string salary_high,
    13: string description,
    14: string pub_place_code,
    15: i32 position_id,
    16: string work_place,
    17: string email,
    18: string stop_date,
    19: i32 channel,
    20: string type_code,
    21: string job_id,
    22: string pub_place_name
}

/*
 *
 */
struct ThirdPartyPositionForSynchronizationWithAccount {
    1: string user_name,
    2: string password,
    3: string member_name,
    4: string position_id,
    5: string channel,
    6: ThirdPartyPositionForSynchronization position_info
}


// 批量修改职位
struct BatchHandlerJobPostion{
    1:i32 appid,
    2:list<JobPostrionObj> data,
    3:string fields_nooverwrite,
    4:bool nodelete,
    5:string fields_nohash
}

struct JobPostrionObj{
    1:string jobnumber,
    2:i32 company_id,
    3:string title,// 职位名称
    4:string province,
    5:string department,// 所在部门
    6:Timestamp stop_date,
    7:string accountabilities, //职责描述
    8:string experience,  //任职条件
    9:string salary, // 薪水
    10:string language,//  外语要求
    11:i8 status,// 状态 ： 0 有效 ，1 删除 2 :撤下
    12:i32 source_id, // 职位来源
    13:string business_group, // 事业群
    14:i32 employment_type, // 应聘职位类型，0：全职，1：兼职 2：共同工 3：实习 9：其他
    15:string hr_email, // HR联系邮箱，申请通知
    16:i32 degree,// 学历 0：无 1：大专 2：本科 3：硕士 4：MBA 5:博士
    17:string  feature, // 职位特色
    18:i32  email_notice,  // 申请后是否给HR发送邮件，0：发送，1：不发送
    19:i32  candidate_source,// 0：社招 1：校招 2：定向招聘
    20:string occupation, // 职位职能
    21:string  industry, // 所属行业
    22:i32 email_resume_conf, // 0:允许使用email简历进行投递 1：不允许使用email简历投递
    23:string district, // 添加区
    24:i32 count, // 添加招聘人数 0：不限
    25:double salary_top, // 工资上限
    26:double  salary_bottom, // 工资下限
    27:i32 experience_above, // 经验要求 1：需要 0：不需要
    28:i32   degree_above, // 学位要 1：需要 0：不需要
    29:i32   management_experience, // 是否需要管理经验 1：不需要 0：需求
    30:i32   gender, // 性别要求 0 ：女  1： 男  2：不限
    31:i32   publisher, // hr_account.id
    32:i32   app_cv_config_id, // 职位开启并配置自定义模板
    33:i32   source, // 来源 0：手动创建 1：导入 9 ：ATS导入
    34:i32   age, // 年龄要求 0：无要求
    35:string   major_required, // 专业要求
    36:string   work_address, // 工作地址
    37:string   keyword, // 职位关键字
    38:string   reporting_to, // 回报对象
    39:i32   is_hiring, // 是否急招 1：是 0： 否
    40:i32   underlings,  // 下属人数 0：没有下属
    41:i32   language_required, // 语言要求 1：是 0：否
    42:i32   current_status, // 当前职位状态 0 ：招募中 1：未发布 2：暂停 3：撤下 4： 关闭
    43:i64   position_code, // 智能字典code
    44:i32   team_id,  // 职位所属团队
    45:list<City> city, // 城市列表
    46:string extra,
    47:i32 id,
    48:string requirement
}

struct City{
    1:string type,
    2:string value
}

struct DelePostion{
    1:i32 id,
    2:i32 company_id,
    3:string jobnumber,
    4:i32 source_id
}