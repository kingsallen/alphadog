package com.moseeker.baseorm.dao.wordpress;

import org.junit.Test;

import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressPostsRecord;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermRelationshipsRecord;
import com.moseeker.common.providerutils.QueryUtil;

public class WordpressDaoTest {

	@Test
	public void testMysql() {
		WordpressPostsDao dao = new WordpressPostsDao();
		
		WordpressTermRelationshipDao relationshipDao = new WordpressTermRelationshipDao();
		
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(12));
		try {
			WordpressPostsRecord record = dao.getResource(qu);
			
			WordpressTermRelationshipsRecord record1 = relationshipDao.getLastRelationships(2);
			System.out.println(record.intoMap());
			System.out.println(record1.intoMap());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
