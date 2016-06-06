/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.analytics.tables.records;


import com.moseeker.db.analytics.tables.ViewResumeImport;

import java.math.BigInteger;
import java.sql.Date;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record12;
import org.jooq.Row12;
import org.jooq.impl.TableRecordImpl;


/**
 * VIEW
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ViewResumeImportRecord extends TableRecordImpl<ViewResumeImportRecord> implements Record12<Date, String, String, Long, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> {

	private static final long serialVersionUID = 645132240;

	/**
	 * Setter for <code>analytics.view_resume_import.date</code>.
	 */
	public void setDate(Date value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>analytics.view_resume_import.date</code>.
	 */
	public Date getDate() {
		return (Date) getValue(0);
	}

	/**
	 * Setter for <code>analytics.view_resume_import.url</code>.
	 */
	public void setUrl(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>analytics.view_resume_import.url</code>.
	 */
	public String getUrl() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>analytics.view_resume_import.Job Board</code>.
	 */
	public void setJobBoard(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>analytics.view_resume_import.Job Board</code>.
	 */
	public String getJobBoard() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>analytics.view_resume_import.total</code>.
	 */
	public void setTotal(Long value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>analytics.view_resume_import.total</code>.
	 */
	public Long getTotal() {
		return (Long) getValue(3);
	}

	/**
	 * Setter for <code>analytics.view_resume_import.0</code>.
	 */
	public void set_0(BigInteger value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>analytics.view_resume_import.0</code>.
	 */
	public BigInteger get_0() {
		return (BigInteger) getValue(4);
	}

	/**
	 * Setter for <code>analytics.view_resume_import.-3</code>.
	 */
	public void set__3(BigInteger value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>analytics.view_resume_import.-3</code>.
	 */
	public BigInteger get__3() {
		return (BigInteger) getValue(5);
	}

	/**
	 * Setter for <code>analytics.view_resume_import.-2</code>.
	 */
	public void set__2(BigInteger value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>analytics.view_resume_import.-2</code>.
	 */
	public BigInteger get__2() {
		return (BigInteger) getValue(6);
	}

	/**
	 * Setter for <code>analytics.view_resume_import.-1</code>.
	 */
	public void set__1(BigInteger value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>analytics.view_resume_import.-1</code>.
	 */
	public BigInteger get__1() {
		return (BigInteger) getValue(7);
	}

	/**
	 * Setter for <code>analytics.view_resume_import.1</code>.
	 */
	public void set_1(BigInteger value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>analytics.view_resume_import.1</code>.
	 */
	public BigInteger get_1() {
		return (BigInteger) getValue(8);
	}

	/**
	 * Setter for <code>analytics.view_resume_import.2</code>.
	 */
	public void set_2(BigInteger value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>analytics.view_resume_import.2</code>.
	 */
	public BigInteger get_2() {
		return (BigInteger) getValue(9);
	}

	/**
	 * Setter for <code>analytics.view_resume_import.3</code>.
	 */
	public void set_3(BigInteger value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>analytics.view_resume_import.3</code>.
	 */
	public BigInteger get_3() {
		return (BigInteger) getValue(10);
	}

	/**
	 * Setter for <code>analytics.view_resume_import.4</code>.
	 */
	public void set_4(BigInteger value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>analytics.view_resume_import.4</code>.
	 */
	public BigInteger get_4() {
		return (BigInteger) getValue(11);
	}

	// -------------------------------------------------------------------------
	// Record12 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row12<Date, String, String, Long, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> fieldsRow() {
		return (Row12) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row12<Date, String, String, Long, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger> valuesRow() {
		return (Row12) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Date> field1() {
		return ViewResumeImport.VIEW_RESUME_IMPORT.DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return ViewResumeImport.VIEW_RESUME_IMPORT.URL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return ViewResumeImport.VIEW_RESUME_IMPORT.JOB_BOARD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field4() {
		return ViewResumeImport.VIEW_RESUME_IMPORT.TOTAL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigInteger> field5() {
		return ViewResumeImport.VIEW_RESUME_IMPORT._0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigInteger> field6() {
		return ViewResumeImport.VIEW_RESUME_IMPORT._3;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigInteger> field7() {
		return ViewResumeImport.VIEW_RESUME_IMPORT._2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigInteger> field8() {
		return ViewResumeImport.VIEW_RESUME_IMPORT._1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigInteger> field9() {
		return ViewResumeImport.VIEW_RESUME_IMPORT._1;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigInteger> field10() {
		return ViewResumeImport.VIEW_RESUME_IMPORT._2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigInteger> field11() {
		return ViewResumeImport.VIEW_RESUME_IMPORT._3;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<BigInteger> field12() {
		return ViewResumeImport.VIEW_RESUME_IMPORT._4;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date value1() {
		return getDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value2() {
		return getUrl();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getJobBoard();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value4() {
		return getTotal();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigInteger value5() {
		return get_0();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigInteger value6() {
		return get__3();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigInteger value7() {
		return get__2();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigInteger value8() {
		return get__1();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigInteger value9() {
		return get_1();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigInteger value10() {
		return get_2();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigInteger value11() {
		return get_3();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BigInteger value12() {
		return get_4();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewResumeImportRecord value1(Date value) {
		setDate(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewResumeImportRecord value2(String value) {
		setUrl(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewResumeImportRecord value3(String value) {
		setJobBoard(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewResumeImportRecord value4(Long value) {
		setTotal(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewResumeImportRecord value5(BigInteger value) {
		set_0(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewResumeImportRecord value6(BigInteger value) {
		set__3(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewResumeImportRecord value7(BigInteger value) {
		set__2(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewResumeImportRecord value8(BigInteger value) {
		set__1(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewResumeImportRecord value9(BigInteger value) {
		set_1(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewResumeImportRecord value10(BigInteger value) {
		set_2(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewResumeImportRecord value11(BigInteger value) {
		set_3(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewResumeImportRecord value12(BigInteger value) {
		set_4(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ViewResumeImportRecord values(Date value1, String value2, String value3, Long value4, BigInteger value5, BigInteger value6, BigInteger value7, BigInteger value8, BigInteger value9, BigInteger value10, BigInteger value11, BigInteger value12) {
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
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached ViewResumeImportRecord
	 */
	public ViewResumeImportRecord() {
		super(ViewResumeImport.VIEW_RESUME_IMPORT);
	}

	/**
	 * Create a detached, initialised ViewResumeImportRecord
	 */
	public ViewResumeImportRecord(Date date, String url, String jobBoard, Long total, BigInteger _0, BigInteger __3, BigInteger __2, BigInteger __1, BigInteger _1, BigInteger _2, BigInteger _3, BigInteger _4) {
		super(ViewResumeImport.VIEW_RESUME_IMPORT);

		setValue(0, date);
		setValue(1, url);
		setValue(2, jobBoard);
		setValue(3, total);
		setValue(4, _0);
		setValue(5, __3);
		setValue(6, __2);
		setValue(7, __1);
		setValue(8, _1);
		setValue(9, _2);
		setValue(10, _3);
		setValue(11, _4);
	}
}