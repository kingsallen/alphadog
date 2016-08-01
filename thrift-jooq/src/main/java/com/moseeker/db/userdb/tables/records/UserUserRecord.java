/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.userdb.tables.records;


import com.moseeker.db.userdb.tables.UserUser;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


/**
 * 用户表
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.3"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserUserRecord extends UpdatableRecordImpl<UserUserRecord> {

	private static final long serialVersionUID = -1133505425;

	/**
	 * Setter for <code>userdb.user_user.id</code>. 主key
	 */
	public void setId(UInteger value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>userdb.user_user.id</code>. 主key
	 */
	public UInteger getId() {
		return (UInteger) getValue(0);
	}

	/**
	 * Setter for <code>userdb.user_user.username</code>. 用户名，比如手机号、邮箱等
	 */
	public void setUsername(String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>userdb.user_user.username</code>. 用户名，比如手机号、邮箱等
	 */
	public String getUsername() {
		return (String) getValue(1);
	}

	/**
	 * Setter for <code>userdb.user_user.password</code>. 密码
	 */
	public void setPassword(String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>userdb.user_user.password</code>. 密码
	 */
	public String getPassword() {
		return (String) getValue(2);
	}

	/**
	 * Setter for <code>userdb.user_user.is_disable</code>. 是否禁用，0：可用，1：禁用
	 */
	public void setIsDisable(Byte value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>userdb.user_user.is_disable</code>. 是否禁用，0：可用，1：禁用
	 */
	public Byte getIsDisable() {
		return (Byte) getValue(3);
	}

	/**
	 * Setter for <code>userdb.user_user.rank</code>. 用户等级
	 */
	public void setRank(Integer value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>userdb.user_user.rank</code>. 用户等级
	 */
	public Integer getRank() {
		return (Integer) getValue(4);
	}

	/**
	 * Setter for <code>userdb.user_user.register_time</code>. 注册时间
	 */
	public void setRegisterTime(Timestamp value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>userdb.user_user.register_time</code>. 注册时间
	 */
	public Timestamp getRegisterTime() {
		return (Timestamp) getValue(5);
	}

	/**
	 * Setter for <code>userdb.user_user.register_ip</code>. 注册IP
	 */
	public void setRegisterIp(String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>userdb.user_user.register_ip</code>. 注册IP
	 */
	public String getRegisterIp() {
		return (String) getValue(6);
	}

	/**
	 * Setter for <code>userdb.user_user.last_login_time</code>. 最近登录时间
	 */
	public void setLastLoginTime(Timestamp value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>userdb.user_user.last_login_time</code>. 最近登录时间
	 */
	public Timestamp getLastLoginTime() {
		return (Timestamp) getValue(7);
	}

	/**
	 * Setter for <code>userdb.user_user.last_login_ip</code>. 最近登录IP
	 */
	public void setLastLoginIp(String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>userdb.user_user.last_login_ip</code>. 最近登录IP
	 */
	public String getLastLoginIp() {
		return (String) getValue(8);
	}

	/**
	 * Setter for <code>userdb.user_user.login_count</code>. 登录次数
	 */
	public void setLoginCount(Integer value) {
		setValue(9, value);
	}

	/**
	 * Getter for <code>userdb.user_user.login_count</code>. 登录次数
	 */
	public Integer getLoginCount() {
		return (Integer) getValue(9);
	}

	/**
	 * Setter for <code>userdb.user_user.mobile</code>. user pass mobile registe
	 */
	public void setMobile(Long value) {
		setValue(10, value);
	}

	/**
	 * Getter for <code>userdb.user_user.mobile</code>. user pass mobile registe
	 */
	public Long getMobile() {
		return (Long) getValue(10);
	}

	/**
	 * Setter for <code>userdb.user_user.email</code>. user pass email registe
	 */
	public void setEmail(String value) {
		setValue(11, value);
	}

	/**
	 * Getter for <code>userdb.user_user.email</code>. user pass email registe
	 */
	public String getEmail() {
		return (String) getValue(11);
	}

	/**
	 * Setter for <code>userdb.user_user.activation</code>. is not activation 0:no 1:yes
	 */
	public void setActivation(Byte value) {
		setValue(12, value);
	}

	/**
	 * Getter for <code>userdb.user_user.activation</code>. is not activation 0:no 1:yes
	 */
	public Byte getActivation() {
		return (Byte) getValue(12);
	}

	/**
	 * Setter for <code>userdb.user_user.activation_code</code>. activation code
	 */
	public void setActivationCode(String value) {
		setValue(13, value);
	}

	/**
	 * Getter for <code>userdb.user_user.activation_code</code>. activation code
	 */
	public String getActivationCode() {
		return (String) getValue(13);
	}

	/**
	 * Setter for <code>userdb.user_user.token</code>.
	 */
	public void setToken(String value) {
		setValue(14, value);
	}

	/**
	 * Getter for <code>userdb.user_user.token</code>.
	 */
	public String getToken() {
		return (String) getValue(14);
	}

	/**
	 * Setter for <code>userdb.user_user.name</code>. 真实姓名
	 */
	public void setName(String value) {
		setValue(15, value);
	}

	/**
	 * Getter for <code>userdb.user_user.name</code>. 真实姓名
	 */
	public String getName() {
		return (String) getValue(15);
	}

	/**
	 * Setter for <code>userdb.user_user.headimg</code>. 头像
	 */
	public void setHeadimg(String value) {
		setValue(16, value);
	}

	/**
	 * Getter for <code>userdb.user_user.headimg</code>. 头像
	 */
	public String getHeadimg() {
		return (String) getValue(16);
	}

	/**
	 * Setter for <code>userdb.user_user.national_code_id</code>. 国际电话区号ID
	 */
	public void setNationalCodeId(Integer value) {
		setValue(17, value);
	}

	/**
	 * Getter for <code>userdb.user_user.national_code_id</code>. 国际电话区号ID
	 */
	public Integer getNationalCodeId() {
		return (Integer) getValue(17);
	}

	/**
	 * Setter for <code>userdb.user_user.wechat_id</code>. 注册用户来自于哪个公众号, 0:默认为来自浏览器的用户
	 */
	public void setWechatId(Integer value) {
		setValue(18, value);
	}

	/**
	 * Getter for <code>userdb.user_user.wechat_id</code>. 注册用户来自于哪个公众号, 0:默认为来自浏览器的用户
	 */
	public Integer getWechatId() {
		return (Integer) getValue(18);
	}

	/**
	 * Setter for <code>userdb.user_user.unionid</code>. 存储仟寻服务号的unionid
	 */
	public void setUnionid(String value) {
		setValue(19, value);
	}

	/**
	 * Getter for <code>userdb.user_user.unionid</code>. 存储仟寻服务号的unionid
	 */
	public String getUnionid() {
		return (String) getValue(19);
	}

	/**
	 * Setter for <code>userdb.user_user.source</code>. 来源：0:手机注册 1:聚合号一键登录 2:企业号一键登录
	 */
	public void setSource(Byte value) {
		setValue(20, value);
	}

	/**
	 * Getter for <code>userdb.user_user.source</code>. 来源：0:手机注册 1:聚合号一键登录 2:企业号一键登录
	 */
	public Byte getSource() {
		return (Byte) getValue(20);
	}

	/**
	 * Setter for <code>userdb.user_user.company</code>. 点击我感兴趣时填写的公司
	 */
	public void setCompany(String value) {
		setValue(21, value);
	}

	/**
	 * Getter for <code>userdb.user_user.company</code>. 点击我感兴趣时填写的公司
	 */
	public String getCompany() {
		return (String) getValue(21);
	}

	/**
	 * Setter for <code>userdb.user_user.position</code>. 点击我感兴趣时填写的职位
	 */
	public void setPosition(String value) {
		setValue(22, value);
	}

	/**
	 * Getter for <code>userdb.user_user.position</code>. 点击我感兴趣时填写的职位
	 */
	public String getPosition() {
		return (String) getValue(22);
	}

	/**
	 * Setter for <code>userdb.user_user.parentid</code>. 合并到了新用户的id
	 */
	public void setParentid(UInteger value) {
		setValue(23, value);
	}

	/**
	 * Getter for <code>userdb.user_user.parentid</code>. 合并到了新用户的id
	 */
	public UInteger getParentid() {
		return (UInteger) getValue(23);
	}

	/**
	 * Setter for <code>userdb.user_user.nickname</code>. 用户昵称
	 */
	public void setNickname(String value) {
		setValue(24, value);
	}

	/**
	 * Getter for <code>userdb.user_user.nickname</code>. 用户昵称
	 */
	public String getNickname() {
		return (String) getValue(24);
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
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached UserUserRecord
	 */
	public UserUserRecord() {
		super(UserUser.USER_USER);
	}

	/**
	 * Create a detached, initialised UserUserRecord
	 */
	public UserUserRecord(UInteger id, String username, String password, Byte isDisable, Integer rank, Timestamp registerTime, String registerIp, Timestamp lastLoginTime, String lastLoginIp, Integer loginCount, Long mobile, String email, Byte activation, String activationCode, String token, String name, String headimg, Integer nationalCodeId, Integer wechatId, String unionid, Byte source, String company, String position, UInteger parentid, String nickname) {
		super(UserUser.USER_USER);

		setValue(0, id);
		setValue(1, username);
		setValue(2, password);
		setValue(3, isDisable);
		setValue(4, rank);
		setValue(5, registerTime);
		setValue(6, registerIp);
		setValue(7, lastLoginTime);
		setValue(8, lastLoginIp);
		setValue(9, loginCount);
		setValue(10, mobile);
		setValue(11, email);
		setValue(12, activation);
		setValue(13, activationCode);
		setValue(14, token);
		setValue(15, name);
		setValue(16, headimg);
		setValue(17, nationalCodeId);
		setValue(18, wechatId);
		setValue(19, unionid);
		setValue(20, source);
		setValue(21, company);
		setValue(22, position);
		setValue(23, parentid);
		setValue(24, nickname);
	}
}
