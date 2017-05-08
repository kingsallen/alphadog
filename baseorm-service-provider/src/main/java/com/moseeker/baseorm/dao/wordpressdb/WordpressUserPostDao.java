package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.WordpressUserPost;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressUserPostRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressUserPostDO;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class WordpressUserPostDao
		extends JooqCrudImpl<WordpressUserPostDO, WordpressUserPostRecord> {
	
	private static final String INSERT_SQL = "insert into wordpressdb.wordpress_user_post(user_id, object_id) select ?, ? from DUAL where not exists(select user_id from wordpressdb.wordpress_user_post where user_id = ?)";

	public WordpressUserPostDao() {
		super(WordpressUserPost.WORDPRESS_USER_POST, WordpressUserPostDO.class);
	}

	public WordpressUserPostDao(TableImpl<WordpressUserPostRecord> table, Class<WordpressUserPostDO> wordpressUserPostDOClass) {
		super(table, wordpressUserPostDOClass);
	}

	public int upsertUserPost(int userId, long postId) {
		int count = 0;
		count = create.execute(INSERT_SQL, userId, postId, userId);
		if(count == 0) {
			WordpressUserPostRecord userPost = new WordpressUserPostRecord();
			userPost.setUserId(userId);
			userPost.setObjectId((long)(postId));
			create.attach(userPost);
			count = userPost.update();
		}
		return count;
	}
}
