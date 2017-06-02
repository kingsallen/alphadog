/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrResourceBackupChendi;

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
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrResourceBackupChendiRecord extends TableRecordImpl<HrResourceBackupChendiRecord> implements Record7<Integer, String, Integer, String, Integer, String, Integer> {

    private static final long serialVersionUID = 568156256;

    /**
     * Setter for <code>hrdb.hr_resource_backup_chendi.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_resource_backup_chendi.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_resource_backup_chendi.res_url</code>. 资源链接
     */
    public void setResUrl(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_resource_backup_chendi.res_url</code>. 资源链接
     */
    public String getResUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_resource_backup_chendi.res_type</code>. 0: image  1: video
     */
    public void setResType(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_resource_backup_chendi.res_type</code>. 0: image  1: video
     */
    public Integer getResType() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_resource_backup_chendi.remark</code>. 备注资源
     */
    public void setRemark(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_resource_backup_chendi.remark</code>. 备注资源
     */
    public String getRemark() {
        return (String) get(3);
    }

    /**
     * Setter for <code>hrdb.hr_resource_backup_chendi.company_id</code>. 企业id
     */
    public void setCompanyId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>hrdb.hr_resource_backup_chendi.company_id</code>. 企业id
     */
    public Integer getCompanyId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>hrdb.hr_resource_backup_chendi.title</code>. 资源名称
     */
    public void setTitle(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>hrdb.hr_resource_backup_chendi.title</code>. 资源名称
     */
    public String getTitle() {
        return (String) get(5);
    }

    /**
     * Setter for <code>hrdb.hr_resource_backup_chendi.disable</code>. 0是正常1是删除
     */
    public void setDisable(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>hrdb.hr_resource_backup_chendi.disable</code>. 0是正常1是删除
     */
    public Integer getDisable() {
        return (Integer) get(6);
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, Integer, String, Integer, String, Integer> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row7<Integer, String, Integer, String, Integer, String, Integer> valuesRow() {
        return (Row7) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrResourceBackupChendi.HR_RESOURCE_BACKUP_CHENDI.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return HrResourceBackupChendi.HR_RESOURCE_BACKUP_CHENDI.RES_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrResourceBackupChendi.HR_RESOURCE_BACKUP_CHENDI.RES_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrResourceBackupChendi.HR_RESOURCE_BACKUP_CHENDI.REMARK;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field5() {
        return HrResourceBackupChendi.HR_RESOURCE_BACKUP_CHENDI.COMPANY_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return HrResourceBackupChendi.HR_RESOURCE_BACKUP_CHENDI.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return HrResourceBackupChendi.HR_RESOURCE_BACKUP_CHENDI.DISABLE;
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
        return getResUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getResType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getRemark();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value5() {
        return getCompanyId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getDisable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrResourceBackupChendiRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrResourceBackupChendiRecord value2(String value) {
        setResUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrResourceBackupChendiRecord value3(Integer value) {
        setResType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrResourceBackupChendiRecord value4(String value) {
        setRemark(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrResourceBackupChendiRecord value5(Integer value) {
        setCompanyId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrResourceBackupChendiRecord value6(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrResourceBackupChendiRecord value7(Integer value) {
        setDisable(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrResourceBackupChendiRecord values(Integer value1, String value2, Integer value3, String value4, Integer value5, String value6, Integer value7) {
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
     * Create a detached HrResourceBackupChendiRecord
     */
    public HrResourceBackupChendiRecord() {
        super(HrResourceBackupChendi.HR_RESOURCE_BACKUP_CHENDI);
    }

    /**
     * Create a detached, initialised HrResourceBackupChendiRecord
     */
    public HrResourceBackupChendiRecord(Integer id, String resUrl, Integer resType, String remark, Integer companyId, String title, Integer disable) {
        super(HrResourceBackupChendi.HR_RESOURCE_BACKUP_CHENDI);

        set(0, id);
        set(1, resUrl);
        set(2, resType);
        set(3, remark);
        set(4, companyId);
        set(5, title);
        set(6, disable);
    }
}
