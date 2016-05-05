# file: profile_service.thrift

include "../struct/profile_struct.thrift"
namespace java com.moseeker.thrift.gen.profile.service

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
service ProfileServices {
    list<profile_struct.Profile> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.Profile profile);
    profile_struct.ProfilePagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Profile profile);
    i32 postResources(1: list<profile_struct.Profile> profiles);
    i32 putResources(1: list<profile_struct.Profile> profiles);
    i32 delResources(1: list<profile_struct.Profile> profiles);
    
    profile_struct.Profile getResource(1:profile_struct.CommonQuery query, 2:profile_struct.Profile profile);
    i32 postResource(1: profile_struct.Profile profile);
    i32 putResource(1: profile_struct.Profile profile);
    i32 delResource(1: profile_struct.Profile profile);
}

service AttachmentServices {
    list<profile_struct.Attachment> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.Attachment profile);
    profile_struct.AttachmentPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Attachment profile);
    i32 postResources(1: list<profile_struct.Attachment> profiles);
    i32 putResources(1: list<profile_struct.Attachment> profiles);
    i32 delResources(1: list<profile_struct.Attachment> profiles);
    
    profile_struct.Attachment getResource(1:profile_struct.CommonQuery query, 2:profile_struct.Attachment profile);
    i32 postResource(1: profile_struct.Attachment profile);
    i32 putResource(1: profile_struct.Attachment profile);
    i32 delResource(1: profile_struct.Attachment profile);
}

service BasicServices {
    list<profile_struct.Basic> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.Basic basic);
    profile_struct.BasicPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Basic basic);
    i32 postResources(1: list<profile_struct.Basic> basics);
    i32 putResources(1: list<profile_struct.Basic> basics);
    i32 delResources(1: list<profile_struct.Basic> basics);
    
    profile_struct.Basic getResource(1:profile_struct.CommonQuery query, 2:profile_struct.Basic basic);
    i32 postResource(1: profile_struct.Basic basic);
    i32 putResource(1: profile_struct.Basic basic);
    i32 delResource(1: profile_struct.Basic basic);
}

service EducationServices {
    list<profile_struct.Education> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.Education education);
    profile_struct.EducationPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Basic basic);
    i32 postResources(1: list<profile_struct.Education> educations);
    i32 putResources(1: list<profile_struct.Education> educations);
    i32 delResources(1: list<profile_struct.Education> educations);
    
    profile_struct.Education getResource(1:profile_struct.CommonQuery query, 2:profile_struct.Education education);
    i32 postResource(1: profile_struct.Education education);
    i32 putResource(1: profile_struct.Education education);
    i32 delResource(1: profile_struct.Education education);
}

service EducationExtServices {
    list<profile_struct.EducationExt> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.EducationExt educationExt);
    profile_struct.EducationExtPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.EducationExt educationExt);
    i32 postResources(1: list<profile_struct.EducationExt> educationExts);
    i32 putResources(1: list<profile_struct.EducationExt> educationExts);
    i32 delEducations(1: list<profile_struct.EducationExt> educationExts);
    
    profile_struct.EducationExt getResource(1:profile_struct.CommonQuery query, 2:profile_struct.EducationExt educationExt);
    i32 postResource(1: profile_struct.EducationExt educationExt);
    i32 putResource(1: profile_struct.EducationExt educationExt);
    i32 delResource(1: profile_struct.EducationExt educationExt);
}

service ProfileExtServices {
    list<profile_struct.ProfileExt> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.ProfileExt profileExt);
    profile_struct.ProfileExtPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.ProfileExt profileExt);
    i32 postResources(1: list<profile_struct.ProfileExt> profileExts);
    i32 putResources(1: list<profile_struct.ProfileExt> profileExts);
    i32 delResources(1: list<profile_struct.ProfileExt> profileExts);
    
    profile_struct.ProfileExt getResource(1:profile_struct.CommonQuery query, 2:profile_struct.ProfileExt profileExt);
    i32 postResource(1: profile_struct.ProfileExt profileExt);
    i32 putResource(1: profile_struct.ProfileExt profileExt);
    i32 delResource(1: profile_struct.ProfileExt profileExt);
}

