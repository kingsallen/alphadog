/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbItemsBackupChendi_180625Record;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrHbItemsBackupChendi_180625 extends TableImpl<HrHbItemsBackupChendi_180625Record> {

    private static final long serialVersionUID = 226534377;

    /**
     * The reference instance of <code>hrdb.hr_hb_items_backup_chendi_180625</code>
     */
    public static final HrHbItemsBackupChendi_180625 HR_HB_ITEMS_BACKUP_CHENDI_180625 = new HrHbItemsBackupChendi_180625();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrHbItemsBackupChendi_180625Record> getRecordType() {
        return HrHbItemsBackupChendi_180625Record.class;
    }

    /**
     * The column <code>hrdb.hr_hb_items_backup_chendi_180625.id</code>.
     */
    public final TableField<HrHbItemsBackupChendi_180625Record, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>hrdb.hr_hb_items_backup_chendi_180625.hb_config_id</code>. hr_hb_config.id
     */
    public final TableField<HrHbItemsBackupChendi_180625Record, Integer> HB_CONFIG_ID = createField("hb_config_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_hb_config.id");

    /**
     * The column <code>hrdb.hr_hb_items_backup_chendi_180625.binding_id</code>. position_hb_binding.id
     */
    public final TableField<HrHbItemsBackupChendi_180625Record, Integer> BINDING_ID = createField("binding_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "position_hb_binding.id");

    /**
     * The column <code>hrdb.hr_hb_items_backup_chendi_180625.index</code>. 这条数据是第几个红包 0 &lt; x &lt;= 总红包数, 如果是 NULL 表示这是一个空红包
     */
    public final TableField<HrHbItemsBackupChendi_180625Record, Integer> INDEX = createField("index", org.jooq.impl.SQLDataType.INTEGER, this, "这条数据是第几个红包 0 < x <= 总红包数, 如果是 NULL 表示这是一个空红包");

    /**
     * The column <code>hrdb.hr_hb_items_backup_chendi_180625.amount</code>. 红包金额
     */
    public final TableField<HrHbItemsBackupChendi_180625Record, BigDecimal> AMOUNT = createField("amount", org.jooq.impl.SQLDataType.DECIMAL.precision(11, 2).nullable(false).defaultValue(org.jooq.impl.DSL.inline("0.00", org.jooq.impl.SQLDataType.DECIMAL)), this, "红包金额");

    /**
     * The column <code>hrdb.hr_hb_items_backup_chendi_180625.status</code>. 0:初始状态,1:发送了消息模成功,2:发送消息模板失败,尝试直接发送有金额的红包,3:打开刮刮卡,点击红包数字前,4:点击刮刮卡上红包数字后,5:发送红包前,校验 current_user.qxuser 不通过,红包停发,6:发送红包前,校验刮刮卡中的 hb_item 不通过,红包停发,7:跳过模版消息直接发送红包失败,100: 发送消息模板后成功发送了红包,101: 跳过发送消息模板后成功发送了红包,-1: 发送了 0 元红包的消息模板
     */
    public final TableField<HrHbItemsBackupChendi_180625Record, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0:初始状态,1:发送了消息模成功,2:发送消息模板失败,尝试直接发送有金额的红包,3:打开刮刮卡,点击红包数字前,4:点击刮刮卡上红包数字后,5:发送红包前,校验 current_user.qxuser 不通过,红包停发,6:发送红包前,校验刮刮卡中的 hb_item 不通过,红包停发,7:跳过模版消息直接发送红包失败,100: 发送消息模板后成功发送了红包,101: 跳过发送消息模板后成功发送了红包,-1: 发送了 0 元红包的消息模板");

    /**
     * The column <code>hrdb.hr_hb_items_backup_chendi_180625.wxuser_id</code>. 获取红包的用户
     */
    public final TableField<HrHbItemsBackupChendi_180625Record, Integer> WXUSER_ID = createField("wxuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "获取红包的用户");

    /**
     * The column <code>hrdb.hr_hb_items_backup_chendi_180625.open_time</code>. 红包打开时间
     */
    public final TableField<HrHbItemsBackupChendi_180625Record, Timestamp> OPEN_TIME = createField("open_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "红包打开时间");

    /**
     * The column <code>hrdb.hr_hb_items_backup_chendi_180625.create_time</code>. 创建时间
     */
    public final TableField<HrHbItemsBackupChendi_180625Record, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>hrdb.hr_hb_items_backup_chendi_180625.update_time</code>. 更新时间
     */
    public final TableField<HrHbItemsBackupChendi_180625Record, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * The column <code>hrdb.hr_hb_items_backup_chendi_180625.trigger_wxuser_id</code>. 触发发送红包行为时的当前用户, 即 JD 页点击者或职位申请者 wx_group_user.id
     */
    public final TableField<HrHbItemsBackupChendi_180625Record, Integer> TRIGGER_WXUSER_ID = createField("trigger_wxuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "触发发送红包行为时的当前用户, 即 JD 页点击者或职位申请者 wx_group_user.id");

    /**
     * Create a <code>hrdb.hr_hb_items_backup_chendi_180625</code> table reference
     */
    public HrHbItemsBackupChendi_180625() {
        this("hr_hb_items_backup_chendi_180625", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_hb_items_backup_chendi_180625</code> table reference
     */
    public HrHbItemsBackupChendi_180625(String alias) {
        this(alias, HR_HB_ITEMS_BACKUP_CHENDI_180625);
    }

    private HrHbItemsBackupChendi_180625(String alias, Table<HrHbItemsBackupChendi_180625Record> aliased) {
        this(alias, aliased, null);
    }

    private HrHbItemsBackupChendi_180625(String alias, Table<HrHbItemsBackupChendi_180625Record> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
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
    public HrHbItemsBackupChendi_180625 as(String alias) {
        return new HrHbItemsBackupChendi_180625(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrHbItemsBackupChendi_180625 rename(String name) {
        return new HrHbItemsBackupChendi_180625(name, null);
    }
}
