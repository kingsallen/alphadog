/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.wordpressdb.tables;


import com.moseeker.baseorm.db.wordpressdb.Keys;
import com.moseeker.baseorm.db.wordpressdb.Wordpressdb;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressOptionsRecord;

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
public class WordpressOptions extends TableImpl<WordpressOptionsRecord> {

    private static final long serialVersionUID = -1328222196;

    /**
     * The reference instance of <code>wordpressdb.wordpress_options</code>
     */
    public static final WordpressOptions WORDPRESS_OPTIONS = new WordpressOptions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WordpressOptionsRecord> getRecordType() {
        return WordpressOptionsRecord.class;
    }

    /**
     * The column <code>wordpressdb.wordpress_options.option_id</code>.
     */
    public final TableField<WordpressOptionsRecord, Long> OPTION_ID = createField("option_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>wordpressdb.wordpress_options.option_name</code>.
     */
    public final TableField<WordpressOptionsRecord, String> OPTION_NAME = createField("option_name", org.jooq.impl.SQLDataType.VARCHAR.length(191).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_options.option_value</code>.
     */
    public final TableField<WordpressOptionsRecord, String> OPTION_VALUE = createField("option_value", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>wordpressdb.wordpress_options.autoload</code>.
     */
    public final TableField<WordpressOptionsRecord, String> AUTOLOAD = createField("autoload", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("yes", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * Create a <code>wordpressdb.wordpress_options</code> table reference
     */
    public WordpressOptions() {
        this("wordpress_options", null);
    }

    /**
     * Create an aliased <code>wordpressdb.wordpress_options</code> table reference
     */
    public WordpressOptions(String alias) {
        this(alias, WORDPRESS_OPTIONS);
    }

    private WordpressOptions(String alias, Table<WordpressOptionsRecord> aliased) {
        this(alias, aliased, null);
    }

    private WordpressOptions(String alias, Table<WordpressOptionsRecord> aliased, Field<?>[] parameters) {
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
    public Identity<WordpressOptionsRecord, Long> getIdentity() {
        return Keys.IDENTITY_WORDPRESS_OPTIONS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<WordpressOptionsRecord> getPrimaryKey() {
        return Keys.KEY_WORDPRESS_OPTIONS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<WordpressOptionsRecord>> getKeys() {
        return Arrays.<UniqueKey<WordpressOptionsRecord>>asList(Keys.KEY_WORDPRESS_OPTIONS_PRIMARY, Keys.KEY_WORDPRESS_OPTIONS_OPTION_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressOptions as(String alias) {
        return new WordpressOptions(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public WordpressOptions rename(String name) {
        return new WordpressOptions(name, null);
    }
}
