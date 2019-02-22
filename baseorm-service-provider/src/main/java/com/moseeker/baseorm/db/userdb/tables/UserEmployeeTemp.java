/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables;


import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeTempRecord;

import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserEmployeeTemp extends TableImpl<UserEmployeeTempRecord> {

    private static final long serialVersionUID = -362287630;

    /**
     * The reference instance of <code>userdb.user_employee_temp</code>
     */
    public static final UserEmployeeTemp USER_EMPLOYEE_TEMP = new UserEmployeeTemp();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserEmployeeTempRecord> getRecordType() {
        return UserEmployeeTempRecord.class;
    }

    /**
     * The column <code>userdb.user_employee_temp.id</code>.
     */
    public final TableField<UserEmployeeTempRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.employeeid</code>. 员工ID
     */
    public final TableField<UserEmployeeTempRecord, String> EMPLOYEEID = createField("employeeid", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "员工ID");

    /**
     * The column <code>userdb.user_employee_temp.company_id</code>.
     */
    public final TableField<UserEmployeeTempRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.role_id</code>. sys_role.id
     */
    public final TableField<UserEmployeeTempRecord, Integer> ROLE_ID = createField("role_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "sys_role.id");

    /**
     * The column <code>userdb.user_employee_temp.wxuser_id</code>. userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考sysuser_id
     */
    public final TableField<UserEmployeeTempRecord, Integer> WXUSER_ID = createField("wxuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考sysuser_id");

    /**
     * The column <code>userdb.user_employee_temp.sex</code>. 0：未知，1：男，2：女
     */
    public final TableField<UserEmployeeTempRecord, Byte> SEX = createField("sex", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0：未知，1：男，2：女");

    /**
     * The column <code>userdb.user_employee_temp.ename</code>. 英文名
     */
    public final TableField<UserEmployeeTempRecord, String> ENAME = createField("ename", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "英文名");

    /**
     * The column <code>userdb.user_employee_temp.efname</code>. 英文姓
     */
    public final TableField<UserEmployeeTempRecord, String> EFNAME = createField("efname", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "英文姓");

    /**
     * The column <code>userdb.user_employee_temp.cname</code>.
     */
    public final TableField<UserEmployeeTempRecord, String> CNAME = createField("cname", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

    /**
     * The column <code>userdb.user_employee_temp.cfname</code>. 中文姓
     */
    public final TableField<UserEmployeeTempRecord, String> CFNAME = createField("cfname", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "中文姓");

    /**
     * The column <code>userdb.user_employee_temp.password</code>. 如果为管理员，存储登陆密码
     */
    public final TableField<UserEmployeeTempRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "如果为管理员，存储登陆密码");

    /**
     * The column <code>userdb.user_employee_temp.is_admin</code>.
     */
    public final TableField<UserEmployeeTempRecord, Byte> IS_ADMIN = createField("is_admin", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.status</code>.
     */
    public final TableField<UserEmployeeTempRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.companybody</code>.
     */
    public final TableField<UserEmployeeTempRecord, String> COMPANYBODY = createField("companybody", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.departmentname</code>.
     */
    public final TableField<UserEmployeeTempRecord, String> DEPARTMENTNAME = createField("departmentname", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.groupname</code>.
     */
    public final TableField<UserEmployeeTempRecord, String> GROUPNAME = createField("groupname", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.position</code>.
     */
    public final TableField<UserEmployeeTempRecord, String> POSITION = createField("position", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.employdate</code>.
     */
    public final TableField<UserEmployeeTempRecord, Date> EMPLOYDATE = createField("employdate", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>userdb.user_employee_temp.managername</code>.
     */
    public final TableField<UserEmployeeTempRecord, String> MANAGERNAME = createField("managername", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.city</code>.
     */
    public final TableField<UserEmployeeTempRecord, String> CITY = createField("city", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.birthday</code>.
     */
    public final TableField<UserEmployeeTempRecord, Date> BIRTHDAY = createField("birthday", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>userdb.user_employee_temp.retiredate</code>.
     */
    public final TableField<UserEmployeeTempRecord, Date> RETIREDATE = createField("retiredate", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>userdb.user_employee_temp.education</code>.
     */
    public final TableField<UserEmployeeTempRecord, String> EDUCATION = createField("education", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.address</code>.
     */
    public final TableField<UserEmployeeTempRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.idcard</code>.
     */
    public final TableField<UserEmployeeTempRecord, String> IDCARD = createField("idcard", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.mobile</code>.
     */
    public final TableField<UserEmployeeTempRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_temp.award</code>. 员工积分
     */
    public final TableField<UserEmployeeTempRecord, Integer> AWARD = createField("award", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "员工积分");

    /**
     * The column <code>userdb.user_employee_temp.binding_time</code>.
     */
    public final TableField<UserEmployeeTempRecord, Timestamp> BINDING_TIME = createField("binding_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>userdb.user_employee_temp.email</code>. email
     */
    public final TableField<UserEmployeeTempRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "email");

    /**
     * The column <code>userdb.user_employee_temp.activation</code>. '员工认证激活状态，0：认证成功，1：认证后取消认证 2：认证失败 3：未认证 4：认证后又认证了其他公司导致本条数据变成未认证 5:取消关注，再关注
     */
    public final TableField<UserEmployeeTempRecord, Byte> ACTIVATION = createField("activation", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("3", org.jooq.impl.SQLDataType.TINYINT)), this, "'员工认证激活状态，0：认证成功，1：认证后取消认证 2：认证失败 3：未认证 4：认证后又认证了其他公司导致本条数据变成未认证 5:取消关注，再关注");

    /**
     * The column <code>userdb.user_employee_temp.activation_code</code>. 激活码
     */
    public final TableField<UserEmployeeTempRecord, String> ACTIVATION_CODE = createField("activation_code", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "激活码");

    /**
     * The column <code>userdb.user_employee_temp.disable</code>. 是否禁用0：可用1：禁用
     */
    public final TableField<UserEmployeeTempRecord, Byte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否禁用0：可用1：禁用");

    /**
     * The column <code>userdb.user_employee_temp.create_time</code>. 创建时间
     */
    public final TableField<UserEmployeeTempRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>userdb.user_employee_temp.update_time</code>. 修改时间
     */
    public final TableField<UserEmployeeTempRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>userdb.user_employee_temp.auth_level</code>. 雇主认证：0代表刚注册, 1代表提交了基本信息, 2代表邮箱认证通过, 3代表通过人工审核
     */
    public final TableField<UserEmployeeTempRecord, Byte> AUTH_LEVEL = createField("auth_level", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "雇主认证：0代表刚注册, 1代表提交了基本信息, 2代表邮箱认证通过, 3代表通过人工审核");

    /**
     * The column <code>userdb.user_employee_temp.register_time</code>. 注册时间
     */
    public final TableField<UserEmployeeTempRecord, Timestamp> REGISTER_TIME = createField("register_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "注册时间");

    /**
     * The column <code>userdb.user_employee_temp.register_ip</code>. 注册IP
     */
    public final TableField<UserEmployeeTempRecord, String> REGISTER_IP = createField("register_ip", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "注册IP");

    /**
     * The column <code>userdb.user_employee_temp.last_login_time</code>. 最近登录时间
     */
    public final TableField<UserEmployeeTempRecord, Timestamp> LAST_LOGIN_TIME = createField("last_login_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "最近登录时间");

    /**
     * The column <code>userdb.user_employee_temp.last_login_ip</code>. 最近登录IP
     */
    public final TableField<UserEmployeeTempRecord, String> LAST_LOGIN_IP = createField("last_login_ip", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "最近登录IP");

    /**
     * The column <code>userdb.user_employee_temp.login_count</code>. 登录次数
     */
    public final TableField<UserEmployeeTempRecord, Long> LOGIN_COUNT = createField("login_count", org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "登录次数");

    /**
     * The column <code>userdb.user_employee_temp.source</code>. 来源，0:默认 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 8:hr导入员工 9:hr添加的员工
     */
    public final TableField<UserEmployeeTempRecord, Byte> SOURCE = createField("source", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "来源，0:默认 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 8:hr导入员工 9:hr添加的员工");

    /**
     * The column <code>userdb.user_employee_temp.download_token</code>. 下载行业报告的校验token
     */
    public final TableField<UserEmployeeTempRecord, String> DOWNLOAD_TOKEN = createField("download_token", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "下载行业报告的校验token");

    /**
     * The column <code>userdb.user_employee_temp.hr_wxuser_id</code>. hr招聘助手的微信ID，wx_group_user.id
     */
    public final TableField<UserEmployeeTempRecord, Integer> HR_WXUSER_ID = createField("hr_wxuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr招聘助手的微信ID，wx_group_user.id");

    /**
     * The column <code>userdb.user_employee_temp.custom_field</code>. 配置的自定义认证名称对应的内容
     */
    public final TableField<UserEmployeeTempRecord, String> CUSTOM_FIELD = createField("custom_field", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "配置的自定义认证名称对应的内容");

    /**
     * The column <code>userdb.user_employee_temp.is_rp_sent</code>. 是否拿过员工认证红包 0: 没有 1:有
     */
    public final TableField<UserEmployeeTempRecord, Byte> IS_RP_SENT = createField("is_rp_sent", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否拿过员工认证红包 0: 没有 1:有");

    /**
     * The column <code>userdb.user_employee_temp.sysuser_id</code>. sysuser.id, 用户ID
     */
    public final TableField<UserEmployeeTempRecord, Integer> SYSUSER_ID = createField("sysuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "sysuser.id, 用户ID");

    /**
     * The column <code>userdb.user_employee_temp.position_id</code>. hr_employee_position.id, 职能ID
     */
    public final TableField<UserEmployeeTempRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_employee_position.id, 职能ID");

    /**
     * The column <code>userdb.user_employee_temp.section_id</code>. hr_employee_section.id, 部门ID
     */
    public final TableField<UserEmployeeTempRecord, Integer> SECTION_ID = createField("section_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_employee_section.id, 部门ID");

    /**
     * The column <code>userdb.user_employee_temp.email_isvalid</code>. 是否认证了1：是, 0：否
     */
    public final TableField<UserEmployeeTempRecord, Byte> EMAIL_ISVALID = createField("email_isvalid", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否认证了1：是, 0：否");

    /**
     * The column <code>userdb.user_employee_temp.auth_method</code>. 员工认证途径 0:使用邮箱认证 1:使用自定义认证 2:使用问答认证
     */
    public final TableField<UserEmployeeTempRecord, Byte> AUTH_METHOD = createField("auth_method", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "员工认证途径 0:使用邮箱认证 1:使用自定义认证 2:使用问答认证");

    /**
     * The column <code>userdb.user_employee_temp.custom_field_values</code>. 自定 义字段键值, 结构[{&lt;id&gt;: "&lt;value&gt;"},{...},...]
     */
    public final TableField<UserEmployeeTempRecord, String> CUSTOM_FIELD_VALUES = createField("custom_field_values", org.jooq.impl.SQLDataType.VARCHAR.length(4096).nullable(false).defaultValue(org.jooq.impl.DSL.inline("[]", org.jooq.impl.SQLDataType.VARCHAR)), this, "自定 义字段键值, 结构[{<id>: \"<value>\"},{...},...]");

    /**
     * The column <code>userdb.user_employee_temp.team_id</code>. 团队id
     */
    public final TableField<UserEmployeeTempRecord, Integer> TEAM_ID = createField("team_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "团队id");

    /**
     * The column <code>userdb.user_employee_temp.job_grade</code>. 职级
     */
    public final TableField<UserEmployeeTempRecord, Byte> JOB_GRADE = createField("job_grade", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "职级");

    /**
     * The column <code>userdb.user_employee_temp.city_code</code>. 城市code
     */
    public final TableField<UserEmployeeTempRecord, Integer> CITY_CODE = createField("city_code", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "城市code");

    /**
     * The column <code>userdb.user_employee_temp.degree</code>. 学历
     */
    public final TableField<UserEmployeeTempRecord, Byte> DEGREE = createField("degree", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "学历");

    /**
     * Create a <code>userdb.user_employee_temp</code> table reference
     */
    public UserEmployeeTemp() {
        this("user_employee_temp", null);
    }

    /**
     * Create an aliased <code>userdb.user_employee_temp</code> table reference
     */
    public UserEmployeeTemp(String alias) {
        this(alias, USER_EMPLOYEE_TEMP);
    }

    private UserEmployeeTemp(String alias, Table<UserEmployeeTempRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserEmployeeTemp(String alias, Table<UserEmployeeTempRecord> aliased, Field<?>[] parameters) {
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
    public UserEmployeeTemp as(String alias) {
        return new UserEmployeeTemp(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserEmployeeTemp rename(String name) {
        return new UserEmployeeTemp(name, null);
    }
}
