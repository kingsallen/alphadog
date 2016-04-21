/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.profileDB;


import com.moseeker.db.profileDB.tables.Profile;
import com.moseeker.db.profileDB.tables.ProfileAttachment;
import com.moseeker.db.profileDB.tables.ProfileBasic;
import com.moseeker.db.profileDB.tables.ProfileEducation;
import com.moseeker.db.profileDB.tables.ProfileEducationExt;
import com.moseeker.db.profileDB.tables.ProfileExt;
import com.moseeker.db.profileDB.tables.ProfileImport;
import com.moseeker.db.profileDB.tables.ProfileIntention;
import com.moseeker.db.profileDB.tables.ProfileInternship;
import com.moseeker.db.profileDB.tables.ProfileLanguage;
import com.moseeker.db.profileDB.tables.ProfileProjectexp;
import com.moseeker.db.profileDB.tables.ProfileReward;
import com.moseeker.db.profileDB.tables.ProfileSchooljob;
import com.moseeker.db.profileDB.tables.ProfileSkill;
import com.moseeker.db.profileDB.tables.ProfileTraining;
import com.moseeker.db.profileDB.tables.ProfileWorkexp;
import com.moseeker.db.profileDB.tables.ProfileWorks;
import com.moseeker.db.profileDB.tables.records.ProfileAttachmentRecord;
import com.moseeker.db.profileDB.tables.records.ProfileBasicRecord;
import com.moseeker.db.profileDB.tables.records.ProfileEducationExtRecord;
import com.moseeker.db.profileDB.tables.records.ProfileEducationRecord;
import com.moseeker.db.profileDB.tables.records.ProfileExtRecord;
import com.moseeker.db.profileDB.tables.records.ProfileImportRecord;
import com.moseeker.db.profileDB.tables.records.ProfileIntentionRecord;
import com.moseeker.db.profileDB.tables.records.ProfileInternshipRecord;
import com.moseeker.db.profileDB.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profileDB.tables.records.ProfileProjectexpRecord;
import com.moseeker.db.profileDB.tables.records.ProfileRecord;
import com.moseeker.db.profileDB.tables.records.ProfileRewardRecord;
import com.moseeker.db.profileDB.tables.records.ProfileSchooljobRecord;
import com.moseeker.db.profileDB.tables.records.ProfileSkillRecord;
import com.moseeker.db.profileDB.tables.records.ProfileTrainingRecord;
import com.moseeker.db.profileDB.tables.records.ProfileWorkexpRecord;
import com.moseeker.db.profileDB.tables.records.ProfileWorksRecord;

import javax.annotation.Generated;

import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;
import org.jooq.types.UInteger;


