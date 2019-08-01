package com.moseeker.baseorm.dao.profiledb.entity;

import com.moseeker.baseorm.db.profiledb.tables.records.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @Author jack
 * @Date 2019/7/4 11:22 AM
 * @Version 1.0
 */
public class ProfileSaveResult {

    private ProfileProfileRecord profileRecord;
    private ProfileBasicRecord basicRecord;
    private java.util.List<ProfileAttachmentRecord> attachmentRecords;
    private List<ProfileAwardsRecord> awardsRecords;
    private List<ProfileCredentialsRecord> credentialsRecords;
    private List<ProfileEducationRecord> educationRecords;
    private ProfileImportRecord importRecord;
    private List<ProfileIntentionRecord> intentionRecords;
    private List<ProfileLanguageRecord> languages;
    private ProfileOtherRecord otherRecord;
    private List<ProfileProjectexpRecord> projectExps;
    private List<ProfileSkillRecord> skillRecords;
    private List<ProfileWorkexpEntity> workexpRecords;
    private List<ProfileWorksRecord> worksRecords;

    public ProfileProfileRecord getProfileRecord() {
        return profileRecord;
    }

    public void setProfileRecord(ProfileProfileRecord profileRecord) {
        this.profileRecord = profileRecord;
    }

    public ProfileBasicRecord getBasicRecord() {
        return basicRecord;
    }

    public void setBasicRecord(ProfileBasicRecord basicRecord) {
        this.basicRecord = basicRecord;
    }

    public List<ProfileAttachmentRecord> getAttachmentRecords() {
        return attachmentRecords;
    }

    public void setAttachmentRecords(List<ProfileAttachmentRecord> attachmentRecords) {
        this.attachmentRecords = attachmentRecords;
    }

    public void setAttachmentRecord(ProfileAttachmentRecord attachmentRecord) {
        if (attachmentRecord != null) {
            if (this.attachmentRecords == null) {
                setAttachmentRecords(new ArrayList<ProfileAttachmentRecord>(){{add(attachmentRecord);}});
            } else {
                this.attachmentRecords.add(attachmentRecord);
            }
        }
    }

    public List<ProfileAwardsRecord> getAwardsRecords() {
        return awardsRecords;
    }

    public void setAwardsRecords(List<ProfileAwardsRecord> awardsRecords) {
        this.awardsRecords = awardsRecords;
    }

    public void setAwardsRecords(ProfileAwardsRecord awardsRecord) {
        if (awardsRecord != null) {
            if (this.awardsRecords == null) {
                setAwardsRecords(new ArrayList<ProfileAwardsRecord>(){{add(awardsRecord);}});
            } else {
                this.awardsRecords.add(awardsRecord);
            }
        }
    }

    public List<ProfileCredentialsRecord> getCredentialsRecords() {
        return credentialsRecords;
    }

    public void setCredentialsRecords(List<ProfileCredentialsRecord> credentialsRecords) {
        this.credentialsRecords = credentialsRecords;
    }

    public void setCredentialsRecord(ProfileCredentialsRecord credentialsRecord) {
        if (credentialsRecord != null) {
            if (this.credentialsRecords == null) {
                setCredentialsRecords(new ArrayList<ProfileCredentialsRecord>(){{add(credentialsRecord);}});
            } else {
                this.credentialsRecords.add(credentialsRecord);
            }
        }
    }

    public List<ProfileEducationRecord> getEducationRecords() {
        return educationRecords;
    }

    public void setEducationRecords(List<ProfileEducationRecord> educationRecords) {
        this.educationRecords = educationRecords;
    }

    public void setEducationRecord(ProfileEducationRecord educationRecord) {
        if (educationRecord != null) {
            if (this.educationRecords == null) {
                setEducationRecords(new ArrayList<ProfileEducationRecord>(){{add(educationRecord);}});
            } else {
                this.educationRecords.add(educationRecord);
            }
        }
    }

    public ProfileImportRecord getImportRecord() {
        return importRecord;
    }

    public void setImportRecord(ProfileImportRecord importRecord) {
        this.importRecord = importRecord;
    }

    public List<ProfileIntentionRecord> getIntentionRecords() {
        return intentionRecords;
    }

    public void setIntentionRecords(List<ProfileIntentionRecord> intentionRecords) {
        this.intentionRecords = intentionRecords;
    }

    public void setIntentionRecord(ProfileIntentionRecord intentionRecord) {
        if (intentionRecord != null) {
            if (this.intentionRecords == null) {
                setIntentionRecords(new ArrayList<ProfileIntentionRecord>(){{add(intentionRecord);}});
            } else {
                this.intentionRecords.add(intentionRecord);
            }
        }
    }

    public List<ProfileLanguageRecord> getLanguages() {
        return languages;
    }

    public void setLanguages(List<ProfileLanguageRecord> languages) {
        this.languages = languages;
    }

    public void setLanguages(ProfileLanguageRecord language) {
        if (language != null) {
            if (this.languages == null) {
                setLanguages(new ArrayList<ProfileLanguageRecord>(){{add(language);}});
            } else {
                this.languages.add(language);
            }
        }
    }

    public ProfileOtherRecord getOtherRecord() {
        return otherRecord;
    }

    public void setOtherRecord(ProfileOtherRecord otherRecord) {
        this.otherRecord = otherRecord;
    }

    public List<ProfileProjectexpRecord> getProjectExps() {
        return projectExps;
    }

    public void setProjectExps(List<ProfileProjectexpRecord> projectExps) {
        this.projectExps = projectExps;
    }

    public void setProjectExp(ProfileProjectexpRecord projectExp) {
        if (projectExp != null) {
            if (this.projectExps == null) {
                setProjectExps(new ArrayList<ProfileProjectexpRecord>(){{add(projectExp);}});
            } else {
                this.projectExps.add(projectExp);
            }
        }
    }

    public List<ProfileSkillRecord> getSkillRecords() {
        return skillRecords;
    }

    public void setSkillRecords(List<ProfileSkillRecord> skillRecords) {
        this.skillRecords = skillRecords;
    }

    public void setSkillRecord(ProfileSkillRecord skillRecord) {
        if (skillRecord != null) {
            if (this.skillRecords == null) {
                setSkillRecords(new ArrayList<ProfileSkillRecord>(){{add(skillRecord);}});
            } else {
                this.skillRecords.add(skillRecord);
            }
        }
    }

    public List<ProfileWorkexpEntity> getWorkexpRecords() {
        return workexpRecords;
    }

    public void setWorkexpRecords(List<ProfileWorkexpEntity> workexpRecords) {
        this.workexpRecords = workexpRecords;
    }

    public void setWorkexpRecord(ProfileWorkexpEntity workexpRecord) {
        if (workexpRecord != null) {
            if (this.workexpRecords == null) {
                setWorkexpRecords(new ArrayList<ProfileWorkexpEntity>(){{add(workexpRecord);}});
            } else {
                this.workexpRecords.add(workexpRecord);
            }
        }
    }

    public List<ProfileWorksRecord> getWorksRecords() {
        return worksRecords;
    }

    public void setWorksRecords(List<ProfileWorksRecord> worksRecords) {
        this.worksRecords = worksRecords;
    }

    public void setWorksRecord(ProfileWorksRecord worksRecord) {
        if (worksRecord != null) {
            if (this.worksRecords == null) {
                setWorksRecords(new ArrayList<ProfileWorksRecord>(){{add(worksRecord);}});
            } else {
                this.worksRecords.add(worksRecord);
            }
        }
    }
}
