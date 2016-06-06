/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.analytics.tables.records;


import com.moseeker.db.analytics.tables.StOfficialPuv;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;


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
public class StOfficialPuvRecord extends UpdatableRecordImpl<StOfficialPuvRecord> implements Record11<Integer, Timestamp, String, Integer, Integer, String, Integer, Integer, Integer, String, Integer> {

	private static final long serialVersionUID = 399521080;

	/**
	 * Setter for <code>analytics.st_official_puv.id</code>.
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>analytics.st_official_puv.id</code>.
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>analytics.st_official_puv.create_time</code>.
	 */
	public void setCreateTime(Timestamp value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>analytics.st_official_puv.create_time</code>.
	 */
	public Timestamp getCreateTime() {
		return (Timestamp) getValue(1);
	}

	/**
	 * Setter for <code>analytics.st_official_puv.page</code>. 页面类型,index代表首页，aboutus代表关于我们，produc代表产品介绍,login_click代表登录点击,register_click代表注册点击,login代表登录页面,register代表注册页面
	 */
	public void setPage(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>analytics.st_official_puv.page</code>. 页面类型,index代表首页，aboutus代表关于我们，produc代表产品介绍,login_click代表登录点击,register_click代表注册点击,login代表登录页面,register代表注册页面
	 */
	public String getPage() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>analytics.st_official_puv.ip_count</code>. pv
	 */
	public void setIpCount(Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>analytics.st_official_puv.ip_count</code>. pv
	 */
	public Integer getIpCount() {
		return (Integer) getValue(3);
	}

	/**
	 * Setter for <code>analytics.st_official_puv.ip_unique</code>. uv
	 */
	public void setIpUnique(Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>analytics.st_official_puv.ip_unique</code>. uv
	 */
	public Integer getIpUnique() {
		return (Integer) getValue(4);
	}

	/**
	 * Setter for <code>analytics.st_official_puv.ip_top</code>. 访问最高ip
	 */
	public void setIpTop(String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>analytics.st_official_puv.ip_top</code>. 访问最高ip
	 */
	public String getIpTop() {
		return (String) getValue(5);
	}

	/**
	 * Setter for <code>analytics.st_official_puv.ip_freq</code>. 访问最高ip的频次
	 */
	public void setIpFreq(Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>analytics.st_official_puv.ip_freq</code>. 访问最高ip的频次
	 */
	public Integer getIpFreq() {
		return (Integer) getValue(6);
	}

	/**
	 * Setter for <code>analytics.st_official_puv.baidu_ip_count</code>. baidu来源pv
	 */
	public void setBaiduIpCount(Integer value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>analytics.st_official_puv.baidu_ip_count</code>. baidu来源pv
	 */
	public Integer getBaiduIpCount() {
		return (Integer) getValue(7);
	}

	/**
	 * Setter for <code>analytics.st_official_puv.baidu_ip_unique</code>. baidu来源uv
	 */
	public void setBaiduIpUnique(Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>analytics.st_official_puv.baidu_ip_unique</code>. baidu来源uv
	 */
	public Integer getBaiduIpUnique() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>analytics.st_official_puv.baidu_ip_top</code>. baidu来源最高访问ip
	 */
	public void setBaiduIpTop(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>analytics.st_official_puv.baidu_ip_top</code>. baidu来源最高访问ip
	 */
	public String getBaiduIpTop() {
		return (String) getValue(9);
	}

	/**
	 * Setter for <code>analytics.st_official_puv.baidu_ip_freq</code>. baidu来源最高访问ip频次
	 */
	public void setBaiduIpFreq(Integer value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>analytics.st_official_puv.baidu_ip_freq</code>. baidu来源最高访问ip频次
	 */
	public Integer getBaiduIpFreq() {
		return (Integer) getValue(10);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Integer> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record11 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<Integer, Timestamp, String, Integer, Integer, String, Integer, Integer, Integer, String, Integer> fieldsRow() {
		return (Row11) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row11<Integer, Timestamp, String, Integer, Integer, String, Integer, Integer, Integer, String, Integer> valuesRow() {
		return (Row11) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return StOfficialPuv.ST_OFFICIAL_PUV.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field2() {
		return StOfficialPuv.ST_OFFICIAL_PUV.CREATE_TIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return StOfficialPuv.ST_OFFICIAL_PUV.PAGE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field4() {
		return StOfficialPuv.ST_OFFICIAL_PUV.IP_COUNT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field5() {
		return StOfficialPuv.ST_OFFICIAL_PUV.IP_UNIQUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field6() {
		return StOfficialPuv.ST_OFFICIAL_PUV.IP_TOP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field7() {
		return StOfficialPuv.ST_OFFICIAL_PUV.IP_FREQ;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field8() {
		return StOfficialPuv.ST_OFFICIAL_PUV.BAIDU_IP_COUNT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field9() {
		return StOfficialPuv.ST_OFFICIAL_PUV.BAIDU_IP_UNIQUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return StOfficialPuv.ST_OFFICIAL_PUV.BAIDU_IP_TOP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field11() {
		return StOfficialPuv.ST_OFFICIAL_PUV.BAIDU_IP_FREQ;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value2() {
		return getCreateTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getPage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value4() {
		return getIpCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value5() {
		return getIpUnique();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value6() {
		return getIpTop();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value7() {
		return getIpFreq();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value8() {
		return getBaiduIpCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value9() {
		return getBaiduIpUnique();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getBaiduIpTop();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value11() {
		return getBaiduIpFreq();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StOfficialPuvRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StOfficialPuvRecord value2(Timestamp value) {
		setCreateTime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StOfficialPuvRecord value3(String value) {
		setPage(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StOfficialPuvRecord value4(Integer value) {
		setIpCount(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StOfficialPuvRecord value5(Integer value) {
		setIpUnique(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StOfficialPuvRecord value6(String value) {
		setIpTop(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StOfficialPuvRecord value7(Integer value) {
		setIpFreq(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StOfficialPuvRecord value8(Integer value) {
		setBaiduIpCount(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StOfficialPuvRecord value9(Integer value) {
		setBaiduIpUnique(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StOfficialPuvRecord value10(String value) {
		setBaiduIpTop(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StOfficialPuvRecord value11(Integer value) {
		setBaiduIpFreq(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StOfficialPuvRecord values(Integer value1, Timestamp value2, String value3, Integer value4, Integer value5, String value6, Integer value7, Integer value8, Integer value9, String value10, Integer value11) {
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
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached StOfficialPuvRecord
	 */
	public StOfficialPuvRecord() {
		super(StOfficialPuv.ST_OFFICIAL_PUV);
	}

	/**
	 * Create a detached, initialised StOfficialPuvRecord
	 */
	public StOfficialPuvRecord(Integer id, Timestamp createTime, String page, Integer ipCount, Integer ipUnique, String ipTop, Integer ipFreq, Integer baiduIpCount, Integer baiduIpUnique, String baiduIpTop, Integer baiduIpFreq) {
		super(StOfficialPuv.ST_OFFICIAL_PUV);

		setValue(0, id);
		setValue(1, createTime);
		setValue(2, page);
		setValue(3, ipCount);
		setValue(4, ipUnique);
		setValue(5, ipTop);
		setValue(6, ipFreq);
		setValue(7, baiduIpCount);
		setValue(8, baiduIpUnique);
		setValue(9, baiduIpTop);
		setValue(10, baiduIpFreq);
	}
}