/**
 * A class modelling foreign key relationships between tables of the <code>profileDB</code> 
 * schema
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final Identity<ProfileRecord, UInteger> IDENTITY_PROFILE = Identities0.IDENTITY_PROFILE;
	public static final Identity<ProfileAttachmentRecord, UInteger> IDENTITY_PROFILE_ATTACHMENT = Identities0.IDENTITY_PROFILE_ATTACHMENT;
	public static final Identity<ProfileEducationRecord, UInteger> IDENTITY_PROFILE_EDUCATION = Identities0.IDENTITY_PROFILE_EDUCATION;
	public static final Identity<ProfileIntentionRecord, UInteger> IDENTITY_PROFILE_INTENTION = Identities0.IDENTITY_PROFILE_INTENTION;
	public static final Identity<ProfileInternshipRecord, UInteger> IDENTITY_PROFILE_INTERNSHIP = Identities0.IDENTITY_PROFILE_INTERNSHIP;
	public static final Identity<ProfileLanguageRecord, UInteger> IDENTITY_PROFILE_LANGUAGE = Identities0.IDENTITY_PROFILE_LANGUAGE;
	public static final Identity<ProfileProjectexpRecord, UInteger> IDENTITY_PROFILE_PROJECTEXP = Identities0.IDENTITY_PROFILE_PROJECTEXP;
	public static final Identity<ProfileRewardRecord, UInteger> IDENTITY_PROFILE_REWARD = Identities0.IDENTITY_PROFILE_REWARD;
	public static final Identity<ProfileSchooljobRecord, UInteger> IDENTITY_PROFILE_SCHOOLJOB = Identities0.IDENTITY_PROFILE_SCHOOLJOB;
	public static final Identity<ProfileSkillRecord, UInteger> IDENTITY_PROFILE_SKILL = Identities0.IDENTITY_PROFILE_SKILL;
	public static final Identity<ProfileTrainingRecord, UInteger> IDENTITY_PROFILE_TRAINING = Identities0.IDENTITY_PROFILE_TRAINING;
	public static final Identity<ProfileWorkexpRecord, UInteger> IDENTITY_PROFILE_WORKEXP = Identities0.IDENTITY_PROFILE_WORKEXP;
	public static final Identity<ProfileWorksRecord, UInteger> IDENTITY_PROFILE_WORKS = Identities0.IDENTITY_PROFILE_WORKS;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final UniqueKey<ProfileRecord> KEY_PROFILE_PRIMARY = UniqueKeys0.KEY_PROFILE_PRIMARY;
	public static final UniqueKey<ProfileAttachmentRecord> KEY_PROFILE_ATTACHMENT_PRIMARY = UniqueKeys0.KEY_PROFILE_ATTACHMENT_PRIMARY;
	public static final UniqueKey<ProfileBasicRecord> KEY_PROFILE_BASIC_PRIMARY = UniqueKeys0.KEY_PROFILE_BASIC_PRIMARY;
	public static final UniqueKey<ProfileEducationRecord> KEY_PROFILE_EDUCATION_PRIMARY = UniqueKeys0.KEY_PROFILE_EDUCATION_PRIMARY;
	public static final UniqueKey<ProfileEducationExtRecord> KEY_PROFILE_EDUCATION_EXT_PRIMARY = UniqueKeys0.KEY_PROFILE_EDUCATION_EXT_PRIMARY;
	public static final UniqueKey<ProfileExtRecord> KEY_PROFILE_EXT_PRIMARY = UniqueKeys0.KEY_PROFILE_EXT_PRIMARY;
	public static final UniqueKey<ProfileImportRecord> KEY_PROFILE_IMPORT_PRIMARY = UniqueKeys0.KEY_PROFILE_IMPORT_PRIMARY;
	public static final UniqueKey<ProfileIntentionRecord> KEY_PROFILE_INTENTION_PRIMARY = UniqueKeys0.KEY_PROFILE_INTENTION_PRIMARY;
	public static final UniqueKey<ProfileInternshipRecord> KEY_PROFILE_INTERNSHIP_PRIMARY = UniqueKeys0.KEY_PROFILE_INTERNSHIP_PRIMARY;
	public static final UniqueKey<ProfileLanguageRecord> KEY_PROFILE_LANGUAGE_PRIMARY = UniqueKeys0.KEY_PROFILE_LANGUAGE_PRIMARY;
	public static final UniqueKey<ProfileProjectexpRecord> KEY_PROFILE_PROJECTEXP_PRIMARY = UniqueKeys0.KEY_PROFILE_PROJECTEXP_PRIMARY;
	public static final UniqueKey<ProfileRewardRecord> KEY_PROFILE_REWARD_PRIMARY = UniqueKeys0.KEY_PROFILE_REWARD_PRIMARY;
	public static final UniqueKey<ProfileSchooljobRecord> KEY_PROFILE_SCHOOLJOB_PRIMARY = UniqueKeys0.KEY_PROFILE_SCHOOLJOB_PRIMARY;
	public static final UniqueKey<ProfileSkillRecord> KEY_PROFILE_SKILL_PRIMARY = UniqueKeys0.KEY_PROFILE_SKILL_PRIMARY;
	public static final UniqueKey<ProfileTrainingRecord> KEY_PROFILE_TRAINING_PRIMARY = UniqueKeys0.KEY_PROFILE_TRAINING_PRIMARY;
	public static final UniqueKey<ProfileWorkexpRecord> KEY_PROFILE_WORKEXP_PRIMARY = UniqueKeys0.KEY_PROFILE_WORKEXP_PRIMARY;
	public static final UniqueKey<ProfileWorksRecord> KEY_PROFILE_WORKS_PRIMARY = UniqueKeys0.KEY_PROFILE_WORKS_PRIMARY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------


	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends AbstractKeys {
		public static Identity<ProfileRecord, UInteger> IDENTITY_PROFILE = createIdentity(Profile.PROFILE, Profile.PROFILE.ID);
		public static Identity<ProfileAttachmentRecord, UInteger> IDENTITY_PROFILE_ATTACHMENT = createIdentity(ProfileAttachment.PROFILE_ATTACHMENT, ProfileAttachment.PROFILE_ATTACHMENT.ID);
		public static Identity<ProfileEducationRecord, UInteger> IDENTITY_PROFILE_EDUCATION = createIdentity(ProfileEducation.PROFILE_EDUCATION, ProfileEducation.PROFILE_EDUCATION.ID);
		public static Identity<ProfileIntentionRecord, UInteger> IDENTITY_PROFILE_INTENTION = createIdentity(ProfileIntention.PROFILE_INTENTION, ProfileIntention.PROFILE_INTENTION.ID);
		public static Identity<ProfileInternshipRecord, UInteger> IDENTITY_PROFILE_INTERNSHIP = createIdentity(ProfileInternship.PROFILE_INTERNSHIP, ProfileInternship.PROFILE_INTERNSHIP.ID);
		public static Identity<ProfileLanguageRecord, UInteger> IDENTITY_PROFILE_LANGUAGE = createIdentity(ProfileLanguage.PROFILE_LANGUAGE, ProfileLanguage.PROFILE_LANGUAGE.ID);
		public static Identity<ProfileProjectexpRecord, UInteger> IDENTITY_PROFILE_PROJECTEXP = createIdentity(ProfileProjectexp.PROFILE_PROJECTEXP, ProfileProjectexp.PROFILE_PROJECTEXP.ID);
		public static Identity<ProfileRewardRecord, UInteger> IDENTITY_PROFILE_REWARD = createIdentity(ProfileReward.PROFILE_REWARD, ProfileReward.PROFILE_REWARD.ID);
		public static Identity<ProfileSchooljobRecord, UInteger> IDENTITY_PROFILE_SCHOOLJOB = createIdentity(ProfileSchooljob.PROFILE_SCHOOLJOB, ProfileSchooljob.PROFILE_SCHOOLJOB.ID);
		public static Identity<ProfileSkillRecord, UInteger> IDENTITY_PROFILE_SKILL = createIdentity(ProfileSkill.PROFILE_SKILL, ProfileSkill.PROFILE_SKILL.ID);
		public static Identity<ProfileTrainingRecord, UInteger> IDENTITY_PROFILE_TRAINING = createIdentity(ProfileTraining.PROFILE_TRAINING, ProfileTraining.PROFILE_TRAINING.ID);
		public static Identity<ProfileWorkexpRecord, UInteger> IDENTITY_PROFILE_WORKEXP = createIdentity(ProfileWorkexp.PROFILE_WORKEXP, ProfileWorkexp.PROFILE_WORKEXP.ID);
		public static Identity<ProfileWorksRecord, UInteger> IDENTITY_PROFILE_WORKS = createIdentity(ProfileWorks.PROFILE_WORKS, ProfileWorks.PROFILE_WORKS.ID);
	}

	private static class UniqueKeys0 extends AbstractKeys {
		public static final UniqueKey<ProfileRecord> KEY_PROFILE_PRIMARY = createUniqueKey(Profile.PROFILE, Profile.PROFILE.ID);
		public static final UniqueKey<ProfileAttachmentRecord> KEY_PROFILE_ATTACHMENT_PRIMARY = createUniqueKey(ProfileAttachment.PROFILE_ATTACHMENT, ProfileAttachment.PROFILE_ATTACHMENT.ID);
		public static final UniqueKey<ProfileBasicRecord> KEY_PROFILE_BASIC_PRIMARY = createUniqueKey(ProfileBasic.PROFILE_BASIC, ProfileBasic.PROFILE_BASIC.PROFILE_ID);
		public static final UniqueKey<ProfileEducationRecord> KEY_PROFILE_EDUCATION_PRIMARY = createUniqueKey(ProfileEducation.PROFILE_EDUCATION, ProfileEducation.PROFILE_EDUCATION.ID);
		public static final UniqueKey<ProfileEducationExtRecord> KEY_PROFILE_EDUCATION_EXT_PRIMARY = createUniqueKey(ProfileEducationExt.PROFILE_EDUCATION_EXT, ProfileEducationExt.PROFILE_EDUCATION_EXT.PROFILE_ID);
		public static final UniqueKey<ProfileExtRecord> KEY_PROFILE_EXT_PRIMARY = createUniqueKey(ProfileExt.PROFILE_EXT, ProfileExt.PROFILE_EXT.PROFILE_ID);
		public static final UniqueKey<ProfileImportRecord> KEY_PROFILE_IMPORT_PRIMARY = createUniqueKey(ProfileImport.PROFILE_IMPORT, ProfileImport.PROFILE_IMPORT.PROFILE_ID);
		public static final UniqueKey<ProfileIntentionRecord> KEY_PROFILE_INTENTION_PRIMARY = createUniqueKey(ProfileIntention.PROFILE_INTENTION, ProfileIntention.PROFILE_INTENTION.ID);
		public static final UniqueKey<ProfileInternshipRecord> KEY_PROFILE_INTERNSHIP_PRIMARY = createUniqueKey(ProfileInternship.PROFILE_INTERNSHIP, ProfileInternship.PROFILE_INTERNSHIP.ID);
		public static final UniqueKey<ProfileLanguageRecord> KEY_PROFILE_LANGUAGE_PRIMARY = createUniqueKey(ProfileLanguage.PROFILE_LANGUAGE, ProfileLanguage.PROFILE_LANGUAGE.ID);
		public static final UniqueKey<ProfileProjectexpRecord> KEY_PROFILE_PROJECTEXP_PRIMARY = createUniqueKey(ProfileProjectexp.PROFILE_PROJECTEXP, ProfileProjectexp.PROFILE_PROJECTEXP.ID);
		public static final UniqueKey<ProfileRewardRecord> KEY_PROFILE_REWARD_PRIMARY = createUniqueKey(ProfileReward.PROFILE_REWARD, ProfileReward.PROFILE_REWARD.ID);
		public static final UniqueKey<ProfileSchooljobRecord> KEY_PROFILE_SCHOOLJOB_PRIMARY = createUniqueKey(ProfileSchooljob.PROFILE_SCHOOLJOB, ProfileSchooljob.PROFILE_SCHOOLJOB.ID);
		public static final UniqueKey<ProfileSkillRecord> KEY_PROFILE_SKILL_PRIMARY = createUniqueKey(ProfileSkill.PROFILE_SKILL, ProfileSkill.PROFILE_SKILL.ID);
		public static final UniqueKey<ProfileTrainingRecord> KEY_PROFILE_TRAINING_PRIMARY = createUniqueKey(ProfileTraining.PROFILE_TRAINING, ProfileTraining.PROFILE_TRAINING.ID);
		public static final UniqueKey<ProfileWorkexpRecord> KEY_PROFILE_WORKEXP_PRIMARY = createUniqueKey(ProfileWorkexp.PROFILE_WORKEXP, ProfileWorkexp.PROFILE_WORKEXP.ID);
		public static final UniqueKey<ProfileWorksRecord> KEY_PROFILE_WORKS_PRIMARY = createUniqueKey(ProfileWorks.PROFILE_WORKS, ProfileWorks.PROFILE_WORKS.ID);
	}
}
