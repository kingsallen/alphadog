package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressUserPostRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressUserPostDO;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class WordpressUserPostDao
		extends JooqCrudImpl<WordpressUserPostDO, WordpressUserPostRecord> {
	
	private static final String INSERT_SQL = "insert into wordpressdb.wordpress_user_post(user_id, object_id) select ?, ? from DUAL where not exists(select user_id from wordpressdb.wordpress_user_post where user_id = ?)";

	public WordpressUserPostDao(TableImpl<WordpressUserPostRecord> table, Class<WordpressUserPostDO> wordpressUserPostDOClass) {
		super(table, wordpressUserPostDOClass);
	}

	public int upsertUserPost(int userId, long postId) {
		int count = 0;
		try(Connection conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);) {
			PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL);
			pstmt.setInt(1, userId);
			pstmt.setLong(2, postId);;
			pstmt.setInt(3, userId);
			count = pstmt.executeUpdate();
			if(count == 0) {
				WordpressUserPostRecord userPost = new WordpressUserPostRecord();
				userPost.setUserId(userId);
				userPost.setObjectId((long)(postId));
				create.attach(userPost);
				count = userPost.update();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		
		return count;
	}
}
