package com.moseeker.profile.dao;

import java.util.List;
import java.util.Set;

import org.jooq.Record2;
import org.jooq.Result;


import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.profiledb.tables.records.ProfileAttachmentRecord;
import com.moseeker.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.db.profiledb.tables.records.ProfileImportRecord;
import com.moseeker.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.db.profiledb.tables.records.ProfileSkillRecord;
import com.moseeker.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.profile.dao.entity.ProfileWorkexpEntity;
import com.moseeker.profile.dao.impl.IntentionRecord;



public interface ProfileDao extends BaseDao<ProfileProfileRecord> {

	ProfileProfileRecord getProfileByIdOrUserIdOrUUID(int userId, int profileId, String uuid);

	int saveProfile(ProfileProfileRecord profileRecord, ProfileBasicRecord basicRecord,
			List<ProfileAttachmentRecord> attachmentRecords, List<ProfileAwardsRecord> awardsRecords,
			List<ProfileCredentialsRecord> credentialsRecords, List<ProfileEducationRecord> educationRecords,
			ProfileImportRecord importRecords, List<IntentionRecord> intentionRecords,
			List<ProfileLanguageRecord> languages, ProfileOtherRecord otherRecord,
			List<ProfileProjectexpRecord> projectExps, List<ProfileSkillRecord> skillRecords,
			List<ProfileWorkexpEntity> workexpRecords, List<ProfileWorksRecord> worksRecords,
			UserUserRecord userRecord);

	int deleteProfile(int profileId);

	List<ProfileProfileRecord> getProfilesByIdOrUserIdOrUUID(int userId, int profileId, String uuid);

	int saveProfile(ProfileProfileRecord profileRecord, ProfileBasicRecord basicRecord,
			List<ProfileAttachmentRecord> attachmentRecords, List<ProfileAwardsRecord> awardsRecords,
			List<ProfileCredentialsRecord> credentialsRecords, List<ProfileEducationRecord> educationRecords,
			ProfileImportRecord importRecords, List<IntentionRecord> intentionRecords,
			List<ProfileLanguageRecord> languages, ProfileOtherRecord otherRecord,
			List<ProfileProjectexpRecord> projectExps, List<ProfileSkillRecord> skillRecords,
			List<ProfileWorkexpEntity> workexpRecords, List<ProfileWorksRecord> worksRecords, UserUserRecord userRecord,
			List<ProfileProfileRecord> oldProfile);

	Result<Record2<Integer, String>> findRealName(List<Integer> profileIds);

	String findRealName(int profile_id);

	void updateRealName(int profileId, String name);

	int updateUpdateTime(Set<Integer> profileIds);

}
