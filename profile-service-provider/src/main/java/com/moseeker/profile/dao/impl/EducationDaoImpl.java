package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.common.util.StringUtils;
import com.moseeker.db.dictdb.tables.DictCollege;
import com.moseeker.db.dictdb.tables.records.DictCollegeRecord;
import com.moseeker.db.profiledb.tables.ProfileCompleteness;
import com.moseeker.db.profiledb.tables.ProfileEducation;
import com.moseeker.db.profiledb.tables.ProfileProfile;
import com.moseeker.db.profiledb.tables.records.ProfileCompletenessRecord;
import com.moseeker.db.profiledb.tables.records.ProfileEducationRecord;
import com.moseeker.profile.dao.EducationDao;
import com.moseeker.profile.service.impl.serviceutils.CompletenessCalculator;

@Repository
public class EducationDaoImpl extends
		BaseDaoImpl<ProfileEducationRecord, ProfileEducation> implements
		 EducationDao {
	
	private CompletenessCalculator completenessCalculator = new CompletenessCalculator();

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileEducation.PROFILE_EDUCATION;
	}

	@Override
	public int updateProfileUpdateTime(HashSet<Integer> educationIds) {
		int status = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {

			Timestamp updateTime = new Timestamp(System.currentTimeMillis());
			status = create.update(ProfileProfile.PROFILE_PROFILE)
					.set(ProfileProfile.PROFILE_PROFILE.UPDATE_TIME, updateTime)
					.where(ProfileProfile.PROFILE_PROFILE.ID
							.in(create.select(ProfileEducation.PROFILE_EDUCATION.PROFILE_ID)
									.from(ProfileEducation.PROFILE_EDUCATION)
									.where(ProfileEducation.PROFILE_EDUCATION.ID.in(educationIds))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return status;
	}

	@Override
	public int delEducationsByProfileId(int profileId) {
		int count = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
			count = create.delete(ProfileEducation.PROFILE_EDUCATION)
					.where(ProfileEducation.PROFILE_EDUCATION.PROFILE_ID.equal(UInteger.valueOf(profileId)))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return count;
	}

	@Override
	public int saveEducations(List<ProfileEducationRecord> educationRecords) {
		int count = 0;
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn)) {
			if (educationRecords != null && educationRecords.size() > 0) {
				
//				ProfileCompletenessRecord completenessRecord = create.selectFrom(ProfileCompleteness.PROFILE_COMPLETENESS).where(ProfileCompleteness.PROFILE_COMPLETENESS.PROFILE_ID.equal(educationRecords.get(0).getProfileId())).fetchOne();
//				if(completenessRecord == null) {
//					completenessRecord = new ProfileCompletenessRecord();
//					completenessRecord.setProfileId(educationRecords.get(0).getProfileId());
//				}
				
				Timestamp now = new Timestamp(System.currentTimeMillis());
				Result<DictCollegeRecord> colleges = create.selectFrom(DictCollege.DICT_COLLEGE).fetch();
				educationRecords.forEach(educationRecord -> {
					educationRecord.setCreateTime(now);
					if (!StringUtils.isNullOrEmpty(educationRecord.getCollegeName())) {
						for (DictCollegeRecord collegeRecord : colleges) {
							if (educationRecord.getCollegeName().equals(collegeRecord.getName())) {
								educationRecord.setCollegeCode(collegeRecord.getCode().intValue());
								educationRecord.setCollegeLogo(collegeRecord.getLogo());
								break;
							}
						}
					}
					create.attach(educationRecord);
					educationRecord.insert();
				});
//				int educationCompleteness = completenessCalculator.calculateProfileEducations(educationRecords);
//				completenessRecord.setProfileEducation(educationCompleteness);
//				create.attach(completenessRecord);
//				completenessRecord.update();
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return count;
	}
}
