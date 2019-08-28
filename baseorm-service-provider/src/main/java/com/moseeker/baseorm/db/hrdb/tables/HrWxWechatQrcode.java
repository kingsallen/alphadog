/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxWechatQrcodeRecord;

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
 * 微信公众号的场景二维码表(永久)
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxWechatQrcode extends TableImpl<HrWxWechatQrcodeRecord> {

    private static final long serialVersionUID = -1224881658;

    /**
     * The reference instance of <code>hrdb.hr_wx_wechat_qrcode</code>
     */
    public static final HrWxWechatQrcode HR_WX_WECHAT_QRCODE = new HrWxWechatQrcode();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrWxWechatQrcodeRecord> getRecordType() {
        return HrWxWechatQrcodeRecord.class;
    }

    /**
     * The column <code>hrdb.hr_wx_wechat_qrcode.id</code>. 主键
     */
    public final TableField<HrWxWechatQrcodeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "主键");

    /**
     * The column <code>hrdb.hr_wx_wechat_qrcode.wechat_id</code>. hr_wx_wechat.id
     */
    public final TableField<HrWxWechatQrcodeRecord, Integer> WECHAT_ID = createField("wechat_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "hr_wx_wechat.id");

    /**
     * The column <code>hrdb.hr_wx_wechat_qrcode.qrcode_url</code>. 该公众号场景二维码的url
     */
    public final TableField<HrWxWechatQrcodeRecord, String> QRCODE_URL = createField("qrcode_url", org.jooq.impl.SQLDataType.VARCHAR.length(300), this, "该公众号场景二维码的url");

    /**
     * The column <code>hrdb.hr_wx_wechat_qrcode.scene</code>. 场景值id
     */
    public final TableField<HrWxWechatQrcodeRecord, String> SCENE = createField("scene", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "场景值id");

    /**
     * The column <code>hrdb.hr_wx_wechat_qrcode.create_time</code>. 创建时间
     */
    public final TableField<HrWxWechatQrcodeRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>hrdb.hr_wx_wechat_qrcode.update_time</code>. 更新时间
     */
    public final TableField<HrWxWechatQrcodeRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * The column <code>hrdb.hr_wx_wechat_qrcode.ticket</code>. 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
     */
    public final TableField<HrWxWechatQrcodeRecord, String> TICKET = createField("ticket", org.jooq.impl.SQLDataType.VARCHAR.length(300), this, "获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。");

    /**
     * Create a <code>hrdb.hr_wx_wechat_qrcode</code> table reference
     */
    public HrWxWechatQrcode() {
        this("hr_wx_wechat_qrcode", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_wx_wechat_qrcode</code> table reference
     */
    public HrWxWechatQrcode(String alias) {
        this(alias, HR_WX_WECHAT_QRCODE);
    }

    private HrWxWechatQrcode(String alias, Table<HrWxWechatQrcodeRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrWxWechatQrcode(String alias, Table<HrWxWechatQrcodeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "微信公众号的场景二维码表(永久)");
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
    public Identity<HrWxWechatQrcodeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_WX_WECHAT_QRCODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrWxWechatQrcodeRecord> getPrimaryKey() {
        return Keys.KEY_HR_WX_WECHAT_QRCODE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrWxWechatQrcodeRecord>> getKeys() {
        return Arrays.<UniqueKey<HrWxWechatQrcodeRecord>>asList(Keys.KEY_HR_WX_WECHAT_QRCODE_PRIMARY, Keys.KEY_HR_WX_WECHAT_QRCODE_UNIQUE_QRCODE_URL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxWechatQrcode as(String alias) {
        return new HrWxWechatQrcode(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrWxWechatQrcode rename(String name) {
        return new HrWxWechatQrcode(name, null);
    }
}
