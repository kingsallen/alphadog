/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTalentpoolRecord;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.jooq.*;
import org.jooq.impl.TableImpl;


/**
 * 人才库
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrTalentpool extends TableImpl<HrTalentpoolRecord> {

    private static final long serialVersionUID = -132379548;

    /**
     * The reference instance of <code>hrdb.hr_talentpool</code>
     */
    public static final HrTalentpool HR_TALENTPOOL = new HrTalentpool();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrTalentpoolRecord> getRecordType() {
        return HrTalentpoolRecord.class;
    }

    /**
     * The column <code>hrdb.hr_talentpool.id</code>.
     */
    public final TableField<HrTalentpoolRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>hrdb.hr_talentpool.hr_account_id</code>. 创建人id(user_hr_account.id)
     */
    public final TableField<HrTalentpoolRecord, Integer> HR_ACCOUNT_ID = createField("hr_account_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "创建人id(user_hr_account.id)");

    /**
     * The column <code>hrdb.hr_talentpool.applier_id</code>. 候选人id（user_user.id）
     */
    public final TableField<HrTalentpoolRecord, Integer> APPLIER_ID = createField("applier_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "候选人id（user_user.id）");

    /**
     * The column <code>hrdb.hr_talentpool.create_time</code>.
     */
    public final TableField<HrTalentpoolRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>hrdb.hr_talentpool.update_time</code>.
     */
    public final TableField<HrTalentpoolRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>hrdb.hr_talentpool.status</code>. 状态(0：正常，1：删除)
     */
    public final TableField<HrTalentpoolRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "状态(0：正常，1：删除)");

    /**
     * Create a <code>hrdb.hr_talentpool</code> table reference
     */
    public HrTalentpool() {
        this("hr_talentpool", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_talentpool</code> table reference
     */
    public HrTalentpool(String alias) {
        this(alias, HR_TALENTPOOL);
    }

    private HrTalentpool(String alias, Table<HrTalentpoolRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrTalentpool(String alias, Table<HrTalentpoolRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "人才库");
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
    public Identity<HrTalentpoolRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_TALENTPOOL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrTalentpoolRecord> getPrimaryKey() {
        return Keys.KEY_HR_TALENTPOOL_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrTalentpoolRecord>> getKeys() {
        return Arrays.<UniqueKey<HrTalentpoolRecord>>asList(Keys.KEY_HR_TALENTPOOL_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrTalentpool as(String alias) {
        return new HrTalentpool(alias, this);
    }

    /**
     * Rename this table
     */
    public HrTalentpool rename(String name) {
        return new HrTalentpool(name, null);
    }
}
