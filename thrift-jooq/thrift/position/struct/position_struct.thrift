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
    4: string province,
    5: string city,
    6: string department,
    7: i32 l_jobid,
    8: string publish_date,
    9: string stop_date, // 截止日期
    10: string accountabilities, // 职位描述
    11: string experience, // 经验要求
    12: string requirement, // 任职条件
    13: string salary, // 不用
    14: string language, //
    15: i32 job_grade, // 不用
    16: i32 status, //
    17: i32 visitnum,
    18: string lastvisit,
    19: i32 source_id,
    20: string update_time,
    21: string business_group,
    22: byte employment_type,
    23: string hr_email,
    24: string benefits,
    25: i32 degree,
    26: string feature,
    27: byte email_notice,
    28: byte candidate_source,
    29: string occupation,
    30: byte is_recom,
    31: string industry,
    32: i32 hongbao_config_id,
    33: i32 hongbao_config_recom_id,
    34: i32 hongbao_config_app_id,
    35: byte email_resume_conf,
    36: i32 l_PostingTargetId,
    37: i32 priority,
    38: i32 share_tpl_id,
    39: string district,
    40: i32 count,
    41: i32 salary_top,
    42: i32 salary_bottom,
    43: byte experience_above,
    44: byte degree_above,
    45: byte management_experience,
    46: byte gender,
    47: i32 publisher,
    48: i32 app_cv_config_id,
    49: i32 source,
    50: byte hb_status,
    51: i32 child_company_id,
    52: i32 age,
    53: string major_required,
    54: string work_address,
    55: string keyword,
    56: string reporting_to,
    57: i32 is_hiring,
    58: i32 underlings,
    59: byte language_required,
    60: i32 target_industry,
    61: i32 current_status
}
