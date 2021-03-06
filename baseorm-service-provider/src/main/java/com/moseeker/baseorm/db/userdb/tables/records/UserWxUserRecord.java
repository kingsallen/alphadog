/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserWxUser;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record21;
import org.jooq.Row21;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 微信用户表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserWxUserRecord extends UpdatableRecordImpl<UserWxUserRecord> implements Record21<Long, Integer, Integer, Integer, Byte, String, String, Integer, String, String, String, String, String, Timestamp, Timestamp, String, Integer, Byte, Timestamp, Timestamp, Byte> {

    private static final long serialVersionUID = 248897610;

    /**
     * Setter for <code>userdb.user_wx_user.id</code>. 主key
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.id</code>. 主key
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>userdb.user_wx_user.wechat_id</code>. 所属公众号
     */
    public void setWechatId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.wechat_id</code>. 所属公众号
     */
    public Integer getWechatId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>userdb.user_wx_user.group_id</code>. 分组ID
     */
    public void setGroupId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.group_id</code>. 分组ID
     */
    public Integer getGroupId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>userdb.user_wx_user.sysuser_id</code>. user_user.id, C端用户ID
     */
    public void setSysuserId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.sysuser_id</code>. user_user.id, C端用户ID
     */
    public Integer getSysuserId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>userdb.user_wx_user.is_subscribe</code>. 是否关注 1:关注 0：没关注
     */
    public void setIsSubscribe(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.is_subscribe</code>. 是否关注 1:关注 0：没关注
     */
    public Byte getIsSubscribe() {
        return (Byte) get(4);
    }

    /**
     * Setter for <code>userdb.user_wx_user.openid</code>. 用户标示
     */
    public void setOpenid(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.openid</code>. 用户标示
     */
    public String getOpenid() {
        return (String) get(5);
    }

    /**
     * Setter for <code>userdb.user_wx_user.nickname</code>. 用户昵称
     */
    public void setNickname(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.nickname</code>. 用户昵称
     */
    public String getNickname() {
        return (String) get(6);
    }

    /**
     * Setter for <code>userdb.user_wx_user.sex</code>. 用户性别 0:未知 1:男性 2:女性
     */
    public void setSex(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.sex</code>. 用户性别 0:未知 1:男性 2:女性
     */
    public Integer getSex() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>userdb.user_wx_user.city</code>. 用户所在城市
     */
    public void setCity(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.city</code>. 用户所在城市
     */
    public String getCity() {
        return (String) get(8);
    }

    /**
     * Setter for <code>userdb.user_wx_user.country</code>. 用户所在国家
     */
    public void setCountry(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.country</code>. 用户所在国家
     */
    public String getCountry() {
        return (String) get(9);
    }

    /**
     * Setter for <code>userdb.user_wx_user.province</code>. 用户所在省份
     */
    public void setProvince(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.province</code>. 用户所在省份
     */
    public String getProvince() {
        return (String) get(10);
    }

    /**
     * Setter for <code>userdb.user_wx_user.language</code>. 用户语言
     */
    public void setLanguage(String value) {
        set(11, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.language</code>. 用户语言
     */
    public String getLanguage() {
        return (String) get(11);
    }

    /**
     * Setter for <code>userdb.user_wx_user.headimgurl</code>. 用户头像
     */
    public void setHeadimgurl(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.headimgurl</code>. 用户头像
     */
    public String getHeadimgurl() {
        return (String) get(12);
    }

    /**
     * Setter for <code>userdb.user_wx_user.subscribe_time</code>. 用户关注时间
     */
    public void setSubscribeTime(Timestamp value) {
        set(13, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.subscribe_time</code>. 用户关注时间
     */
    public Timestamp getSubscribeTime() {
        return (Timestamp) get(13);
    }

    /**
     * Setter for <code>userdb.user_wx_user.unsubscibe_time</code>.
     */
    public void setUnsubscibeTime(Timestamp value) {
        set(14, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.unsubscibe_time</code>.
     */
    public Timestamp getUnsubscibeTime() {
        return (Timestamp) get(14);
    }

    /**
     * Setter for <code>userdb.user_wx_user.unionid</code>. UnionID
     */
    public void setUnionid(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.unionid</code>. UnionID
     */
    public String getUnionid() {
        return (String) get(15);
    }

    /**
     * Setter for <code>userdb.user_wx_user.reward</code>. 积分奖励，暂时不用
     */
    public void setReward(Integer value) {
        set(16, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.reward</code>. 积分奖励，暂时不用
     */
    public Integer getReward() {
        return (Integer) get(16);
    }

    /**
     * Setter for <code>userdb.user_wx_user.auto_sync_info</code>. 0：需要处理，1：处理过了
     */
    public void setAutoSyncInfo(Byte value) {
        set(17, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.auto_sync_info</code>. 0：需要处理，1：处理过了
     */
    public Byte getAutoSyncInfo() {
        return (Byte) get(17);
    }

    /**
     * Setter for <code>userdb.user_wx_user.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(18, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(18);
    }

    /**
     * Setter for <code>userdb.user_wx_user.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(19, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(19);
    }

    /**
     * Setter for <code>userdb.user_wx_user.source</code>. insert来源 1:SUBSCRIBED 2:UNSUBSCRIBED 3:订阅号调用api的48001 4:oauth 5:update all 6:update short 7:oauth update 8:微信扫码注册 9:upd unionid 10:upd sysuser, 11:ups sysnuser 12：微信端我也要招人注册
     */
    public void setSource(Byte value) {
        set(20, value);
    }

    /**
     * Getter for <code>userdb.user_wx_user.source</code>. insert来源 1:SUBSCRIBED 2:UNSUBSCRIBED 3:订阅号调用api的48001 4:oauth 5:update all 6:update short 7:oauth update 8:微信扫码注册 9:upd unionid 10:upd sysuser, 11:ups sysnuser 12：微信端我也要招人注册
     */
    public Byte getSource() {
        return (Byte) get(20);
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
    // Record21 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row21<Long, Integer, Integer, Integer, Byte, String, String, Integer, String, String, String, String, String, Timestamp, Timestamp, String, Integer, Byte, Timestamp, Timestamp, Byte> fieldsRow() {
        return (Row21) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row21<Long, Integer, Integer, Integer, Byte, String, String, Integer, String, String, String, String, String, Timestamp, Timestamp, String, Integer, Byte, Timestamp, Timestamp, Byte> valuesRow() {
        return (Row21) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field1() {
        return UserWxUser.USER_WX_USER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return UserWxUser.USER_WX_USER.WECHAT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return UserWxUser.USER_WX_USER.GROUP_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return UserWxUser.USER_WX_USER.SYSUSER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field5() {
        return UserWxUser.USER_WX_USER.IS_SUBSCRIBE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return UserWxUser.USER_WX_USER.OPENID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return UserWxUser.USER_WX_USER.NICKNAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field8() {
        return UserWxUser.USER_WX_USER.SEX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field9() {
        return UserWxUser.USER_WX_USER.CITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field10() {
        return UserWxUser.USER_WX_USER.COUNTRY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field11() {
        return UserWxUser.USER_WX_USER.PROVINCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field12() {
        return UserWxUser.USER_WX_USER.LANGUAGE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field13() {
        return UserWxUser.USER_WX_USER.HEADIMGURL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field14() {
        return UserWxUser.USER_WX_USER.SUBSCRIBE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field15() {
        return UserWxUser.USER_WX_USER.UNSUBSCIBE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field16() {
        return UserWxUser.USER_WX_USER.UNIONID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field17() {
        return UserWxUser.USER_WX_USER.REWARD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field18() {
        return UserWxUser.USER_WX_USER.AUTO_SYNC_INFO;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field19() {
        return UserWxUser.USER_WX_USER.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field20() {
        return UserWxUser.USER_WX_USER.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field21() {
        return UserWxUser.USER_WX_USER.SOURCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getWechatId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getGroupId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getSysuserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value5() {
        return getIsSubscribe();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getOpenid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getNickname();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value8() {
        return getSex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value9() {
        return getCity();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value10() {
        return getCountry();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value11() {
        return getProvince();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value12() {
        return getLanguage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value13() {
        return getHeadimgurl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value14() {
        return getSubscribeTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value15() {
        return getUnsubscibeTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value16() {
        return getUnionid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value17() {
        return getReward();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value18() {
        return getAutoSyncInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value19() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value20() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value21() {
        return getSource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value1(Long value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value2(Integer value) {
        setWechatId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value3(Integer value) {
        setGroupId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value4(Integer value) {
        setSysuserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value5(Byte value) {
        setIsSubscribe(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value6(String value) {
        setOpenid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value7(String value) {
        setNickname(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value8(Integer value) {
        setSex(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value9(String value) {
        setCity(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value10(String value) {
        setCountry(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value11(String value) {
        setProvince(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value12(String value) {
        setLanguage(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value13(String value) {
        setHeadimgurl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value14(Timestamp value) {
        setSubscribeTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value15(Timestamp value) {
        setUnsubscibeTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value16(String value) {
        setUnionid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value17(Integer value) {
        setReward(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value18(Byte value) {
        setAutoSyncInfo(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value19(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value20(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord value21(Byte value) {
        setSource(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserWxUserRecord values(Long value1, Integer value2, Integer value3, Integer value4, Byte value5, String value6, String value7, Integer value8, String value9, String value10, String value11, String value12, String value13, Timestamp value14, Timestamp value15, String value16, Integer value17, Byte value18, Timestamp value19, Timestamp value20, Byte value21) {
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
        value14(value14);
        value15(value15);
        value16(value16);
        value17(value17);
        value18(value18);
        value19(value19);
        value20(value20);
        value21(value21);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserWxUserRecord
     */
    public UserWxUserRecord() {
        super(UserWxUser.USER_WX_USER);
    }

    /**
     * Create a detached, initialised UserWxUserRecord
     */
    public UserWxUserRecord(Long id, Integer wechatId, Integer groupId, Integer sysuserId, Byte isSubscribe, String openid, String nickname, Integer sex, String city, String country, String province, String language, String headimgurl, Timestamp subscribeTime, Timestamp unsubscibeTime, String unionid, Integer reward, Byte autoSyncInfo, Timestamp createTime, Timestamp updateTime, Byte source) {
        super(UserWxUser.USER_WX_USER);

        set(0, id);
        set(1, wechatId);
        set(2, groupId);
        set(3, sysuserId);
        set(4, isSubscribe);
        set(5, openid);
        set(6, nickname);
        set(7, sex);
        set(8, city);
        set(9, country);
        set(10, province);
        set(11, language);
        set(12, headimgurl);
        set(13, subscribeTime);
        set(14, unsubscibeTime);
        set(15, unionid);
        set(16, reward);
        set(17, autoSyncInfo);
        set(18, createTime);
        set(19, updateTime);
        set(20, source);
    }
}
