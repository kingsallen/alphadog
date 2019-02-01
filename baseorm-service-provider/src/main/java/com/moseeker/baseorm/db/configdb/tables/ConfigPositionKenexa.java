/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables;


import com.moseeker.baseorm.db.configdb.Configdb;
import com.moseeker.baseorm.db.configdb.Keys;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigPositionKenexaRecord;

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
 * kenexa职位字段映射表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigPositionKenexa extends TableImpl<ConfigPositionKenexaRecord> {

    private static final long serialVersionUID = -1807985673;

    /**
     * The reference instance of <code>configdb.config_position_kenexa</code>
     */
    public static final ConfigPositionKenexa CONFIG_POSITION_KENEXA = new ConfigPositionKenexa();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConfigPositionKenexaRecord> getRecordType() {
        return ConfigPositionKenexaRecord.class;
    }

    /**
     * The column <code>configdb.config_position_kenexa.id</code>.
     */
    public final TableField<ConfigPositionKenexaRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>configdb.config_position_kenexa.source_id</code>. ATS来源ID
     */
    public final TableField<ConfigPositionKenexaRecord, Integer> SOURCE_ID = createField("source_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "ATS来源ID");

    /**
     * The column <code>configdb.config_position_kenexa.position_column</code>. 职位表字段名,  _ 暂未设置
     */
    public final TableField<ConfigPositionKenexaRecord, String> POSITION_COLUMN = createField("position_column", org.jooq.impl.SQLDataType.VARCHAR.length(32).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "职位表字段名,  _ 暂未设置");

    /**
     * The column <code>configdb.config_position_kenexa.kenexa_job_id</code>. kenexa对应的JOB字段ID
     */
    public final TableField<ConfigPositionKenexaRecord, String> KENEXA_JOB_ID = createField("kenexa_job_id", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "kenexa对应的JOB字段ID");

    /**
     * The column <code>configdb.config_position_kenexa.disable</code>. 是否有效，0：有效，1：无效
     */
    public final TableField<ConfigPositionKenexaRecord, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否有效，0：有效，1：无效");

    /**
     * The column <code>configdb.config_position_kenexa.create_time</code>. 创建时间
     */
    public final TableField<ConfigPositionKenexaRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>configdb.config_position_kenexa.update_time</code>. 修改时间
     */
    public final TableField<ConfigPositionKenexaRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>configdb.config_position_kenexa.app_tpl_id</code>. 模板Id
     */
    public final TableField<ConfigPositionKenexaRecord, Integer> APP_TPL_ID = createField("app_tpl_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "模板Id");

    /**
     * Create a <code>configdb.config_position_kenexa</code> table reference
     */
    public ConfigPositionKenexa() {
        this("config_position_kenexa", null);
    }

    /**
     * Create an aliased <code>configdb.config_position_kenexa</code> table reference
     */
    public ConfigPositionKenexa(String alias) {
        this(alias, CONFIG_POSITION_KENEXA);
    }

    private ConfigPositionKenexa(String alias, Table<ConfigPositionKenexaRecord> aliased) {
        this(alias, aliased, null);
    }

    private ConfigPositionKenexa(String alias, Table<ConfigPositionKenexaRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "kenexa职位字段映射表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Configdb.CONFIGDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<ConfigPositionKenexaRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CONFIG_POSITION_KENEXA;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ConfigPositionKenexaRecord> getPrimaryKey() {
        return Keys.KEY_CONFIG_POSITION_KENEXA_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ConfigPositionKenexaRecord>> getKeys() {
        return Arrays.<UniqueKey<ConfigPositionKenexaRecord>>asList(Keys.KEY_CONFIG_POSITION_KENEXA_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigPositionKenexa as(String alias) {
        return new ConfigPositionKenexa(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ConfigPositionKenexa rename(String name) {
        return new ConfigPositionKenexa(name, null);
    }
}
