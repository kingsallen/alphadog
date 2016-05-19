package com.moseeker.useraccounts.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseJooqDaoImpl;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.useraccounts.dao.UserDao;

@Repository
public class UserDaoImpl extends BaseJooqDaoImpl<UserUserRecord, UserUser> implements UserDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserUser.USER_USER;
	}

	@Override
	public void combineAccount(List<String> tables, String column, int orig, int dest) throws Exception {
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			for(String table : tables) {
				PreparedStatement pstmt = conn.prepareStatement(COMBINE_ACCOUNT.replace("{table}", table).replace("{column}", column));
				pstmt.setInt(0, orig);
				pstmt.setInt(1, dest);
				pstmt.executeUpdate();
				pstmt.close();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if(conn != null && !conn.isClosed()) {
				conn.isClosed();
			}
		}
		
	}
	
	private static final String COMBINE_ACCOUNT = "update {table} set {column} = ? where {column} = ?";
}
