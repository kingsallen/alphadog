/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables;


import com.moseeker.baseorm.db.configdb.Configdb;
import com.moseeker.baseorm.db.configdb.Keys;
import com.moseeker.baseorm.db.configdb.tables.records.ConfigSysTemplateMessageLibraryRecord;

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
 * 模板消息库
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ConfigSysTemplateMessageLibrary extends TableImpl<ConfigSysTemplateMessageLibraryRecord> {

    private static final long serialVersionUID = -1333086756;

    /**
     * The reference instance of <code>configdb.config_sys_template_message_library</code>
     */
    public static final ConfigSysTemplateMessageLibrary CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY = new ConfigSysTemplateMessageLibrary();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ConfigSysTemplateMessageLibraryRecord> getRecordType() {
        return ConfigSysTemplateMessageLibraryRecord.class;
    }

    /**
     * The column <code>configdb.config_sys_template_message_library.id</code>. 主key
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主key");

    /**
     * The column <code>configdb.config_sys_template_message_library.title</code>. 模板标题
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> TITLE = createField("title", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "模板标题");

    /**
     * The column <code>configdb.config_sys_template_message_library.primary_industry</code>. 一级行业
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> PRIMARY_INDUSTRY = createField("primary_industry", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "一级行业");

    /**
     * The column <code>configdb.config_sys_template_message_library.two_industry</code>. 二级行业
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> TWO_INDUSTRY = createField("two_industry", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "二级行业");

    /**
     * The column <code>configdb.config_sys_template_message_library.content</code>. 详细内容
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> CONTENT = createField("content", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "详细内容");

    /**
     * The column <code>configdb.config_sys_template_message_library.sample</code>. 内容示例
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> SAMPLE = createField("sample", org.jooq.impl.SQLDataType.CLOB.nullable(false), this, "内容示例");

    /**
     * The column <code>configdb.config_sys_template_message_library.display</code>. 是否显示
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, Integer> DISPLAY = createField("display", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否显示");

    /**
     * The column <code>configdb.config_sys_template_message_library.priority</code>. 排序
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, Integer> PRIORITY = createField("priority", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("10", org.jooq.impl.SQLDataType.INTEGER)), this, "排序");

    /**
     * The column <code>configdb.config_sys_template_message_library.disable</code>. 是否可用
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, Integer> DISABLE = createField("disable", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否可用");

    /**
     * The column <code>configdb.config_sys_template_message_library.type</code>. 模板类别 0:微信 1:邮件 2:短信 3:申请模板 4:其他
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, Byte> TYPE = createField("type", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "模板类别 0:微信 1:邮件 2:短信 3:申请模板 4:其他");

    /**
     * The column <code>configdb.config_sys_template_message_library.template_id_short</code>. 模板库中模板的编号
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> TEMPLATE_ID_SHORT = createField("template_id_short", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "模板库中模板的编号");

    /**
     * The column <code>configdb.config_sys_template_message_library.send_condition</code>. 触发条件
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> SEND_CONDITION = createField("send_condition", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "触发条件");

    /**
     * The column <code>configdb.config_sys_template_message_library.sendtime</code>. 发送时间
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> SENDTIME = createField("sendtime", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "发送时间");

    /**
     * The column <code>configdb.config_sys_template_message_library.sendto</code>. 发送对象
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> SENDTO = createField("sendto", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "发送对象");

    /**
     * The column <code>configdb.config_sys_template_message_library.first</code>. 消息模板first文案
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> FIRST = createField("first", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "消息模板first文案");

    /**
     * The column <code>configdb.config_sys_template_message_library.remark</code>. 消息模板remark文案
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> REMARK = createField("remark", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false), this, "消息模板remark文案");

    /**
     * The column <code>configdb.config_sys_template_message_library.url</code>. 跳转页面
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> URL = createField("url", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false), this, "跳转页面");

    /**
     * The column <code>configdb.config_sys_template_message_library.from_name</code>.
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> FROM_NAME = createField("from_name", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

    /**
     * The column <code>configdb.config_sys_template_message_library.subject</code>.
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> SUBJECT = createField("subject", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

    /**
     * The column <code>configdb.config_sys_template_message_library.color_json</code>. 消息模板字体默认颜色
     */
    public final TableField<ConfigSysTemplateMessageLibraryRecord, String> COLOR_JSON = createField("color_json", org.jooq.impl.SQLDataType.VARCHAR.length(512).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "消息模板字体默认颜色");

    /**
     * Create a <code>configdb.config_sys_template_message_library</code> table reference
     */
    public ConfigSysTemplateMessageLibrary() {
        this("config_sys_template_message_library", null);
    }

    /**
     * Create an aliased <code>configdb.config_sys_template_message_library</code> table reference
     */
    public ConfigSysTemplateMessageLibrary(String alias) {
        this(alias, CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY);
    }

    private ConfigSysTemplateMessageLibrary(String alias, Table<ConfigSysTemplateMessageLibraryRecord> aliased) {
        this(alias, aliased, null);
    }

    private ConfigSysTemplateMessageLibrary(String alias, Table<ConfigSysTemplateMessageLibraryRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "模板消息库");
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
    public Identity<ConfigSysTemplateMessageLibraryRecord, Integer> getIdentity() {
        return Keys.IDENTITY_CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ConfigSysTemplateMessageLibraryRecord> getPrimaryKey() {
        return Keys.KEY_CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ConfigSysTemplateMessageLibraryRecord>> getKeys() {
        return Arrays.<UniqueKey<ConfigSysTemplateMessageLibraryRecord>>asList(Keys.KEY_CONFIG_SYS_TEMPLATE_MESSAGE_LIBRARY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConfigSysTemplateMessageLibrary as(String alias) {
        return new ConfigSysTemplateMessageLibrary(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ConfigSysTemplateMessageLibrary rename(String name) {
        return new ConfigSysTemplateMessageLibrary(name, null);
    }
}
