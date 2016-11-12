/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.wordpressdb.tables;


import com.moseeker.baseorm.db.wordpressdb.Keys;
import com.moseeker.baseorm.db.wordpressdb.Wordpressdb;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressTermTaxonomyRecord;

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
public class WordpressTermTaxonomy extends TableImpl<WordpressTermTaxonomyRecord> {

	private static final long serialVersionUID = -1726882925;

	/**
	 * The reference instance of <code>wordpressdb.wordpress_term_taxonomy</code>
	 */
	public static final WordpressTermTaxonomy WORDPRESS_TERM_TAXONOMY = new WordpressTermTaxonomy();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<WordpressTermTaxonomyRecord> getRecordType() {
		return WordpressTermTaxonomyRecord.class;
	}

	/**
	 * The column <code>wordpressdb.wordpress_term_taxonomy.term_taxonomy_id</code>.
	 */
	public final TableField<WordpressTermTaxonomyRecord, ULong> TERM_TAXONOMY_ID = createField("term_taxonomy_id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_term_taxonomy.term_id</code>.
	 */
	public final TableField<WordpressTermTaxonomyRecord, ULong> TERM_ID = createField("term_id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_term_taxonomy.taxonomy</code>.
	 */
	public final TableField<WordpressTermTaxonomyRecord, String> TAXONOMY = createField("taxonomy", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_term_taxonomy.description</code>.
	 */
	public final TableField<WordpressTermTaxonomyRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_term_taxonomy.parent</code>.
	 */
	public final TableField<WordpressTermTaxonomyRecord, ULong> PARENT = createField("parent", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_term_taxonomy.count</code>.
	 */
	public final TableField<WordpressTermTaxonomyRecord, Long> COUNT = createField("count", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>wordpressdb.wordpress_term_taxonomy</code> table reference
	 */
	public WordpressTermTaxonomy() {
		this("wordpress_term_taxonomy", null);
	}

	/**
	 * Create an aliased <code>wordpressdb.wordpress_term_taxonomy</code> table reference
	 */
	public WordpressTermTaxonomy(String alias) {
		this(alias, WORDPRESS_TERM_TAXONOMY);
	}

	private WordpressTermTaxonomy(String alias, Table<WordpressTermTaxonomyRecord> aliased) {
		this(alias, aliased, null);
	}

	private WordpressTermTaxonomy(String alias, Table<WordpressTermTaxonomyRecord> aliased, Field<?>[] parameters) {
		super(alias, Wordpressdb.WORDPRESSDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<WordpressTermTaxonomyRecord, ULong> getIdentity() {
		return Keys.IDENTITY_WORDPRESS_TERM_TAXONOMY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<WordpressTermTaxonomyRecord> getPrimaryKey() {
		return Keys.KEY_WORDPRESS_TERM_TAXONOMY_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<WordpressTermTaxonomyRecord>> getKeys() {
		return Arrays.<UniqueKey<WordpressTermTaxonomyRecord>>asList(Keys.KEY_WORDPRESS_TERM_TAXONOMY_PRIMARY, Keys.KEY_WORDPRESS_TERM_TAXONOMY_TERM_ID_TAXONOMY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressTermTaxonomy as(String alias) {
		return new WordpressTermTaxonomy(alias, this);
	}

	/**
	 * Rename this table
	 */
	public WordpressTermTaxonomy rename(String name) {
		return new WordpressTermTaxonomy(name, null);
	}
}
