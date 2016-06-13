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
    18: optional byte employment_type,
    19: optional string hr_email,
    20: optional i32 degree,
    21: optional string feature,
    22: optional byte candidate_source,
    23: optional string occupation,
    24: optional string industry,
    25: optional byte email_resume_conf,
    26: optional i32 l_PostingTargetId,
    27: optional i32 priority,
    28: optional i32 share_tpl_id,
    29: optional i32 count,
    30: optional i32 salary_top,
    31: optional i32 salary_bottom,
    32: optional byte experience_above,
    33: optional byte degree_above,
    34: optional byte management_experience,
    35: optional byte gender,
    36: optional i32 publisher,
    37: optional i32 app_cv_config_id,
    38: optional i32 source,
    39: optional byte hb_status,
    40: optional i32 age,
    41: optional string major_required,
    42: optional string work_address,
    43: optional string keyword,
    44: optional string reporting_to,
    45: optional i32 is_hiring,
    46: optional i32 underlings,
    47: optional byte language_required,
    48: optional i32 target_industry,
    49: optional i32 current_status
}
