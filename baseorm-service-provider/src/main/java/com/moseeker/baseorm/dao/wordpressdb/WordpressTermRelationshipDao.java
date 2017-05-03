package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.WordpressTermRelationships;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermRelationshipsRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressTermRelationshipsDO;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

@Service
public class WordpressTermRelationshipDao
		extends JooqCrudImpl<WordpressTermRelationshipsDO, WordpressTermRelationshipsRecord> {

	public WordpressTermRelationshipDao(TableImpl<WordpressTermRelationshipsRecord> table, Class<WordpressTermRelationshipsDO> wordpressTermRelationshipsDOClass) {
		super(table, wordpressTermRelationshipsDOClass);
	}

	public WordpressTermRelationshipsRecord getLastRelationships(long termTaxonomyId) {
		WordpressTermRelationshipsRecord record = null;
		try (Connection conn = DBConnHelper.DBConn.getConn();) {
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			record = create.selectFrom(WordpressTermRelationships.WORDPRESS_TERM_RELATIONSHIPS)
					.where(WordpressTermRelationships.WORDPRESS_TERM_RELATIONSHIPS.TERM_TAXONOMY_ID
							.equal((long)(termTaxonomyId))).orderBy(WordpressTermRelationships.WORDPRESS_TERM_RELATIONSHIPS.OBJECT_ID.desc()).limit(1).fetchOne();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return record;
	}

}
