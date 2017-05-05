package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.WordpressTermRelationships;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermRelationshipsRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressTermRelationshipsDO;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class WordpressTermRelationshipDao
		extends JooqCrudImpl<WordpressTermRelationshipsDO, WordpressTermRelationshipsRecord> {

	public WordpressTermRelationshipDao(TableImpl<WordpressTermRelationshipsRecord> table, Class<WordpressTermRelationshipsDO> wordpressTermRelationshipsDOClass) {
		super(table, wordpressTermRelationshipsDOClass);
	}

	public WordpressTermRelationshipsRecord getLastRelationships(long termTaxonomyId) {
		WordpressTermRelationshipsRecord record = null;
		record = create.selectFrom(WordpressTermRelationships.WORDPRESS_TERM_RELATIONSHIPS)
				.where(WordpressTermRelationships.WORDPRESS_TERM_RELATIONSHIPS.TERM_TAXONOMY_ID
						.equal((long)(termTaxonomyId)))
				.orderBy(WordpressTermRelationships.WORDPRESS_TERM_RELATIONSHIPS.OBJECT_ID.desc())
				.limit(1).fetchOne();
		return record;
	}

}
