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
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressPostmetaRecord;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressPostsRecord;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermRelationshipsRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.orm.service.WordpressDao.Iface;
import com.moseeker.thrift.gen.orm.struct.PostExt;
import com.moseeker.thrift.gen.orm.struct.WordpressPosts;
import com.moseeker.thrift.gen.orm.struct.WordpressTermRelationships;

@Service
public class WordpressDaoThriftService implements Iface{
	
	Logger logger = LoggerFactory.getLogger(WordpressDaoThriftService.class);
	
	public static int count = 0;

	@Autowired
	private WordpressPostsDao wordpressPostsDao;
	
	@Autowired
	private WordpressTermRelationshipDao wordpressTermRelationshipDao;
	
	@Autowired
	private WordpressPostmetaDao wordpressPostmetaDao;
	
	/**
	 * 通用查询方法查询文章信息
	 */
	@Override
	public WordpressPosts getPost(CommonQuery query) throws TException {
		count ++;
		WordpressPosts posts = null;
		try {
			WordpressPostsRecord record = wordpressPostsDao.getResource(query);
			if(record != null) {
				posts = new WordpressPosts();
				posts.setId(record.getId().intValue());
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
		System.out.println("method count:"+count);
		return posts;
	}

	/**
	 * 查询文章类别关系表
	 */
	@Override
	public WordpressTermRelationships getRelationships(long objectId, long termTaxonomyId) throws TException {
		
		WordpressTermRelationships relationship = null;
		
		return null;
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
			
		}
		return relationshipData;
	}

	/**
	 * 查询文章的版本号和平台类别
	 */
	@Override
	public PostExt getPostExt(long objectId) throws TException {
		count ++;
		try {
			List<String> keys = new ArrayList<>();
			keys.add(Constant.WORDPRESS_POST_CUSTOMFIELD_VERSION);
			keys.add(Constant.WORDPRESS_POST_CUSTOMFIELD_PLATFORM);
			List<WordpressPostmetaRecord> records = wordpressPostmetaDao.getPostExt(objectId, keys);
			if(records != null) {
				PostExt postExt = new PostExt();
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
		System.out.println("method count:"+count);
		return null;
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
