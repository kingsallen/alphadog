# file: profile.thrift

namespace java com.moseeker.thrift.gen.position.struct

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
typedef string Timestamp;

struct Position {
    1: i32 id,
    2: string jobnumber, // 职位编号
    3: i32 company_id,
    4: string city,
    5: string department,
    6: i32 l_jobid,
    7: string publish_date,
    8: string stop_date, // 截止日期
    9:  string accountabilities, // 职位描述
    10: string experience, // 经验要求
    11: string requirement, // 任职条件
    12: string language,
    13: i32 status,
    14: i32 visitnum,
    15: i32 source_id,
    16: string update_time,
    17: byte employment_type,
    18: string hr_email,
    19: i32 degree,
    20: string feature,
    21: byte candidate_source,
    22: string occupation,
    23: string industry,
    24: byte email_resume_conf,
    25: i32 l_PostingTargetId,
    26: i32 priority,
    27: i32 share_tpl_id,
    28: i32 count,
    29: i32 salary_top,
    30: i32 salary_bottom,
    31: byte experience_above,
    32: byte degree_above,
    33: byte management_experience,
    34: byte gender,
    35: i32 publisher,
    36: i32 app_cv_config_id,
    37: i32 source,
    38: byte hb_status,
    39: i32 age,
    40: string major_required,
    41: string work_address,
    42: string keyword,
    43: string reporting_to,
    44: i32 is_hiring,
    45: i32 underlings,
    46: byte language_required,
    47: i32 target_industry,
    48: i32 current_status
}