service ProfileImportServices {
    list<profile_struct.ProfileImport> getResource(1:profile_struct.CommonQuery query, 2:profile_struct.ProfileImport profileImport);
    profile_struct.ProfileImportPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.ProfileImport profileImport);
    i32 postResources(1: list<profile_struct.ProfileImport> profileImports);
    i32 putResources(1: list<profile_struct.ProfileImport> profileImports);
    i32 delResources(1: list<profile_struct.ProfileImport> profileImports);
    
    profile_struct.ProfileImport getResourc(1:profile_struct.CommonQuery query, 2:profile_struct.ProfileImport profileImport);
    i32 postResource(1: profile_struct.ProfileImport profileImport);
    i32 putResource(1: profile_struct.ProfileImport profileImport);
    i32 delResource(1: profile_struct.ProfileImport profileImport);
}

service IntentionServices {
    list<profile_struct.Intention> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.Intention intention);
    profile_struct.IntentionPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Intention intention);
    i32 postResources(1: list<profile_struct.Intention> intentions);
    i32 putResources(1: list<profile_struct.Intention> intentions);
    i32 delResources(1: list<profile_struct.Intention> intentions);
    
    profile_struct.Intention getResource(1:profile_struct.CommonQuery query, 2:profile_struct.Intention intention);
    i32 postResource(1: profile_struct.Intention intention);
    i32 putResource(1: profile_struct.Intention intention);
    i32 delResource(1: profile_struct.Intention intention);
}

service InternshipServices {
    list<profile_struct.Internship> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.Internship internship);
    profile_struct.InternshipPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Internship internship);
    i32 postResources(1: list<profile_struct.Internship> internships);
    i32 putResources(1: list<profile_struct.Internship> internships);
    i32 delResources(1: list<profile_struct.Internship> internships);
    
    profile_struct.Internship getResource(1:profile_struct.CommonQuery query, 2:profile_struct.Internship internship);
    i32 postResource(1: profile_struct.Internship internship);
    i32 putResource(1: profile_struct.Internship internship);
    i32 delResource(1: profile_struct.Internship internship);
}

service LanguageServices {
    list<profile_struct.Language> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.Language language);
    profile_struct.LanguagePagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Language language);
    i32 postResources(1: list<profile_struct.Language> languages);
    i32 putResources(1: list<profile_struct.Language> languages);
    i32 delResources(1: list<profile_struct.Language> languages);
    
    profile_struct.Language getResource(1:profile_struct.CommonQuery query, 2:profile_struct.Language language);
    i32 postResource(1: profile_struct.Language language);
    i32 putResource(1: profile_struct.Language language);
    i32 delResource(1: profile_struct.Language language);
}

service ProjectExpServices {
    list<profile_struct.ProjectExp> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.ProjectExp projectExp);
    profile_struct.ProjectExpPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.ProjectExp projectExp);
    i32 postResources(1: list<profile_struct.ProjectExp> projectExps);
    i32 putResources(1: list<profile_struct.ProjectExp> projectExps);
    i32 delResources(1: list<profile_struct.ProjectExp> projectExps);
    
    profile_struct.ProjectExp getResource(1:profile_struct.CommonQuery query, 2:profile_struct.ProjectExp projectExp);
    i32 postResource(1: profile_struct.ProjectExp projectExp);
    i32 putResource(1: profile_struct.ProjectExp projectExp);
    i32 delResource(1: profile_struct.ProjectExp projectExp);
}

