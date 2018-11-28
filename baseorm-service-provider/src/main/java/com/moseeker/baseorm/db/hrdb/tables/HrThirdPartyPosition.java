/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyPositionRecord;

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
 * 第三方渠道同步的职位
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrThirdPartyPosition extends TableImpl<HrThirdPartyPositionRecord> {

    private static final long serialVersionUID = 726579103;

    /**
     * The reference instance of <code>hrdb.hr_third_party_position</code>
     */
    public static final HrThirdPartyPosition HR_THIRD_PARTY_POSITION = new HrThirdPartyPosition();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrThirdPartyPositionRecord> getRecordType() {
        return HrThirdPartyPositionRecord.class;
    }

    /**
     * The column <code>hrdb.hr_third_party_position.id</code>.
     */
    public final TableField<HrThirdPartyPositionRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>hrdb.hr_third_party_position.position_id</code>. jobdb.job_position.id
     */
    public final TableField<HrThirdPartyPositionRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "jobdb.job_position.id");

    /**
     * The column <code>hrdb.hr_third_party_position.third_part_position_id</code>. 第三方渠道编号
     */
    public final TableField<HrThirdPartyPositionRecord, String> THIRD_PART_POSITION_ID = createField("third_part_position_id", org.jooq.impl.SQLDataType.VARCHAR.length(200).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "第三方渠道编号");

    /**
     * The column <code>hrdb.hr_third_party_position.channel</code>. 1=51job,2=猎聘,3=智联,4=linkedin
     */
    public final TableField<HrThirdPartyPositionRecord, Short> CHANNEL = createField("channel", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "1=51job,2=猎聘,3=智联,4=linkedin");

    /**
     * The column <code>hrdb.hr_third_party_position.is_synchronization</code>. 是否同步:0=未同步,1=同步,2=同步中，3=同步失败，4=同步错误(发生不能给用户显示的错误)
     */
    public final TableField<HrThirdPartyPositionRecord, Short> IS_SYNCHRONIZATION = createField("is_synchronization", org.jooq.impl.SQLDataType.SMALLINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "是否同步:0=未同步,1=同步,2=同步中，3=同步失败，4=同步错误(发生不能给用户显示的错误)");

    /**
     * The column <code>hrdb.hr_third_party_position.is_refresh</code>. 是否刷新:0=未刷新,1=刷新,2=刷新中
     */
    public final TableField<HrThirdPartyPositionRecord, Short> IS_REFRESH = createField("is_refresh", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "是否刷新:0=未刷新,1=刷新,2=刷新中");

    /**
     * The column <code>hrdb.hr_third_party_position.sync_time</code>. 职位同步时间
     */
    public final TableField<HrThirdPartyPositionRecord, Timestamp> SYNC_TIME = createField("sync_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "职位同步时间");

    /**
     * The column <code>hrdb.hr_third_party_position.refresh_time</code>. 职位刷新时间
     */
    public final TableField<HrThirdPartyPositionRecord, Timestamp> REFRESH_TIME = createField("refresh_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "职位刷新时间");

    /**
     * The column <code>hrdb.hr_third_party_position.update_time</code>. 数据更新时间
     */
    public final TableField<HrThirdPartyPositionRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "数据更新时间");

    /**
     * The column <code>hrdb.hr_third_party_position.address</code>. 详细地址
     */
    public final TableField<HrThirdPartyPositionRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "详细地址");

    /**
     * The column <code>hrdb.hr_third_party_position.occupation</code>. 同步时选中的第三方职位职能,智联，猎聘，51用逗号分隔，代表多个职能
     */
    public final TableField<HrThirdPartyPositionRecord, String> OCCUPATION = createField("occupation", org.jooq.impl.SQLDataType.VARCHAR.length(200), this, "同步时选中的第三方职位职能,智联，猎聘，51用逗号分隔，代表多个职能");

    /**
     * The column <code>hrdb.hr_third_party_position.sync_fail_reason</code>. 失败原因
     */
    public final TableField<HrThirdPartyPositionRecord, String> SYNC_FAIL_REASON = createField("sync_fail_reason", org.jooq.impl.SQLDataType.VARCHAR.length(1000), this, "失败原因");

    /**
     * The column <code>hrdb.hr_third_party_position.use_company_address</code>. 使用企业地址
     */
    public final TableField<HrThirdPartyPositionRecord, Short> USE_COMPANY_ADDRESS = createField("use_company_address", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "使用企业地址");

    /**
     * The column <code>hrdb.hr_third_party_position.third_party_account_id</code>. 第三方账号ID
     */
    public final TableField<HrThirdPartyPositionRecord, Integer> THIRD_PARTY_ACCOUNT_ID = createField("third_party_account_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "第三方账号ID");

    /**
     * The column <code>hrdb.hr_third_party_position.department</code>. 同步时自定义或者匹配的部门名
     */
    public final TableField<HrThirdPartyPositionRecord, String> DEPARTMENT = createField("department", org.jooq.impl.SQLDataType.VARCHAR.length(100).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "同步时自定义或者匹配的部门名");

    /**
     * The column <code>hrdb.hr_third_party_position.salary_month</code>. 发放月薪数
     */
    public final TableField<HrThirdPartyPositionRecord, Integer> SALARY_MONTH = createField("salary_month", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "发放月薪数");

    /**
     * The column <code>hrdb.hr_third_party_position.feedback_period</code>. 招聘反馈时长
     */
    public final TableField<HrThirdPartyPositionRecord, Integer> FEEDBACK_PERIOD = createField("feedback_period", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "招聘反馈时长");

    /**
     * The column <code>hrdb.hr_third_party_position.salary_discuss</code>. 是否显示为面议0否1是
     */
    public final TableField<HrThirdPartyPositionRecord, Short> SALARY_DISCUSS = createField("salary_discuss", org.jooq.impl.SQLDataType.SMALLINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.SMALLINT)), this, "是否显示为面议0否1是");

    /**
     * The column <code>hrdb.hr_third_party_position.salary_bottom</code>. 薪资底线
     */
    public final TableField<HrThirdPartyPositionRecord, Integer> SALARY_BOTTOM = createField("salary_bottom", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "薪资底线");

    /**
     * The column <code>hrdb.hr_third_party_position.salary_top</code>. 薪资封顶
     */
    public final TableField<HrThirdPartyPositionRecord, Integer> SALARY_TOP = createField("salary_top", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "薪资封顶");

    /**
     * The column <code>hrdb.hr_third_party_position.practice_salary</code>. 实习薪资，完整薪资
     */
    public final TableField<HrThirdPartyPositionRecord, Integer> PRACTICE_SALARY = createField("practice_salary", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "实习薪资，完整薪资");

    /**
     * The column <code>hrdb.hr_third_party_position.practice_per_week</code>. 每周实习天数
     */
    public final TableField<HrThirdPartyPositionRecord, Byte> PRACTICE_PER_WEEK = createField("practice_per_week", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "每周实习天数");

    /**
     * The column <code>hrdb.hr_third_party_position.practice_salary_unit</code>. 实习薪资单位，0：元/月，1：元/天
     */
    public final TableField<HrThirdPartyPositionRecord, Byte> PRACTICE_SALARY_UNIT = createField("practice_salary_unit", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "实习薪资单位，0：元/月，1：元/天");

    /**
     * The column <code>hrdb.hr_third_party_position.company_id</code>. 绑定第三方账号对应公司的id，关联thirdparty_account_city的id
     */
    public final TableField<HrThirdPartyPositionRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER, this, "绑定第三方账号对应公司的id，关联thirdparty_account_city的id");

    /**
     * The column <code>hrdb.hr_third_party_position.address_id</code>. 绑定第三方账号对应地址的id，关联thirdparty_account_company_address的id
     */
    public final TableField<HrThirdPartyPositionRecord, Integer> ADDRESS_ID = createField("address_id", org.jooq.impl.SQLDataType.INTEGER, this, "绑定第三方账号对应地址的id，关联thirdparty_account_company_address的id");

    /**
     * The column <code>hrdb.hr_third_party_position.department_id</code>. 绑定第三方账号对应部门的id，关联thirdparty_account_department的id
     */
    public final TableField<HrThirdPartyPositionRecord, Integer> DEPARTMENT_ID = createField("department_id", org.jooq.impl.SQLDataType.INTEGER, this, "绑定第三方账号对应部门的id，关联thirdparty_account_department的id");

    /**
     * The column <code>hrdb.hr_third_party_position.company_name</code>. 绑定第三方账号对应公司的名称
     */
    public final TableField<HrThirdPartyPositionRecord, String> COMPANY_NAME = createField("company_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "绑定第三方账号对应公司的名称");

    /**
     * The column <code>hrdb.hr_third_party_position.address_name</code>. 绑定第三方账号对应地址的名称
     */
    public final TableField<HrThirdPartyPositionRecord, String> ADDRESS_NAME = createField("address_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "绑定第三方账号对应地址的名称");

    /**
     * The column <code>hrdb.hr_third_party_position.department_name</code>. 绑定第三方账号对应部门的名称
     */
    public final TableField<HrThirdPartyPositionRecord, String> DEPARTMENT_NAME = createField("department_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "绑定第三方账号对应部门的名称");

    /**
     * The column <code>hrdb.hr_third_party_position.count</code>. 招聘人数
     */
    public final TableField<HrThirdPartyPositionRecord, Integer> COUNT = createField("count", org.jooq.impl.SQLDataType.INTEGER, this, "招聘人数");

    /**
     * The column <code>hrdb.hr_third_party_position.feature</code>.
     */
    public final TableField<HrThirdPartyPositionRecord, String> FEATURE = createField("feature", org.jooq.impl.SQLDataType.VARCHAR.length(150).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>hrdb.hr_third_party_position.internship</code>. 是否使用实习职位的额度,0：不使用，1：使用
     */
    public final TableField<HrThirdPartyPositionRecord, Byte> INTERNSHIP = createField("internship", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否使用实习职位的额度,0：不使用，1：使用");

    /**
     * Create a <code>hrdb.hr_third_party_position</code> table reference
     */
    public HrThirdPartyPosition() {
        this("hr_third_party_position", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_third_party_position</code> table reference
     */
    public HrThirdPartyPosition(String alias) {
        this(alias, HR_THIRD_PARTY_POSITION);
    }

    private HrThirdPartyPosition(String alias, Table<HrThirdPartyPositionRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrThirdPartyPosition(String alias, Table<HrThirdPartyPositionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "第三方渠道同步的职位");
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
    public Identity<HrThirdPartyPositionRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_THIRD_PARTY_POSITION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrThirdPartyPositionRecord> getPrimaryKey() {
        return Keys.KEY_HR_THIRD_PARTY_POSITION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrThirdPartyPositionRecord>> getKeys() {
        return Arrays.<UniqueKey<HrThirdPartyPositionRecord>>asList(Keys.KEY_HR_THIRD_PARTY_POSITION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrThirdPartyPosition as(String alias) {
        return new HrThirdPartyPosition(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrThirdPartyPosition rename(String name) {
        return new HrThirdPartyPosition(name, null);
    }
}
