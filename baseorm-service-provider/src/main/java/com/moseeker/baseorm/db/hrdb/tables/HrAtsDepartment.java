/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrAtsDepartmentRecord;

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
 * 部门机构管理表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrAtsDepartment extends TableImpl<HrAtsDepartmentRecord> {

    private static final long serialVersionUID = 1998396373;

    /**
     * The reference instance of <code>hrdb.hr_ats_department</code>
     */
    public static final HrAtsDepartment HR_ATS_DEPARTMENT = new HrAtsDepartment();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrAtsDepartmentRecord> getRecordType() {
        return HrAtsDepartmentRecord.class;
    }

    /**
     * The column <code>hrdb.hr_ats_department.id</code>. 自增id
     */
    public final TableField<HrAtsDepartmentRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "自增id");

    /**
     * The column <code>hrdb.hr_ats_department.company_id</code>. 公司id
     */
    public final TableField<HrAtsDepartmentRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "公司id");

    /**
     * The column <code>hrdb.hr_ats_department.parent_id</code>. 上级部门id
     */
    public final TableField<HrAtsDepartmentRecord, Integer> PARENT_ID = createField("parent_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "上级部门id");

    /**
     * The column <code>hrdb.hr_ats_department.department_code</code>. 部门编码
     */
    public final TableField<HrAtsDepartmentRecord, String> DEPARTMENT_CODE = createField("department_code", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "部门编码");

    /**
     * The column <code>hrdb.hr_ats_department.department_name</code>. 部门名称
     */
    public final TableField<HrAtsDepartmentRecord, String> DEPARTMENT_NAME = createField("department_name", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "部门名称");

    /**
     * The column <code>hrdb.hr_ats_department.create_time</code>. 创建时间
     */
    public final TableField<HrAtsDepartmentRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>hrdb.hr_ats_department.update_time</code>. 更新时间
     */
    public final TableField<HrAtsDepartmentRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "更新时间");

    /**
     * Create a <code>hrdb.hr_ats_department</code> table reference
     */
    public HrAtsDepartment() {
        this("hr_ats_department", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_ats_department</code> table reference
     */
    public HrAtsDepartment(String alias) {
        this(alias, HR_ATS_DEPARTMENT);
    }

    private HrAtsDepartment(String alias, Table<HrAtsDepartmentRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrAtsDepartment(String alias, Table<HrAtsDepartmentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "部门机构管理表");
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
    public Identity<HrAtsDepartmentRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_ATS_DEPARTMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrAtsDepartmentRecord> getPrimaryKey() {
        return Keys.KEY_HR_ATS_DEPARTMENT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrAtsDepartmentRecord>> getKeys() {
        return Arrays.<UniqueKey<HrAtsDepartmentRecord>>asList(Keys.KEY_HR_ATS_DEPARTMENT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrAtsDepartment as(String alias) {
        return new HrAtsDepartment(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public HrAtsDepartment rename(String name) {
        return new HrAtsDepartment(name, null);
    }
}