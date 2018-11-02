# file: company.struct

namespace java com.moseeker.thrift.gen.company.struct
include "../../dao/struct/hrdb_struct.thrift"


typedef string Timestamp


struct TalentpoolCompanyTagDO {
   1: optional i32 id,
   2: optional i32 company_id,
   3: optional string name,
   4: optional string color,
   5: optional string work_years="",
   6: optional string city_name="",
   7: optional string city_code="",
   8: optional string degree="",
   9: optional string past_position="",
   10: optional i32  in_last_job_search_position=0,
   11: optional i32 min_age=0,
   12: optional i32 max_age=0,
   13: optional string intention_city_name="",
   14: optional string intention_city_code="",
   15: optional string intention_salary_code="",
   16: optional i32 sex=0,
   17: optional i32 is_recommend=0,
   18: optional string company_name="",
   19: optional i32 in_last_job_search_company=0,
   20: optional i32 source=0,
   21: optional Timestamp create_time,
   22: optional Timestamp update_time,
   23: optional i32 disable=1,
   24: optional string origins="",
   25: optional list<string> keyword_list,
   26: optional i8 contain_any_key
}
struct TalentpoolHrAutomaticTagDO {
   1: optional i32 id,
   2: optional i32 hr_id,
   3: optional string name,
   4: optional string color,
   5: optional string work_years="",
   6: optional string city_name="",
   7: optional string city_code="",
   8: optional string degree="",
   9: optional string past_position="",
   10: optional i32  in_last_job_search_position=0,
   11: optional i32 min_age=0,
   12: optional i32 max_age=0,
   13: optional string intention_city_name="",
   14: optional string intention_city_code="",
   15: optional string intention_salary_code="",
   16: optional i32 sex=0,
   17: optional i32 is_recommend=0,
   18: optional string company_name="",
   19: optional i32 in_last_job_search_company=0,
   20: optional i32 source=0,
   21: optional Timestamp create_time,
   22: optional Timestamp update_time,
   23: optional i32 disable=1,
   24: optional string origins="",
   25: optional list<string> keyword_list,
   26: optional i8 contain_any_key
}

 struct ActionForm {
     1: optional i32 type,
     2: optional i32 value
  }

struct EmailAccountInfo {
    1: optional i32 id,                 
    2: optional i32 company_id,         //公司编号
    3: optional string abbersive,       //公司简称
    4: optional i32 balance,            //账号余额
    5: optional i32 total,              //总采购额度
    6: optional i32 use_num             //已经使用额度
}

struct EmailAccountForm {
    1: optional i32 total,
    2: optional i32 page_number,
    3: optional i32 page_size,
    4: optional i32 company_id,
    5: optional list<EmailAccountInfo> email_accounts
}

struct EmailAccountConsumption {
    1: optional i32 id,
    2: optional i32 company_id,
    3: optional i8 type,
    4: optional i32 lost,
    5: optional string create_time
}

struct EmailAccountConsumptionForm {
    1: optional i32 total,
    2: optional i32 page_number,
    3: optional i32 page_size,
    4: optional i32 company_id,
    5: optional list<EmailAccountConsumption> purchases
}
