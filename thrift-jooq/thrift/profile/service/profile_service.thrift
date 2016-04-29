# file: profile_service.thrift

include "../struct/profile_struct.thrift"
namespace java com.moseeker.thrift.gen.profile.service

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
service ProfileServices {
    list<profile_struct.Profile> getProfiles(1:profile_struct.CommonQuery query, 2:profile_struct.Profile profile);
    profile_struct.ProfilePagination getProfilePagination(1:profile_struct.CommonQuery query, 2:profile_struct.Profile profile);
    i32 postProfiles(1: list<profile_struct.Profile> profiles);
    i32 putProfiles(1: list<profile_struct.Profile> profiles);
    i32 delProfiles(1: list<profile_struct.Profile> profiles);
    
    i32 postProfile(1: profile_struct.Profile profile);
    i32 putProfile(1: profile_struct.Profile profile);
    i32 delProfile(1: profile_struct.Profile profile);
}

service AttachmentServices {
    list<profile_struct.Attachment> getAttachments(1:profile_struct.CommonQuery query, 2:profile_struct.Attachment profile);
    profile_struct.AttachmentPagination getAttachmentPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Attachment profile);
    i32 postAttachments(1: list<profile_struct.Attachment> profiles);
    i32 putAttachments(1: list<profile_struct.Attachment> profiles);
    i32 delAttachments(1: list<profile_struct.Attachment> profiles);
    
    i32 postAttachment(1: profile_struct.Attachment profile);
    i32 putAttachment(1: profile_struct.Attachment profile);
    i32 delAttachment(1: profile_struct.Attachment profile);
}

service BasicServices {
    list<profile_struct.Basic> getBasics(1:profile_struct.CommonQuery query, 2:profile_struct.Basic basic);
    profile_struct.BasicPagination getBasicPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Basic basic);
    i32 postBasics(1: list<profile_struct.Basic> basics);
    i32 putBasics(1: list<profile_struct.Basic> basics);
    i32 delBasics(1: list<profile_struct.Basic> basics);
    
    i32 postBasic(1: profile_struct.Basic basic);
    i32 putBasic(1: profile_struct.Basic basic);
    i32 delBasic(1: profile_struct.Basic basic);
}

service EducationServices {
    list<profile_struct.Education> getEducations(1:profile_struct.CommonQuery query, 2:profile_struct.Education education);
    profile_struct.EducationPagination getEducationPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Basic basic);
    i32 postEducations(1: list<profile_struct.Education> educations);
    i32 putEducations(1: list<profile_struct.Education> educations);
    i32 delEducations(1: list<profile_struct.Education> educations);
    
    i32 postEducation(1: profile_struct.Education education);
    i32 putEducation(1: profile_struct.Education education);
    i32 delEducation(1: profile_struct.Education education);
}

service EducationExtServices {
    list<profile_struct.EducationExt> getEducationExts(1:profile_struct.CommonQuery query, 2:profile_struct.EducationExt educationExt);
    profile_struct.EducationExtPagination getEducationExtPagination(1:profile_struct.CommonQuery query, 2:profile_struct.EducationExt educationExt);
    i32 postEducationExts(1: list<profile_struct.EducationExt> educationExts);
    i32 putEducationExts(1: list<profile_struct.EducationExt> educationExts);
    i32 delEducationExts(1: list<profile_struct.EducationExt> educationExts);
    
    i32 postEducationExt(1: profile_struct.EducationExt educationExt);
    i32 putEducationExt(1: profile_struct.EducationExt educationExt);
    i32 delEducationExt(1: profile_struct.EducationExt educationExt);
}

service ProfileExtServices {
    list<profile_struct.ProfileExt> getProfileExts(1:profile_struct.CommonQuery query, 2:profile_struct.ProfileExt profileExt);
    profile_struct.ProfileExtPagination getProfileExtPagination(1:profile_struct.CommonQuery query, 2:profile_struct.ProfileExt profileExt);
    i32 postProfileExts(1: list<profile_struct.ProfileExt> profileExts);
    i32 putProfileExts(1: list<profile_struct.ProfileExt> profileExts);
    i32 delProfileExts(1: list<profile_struct.ProfileExt> profileExts);
    
    i32 postProfileExt(1: profile_struct.ProfileExt profileExt);
    i32 putProfileExt(1: profile_struct.ProfileExt profileExt);
    i32 delProfileExt(1: profile_struct.ProfileExt profileExt);
}

