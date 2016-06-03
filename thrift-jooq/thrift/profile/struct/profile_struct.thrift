# file: profile.thrift

namespace java com.moseeker.thrift.gen.profile.struct

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
typedef string Timestamp;

struct Attachment { 
    1: i32 id,
    2: i32 profile_id,
    3: string name,
    4: string path,
    5: string description,
    6: Timestamp create_time,
    7: Timestamp update_time
}

struct Profile { 
    1: i32 id,
    2: string uuid,
    3: i32 lang,
    4: i32 source, 
    5: i32 completeness,
    6: i32 user_id,
    7: i16 disable,
    8: Timestamp create_time,
    9: Timestamp update_time
}

struct Awards { 
    1: i32 id,
    2: i32 profile_id,
    3: Timestamp reward_date,
    4: string name, 
    5: string award_winning_status,
    6: string level,
    7: string description,
    8: Timestamp create_time,
    9: Timestamp update_time
}

struct Basic { 
    1: i32 profile_id,
    2: string name,
    3: i16 gender,
    4: i32 nationality_code,
    5: string nationality_name,
    6: i32 city_code,
    7: string city_name,
    8: Timestamp birth,
    9: string weixin,
   10: string qq,
   11: string motto,
   12: string self_introduction,
   13: Timestamp create_time,
   14: Timestamp update_time
}

struct Credentials {
    1: i32 id,
    2: i32 profile_id,
    3: string name,
    4: string organization,
    5: string code,
    6: string url,
    7: Timestamp get_date,
    8: string score,
    9: Timestamp create_time,
   10: Timestamp update_time
}

struct Education { 
	1: i32 id,
    2: i32 profile_id,
    3: Timestamp start_date,
    4: Timestamp end_date, 
    5: i16 end_until_now,
    6: i32 degree,
    7: i32 college_code,
    8: string college_name,
    9: string college_logo,
    10: string major_code,
    11: string major_name,
    12: string description, 
    13: i16 is_full,
    14: i16 is_unified,
    15: i16 is_study_abroad,
    16: string study_abroad_country,
    17: Timestamp create_time,
    18: Timestamp update_time
}

struct ProfileImport { 
	1: i32 profile_id,
    2: i16 source,
    3: Timestamp last_update_time,
    4: string account_id, 
    5: string resume_id,
    6: string user_name,
    7: Timestamp create_time,
    8: Timestamp update_time
}

struct Intention { 
	1: i32 id,
	2: i32 profile_id,
    3: i32 workstate,
    4: i16 salary_type,
    5: i32 salary_code, 
    6: string tag,
    7: i16 consider_venture_company_opportunities,
    8: Timestamp create_time,
    9: Timestamp update_time,
   10: i16 worktype,
   11: string salary_str,
   12: map<i32, string> industries,
   13: map<i32, string> positions,
   14: map<i32, string> cities
}

struct Language { 
	1: i32 id,
    2: i32 profile_id,
    3: string name,
    4: i16 level, 
    5: Timestamp create_time,
    6: Timestamp update_time
}

struct CustomizeResume { 
    1: i32 profile_id,
    2: string other,
    3: Timestamp create_time,
    4: Timestamp update_time
}

struct ProjectExp { 
	1: i32 id,
	2: i32 profile_id,
    3: Timestamp start_date,
    4: Timestamp end_date,
    5: i16 end_until_now, 
    6: string name,
	7: string company_name,
    8: i16 is_it,
    9: string dev_tool,
    10: string hardware, 
    11: string software, 
    12: string url,
    13: string description,
    14: string role,
    15: string responsibility, 
    16: string achievement,
    17: string member, 
    18: Timestamp create_time,
    19: Timestamp update_time
}

struct Skill { 
	1: i32 id,
	2: i32 profile_id,
	3: string name,
	4: i16 level,
	5: i16 month,
    6: Timestamp create_time,
    7: Timestamp update_time
}

struct WorkExp { 
	1: i32 id,
	2: i32 profile_id,
	3: Timestamp start_date,
	4: Timestamp end_date,
	5: i16 end_until_now,
	6: i16 salary_type,
	7: i32 salary_code,
	8: i32 industry_code,
	9: string industry_name,
	10: i32 company_id,
	11: string department_name,
	12: i32 position_code,
	13: string position_name,
	14: string description,
	15: i16 type,
	16: i32 city_code,
	17: string city_name,
	18: string report_to,
	19: i32 underlings,
	20: string reference,
	21: string resign_reason,
	22: string achievement,
    23: Timestamp create_time,
    24: Timestamp update_time,
    25: string company_name,
    26: string company_logo,
    27: i16 source,
    28: string job
}

struct Works { 
	1: i32 id,
	2: i32 profile_id,
	3: string name,
	4: string url,
	5: string cover,
	6: string description,
    7: Timestamp create_time,
    8: Timestamp update_time
}