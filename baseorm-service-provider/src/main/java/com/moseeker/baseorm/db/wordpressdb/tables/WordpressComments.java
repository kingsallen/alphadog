/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.wordpressdb.tables;


import com.moseeker.baseorm.db.wordpressdb.Keys;
import com.moseeker.baseorm.db.wordpressdb.Wordpressdb;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressCommentsRecord;

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
public class WordpressComments extends TableImpl<WordpressCommentsRecord> {

    private static final long serialVersionUID = 1390778392;

    /**
     * The reference instance of <code>wordpressdb.wordpress_comments</code>
     */
    public static final WordpressComments WORDPRESS_COMMENTS = new WordpressComments();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WordpressCommentsRecord> getRecordType() {
        return WordpressCommentsRecord.class;
    }

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_ID</code>.
     */
    public final TableField<WordpressCommentsRecord, Long> COMMENT_ID = createField("comment_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_post_ID</code>.
     */
    public final TableField<WordpressCommentsRecord, Long> COMMENT_POST_ID = createField("comment_post_ID", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_author</code>.
     */
    public final TableField<WordpressCommentsRecord, String> COMMENT_AUTHOR = createField("comment_author", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_author_email</code>.
     */
    public final TableField<WordpressCommentsRecord, String> COMMENT_AUTHOR_EMAIL = createField("comment_author_email", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_author_url</code>.
     */
    public final TableField<WordpressCommentsRecord, String> COMMENT_AUTHOR_URL = createField("comment_author_url", org.jooq.impl.SQLDataType.VARCHAR.length(200).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_author_IP</code>.
     */
    public final TableField<WordpressCommentsRecord, String> COMMENT_AUTHOR_IP = createField("comment_author_IP", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_date</code>.
     */
    public final TableField<WordpressCommentsRecord, Timestamp> COMMENT_DATE = createField("comment_date", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_date_gmt</code>.
     */
    public final TableField<WordpressCommentsRecord, Timestamp> COMMENT_DATE_GMT = createField("comment_date_gmt", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0000-00-00 00:00:00", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_content</code>.
     */
    public final TableField<WordpressCommentsRecord, String> COMMENT_CONTENT = createField("comment_content", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_karma</code>.
     */
    public final TableField<WordpressCommentsRecord, Integer> COMMENT_KARMA = createField("comment_karma", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_approved</code>.
     */
    public final TableField<WordpressCommentsRecord, String> COMMENT_APPROVED = createField("comment_approved", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_agent</code>.
     */
    public final TableField<WordpressCommentsRecord, String> COMMENT_AGENT = createField("comment_agent", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_type</code>.
     */
    public final TableField<WordpressCommentsRecord, String> COMMENT_TYPE = createField("comment_type", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.comment_parent</code>.
     */
    public final TableField<WordpressCommentsRecord, Long> COMMENT_PARENT = createField("comment_parent", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_comments.user_id</code>.
     */
    public final TableField<WordpressCommentsRecord, Long> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * Create a <code>wordpressdb.wordpress_comments</code> table reference
     */
    public WordpressComments() {
        this("wordpress_comments", null);
    }

    /**
     * Create an aliased <code>wordpressdb.wordpress_comments</code> table reference
     */
    public WordpressComments(String alias) {
        this(alias, WORDPRESS_COMMENTS);
    }

    private WordpressComments(String alias, Table<WordpressCommentsRecord> aliased) {
        this(alias, aliased, null);
    }

    private WordpressComments(String alias, Table<WordpressCommentsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<WordpressCommentsRecord, Long> getIdentity() {
        return Keys.IDENTITY_WORDPRESS_COMMENTS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<WordpressCommentsRecord> getPrimaryKey() {
        return Keys.KEY_WORDPRESS_COMMENTS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<WordpressCommentsRecord>> getKeys() {
        return Arrays.<UniqueKey<WordpressCommentsRecord>>asList(Keys.KEY_WORDPRESS_COMMENTS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressComments as(String alias) {
        return new WordpressComments(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public WordpressComments rename(String name) {
        return new WordpressComments(name, null);
    }
}
