package com.moseeker.baseorm.dao.wordpressdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.wordpressdb.tables.WordpressPostmeta;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressPostmetaRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.wordpressdb.WordpressPostmetaDO;
import org.jooq.DSLContext;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WordpressPostmetaDao extends JooqCrudImpl<WordpressPostmetaDO, WordpressPostmetaRecord> {

	public WordpressPostmetaDao(TableImpl<WordpressPostmetaRecord> table, Class<WordpressPostmetaDO> wordpressPostmetaDOClass) {
		super(table, wordpressPostmetaDOClass);
	}

	public List<WordpressPostmetaRecord> getPostExt(long objectId, List<String> keys) {
		List<WordpressPostmetaRecord> records = null;
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			records = create.selectFrom(WordpressPostmeta.WORDPRESS_POSTMETA).where(WordpressPostmeta.WORDPRESS_POSTMETA.POST_ID
					.equal((long)(objectId)).and(WordpressPostmeta.WORDPRESS_POSTMETA.META_KEY.in(keys)))
					.fetch();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// do nothing
		}

		return records;
	}

}
