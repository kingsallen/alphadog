# file: profile.thrift

namespace java com.moseeker.thrift.gen.position.struct

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
typedef string Timestamp;

struct Position {
    1:  i32 id,
    2:  string jobnumber, // 职位编号
    3:  i32 company_id,
    4:  string title, // 职位标题
    5:  string city,
    6:  string department,
    7:  i32 l_jobid,
    8:  string publish_date,
    9:  string stop_date, // 截止日期
    10: string accountabilities, // 职位描述
    11: string experience, // 经验要求
    12: string requirement, // 任职条件
    13: string language,
    14: i32 status,
    15: i32 visitnum,
    16: i32 source_id,
    17: string update_time,
    18: byte employment_type,
    19: string hr_email,
    20: i32 degree,
    21: string feature,
    22: byte candidate_source,
    23: string occupation,
    24: string industry,
    25: byte email_resume_conf,
    26: i32 l_PostingTargetId,
    27: i32 priority,
    28: i32 share_tpl_id,
    29: i32 count,
    30: i32 salary_top,
    31: i32 salary_bottom,
    32: byte experience_above,
    33: byte degree_above,
    34: byte management_experience,
    35: byte gender,
    36: i32 publisher,
    37: i32 app_cv_config_id,
    38: i32 source,
    39: byte hb_status,
    40: i32 age,
    41: string major_required,
    42: string work_address,
    43: string keyword,
    44: string reporting_to,
    45: i32 is_hiring,
    46: i32 underlings,
    47: byte language_required,
    48: i32 target_industry,
    49: i32 current_status
}
