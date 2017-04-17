/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.wordpressdb.tables;


import com.moseeker.baseorm.db.wordpressdb.Keys;
import com.moseeker.baseorm.db.wordpressdb.Wordpressdb;
import com.moseeker.baseorm.db.wordpressdb.tables.records.WordpressPostmetaRecord;

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
public class WordpressPostmeta extends TableImpl<WordpressPostmetaRecord> {

    private static final long serialVersionUID = -1155405702;

    /**
     * The reference instance of <code>wordpressdb.wordpress_postmeta</code>
     */
    public static final WordpressPostmeta WORDPRESS_POSTMETA = new WordpressPostmeta();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<WordpressPostmetaRecord> getRecordType() {
        return WordpressPostmetaRecord.class;
    }

    /**
     * The column <code>wordpressdb.wordpress_postmeta.meta_id</code>.
     */
    public final TableField<WordpressPostmetaRecord, Long> META_ID = createField("meta_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>wordpressdb.wordpress_postmeta.post_id</code>.
     */
    public final TableField<WordpressPostmetaRecord, Long> POST_ID = createField("post_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>wordpressdb.wordpress_postmeta.meta_key</code>.
     */
    public final TableField<WordpressPostmetaRecord, String> META_KEY = createField("meta_key", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");

    /**
     * The column <code>wordpressdb.wordpress_postmeta.meta_value</code>.
     */
    public final TableField<WordpressPostmetaRecord, String> META_VALUE = createField("meta_value", org.jooq.impl.SQLDataType.CLOB, this, "");

    /**
     * Create a <code>wordpressdb.wordpress_postmeta</code> table reference
     */
    public WordpressPostmeta() {
        this("wordpress_postmeta", null);
    }

    /**
     * Create an aliased <code>wordpressdb.wordpress_postmeta</code> table reference
     */
    public WordpressPostmeta(String alias) {
        this(alias, WORDPRESS_POSTMETA);
    }

    private WordpressPostmeta(String alias, Table<WordpressPostmetaRecord> aliased) {
        this(alias, aliased, null);
    }

    private WordpressPostmeta(String alias, Table<WordpressPostmetaRecord> aliased, Field<?>[] parameters) {
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
    public Identity<WordpressPostmetaRecord, Long> getIdentity() {
        return Keys.IDENTITY_WORDPRESS_POSTMETA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<WordpressPostmetaRecord> getPrimaryKey() {
        return Keys.KEY_WORDPRESS_POSTMETA_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<WordpressPostmetaRecord>> getKeys() {
        return Arrays.<UniqueKey<WordpressPostmetaRecord>>asList(Keys.KEY_WORDPRESS_POSTMETA_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressPostmeta as(String alias) {
        return new WordpressPostmeta(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public WordpressPostmeta rename(String name) {
        return new WordpressPostmeta(name, null);
    }
}
