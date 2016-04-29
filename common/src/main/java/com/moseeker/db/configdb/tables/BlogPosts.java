/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.configdb.tables;


import com.moseeker.db.configdb.Configdb;
import com.moseeker.db.configdb.Keys;
import com.moseeker.db.configdb.tables.records.BlogPostsRecord;

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
import org.jooq.types.UInteger;


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
public class BlogPosts extends TableImpl<BlogPostsRecord> {

	private static final long serialVersionUID = -466127394;

	/**
	 * The reference instance of <code>configDB.blog_posts</code>
	 */
	public static final BlogPosts BLOG_POSTS = new BlogPosts();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<BlogPostsRecord> getRecordType() {
		return BlogPostsRecord.class;
	}

	/**
	 * The column <code>configDB.blog_posts.id</code>.
	 */
	public final TableField<BlogPostsRecord, UInteger> ID = createField("id", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false), this, "");

	/**
	 * The column <code>configDB.blog_posts.title</code>.
	 */
	public final TableField<BlogPostsRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaulted(true), this, "");

	/**
	 * The column <code>configDB.blog_posts.text</code>.
	 */
	public final TableField<BlogPostsRecord, String> TEXT = createField("text", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

	/**
	 * The column <code>configDB.blog_posts.created</code>.
	 */
	public final TableField<BlogPostsRecord, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

	/**
	 * The column <code>configDB.blog_posts.updated</code>.
	 */
	public final TableField<BlogPostsRecord, Timestamp> UPDATED = createField("updated", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

	/**
	 * Create a <code>configDB.blog_posts</code> table reference
	 */
	public BlogPosts() {
		this("blog_posts", null);
	}

	/**
	 * Create an aliased <code>configDB.blog_posts</code> table reference
	 */
	public BlogPosts(String alias) {
		this(alias, BLOG_POSTS);
	}

	private BlogPosts(String alias, Table<BlogPostsRecord> aliased) {
		this(alias, aliased, null);
	}

	private BlogPosts(String alias, Table<BlogPostsRecord> aliased, Field<?>[] parameters) {
		super(alias, Configdb.CONFIGDB, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<BlogPostsRecord, UInteger> getIdentity() {
		return Keys.IDENTITY_BLOG_POSTS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<BlogPostsRecord> getPrimaryKey() {
		return Keys.KEY_BLOG_POSTS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<BlogPostsRecord>> getKeys() {
		return Arrays.<UniqueKey<BlogPostsRecord>>asList(Keys.KEY_BLOG_POSTS_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogPosts as(String alias) {
		return new BlogPosts(alias, this);
	}

	/**
	 * Rename this table
	 */
	public BlogPosts rename(String name) {
		return new BlogPosts(name, null);
	}
}
