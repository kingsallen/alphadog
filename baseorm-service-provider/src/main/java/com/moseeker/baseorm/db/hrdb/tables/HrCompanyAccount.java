/**
 * This class is generated by jOOQ
 */
package com.moseeker.baseorm.db.hrdb.tables;


import com.moseeker.baseorm.db.hrdb.Hrdb;
import com.moseeker.baseorm.db.hrdb.Keys;
import com.moseeker.baseorm.db.hrdb.tables.records.HrCompanyAccountRecord;
import org.jooq.*;
import org.jooq.impl.TableImpl;

import javax.annotation.Generated;
import java.util.Arrays;
import java.util.List;


/**
 * 账号公司关联记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.8.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrCompanyAccount extends TableImpl<HrCompanyAccountRecord> {

    private static final long serialVersionUID = -451797659;

    /**
     * The reference instance of <code>hrdb.hr_company_account</code>
     */
    public static final HrCompanyAccount HR_COMPANY_ACCOUNT = new HrCompanyAccount();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HrCompanyAccountRecord> getRecordType() {
        return HrCompanyAccountRecord.class;
    }

    /**
     * The column <code>hrdb.hr_company_account.company_id</code>. hr_company.id
     */
    public final TableField<HrCompanyAccountRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "hr_company.id");

    /**
     * The column <code>hrdb.hr_company_account.account_id</code>. user_hr_account.id
     */
    public final TableField<HrCompanyAccountRecord, Integer> ACCOUNT_ID = createField("account_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "user_hr_account.id");

    /**
     * Create a <code>hrdb.hr_company_account</code> table reference
     */
    public HrCompanyAccount() {
        this("hr_company_account", null);
    }

    /**
     * Create an aliased <code>hrdb.hr_company_account</code> table reference
     */
    public HrCompanyAccount(String alias) {
        this(alias, HR_COMPANY_ACCOUNT);
    }

    private HrCompanyAccount(String alias, Table<HrCompanyAccountRecord> aliased) {
        this(alias, aliased, null);
    }

    private HrCompanyAccount(String alias, Table<HrCompanyAccountRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "账号公司关联记录");
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
    public UniqueKey<HrCompanyAccountRecord> getPrimaryKey() {
        return Keys.KEY_HR_COMPANY_ACCOUNT_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HrCompanyAccountRecord>> getKeys() {
        return Arrays.<UniqueKey<HrCompanyAccountRecord>>asList(Keys.KEY_HR_COMPANY_ACCOUNT_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HrCompanyAccount as(String alias) {
        return new HrCompanyAccount(alias, this);
    }

    /**
     * Rename this table
     */
    public HrCompanyAccount rename(String name) {
        return new HrCompanyAccount(name, null);
    }
}
