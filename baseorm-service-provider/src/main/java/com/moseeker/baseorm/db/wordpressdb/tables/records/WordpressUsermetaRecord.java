/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.wordpressdb.tables.records;


import com.moseeker.baseorm.db.wordpressdb.tables.WordpressUsermeta;

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
public class WordpressUsermetaRecord extends UpdatableRecordImpl<WordpressUsermetaRecord> implements Record4<ULong, ULong, String, String> {

	private static final long serialVersionUID = -686825606;

	/**
	 * Setter for <code>wordpressdb.wordpress_usermeta.umeta_id</code>.
	 */
	public void setUmetaId(ULong value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_usermeta.umeta_id</code>.
	 */
	public ULong getUmetaId() {
		return (ULong) getValue(0);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_usermeta.user_id</code>.
	 */
	public void setUserId(ULong value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_usermeta.user_id</code>.
	 */
	public ULong getUserId() {
		return (ULong) getValue(1);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_usermeta.meta_key</code>.
	 */
	public void setMetaKey(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_usermeta.meta_key</code>.
	 */
	public String getMetaKey() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_usermeta.meta_value</code>.
	 */
	public void setMetaValue(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_usermeta.meta_value</code>.
	 */
	public String getMetaValue() {
		return (String) getValue(3);
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
	public Row4<ULong, ULong, String, String> fieldsRow() {
		return (Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row4<ULong, ULong, String, String> valuesRow() {
		return (Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<ULong> field1() {
		return WordpressUsermeta.WORDPRESS_USERMETA.UMETA_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<ULong> field2() {
		return WordpressUsermeta.WORDPRESS_USERMETA.USER_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return WordpressUsermeta.WORDPRESS_USERMETA.META_KEY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return WordpressUsermeta.WORDPRESS_USERMETA.META_VALUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ULong value1() {
		return getUmetaId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ULong value2() {
		return getUserId();
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
	public WordpressUsermetaRecord value1(ULong value) {
		setUmetaId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressUsermetaRecord value2(ULong value) {
		setUserId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressUsermetaRecord value3(String value) {
		setMetaKey(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressUsermetaRecord value4(String value) {
		setMetaValue(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressUsermetaRecord values(ULong value1, ULong value2, String value3, String value4) {
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
	 * Create a detached WordpressUsermetaRecord
	 */
	public WordpressUsermetaRecord() {
		super(WordpressUsermeta.WORDPRESS_USERMETA);
	}

	/**
	 * Create a detached, initialised WordpressUsermetaRecord
	 */
	public WordpressUsermetaRecord(ULong umetaId, ULong userId, String metaKey, String metaValue) {
		super(WordpressUsermeta.WORDPRESS_USERMETA);

		setValue(0, umetaId);
		setValue(1, userId);
		setValue(2, metaKey);
		setValue(3, metaValue);
	}
}