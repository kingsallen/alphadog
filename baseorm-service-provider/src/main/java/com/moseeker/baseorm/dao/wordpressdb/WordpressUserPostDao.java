package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.WordpressUserPost;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressUserPostRecord;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.Update;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressUserPostDO;
import org.apache.thrift.TException;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

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
			Update update=new Update.UpdateBuilder().set("object_id",postId).where("user_id",userId).buildUpdate();
			count=update(update);
		}
		return count;
	}


    public long getReadedPostId(int userId,long id ) throws TException {
        long postId = 0;
        Query.QueryBuilder qu = new Query.QueryBuilder();
        qu.where("user_id", String.valueOf(userId)).and("object_id",id);
        try {
            WordpressUserPostRecord record = getRecord(qu.buildQuery());
            if(record != null) {
                postId = record.getObjectId().longValue();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        } finally {
            //do nothing
        }
        return postId;
    }
}
