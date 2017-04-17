/*
 * This file is generated by jOOQ.
*/
package com.moseeker.db.analytics.tables;


import com.moseeker.db.analytics.Analytics;
import com.moseeker.db.analytics.Keys;
import com.moseeker.db.analytics.tables.records.StmEvent_11_01Record;

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
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class StmEvent_11_01 extends TableImpl<StmEvent_11_01Record> {

    private static final long serialVersionUID = -13355690;

    /**
     * The reference instance of <code>analytics.stm_event_11_01</code>
     */
    public static final StmEvent_11_01 STM_EVENT_11_01 = new StmEvent_11_01();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<StmEvent_11_01Record> getRecordType() {
        return StmEvent_11_01Record.class;
    }

    /**
     * The column <code>analytics.stm_event_11_01.id</code>.
     */
    public final TableField<StmEvent_11_01Record, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>analytics.stm_event_11_01.req_type_id</code>. 请求类型外键
     */
    public final TableField<StmEvent_11_01Record, Integer> REQ_TYPE_ID = createField("req_type_id", org.jooq.impl.SQLDataType.INTEGER, this, "请求类型外键");

    /**
     * The column <code>analytics.stm_event_11_01.status_code</code>. 状态码 (200.404,etc)
     */
    public final TableField<StmEvent_11_01Record, Integer> STATUS_CODE = createField("status_code", org.jooq.impl.SQLDataType.INTEGER, this, "状态码 (200.404,etc)");

    /**
     * The column <code>analytics.stm_event_11_01.wechat_id</code>. 微信公共号id
     */
    public final TableField<StmEvent_11_01Record, Integer> WECHAT_ID = createField("wechat_id", org.jooq.impl.SQLDataType.INTEGER, this, "微信公共号id");

    /**
     * The column <code>analytics.stm_event_11_01.res_type_id</code>. 返回类型外键
     */
    public final TableField<StmEvent_11_01Record, Integer> RES_TYPE_ID = createField("res_type_id", org.jooq.impl.SQLDataType.INTEGER, this, "返回类型外键");

    /**
     * The column <code>analytics.stm_event_11_01.handler</code>. handler处理文件
     */
    public final TableField<StmEvent_11_01Record, String> HANDLER = createField("handler", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "handler处理文件");

    /**
     * The column <code>analytics.stm_event_11_01.wechat_type</code>. 微信公共号类型
     */
    public final TableField<StmEvent_11_01Record, Integer> WECHAT_TYPE = createField("wechat_type", org.jooq.impl.SQLDataType.INTEGER, this, "微信公共号类型");

    /**
     * The column <code>analytics.stm_event_11_01.open_id</code>. 微信绑定id
     */
    public final TableField<StmEvent_11_01Record, String> OPEN_ID = createField("open_id", org.jooq.impl.SQLDataType.VARCHAR.length(60), this, "微信绑定id");

    /**
     * The column <code>analytics.stm_event_11_01.dquser_id</code>. Moseeker注册id
     */
    public final TableField<StmEvent_11_01Record, String> DQUSER_ID = createField("dquser_id", org.jooq.impl.SQLDataType.VARCHAR.length(60), this, "Moseeker注册id");

    /**
     * The column <code>analytics.stm_event_11_01.viewer_id</code>. 系统分配的访问者id
     */
    public final TableField<StmEvent_11_01Record, String> VIEWER_ID = createField("viewer_id", org.jooq.impl.SQLDataType.VARCHAR.length(60), this, "系统分配的访问者id");

    /**
     * The column <code>analytics.stm_event_11_01.res_content</code>. 返回内容
     */
    public final TableField<StmEvent_11_01Record, String> RES_CONTENT = createField("res_content", org.jooq.impl.SQLDataType.CLOB, this, "返回内容");

    /**
     * The column <code>analytics.stm_event_11_01.req_uri</code>. 请求uri
     */
    public final TableField<StmEvent_11_01Record, String> REQ_URI = createField("req_uri", org.jooq.impl.SQLDataType.VARCHAR.length(120), this, "请求uri");

    /**
     * The column <code>analytics.stm_event_11_01.req_params</code>. 请求参数
     */
    public final TableField<StmEvent_11_01Record, String> REQ_PARAMS = createField("req_params", org.jooq.impl.SQLDataType.CLOB, this, "请求参数");

    /**
     * The column <code>analytics.stm_event_11_01.event_id</code>. 事件类型外键
     */
    public final TableField<StmEvent_11_01Record, Integer> EVENT_ID = createField("event_id", org.jooq.impl.SQLDataType.INTEGER, this, "事件类型外键");

    /**
     * The column <code>analytics.stm_event_11_01.remote_ip</code>.
     */
    public final TableField<StmEvent_11_01Record, String> REMOTE_IP = createField("remote_ip", org.jooq.impl.SQLDataType.VARCHAR.length(18), this, "");

    /**
     * The column <code>analytics.stm_event_11_01.session_id</code>. session id
     */
    public final TableField<StmEvent_11_01Record, String> SESSION_ID = createField("session_id", org.jooq.impl.SQLDataType.VARCHAR.length(128), this, "session id");

    /**
     * The column <code>analytics.stm_event_11_01.sys_user_cookie</code>. 用户的cookie内容
     */
    public final TableField<StmEvent_11_01Record, String> SYS_USER_COOKIE = createField("sys_user_cookie", org.jooq.impl.SQLDataType.CLOB, this, "用户的cookie内容");

    /**
     * The column <code>analytics.stm_event_11_01.useragent</code>. 客户端user agent
     */
    public final TableField<StmEvent_11_01Record, String> USERAGENT = createField("useragent", org.jooq.impl.SQLDataType.CLOB, this, "客户端user agent");

    /**
     * The column <code>analytics.stm_event_11_01.create_time</code>. 日志记录创建时间
     */
    public final TableField<StmEvent_11_01Record, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "日志记录创建时间");

    /**
     * Create a <code>analytics.stm_event_11_01</code> table reference
     */
    public StmEvent_11_01() {
        this("stm_event_11_01", null);
    }

    /**
     * Create an aliased <code>analytics.stm_event_11_01</code> table reference
     */
    public StmEvent_11_01(String alias) {
        this(alias, STM_EVENT_11_01);
    }

    private StmEvent_11_01(String alias, Table<StmEvent_11_01Record> aliased) {
        this(alias, aliased, null);
    }

    private StmEvent_11_01(String alias, Table<StmEvent_11_01Record> aliased, Field<?>[] parameters) {
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
    public Identity<StmEvent_11_01Record, Integer> getIdentity() {
        return Keys.IDENTITY_STM_EVENT_11_01;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<StmEvent_11_01Record> getPrimaryKey() {
        return Keys.KEY_STM_EVENT_11_01_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<StmEvent_11_01Record>> getKeys() {
        return Arrays.<UniqueKey<StmEvent_11_01Record>>asList(Keys.KEY_STM_EVENT_11_01_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StmEvent_11_01 as(String alias) {
        return new StmEvent_11_01(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public StmEvent_11_01 rename(String name) {
        return new StmEvent_11_01(name, null);
    }
}
