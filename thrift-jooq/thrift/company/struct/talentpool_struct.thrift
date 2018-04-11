# file: company.struct

namespace java com.moseeker.thrift.gen.company.struct
include "../../dao/struct/hrdb_struct.thrift"


typedef string Timestamp


struct TalentpoolCompanyTagDO {
    1: optional i32 id,
    2: optional i32 company_id,
    3: optional string name,
    4: optional string color,
    5: optional string work_years,
    6: optional string submit_time,
    7: optional string city_name,
    8: optional string city_code,
    9: optional string degree,
    10: optional string past_position,
    11: optional i32  in_last_job_search_position,
    12: optional i32 min_age,
    13: optional i32 max_age,
    14: optional string intention_city_name,
    15: optional string intention_city_code,
    16: optional string intention_salary_code,
    17: optional i32 sex,
    18: optional i32 is_recommend,
    19: optional string company_name,
    20: optional i32 in_last_job_search_company,
    21: optional i32 source,
    22: optional Timestamp create_time,
    23: optional Timestamp update_time,
    24: optional i32 disable,
    25: optional string origins
 }

 struct TalentpoolProfileFilterDO {
    1: optional i32 id,
    2: optional i32 company_id,
    3: optional string name,
    4: optional string origins,
    5: optional string work_years,
    6: optional string submit_time,
    7: optional string city_name,
    8: optional string city_code,
    9: optional string degree,
    10: optional string past_position,
    11: optional i32  in_last_job_search_position,
    12: optional i32 min_age,
    13: optional i32 max_age,
    14: optional string intention_city_name,
    15: optional string intention_city_code,
    16: optional string intention_salary_code,
    17: optional i32 sex,
    18: optional i32 is_recommend,
    19: optional string company_name,
    20: optional i32 in_last_job_search_company,
    21: optional Timestamp create_time,
    22: optional Timestamp update_time,
    23: optional i32 disable
 }


