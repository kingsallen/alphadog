/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables;


import com.moseeker.baseorm.db.userdb.Keys;
import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.records.UserHrAccountRecord;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * hr账号表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserHrAccount extends TableImpl<UserHrAccountRecord> {

    private static final long serialVersionUID = -481360502;

    /**
     * The reference instance of <code>userdb.user_hr_account</code>
     */
    public static final UserHrAccount USER_HR_ACCOUNT = new UserHrAccount();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserHrAccountRecord> getRecordType() {
        return UserHrAccountRecord.class;
    }

    /**
     * The column <code>userdb.user_hr_account.id</code>.
     */
    public final TableField<UserHrAccountRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>userdb.user_hr_account.company_id</code>. company.id
     */
    public final TableField<UserHrAccountRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "company.id");

    /**
     * The column <code>userdb.user_hr_account.mobile</code>. 手机号码
     */
    public final TableField<UserHrAccountRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "手机号码");

    /**
     * The column <code>userdb.user_hr_account.email</code>. 邮箱
     */
    public final TableField<UserHrAccountRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "邮箱");

    /**
     * The column <code>userdb.user_hr_account.wxuser_id</code>. 绑定的微信 账号
     */
    public final TableField<UserHrAccountRecord, Integer> WXUSER_ID = createField("wxuser_id", org.jooq.impl.SQLDataType.INTEGER, this, "绑定的微信 账号");

    /**
     * The column <code>userdb.user_hr_account.password</code>. 登录密码
     */
    public final TableField<UserHrAccountRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "登录密码");

    /**
     * The column <code>userdb.user_hr_account.username</code>. 企业联系人
     */
    public final TableField<UserHrAccountRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR.length(60).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "企业联系人");

    /**
     * The column <code>userdb.user_hr_account.account_type</code>. 0 超级账号；1：子账号; 2：普通账号
     */
    public final TableField<UserHrAccountRecord, Integer> ACCOUNT_TYPE = createField("account_type", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("2", org.jooq.impl.SQLDataType.INTEGER)), this, "0 超级账号；1：子账号; 2：普通账号");

    /**
     * The column <code>userdb.user_hr_account.activation</code>. 子账号邀请使用，账号是否激活，1：激活；0：未激活
     */
    public final TableField<UserHrAccountRecord, Byte> ACTIVATION = createField("activation", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.TINYINT)), this, "子账号邀请使用，账号是否激活，1：激活；0：未激活");

    /**
     * The column <code>userdb.user_hr_account.disable</code>. 1：可用账号；0禁用账号 ） 遵循数据库整体的设计习惯，1表示可用，0表示不可用
     */
    public final TableField<UserHrAccountRecord, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "1：可用账号；0禁用账号 ） 遵循数据库整体的设计习惯，1表示可用，0表示不可用");

    /**
     * The column <code>userdb.user_hr_account.register_time</code>. 注册时间
     */
    public final TableField<UserHrAccountRecord, Timestamp> REGISTER_TIME = createField("register_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "注册时间");

    /**
     * The column <code>userdb.user_hr_account.register_ip</code>. 注册时的IP地址
     */
    public final TableField<UserHrAccountRecord, String> REGISTER_IP = createField("register_ip", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "注册时的IP地址");

    /**
     * The column <code>userdb.user_hr_account.last_login_time</code>. 最后的登录时间
     */
    public final TableField<UserHrAccountRecord, Timestamp> LAST_LOGIN_TIME = createField("last_login_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "最后的登录时间");

    /**
     * The column <code>userdb.user_hr_account.last_login_ip</code>. 最后一次登录的IP
     */
    public final TableField<UserHrAccountRecord, String> LAST_LOGIN_IP = createField("last_login_ip", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "最后一次登录的IP");

    /**
     * The column <code>userdb.user_hr_account.login_count</code>. 登录次数
     */
    public final TableField<UserHrAccountRecord, Integer> LOGIN_COUNT = createField("login_count", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "登录次数");

    /**
     * The column <code>userdb.user_hr_account.source</code>. 来源1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 6:我也要招人(手机官网)
     */
    public final TableField<UserHrAccountRecord, Integer> SOURCE = createField("source", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "来源1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 6:我也要招人(手机官网)");

    /**
     * The column <code>userdb.user_hr_account.download_token</code>. 下载行业报告校验码
     */
    public final TableField<UserHrAccountRecord, String> DOWNLOAD_TOKEN = createField("download_token", org.jooq.impl.SQLDataType.VARCHAR.length(256), this, "下载行业报告校验码");

    /**
     * The column <code>userdb.user_hr_account.create_time</code>. 创建时间
     */
    public final TableField<UserHrAccountRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>userdb.user_hr_account.update_time</code>. 修改时间
     */
    public final TableField<UserHrAccountRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>userdb.user_hr_account.headimgurl</code>. 头像 url
     */
    public final TableField<UserHrAccountRecord, String> HEADIMGURL = createField("headimgurl", org.jooq.impl.SQLDataType.VARCHAR.length(256), this, "头像 url");

    /**
     * The column <code>userdb.user_hr_account.leave_to_mobot</code>. HR聊天是否托管给智能招聘助手，0 不托管，1 托管
     */
    public final TableField<UserHrAccountRecord, Byte> LEAVE_TO_MOBOT = createField("leave_to_mobot", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "HR聊天是否托管给智能招聘助手，0 不托管，1 托管");

    /**
     * The column <code>userdb.user_hr_account.remark_name</code>. 备注名，是由HR主账号设置的对外显示名称，暂时在微信端聊天时使用到，即C端用户看到的HR名称
     */
    public final TableField<UserHrAccountRecord, String> REMARK_NAME = createField("remark_name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "备注名，是由HR主账号设置的对外显示名称，暂时在微信端聊天时使用到，即C端用户看到的HR名称");

    /**
     * The column <code>userdb.user_hr_account.job_number</code>. 工号
     */
    public final TableField<UserHrAccountRecord, String> JOB_NUMBER = createField("job_number", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "工号");

    /**
     * The column <code>userdb.user_hr_account.deparment_id</code>. 部门id
     */
    public final TableField<UserHrAccountRecord, Integer> DEPARMENT_ID = createField("deparment_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "部门id");

    /**
     * The column <code>userdb.user_hr_account.managed_department_id</code>. 负责部门id
     */
    public final TableField<UserHrAccountRecord, String> MANAGED_DEPARTMENT_ID = createField("managed_department_id", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "负责部门id");

    /**
     * Create a <code>userdb.user_hr_account</code> table reference
     */
    public UserHrAccount() {
        this("user_hr_account", null);
    }

    /**
     * Create an aliased <code>userdb.user_hr_account</code> table reference
     */
    public UserHrAccount(String alias) {
        this(alias, USER_HR_ACCOUNT);
    }

    private UserHrAccount(String alias, Table<UserHrAccountRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserHrAccount(String alias, Table<UserHrAccountRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "hr账号表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Userdb.USERDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserHrAccountRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_HR_ACCOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserHrAccountRecord> getPrimaryKey() {
        return Keys.KEY_USER_HR_ACCOUNT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserHrAccountRecord>> getKeys() {
        return Arrays.<UniqueKey<UserHrAccountRecord>>asList(Keys.KEY_USER_HR_ACCOUNT_PRIMARY, Keys.KEY_USER_HR_ACCOUNT_WXUSER_ID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserHrAccount as(String alias) {
        return new UserHrAccount(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserHrAccount rename(String name) {
        return new UserHrAccount(name, null);
    }
}
