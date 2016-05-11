# file: profile_service.thrift

include "../struct/profile_struct.thrift"
namespace java com.moseeker.thrift.gen.profile.service

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
service ProfileServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(2:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.Profile> profiles);
    profile_struct.ProviderResult putResources(1: list<profile_struct.Profile> profiles);
    profile_struct.ProviderResult delResources(1: list<profile_struct.Profile> profiles);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.Profile profile);
    profile_struct.ProviderResult putResource(1: profile_struct.Profile profile);
    profile_struct.ProviderResult delResource(1: profile_struct.Profile profile);
}

service AttachmentServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.Attachment> profiles);
    profile_struct.ProviderResult putResources(1: list<profile_struct.Attachment> profiles);
    profile_struct.ProviderResult delResources(1: list<profile_struct.Attachment> profiles);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.Attachment profile);
    profile_struct.ProviderResult putResource(1: profile_struct.Attachment profile);
    profile_struct.ProviderResult delResource(1: profile_struct.Attachment profile);
}

service BasicServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.Basic> basics);
    profile_struct.ProviderResult putResources(1: list<profile_struct.Basic> basics);
    profile_struct.ProviderResult delResources(1: list<profile_struct.Basic> basics);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.Basic basic);
    profile_struct.ProviderResult putResource(1: profile_struct.Basic basic);
    profile_struct.ProviderResult delResource(1: profile_struct.Basic basic);
}

service EducationServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.Education> educations);
    profile_struct.ProviderResult putResources(1: list<profile_struct.Education> educations);
    profile_struct.ProviderResult delResources(1: list<profile_struct.Education> educations);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.Education education);
    profile_struct.ProviderResult putResource(1: profile_struct.Education education);
    profile_struct.ProviderResult delResource(1: profile_struct.Education education);
}

service EducationExtServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.EducationExt> educationExts);
    profile_struct.ProviderResult putResources(1: list<profile_struct.EducationExt> educationExts);
    profile_struct.ProviderResult delResources(1: list<profile_struct.EducationExt> educationExts);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.EducationExt educationExt);
    profile_struct.ProviderResult putResource(1: profile_struct.EducationExt educationExt);
    profile_struct.ProviderResult delResource(1: profile_struct.EducationExt educationExt);
}

service ProfileExtServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.ProfileExt> profileExts);
    profile_struct.ProviderResult putResources(1: list<profile_struct.ProfileExt> profileExts);
    profile_struct.ProviderResult delResources(1: list<profile_struct.ProfileExt> profileExts);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.ProfileExt profileExt);
    profile_struct.ProviderResult putResource(1: profile_struct.ProfileExt profileExt);
    profile_struct.ProviderResult delResource(1: profile_struct.ProfileExt profileExt);
}

service ProfileImportServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.ProfileImport> profileImports);
    profile_struct.ProviderResult putResources(1: list<profile_struct.ProfileImport> profileImports);
    profile_struct.ProviderResult delResources(1: list<profile_struct.ProfileImport> profileImports);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.ProfileImport profileImport);
    profile_struct.ProviderResult putResource(1: profile_struct.ProfileImport profileImport);
    profile_struct.ProviderResult delResource(1: profile_struct.ProfileImport profileImport);
}

service IntentionServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.Intention> intentions);
    profile_struct.ProviderResult putResources(1: list<profile_struct.Intention> intentions);
    profile_struct.ProviderResult delResources(1: list<profile_struct.Intention> intentions);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.Intention intention);
    profile_struct.ProviderResult putResource(1: profile_struct.Intention intention);
    profile_struct.ProviderResult delResource(1: profile_struct.Intention intention);
}

service InternshipServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.Internship> internships);
    profile_struct.ProviderResult putResources(1: list<profile_struct.Internship> internships);
    profile_struct.ProviderResult delResources(1: list<profile_struct.Internship> internships);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.Internship internship);
    profile_struct.ProviderResult putResource(1: profile_struct.Internship internship);
    profile_struct.ProviderResult delResource(1: profile_struct.Internship internship);
}

service LanguageServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.Language> languages);
    profile_struct.ProviderResult putResources(1: list<profile_struct.Language> languages);
    profile_struct.ProviderResult delResources(1: list<profile_struct.Language> languages);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.Language language);
    profile_struct.ProviderResult putResource(1: profile_struct.Language language);
    profile_struct.ProviderResult delResource(1: profile_struct.Language language);
}

service ProjectExpServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.ProjectExp> projectExps);
    profile_struct.ProviderResult putResources(1: list<profile_struct.ProjectExp> projectExps);
    profile_struct.ProviderResult delResources(1: list<profile_struct.ProjectExp> projectExps);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.ProjectExp projectExp);
    profile_struct.ProviderResult putResource(1: profile_struct.ProjectExp projectExp);
    profile_struct.ProviderResult delResource(1: profile_struct.ProjectExp projectExp);
}

service RewardServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.Reward> rewards);
    profile_struct.ProviderResult putResources(1: list<profile_struct.Reward> rewards);
    profile_struct.ProviderResult delResources(1: list<profile_struct.Reward> rewards);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.Reward reward);
    profile_struct.ProviderResult putResource(1: profile_struct.Reward reward);
    profile_struct.ProviderResult delResource(1: profile_struct.Reward reward);
}

service SchoolJobServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.SchoolJob> schoolJobs);
    profile_struct.ProviderResult putResources(1: list<profile_struct.SchoolJob> schoolJobs);
    profile_struct.ProviderResult delResources(1: list<profile_struct.SchoolJob> schoolJobs);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.SchoolJob schoolJob);
    profile_struct.ProviderResult putResource(1: profile_struct.SchoolJob schoolJob);
    profile_struct.ProviderResult delResource(1: profile_struct.SchoolJob schoolJob);
}

service SkillServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.Skill> skills);
    profile_struct.ProviderResult putResources(1: list<profile_struct.Skill> skills);
    profile_struct.ProviderResult delResources(1: list<profile_struct.Skill> skills);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.Skill skill);
    profile_struct.ProviderResult putResource(1: profile_struct.Skill skill);
    profile_struct.ProviderResult delResource(1: profile_struct.Skill skill);
}

service TrainingServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.Training> trainings);
    profile_struct.ProviderResult putResources(1: list<profile_struct.Training> trainings);
    profile_struct.ProviderResult delResources(1: list<profile_struct.Training> trainings);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.Training training);
    profile_struct.ProviderResult putResource(1: profile_struct.Training training);
    profile_struct.ProviderResult delResource(1: profile_struct.Training training);
}

service WorkExpServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.WorkExp> workExps);
    profile_struct.ProviderResult putResources(1: list<profile_struct.WorkExp> workExps);
    profile_struct.ProviderResult delResources(1: list<profile_struct.WorkExp> workExps);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.WorkExp workExp);
    profile_struct.ProviderResult putResource(1: profile_struct.WorkExp workExp);
    profile_struct.ProviderResult delResource(1: profile_struct.WorkExp workExp);
}

service WorksServices {
    profile_struct.ProviderResult getResources(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult getPagination(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResources(1: list<profile_struct.Works> works);
    profile_struct.ProviderResult putResources(1: list<profile_struct.Works> works);
    profile_struct.ProviderResult delResources(1: list<profile_struct.Works> works);
    
    profile_struct.ProviderResult getResource(1:profile_struct.CommonQuery query);
    profile_struct.ProviderResult postResource(1: profile_struct.Works works);
    profile_struct.ProviderResult putResource(1: profile_struct.Works works);
    profile_struct.ProviderResult delResource(1: profile_struct.Works works);
}
