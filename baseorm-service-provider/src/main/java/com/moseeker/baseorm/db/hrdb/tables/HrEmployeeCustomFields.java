/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeCustomFieldsRecord;

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
 * 员工认证自定义字段表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrEmployeeCustomFields extends TableImpl<HrEmployeeCustomFieldsRecord> {

    private static final long serialVersionUID = -1229931529;

    /**
     * The reference instance of <code>hrdb.hr_employee_custom_fields</code>
     */
    public static final HrEmployeeCustomFields HR_EMPLOYEE_CUSTOM_FIELDS = new HrEmployeeCustomFields();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrEmployeeCustomFieldsRecord> getRecordType() {
        return HrEmployeeCustomFieldsRecord.class;
    }

    /**
     * The column <code>hrdb.hr_employee_custom_fields.id</code>.
     */
    public final TableField<HrEmployeeCustomFieldsRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>hrdb.hr_employee_custom_fields.company_id</code>. sys_company.id, 部门ID
     */
    public final TableField<HrEmployeeCustomFieldsRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "sys_company.id, 部门ID");

    /**
     * The column <code>hrdb.hr_employee_custom_fields.fname</code>. 自定义字段名
     */
    public final TableField<HrEmployeeCustomFieldsRecord, String> FNAME = createField("fname", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false), this, "自定义字段名");

    /**
     * The column <code>hrdb.hr_employee_custom_fields.fvalues</code>. 自定义字段可选值
     */
    public final TableField<HrEmployeeCustomFieldsRecord, String> FVALUES = createField("fvalues", org.jooq.impl.SQLDataType.VARCHAR.length(4096).nullable(false).defaultValue(org.jooq.impl.DSL.field("[]", org.jooq.impl.SQLDataType.VARCHAR)), this, "自定义字段可选值");

    /**
     * The column <code>hrdb.hr_employee_custom_fields.forder</code>. 排序优先级，0:不显示, 正整数由小到大排序
     */
    public final TableField<HrEmployeeCustomFieldsRecord, Integer> FORDER = createField("forder", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "排序优先级，0:不显示, 正整数由小到大排序");

    /**
     * The column <code>hrdb.hr_employee_custom_fields.disable</code>. 是否停用 0:不停用(有效)， 1:停用(无效)
     */
    public final TableField<HrEmployeeCustomFieldsRecord, Byte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否停用 0:不停用(有效)， 1:停用(无效)");

    /**
     * The column <code>hrdb.hr_employee_custom_fields.mandatory</code>. 是否必填 0:不是必填, 1:必填
     */
    public final TableField<HrEmployeeCustomFieldsRecord, Integer> MANDATORY = createField("mandatory", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "是否必填 0:不是必填, 1:必填");

    /**
     * The column <code>hrdb.hr_employee_custom_fields.status</code>. 0: 正常 1: 被删除
     */
    public final TableField<HrEmployeeCustomFieldsRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "0: 正常 1: 被删除");

    /**
     * Create a <code>hrdb.hr_employee_custom_fields</code> table reference
     */
    public HrEmployeeCustomFields() {
        this("hr_employee_custom_fields", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_employee_custom_fields</code> table reference
     */
    public HrEmployeeCustomFields(String alias) {
        this(alias, HR_EMPLOYEE_CUSTOM_FIELDS);
    }

    private HrEmployeeCustomFields(String alias, Table<HrEmployeeCustomFieldsRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrEmployeeCustomFields(String alias, Table<HrEmployeeCustomFieldsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "员工认证自定义字段表");
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
    public Identity<HrEmployeeCustomFieldsRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_EMPLOYEE_CUSTOM_FIELDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrEmployeeCustomFieldsRecord> getPrimaryKey() {
        return Keys.KEY_HR_EMPLOYEE_CUSTOM_FIELDS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrEmployeeCustomFieldsRecord>> getKeys() {
        return Arrays.<UniqueKey<HrEmployeeCustomFieldsRecord>>asList(Keys.KEY_HR_EMPLOYEE_CUSTOM_FIELDS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrEmployeeCustomFields as(String alias) {
        return new HrEmployeeCustomFields(alias, this);
    }

    /**
     * Rename this table
     */
    public HrEmployeeCustomFields rename(String name) {
        return new HrEmployeeCustomFields(name, null);
    }
}
