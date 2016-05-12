# file: profile_service.thrift

include "../struct/profile_struct.thrift"
include "../../common/struct/common_struct.thrift"
namespace java com.moseeker.thrift.gen.profile.service

/**
 * TODO:list what notation this dateTime represents. eg ISO-8601
 * or if its in the format like YYYY-mm-DD you mentioned.
 */
 
service WholeProfileServices {
    common_struct.Response getResource(1:i32 id);
}

service ProfileServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(2:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.Profile> profiles);
    common_struct.Response putResources(1: list<profile_struct.Profile> profiles);
    common_struct.Response delResources(1: list<profile_struct.Profile> profiles);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.Profile profile);
    common_struct.Response putResource(1: profile_struct.Profile profile);
    common_struct.Response delResource(1: profile_struct.Profile profile);
}

service AttachmentServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.Attachment> profiles);
    common_struct.Response putResources(1: list<profile_struct.Attachment> profiles);
    common_struct.Response delResources(1: list<profile_struct.Attachment> profiles);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.Attachment profile);
    common_struct.Response putResource(1: profile_struct.Attachment profile);
    common_struct.Response delResource(1: profile_struct.Attachment profile);
}

service BasicServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.Basic> basics);
    common_struct.Response putResources(1: list<profile_struct.Basic> basics);
    common_struct.Response delResources(1: list<profile_struct.Basic> basics);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.Basic basic);
    common_struct.Response putResource(1: profile_struct.Basic basic);
    common_struct.Response delResource(1: profile_struct.Basic basic);
}

service EducationServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.Education> educations);
    common_struct.Response putResources(1: list<profile_struct.Education> educations);
    common_struct.Response delResources(1: list<profile_struct.Education> educations);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.Education education);
    common_struct.Response putResource(1: profile_struct.Education education);
    common_struct.Response delResource(1: profile_struct.Education education);
}

service EducationExtServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.EducationExt> educationExts);
    common_struct.Response putResources(1: list<profile_struct.EducationExt> educationExts);
    common_struct.Response delResources(1: list<profile_struct.EducationExt> educationExts);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.EducationExt educationExt);
    common_struct.Response putResource(1: profile_struct.EducationExt educationExt);
    common_struct.Response delResource(1: profile_struct.EducationExt educationExt);
}

service ProfileExtServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.ProfileExt> profileExts);
    common_struct.Response putResources(1: list<profile_struct.ProfileExt> profileExts);
    common_struct.Response delResources(1: list<profile_struct.ProfileExt> profileExts);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.ProfileExt profileExt);
    common_struct.Response putResource(1: profile_struct.ProfileExt profileExt);
    common_struct.Response delResource(1: profile_struct.ProfileExt profileExt);
}

service ProfileImportServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.ProfileImport> profileImports);
    common_struct.Response putResources(1: list<profile_struct.ProfileImport> profileImports);
    common_struct.Response delResources(1: list<profile_struct.ProfileImport> profileImports);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.ProfileImport profileImport);
    common_struct.Response putResource(1: profile_struct.ProfileImport profileImport);
    common_struct.Response delResource(1: profile_struct.ProfileImport profileImport);
}

service IntentionServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.Intention> intentions);
    common_struct.Response putResources(1: list<profile_struct.Intention> intentions);
    common_struct.Response delResources(1: list<profile_struct.Intention> intentions);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.Intention intention);
    common_struct.Response putResource(1: profile_struct.Intention intention);
    common_struct.Response delResource(1: profile_struct.Intention intention);
}

service InternshipServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.Internship> internships);
    common_struct.Response putResources(1: list<profile_struct.Internship> internships);
    common_struct.Response delResources(1: list<profile_struct.Internship> internships);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.Internship internship);
    common_struct.Response putResource(1: profile_struct.Internship internship);
    common_struct.Response delResource(1: profile_struct.Internship internship);
}

service LanguageServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.Language> languages);
    common_struct.Response putResources(1: list<profile_struct.Language> languages);
    common_struct.Response delResources(1: list<profile_struct.Language> languages);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.Language language);
    common_struct.Response putResource(1: profile_struct.Language language);
    common_struct.Response delResource(1: profile_struct.Language language);
}

service ProjectExpServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.ProjectExp> projectExps);
    common_struct.Response putResources(1: list<profile_struct.ProjectExp> projectExps);
    common_struct.Response delResources(1: list<profile_struct.ProjectExp> projectExps);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.ProjectExp projectExp);
    common_struct.Response putResource(1: profile_struct.ProjectExp projectExp);
    common_struct.Response delResource(1: profile_struct.ProjectExp projectExp);
}

service RewardServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.Reward> rewards);
    common_struct.Response putResources(1: list<profile_struct.Reward> rewards);
    common_struct.Response delResources(1: list<profile_struct.Reward> rewards);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.Reward reward);
    common_struct.Response putResource(1: profile_struct.Reward reward);
    common_struct.Response delResource(1: profile_struct.Reward reward);
}

service SchoolJobServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.SchoolJob> schoolJobs);
    common_struct.Response putResources(1: list<profile_struct.SchoolJob> schoolJobs);
    common_struct.Response delResources(1: list<profile_struct.SchoolJob> schoolJobs);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.SchoolJob schoolJob);
    common_struct.Response putResource(1: profile_struct.SchoolJob schoolJob);
    common_struct.Response delResource(1: profile_struct.SchoolJob schoolJob);
}

service SkillServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.Skill> skills);
    common_struct.Response putResources(1: list<profile_struct.Skill> skills);
    common_struct.Response delResources(1: list<profile_struct.Skill> skills);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.Skill skill);
    common_struct.Response putResource(1: profile_struct.Skill skill);
    common_struct.Response delResource(1: profile_struct.Skill skill);
}

service TrainingServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.Training> trainings);
    common_struct.Response putResources(1: list<profile_struct.Training> trainings);
    common_struct.Response delResources(1: list<profile_struct.Training> trainings);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.Training training);
    common_struct.Response putResource(1: profile_struct.Training training);
    common_struct.Response delResource(1: profile_struct.Training training);
}

service WorkExpServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.WorkExp> workExps);
    common_struct.Response putResources(1: list<profile_struct.WorkExp> workExps);
    common_struct.Response delResources(1: list<profile_struct.WorkExp> workExps);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.WorkExp workExp);
    common_struct.Response putResource(1: profile_struct.WorkExp workExp);
    common_struct.Response delResource(1: profile_struct.WorkExp workExp);
}

service WorksServices {
    common_struct.Response getResources(1:common_struct.CommonQuery query);
    common_struct.Response getPagination(1:common_struct.CommonQuery query);
    common_struct.Response postResources(1: list<profile_struct.Works> works);
    common_struct.Response putResources(1: list<profile_struct.Works> works);
    common_struct.Response delResources(1: list<profile_struct.Works> works);
    
    common_struct.Response getResource(1:common_struct.CommonQuery query);
    common_struct.Response postResource(1: profile_struct.Works works);
    common_struct.Response putResource(1: profile_struct.Works works);
    common_struct.Response delResource(1: profile_struct.Works works);
}
