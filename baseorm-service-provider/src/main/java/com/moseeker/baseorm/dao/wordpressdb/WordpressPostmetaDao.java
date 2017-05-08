package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.WordpressPostmeta;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressPostmetaRecord;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressPostmetaDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WordpressPostmetaDao extends JooqCrudImpl<WordpressPostmetaDO, WordpressPostmetaRecord> {

	public WordpressPostmetaDao() {
		super(WordpressPostmeta.WORDPRESS_POSTMETA, WordpressPostmetaDO.class);
	}

	public WordpressPostmetaDao(TableImpl<WordpressPostmetaRecord> table, Class<WordpressPostmetaDO> wordpressPostmetaDOClass) {
		super(table, wordpressPostmetaDOClass);
	}

	public List<WordpressPostmetaRecord> getPostExt(long objectId, List<String> keys) {
		List<WordpressPostmetaRecord> records = null;
			records = create.selectFrom(WordpressPostmeta.WORDPRESS_POSTMETA).where(WordpressPostmeta.WORDPRESS_POSTMETA.POST_ID
					.equal((long)(objectId)).and(WordpressPostmeta.WORDPRESS_POSTMETA.META_KEY.in(keys)))
					.fetch();
		return records;
	}
}