service RewardServices {
    list<profile_struct.Reward> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.Reward reward);
    profile_struct.RewardPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Reward reward);
    i32 postResources(1: list<profile_struct.Reward> rewards);
    i32 putResources(1: list<profile_struct.Reward> rewards);
    i32 delResources(1: list<profile_struct.Reward> rewards);
    
    profile_struct.Reward getResource(1:profile_struct.CommonQuery query, 2:profile_struct.Reward reward);
    i32 postResource(1: profile_struct.Reward reward);
    i32 putResource(1: profile_struct.Reward reward);
    i32 delResource(1: profile_struct.Reward reward);
}

service SchoolJobServices {
    list<profile_struct.SchoolJob> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.SchoolJob schoolJob);
    profile_struct.SchoolJobPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.SchoolJob schoolJob);
    i32 postResources(1: list<profile_struct.SchoolJob> schoolJobs);
    i32 putResources(1: list<profile_struct.SchoolJob> schoolJobs);
    i32 delResources(1: list<profile_struct.SchoolJob> schoolJobs);
    
    profile_struct.SchoolJob getResource(1:profile_struct.CommonQuery query, 2:profile_struct.SchoolJob schoolJob);
    i32 postResource(1: profile_struct.SchoolJob schoolJob);
    i32 putResource(1: profile_struct.SchoolJob schoolJob);
    i32 delResource(1: profile_struct.SchoolJob schoolJob);
}

service SkillServices {
    list<profile_struct.Skill> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.Skill skill);
    profile_struct.SkillPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Skill skill);
    i32 postResources(1: list<profile_struct.Skill> skills);
    i32 putResources(1: list<profile_struct.Skill> skills);
    i32 delResources(1: list<profile_struct.Skill> skills);
    
    profile_struct.Skill getResource(1:profile_struct.CommonQuery query, 2:profile_struct.Skill skill);
    i32 postResource(1: profile_struct.Skill skill);
    i32 putResource(1: profile_struct.Skill skill);
    i32 delResource(1: profile_struct.Skill skill);
}

service TrainingServices {
    list<profile_struct.Training> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.Training training);
    profile_struct.TrainingPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Training training);
    i32 postResources(1: list<profile_struct.Training> trainings);
    i32 putResources(1: list<profile_struct.Training> trainings);
    i32 delResources(1: list<profile_struct.Training> trainings);
    
    profile_struct.Training getResource(1:profile_struct.CommonQuery query, 2:profile_struct.Training training);
    i32 postResource(1: profile_struct.Training training);
    i32 putResource(1: profile_struct.Training training);
    i32 delResource(1: profile_struct.Training training);
}

service WorkExpServices {
    list<profile_struct.WorkExp> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.WorkExp workExp);
    profile_struct.WorkExpPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.WorkExp workExp);
    i32 postResources(1: list<profile_struct.WorkExp> workExps);
    i32 putResources(1: list<profile_struct.WorkExp> workExps);
    i32 delResources(1: list<profile_struct.WorkExp> workExps);
    
    profile_struct.WorkExp getResource(1:profile_struct.CommonQuery query, 2:profile_struct.WorkExp workExp);
    i32 postResource(1: profile_struct.WorkExp workExp);
    i32 putResource(1: profile_struct.WorkExp workExp);
    i32 delResource(1: profile_struct.WorkExp workExp);
}

service WorksServices {
    list<profile_struct.Works> getResources(1:profile_struct.CommonQuery query, 2:profile_struct.Works works);
    profile_struct.WorksPagination getPagination(1:profile_struct.CommonQuery query, 2:profile_struct.Works works);
    i32 postResources(1: list<profile_struct.Works> works);
    i32 putResources(1: list<profile_struct.Works> works);
    i32 delResources(1: list<profile_struct.Works> works);
    
    profile_struct.Works getResource(1:profile_struct.CommonQuery query, 2:profile_struct.Works works);
    i32 postResource(1: profile_struct.Works works);
    i32 putResource(1: profile_struct.Works works);
    i32 delResource(1: profile_struct.Works works);
}
