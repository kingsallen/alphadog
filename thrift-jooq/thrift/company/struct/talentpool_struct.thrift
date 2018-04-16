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
   6: optional string city_name,
   7: optional string city_code,
   8: optional string degree,
   9: optional string past_position,
   10: optional i32  in_last_job_search_position,
   11: optional i32 min_age,
   12: optional i32 max_age,
   13: optional string intention_city_name,
   14: optional string intention_city_code,
   15: optional string intention_salary_code,
   16: optional i32 sex,
   17: optional i32 is_recommend,
   18: optional string company_name,
   19: optional i32 in_last_job_search_company,
   20: optional i32 source,
   21: optional Timestamp create_time,
   22: optional Timestamp update_time,
   23: optional i32 disable,
   24: optional string origins
}


