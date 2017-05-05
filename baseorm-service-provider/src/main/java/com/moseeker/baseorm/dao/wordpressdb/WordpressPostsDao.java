package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.WordpressPosts;
import com.moseeker.baseorm.db.wordpressdb.tables.WordpressTermRelationships;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressPostsRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressPostsDO;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class WordpressPostsDao extends JooqCrudImpl<WordpressPostsDO, WordpressPostsRecord> {

	private static Logger logger = LoggerFactory.getLogger(WordpressPostsDao.class);

	public WordpressPostsDao(TableImpl<WordpressPostsRecord> table, Class<WordpressPostsDO> wordpressPostsDOClass) {
		super(table, wordpressPostsDOClass);
	}

	public WordpressPostsRecord getReleaseVersionPost() {
		Record postRecord = create.select(WordpressPosts.WORDPRESS_POSTS.fields()).from(WordpressPosts.WORDPRESS_POSTS)
				.join(WordpressTermRelationships.WORDPRESS_TERM_RELATIONSHIPS)
				.on(WordpressPosts.WORDPRESS_POSTS.ID
						.equal(WordpressTermRelationships.WORDPRESS_TERM_RELATIONSHIPS.OBJECT_ID))
				.where(WordpressTermRelationships.WORDPRESS_TERM_RELATIONSHIPS.TERM_TAXONOMY_ID
						.equal((long)(Constant.WORDPRESS_NEWSLETTER_VALUE)))
				.and(WordpressPosts.WORDPRESS_POSTS.POST_STATUS.equal(Constant.WORDPRESS_POST_POSTSTATUS_PUBLISH))
				.orderBy(WordpressPosts.WORDPRESS_POSTS.ID.desc())
				.limit(1).fetchOne();
		if(postRecord != null) {
			WordpressPostsRecord record = new WordpressPostsRecord();
			record.setCommentCount(postRecord.get(WordpressPosts.WORDPRESS_POSTS.COMMENT_COUNT));
			record.setCommentStatus(postRecord.get(WordpressPosts.WORDPRESS_POSTS.COMMENT_STATUS));
			record.setGuid(postRecord.get(WordpressPosts.WORDPRESS_POSTS.GUID));
			record.setId(postRecord.get(WordpressPosts.WORDPRESS_POSTS.ID));
			record.setMenuOrder(postRecord.get(WordpressPosts.WORDPRESS_POSTS.MENU_ORDER));
			record.setPinged(postRecord.get(WordpressPosts.WORDPRESS_POSTS.PINGED));
			record.setPostAuthor(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_AUTHOR));
			record.setPostContent(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_CONTENT));
			record.setPostDate(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_DATE));
			record.setPostDateGmt(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_DATE_GMT));
			record.setPostExcerpt(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_EXCERPT));
			record.setPostMimeType(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_MIME_TYPE));
			record.setPostModified(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_MODIFIED));
			record.setPostModifiedGmt(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_MODIFIED_GMT));
			record.setPostName(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_NAME));
			record.setPostParent(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_PARENT));
			record.setPostPassword(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_NAME));
			record.setPostStatus(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_STATUS));
			record.setPostTitle(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_TITLE));
			record.setPostType(postRecord.get(WordpressPosts.WORDPRESS_POSTS.POST_TYPE));
			record.setToPing(postRecord.get(WordpressPosts.WORDPRESS_POSTS.TO_PING));
			return record;
		}
		// TODO Auto-generated method stub
		return null;
	}
}
