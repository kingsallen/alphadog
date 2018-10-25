/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables.records;


import com.moseeker.baseorm.db.historydb.tables.HrMedia_20170612;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 模板媒体表，存储模板渲染的媒体信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrMedia_20170612Record extends UpdatableRecordImpl<HrMedia_20170612Record> implements Record7<Integer, String, String, String, String, String, Integer> {

    private static final long serialVersionUID = 673562289;

    /**
     * Setter for <code>historydb.hr_media_20170612.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>historydb.hr_media_20170612.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>historydb.hr_media_20170612.longtext</code>. 描述
     */
    public void setLongtext(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>historydb.hr_media_20170612.longtext</code>. 描述
     */
    public String getLongtext() {
        return (String) get(1);
    }

    /**
     * Setter for <code>historydb.hr_media_20170612.attrs</code>. 客户属性，可选 字段
     */
    public void setAttrs(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>historydb.hr_media_20170612.attrs</code>. 客户属性，可选 字段
     */
    public String getAttrs() {
        return (String) get(2);
    }

    /**
     * Setter for <code>historydb.hr_media_20170612.title</code>. 模板名称
     */
    public void setTitle(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>historydb.hr_media_20170612.title</code>. 模板名称
     */
    public String getTitle() {
        return (String) get(3);
    }

    /**
     * Setter for <code>historydb.hr_media_20170612.sub_title</code>. 模板子名称
     */
    public void setSubTitle(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>historydb.hr_media_20170612.sub_title</code>. 模板子名称
     */
    public String getSubTitle() {
        return (String) get(4);
    }

    /**
     * Setter for <code>historydb.hr_media_20170612.link</code>. 模板链接
     */
    public void setLink(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>historydb.hr_media_20170612.link</code>. 模板链接
     */
    public String getLink() {
        return (String) get(5);
    }

    /**
     * Setter for <code>historydb.hr_media_20170612.res_id</code>. 资源hr_resource.id
     */
    public void setResId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>historydb.hr_media_20170612.res_id</code>. 资源hr_resource.id
     */
    public Integer getResId() {
        return (Integer) get(6);
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
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, String, String, String, String, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, String, String, String, String, Integer> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrMedia_20170612.HR_MEDIA_20170612.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return HrMedia_20170612.HR_MEDIA_20170612.LONGTEXT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrMedia_20170612.HR_MEDIA_20170612.ATTRS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrMedia_20170612.HR_MEDIA_20170612.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return HrMedia_20170612.HR_MEDIA_20170612.SUB_TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return HrMedia_20170612.HR_MEDIA_20170612.LINK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrMedia_20170612.HR_MEDIA_20170612.RES_ID;
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
    public String value2() {
        return getLongtext();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getAttrs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getSubTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getLink();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getResId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMedia_20170612Record value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMedia_20170612Record value2(String value) {
        setLongtext(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMedia_20170612Record value3(String value) {
        setAttrs(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMedia_20170612Record value4(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMedia_20170612Record value5(String value) {
        setSubTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMedia_20170612Record value6(String value) {
        setLink(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMedia_20170612Record value7(Integer value) {
        setResId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMedia_20170612Record values(Integer value1, String value2, String value3, String value4, String value5, String value6, Integer value7) {
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
     * Create a detached HrMedia_20170612Record
     */
    public HrMedia_20170612Record() {
        super(HrMedia_20170612.HR_MEDIA_20170612);
    }

    /**
     * Create a detached, initialised HrMedia_20170612Record
     */
    public HrMedia_20170612Record(Integer id, String longtext, String attrs, String title, String subTitle, String link, Integer resId) {
        super(HrMedia_20170612.HR_MEDIA_20170612);

        set(0, id);
        set(1, longtext);
        set(2, attrs);
        set(3, title);
        set(4, subTitle);
        set(5, link);
        set(6, resId);
    }
}
