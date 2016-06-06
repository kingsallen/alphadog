/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.analytics.tables.records;


import com.moseeker.db.analytics.tables.WebAdmin;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Row10;
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
public class WebAdminRecord extends UpdatableRecordImpl<WebAdminRecord> implements Record10<Integer, String, String, Timestamp, String, Integer, Integer, String, Integer, String> {

	private static final long serialVersionUID = 273478205;

	/**
	 * Setter for <code>analytics.web_admin.id</code>. 唯一标识
	 */
	public void setId(Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>analytics.web_admin.id</code>. 唯一标识
	 */
	public Integer getId() {
		return (Integer) getValue(0);
	}

	/**
	 * Setter for <code>analytics.web_admin.username</code>. 用户名
	 */
	public void setUsername(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>analytics.web_admin.username</code>. 用户名
	 */
	public String getUsername() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>analytics.web_admin.password</code>.
	 */
	public void setPassword(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>analytics.web_admin.password</code>.
	 */
	public String getPassword() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>analytics.web_admin.last_login</code>. 上次登录时间
	 */
	public void setLastLogin(Timestamp value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>analytics.web_admin.last_login</code>. 上次登录时间
	 */
	public Timestamp getLastLogin() {
		return (Timestamp) getValue(3);
	}

	/**
	 * Setter for <code>analytics.web_admin.last_ip</code>. 上次登录ip
	 */
	public void setLastIp(String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>analytics.web_admin.last_ip</code>. 上次登录ip
	 */
	public String getLastIp() {
		return (String) getValue(4);
	}

	/**
	 * Setter for <code>analytics.web_admin.login_count</code>. 登录次数
	 */
	public void setLoginCount(Integer value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>analytics.web_admin.login_count</code>. 登录次数
	 */
	public Integer getLoginCount() {
		return (Integer) getValue(5);
	}

	/**
	 * Setter for <code>analytics.web_admin.mobile</code>. 手机号
	 */
	public void setMobile(Integer value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>analytics.web_admin.mobile</code>. 手机号
	 */
	public Integer getMobile() {
		return (Integer) getValue(6);
	}

	/**
	 * Setter for <code>analytics.web_admin.email</code>. email
	 */
	public void setEmail(String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>analytics.web_admin.email</code>. email
	 */
	public String getEmail() {
		return (String) getValue(7);
	}

	/**
	 * Setter for <code>analytics.web_admin.disable</code>. 0:可用,1:禁用,2:未激活
	 */
	public void setDisable(Integer value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>analytics.web_admin.disable</code>. 0:可用,1:禁用,2:未激活
	 */
	public Integer getDisable() {
		return (Integer) getValue(8);
	}

	/**
	 * Setter for <code>analytics.web_admin.code</code>. 激活码
	 */
	public void setCode(String value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>analytics.web_admin.code</code>. 激活码
	 */
	public String getCode() {
		return (String) getValue(9);
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
	// Record10 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row10<Integer, String, String, Timestamp, String, Integer, Integer, String, Integer, String> fieldsRow() {
		return (Row10) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row10<Integer, String, String, Timestamp, String, Integer, Integer, String, Integer, String> valuesRow() {
		return (Row10) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field1() {
		return WebAdmin.WEB_ADMIN.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field2() {
		return WebAdmin.WEB_ADMIN.USERNAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field3() {
		return WebAdmin.WEB_ADMIN.PASSWORD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Timestamp> field4() {
		return WebAdmin.WEB_ADMIN.LAST_LOGIN;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field5() {
		return WebAdmin.WEB_ADMIN.LAST_IP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field6() {
		return WebAdmin.WEB_ADMIN.LOGIN_COUNT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field7() {
		return WebAdmin.WEB_ADMIN.MOBILE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field8() {
		return WebAdmin.WEB_ADMIN.EMAIL;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Integer> field9() {
		return WebAdmin.WEB_ADMIN.DISABLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<String> field10() {
		return WebAdmin.WEB_ADMIN.CODE;
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
	public String value2() {
		return getUsername();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value3() {
		return getPassword();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Timestamp value4() {
		return getLastLogin();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value5() {
		return getLastIp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value6() {
		return getLoginCount();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value7() {
		return getMobile();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value8() {
		return getEmail();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Integer value9() {
		return getDisable();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String value10() {
		return getCode();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebAdminRecord value1(Integer value) {
		setId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebAdminRecord value2(String value) {
		setUsername(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebAdminRecord value3(String value) {
		setPassword(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebAdminRecord value4(Timestamp value) {
		setLastLogin(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebAdminRecord value5(String value) {
		setLastIp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebAdminRecord value6(Integer value) {
		setLoginCount(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebAdminRecord value7(Integer value) {
		setMobile(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebAdminRecord value8(String value) {
		setEmail(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebAdminRecord value9(Integer value) {
		setDisable(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebAdminRecord value10(String value) {
		setCode(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebAdminRecord values(Integer value1, String value2, String value3, Timestamp value4, String value5, Integer value6, Integer value7, String value8, Integer value9, String value10) {
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
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached WebAdminRecord
	 */
	public WebAdminRecord() {
		super(WebAdmin.WEB_ADMIN);
	}

	/**
	 * Create a detached, initialised WebAdminRecord
	 */
	public WebAdminRecord(Integer id, String username, String password, Timestamp lastLogin, String lastIp, Integer loginCount, Integer mobile, String email, Integer disable, String code) {
		super(WebAdmin.WEB_ADMIN);

		setValue(0, id);
		setValue(1, username);
		setValue(2, password);
		setValue(3, lastLogin);
		setValue(4, lastIp);
		setValue(5, loginCount);
		setValue(6, mobile);
		setValue(7, email);
		setValue(8, disable);
		setValue(9, code);
	}
}