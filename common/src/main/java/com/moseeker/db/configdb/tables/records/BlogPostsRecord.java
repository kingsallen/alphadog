/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.configdb.tables.records;


import com.moseeker.db.configdb.tables.BlogPosts;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;
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
public class BlogPostsRecord extends UpdatableRecordImpl<BlogPostsRecord> implements Record5<UInteger, String, String, Timestamp, Timestamp> {

	private static final long serialVersionUID = 1561105176;

	/**
	 * Setter for <code>configDB.blog_posts.id</code>.
	 */
	public void setId(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>configDB.blog_posts.id</code>.
	 */
	public UInteger getId() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>configDB.blog_posts.title</code>.
	 */
	public void setTitle(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>configDB.blog_posts.title</code>.
	 */
	public String getTitle() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>configDB.blog_posts.text</code>.
	 */
	public void setText(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>configDB.blog_posts.text</code>.
	 */
	public String getText() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>configDB.blog_posts.created</code>.
	 */
	public void setCreated(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>configDB.blog_posts.created</code>.
	 */
	public Timestamp getCreated() {
		return (Timestamp) getValue(3);
	}

	/**
	 * Setter for <code>configDB.blog_posts.updated</code>.
	 */
	public void setUpdated(Timestamp value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>configDB.blog_posts.updated</code>.
	 */
	public Timestamp getUpdated() {
		return (Timestamp) getValue(4);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<UInteger> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record5 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<UInteger, String, String, Timestamp, Timestamp> fieldsRow() {
		return (Row5) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row5<UInteger, String, String, Timestamp, Timestamp> valuesRow() {
		return (Row5) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<UInteger> field1() {
		return BlogPosts.BLOG_POSTS.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return BlogPosts.BLOG_POSTS.TITLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return BlogPosts.BLOG_POSTS.TEXT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return BlogPosts.BLOG_POSTS.CREATED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field5() {
		return BlogPosts.BLOG_POSTS.UPDATED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UInteger value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getTitle();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getText();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value4() {
		return getCreated();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value5() {
		return getUpdated();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogPostsRecord value1(UInteger value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogPostsRecord value2(String value) {
		setTitle(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogPostsRecord value3(String value) {
		setText(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogPostsRecord value4(Timestamp value) {
		setCreated(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogPostsRecord value5(Timestamp value) {
		setUpdated(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BlogPostsRecord values(UInteger value1, String value2, String value3, Timestamp value4, Timestamp value5) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached BlogPostsRecord
	 */
	public BlogPostsRecord() {
		super(BlogPosts.BLOG_POSTS);
	}

	/**
	 * Create a detached, initialised BlogPostsRecord
	 */
	public BlogPostsRecord(UInteger id, String title, String text, Timestamp created, Timestamp updated) {
		super(BlogPosts.BLOG_POSTS);

		setValue(0, id);
		setValue(1, title);
		setValue(2, text);
		setValue(3, created);
		setValue(4, updated);
	}
}
