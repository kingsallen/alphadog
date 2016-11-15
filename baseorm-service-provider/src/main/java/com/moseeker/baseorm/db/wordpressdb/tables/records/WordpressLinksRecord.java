/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.wordpressdb.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.ULong;

import com.moseeker.baseorm.db.wordpressdb.tables.WordpressLinks;


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
public class WordpressLinksRecord extends UpdatableRecordImpl<WordpressLinksRecord> implements Record13<ULong, String, String, String, String, String, String, ULong, Integer, Timestamp, String, String, String> {

	private static final long serialVersionUID = 1519553306;

	/**
	 * Setter for <code>wordpressdb.wordpress_links.link_id</code>.
	 */
	public void setLinkId(ULong value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_links.link_id</code>.
	 */
	public ULong getLinkId() {
		return (ULong) getValue(0);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_links.link_url</code>.
	 */
	public void setLinkUrl(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_links.link_url</code>.
	 */
	public String getLinkUrl() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_links.link_name</code>.
	 */
	public void setLinkName(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_links.link_name</code>.
	 */
	public String getLinkName() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_links.link_image</code>.
	 */
	public void setLinkImage(String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_links.link_image</code>.
	 */
	public String getLinkImage() {
		return (String) getValue(3);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_links.link_target</code>.
	 */
	public void setLinkTarget(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_links.link_target</code>.
	 */
	public String getLinkTarget() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_links.link_description</code>.
	 */
	public void setLinkDescription(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_links.link_description</code>.
	 */
	public String getLinkDescription() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_links.link_visible</code>.
	 */
	public void setLinkVisible(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_links.link_visible</code>.
	 */
	public String getLinkVisible() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_links.link_owner</code>.
	 */
	public void setLinkOwner(ULong value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_links.link_owner</code>.
	 */
	public ULong getLinkOwner() {
		return (ULong) getValue(7);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_links.link_rating</code>.
	 */
	public void setLinkRating(Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_links.link_rating</code>.
	 */
	public Integer getLinkRating() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_links.link_updated</code>.
	 */
	public void setLinkUpdated(Timestamp value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_links.link_updated</code>.
	 */
	public Timestamp getLinkUpdated() {
		return (Timestamp) getValue(9);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_links.link_rel</code>.
	 */
	public void setLinkRel(String value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_links.link_rel</code>.
	 */
	public String getLinkRel() {
		return (String) getValue(10);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_links.link_notes</code>.
	 */
	public void setLinkNotes(String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_links.link_notes</code>.
	 */
	public String getLinkNotes() {
		return (String) getValue(11);
	}

	/**
	 * Setter for <code>wordpressdb.wordpress_links.link_rss</code>.
	 */
	public void setLinkRss(String value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>wordpressdb.wordpress_links.link_rss</code>.
	 */
	public String getLinkRss() {
		return (String) getValue(12);
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
	// Record13 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row13<ULong, String, String, String, String, String, String, ULong, Integer, Timestamp, String, String, String> fieldsRow() {
		return (Row13) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row13<ULong, String, String, String, String, String, String, ULong, Integer, Timestamp, String, String, String> valuesRow() {
		return (Row13) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<ULong> field1() {
		return WordpressLinks.WORDPRESS_LINKS.LINK_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return WordpressLinks.WORDPRESS_LINKS.LINK_URL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return WordpressLinks.WORDPRESS_LINKS.LINK_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field4() {
		return WordpressLinks.WORDPRESS_LINKS.LINK_IMAGE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return WordpressLinks.WORDPRESS_LINKS.LINK_TARGET;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return WordpressLinks.WORDPRESS_LINKS.LINK_DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field7() {
		return WordpressLinks.WORDPRESS_LINKS.LINK_VISIBLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<ULong> field8() {
		return WordpressLinks.WORDPRESS_LINKS.LINK_OWNER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field9() {
		return WordpressLinks.WORDPRESS_LINKS.LINK_RATING;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field10() {
		return WordpressLinks.WORDPRESS_LINKS.LINK_UPDATED;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field11() {
		return WordpressLinks.WORDPRESS_LINKS.LINK_REL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field12() {
		return WordpressLinks.WORDPRESS_LINKS.LINK_NOTES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field13() {
		return WordpressLinks.WORDPRESS_LINKS.LINK_RSS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ULong value1() {
		return getLinkId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getLinkUrl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getLinkName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value4() {
		return getLinkImage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getLinkTarget();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getLinkDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value7() {
		return getLinkVisible();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ULong value8() {
		return getLinkOwner();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value9() {
		return getLinkRating();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value10() {
		return getLinkUpdated();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value11() {
		return getLinkRel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value12() {
		return getLinkNotes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value13() {
		return getLinkRss();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord value1(ULong value) {
		setLinkId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord value2(String value) {
		setLinkUrl(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord value3(String value) {
		setLinkName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord value4(String value) {
		setLinkImage(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord value5(String value) {
		setLinkTarget(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord value6(String value) {
		setLinkDescription(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord value7(String value) {
		setLinkVisible(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord value8(ULong value) {
		setLinkOwner(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord value9(Integer value) {
		setLinkRating(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord value10(Timestamp value) {
		setLinkUpdated(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord value11(String value) {
		setLinkRel(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord value12(String value) {
		setLinkNotes(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord value13(String value) {
		setLinkRss(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordpressLinksRecord values(ULong value1, String value2, String value3, String value4, String value5, String value6, String value7, ULong value8, Integer value9, Timestamp value10, String value11, String value12, String value13) {
		value1(value1);
		value2(value2);
		value3(value3);
		value4(value4);
		value5(value5);
		value6(value6);
		value7(value7);
		value8(value8);
		value9(value9);
		value10(value10);
		value11(value11);
		value12(value12);
		value13(value13);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached WordpressLinksRecord
	 */
	public WordpressLinksRecord() {
		super(WordpressLinks.WORDPRESS_LINKS);
	}

	/**
	 * Create a detached, initialised WordpressLinksRecord
	 */
	public WordpressLinksRecord(ULong linkId, String linkUrl, String linkName, String linkImage, String linkTarget, String linkDescription, String linkVisible, ULong linkOwner, Integer linkRating, Timestamp linkUpdated, String linkRel, String linkNotes, String linkRss) {
		super(WordpressLinks.WORDPRESS_LINKS);

		setValue(0, linkId);
		setValue(1, linkUrl);
		setValue(2, linkName);
		setValue(3, linkImage);
		setValue(4, linkTarget);
		setValue(5, linkDescription);
		setValue(6, linkVisible);
		setValue(7, linkOwner);
		setValue(8, linkRating);
		setValue(9, linkUpdated);
		setValue(10, linkRel);
		setValue(11, linkNotes);
		setValue(12, linkRss);
	}
}
