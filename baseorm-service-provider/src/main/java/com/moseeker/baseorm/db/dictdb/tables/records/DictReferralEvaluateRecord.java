/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables.records;


import com.moseeker.baseorm.db.dictdb.tables.DictReferralEvaluate;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 内推能力标签常量
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictReferralEvaluateRecord extends UpdatableRecordImpl<DictReferralEvaluateRecord> implements Record4<Integer, Integer, String, String> {

    private static final long serialVersionUID = 1314692979;

    /**
     * Setter for <code>dictdb.dict_referral_evaluate.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>dictdb.dict_referral_evaluate.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>dictdb.dict_referral_evaluate.code</code>. dict_constant.code parent_code 3135
     */
    public void setCode(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>dictdb.dict_referral_evaluate.code</code>. dict_constant.code parent_code 3135
     */
    public Integer getCode() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>dictdb.dict_referral_evaluate.tag</code>. 标签名称
     */
    public void setTag(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>dictdb.dict_referral_evaluate.tag</code>. 标签名称
     */
    public String getTag() {
        return (String) get(2);
    }

    /**
     * Setter for <code>dictdb.dict_referral_evaluate.tag_en</code>. 标签英文
     */
    public void setTagEn(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>dictdb.dict_referral_evaluate.tag_en</code>. 标签英文
     */
    public String getTagEn() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, Integer, String, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return DictReferralEvaluate.DICT_REFERRAL_EVALUATE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field2() {
        return DictReferralEvaluate.DICT_REFERRAL_EVALUATE.CODE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return DictReferralEvaluate.DICT_REFERRAL_EVALUATE.TAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return DictReferralEvaluate.DICT_REFERRAL_EVALUATE.TAG_EN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value2() {
        return getCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getTag();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getTagEn();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictReferralEvaluateRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictReferralEvaluateRecord value2(Integer value) {
        setCode(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictReferralEvaluateRecord value3(String value) {
        setTag(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictReferralEvaluateRecord value4(String value) {
        setTagEn(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DictReferralEvaluateRecord values(Integer value1, Integer value2, String value3, String value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DictReferralEvaluateRecord
     */
    public DictReferralEvaluateRecord() {
        super(DictReferralEvaluate.DICT_REFERRAL_EVALUATE);
    }

    /**
     * Create a detached, initialised DictReferralEvaluateRecord
     */
    public DictReferralEvaluateRecord(Integer id, Integer code, String tag, String tagEn) {
        super(DictReferralEvaluate.DICT_REFERRAL_EVALUATE);

        set(0, id);
        set(1, code);
        set(2, tag);
        set(3, tagEn);
    }
}
