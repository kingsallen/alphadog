package com.moseeker.baseorm.dao.wordpress;

import java.sql.Connection;
import java.sql.SQLException;

import org.jooq.DSLContext;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.wordpressdb.tables.WordpressTermRelationships;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermRelationshipsRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;

@Service
public class WordpressTermRelationshipDao
		extends BaseDaoImpl<WordpressTermRelationshipsRecord, WordpressTermRelationships> {

	protected void initJOOQEntity() {
		this.tableLike = WordpressTermRelationships.WORDPRESS_TERM_RELATIONSHIPS;

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
