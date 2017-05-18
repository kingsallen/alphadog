/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrEmployeeSectionRecord;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import org.jooq.*;
import org.jooq.impl.TableImpl;


/**
 * 员工部门表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrEmployeeSection extends TableImpl<HrEmployeeSectionRecord> {

    private static final long serialVersionUID = -553653821;

    /**
     * The reference instance of <code>hrdb.hr_employee_section</code>
     */
    public static final HrEmployeeSection HR_EMPLOYEE_SECTION = new HrEmployeeSection();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrEmployeeSectionRecord> getRecordType() {
        return HrEmployeeSectionRecord.class;
    }

    /**
     * The column <code>hrdb.hr_employee_section.id</code>.
     */
    public final TableField<HrEmployeeSectionRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>hrdb.hr_employee_section.company_id</code>. sys_company.id, 部门ID
     */
    public final TableField<HrEmployeeSectionRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "sys_company.id, 部门ID");

    /**
     * The column <code>hrdb.hr_employee_section.name</code>. 部门名称
     */
    public final TableField<HrEmployeeSectionRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(128).nullable(false), this, "部门名称");

    /**
     * The column <code>hrdb.hr_employee_section.priority</code>. 排序优先级
     */
    public final TableField<HrEmployeeSectionRecord, Integer> PRIORITY = createField("priority", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("0", org.jooq.impl.SQLDataType.INTEGER)), this, "排序优先级");

    /**
     * The column <code>hrdb.hr_employee_section.status</code>. 1:有效, 0:无效
     */
    public final TableField<HrEmployeeSectionRecord, Byte> STATUS = createField("status", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("1", org.jooq.impl.SQLDataType.TINYINT)), this, "1:有效, 0:无效");

    /**
     * Create a <code>hrdb.hr_employee_section</code> table reference
     */
    public HrEmployeeSection() {
        this("hr_employee_section", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_employee_section</code> table reference
     */
    public HrEmployeeSection(String alias) {
        this(alias, HR_EMPLOYEE_SECTION);
    }

    private HrEmployeeSection(String alias, Table<HrEmployeeSectionRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrEmployeeSection(String alias, Table<HrEmployeeSectionRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "员工部门表");
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
    public Identity<HrEmployeeSectionRecord, Integer> getIdentity() {
        return Keys.IDENTITY_HR_EMPLOYEE_SECTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HrEmployeeSectionRecord> getPrimaryKey() {
        return Keys.KEY_HR_EMPLOYEE_SECTION_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrEmployeeSectionRecord>> getKeys() {
        return Arrays.<UniqueKey<HrEmployeeSectionRecord>>asList(Keys.KEY_HR_EMPLOYEE_SECTION_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrEmployeeSection as(String alias) {
        return new HrEmployeeSection(alias, this);
    }

    /**
     * Rename this table
     */
    public HrEmployeeSection rename(String name) {
        return new HrEmployeeSection(name, null);
    }
}
