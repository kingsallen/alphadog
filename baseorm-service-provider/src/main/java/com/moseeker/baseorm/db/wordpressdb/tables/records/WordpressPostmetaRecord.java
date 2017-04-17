/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.wordpressdb.tables.records;


import com.moseeker.baseorm.db.wordpressdb.tables.WordpressPostmeta;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


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
public class WordpressPostmetaRecord extends UpdatableRecordImpl<WordpressPostmetaRecord> implements Record4<Long, Long, String, String> {

    private static final long serialVersionUID = 806259323;

    /**
     * Setter for <code>wordpressdb.wordpress_postmeta.meta_id</code>.
     */
    public void setMetaId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>wordpressdb.wordpress_postmeta.meta_id</code>.
     */
    public Long getMetaId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>wordpressdb.wordpress_postmeta.post_id</code>.
     */
    public void setPostId(Long value) {
        set(1, value);
    }

    /**
     * Getter for <code>wordpressdb.wordpress_postmeta.post_id</code>.
     */
    public Long getPostId() {
        return (Long) get(1);
    }

    /**
     * Setter for <code>wordpressdb.wordpress_postmeta.meta_key</code>.
     */
    public void setMetaKey(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>wordpressdb.wordpress_postmeta.meta_key</code>.
     */
    public String getMetaKey() {
        return (String) get(2);
    }

    /**
     * Setter for <code>wordpressdb.wordpress_postmeta.meta_value</code>.
     */
    public void setMetaValue(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>wordpressdb.wordpress_postmeta.meta_value</code>.
     */
    public String getMetaValue() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, Long, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, Long, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return WordpressPostmeta.WORDPRESS_POSTMETA.META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field2() {
        return WordpressPostmeta.WORDPRESS_POSTMETA.POST_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return WordpressPostmeta.WORDPRESS_POSTMETA.META_KEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return WordpressPostmeta.WORDPRESS_POSTMETA.META_VALUE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getMetaId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value2() {
        return getPostId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getMetaKey();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getMetaValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressPostmetaRecord value1(Long value) {
        setMetaId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressPostmetaRecord value2(Long value) {
        setPostId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressPostmetaRecord value3(String value) {
        setMetaKey(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressPostmetaRecord value4(String value) {
        setMetaValue(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressPostmetaRecord values(Long value1, Long value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached WordpressPostmetaRecord
     */
    public WordpressPostmetaRecord() {
        super(WordpressPostmeta.WORDPRESS_POSTMETA);
    }

    /**
     * Create a detached, initialised WordpressPostmetaRecord
     */
    public WordpressPostmetaRecord(Long metaId, Long postId, String metaKey, String metaValue) {
        super(WordpressPostmeta.WORDPRESS_POSTMETA);

        set(0, metaId);
        set(1, postId);
        set(2, metaKey);
        set(3, metaValue);
    }
}