service ProfileImportServices {
    list<profile_struct.ProfileImport> getProfileImports(1:profile_struct.CommonQuery query, 2:profile_struct.ProfileImport profileImport);
    profile_struct.ProfileImportPagination getProfileImportPagination(1:profile_struct.CommonQuery query, 2:profile_struct.ProfileImport profileImport);
    i32 postProfileImports(1: list<profile_struct.ProfileImport> profileImports);
    i32 putProfileImports(1: list<profile_struct.ProfileImport> profileImports);
    i32 delProfileImports(1: list<profile_struct.ProfileImport> profileImports);
    
    i32 postProfileImport(1: profile_struct.ProfileImport profileImport);
    i32 putProfileImport(1: profile_struct.ProfileImport profileImport);
    i32 delProfileImport(1: profile_struct.ProfileImport profileImport);
}

service IntentionServices {
    list<profile_struct.Intention> getIntentions(1:profile_struct.CommonQuery query, 2:profile_struct.Intention intention);
    profile_struct.IntentionPagination getIntentionPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Intention intention);
    i32 postIntentions(1: list<profile_struct.Intention> intentions);
    i32 putIntentions(1: list<profile_struct.Intention> intentions);
    i32 delIntentions(1: list<profile_struct.Intention> intentions);
    
    i32 postIntention(1: profile_struct.Intention intention);
    i32 putIntention(1: profile_struct.Intention intention);
    i32 delIntention(1: profile_struct.Intention intention);
}

service InternshipServices {
    list<profile_struct.Internship> getInternships(1:profile_struct.CommonQuery query, 2:profile_struct.Internship internship);
    profile_struct.InternshipPagination getInternshipPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Internship internship);
    i32 postInternships(1: list<profile_struct.Internship> internships);
    i32 putInternships(1: list<profile_struct.Internship> internships);
    i32 delInternships(1: list<profile_struct.Internship> internships);
    
    i32 postInternship(1: profile_struct.Internship internship);
    i32 putInternship(1: profile_struct.Internship internship);
    i32 delInternship(1: profile_struct.Internship internship);
}

service LanguageServices {
    list<profile_struct.Language> getLanguages(1:profile_struct.CommonQuery query, 2:profile_struct.Language language);
    profile_struct.LanguagePagination getLanguagePagination(1:profile_struct.CommonQuery query, 2:profile_struct.Language language);
    i32 postLanguages(1: list<profile_struct.Language> languages);
    i32 putLanguages(1: list<profile_struct.Language> languages);
    i32 delLanguages(1: list<profile_struct.Language> languages);
    
    i32 postLanguage(1: profile_struct.Language language);
    i32 putLanguage(1: profile_struct.Language language);
    i32 delLanguage(1: profile_struct.Language language);
}

service ProjectExpServices {
    list<profile_struct.ProjectExp> getProjectExps(1:profile_struct.CommonQuery query, 2:profile_struct.ProjectExp projectExp);
    profile_struct.ProjectExpPagination getProjectExpPagination(1:profile_struct.CommonQuery query, 2:profile_struct.ProjectExp projectExp);
    i32 postProjectExps(1: list<profile_struct.ProjectExp> projectExps);
    i32 putProjectExps(1: list<profile_struct.ProjectExp> projectExps);
    i32 delProjectExps(1: list<profile_struct.ProjectExp> projectExps);
    
    i32 postProjectExp(1: profile_struct.ProjectExp projectExp);
    i32 putProjectExp(1: profile_struct.ProjectExp projectExp);
    i32 delProjectExp(1: profile_struct.ProjectExp projectExp);
}

