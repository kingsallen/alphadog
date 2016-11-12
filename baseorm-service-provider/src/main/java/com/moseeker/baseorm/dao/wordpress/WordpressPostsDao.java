package com.moseeker.baseorm.dao.wordpress;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.wordpressdb.tables.WordpressPosts;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressPostsRecord;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
@Service
public class WordpressPostsDao extends BaseDaoImpl<WordpressPostsRecord, WordpressPosts>{

	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike = WordpressPosts.WORDPRESS_POSTS;
		
	}
}
