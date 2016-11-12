/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.wordpressdb.tables;


import com.moseeker.baseorm.db.wordpressdb.Keys;
import com.moseeker.baseorm.db.wordpressdb.Wordpressdb;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressLinksRecord;

import java.sql.Timestamp;
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
public class WordpressLinks extends TableImpl<WordpressLinksRecord> {

	private static final long serialVersionUID = -813625541;

	/**
	 * The reference instance of <code>wordpressdb.wordpress_links</code>
	 */
	public static final WordpressLinks WORDPRESS_LINKS = new WordpressLinks();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<WordpressLinksRecord> getRecordType() {
		return WordpressLinksRecord.class;
	}

	/**
	 * The column <code>wordpressdb.wordpress_links.link_id</code>.
	 */
	public final TableField<WordpressLinksRecord, ULong> LINK_ID = createField("link_id", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_links.link_url</code>.
	 */
	public final TableField<WordpressLinksRecord, String> LINK_URL = createField("link_url", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_links.link_name</code>.
	 */
	public final TableField<WordpressLinksRecord, String> LINK_NAME = createField("link_name", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_links.link_image</code>.
	 */
	public final TableField<WordpressLinksRecord, String> LINK_IMAGE = createField("link_image", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_links.link_target</code>.
	 */
	public final TableField<WordpressLinksRecord, String> LINK_TARGET = createField("link_target", org.jooq.impl.SQLDataType.VARCHAR.length(25).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_links.link_description</code>.
	 */
	public final TableField<WordpressLinksRecord, String> LINK_DESCRIPTION = createField("link_description", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_links.link_visible</code>.
	 */
	public final TableField<WordpressLinksRecord, String> LINK_VISIBLE = createField("link_visible", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_links.link_owner</code>.
	 */
	public final TableField<WordpressLinksRecord, ULong> LINK_OWNER = createField("link_owner", org.jooq.impl.SQLDataType.BIGINTUNSIGNED.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_links.link_rating</code>.
	 */
	public final TableField<WordpressLinksRecord, Integer> LINK_RATING = createField("link_rating", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_links.link_updated</code>.
	 */
	public final TableField<WordpressLinksRecord, Timestamp> LINK_UPDATED = createField("link_updated", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_links.link_rel</code>.
	 */
	public final TableField<WordpressLinksRecord, String> LINK_REL = createField("link_rel", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_links.link_notes</code>.
	 */
	public final TableField<WordpressLinksRecord, String> LINK_NOTES = createField("link_notes", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * The column <code>wordpressdb.wordpress_links.link_rss</code>.
	 */
	public final TableField<WordpressLinksRecord, String> LINK_RSS = createField("link_rss", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>wordpressdb.wordpress_links</code> table reference
	 */
	public WordpressLinks() {
		this("wordpress_links", null);
	}

	/**
	 * Create an aliased <code>wordpressdb.wordpress_links</code> table reference
	 */
	public WordpressLinks(String alias) {
		this(alias, WORDPRESS_LINKS);
	}

	private WordpressLinks(String alias, Table<WordpressLinksRecord> aliased) {
		this(alias, aliased, null);
	}

	private WordpressLinks(String alias, Table<WordpressLinksRecord> aliased, Field<?>[] parameters) {
		super(alias, Wordpressdb.WORDPRESSDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<WordpressLinksRecord, ULong> getIdentity() {
		return Keys.IDENTITY_WORDPRESS_LINKS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<WordpressLinksRecord> getPrimaryKey() {
		return Keys.KEY_WORDPRESS_LINKS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<WordpressLinksRecord>> getKeys() {
		return Arrays.<UniqueKey<WordpressLinksRecord>>asList(Keys.KEY_WORDPRESS_LINKS_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinks as(String alias) {
		return new WordpressLinks(alias, this);
	}

	/**
	 * Rename this table
	 */
	public WordpressLinks rename(String name) {
		return new WordpressLinks(name, null);
	}
}
