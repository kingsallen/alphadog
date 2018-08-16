/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserUser;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 用户表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserUserRecord extends UpdatableRecordImpl<UserUserRecord> {

    private static final long serialVersionUID = 1025138512;

    /**
     * Setter for <code>userdb.user_user.id</code>. 主key
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_user.id</code>. 主key
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>userdb.user_user.country_code</code>. 国家代码，用于支持国际短信
     */
    public void setCountryCode(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_user.country_code</code>. 国家代码，用于支持国际短信
     */
    public String getCountryCode() {
        return (String) get(1);
    }

    /**
     * Setter for <code>userdb.user_user.username</code>. 用户名，目前存放已验证手机号或者unionid
     */
    public void setUsername(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_user.username</code>. 用户名，目前存放已验证手机号或者unionid
     */
    public String getUsername() {
        return (String) get(2);
    }

    /**
     * Setter for <code>userdb.user_user.password</code>. 密码
     */
    public void setPassword(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>userdb.user_user.password</code>. 密码
     */
    public String getPassword() {
        return (String) get(3);
    }

    /**
     * Setter for <code>userdb.user_user.is_disable</code>. 是否禁用，0：可用，1：禁用
     */
    public void setIsDisable(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>userdb.user_user.is_disable</code>. 是否禁用，0：可用，1：禁用
     */
    public Byte getIsDisable() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>userdb.user_user.rank</code>. 用户等级
     */
    public void setRank(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>userdb.user_user.rank</code>. 用户等级
     */
    public Integer getRank() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>userdb.user_user.register_time</code>. 注册时间
     */
    public void setRegisterTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>userdb.user_user.register_time</code>. 注册时间
     */
    public Timestamp getRegisterTime() {
        return (Timestamp) get(6);
    }

    /**
     * Setter for <code>userdb.user_user.register_ip</code>. 注册IP
     */
    public void setRegisterIp(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>userdb.user_user.register_ip</code>. 注册IP
     */
    public String getRegisterIp() {
        return (String) get(7);
    }

    /**
     * Setter for <code>userdb.user_user.last_login_time</code>. 最近登录时间
     */
    public void setLastLoginTime(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>userdb.user_user.last_login_time</code>. 最近登录时间
     */
    public Timestamp getLastLoginTime() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>userdb.user_user.last_login_ip</code>. 最近登录IP
     */
    public void setLastLoginIp(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>userdb.user_user.last_login_ip</code>. 最近登录IP
     */
    public String getLastLoginIp() {
        return (String) get(9);
    }

    /**
     * Setter for <code>userdb.user_user.login_count</code>. 登录次数
     */
    public void setLoginCount(Integer value) {
        set(10, value);
    }

    /**
     * Getter for <code>userdb.user_user.login_count</code>. 登录次数
     */
    public Integer getLoginCount() {
        return (Integer) get(10);
    }

    /**
     * Setter for <code>userdb.user_user.mobile</code>. 手机号(未验证)
     */
    public void setMobile(Long value) {
        set(11, value);
    }

    /**
     * Getter for <code>userdb.user_user.mobile</code>. 手机号(未验证)
     */
    public Long getMobile() {
        return (Long) get(11);
    }

    /**
     * Setter for <code>userdb.user_user.email</code>. user pass email registe
     */
    public void setEmail(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>userdb.user_user.email</code>. user pass email registe
     */
    public String getEmail() {
        return (String) get(12);
    }

    /**
     * Setter for <code>userdb.user_user.activation</code>. is not activation 0:no 1:yes
     */
    public void setActivation(Byte value) {
        set(13, value);
    }

    /**
     * Getter for <code>userdb.user_user.activation</code>. is not activation 0:no 1:yes
     */
    public Byte getActivation() {
        return (Byte) get(13);
    }

    /**
     * Setter for <code>userdb.user_user.activation_code</code>. activation code
     */
    public void setActivationCode(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>userdb.user_user.activation_code</code>. activation code
     */
    public String getActivationCode() {
        return (String) get(14);
    }

    /**
     * Setter for <code>userdb.user_user.token</code>.
     */
    public void setToken(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>userdb.user_user.token</code>.
     */
    public String getToken() {
        return (String) get(15);
    }

    /**
     * Setter for <code>userdb.user_user.name</code>. 真实姓名
     */
    public void setName(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>userdb.user_user.name</code>. 真实姓名
     */
    public String getName() {
        return (String) get(16);
    }

    /**
     * Setter for <code>userdb.user_user.headimg</code>. 头像
     */
    public void setHeadimg(String value) {
        set(17, value);
    }

    /**
     * Getter for <code>userdb.user_user.headimg</code>. 头像
     */
    public String getHeadimg() {
        return (String) get(17);
    }

    /**
     * Setter for <code>userdb.user_user.national_code_id</code>. 国际电话区号ID
     */
    public void setNationalCodeId(Integer value) {
        set(18, value);
    }

    /**
     * Getter for <code>userdb.user_user.national_code_id</code>. 国际电话区号ID
     */
    public Integer getNationalCodeId() {
        return (Integer) get(18);
    }

    /**
     * Setter for <code>userdb.user_user.wechat_id</code>. 注册用户来自于哪个公众号, 0:默认为来自浏览器的用户
     */
    public void setWechatId(Integer value) {
        set(19, value);
    }

    /**
     * Getter for <code>userdb.user_user.wechat_id</code>. 注册用户来自于哪个公众号, 0:默认为来自浏览器的用户
     */
    public Integer getWechatId() {
        return (Integer) get(19);
    }

    /**
     * Setter for <code>userdb.user_user.unionid</code>. 存储仟寻服务号的unionid
     */
    public void setUnionid(String value) {
        set(20, value);
    }

    /**
     * Getter for <code>userdb.user_user.unionid</code>. 存储仟寻服务号的unionid
     */
    public String getUnionid() {
        return (String) get(20);
    }

    /**
     * Setter for <code>userdb.user_user.source</code>. 来源：0:手机注册 1:聚合号一键登录 2:企业号一键登录 3:微信端百度 oauth 7:PC(正常添加) 8:PC(我要投递) 9:PC(我感兴趣) 10:PC(微信扫描后手机注册) 100:简历回收自动创建, 101支付宝, 103 程序导入(和黄)
     */
    public void setSource(Short value) {
        set(21, value);
    }

    /**
     * Getter for <code>userdb.user_user.source</code>. 来源：0:手机注册 1:聚合号一键登录 2:企业号一键登录 3:微信端百度 oauth 7:PC(正常添加) 8:PC(我要投递) 9:PC(我感兴趣) 10:PC(微信扫描后手机注册) 100:简历回收自动创建, 101支付宝, 103 程序导入(和黄)
     */
    public Short getSource() {
        return (Short) get(21);
    }

    /**
     * Setter for <code>userdb.user_user.company</code>. 点击我感兴趣时填写的公司
     */
    public void setCompany(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>userdb.user_user.company</code>. 点击我感兴趣时填写的公司
     */
    public String getCompany() {
        return (String) get(22);
    }

    /**
     * Setter for <code>userdb.user_user.position</code>. 点击我感兴趣时填写的职位
     */
    public void setPosition(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>userdb.user_user.position</code>. 点击我感兴趣时填写的职位
     */
    public String getPosition() {
        return (String) get(23);
    }

    /**
     * Setter for <code>userdb.user_user.parentid</code>. 合并到了新用户的id
     */
    public void setParentid(Integer value) {
        set(24, value);
    }

    /**
     * Getter for <code>userdb.user_user.parentid</code>. 合并到了新用户的id
     */
    public Integer getParentid() {
        return (Integer) get(24);
    }

    /**
     * Setter for <code>userdb.user_user.nickname</code>. 用户昵称
     */
    public void setNickname(String value) {
        set(25, value);
    }

    /**
     * Getter for <code>userdb.user_user.nickname</code>. 用户昵称
     */
    public String getNickname() {
        return (String) get(25);
    }

    /**
     * Setter for <code>userdb.user_user.email_verified</code>. 邮箱是否认证 2:老数据 1:已认证 0:未认证
     */
    public void setEmailVerified(Byte value) {
        set(26, value);
    }

    /**
     * Getter for <code>userdb.user_user.email_verified</code>. 邮箱是否认证 2:老数据 1:已认证 0:未认证
     */
    public Byte getEmailVerified() {
        return (Byte) get(26);
    }

    /**
     * Setter for <code>userdb.user_user.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(27, value);
    }

    /**
     * Getter for <code>userdb.user_user.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(27);
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
    public UserUserRecord(Integer id, String countryCode, String username, String password, Byte isDisable, Integer rank, Timestamp registerTime, String registerIp, Timestamp lastLoginTime, String lastLoginIp, Integer loginCount, Long mobile, String email, Byte activation, String activationCode, String token, String name, String headimg, Integer nationalCodeId, Integer wechatId, String unionid, Short source, String company, String position, Integer parentid, String nickname, Byte emailVerified, Timestamp updateTime) {
        super(UserUser.USER_USER);

        set(0, id);
        set(1, countryCode);
        set(2, username);
        set(3, password);
        set(4, isDisable);
        set(5, rank);
        set(6, registerTime);
        set(7, registerIp);
        set(8, lastLoginTime);
        set(9, lastLoginIp);
        set(10, loginCount);
        set(11, mobile);
        set(12, email);
        set(13, activation);
        set(14, activationCode);
        set(15, token);
        set(16, name);
        set(17, headimg);
        set(18, nationalCodeId);
        set(19, wechatId);
        set(20, unionid);
        set(21, source);
        set(22, company);
        set(23, position);
        set(24, parentid);
        set(25, nickname);
        set(26, emailVerified);
        set(27, updateTime);
    }
}
