package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileIntentionIndustry;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionIndustryRecord;
import com.moseeker.profile.dao.IntentionIndustryDao;

@Repository
public class IntentionIndustryDaoImpl extends
		BaseDaoImpl<ProfileIntentionIndustryRecord, ProfileIntentionIndustry> implements
		IntentionIndustryDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY;
	}

	@Override
	public List<ProfileIntentionIndustryRecord> getIntentionIndustries(List<Integer> intentionIds) {
		List<ProfileIntentionIndustryRecord> records = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			SelectWhereStep<ProfileIntentionIndustryRecord> select = create.selectFrom(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY);
			SelectConditionStep<ProfileIntentionIndustryRecord> selectCondition = null;
			if(intentionIds != null && intentionIds.size() > 0) {
				for(int i=0; i<intentionIds.size(); i++) {
					if(i == 0) {
						selectCondition = select.where(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY.PROFILE_INTENTION_ID.equal(UInteger.valueOf(intentionIds.get(i))));
					} else {
						selectCondition.or(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY.PROFILE_INTENTION_ID.equal(UInteger.valueOf(intentionIds.get(i))));
					}
				}
			}
			records = selectCondition.fetch();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
		
		return records;
	}
}
