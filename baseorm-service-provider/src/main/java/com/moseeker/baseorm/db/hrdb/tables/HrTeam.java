/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTeamRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


/**
 * 团队信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrTeam extends TableImpl<HrTeamRecord> {

    private static final long serialVersionUID = 1409161363;

    /**
     * The reference instance of <code>hrdb.hr_team</code>
     */
    public static final HrTeam HR_TEAM = new HrTeam();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrTeamRecord> getRecordType() {
        return HrTeamRecord.class;
    }

    /**
     * The column <code>hrdb.hr_team.id</code>.
     */
    public final TableField<HrTeamRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>hrdb.hr_team.name</code>. 团队/部门名称
     */
    public final TableField<HrTeamRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "团队/部门名称");

    /**
     * The column <code>hrdb.hr_team.summary</code>. 职能概述
     */
    public final TableField<HrTeamRecord, String> SUMMARY = createField("summary", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "职能概述");

    /**
     * The column <code>hrdb.hr_team.description</code>. 团队介绍
     */
    public final TableField<HrTeamRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR.length(2048).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "团队介绍");

    /**
     * The column <code>hrdb.hr_team.show_order</code>. 团队显示顺序
     */
    public final TableField<HrTeamRecord, Integer> SHOW_ORDER = createField("show_order", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "团队显示顺序");

    /**
     * The column <code>hrdb.hr_team.jd_media</code>. 成员一天信息hr_media.id: [1, 23, 32]
     */
    public final TableField<HrTeamRecord, String> JD_MEDIA = createField("jd_media", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaultValue(org.jooq.impl.DSL.field("[]", org.jooq.impl.SQLDataType.VARCHAR)), this, "成员一天信息hr_media.id: [1, 23, 32]");

    /**
     * The column <code>hrdb.hr_team.company_id</code>. 团队所在母公司
     */
    public final TableField<HrTeamRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "团队所在母公司");

    /**
     * The column <code>hrdb.hr_team.create_time</code>. 创建时间
     */
    public final TableField<HrTeamRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>hrdb.hr_team.update_time</code>. 更新时间
     */
    public final TableField<HrTeamRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * The column <code>hrdb.hr_team.is_show</code>. 当前团队在列表等处是否显示, 0:不显示, 1:显示
     */
    public final TableField<HrTeamRecord, Integer> IS_SHOW = createField("is_show", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.INTEGER)), this, "当前团队在列表等处是否显示, 0:不显示, 1:显示");

    /**
     * The column <code>hrdb.hr_team.slogan</code>. 团队标语
     */
    public final TableField<HrTeamRecord, String> SLOGAN = createField("slogan", org.jooq.impl.SQLDataType.VARCHAR.length(1024).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "团队标语");

    /**
     * The column <code>hrdb.hr_team.res_id</code>. 团队主图片hr_resource.id
     */
    public final TableField<HrTeamRecord, Integer> RES_ID = createField("res_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "团队主图片hr_resource.id");

    /**
     * The column <code>hrdb.hr_team.team_detail</code>. 团队详情页配置hr_media.id: [1, 23, 32]
     */
    public final TableField<HrTeamRecord, String> TEAM_DETAIL = createField("team_detail", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaultValue(org.jooq.impl.DSL.field("[]", org.jooq.impl.SQLDataType.VARCHAR)), this, "团队详情页配置hr_media.id: [1, 23, 32]");

    /**
     * The column <code>hrdb.hr_team.disable</code>. 0是正常 1是删除
     */
    public final TableField<HrTeamRecord, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "0是正常 1是删除");

    /**
     * The column <code>hrdb.hr_team.sub_title</code>. 团队小标题
     */
    public final TableField<HrTeamRecord, String> SUB_TITLE = createField("sub_title", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "团队小标题");

    /**
     * Create a <code>hrdb.hr_team</code> table reference
     */
    public HrTeam() {
        this("hr_team", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_team</code> table reference
     */
    public HrTeam(String alias) {
        this(alias, HR_TEAM);
    }

    private HrTeam(String alias, Table<HrTeamRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrTeam(String alias, Table<HrTeamRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "团队信息");
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
    public Identity<HrTeamRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_TEAM;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrTeamRecord> getPrimaryKey() {
        return Keys.KEY_HR_TEAM_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrTeamRecord>> getKeys() {
        return Arrays.<UniqueKey<HrTeamRecord>>asList(Keys.KEY_HR_TEAM_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTeam as(String alias) {
        return new HrTeam(alias, this);
    }

    /**
     * Rename this table
     */
    public HrTeam rename(String name) {
        return new HrTeam(name, null);
    }
}
