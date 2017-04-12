/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrHbSendRecordRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


/**
 * 红包发送记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrHbSendRecord extends TableImpl<HrHbSendRecordRecord> {

    private static final long serialVersionUID = -972774367;

    /**
     * The reference instance of <code>hrdb.hr_hb_send_record</code>
     */
    public static final HrHbSendRecord HR_HB_SEND_RECORD = new HrHbSendRecord();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrHbSendRecordRecord> getRecordType() {
        return HrHbSendRecordRecord.class;
    }

    /**
     * The column <code>hrdb.hr_hb_send_record.id</code>.
     */
    public final TableField<HrHbSendRecordRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>hrdb.hr_hb_send_record.return_code</code>. SUCCESS/FAIL 此字段是通信标识,非交易标识,交易是否成功需要查看result_code来判断
     */
    public final TableField<HrHbSendRecordRecord, String> RETURN_CODE = createField("return_code", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.VARCHAR)), this, "SUCCESS/FAIL 此字段是通信标识,非交易标识,交易是否成功需要查看result_code来判断");

    /**
     * The column <code>hrdb.hr_hb_send_record.return_msg</code>. 返回信息,如非空,为错误原因
     */
    public final TableField<HrHbSendRecordRecord, String> RETURN_MSG = createField("return_msg", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "返回信息,如非空,为错误原因");

    /**
     * The column <code>hrdb.hr_hb_send_record.sign</code>. 生成签名
     */
    public final TableField<HrHbSendRecordRecord, String> SIGN = createField("sign", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "生成签名");

    /**
     * The column <code>hrdb.hr_hb_send_record.result_code</code>. SUCCESS/FAIL
     */
    public final TableField<HrHbSendRecordRecord, String> RESULT_CODE = createField("result_code", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "SUCCESS/FAIL");

    /**
     * The column <code>hrdb.hr_hb_send_record.err_code</code>. 错误码信息
     */
    public final TableField<HrHbSendRecordRecord, String> ERR_CODE = createField("err_code", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "错误码信息");

    /**
     * The column <code>hrdb.hr_hb_send_record.err_code_des</code>. 结果信息描述
     */
    public final TableField<HrHbSendRecordRecord, String> ERR_CODE_DES = createField("err_code_des", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "结果信息描述");

    /**
     * The column <code>hrdb.hr_hb_send_record.mch_billno</code>. 商户订单号
     */
    public final TableField<HrHbSendRecordRecord, String> MCH_BILLNO = createField("mch_billno", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "商户订单号");

    /**
     * The column <code>hrdb.hr_hb_send_record.mch_id</code>. 微信支付分配的商户号
     */
    public final TableField<HrHbSendRecordRecord, String> MCH_ID = createField("mch_id", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "微信支付分配的商户号");

    /**
     * The column <code>hrdb.hr_hb_send_record.wxappid</code>. 商户appid
     */
    public final TableField<HrHbSendRecordRecord, String> WXAPPID = createField("wxappid", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "商户appid");

    /**
     * The column <code>hrdb.hr_hb_send_record.re_openid</code>. 接受收红包的用户,用户在wxappid下的openid
     */
    public final TableField<HrHbSendRecordRecord, String> RE_OPENID = createField("re_openid", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "接受收红包的用户,用户在wxappid下的openid");

    /**
     * The column <code>hrdb.hr_hb_send_record.total_amount</code>. 付款金额,单位分
     */
    public final TableField<HrHbSendRecordRecord, Integer> TOTAL_AMOUNT = createField("total_amount", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "付款金额,单位分");

    /**
     * The column <code>hrdb.hr_hb_send_record.send_time</code>. 红包发送时间
     */
    public final TableField<HrHbSendRecordRecord, String> SEND_TIME = createField("send_time", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "红包发送时间");

    /**
     * The column <code>hrdb.hr_hb_send_record.send_listid</code>. 红包订单的微信单号
     */
    public final TableField<HrHbSendRecordRecord, String> SEND_LISTID = createField("send_listid", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "红包订单的微信单号");

    /**
     * The column <code>hrdb.hr_hb_send_record.create_time</code>.
     */
    public final TableField<HrHbSendRecordRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>hrdb.hr_hb_send_record.hb_item_id</code>. hr_hb_items.id 该红包 api 调用所对应的红包记录
     */
    public final TableField<HrHbSendRecordRecord, Integer> HB_ITEM_ID = createField("hb_item_id", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_hb_items.id 该红包 api 调用所对应的红包记录");

    /**
     * Create a <code>hrdb.hr_hb_send_record</code> table reference
     */
    public HrHbSendRecord() {
        this("hr_hb_send_record", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_hb_send_record</code> table reference
     */
    public HrHbSendRecord(String alias) {
        this(alias, HR_HB_SEND_RECORD);
    }

    private HrHbSendRecord(String alias, Table<HrHbSendRecordRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrHbSendRecord(String alias, Table<HrHbSendRecordRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "红包发送记录");
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
    public Identity<HrHbSendRecordRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_HB_SEND_RECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrHbSendRecordRecord> getPrimaryKey() {
        return Keys.KEY_HR_HB_SEND_RECORD_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrHbSendRecordRecord>> getKeys() {
        return Arrays.<UniqueKey<HrHbSendRecordRecord>>asList(Keys.KEY_HR_HB_SEND_RECORD_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrHbSendRecord as(String alias) {
        return new HrHbSendRecord(alias, this);
    }

    /**
     * Rename this table
     */
    public HrHbSendRecord rename(String name) {
        return new HrHbSendRecord(name, null);
    }
}
