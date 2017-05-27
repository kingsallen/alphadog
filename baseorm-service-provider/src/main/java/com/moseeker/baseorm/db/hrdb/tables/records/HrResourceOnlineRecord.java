/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables.records;


import com.moseeker.baseorm.db.hrdb.tables.HrResourceOnline;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record4;
import org.jooq.Row4;
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
public class HrResourceOnlineRecord extends TableRecordImpl<HrResourceOnlineRecord> implements Record4<Integer, String, Integer, String> {

    private static final long serialVersionUID = 2058399203;

    /**
     * Setter for <code>hrdb.hr_resource_online.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>hrdb.hr_resource_online.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>hrdb.hr_resource_online.res_url</code>. 资源链接
     */
    public void setResUrl(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>hrdb.hr_resource_online.res_url</code>. 资源链接
     */
    public String getResUrl() {
        return (String) get(1);
    }

    /**
     * Setter for <code>hrdb.hr_resource_online.res_type</code>. 0: image  1: video
     */
    public void setResType(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>hrdb.hr_resource_online.res_type</code>. 0: image  1: video
     */
    public Integer getResType() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>hrdb.hr_resource_online.remark</code>. 备注资源
     */
    public void setRemark(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>hrdb.hr_resource_online.remark</code>. 备注资源
     */
    public String getRemark() {
        return (String) get(3);
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, Integer, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, Integer, String> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return HrResourceOnline.HR_RESOURCE_ONLINE.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return HrResourceOnline.HR_RESOURCE_ONLINE.RES_URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return HrResourceOnline.HR_RESOURCE_ONLINE.RES_TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return HrResourceOnline.HR_RESOURCE_ONLINE.REMARK;
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
    public HrResourceOnlineRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrResourceOnlineRecord value2(String value) {
        setResUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrResourceOnlineRecord value3(Integer value) {
        setResType(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrResourceOnlineRecord value4(String value) {
        setRemark(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrResourceOnlineRecord values(Integer value1, String value2, Integer value3, String value4) {
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
     * Create a detached HrResourceOnlineRecord
     */
    public HrResourceOnlineRecord() {
        super(HrResourceOnline.HR_RESOURCE_ONLINE);
    }

    /**
     * Create a detached, initialised HrResourceOnlineRecord
     */
    public HrResourceOnlineRecord(Integer id, String resUrl, Integer resType, String remark) {
        super(HrResourceOnline.HR_RESOURCE_ONLINE);

        set(0, id);
        set(1, resUrl);
        set(2, resType);
        set(3, remark);
    }
}
