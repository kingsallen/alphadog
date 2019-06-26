/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables;


import com.moseeker.baseorm.db.userdb.Keys;
import com.moseeker.baseorm.db.userdb.Userdb;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeTestRecord;

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
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserEmployeeTest extends TableImpl<UserEmployeeTestRecord> {

    private static final long serialVersionUID = 1222932847;

    /**
     * The reference instance of <code>userdb.user_employee_test</code>
     */
    public static final UserEmployeeTest USER_EMPLOYEE_TEST = new UserEmployeeTest();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserEmployeeTestRecord> getRecordType() {
        return UserEmployeeTestRecord.class;
    }

    /**
     * The column <code>userdb.user_employee_test.id</code>.
     */
    public final TableField<UserEmployeeTestRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>userdb.user_employee_test.employeeid</code>. 员工ID
     */
    public final TableField<UserEmployeeTestRecord, String> EMPLOYEEID = createField("employeeid", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "员工ID");

    /**
     * The column <code>userdb.user_employee_test.company_id</code>.
     */
    public final TableField<UserEmployeeTestRecord, Integer> COMPANY_ID = createField("company_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>userdb.user_employee_test.role_id</code>. sys_role.id
     */
    public final TableField<UserEmployeeTestRecord, Integer> ROLE_ID = createField("role_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "sys_role.id");

    /**
     * The column <code>userdb.user_employee_test.wxuser_id</code>. userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考sysuser_id
     */
    public final TableField<UserEmployeeTestRecord, Integer> WXUSER_ID = createField("wxuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考sysuser_id");

    /**
     * The column <code>userdb.user_employee_test.sex</code>. 0：未知，1：男，2：女
     */
    public final TableField<UserEmployeeTestRecord, Byte> SEX = createField("sex", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "0：未知，1：男，2：女");

    /**
     * The column <code>userdb.user_employee_test.ename</code>. 英文名
     */
    public final TableField<UserEmployeeTestRecord, String> ENAME = createField("ename", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "英文名");

    /**
     * The column <code>userdb.user_employee_test.efname</code>. 英文姓
     */
    public final TableField<UserEmployeeTestRecord, String> EFNAME = createField("efname", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "英文姓");

    /**
     * The column <code>userdb.user_employee_test.cname</code>.
     */
    public final TableField<UserEmployeeTestRecord, String> CNAME = createField("cname", org.jooq.impl.SQLDataType.VARCHAR.length(100), this, "");

    /**
     * The column <code>userdb.user_employee_test.cfname</code>. 中文姓
     */
    public final TableField<UserEmployeeTestRecord, String> CFNAME = createField("cfname", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "中文姓");

    /**
     * The column <code>userdb.user_employee_test.password</code>. 如果为管理员，存储登陆密码
     */
    public final TableField<UserEmployeeTestRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "如果为管理员，存储登陆密码");

    /**
     * The column <code>userdb.user_employee_test.is_admin</code>.
     */
    public final TableField<UserEmployeeTestRecord, Byte> IS_ADMIN = createField("is_admin", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "");

    /**
     * The column <code>userdb.user_employee_test.status</code>.
     */
    public final TableField<UserEmployeeTestRecord, Integer> STATUS = createField("status", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>userdb.user_employee_test.companybody</code>.
     */
    public final TableField<UserEmployeeTestRecord, String> COMPANYBODY = createField("companybody", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_test.departmentname</code>.
     */
    public final TableField<UserEmployeeTestRecord, String> DEPARTMENTNAME = createField("departmentname", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_test.groupname</code>.
     */
    public final TableField<UserEmployeeTestRecord, String> GROUPNAME = createField("groupname", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_test.position</code>.
     */
    public final TableField<UserEmployeeTestRecord, String> POSITION = createField("position", org.jooq.impl.SQLDataType.VARCHAR.length(40).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_test.employdate</code>.
     */
    public final TableField<UserEmployeeTestRecord, Date> EMPLOYDATE = createField("employdate", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>userdb.user_employee_test.managername</code>.
     */
    public final TableField<UserEmployeeTestRecord, String> MANAGERNAME = createField("managername", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_test.city</code>.
     */
    public final TableField<UserEmployeeTestRecord, String> CITY = createField("city", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_test.birthday</code>.
     */
    public final TableField<UserEmployeeTestRecord, Date> BIRTHDAY = createField("birthday", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>userdb.user_employee_test.retiredate</code>.
     */
    public final TableField<UserEmployeeTestRecord, Date> RETIREDATE = createField("retiredate", org.jooq.impl.SQLDataType.DATE, this, "");

    /**
     * The column <code>userdb.user_employee_test.education</code>.
     */
    public final TableField<UserEmployeeTestRecord, String> EDUCATION = createField("education", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_test.address</code>.
     */
    public final TableField<UserEmployeeTestRecord, String> ADDRESS = createField("address", org.jooq.impl.SQLDataType.VARCHAR.length(100).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_test.idcard</code>.
     */
    public final TableField<UserEmployeeTestRecord, String> IDCARD = createField("idcard", org.jooq.impl.SQLDataType.VARCHAR.length(30).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_test.mobile</code>.
     */
    public final TableField<UserEmployeeTestRecord, String> MOBILE = createField("mobile", org.jooq.impl.SQLDataType.VARCHAR.length(20).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>userdb.user_employee_test.award</code>. 员工积分
     */
    public final TableField<UserEmployeeTestRecord, Integer> AWARD = createField("award", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "员工积分");

    /**
     * The column <code>userdb.user_employee_test.binding_time</code>.
     */
    public final TableField<UserEmployeeTestRecord, Timestamp> BINDING_TIME = createField("binding_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "");

    /**
     * The column <code>userdb.user_employee_test.email</code>. email
     */
    public final TableField<UserEmployeeTestRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "email");

    /**
     * The column <code>userdb.user_employee_test.activation</code>. 0：认证成功，1：认证后取消认证 2：认证失败 3：未认证 4：认证后又认证了其他公司导致本条数据变成未认证 5:取消关注公众号
     */
    public final TableField<UserEmployeeTestRecord, Byte> ACTIVATION = createField("activation", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("3", org.jooq.impl.SQLDataType.TINYINT)), this, "0：认证成功，1：认证后取消认证 2：认证失败 3：未认证 4：认证后又认证了其他公司导致本条数据变成未认证 5:取消关注公众号");

    /**
     * The column <code>userdb.user_employee_test.activation_code</code>. 激活码
     */
    public final TableField<UserEmployeeTestRecord, String> ACTIVATION_CODE = createField("activation_code", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "激活码");

    /**
     * The column <code>userdb.user_employee_test.disable</code>. 是否禁用0：可用1：禁用
     */
    public final TableField<UserEmployeeTestRecord, Byte> DISABLE = createField("disable", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否禁用0：可用1：禁用");

    /**
     * The column <code>userdb.user_employee_test.create_time</code>. 创建时间
     */
    public final TableField<UserEmployeeTestRecord, Timestamp> CREATE_TIME = createField("create_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "创建时间");

    /**
     * The column <code>userdb.user_employee_test.update_time</code>. 修改时间
     */
    public final TableField<UserEmployeeTestRecord, Timestamp> UPDATE_TIME = createField("update_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "修改时间");

    /**
     * The column <code>userdb.user_employee_test.auth_level</code>. 雇主认证：0代表刚注册, 1代表提交了基本信息, 2代表邮箱认证通过, 3代表通过人工审核
     */
    public final TableField<UserEmployeeTestRecord, Byte> AUTH_LEVEL = createField("auth_level", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "雇主认证：0代表刚注册, 1代表提交了基本信息, 2代表邮箱认证通过, 3代表通过人工审核");

    /**
     * The column <code>userdb.user_employee_test.register_time</code>. 注册时间
     */
    public final TableField<UserEmployeeTestRecord, Timestamp> REGISTER_TIME = createField("register_time", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.inline("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "注册时间");

    /**
     * The column <code>userdb.user_employee_test.register_ip</code>. 注册IP
     */
    public final TableField<UserEmployeeTestRecord, String> REGISTER_IP = createField("register_ip", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "注册IP");

    /**
     * The column <code>userdb.user_employee_test.last_login_time</code>. 最近登录时间
     */
    public final TableField<UserEmployeeTestRecord, Timestamp> LAST_LOGIN_TIME = createField("last_login_time", org.jooq.impl.SQLDataType.TIMESTAMP, this, "最近登录时间");

    /**
     * The column <code>userdb.user_employee_test.last_login_ip</code>. 最近登录IP
     */
    public final TableField<UserEmployeeTestRecord, String> LAST_LOGIN_IP = createField("last_login_ip", org.jooq.impl.SQLDataType.VARCHAR.length(50), this, "最近登录IP");

    /**
     * The column <code>userdb.user_employee_test.login_count</code>. 登录次数
     */
    public final TableField<UserEmployeeTestRecord, Long> LOGIN_COUNT = createField("login_count", org.jooq.impl.SQLDataType.BIGINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.BIGINT)), this, "登录次数");

    /**
     * The column <code>userdb.user_employee_test.source</code>. 来源，0:默认 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 8:hr导入员工 9:hr添加的员工 10:年度总结引导认证
     */
    public final TableField<UserEmployeeTestRecord, Byte> SOURCE = createField("source", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "来源，0:默认 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 8:hr导入员工 9:hr添加的员工 10:年度总结引导认证");

    /**
     * The column <code>userdb.user_employee_test.download_token</code>. 下载行业报告的校验token
     */
    public final TableField<UserEmployeeTestRecord, String> DOWNLOAD_TOKEN = createField("download_token", org.jooq.impl.SQLDataType.VARCHAR.length(256).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "下载行业报告的校验token");

    /**
     * The column <code>userdb.user_employee_test.hr_wxuser_id</code>. hr招聘助手的微信ID，wx_group_user.id
     */
    public final TableField<UserEmployeeTestRecord, Integer> HR_WXUSER_ID = createField("hr_wxuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr招聘助手的微信ID，wx_group_user.id");

    /**
     * The column <code>userdb.user_employee_test.custom_field</code>. 配置的自定义认证名称对应的内容
     */
    public final TableField<UserEmployeeTestRecord, String> CUSTOM_FIELD = createField("custom_field", org.jooq.impl.SQLDataType.VARCHAR.length(64).nullable(false).defaultValue(org.jooq.impl.DSL.inline("", org.jooq.impl.SQLDataType.VARCHAR)), this, "配置的自定义认证名称对应的内容");

    /**
     * The column <code>userdb.user_employee_test.is_rp_sent</code>. 是否拿过员工认证红包 0: 没有 1:有
     */
    public final TableField<UserEmployeeTestRecord, Byte> IS_RP_SENT = createField("is_rp_sent", org.jooq.impl.SQLDataType.TINYINT.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否拿过员工认证红包 0: 没有 1:有");

    /**
     * The column <code>userdb.user_employee_test.sysuser_id</code>. sysuser.id, 用户ID
     */
    public final TableField<UserEmployeeTestRecord, Integer> SYSUSER_ID = createField("sysuser_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "sysuser.id, 用户ID");

    /**
     * The column <code>userdb.user_employee_test.position_id</code>. hr_employee_position.id, 职能ID
     */
    public final TableField<UserEmployeeTestRecord, Integer> POSITION_ID = createField("position_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_employee_position.id, 职能ID");

    /**
     * The column <code>userdb.user_employee_test.section_id</code>. hr_employee_section.id, 部门ID
     */
    public final TableField<UserEmployeeTestRecord, Integer> SECTION_ID = createField("section_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "hr_employee_section.id, 部门ID");

    /**
     * The column <code>userdb.user_employee_test.email_isvalid</code>. 是否认证了1：是, 0：否
     */
    public final TableField<UserEmployeeTestRecord, Byte> EMAIL_ISVALID = createField("email_isvalid", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "是否认证了1：是, 0：否");

    /**
     * The column <code>userdb.user_employee_test.auth_method</code>. 员工认证途径 0:使用邮箱认证 1:使用自定义认证 2:使用问答认证
     */
    public final TableField<UserEmployeeTestRecord, Byte> AUTH_METHOD = createField("auth_method", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "员工认证途径 0:使用邮箱认证 1:使用自定义认证 2:使用问答认证");

    /**
     * The column <code>userdb.user_employee_test.custom_field_values</code>. 自定 义字段键值, 结构[{&lt;id&gt;: "&lt;value&gt;"},{...},...]
     */
    public final TableField<UserEmployeeTestRecord, String> CUSTOM_FIELD_VALUES = createField("custom_field_values", org.jooq.impl.SQLDataType.VARCHAR.length(4096).nullable(false).defaultValue(org.jooq.impl.DSL.inline("[]", org.jooq.impl.SQLDataType.VARCHAR)), this, "自定 义字段键值, 结构[{<id>: \"<value>\"},{...},...]");

    /**
     * The column <code>userdb.user_employee_test.team_id</code>. 团队id
     */
    public final TableField<UserEmployeeTestRecord, Integer> TEAM_ID = createField("team_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "团队id");

    /**
     * The column <code>userdb.user_employee_test.job_grade</code>. 职级
     */
    public final TableField<UserEmployeeTestRecord, Byte> JOB_GRADE = createField("job_grade", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "职级");

    /**
     * The column <code>userdb.user_employee_test.city_code</code>. 城市code
     */
    public final TableField<UserEmployeeTestRecord, Integer> CITY_CODE = createField("city_code", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "城市code");

    /**
     * The column <code>userdb.user_employee_test.degree</code>. 学历
     */
    public final TableField<UserEmployeeTestRecord, Byte> DEGREE = createField("degree", org.jooq.impl.SQLDataType.TINYINT.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.TINYINT)), this, "学历");

    /**
     * The column <code>userdb.user_employee_test.bonus</code>. 员工当前的奖金总额
     */
    public final TableField<UserEmployeeTestRecord, Integer> BONUS = createField("bonus", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "员工当前的奖金总额");

    /**
     * Create a <code>userdb.user_employee_test</code> table reference
     */
    public UserEmployeeTest() {
        this("user_employee_test", null);
    }

    /**
     * Create an aliased <code>userdb.user_employee_test</code> table reference
     */
    public UserEmployeeTest(String alias) {
        this(alias, USER_EMPLOYEE_TEST);
    }

    private UserEmployeeTest(String alias, Table<UserEmployeeTestRecord> aliased) {
        this(alias, aliased, null);
    }

    private UserEmployeeTest(String alias, Table<UserEmployeeTestRecord> aliased, Field<?>[] parameters) {
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
    public Identity<UserEmployeeTestRecord, Integer> getIdentity() {
        return Keys.IDENTITY_USER_EMPLOYEE_TEST;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<UserEmployeeTestRecord> getPrimaryKey() {
        return Keys.KEY_USER_EMPLOYEE_TEST_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<UserEmployeeTestRecord>> getKeys() {
        return Arrays.<UniqueKey<UserEmployeeTestRecord>>asList(Keys.KEY_USER_EMPLOYEE_TEST_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserEmployeeTest as(String alias) {
        return new UserEmployeeTest(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public UserEmployeeTest rename(String name) {
        return new UserEmployeeTest(name, null);
    }
}