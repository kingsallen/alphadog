package com.moseeker.baseorm.Thriftservice;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.wordpress.WordpressPostmetaDao;
import com.moseeker.baseorm.dao.wordpress.WordpressPostsDao;
import com.moseeker.baseorm.dao.wordpress.WordpressTermRelationshipDao;
import com.moseeker.baseorm.dao.wordpress.WordpressUserPostDao;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressPostmetaRecord;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressPostsRecord;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermRelationshipsRecord;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressUserPostRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.WordpressDao.Iface;
import com.moseeker.thrift.gen.dao.struct.PostExt;
import com.moseeker.thrift.gen.dao.struct.WordpressPosts;
import com.moseeker.thrift.gen.dao.struct.WordpressTermRelationships;

@Service
public class WordpressDaoThriftService implements Iface{
	
	Logger logger = LoggerFactory.getLogger(WordpressDaoThriftService.class);
	
	@Autowired
	private WordpressPostsDao wordpressPostsDao;
	
	@Autowired
	private WordpressTermRelationshipDao wordpressTermRelationshipDao;
	
	@Autowired
	private WordpressPostmetaDao wordpressPostmetaDao;
	
	@Autowired
	private WordpressUserPostDao wordpressUserPostDao;
	
	/**
	 * 通用查询方法查询文章信息
	 */
	@Override
	public WordpressPosts getPost(CommonQuery query) throws TException {
		WordpressPosts posts = new WordpressPosts();
		try {
			WordpressPostsRecord record = wordpressPostsDao.getResource(query);
			if(record != null) {
				posts = new WordpressPosts();
				posts.setId(record.getId().longValue());
				posts.setPostAuthor(record.getPostAuthor().intValue());
				posts.setPostContent(record.getPostContent());
				posts.setPostDate(new DateTime(record.getPostDate().getTime()).toString("yyyy.MM.dd"));
				posts.setPostExcerpt(record.getPostExcerpt());
				posts.setPostModified(new DateTime(record.getPostModified().getTime()).toString("yyyy.MM.dd"));
				posts.setPostStatus(record.getPostStatus());
				posts.setPostTitle(record.getPostTitle());
				posts.setPostName(record.getPostName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		} finally {
			
		}
		return posts;
	}

	/**
	 * 查询文章类别关系表
	 */
	@Override
	public WordpressTermRelationships getRelationships(long objectId, long termTaxonomyId) throws TException {
		
		WordpressTermRelationships relationship = new WordpressTermRelationships();
		QueryUtil qu = new QueryUtil();
		if(objectId > 0) {
			qu.addEqualFilter("object_id", String.valueOf(objectId));
		}
		if(termTaxonomyId > 0) {
			qu.addEqualFilter("term_taxonomy_id", String.valueOf(termTaxonomyId));
		}
		try {
			WordpressTermRelationshipsRecord record = wordpressTermRelationshipDao.getResource(qu);
			if(record != null) {
				relationship.setObjectId(record.getObjectId().longValue());
				relationship.setTermTaxonomyId(record.getTermTaxonomyId().longValue());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		
		return relationship;
	}

	/**
	 * 根据文章类别查询该类别下最后一篇文章
	 */
	@Override
	public WordpressTermRelationships getLastRelationships(long termTaxonomyId) throws TException {
		WordpressTermRelationships relationshipData = null;
		try {
			relationshipData = null;
			WordpressTermRelationshipsRecord relationship = wordpressTermRelationshipDao.getLastRelationships(termTaxonomyId);
			if(relationship != null) {
				relationshipData = new WordpressTermRelationships();
				relationshipData.setObjectId(relationship.getObjectId().longValue());
				relationshipData.setTermTaxonomyId(relationship.getTermTaxonomyId().longValue());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			
		} finally {
			//do nothing
		}
		return relationshipData;
	}

	/**
	 * 查询文章的版本号和平台类别
	 */
	@Override
	public PostExt getPostExt(long objectId) throws TException {
		PostExt postExt = new PostExt();
		try {
			List<String> keys = new ArrayList<>();
			keys.add(Constant.WORDPRESS_POST_CUSTOMFIELD_VERSION);
			keys.add(Constant.WORDPRESS_POST_CUSTOMFIELD_PLATFORM);
			List<WordpressPostmetaRecord> records = wordpressPostmetaDao.getPostExt(objectId, keys);
			if(records != null) {
				postExt.setObjectId(objectId);
				records.forEach(record -> {
					if(record.getMetaKey() != null && record.getMetaKey().equals(Constant.WORDPRESS_POST_CUSTOMFIELD_VERSION)) {
						postExt.setVersion(record.getMetaValue());
					}
					if(record.getMetaKey() != null && record.getMetaKey().equals(Constant.WORDPRESS_POST_CUSTOMFIELD_PLATFORM)) {
						postExt.setPlatform(record.getMetaValue());
					}
				});
				return postExt;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return postExt;
	}
	
	/**
	 * 获取最新的处于发布状态的版本更新文章
	 * @return
	 * @throws TException
	 */
	@Override
	public WordpressPosts getReleaseVersionPost() throws TException {
		WordpressPosts posts = new WordpressPosts();
		try {
			WordpressPostsRecord record = wordpressPostsDao.getReleaseVersionPost();
			if(record != null) {
				posts = new WordpressPosts();
				posts.setId(record.getId().longValue());
				posts.setPostAuthor(record.getPostAuthor().intValue());
				posts.setPostContent(record.getPostContent());
				posts.setPostDate(new DateTime(record.getPostDate().getTime()).toString("yyyy.MM.dd"));
				posts.setPostExcerpt(record.getPostExcerpt());
				posts.setPostModified(new DateTime(record.getPostModified().getTime()).toString("yyyy.MM.dd"));
				posts.setPostStatus(record.getPostStatus());
				posts.setPostTitle(record.getPostTitle());
				posts.setPostName(record.getPostName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
		} finally {
			
		}
		return posts;
	}

	/**
	 * 查询用户访问的版本更新的记录
	 * @param userId
	 * @return
	 * @throws TException
	 */
	@Override
	public long getReadedPostId(int userId) throws TException {
		long postId = 0;
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("user_id", String.valueOf(userId));
		try {
			WordpressUserPostRecord record = wordpressUserPostDao.getResource(qu);
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
	
	/**
	 * 更新用户浏览新版本信息的内容
	 * @param userId
	 * @param postId
	 * @return
	 * @throws TException
	 */
	@Override
	public Response upsertUserPost(int userId, long postId) throws TException {
		int count = wordpressUserPostDao.upsertUserPost(userId, postId);
		if(count > 0) {
			return ResponseUtils.success(count);
		} else {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_POST_FAILED);
		}
	}

	public WordpressPostsDao getWordpressPostsDao() {
		return wordpressPostsDao;
	}

	public void setWordpressPostsDao(WordpressPostsDao wordpressPostsDao) {
		this.wordpressPostsDao = wordpressPostsDao;
	}

	public WordpressTermRelationshipDao getWordpressTermRelationshipDao() {
		return wordpressTermRelationshipDao;
	}

	public void setWordpressTermRelationshipDao(WordpressTermRelationshipDao wordpressTermRelationshipDao) {
		this.wordpressTermRelationshipDao = wordpressTermRelationshipDao;
	}

	public WordpressPostmetaDao getWordpressPostmetaDao() {
		return wordpressPostmetaDao;
	}

	public void setWordpressPostmetaDao(WordpressPostmetaDao wordpressPostmetaDao) {
		this.wordpressPostmetaDao = wordpressPostmetaDao;
	}
}
