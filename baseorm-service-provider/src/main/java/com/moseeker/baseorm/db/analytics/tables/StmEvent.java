/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.analytics.tables;


import com.moseeker.baseorm.db.analytics.Analytics;
import com.moseeker.baseorm.db.analytics.Keys;
import com.moseeker.baseorm.db.analytics.tables.records.StmEventRecord;

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
 * 请求事件元数据表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StmEvent extends TableImpl<StmEventRecord> {

    private static final long serialVersionUID = 1322302528;

    /**
     * The reference instance of <code>analytics.stm_event</code>
     */
    public static final StmEvent STM_EVENT = new StmEvent();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StmEventRecord> getRecordType() {
        return StmEventRecord.class;
    }

    /**
     * The column <code>analytics.stm_event.id</code>. primary key
     */
    public final TableField<StmEventRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "primary key");

    /**
     * The column <code>analytics.stm_event.req_type_id</code>. 请求类型外键
     */
    public final TableField<StmEventRecord, Integer> REQ_TYPE_ID = createField("req_type_id", org.jooq.impl.SQLDataType.INTEGER, this, "请求类型外键");

    /**
     * The column <code>analytics.stm_event.status_code</code>. 状态码 (200.404,etc)
     */
    public final TableField<StmEventRecord, Integer> STATUS_CODE = createField("status_code", org.jooq.impl.SQLDataType.INTEGER, this, "状态码 (200.404,etc)");

    /**
     * The column <code>analytics.stm_event.wechat_id</code>. 微信公共号id
     */
    public final TableField<StmEventRecord, Integer> WECHAT_ID = createField("wechat_id", org.jooq.impl.SQLDataType.INTEGER, this, "微信公共号id");

    /**
     * The column <code>analytics.stm_event.res_type_id</code>. 返回类型外键
     */
    public final TableField<StmEventRecord, Integer> RES_TYPE_ID = createField("res_type_id", org.jooq.impl.SQLDataType.INTEGER, this, "返回类型外键");

    /**
     * The column <code>analytics.stm_event.handler</code>. handler处理文件
     */
    public final TableField<StmEventRecord, String> HANDLER = createField("handler", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "handler处理文件");

    /**
     * The column <code>analytics.stm_event.wechat_type</code>. 微信公共号类型
     */
    public final TableField<StmEventRecord, Integer> WECHAT_TYPE = createField("wechat_type", org.jooq.impl.SQLDataType.INTEGER, this, "微信公共号类型");

    /**
     * The column <code>analytics.stm_event.open_id</code>. 微信绑定id
     */
    public final TableField<StmEventRecord, String> OPEN_ID = createField("open_id", org.jooq.impl.SQLDataType.VARCHAR.length(60), this, "微信绑定id");

    /**
     * The column <code>analytics.stm_event.dquser_id</code>. Moseeker注册id
     */
    public final TableField<StmEventRecord, String> DQUSER_ID = createField("dquser_id", org.jooq.impl.SQLDataType.VARCHAR.length(60), this, "Moseeker注册id");

    /**
     * The column <code>analytics.stm_event.viewer_id</code>. 系统分配的访问者id
     */
    public final TableField<StmEventRecord, String> VIEWER_ID = createField("viewer_id", org.jooq.impl.SQLDataType.VARCHAR.length(60), this, "系统分配的访问者id");

    /**
     * The column <code>analytics.stm_event.res_content</code>. 返回内容
     */
    public final TableField<StmEventRecord, String> RES_CONTENT = createField("res_content", org.jooq.impl.SQLDataType.CLOB, this, "返回内容");

    /**
     * The column <code>analytics.stm_event.req_uri</code>. 请求uri
     */
    public final TableField<StmEventRecord, String> REQ_URI = createField("req_uri", org.jooq.impl.SQLDataType.VARCHAR.length(120), this, "请求uri");

    /**
     * The column <code>analytics.stm_event.req_params</code>. 请求参数
     */
    public final TableField<StmEventRecord, String> REQ_PARAMS = createField("req_params", org.jooq.impl.SQLDataType.CLOB, this, "请求参数");

    /**
     * The column <code>analytics.stm_event.event_id</code>. 事件类型外键
     */
    public final TableField<StmEventRecord, Integer> EVENT_ID = createField("event_id", org.jooq.impl.SQLDataType.INTEGER, this, "事件类型外键");

    /**
     * The column <code>analytics.stm_event.remote_ip</code>.
     */
    public final TableField<StmEventRecord, String> REMOTE_IP = createField("remote_ip", org.jooq.impl.SQLDataType.VARCHAR.length(18), this, "");

    /**
     * The column <code>analytics.stm_event.session_id</code>. session id
     */
    public final TableField<StmEventRecord, String> SESSION_ID = createField("session_id", org.jooq.impl.SQLDataType.VARCHAR.length(128), this, "session id");

    /**
     * The column <code>analytics.stm_event.sys_user_cookie</code>. 用户的cookie内容
     */
    public final TableField<StmEventRecord, String> SYS_USER_COOKIE = createField("sys_user_cookie", org.jooq.impl.SQLDataType.CLOB, this, "用户的cookie内容");

    /**
     * The column <code>analytics.stm_event.useragent</code>. 客户端user agent
     */
    public final TableField<StmEventRecord, String> USERAGENT = createField("useragent", org.jooq.impl.SQLDataType.CLOB, this, "客户端user agent");

    /**
     * The column <code>analytics.stm_event.create_time</code>. 日志记录创建时间
     */
    public final TableField<StmEventRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "日志记录创建时间");

    /**
     * Create a <code>analytics.stm_event</code> table reference
     */
    public StmEvent() {
        this("stm_event", null);
    }

    /**
     * Create an aliased <code>analytics.stm_event</code> table reference
     */
    public StmEvent(String alias) {
        this(alias, STM_EVENT);
    }

    private StmEvent(String alias, Table<StmEventRecord> aliased) {
        this(alias, aliased, null);
    }

    private StmEvent(String alias, Table<StmEventRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "请求事件元数据表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Analytics.ANALYTICS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<StmEventRecord, Integer> getIdentity() {
        return Keys.IDENTITY_STM_EVENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<StmEventRecord> getPrimaryKey() {
        return Keys.KEY_STM_EVENT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StmEventRecord>> getKeys() {
        return Arrays.<UniqueKey<StmEventRecord>>asList(Keys.KEY_STM_EVENT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StmEvent as(String alias) {
        return new StmEvent(alias, this);
    }

    /**
     * Rename this table
     */
    public StmEvent rename(String name) {
        return new StmEvent(name, null);
    }
}