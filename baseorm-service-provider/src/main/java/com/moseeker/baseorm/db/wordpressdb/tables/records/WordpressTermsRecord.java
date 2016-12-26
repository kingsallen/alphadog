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
public class WordpressTermsRecord extends UpdatableRecordImpl<WordpressTermsRecord> implements Record4<ULong, String, String, Long> {

	private static final long serialVersionUID = -1949349696;

	/**
	 * Setter for <code>wordpressdb.wordpress_terms.term_id</code>.
	 */
	public void setTermId(ULong value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_terms.term_id</code>.
	 */
	public ULong getTermId() {
		return (ULong) getValue(0);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_terms.name</code>.
	 */
	public void setName(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_terms.name</code>.
	 */
	public String getName() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_terms.slug</code>.
	 */
	public void setSlug(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_terms.slug</code>.
	 */
	public String getSlug() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_terms.term_group</code>.
	 */
	public void setTermGroup(Long value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_terms.term_group</code>.
	 */
	public Long getTermGroup() {
		return (Long) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<ULong> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<ULong, String, String, Long> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<ULong, String, String, Long> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<ULong> field1() {
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
	public ULong value1() {
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
	public WordpressTermsRecord value1(ULong value) {
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
	public WordpressTermsRecord values(ULong value1, String value2, String value3, Long value4) {
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
	public WordpressTermsRecord(ULong termId, String name, String slug, Long termGroup) {
		super(WordpressTerms.WORDPRESS_TERMS);

		setValue(0, termId);
		setValue(1, name);
		setValue(2, slug);
		setValue(3, termGroup);
	}
}
