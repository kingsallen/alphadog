/*
 * This file is generated by jOOQ.
*/
package com.moseeker.db.wordpressdb.tables;


import com.moseeker.db.wordpressdb.Keys;
import com.moseeker.db.wordpressdb.Wordpressdb;
import com.moseeker.db.wordpressdb.tables.records.WordpressPostsRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WordpressPosts extends TableImpl<WordpressPostsRecord> {

    private static final long serialVersionUID = -47880305;

    /**
     * The reference instance of <code>wordpressdb.wordpress_posts</code>
     */
    public static final WordpressPosts WORDPRESS_POSTS = new WordpressPosts();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WordpressPostsRecord> getRecordType() {
        return WordpressPostsRecord.class;
    }

    /**
     * The column <code>wordpressdb.wordpress_posts.ID</code>.
     */
    public final TableField<WordpressPostsRecord, Long> ID = createField("ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_author</code>.
     */
    public final TableField<WordpressPostsRecord, Long> POST_AUTHOR = createField("post_author", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_date</code>.
     */
    public final TableField<WordpressPostsRecord, Timestamp> POST_DATE = createField("post_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_date_gmt</code>.
     */
    public final TableField<WordpressPostsRecord, Timestamp> POST_DATE_GMT = createField("post_date_gmt", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_content</code>.
     */
    public final TableField<WordpressPostsRecord, String> POST_CONTENT = createField("post_content", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_title</code>.
     */
    public final TableField<WordpressPostsRecord, String> POST_TITLE = createField("post_title", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_excerpt</code>.
     */
    public final TableField<WordpressPostsRecord, String> POST_EXCERPT = createField("post_excerpt", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_status</code>.
     */
    public final TableField<WordpressPostsRecord, String> POST_STATUS = createField("post_status", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("publish", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.comment_status</code>.
     */
    public final TableField<WordpressPostsRecord, String> COMMENT_STATUS = createField("comment_status", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("open", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.ping_status</code>.
     */
    public final TableField<WordpressPostsRecord, String> PING_STATUS = createField("ping_status", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("open", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_password</code>.
     */
    public final TableField<WordpressPostsRecord, String> POST_PASSWORD = createField("post_password", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_name</code>.
     */
    public final TableField<WordpressPostsRecord, String> POST_NAME = createField("post_name", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.to_ping</code>.
     */
    public final TableField<WordpressPostsRecord, String> TO_PING = createField("to_ping", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.pinged</code>.
     */
    public final TableField<WordpressPostsRecord, String> PINGED = createField("pinged", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_modified</code>.
     */
    public final TableField<WordpressPostsRecord, Timestamp> POST_MODIFIED = createField("post_modified", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_modified_gmt</code>.
     */
    public final TableField<WordpressPostsRecord, Timestamp> POST_MODIFIED_GMT = createField("post_modified_gmt", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_content_filtered</code>.
     */
    public final TableField<WordpressPostsRecord, String> POST_CONTENT_FILTERED = createField("post_content_filtered", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_parent</code>.
     */
    public final TableField<WordpressPostsRecord, Long> POST_PARENT = createField("post_parent", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.guid</code>.
     */
    public final TableField<WordpressPostsRecord, String> GUID = createField("guid", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.menu_order</code>.
     */
    public final TableField<WordpressPostsRecord, Integer> MENU_ORDER = createField("menu_order", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_type</code>.
     */
    public final TableField<WordpressPostsRecord, String> POST_TYPE = createField("post_type", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("post", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.post_mime_type</code>.
     */
    public final TableField<WordpressPostsRecord, String> POST_MIME_TYPE = createField("post_mime_type", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_posts.comment_count</code>.
     */
    public final TableField<WordpressPostsRecord, Long> COMMENT_COUNT = createField("comment_count", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * Create a <code>wordpressdb.wordpress_posts</code> table reference
     */
    public WordpressPosts() {
        this("wordpress_posts", null);
    }

    /**
     * Create an aliased <code>wordpressdb.wordpress_posts</code> table reference
     */
    public WordpressPosts(String alias) {
        this(alias, WORDPRESS_POSTS);
    }

    private WordpressPosts(String alias, Table<WordpressPostsRecord> aliased) {
        this(alias, aliased, null);
    }

    private WordpressPosts(String alias, Table<WordpressPostsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Wordpressdb.WORDPRESSDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<WordpressPostsRecord, Long> getIdentity() {
        return Keys.IDENTITY_WORDPRESS_POSTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<WordpressPostsRecord> getPrimaryKey() {
        return Keys.KEY_WORDPRESS_POSTS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<WordpressPostsRecord>> getKeys() {
        return Arrays.<UniqueKey<WordpressPostsRecord>>asList(Keys.KEY_WORDPRESS_POSTS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressPosts as(String alias) {
        return new WordpressPosts(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public WordpressPosts rename(String name) {
        return new WordpressPosts(name, null);
    }
}
