/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.wordpressdb.tables.records;


import com.moseeker.baseorm.db.wordpressdb.tables.WordpressTerms;

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
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class WordpressTermsRecord extends UpdatableRecordImpl<WordpressTermsRecord> implements Record4<Long, String, String, Long> {

    private static final long serialVersionUID = -1001323860;

    /**
     * Setter for <code>wordpressdb.wordpress_terms.term_id</code>.
     */
    public void setTermId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>wordpressdb.wordpress_terms.term_id</code>.
     */
    public Long getTermId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>wordpressdb.wordpress_terms.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>wordpressdb.wordpress_terms.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>wordpressdb.wordpress_terms.slug</code>.
     */
    public void setSlug(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>wordpressdb.wordpress_terms.slug</code>.
     */
    public String getSlug() {
        return (String) get(2);
    }

    /**
     * Setter for <code>wordpressdb.wordpress_terms.term_group</code>.
     */
    public void setTermGroup(Long value) {
        set(3, value);
    }

    /**
     * Getter for <code>wordpressdb.wordpress_terms.term_group</code>.
     */
    public Long getTermGroup() {
        return (Long) get(3);
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
    public Row4<Long, String, String, Long> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Long, String, String, Long> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return WordpressTerms.WORDPRESS_TERMS.TERM_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return WordpressTerms.WORDPRESS_TERMS.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return WordpressTerms.WORDPRESS_TERMS.SLUG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field4() {
        return WordpressTerms.WORDPRESS_TERMS.TERM_GROUP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getTermId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getSlug();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value4() {
        return getTermGroup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressTermsRecord value1(Long value) {
        setTermId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressTermsRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressTermsRecord value3(String value) {
        setSlug(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressTermsRecord value4(Long value) {
        setTermGroup(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public WordpressTermsRecord values(Long value1, String value2, String value3, Long value4) {
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
     * Create a detached WordpressTermsRecord
     */
    public WordpressTermsRecord() {
        super(WordpressTerms.WORDPRESS_TERMS);
    }

    /**
     * Create a detached, initialised WordpressTermsRecord
     */
    public WordpressTermsRecord(Long termId, String name, String slug, Long termGroup) {
        super(WordpressTerms.WORDPRESS_TERMS);

        set(0, termId);
        set(1, name);
        set(2, slug);
        set(3, termGroup);
    }
}
