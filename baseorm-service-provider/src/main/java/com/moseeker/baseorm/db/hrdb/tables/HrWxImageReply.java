/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrWxImageReplyRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.jooq.types.UInteger;

import javax.annotation.Generated;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


/**
 * 微信图片回复
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrWxImageReply extends TableImpl<HrWxImageReplyRecord> {

    private static final long serialVersionUID = -1965846193;

    /**
     * The reference instance of <code>hrdb.hr_wx_image_reply</code>
     */
    public static final HrWxImageReply HR_WX_IMAGE_REPLY = new HrWxImageReply();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrWxImageReplyRecord> getRecordType() {
        return HrWxImageReplyRecord.class;
    }

    /**
     * The column <code>hrdb.hr_wx_image_reply.id</code>.
     */
    public final TableField<HrWxImageReplyRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>hrdb.hr_wx_image_reply.rid</code>. wx_rule.id, 规则ID
     */
    public final TableField<HrWxImageReplyRecord, UInteger> RID = createField("rid", org.jooq.impl.SQLDataType.INTEGERUNSIGNED.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGERUNSIGNED)), this, "wx_rule.id, 规则ID");

    /**
     * The column <code>hrdb.hr_wx_image_reply.image</code>. 回复图片的相对路径
     */
    public final TableField<HrWxImageReplyRecord, String> IMAGE = createField("image", org.jooq.impl.SQLDataType.VARCHAR.length(512).nullable(false).defaultValue(org.jooq.impl.DSL.field("", org.jooq.impl.SQLDataType.VARCHAR)), this, "回复图片的相对路径");

    /**
     * The column <code>hrdb.hr_wx_image_reply.create_time</code>.
     */
    public final TableField<HrWxImageReplyRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>hrdb.hr_wx_image_reply.update_time</code>.
     */
    public final TableField<HrWxImageReplyRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>hrdb.hr_wx_image_reply</code> table reference
     */
    public HrWxImageReply() {
        this("hr_wx_image_reply", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_wx_image_reply</code> table reference
     */
    public HrWxImageReply(String alias) {
        this(alias, HR_WX_IMAGE_REPLY);
    }

    private HrWxImageReply(String alias, Table<HrWxImageReplyRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrWxImageReply(String alias, Table<HrWxImageReplyRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "微信图片回复");
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
    public Identity<HrWxImageReplyRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_WX_IMAGE_REPLY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrWxImageReplyRecord> getPrimaryKey() {
        return Keys.KEY_HR_WX_IMAGE_REPLY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrWxImageReplyRecord>> getKeys() {
        return Arrays.<UniqueKey<HrWxImageReplyRecord>>asList(Keys.KEY_HR_WX_IMAGE_REPLY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrWxImageReply as(String alias) {
        return new HrWxImageReply(alias, this);
    }

    /**
     * Rename this table
     */
    public HrWxImageReply rename(String name) {
        return new HrWxImageReply(name, null);
    }
}