service RewardServices {
    list<profile_struct.Reward> getRewards(1:profile_struct.CommonQuery query, 2:profile_struct.Reward reward);
    profile_struct.RewardPagination getRewardPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Reward reward);
    i32 postRewards(1: list<profile_struct.Reward> rewards);
    i32 putRewards(1: list<profile_struct.Reward> rewards);
    i32 delRewards(1: list<profile_struct.Reward> rewards);
    
    i32 postReward(1: profile_struct.Reward reward);
    i32 putReward(1: profile_struct.Reward reward);
    i32 delReward(1: profile_struct.Reward reward);
}

service SchoolJobServices {
    list<profile_struct.SchoolJob> getSchoolJobs(1:profile_struct.CommonQuery query, 2:profile_struct.SchoolJob schoolJob);
    profile_struct.SchoolJobPagination getSchoolJobPagination(1:profile_struct.CommonQuery query, 2:profile_struct.SchoolJob schoolJob);
    i32 postSchoolJobs(1: list<profile_struct.SchoolJob> schoolJobs);
    i32 putSchoolJobs(1: list<profile_struct.SchoolJob> schoolJobs);
    i32 delSchoolJobs(1: list<profile_struct.SchoolJob> schoolJobs);
    
    i32 postSchoolJob(1: profile_struct.SchoolJob schoolJob);
    i32 putSchoolJob(1: profile_struct.SchoolJob schoolJob);
    i32 delSchoolJob(1: profile_struct.SchoolJob schoolJob);
}

service SkillServices {
    list<profile_struct.Skill> getSkills(1:profile_struct.CommonQuery query, 2:profile_struct.Skill skill);
    profile_struct.SkillPagination getSkillPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Skill skill);
    i32 postSkills(1: list<profile_struct.Skill> skills);
    i32 putSkills(1: list<profile_struct.Skill> skills);
    i32 delSkills(1: list<profile_struct.Skill> skills);
    
    i32 postSkill(1: profile_struct.Skill skill);
    i32 putSkill(1: profile_struct.Skill skill);
    i32 delSkill(1: profile_struct.Skill skill);
}

service TrainingServices {
    list<profile_struct.Training> getTrainings(1:profile_struct.CommonQuery query, 2:profile_struct.Training training);
    profile_struct.TrainingPagination getTrainingPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Training training);
    i32 postTrainings(1: list<profile_struct.Training> trainings);
    i32 putTrainings(1: list<profile_struct.Training> trainings);
    i32 delTrainings(1: list<profile_struct.Training> trainings);
    
    i32 postTraining(1: profile_struct.Training training);
    i32 putTraining(1: profile_struct.Training training);
    i32 delTraining(1: profile_struct.Training training);
}

service WorkExpServices {
    list<profile_struct.WorkExp> getWorkExps(1:profile_struct.CommonQuery query, 2:profile_struct.WorkExp workExp);
    profile_struct.WorkExpPagination getWorkExpPagination(1:profile_struct.CommonQuery query, 2:profile_struct.WorkExp workExp);
    i32 postWorkExps(1: list<profile_struct.WorkExp> workExps);
    i32 putWorkExps(1: list<profile_struct.WorkExp> workExps);
    i32 delWorkExps(1: list<profile_struct.WorkExp> workExps);
    
    i32 postWorkExp(1: profile_struct.WorkExp workExp);
    i32 putWorkExp(1: profile_struct.WorkExp workExp);
    i32 delWorkExp(1: profile_struct.WorkExp workExp);
}

service WorksServices {
    list<profile_struct.Works> getWorkss(1:profile_struct.CommonQuery query, 2:profile_struct.Works works);
    profile_struct.WorksPagination getWorksPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Works works);
    i32 postWorkss(1: list<profile_struct.Works> works);
    i32 putWorkss(1: list<profile_struct.Works> works);
    i32 delWorkss(1: list<profile_struct.Works> works);
    
    i32 postWorks(1: profile_struct.Works works);
    i32 putWorks(1: profile_struct.Works works);
    i32 delWorks(1: profile_struct.Works works);
}