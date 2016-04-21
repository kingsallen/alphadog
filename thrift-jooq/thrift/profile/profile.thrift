# file: companyfollowers.thrift

namespace java com.moseeker.thrift.gen.profile

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
    12: optional i32 companyid
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

struct Attachment { 
    1: i32 id,
    2: i32 profile_id,
    3: i32 name,
    4: string source, 
    5: string path,
    6: string description,
    7: Timestamp create_time,
    8: Timestamp update_time
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

struct EducationExt { 
	1: i32 profile_id,
    2: Timestamp graduation,
    3: i16 majorrank,
    4: i16 degree, 
    5: string gpa,
    6: Timestamp create_time,
    7: Timestamp update_time
}

struct ProfileExt { 
	1: i32 profile_id,
    2: string homepage,
    3: string assessment,
    4: string interest, 
    5: Timestamp create_time,
    6: Timestamp update_time
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

struct Language { 
	1: i32 id,
	2: i32 profile_id,
    3: i16 name,
    4: string level,
    5: Timestamp create_time,
    6: Timestamp update_time
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

struct Skill { 
	1: i32 id,
	2: i32 profile_id,
	3: string name,
	4: i16 level,
	5: i16 month,
    6: Timestamp create_time,
    7: Timestamp update_time
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

struct Works { 
	1: i32 id,
	2: i32 profile_id,
	3: string name,
	4: string url,
	5: string description,
    6: Timestamp create_time,
    7: Timestamp update_time
}

service ProfileServices {
    list<Profile> getProfiles(1:CommonQuery query, 2:Profile profile);
    i32 postProfiles(1: list<Profile> profiles);
    i32 putProfiles(1: list<Profile> profiles);
    i32 delProfiles(1: list<Profile> profiles);
    
    i32 postProfile(1: Profile profile);
    i32 putProfile(1: Profile profile);
    i32 delProfile(1: Profile profile);
}

service AttachmentServices {
    list<Attachment> getAttachments(1:CommonQuery query, 2:Attachment profile);
    i32 postAttachments(1: list<Attachment> profiles);
    i32 putAttachments(1: list<Attachment> profiles);
    i32 delAttachments(1: list<Attachment> profiles);
    
    i32 postAttachment(1: Attachment profile);
    i32 putAttachment(1: Attachment profile);
    i32 delAttachment(1: Attachment profile);
}

service BasicServices {
    list<Basic> getBasics(1:CommonQuery query, 2:Basic basic);
    i32 postBasics(1: list<Basic> basics);
    i32 putBasics(1: list<Basic> basics);
    i32 delBasics(1: list<Basic> basics);
    
    i32 postBasic(1: Attachment profile);
    i32 putBasic(1: Attachment profile);
    i32 delBasic(1: Attachment profile);
}

service EducationServices {
    list<Education> getEducations(1:CommonQuery query, 2:Education education);
    i32 postEducations(1: list<Education> educations);
    i32 putEducations(1: list<Education> educations);
    i32 delEducations(1: list<Education> educations);
    
    i32 postEducation(1: Education education);
    i32 putEducation(1: Education education);
    i32 delEducation(1: Education education);
}

service EducationExtServices {
    list<EducationExt> getEducationExts(1:CommonQuery query, 2:EducationExt educationExt);
    i32 postEducationExts(1: list<EducationExt> educationExts);
    i32 putEducationExts(1: list<EducationExt> educationExts);
    i32 delEducationExts(1: list<EducationExt> educationExts);
    
    i32 postEducationExt(1: EducationExt educationExt);
    i32 putEducationExt(1: EducationExt educationExt);
    i32 delEducationExt(1: EducationExt educationExt);
}

service ProfileExtServices {
    list<ProfileExt> getProfileExts(1:CommonQuery query, 2:ProfileExt profileExt);
    i32 postProfileExts(1: list<ProfileExt> profileExts);
    i32 putProfileExts(1: list<ProfileExt> profileExts);
    i32 delProfileExts(1: list<ProfileExt> profileExts);
    
    i32 postProfileExt(1: ProfileExt profileExt);
    i32 putProfileExt(1: ProfileExt profileExt);
    i32 delProfileExt(1: ProfileExt profileExt);
}

service ProfileImportServices {
    list<ProfileImport> getProfileImports(1:CommonQuery query, 2:ProfileImport profileImport);
    i32 postProfileImports(1: list<ProfileImport> profileImports);
    i32 putProfileImports(1: list<ProfileImport> profileImports);
    i32 delProfileImports(1: list<ProfileImport> profileImports);
    
    i32 postProfileImport(1: ProfileImport profileImport);
    i32 putProfileImport(1: ProfileImport profileImport);
    i32 delProfileImport(1: ProfileImport profileImport);
}

service IntentionServices {
    list<Intention> getIntentions(1:CommonQuery query, 2:Intention intention);
    i32 postIntentions(1: list<Intention> intentions);
    i32 putIntentions(1: list<Intention> intentions);
    i32 delIntentions(1: list<Intention> intentions);
    
    i32 postIntention(1: Intention intention);
    i32 putIntention(1: Intention intention);
    i32 delIntention(1: Intention intention);
}

service InternshipServices {
    list<Internship> getInternships(1:CommonQuery query, 2:Internship internship);
    i32 postInternships(1: list<Internship> internships);
    i32 putInternships(1: list<Internship> internships);
    i32 delInternships(1: list<Internship> internships);
    
    i32 postInternship(1: Internship internship);
    i32 putInternship(1: Internship internship);
    i32 delInternship(1: Internship internship);
}

service LanguageServices {
    list<Language> getLanguages(1:CommonQuery query, 2:Language language);
    i32 postLanguages(1: list<Language> languages);
    i32 putLanguages(1: list<Language> languages);
    i32 delLanguages(1: list<Language> languages);
    
    i32 postLanguage(1: Language language);
    i32 putLanguage(1: Language language);
    i32 delLanguage(1: Language language);
}

service ProjectExpServices {
    list<ProjectExp> getProjectExps(1:CommonQuery query, 2:ProjectExp projectExp);
    i32 postProjectExps(1: list<ProjectExp> projectExps);
    i32 putProjectExps(1: list<ProjectExp> projectExps);
    i32 delProjectExps(1: list<ProjectExp> projectExps);
    
    i32 postProjectExp(1: ProjectExp projectExp);
    i32 putProjectExp(1: ProjectExp projectExp);
    i32 delProjectExp(1: ProjectExp projectExp);
}

service RewardServices {
    list<Reward> getRewards(1:CommonQuery query, 2:Reward reward);
    i32 postRewards(1: list<Reward> rewards);
    i32 putRewards(1: list<Reward> rewards);
    i32 delRewards(1: list<Reward> rewards);
    
    i32 postReward(1: Reward reward);
    i32 putReward(1: Reward reward);
    i32 delReward(1: Reward reward);
}

service SchoolJobServices {
    list<SchoolJob> getSchoolJobs(1:CommonQuery query, 2:SchoolJob schoolJob);
    i32 postSchoolJobs(1: list<SchoolJob> schoolJobs);
    i32 putSchoolJobs(1: list<SchoolJob> schoolJobs);
    i32 delSchoolJobs(1: list<SchoolJob> schoolJobs);
    
    i32 postSchoolJob(1: SchoolJob schoolJob);
    i32 putSchoolJob(1: SchoolJob schoolJob);
    i32 delSchoolJob(1: SchoolJob schoolJob);
}

service SkillServices {
    list<Skill> getSkills(1:CommonQuery query, 2:Skill skill);
    i32 postSkills(1: list<Skill> skills);
    i32 putSkills(1: list<Skill> skills);
    i32 delSkills(1: list<Skill> skills);
    
    i32 postSkill(1: Skill skill);
    i32 putSkill(1: Skill skill);
    i32 delSkill(1: Skill skill);
}

service TrainingServices {
    list<Training> getTrainings(1:CommonQuery query, 2:Training training);
    i32 postTrainings(1: list<Training> trainings);
    i32 putTrainings(1: list<Training> trainings);
    i32 delTrainings(1: list<Training> trainings);
    
    i32 postTraining(1: Training training);
    i32 putTraining(1: Training training);
    i32 delTraining(1: Training training);
}

service WorkExpServices {
    list<WorkExp> getWorkExps(1:CommonQuery query, 2:WorkExp workExp);
    i32 postWorkExps(1: list<WorkExp> workExps);
    i32 putWorkExps(1: list<WorkExp> workExps);
    i32 delWorkExps(1: list<WorkExp> workExps);
    
    i32 postWorkExp(1: WorkExp workExp);
    i32 putWorkExp(1: WorkExp workExp);
    i32 delWorkExp(1: WorkExp workExp);
}

service WorksServices {
    list<Works> getWorkss(1:CommonQuery query, 2:Works works);
    i32 postWorkss(1: list<Works> works);
    i32 putWorkss(1: list<Works> works);
    i32 delWorkss(1: list<Works> works);
    
    i32 postWorks(1: Works works);
    i32 putWorks(1: Works works);
    i32 delWorks(1: Works works);
}
