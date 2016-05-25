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
import com.moseeker.db.profiledb.tables.ProfileIntentionPosition;
import com.moseeker.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.profile.dao.IntentionPositionDao;

@Repository
public class IntentionPositionDaoImpl extends
		BaseDaoImpl<ProfileIntentionPositionRecord, ProfileIntentionPosition> implements
		IntentionPositionDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = ProfileIntentionPosition.PROFILE_INTENTION_POSITION;
	}

	@Override
	public List<ProfileIntentionPositionRecord> getIntentionPositions(List<Integer> cityCodes) {
		
		List<ProfileIntentionPositionRecord> records = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			SelectWhereStep<ProfileIntentionPositionRecord> select = create.selectFrom(ProfileIntentionPosition.PROFILE_INTENTION_POSITION);
			SelectConditionStep<ProfileIntentionPositionRecord> selectCondition = null;
			if(cityCodes != null && cityCodes.size() > 0) {
				for(int i=0; i<cityCodes.size(); i++) {
					if(i == 0) {
						selectCondition = select.where(ProfileIntentionPosition.PROFILE_INTENTION_POSITION.PROFILE_INTENTION_ID.equal(UInteger.valueOf(cityCodes.get(i))));
					} else {
						selectCondition.or(ProfileIntentionPosition.PROFILE_INTENTION_POSITION.PROFILE_INTENTION_ID.equal(UInteger.valueOf(cityCodes.get(i))));
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
