/**
 * This class is generated by jOOQ
 */
package com.moseeker.db.userdb.tables.records;


import com.moseeker.db.userdb.tables.UserCompanyVisitReq;

import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;
import org.jooq.types.UInteger;


/**
 * C端用户申请参观记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserCompanyVisitReqRecord extends UpdatableRecordImpl<UserCompanyVisitReqRecord> implements Record7<UInteger, Integer, Integer, Integer, Integer, Timestamp, Timestamp> {

    private static final long serialVersionUID = 332623780;

    /**
     * Setter for <code>userdb.user_company_visit_req.id</code>. id
     */
    public void setId(UInteger value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_company_visit_req.id</code>. id
     */
    public UInteger getId() {
        return (UInteger) get(0);
    }

    /**
     * Setter for <code>userdb.user_company_visit_req.company_id</code>. hr_company.id
     */
    public void setCompanyId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_company_visit_req.company_id</code>. hr_company.id
     */
    public Integer getCompanyId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>userdb.user_company_visit_req.user_id</code>. user_user.id
     */
    public void setUserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_company_visit_req.user_id</code>. user_user.id
     */
    public Integer getUserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>userdb.user_company_visit_req.status</code>. 0: 取消申请参观 1：申请参观
     */
    public void setStatus(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>userdb.user_company_visit_req.status</code>. 0: 取消申请参观 1：申请参观
     */
    public Integer getStatus() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>userdb.user_company_visit_req.source</code>. 操作来源 0: 未知 1：微信端 2：PC 端
     */
    public void setSource(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>userdb.user_company_visit_req.source</code>. 操作来源 0: 未知 1：微信端 2：PC 端
     */
    public Integer getSource() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>userdb.user_company_visit_req.create_time</code>. 关注时间
     */
    public void setCreateTime(Timestamp value) {
        set(5, value);
    }

    /**
     * Getter for <code>userdb.user_company_visit_req.create_time</code>. 关注时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(5);
    }

    /**
     * Setter for <code>userdb.user_company_visit_req.update_time</code>.
     */
    public void setUpdateTime(Timestamp value) {
        set(6, value);
    }

    /**
     * Getter for <code>userdb.user_company_visit_req.update_time</code>.
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<UInteger> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<UInteger, Integer, Integer, Integer, Integer, Timestamp, Timestamp> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<UInteger, Integer, Integer, Integer, Integer, Timestamp, Timestamp> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<UInteger> field1() {
        return UserCompanyVisitReq.USER_COMPANY_VISIT_REQ.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return UserCompanyVisitReq.USER_COMPANY_VISIT_REQ.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return UserCompanyVisitReq.USER_COMPANY_VISIT_REQ.USER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return UserCompanyVisitReq.USER_COMPANY_VISIT_REQ.STATUS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return UserCompanyVisitReq.USER_COMPANY_VISIT_REQ.SOURCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field6() {
        return UserCompanyVisitReq.USER_COMPANY_VISIT_REQ.CREATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field7() {
        return UserCompanyVisitReq.USER_COMPANY_VISIT_REQ.UPDATE_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UInteger value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getUserId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getSource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value6() {
        return getCreateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value7() {
        return getUpdateTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCompanyVisitReqRecord value1(UInteger value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCompanyVisitReqRecord value2(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCompanyVisitReqRecord value3(Integer value) {
        setUserId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCompanyVisitReqRecord value4(Integer value) {
        setStatus(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCompanyVisitReqRecord value5(Integer value) {
        setSource(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCompanyVisitReqRecord value6(Timestamp value) {
        setCreateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCompanyVisitReqRecord value7(Timestamp value) {
        setUpdateTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserCompanyVisitReqRecord values(UInteger value1, Integer value2, Integer value3, Integer value4, Integer value5, Timestamp value6, Timestamp value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserCompanyVisitReqRecord
     */
    public UserCompanyVisitReqRecord() {
        super(UserCompanyVisitReq.USER_COMPANY_VISIT_REQ);
    }

    /**
     * Create a detached, initialised UserCompanyVisitReqRecord
     */
    public UserCompanyVisitReqRecord(UInteger id, Integer companyId, Integer userId, Integer status, Integer source, Timestamp createTime, Timestamp updateTime) {
        super(UserCompanyVisitReq.USER_COMPANY_VISIT_REQ);

        set(0, id);
        set(1, companyId);
        set(2, userId);
        set(3, status);
        set(4, source);
        set(5, createTime);
        set(6, updateTime);
    }
}
