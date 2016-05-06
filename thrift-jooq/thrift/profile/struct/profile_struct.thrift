# file: profile.thrift

namespace java com.moseeker.thrift.gen.profile.struct

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
typedef string Timestamp;

struct CommonQuery {
    1: i32 appid,
    2: optional i32 limit=10,
    3: optional i32 offset,
    4: optional i32 page,
    5: optional i32 per_page,
    6: optional string sortby,
    7: optional string order,
    8: optional string fields,
    9: optional bool nocache=false
    10: optional i32 id,
    11: optional i32 userid,
    12: optional i32 companyid,
    13: optional map<string, string> equalFilter
}

struct Profile { 
    1: i32 id,
    2: string uuid,
    3: i32 lang,
    4: i32 source, 
    5: i32 completeness,
    6: i32 user_id,
    7: Timestamp create_time,
    8: Timestamp update_time
}

struct ProfilePagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<Profile> profiles
}

struct Attachment { 
    1: i32 id,
    2: i32 profile_id,
    3: string name,
    4: string path,
    5: string description,
    6: Timestamp create_time,
    7: Timestamp update_time
}

struct AttachmentPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<Attachment> attacments
}

struct Basic { 
    1: i32 profile_id,
    2: Timestamp birth,
    3: i16 gender,
    4: string idnumber, 
    5: string location,
    6: string nationality,
    7: i16 marriage,
    8: i16 residence,
    9: string address,
    10: i16 residencetype,
    11: double height,
    12: double weight, 
    13: string weixin,
    14: string qq,
    16: Timestamp create_time,
    17: Timestamp update_time
}

struct BasicPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<Basic> basics
}

struct Education { 
	1: i32 id,
    2: i32 profile_id,
    3: Timestamp starDate,
    4: Timestamp endDate, 
    5: i16 end_until_now,
    6: i16 degree,
    7: i16 school_code,
    8: string school_name,
    9: i16 major_code,
    10: string major_name,
    11: i16 type,
    12: string description, 
    13: Timestamp create_time,
    14: Timestamp update_time
}

struct EducationPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<Education> educations
}

struct EducationExt { 
	1: i32 profile_id,
    2: Timestamp graduation,
    3: i16 majorrank,
    4: i16 degree, 
    5: string gpa,
    6: Timestamp create_time,
    7: Timestamp update_time
}

struct EducationExtPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<EducationExt> educationExts
}

struct ProfileExt { 
	1: i32 profile_id,
    2: string homepage,
    3: string assessment,
    4: string interest, 
    5: Timestamp create_time,
    6: Timestamp update_time
}

struct ProfileExtPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<ProfileExt> profileExts
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

struct ProfileImportPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<ProfileImport> profileImports
}

struct Intention { 
	1: i32 id,
	2: i32 profile_id,
    3: string positions,
    4: string industries,
    5: string work_cities, 
    6: i16 workstate,
    7: i16 salary_type,
    8: i16 salary_code,
    9: i16 workdays,
    10: i16 business_trip,
    11: i16 nightjob,
    12: i16 worktype,
    13: i16 shift,
    14: i16 icanstart,
    15: Timestamp create_time,
    16: Timestamp update_time
}

struct IntentionPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<Intention> intentions
}

struct Internship { 
	1: i32 id,
	2: i32 profile_id,
    3: Timestamp startDate,
    4: Timestamp endDate,
    5: i16 end_until_now, 
    6: string company,
    7: string department,
    8: i16 position_code,
    9: i16 position_name,
    10: string description,
    11: Timestamp create_time,
    12: Timestamp update_time
}

struct InternshipPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<Internship> internships
}

struct Language { 
	1: i32 id,
	2: i32 profile_id,
    3: i16 name,
    4: string level,
    5: Timestamp create_time,
    6: Timestamp update_time
}

struct LanguagePagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<Language> languages
}

struct ProjectExp { 
	1: i32 id,
	2: i32 profile_id,
	3: Timestamp startDate,
	4: Timestamp endDate,
	5: i16 end_until_now,
	6: string company,
	7: string name,
	8: string role,
	9: string work_desc,
	10:	string project_desc,
    11: Timestamp create_time,
    12: Timestamp update_time
}

struct ProjectExpPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<ProjectExp> projectExps
}

struct Reward { 
	1: i32 id,
	2: i32 profile_id,
	3: i16 type,
	4: Timestamp reward_date,
	5: string name,
	6: string description,
    7: Timestamp create_time,
    8: Timestamp update_time
}

struct RewardPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<Reward> rewards
}

struct SchoolJob { 
	1: i32 id,
	2: i32 profile_id,
	3: Timestamp startDate,
	4: Timestamp endDate,
	5: i16 end_until_now,
	6: string position,
	7: string description,
    8: Timestamp create_time,
    9: Timestamp update_time
}

struct SchoolJobPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<SchoolJob> schoolJobs
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

struct SkillPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<Skill> skills
}

struct Training { 
	1: i32 id,
	2: i32 profile_id,
	3: string name,
	4: Timestamp start_date,
	5: Timestamp end_date,
	6: string orgnization,
	7: string description,
    8: Timestamp create_time,
    9: Timestamp update_time
}

struct TrainingPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<Training> trainings
}

struct WorkExp { 
	1: i32 id,
	2: i32 profile_id,
	3: Timestamp startDate,
	4: Timestamp endDate,
	5: i16 end_until_now,
	6: i16 salary_type,
	7: i16 salary_code,
	8: i16 industry_code,
	9: string industry_name,
	10: string company,
	11: string department,
	12: i16 position_code,
	13: string position_name,
	14: string description,
	15: i16 work_type,
	16: i16 scale,
    17: Timestamp create_time,
    18: Timestamp update_time
}

struct WorkExpPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<WorkExp> workExps
}

struct Works { 
	1: i32 id,
	2: i32 profile_id,
	3: string name,
	4: string url,
	5: string description,
    6: Timestamp create_time,
    7: Timestamp update_time
}

struct WorksPagination {
	1: i32 total_row,
	2: i32 total_page,
	3: i32 page_number,
	4: i32 page_size,
	5: list<Works> works
}
