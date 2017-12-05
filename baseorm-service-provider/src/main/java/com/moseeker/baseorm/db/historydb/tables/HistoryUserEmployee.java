/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.historydb.tables;


import com.moseeker.baseorm.db.historydb.Historydb;
import com.moseeker.baseorm.db.historydb.Keys;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryUserEmployeeRecord;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * 员工信息归档表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HistoryUserEmployee extends TableImpl<HistoryUserEmployeeRecord> {

    private static final long serialVersionUID = 266887831;

    /**
     * The reference instance of <code>historydb.history_user_employee</code>
     */
    public static final HistoryUserEmployee HISTORY_USER_EMPLOYEE = new HistoryUserEmployee();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<HistoryUserEmployeeRecord> getRecordType() {
        return HistoryUserEmployeeRecord.class;
    }

    /**
     * The column <code>historydb.history_user_employee.id</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>historydb.history_user_employee.employeeid</code>. 员工ID
     */
    public final TableField<HistoryUserEmployeeRecord, String> EMPLOYEEID = createField("employeeid", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "员工ID");

    /**
     * The column <code>historydb.history_user_employee.company_id</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>historydb.history_user_employee.role_id</code>. sys_role.id
     */
    public final TableField<HistoryUserEmployeeRecord, Integer> ROLE_ID = createField("role_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "sys_role.id");

    /**
     * The column <code>historydb.history_user_employee.wxuser_id</code>. userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考sysuser_id
     */
    public final TableField<HistoryUserEmployeeRecord, Integer> WXUSER_ID = createField("wxuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考sysuser_id");

    /**
     * The column <code>historydb.history_user_employee.sex</code>. 0：未知，1：男，2：女
     */
    public final TableField<HistoryUserEmployeeRecord, Byte> SEX = createField("sex", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0：未知，1：男，2：女");

    /**
     * The column <code>historydb.history_user_employee.ename</code>. 英文名
     */
    public final TableField<HistoryUserEmployeeRecord, String> ENAME = createField("ename", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "英文名");

    /**
     * The column <code>historydb.history_user_employee.efname</code>. 英文姓
     */
    public final TableField<HistoryUserEmployeeRecord, String> EFNAME = createField("efname", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "英文姓");

    /**
     * The column <code>historydb.history_user_employee.cname</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, String> CNAME = createField("cname", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

    /**
     * The column <code>historydb.history_user_employee.cfname</code>. 中文姓
     */
    public final TableField<HistoryUserEmployeeRecord, String> CFNAME = createField("cfname", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "中文姓");

    /**
     * The column <code>historydb.history_user_employee.password</code>. 如果为管理员，存储登陆密码
     */
    public final TableField<HistoryUserEmployeeRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "如果为管理员，存储登陆密码");

    /**
     * The column <code>historydb.history_user_employee.is_admin</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, Byte> IS_ADMIN = createField("is_admin", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>historydb.history_user_employee.status</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>historydb.history_user_employee.companybody</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, String> COMPANYBODY = createField("companybody", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>historydb.history_user_employee.departmentname</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, String> DEPARTMENTNAME = createField("departmentname", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>historydb.history_user_employee.groupname</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, String> GROUPNAME = createField("groupname", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>historydb.history_user_employee.position</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, String> POSITION = createField("position", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>historydb.history_user_employee.employdate</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, Date> EMPLOYDATE = createField("employdate", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>historydb.history_user_employee.managername</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, String> MANAGERNAME = createField("managername", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>historydb.history_user_employee.city</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, String> CITY = createField("city", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>historydb.history_user_employee.birthday</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, Date> BIRTHDAY = createField("birthday", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>historydb.history_user_employee.retiredate</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, Date> RETIREDATE = createField("retiredate", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>historydb.history_user_employee.education</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, String> EDUCATION = createField("education", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>historydb.history_user_employee.address</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>historydb.history_user_employee.idcard</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, String> IDCARD = createField("idcard", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>historydb.history_user_employee.mobile</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>historydb.history_user_employee.award</code>. 员工积分
     */
    public final TableField<HistoryUserEmployeeRecord, Integer> AWARD = createField("award", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "员工积分");

    /**
     * The column <code>historydb.history_user_employee.binding_time</code>.
     */
    public final TableField<HistoryUserEmployeeRecord, Timestamp> BINDING_TIME = createField("binding_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>historydb.history_user_employee.email</code>. email
     */
    public final TableField<HistoryUserEmployeeRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "email");

    /**
     * The column <code>historydb.history_user_employee.activation</code>. 员工认证激活状态，0 ：认证成功，1：认证后取消认证 2：认证失败 3：未认证 4：认证后又认证了其他公司导致本条数据变成未认证
     */
    public final TableField<HistoryUserEmployeeRecord, Byte> ACTIVATION = createField("activation", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("3", org.jooq.impl.SQLDataType.TINYINT)), this, "员工认证激活状态，0 ：认证成功，1：认证后取消认证 2：认证失败 3：未认证 4：认证后又认证了其他公司导致本条数据变成未认证");

    /**
     * The column <code>historydb.history_user_employee.activation_code</code>. 激活码
     */
    public final TableField<HistoryUserEmployeeRecord, String> ACTIVATION_CODE = createField("activation_code", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "激活码");

    /**
     * The column <code>historydb.history_user_employee.disable</code>. 是否禁用0：可用1：禁用
     */
    public final TableField<HistoryUserEmployeeRecord, Byte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否禁用0：可用1：禁用");

    /**
     * The column <code>historydb.history_user_employee.create_time</code>. 创建时间
     */
    public final TableField<HistoryUserEmployeeRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>historydb.history_user_employee.update_time</code>. 修改时间
     */
    public final TableField<HistoryUserEmployeeRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>historydb.history_user_employee.auth_level</code>. 雇主认证：0代表刚注册, 1代表提交了基本信息, 2代表邮箱认证通过, 3代表通过人工审核
     */
    public final TableField<HistoryUserEmployeeRecord, Byte> AUTH_LEVEL = createField("auth_level", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "雇主认证：0代表刚注册, 1代表提交了基本信息, 2代表邮箱认证通过, 3代表通过人工审核");

    /**
     * The column <code>historydb.history_user_employee.register_time</code>. 注册时间
     */
    public final TableField<HistoryUserEmployeeRecord, Timestamp> REGISTER_TIME = createField("register_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "注册时间");

    /**
     * The column <code>historydb.history_user_employee.register_ip</code>. 注册IP
     */
    public final TableField<HistoryUserEmployeeRecord, String> REGISTER_IP = createField("register_ip", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "注册IP");

    /**
     * The column <code>historydb.history_user_employee.last_login_time</code>. 最近登录时间
     */
    public final TableField<HistoryUserEmployeeRecord, Timestamp> LAST_LOGIN_TIME = createField("last_login_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "最近登录时间");

    /**
     * The column <code>historydb.history_user_employee.last_login_ip</code>. 最近登录IP
     */
    public final TableField<HistoryUserEmployeeRecord, String> LAST_LOGIN_IP = createField("last_login_ip", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "最近登录IP");

    /**
     * The column <code>historydb.history_user_employee.login_count</code>. 登录次数
     */
    public final TableField<HistoryUserEmployeeRecord, Long> LOGIN_COUNT = createField("login_count", org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "登录次数");

    /**
     * The column <code>historydb.history_user_employee.source</code>. 来源，0:默认 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 8:hr导入员工 9:hr添加的员工
     */
    public final TableField<HistoryUserEmployeeRecord, Byte> SOURCE = createField("source", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "来源，0:默认 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 8:hr导入员工 9:hr添加的员工");

    /**
     * The column <code>historydb.history_user_employee.download_token</code>. 下载行业报告的校验token
     */
    public final TableField<HistoryUserEmployeeRecord, String> DOWNLOAD_TOKEN = createField("download_token", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "下载行业报告的校验token");

    /**
     * The column <code>historydb.history_user_employee.hr_wxuser_id</code>. hr招聘助手的微信ID，wx_group_user.id
     */
    public final TableField<HistoryUserEmployeeRecord, Integer> HR_WXUSER_ID = createField("hr_wxuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr招聘助手的微信ID，wx_group_user.id");

    /**
     * The column <code>historydb.history_user_employee.custom_field</code>. 配置的自定义认证名称对应的内容
     */
    public final TableField<HistoryUserEmployeeRecord, String> CUSTOM_FIELD = createField("custom_field", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "配置的自定义认证名称对应的内容");

    /**
     * The column <code>historydb.history_user_employee.is_rp_sent</code>. 是否拿过员工认证红包 0: 没有 1:有
     */
    public final TableField<HistoryUserEmployeeRecord, Byte> IS_RP_SENT = createField("is_rp_sent", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否拿过员工认证红包 0: 没有 1:有");

    /**
     * The column <code>historydb.history_user_employee.sysuser_id</code>. sysuser.id, 用户ID
     */
    public final TableField<HistoryUserEmployeeRecord, Integer> SYSUSER_ID = createField("sysuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "sysuser.id, 用户ID");

    /**
     * The column <code>historydb.history_user_employee.position_id</code>. hr_employee_position.id, 职能ID
     */
    public final TableField<HistoryUserEmployeeRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_employee_position.id, 职能ID");

    /**
     * The column <code>historydb.history_user_employee.section_id</code>. hr_employee_section.id, 部门ID
     */
    public final TableField<HistoryUserEmployeeRecord, Integer> SECTION_ID = createField("section_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_employee_section.id, 部门ID");

    /**
     * The column <code>historydb.history_user_employee.email_isvalid</code>. 是否认证了1：是, 0：否
     */
    public final TableField<HistoryUserEmployeeRecord, Byte> EMAIL_ISVALID = createField("email_isvalid", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否认证了1：是, 0：否");

    /**
     * The column <code>historydb.history_user_employee.auth_method</code>. 员工认证途径 0:使用邮箱认证 1:使用自定义认证 2:使用问答认证
     */
    public final TableField<HistoryUserEmployeeRecord, Byte> AUTH_METHOD = createField("auth_method", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "员工认证途径 0:使用邮箱认证 1:使用自定义认证 2:使用问答认证");

    /**
     * The column <code>historydb.history_user_employee.custom_field_values</code>. 自定 义字段键值, 结构[{&lt;id&gt;: "&lt;value&gt;"},{...},...]
     */
    public final TableField<HistoryUserEmployeeRecord, String> CUSTOM_FIELD_VALUES = createField("custom_field_values", org.jooq.impl.SQLDataType.VARCHAR.length(4096).nullable(false).defaultValue(org.jooq.impl.DSL.inline("[]", org.jooq.impl.SQLDataType.VARCHAR)), this, "自定 义字段键值, 结构[{<id>: \"<value>\"},{...},...]");

    /**
     * Create a <code>historydb.history_user_employee</code> table reference
     */
    public HistoryUserEmployee() {
        this("history_user_employee", null);
    }

    /**
     * Create an aliased <code>historydb.history_user_employee</code> table reference
     */
    public HistoryUserEmployee(String alias) {
        this(alias, HISTORY_USER_EMPLOYEE);
    }

    private HistoryUserEmployee(String alias, Table<HistoryUserEmployeeRecord> aliased) {
        this(alias, aliased, null);
    }

    private HistoryUserEmployee(String alias, Table<HistoryUserEmployeeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "员工信息归档表");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Historydb.HISTORYDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<HistoryUserEmployeeRecord> getPrimaryKey() {
        return Keys.KEY_HISTORY_USER_EMPLOYEE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<HistoryUserEmployeeRecord>> getKeys() {
        return Arrays.<UniqueKey<HistoryUserEmployeeRecord>>asList(Keys.KEY_HISTORY_USER_EMPLOYEE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoryUserEmployee as(String alias) {
        return new HistoryUserEmployee(alias, this);
    }

    /**
     * Rename this table
     */

    public HistoryUserEmployee rename(String name) {
        return new HistoryUserEmployee(name, null);
    }
}
