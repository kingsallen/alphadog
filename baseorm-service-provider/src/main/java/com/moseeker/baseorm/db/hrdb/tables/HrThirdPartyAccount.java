/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyAccountRecord;

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
 * 第三方渠道帐号
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrThirdPartyAccount extends TableImpl<HrThirdPartyAccountRecord> {

    private static final long serialVersionUID = 1155301868;

    /**
     * The reference instance of <code>hrdb.hr_third_party_account</code>
     */
    public static final HrThirdPartyAccount HR_THIRD_PARTY_ACCOUNT = new HrThirdPartyAccount();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrThirdPartyAccountRecord> getRecordType() {
        return HrThirdPartyAccountRecord.class;
    }

    /**
     * The column <code>hrdb.hr_third_party_account.id</code>. 编号
     */
    public final TableField<HrThirdPartyAccountRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "编号");

    /**
     * The column <code>hrdb.hr_third_party_account.channel</code>. 1=51job,2=猎聘,3=智联,4=linkedin
     */
    public final TableField<HrThirdPartyAccountRecord, Short> CHANNEL = createField("channel", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "1=51job,2=猎聘,3=智联,4=linkedin");

    /**
     * The column <code>hrdb.hr_third_party_account.username</code>. 帐号
     */
    public final TableField<HrThirdPartyAccountRecord, String> USERNAME = createField("username", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "帐号");

    /**
     * The column <code>hrdb.hr_third_party_account.password</code>. 密码
     */
    public final TableField<HrThirdPartyAccountRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "密码");

    /**
     * The column <code>hrdb.hr_third_party_account.binding</code>. 0：未绑定，1:绑定成功，2：绑定中，3：刷新中，4：用户名密码错误，5：绑定或刷新失败，6：绑定程序发生错误（前端和2状态一致），7：刷新程序发生错误（前端和3状态一致）8:绑定成功，正在获取信息，9：解绑状态
     */
    public final TableField<HrThirdPartyAccountRecord, Short> BINDING = createField("binding", org.jooq.impl.SQLDataType.SMALLINT, this, "0：未绑定，1:绑定成功，2：绑定中，3：刷新中，4：用户名密码错误，5：绑定或刷新失败，6：绑定程序发生错误（前端和2状态一致），7：刷新程序发生错误（前端和3状态一致）8:绑定成功，正在获取信息，9：解绑状态");

    /**
     * The column <code>hrdb.hr_third_party_account.company_id</code>. hrdb.hr_company.id
     */
    public final TableField<HrThirdPartyAccountRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hrdb.hr_company.id");

    /**
     * The column <code>hrdb.hr_third_party_account.remain_num</code>. 点数
     */
    public final TableField<HrThirdPartyAccountRecord, Integer> REMAIN_NUM = createField("remain_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "点数");

    /**
     * The column <code>hrdb.hr_third_party_account.sync_time</code>. 同步时间
     */
    public final TableField<HrThirdPartyAccountRecord, Timestamp> SYNC_TIME = createField("sync_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "同步时间");

    /**
     * The column <code>hrdb.hr_third_party_account.update_time</code>. 数据更新时间
     */
    public final TableField<HrThirdPartyAccountRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "数据更新时间");

    /**
     * The column <code>hrdb.hr_third_party_account.create_time</code>. 创建时间
     */
    public final TableField<HrThirdPartyAccountRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>hrdb.hr_third_party_account.remain_profile_num</code>. 第三方账号剩余简历数
     */
    public final TableField<HrThirdPartyAccountRecord, Integer> REMAIN_PROFILE_NUM = createField("remain_profile_num", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "第三方账号剩余简历数");

    /**
     * The column <code>hrdb.hr_third_party_account.error_message</code>. 同步刷新失败的理由
     */
    public final TableField<HrThirdPartyAccountRecord, String> ERROR_MESSAGE = createField("error_message", org.jooq.impl.SQLDataType.VARCHAR.length(500).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "同步刷新失败的理由");

    /**
     * The column <code>hrdb.hr_third_party_account.ext</code>. 扩展字段，以防在登录时需要除了账号密码以外的信息。一揽人才：安全码、
51job：
会员名称; 猎聘:用户在猎聘的userid; 58:用户在58的openId,accessToken,refreshToken的json串
     */
    public final TableField<HrThirdPartyAccountRecord, String> EXT = createField("ext", org.jooq.impl.SQLDataType.VARCHAR.length(500), this, "扩展字段，以防在登录时需要除了账号密码以外的信息。一揽人才：安全码、\r\n51job：\r\n会员名称; 猎聘:用户在猎聘的userid; 58:用户在58的openId,accessToken,refreshToken的json串");

    /**
     * The column <code>hrdb.hr_third_party_account.ext2</code>. 扩展字段2，猎聘：登录时存储返回的token; 58job：账号绑定时随机生成的key，用户获取账号绑定信息
     */
    public final TableField<HrThirdPartyAccountRecord, String> EXT2 = createField("ext2", org.jooq.impl.SQLDataType.VARCHAR.length(500).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "扩展字段2，猎聘：登录时存储返回的token; 58job：账号绑定时随机生成的key，用户获取账号绑定信息");

    /**
     * The column <code>hrdb.hr_third_party_account.sync_require_company</code>. 智联同步时页面是否需要选择公司名称，0 不需要，1 需要
     */
    public final TableField<HrThirdPartyAccountRecord, Byte> SYNC_REQUIRE_COMPANY = createField("sync_require_company", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "智联同步时页面是否需要选择公司名称，0 不需要，1 需要");

    /**
     * The column <code>hrdb.hr_third_party_account.sync_require_department</code>. 智联同步时页面是否需要选择部门名称，0 不需要，1 需要
     */
    public final TableField<HrThirdPartyAccountRecord, Byte> SYNC_REQUIRE_DEPARTMENT = createField("sync_require_department", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "智联同步时页面是否需要选择部门名称，0 不需要，1 需要");

    /**
     * Create a <code>hrdb.hr_third_party_account</code> table reference
     */
    public HrThirdPartyAccount() {
        this("hr_third_party_account", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_third_party_account</code> table reference
     */
    public HrThirdPartyAccount(String alias) {
        this(alias, HR_THIRD_PARTY_ACCOUNT);
    }

    private HrThirdPartyAccount(String alias, Table<HrThirdPartyAccountRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrThirdPartyAccount(String alias, Table<HrThirdPartyAccountRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "第三方渠道帐号");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Hrdb.HRDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<HrThirdPartyAccountRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_THIRD_PARTY_ACCOUNT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrThirdPartyAccountRecord> getPrimaryKey() {
        return Keys.KEY_HR_THIRD_PARTY_ACCOUNT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrThirdPartyAccountRecord>> getKeys() {
        return Arrays.<UniqueKey<HrThirdPartyAccountRecord>>asList(Keys.KEY_HR_THIRD_PARTY_ACCOUNT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrThirdPartyAccount as(String alias) {
        return new HrThirdPartyAccount(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrThirdPartyAccount rename(String name) {
        return new HrThirdPartyAccount(name, null);
    }
}
