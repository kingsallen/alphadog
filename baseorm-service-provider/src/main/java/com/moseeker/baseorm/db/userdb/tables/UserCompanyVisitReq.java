/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables;


import com.moseeker.baseorm.db.userdb.Keys;
import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.records.UserCompanyVisitReqRecord;

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
 * C端用户申请参观记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserCompanyVisitReq extends TableImpl<UserCompanyVisitReqRecord> {

    private static final long serialVersionUID = 354770676;

    /**
     * The reference instance of <code>userdb.user_company_visit_req</code>
     */
    public static final UserCompanyVisitReq USER_COMPANY_VISIT_REQ = new UserCompanyVisitReq();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserCompanyVisitReqRecord> getRecordType() {
        return UserCompanyVisitReqRecord.class;
    }

    /**
     * The column <code>userdb.user_company_visit_req.id</code>. id
     */
    public final TableField<UserCompanyVisitReqRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "id");

    /**
     * The column <code>userdb.user_company_visit_req.company_id</code>. hr_company.id
     */
    public final TableField<UserCompanyVisitReqRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_company.id");

    /**
     * The column <code>userdb.user_company_visit_req.user_id</code>. user_user.id
     */
    public final TableField<UserCompanyVisitReqRecord, Integer> USER_ID = createField("user_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "user_user.id");

    /**
     * The column <code>userdb.user_company_visit_req.status</code>. 0: 取消申请参观 1:申请参观
     */
    public final TableField<UserCompanyVisitReqRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("1", org.jooq.impl.SQLDataType.INTEGER)), this, "0: 取消申请参观 1:申请参观");

    /**
     * The column <code>userdb.user_company_visit_req.source</code>. 操作来源 0: 未知 1:微信端 2:PC 端
     */
    public final TableField<UserCompanyVisitReqRecord, Integer> SOURCE = createField("source", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "操作来源 0: 未知 1:微信端 2:PC 端");

    /**
     * The column <code>userdb.user_company_visit_req.create_time</code>. 关注时间
     */
    public final TableField<UserCompanyVisitReqRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "关注时间");

    /**
     * The column <code>userdb.user_company_visit_req.update_time</code>.
     */
    public final TableField<UserCompanyVisitReqRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * Create a <code>userdb.user_company_visit_req</code> table reference
     */
    public UserCompanyVisitReq() {
        this("user_company_visit_req", null);
    }

    /**
     * Create an aliased <code>userdb.user_company_visit_req</code> table reference
     */
    public UserCompanyVisitReq(String alias) {
        this(alias, USER_COMPANY_VISIT_REQ);
    }

    private UserCompanyVisitReq(String alias, Table<UserCompanyVisitReqRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserCompanyVisitReq(String alias, Table<UserCompanyVisitReqRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "C端用户申请参观记录表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Userdb.USERDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserCompanyVisitReqRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_COMPANY_VISIT_REQ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserCompanyVisitReqRecord> getPrimaryKey() {
        return Keys.KEY_USER_COMPANY_VISIT_REQ_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserCompanyVisitReqRecord>> getKeys() {
        return Arrays.<UniqueKey<UserCompanyVisitReqRecord>>asList(Keys.KEY_USER_COMPANY_VISIT_REQ_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCompanyVisitReq as(String alias) {
        return new UserCompanyVisitReq(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserCompanyVisitReq rename(String name) {
        return new UserCompanyVisitReq(name, null);
    }
}
