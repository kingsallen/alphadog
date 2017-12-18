/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrMediaBackupChendi;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrMediaBackupChendiRecord extends TableRecordImpl<HrMediaBackupChendiRecord> implements Record7<Integer, String, String, String, String, String, Integer> {

    private static final long serialVersionUID = -1373923537;

    /**
     * Setter for <code>hrdb.hr_media_backup_chendi.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_media_backup_chendi.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_media_backup_chendi.longtext</code>. 描述
     */
    public void setLongtext(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_media_backup_chendi.longtext</code>. 描述
     */
    public String getLongtext() {
        return (String) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_media_backup_chendi.attrs</code>. 客户属性，可选 字段
     */
    public void setAttrs(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_media_backup_chendi.attrs</code>. 客户属性，可选 字段
     */
    public String getAttrs() {
        return (String) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_media_backup_chendi.title</code>. 模板名称
     */
    public void setTitle(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_media_backup_chendi.title</code>. 模板名称
     */
    public String getTitle() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_media_backup_chendi.sub_title</code>. 模板子名称
     */
    public void setSubTitle(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_media_backup_chendi.sub_title</code>. 模板子名称
     */
    public String getSubTitle() {
        return (String) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_media_backup_chendi.link</code>. 模板链接
     */
    public void setLink(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_media_backup_chendi.link</code>. 模板链接
     */
    public String getLink() {
        return (String) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_media_backup_chendi.res_id</code>. 资源hr_resource.id
     */
    public void setResId(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_media_backup_chendi.res_id</code>. 资源hr_resource.id
     */
    public Integer getResId() {
        return (Integer) get(6);
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
        return HrMediaBackupChendi.HR_MEDIA_BACKUP_CHENDI.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return HrMediaBackupChendi.HR_MEDIA_BACKUP_CHENDI.LONGTEXT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return HrMediaBackupChendi.HR_MEDIA_BACKUP_CHENDI.ATTRS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrMediaBackupChendi.HR_MEDIA_BACKUP_CHENDI.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return HrMediaBackupChendi.HR_MEDIA_BACKUP_CHENDI.SUB_TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return HrMediaBackupChendi.HR_MEDIA_BACKUP_CHENDI.LINK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrMediaBackupChendi.HR_MEDIA_BACKUP_CHENDI.RES_ID;
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
    public HrMediaBackupChendiRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMediaBackupChendiRecord value2(String value) {
        setLongtext(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMediaBackupChendiRecord value3(String value) {
        setAttrs(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMediaBackupChendiRecord value4(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMediaBackupChendiRecord value5(String value) {
        setSubTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMediaBackupChendiRecord value6(String value) {
        setLink(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMediaBackupChendiRecord value7(Integer value) {
        setResId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrMediaBackupChendiRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, Integer value7) {
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
     * Create a detached HrMediaBackupChendiRecord
     */
    public HrMediaBackupChendiRecord() {
        super(HrMediaBackupChendi.HR_MEDIA_BACKUP_CHENDI);
    }

    /**
     * Create a detached, initialised HrMediaBackupChendiRecord
     */
    public HrMediaBackupChendiRecord(Integer id, String longtext, String attrs, String title, String subTitle, String link, Integer resId) {
        super(HrMediaBackupChendi.HR_MEDIA_BACKUP_CHENDI);

        set(0, id);
        set(1, longtext);
        set(2, attrs);
        set(3, title);
        set(4, subTitle);
        set(5, link);
        set(6, resId);
    }
}
