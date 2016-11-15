/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.wordpressdb.tables;


import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;
import org.jooq.types.ULong;

import com.moseeker.baseorm.db.wordpressdb.Keys;
import com.moseeker.baseorm.db.wordpressdb.Wordpressdb;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressCommentmetaRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WordpressCommentmeta extends TableImpl<WordpressCommentmetaRecord> {

	private static final long serialVersionUID = -826173927;

	/**
	 * The reference instance of <code>wordpressdb.wordpress_commentmeta</code>
	 */
	public static final WordpressCommentmeta WORDPRESS_COMMENTMETA = new WordpressCommentmeta();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<WordpressCommentmetaRecord> getRecordType() {
		return WordpressCommentmetaRecord.class;
	}

	/**
	 * The column <code>wordpressdb.wordpress_commentmeta.meta_id</code>.
	 */
	public final TableField<WordpressCommentmetaRecord, ULong> META_ID = createField("meta_id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_commentmeta.comment_id</code>.
	 */
	public final TableField<WordpressCommentmetaRecord, ULong> COMMENT_ID = createField("comment_id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_commentmeta.meta_key</code>.
	 */
	public final TableField<WordpressCommentmetaRecord, String> META_KEY = createField("meta_key", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_commentmeta.meta_value</code>.
	 */
	public final TableField<WordpressCommentmetaRecord, String> META_VALUE = createField("meta_value", org.jooq.impl.SQLDataType.CLOB, this, "");

	/**
	 * Create a <code>wordpressdb.wordpress_commentmeta</code> table reference
	 */
	public WordpressCommentmeta() {
		this("wordpress_commentmeta", null);
	}

	/**
	 * Create an aliased <code>wordpressdb.wordpress_commentmeta</code> table reference
	 */
	public WordpressCommentmeta(String alias) {
		this(alias, WORDPRESS_COMMENTMETA);
	}

	private WordpressCommentmeta(String alias, Table<WordpressCommentmetaRecord> aliased) {
		this(alias, aliased, null);
	}

	private WordpressCommentmeta(String alias, Table<WordpressCommentmetaRecord> aliased, Field<?>[] parameters) {
		super(alias, Wordpressdb.WORDPRESSDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<WordpressCommentmetaRecord, ULong> getIdentity() {
		return Keys.IDENTITY_WORDPRESS_COMMENTMETA;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<WordpressCommentmetaRecord> getPrimaryKey() {
		return Keys.KEY_WORDPRESS_COMMENTMETA_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<WordpressCommentmetaRecord>> getKeys() {
		return Arrays.<UniqueKey<WordpressCommentmetaRecord>>asList(Keys.KEY_WORDPRESS_COMMENTMETA_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressCommentmeta as(String alias) {
		return new WordpressCommentmeta(alias, this);
	}

	/**
	 * Rename this table
	 */
	public WordpressCommentmeta rename(String name) {
		return new WordpressCommentmeta(name, null);
	}
}
