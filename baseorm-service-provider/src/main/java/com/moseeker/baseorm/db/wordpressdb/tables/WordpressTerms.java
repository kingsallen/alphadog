/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.wordpressdb.tables;


import com.moseeker.baseorm.db.wordpressdb.Keys;
import com.moseeker.baseorm.db.wordpressdb.Wordpressdb;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermsRecord;

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
public class WordpressTerms extends TableImpl<WordpressTermsRecord> {

	private static final long serialVersionUID = 251270033;

	/**
	 * The reference instance of <code>wordpressdb.wordpress_terms</code>
	 */
	public static final WordpressTerms WORDPRESS_TERMS = new WordpressTerms();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<WordpressTermsRecord> getRecordType() {
		return WordpressTermsRecord.class;
	}

	/**
	 * The column <code>wordpressdb.wordpress_terms.term_id</code>.
	 */
	public final TableField<WordpressTermsRecord, ULong> TERM_ID = createField("term_id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_terms.name</code>.
	 */
	public final TableField<WordpressTermsRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_terms.slug</code>.
	 */
	public final TableField<WordpressTermsRecord, String> SLUG = createField("slug", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_terms.term_group</code>.
	 */
	public final TableField<WordpressTermsRecord, Long> TERM_GROUP = createField("term_group", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>wordpressdb.wordpress_terms</code> table reference
	 */
	public WordpressTerms() {
		this("wordpress_terms", null);
	}

	/**
	 * Create an aliased <code>wordpressdb.wordpress_terms</code> table reference
	 */
	public WordpressTerms(String alias) {
		this(alias, WORDPRESS_TERMS);
	}

	private WordpressTerms(String alias, Table<WordpressTermsRecord> aliased) {
		this(alias, aliased, null);
	}

	private WordpressTerms(String alias, Table<WordpressTermsRecord> aliased, Field<?>[] parameters) {
		super(alias, Wordpressdb.WORDPRESSDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<WordpressTermsRecord, ULong> getIdentity() {
		return Keys.IDENTITY_WORDPRESS_TERMS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<WordpressTermsRecord> getPrimaryKey() {
		return Keys.KEY_WORDPRESS_TERMS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<WordpressTermsRecord>> getKeys() {
		return Arrays.<UniqueKey<WordpressTermsRecord>>asList(Keys.KEY_WORDPRESS_TERMS_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressTerms as(String alias) {
		return new WordpressTerms(alias, this);
	}

	/**
	 * Rename this table
	 */
	public WordpressTerms rename(String name) {
		return new WordpressTerms(name, null);
	}
}