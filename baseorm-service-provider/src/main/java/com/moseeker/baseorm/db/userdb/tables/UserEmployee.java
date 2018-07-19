/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables;


import com.moseeker.baseorm.db.userdb.Keys;
import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;

import java.sql.Date;
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
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserEmployee extends TableImpl<UserEmployeeRecord> {

    private static final long serialVersionUID = 1902235693;

    /**
     * The reference instance of <code>userdb.user_employee</code>
     */
    public static final UserEmployee USER_EMPLOYEE = new UserEmployee();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserEmployeeRecord> getRecordType() {
        return UserEmployeeRecord.class;
    }

    /**
     * The column <code>userdb.user_employee.id</code>.
     */
    public final TableField<UserEmployeeRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>userdb.user_employee.employeeid</code>. 员工ID
     */
    public final TableField<UserEmployeeRecord, String> EMPLOYEEID = createField("employeeid", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "员工ID");

    /**
     * The column <code>userdb.user_employee.company_id</code>.
     */
    public final TableField<UserEmployeeRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>userdb.user_employee.role_id</code>. sys_role.id
     */
    public final TableField<UserEmployeeRecord, Integer> ROLE_ID = createField("role_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "sys_role.id");

    /**
     * The column <code>userdb.user_employee.wxuser_id</code>. userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考sysuser_id
     */
    public final TableField<UserEmployeeRecord, Integer> WXUSER_ID = createField("wxuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考sysuser_id");

    /**
     * The column <code>userdb.user_employee.sex</code>. 0：未知，1：男，2：女
     */
    public final TableField<UserEmployeeRecord, Byte> SEX = createField("sex", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0：未知，1：男，2：女");

    /**
     * The column <code>userdb.user_employee.ename</code>. 英文名
     */
    public final TableField<UserEmployeeRecord, String> ENAME = createField("ename", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "英文名");

    /**
     * The column <code>userdb.user_employee.efname</code>. 英文姓
     */
    public final TableField<UserEmployeeRecord, String> EFNAME = createField("efname", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "英文姓");

    /**
     * The column <code>userdb.user_employee.cname</code>.
     */
    public final TableField<UserEmployeeRecord, String> CNAME = createField("cname", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

    /**
     * The column <code>userdb.user_employee.cfname</code>. 中文姓
     */
    public final TableField<UserEmployeeRecord, String> CFNAME = createField("cfname", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "中文姓");

    /**
     * The column <code>userdb.user_employee.password</code>. 如果为管理员，存储登陆密码
     */
    public final TableField<UserEmployeeRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "如果为管理员，存储登陆密码");

    /**
     * The column <code>userdb.user_employee.is_admin</code>.
     */
    public final TableField<UserEmployeeRecord, Byte> IS_ADMIN = createField("is_admin", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>userdb.user_employee.status</code>.
     */
    public final TableField<UserEmployeeRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>userdb.user_employee.companybody</code>.
     */
    public final TableField<UserEmployeeRecord, String> COMPANYBODY = createField("companybody", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee.departmentname</code>.
     */
    public final TableField<UserEmployeeRecord, String> DEPARTMENTNAME = createField("departmentname", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee.groupname</code>.
     */
    public final TableField<UserEmployeeRecord, String> GROUPNAME = createField("groupname", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee.position</code>.
     */
    public final TableField<UserEmployeeRecord, String> POSITION = createField("position", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee.employdate</code>.
     */
    public final TableField<UserEmployeeRecord, Date> EMPLOYDATE = createField("employdate", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>userdb.user_employee.managername</code>.
     */
    public final TableField<UserEmployeeRecord, String> MANAGERNAME = createField("managername", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee.city</code>.
     */
    public final TableField<UserEmployeeRecord, String> CITY = createField("city", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee.birthday</code>.
     */
    public final TableField<UserEmployeeRecord, Date> BIRTHDAY = createField("birthday", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>userdb.user_employee.retiredate</code>.
     */
    public final TableField<UserEmployeeRecord, Date> RETIREDATE = createField("retiredate", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>userdb.user_employee.education</code>.
     */
    public final TableField<UserEmployeeRecord, String> EDUCATION = createField("education", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee.address</code>.
     */
    public final TableField<UserEmployeeRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee.idcard</code>.
     */
    public final TableField<UserEmployeeRecord, String> IDCARD = createField("idcard", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee.mobile</code>.
     */
    public final TableField<UserEmployeeRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee.award</code>. 员工积分
     */
    public final TableField<UserEmployeeRecord, Integer> AWARD = createField("award", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "员工积分");

    /**
     * The column <code>userdb.user_employee.binding_time</code>.
     */
    public final TableField<UserEmployeeRecord, Timestamp> BINDING_TIME = createField("binding_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>userdb.user_employee.email</code>. email
     */
    public final TableField<UserEmployeeRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "email");

    /**
     * The column <code>userdb.user_employee.activation</code>. '员工认证激活状态，0：认证成功，1：认证后取消认证 2：认证失败 3：未认证 4：认证后又认证了其他公司导致本条数据变成未认证
     */
    public final TableField<UserEmployeeRecord, Byte> ACTIVATION = createField("activation", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("3", org.jooq.impl.SQLDataType.TINYINT)), this, "'员工认证激活状态，0：认证成功，1：认证后取消认证 2：认证失败 3：未认证 4：认证后又认证了其他公司导致本条数据变成未认证");

    /**
     * The column <code>userdb.user_employee.activation_code</code>. 激活码
     */
    public final TableField<UserEmployeeRecord, String> ACTIVATION_CODE = createField("activation_code", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "激活码");

    /**
     * The column <code>userdb.user_employee.disable</code>. 是否禁用0：可用1：禁用
     */
    public final TableField<UserEmployeeRecord, Byte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否禁用0：可用1：禁用");

    /**
     * The column <code>userdb.user_employee.create_time</code>. 创建时间
     */
    public final TableField<UserEmployeeRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>userdb.user_employee.update_time</code>. 修改时间
     */
    public final TableField<UserEmployeeRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>userdb.user_employee.auth_level</code>. 雇主认证：0代表刚注册, 1代表提交了基本信息, 2代表邮箱认证通过, 3代表通过人工审核
     */
    public final TableField<UserEmployeeRecord, Byte> AUTH_LEVEL = createField("auth_level", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "雇主认证：0代表刚注册, 1代表提交了基本信息, 2代表邮箱认证通过, 3代表通过人工审核");

    /**
     * The column <code>userdb.user_employee.register_time</code>. 注册时间
     */
    public final TableField<UserEmployeeRecord, Timestamp> REGISTER_TIME = createField("register_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "注册时间");

    /**
     * The column <code>userdb.user_employee.register_ip</code>. 注册IP
     */
    public final TableField<UserEmployeeRecord, String> REGISTER_IP = createField("register_ip", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "注册IP");

    /**
     * The column <code>userdb.user_employee.last_login_time</code>. 最近登录时间
     */
    public final TableField<UserEmployeeRecord, Timestamp> LAST_LOGIN_TIME = createField("last_login_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "最近登录时间");

    /**
     * The column <code>userdb.user_employee.last_login_ip</code>. 最近登录IP
     */
    public final TableField<UserEmployeeRecord, String> LAST_LOGIN_IP = createField("last_login_ip", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "最近登录IP");

    /**
     * The column <code>userdb.user_employee.login_count</code>. 登录次数
     */
    public final TableField<UserEmployeeRecord, Long> LOGIN_COUNT = createField("login_count", org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "登录次数");

    /**
     * The column <code>userdb.user_employee.source</code>. 来源，0:默认 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 8:hr导入员工 9:hr添加的员工
     */
    public final TableField<UserEmployeeRecord, Byte> SOURCE = createField("source", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "来源，0:默认 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 8:hr导入员工 9:hr添加的员工");

    /**
     * The column <code>userdb.user_employee.download_token</code>. 下载行业报告的校验token
     */
    public final TableField<UserEmployeeRecord, String> DOWNLOAD_TOKEN = createField("download_token", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "下载行业报告的校验token");

    /**
     * The column <code>userdb.user_employee.hr_wxuser_id</code>. hr招聘助手的微信ID，wx_group_user.id
     */
    public final TableField<UserEmployeeRecord, Integer> HR_WXUSER_ID = createField("hr_wxuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr招聘助手的微信ID，wx_group_user.id");

    /**
     * The column <code>userdb.user_employee.custom_field</code>. 配置的自定义认证名称对应的内容
     */
    public final TableField<UserEmployeeRecord, String> CUSTOM_FIELD = createField("custom_field", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "配置的自定义认证名称对应的内容");

    /**
     * The column <code>userdb.user_employee.is_rp_sent</code>. 是否拿过员工认证红包 0: 没有 1:有
     */
    public final TableField<UserEmployeeRecord, Byte> IS_RP_SENT = createField("is_rp_sent", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否拿过员工认证红包 0: 没有 1:有");

    /**
     * The column <code>userdb.user_employee.sysuser_id</code>. sysuser.id, 用户ID
     */
    public final TableField<UserEmployeeRecord, Integer> SYSUSER_ID = createField("sysuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "sysuser.id, 用户ID");

    /**
     * The column <code>userdb.user_employee.position_id</code>. hr_employee_position.id, 职能ID
     */
    public final TableField<UserEmployeeRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_employee_position.id, 职能ID");

    /**
     * The column <code>userdb.user_employee.section_id</code>. hr_employee_section.id, 部门ID
     */
    public final TableField<UserEmployeeRecord, Integer> SECTION_ID = createField("section_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_employee_section.id, 部门ID");

    /**
     * The column <code>userdb.user_employee.email_isvalid</code>. 是否认证了1：是, 0：否
     */
    public final TableField<UserEmployeeRecord, Byte> EMAIL_ISVALID = createField("email_isvalid", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否认证了1：是, 0：否");

    /**
     * The column <code>userdb.user_employee.auth_method</code>. 员工认证途径 0:使用邮箱认证 1:使用自定义认证 2:使用问答认证
     */
    public final TableField<UserEmployeeRecord, Byte> AUTH_METHOD = createField("auth_method", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "员工认证途径 0:使用邮箱认证 1:使用自定义认证 2:使用问答认证");

    /**
     * The column <code>userdb.user_employee.custom_field_values</code>. 自定 义字段键值, 结构[{&lt;id&gt;: "&lt;value&gt;"},{...},...]
     */
    public final TableField<UserEmployeeRecord, String> CUSTOM_FIELD_VALUES = createField("custom_field_values", org.jooq.impl.SQLDataType.VARCHAR.length(4096).nullable(false).defaultValue(org.jooq.impl.DSL.inline("[]", org.jooq.impl.SQLDataType.VARCHAR)), this, "自定 义字段键值, 结构[{<id>: \"<value>\"},{...},...]");

    /**
     * The column <code>userdb.user_employee.team_id</code>. 团队id
     */
    public final TableField<UserEmployeeRecord, Integer> TEAM_ID = createField("team_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "团队id");

    /**
     * The column <code>userdb.user_employee.job_grade</code>. 职级
     */
    public final TableField<UserEmployeeRecord, Byte> JOB_GRADE = createField("job_grade", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "职级");

    /**
     * The column <code>userdb.user_employee.city_code</code>. 城市code
     */
    public final TableField<UserEmployeeRecord, Integer> CITY_CODE = createField("city_code", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "城市code");

    /**
     * The column <code>userdb.user_employee.degree</code>. 学历
     */
    public final TableField<UserEmployeeRecord, Byte> DEGREE = createField("degree", org.jooq.impl.SQLDataType.TINYINT.nullable(false), this, "学历");

    /**
     * Create a <code>userdb.user_employee</code> table reference
     */
    public UserEmployee() {
        this("user_employee", null);
    }

    /**
     * Create an aliased <code>userdb.user_employee</code> table reference
     */
    public UserEmployee(String alias) {
        this(alias, USER_EMPLOYEE);
    }

    private UserEmployee(String alias, Table<UserEmployeeRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserEmployee(String alias, Table<UserEmployeeRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Userdb.USERDB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<UserEmployeeRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_EMPLOYEE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserEmployeeRecord> getPrimaryKey() {
        return Keys.KEY_USER_EMPLOYEE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserEmployeeRecord>> getKeys() {
        return Arrays.<UniqueKey<UserEmployeeRecord>>asList(Keys.KEY_USER_EMPLOYEE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployee as(String alias) {
        return new UserEmployee(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserEmployee rename(String name) {
        return new UserEmployee(name, null);
    }
}
