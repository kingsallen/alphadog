package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;

import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.profiledb.tables.ProfileIntentionCity;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.profile.dao.IntentionCityDao;

@Repository
public class IntentionCityDaoImpl extends
		BaseDaoImpl<ProfileIntentionCityRecord, ProfileIntentionCity> implements
		IntentionCityDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileIntentionCity.PROFILE_INTENTION_CITY;
	}

	@Override
	public List<ProfileIntentionCityRecord> getIntentionCities(List<Integer> intentionIds) {
		List<ProfileIntentionCityRecord> records = new ArrayList<>();
		Connection conn = null;
		try {
			if(intentionIds != null && intentionIds.size() > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				SelectWhereStep<ProfileIntentionCityRecord> select = create.selectFrom(ProfileIntentionCity.PROFILE_INTENTION_CITY);
				SelectConditionStep<ProfileIntentionCityRecord> selectCondition = null;
				for(int i=0; i<intentionIds.size(); i++) {
					if(i == 0) {
						selectCondition = select.where(ProfileIntentionCity.PROFILE_INTENTION_CITY.PROFILE_INTENTION_ID.equal((int)(intentionIds.get(i))));
					} else {
						selectCondition.or(ProfileIntentionCity.PROFILE_INTENTION_CITY.PROFILE_INTENTION_ID.equal((int)(intentionIds.get(i))));
					}
				}
				records = selectCondition.fetch();
			}
		} catch (Exception e) {
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
