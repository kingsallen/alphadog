package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;

import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.profile.dao.UserDao;

@Repository
public class UserDaoImpl extends BaseDaoImpl<UserUserRecord, UserUser> implements UserDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserUser.USER_USER;
	}

	@Override
	public List<UserUserRecord> getUserByIds(List<Integer> cityCodes) {

		List<UserUserRecord> records = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			SelectWhereStep<UserUserRecord> select = create.selectFrom(UserUser.USER_USER);
			SelectConditionStep<UserUserRecord> selectCondition = null;
			if (cityCodes != null && cityCodes.size() > 0) {
				for (int i = 0; i < cityCodes.size(); i++) {
					if (i == 0) {
						selectCondition = select.where(UserUser.USER_USER.ID.equal((int)(cityCodes.get(i))));
					} else {
						selectCondition.or(UserUser.USER_USER.ID.equal((int)(cityCodes.get(i))));
					}
				}
			}
			records = selectCondition.fetch();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				// do nothing
			}
		}

		return records;
	}

	@Override
	public UserUserRecord getUserById(int id) {
		UserUserRecord record = null;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			Result<UserUserRecord> result = create.selectFrom(UserUser.USER_USER)
					.where(UserUser.USER_USER.ID.equal((int)(id)))
					.and(UserUser.USER_USER.IS_DISABLE.equal((byte) 0)).fetch();
			if (result != null && result.size() > 0) {
				record = result.get(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				// do nothing
			}
		}

		return record;
	}
}
