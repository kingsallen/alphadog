/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.records;


import com.moseeker.baseorm.db.userdb.tables.UserEmployee;

import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


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
public class UserEmployeeRecord extends UpdatableRecordImpl<UserEmployeeRecord> {

    private static final long serialVersionUID = -1692565512;

    /**
     * Setter for <code>userdb.user_employee.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>userdb.user_employee.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>userdb.user_employee.employeeid</code>. 员工ID
     */
    public void setEmployeeid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>userdb.user_employee.employeeid</code>. 员工ID
     */
    public String getEmployeeid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>userdb.user_employee.company_id</code>.
     */
    public void setCompanyId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>userdb.user_employee.company_id</code>.
     */
    public Integer getCompanyId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>userdb.user_employee.role_id</code>. sys_role.id
     */
    public void setRoleId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>userdb.user_employee.role_id</code>. sys_role.id
     */
    public Integer getRoleId() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>userdb.user_employee.wxuser_id</code>. userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考sysuser_id
     */
    public void setWxuserId(Integer value) {
        set(4, value);
    }

    /**
     * Getter for <code>userdb.user_employee.wxuser_id</code>. userdb.user_wx_user.id 微信账号编号。现在已经废弃。关于员工绑定的C端账号，请参考sysuser_id
     */
    public Integer getWxuserId() {
        return (Integer) get(4);
    }

    /**
     * Setter for <code>userdb.user_employee.sex</code>. 0：未知，1：男，2：女
     */
    public void setSex(Byte value) {
        set(5, value);
    }

    /**
     * Getter for <code>userdb.user_employee.sex</code>. 0：未知，1：男，2：女
     */
    public Byte getSex() {
        return (Byte) get(5);
    }

    /**
     * Setter for <code>userdb.user_employee.ename</code>. 英文名
     */
    public void setEname(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>userdb.user_employee.ename</code>. 英文名
     */
    public String getEname() {
        return (String) get(6);
    }

    /**
     * Setter for <code>userdb.user_employee.efname</code>. 英文姓
     */
    public void setEfname(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>userdb.user_employee.efname</code>. 英文姓
     */
    public String getEfname() {
        return (String) get(7);
    }

    /**
     * Setter for <code>userdb.user_employee.cname</code>.
     */
    public void setCname(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>userdb.user_employee.cname</code>.
     */
    public String getCname() {
        return (String) get(8);
    }

    /**
     * Setter for <code>userdb.user_employee.cfname</code>. 中文姓
     */
    public void setCfname(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>userdb.user_employee.cfname</code>. 中文姓
     */
    public String getCfname() {
        return (String) get(9);
    }

    /**
     * Setter for <code>userdb.user_employee.password</code>. 如果为管理员，存储登陆密码
     */
    public void setPassword(String value) {
        set(10, value);
    }

    /**
     * Getter for <code>userdb.user_employee.password</code>. 如果为管理员，存储登陆密码
     */
    public String getPassword() {
        return (String) get(10);
    }

    /**
     * Setter for <code>userdb.user_employee.is_admin</code>.
     */
    public void setIsAdmin(Byte value) {
        set(11, value);
    }

    /**
     * Getter for <code>userdb.user_employee.is_admin</code>.
     */
    public Byte getIsAdmin() {
        return (Byte) get(11);
    }

    /**
     * Setter for <code>userdb.user_employee.status</code>.
     */
    public void setStatus(Integer value) {
        set(12, value);
    }

    /**
     * Getter for <code>userdb.user_employee.status</code>.
     */
    public Integer getStatus() {
        return (Integer) get(12);
    }

    /**
     * Setter for <code>userdb.user_employee.companybody</code>.
     */
    public void setCompanybody(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>userdb.user_employee.companybody</code>.
     */
    public String getCompanybody() {
        return (String) get(13);
    }

    /**
     * Setter for <code>userdb.user_employee.departmentname</code>.
     */
    public void setDepartmentname(String value) {
        set(14, value);
    }

    /**
     * Getter for <code>userdb.user_employee.departmentname</code>.
     */
    public String getDepartmentname() {
        return (String) get(14);
    }

    /**
     * Setter for <code>userdb.user_employee.groupname</code>.
     */
    public void setGroupname(String value) {
        set(15, value);
    }

    /**
     * Getter for <code>userdb.user_employee.groupname</code>.
     */
    public String getGroupname() {
        return (String) get(15);
    }

    /**
     * Setter for <code>userdb.user_employee.position</code>.
     */
    public void setPosition(String value) {
        set(16, value);
    }

    /**
     * Getter for <code>userdb.user_employee.position</code>.
     */
    public String getPosition() {
        return (String) get(16);
    }

    /**
     * Setter for <code>userdb.user_employee.employdate</code>.
     */
    public void setEmploydate(Date value) {
        set(17, value);
    }

    /**
     * Getter for <code>userdb.user_employee.employdate</code>.
     */
    public Date getEmploydate() {
        return (Date) get(17);
    }

    /**
     * Setter for <code>userdb.user_employee.managername</code>.
     */
    public void setManagername(String value) {
        set(18, value);
    }

    /**
     * Getter for <code>userdb.user_employee.managername</code>.
     */
    public String getManagername() {
        return (String) get(18);
    }

    /**
     * Setter for <code>userdb.user_employee.city</code>.
     */
    public void setCity(String value) {
        set(19, value);
    }

    /**
     * Getter for <code>userdb.user_employee.city</code>.
     */
    public String getCity() {
        return (String) get(19);
    }

    /**
     * Setter for <code>userdb.user_employee.birthday</code>.
     */
    public void setBirthday(Date value) {
        set(20, value);
    }

    /**
     * Getter for <code>userdb.user_employee.birthday</code>.
     */
    public Date getBirthday() {
        return (Date) get(20);
    }

    /**
     * Setter for <code>userdb.user_employee.retiredate</code>.
     */
    public void setRetiredate(Date value) {
        set(21, value);
    }

    /**
     * Getter for <code>userdb.user_employee.retiredate</code>.
     */
    public Date getRetiredate() {
        return (Date) get(21);
    }

    /**
     * Setter for <code>userdb.user_employee.education</code>.
     */
    public void setEducation(String value) {
        set(22, value);
    }

    /**
     * Getter for <code>userdb.user_employee.education</code>.
     */
    public String getEducation() {
        return (String) get(22);
    }

    /**
     * Setter for <code>userdb.user_employee.address</code>.
     */
    public void setAddress(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>userdb.user_employee.address</code>.
     */
    public String getAddress() {
        return (String) get(23);
    }

    /**
     * Setter for <code>userdb.user_employee.idcard</code>.
     */
    public void setIdcard(String value) {
        set(24, value);
    }

    /**
     * Getter for <code>userdb.user_employee.idcard</code>.
     */
    public String getIdcard() {
        return (String) get(24);
    }

    /**
     * Setter for <code>userdb.user_employee.mobile</code>.
     */
    public void setMobile(String value) {
        set(25, value);
    }

    /**
     * Getter for <code>userdb.user_employee.mobile</code>.
     */
    public String getMobile() {
        return (String) get(25);
    }

    /**
     * Setter for <code>userdb.user_employee.award</code>. 员工积分
     */
    public void setAward(Integer value) {
        set(26, value);
    }

    /**
     * Getter for <code>userdb.user_employee.award</code>. 员工积分
     */
    public Integer getAward() {
        return (Integer) get(26);
    }

    /**
     * Setter for <code>userdb.user_employee.binding_time</code>.
     */
    public void setBindingTime(Timestamp value) {
        set(27, value);
    }

    /**
     * Getter for <code>userdb.user_employee.binding_time</code>.
     */
    public Timestamp getBindingTime() {
        return (Timestamp) get(27);
    }

    /**
     * Setter for <code>userdb.user_employee.email</code>. email
     */
    public void setEmail(String value) {
        set(28, value);
    }

    /**
     * Getter for <code>userdb.user_employee.email</code>. email
     */
    public String getEmail() {
        return (String) get(28);
    }

    /**
     * Setter for <code>userdb.user_employee.activation</code>. 0：认证成功，1：认证后取消认证 2：认证失败 3：未认证 4：认证后又认证了其他公司导致本条数据变成未认证  5:取消关注公众号   6：员工离职取消认证
     */
    public void setActivation(Byte value) {
        set(29, value);
    }

    /**
     * Getter for <code>userdb.user_employee.activation</code>. 0：认证成功，1：认证后取消认证 2：认证失败 3：未认证 4：认证后又认证了其他公司导致本条数据变成未认证  5:取消关注公众号   6：员工离职取消认证
     */
    public Byte getActivation() {
        return (Byte) get(29);
    }

    /**
     * Setter for <code>userdb.user_employee.activation_code</code>. 激活码
     */
    public void setActivationCode(String value) {
        set(30, value);
    }

    /**
     * Getter for <code>userdb.user_employee.activation_code</code>. 激活码
     */
    public String getActivationCode() {
        return (String) get(30);
    }

    /**
     * Setter for <code>userdb.user_employee.disable</code>. 是否禁用0：可用1：禁用
     */
    public void setDisable(Byte value) {
        set(31, value);
    }

    /**
     * Getter for <code>userdb.user_employee.disable</code>. 是否禁用0：可用1：禁用
     */
    public Byte getDisable() {
        return (Byte) get(31);
    }

    /**
     * Setter for <code>userdb.user_employee.create_time</code>. 创建时间
     */
    public void setCreateTime(Timestamp value) {
        set(32, value);
    }

    /**
     * Getter for <code>userdb.user_employee.create_time</code>. 创建时间
     */
    public Timestamp getCreateTime() {
        return (Timestamp) get(32);
    }

    /**
     * Setter for <code>userdb.user_employee.update_time</code>. 修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(33, value);
    }

    /**
     * Getter for <code>userdb.user_employee.update_time</code>. 修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(33);
    }

    /**
     * Setter for <code>userdb.user_employee.auth_level</code>. 雇主认证：0代表刚注册, 1代表提交了基本信息, 2代表邮箱认证通过, 3代表通过人工审核
     */
    public void setAuthLevel(Byte value) {
        set(34, value);
    }

    /**
     * Getter for <code>userdb.user_employee.auth_level</code>. 雇主认证：0代表刚注册, 1代表提交了基本信息, 2代表邮箱认证通过, 3代表通过人工审核
     */
    public Byte getAuthLevel() {
        return (Byte) get(34);
    }

    /**
     * Setter for <code>userdb.user_employee.register_time</code>. 注册时间
     */
    public void setRegisterTime(Timestamp value) {
        set(35, value);
    }

    /**
     * Getter for <code>userdb.user_employee.register_time</code>. 注册时间
     */
    public Timestamp getRegisterTime() {
        return (Timestamp) get(35);
    }

    /**
     * Setter for <code>userdb.user_employee.register_ip</code>. 注册IP
     */
    public void setRegisterIp(String value) {
        set(36, value);
    }

    /**
     * Getter for <code>userdb.user_employee.register_ip</code>. 注册IP
     */
    public String getRegisterIp() {
        return (String) get(36);
    }

    /**
     * Setter for <code>userdb.user_employee.last_login_time</code>. 最近登录时间
     */
    public void setLastLoginTime(Timestamp value) {
        set(37, value);
    }

    /**
     * Getter for <code>userdb.user_employee.last_login_time</code>. 最近登录时间
     */
    public Timestamp getLastLoginTime() {
        return (Timestamp) get(37);
    }

    /**
     * Setter for <code>userdb.user_employee.last_login_ip</code>. 最近登录IP
     */
    public void setLastLoginIp(String value) {
        set(38, value);
    }

    /**
     * Getter for <code>userdb.user_employee.last_login_ip</code>. 最近登录IP
     */
    public String getLastLoginIp() {
        return (String) get(38);
    }

    /**
     * Setter for <code>userdb.user_employee.login_count</code>. 登录次数
     */
    public void setLoginCount(Long value) {
        set(39, value);
    }

    /**
     * Getter for <code>userdb.user_employee.login_count</code>. 登录次数
     */
    public Long getLoginCount() {
        return (Long) get(39);
    }

    /**
     * Setter for <code>userdb.user_employee.source</code>. 来源，0:默认 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 8:hr导入员工 9:hr添加的员工 10:年度总结引导认证 12: Joywork对接  13:企业微信 14：小程序登录15：中骏员工信息对接
     */
    public void setSource(Byte value) {
        set(40, value);
    }

    /**
     * Getter for <code>userdb.user_employee.source</code>. 来源，0:默认 1:雇主 2:官网 3:微信扫描 4:我也要招人(聚合号) 5:我也要招人(企业号) 8:hr导入员工 9:hr添加的员工 10:年度总结引导认证 12: Joywork对接  13:企业微信 14：小程序登录15：中骏员工信息对接
     */
    public Byte getSource() {
        return (Byte) get(40);
    }

    /**
     * Setter for <code>userdb.user_employee.download_token</code>. 下载行业报告的校验token
     */
    public void setDownloadToken(String value) {
        set(41, value);
    }

    /**
     * Getter for <code>userdb.user_employee.download_token</code>. 下载行业报告的校验token
     */
    public String getDownloadToken() {
        return (String) get(41);
    }

    /**
     * Setter for <code>userdb.user_employee.hr_wxuser_id</code>. hr招聘助手的微信ID，wx_group_user.id
     */
    public void setHrWxuserId(Integer value) {
        set(42, value);
    }

    /**
     * Getter for <code>userdb.user_employee.hr_wxuser_id</code>. hr招聘助手的微信ID，wx_group_user.id
     */
    public Integer getHrWxuserId() {
        return (Integer) get(42);
    }

    /**
     * Setter for <code>userdb.user_employee.custom_field</code>. 配置的自定义认证名称对应的内容
     */
    public void setCustomField(String value) {
        set(43, value);
    }

    /**
     * Getter for <code>userdb.user_employee.custom_field</code>. 配置的自定义认证名称对应的内容
     */
    public String getCustomField() {
        return (String) get(43);
    }

    /**
     * Setter for <code>userdb.user_employee.is_rp_sent</code>. 是否拿过员工认证红包 0: 没有 1:有
     */
    public void setIsRpSent(Byte value) {
        set(44, value);
    }

    /**
     * Getter for <code>userdb.user_employee.is_rp_sent</code>. 是否拿过员工认证红包 0: 没有 1:有
     */
    public Byte getIsRpSent() {
        return (Byte) get(44);
    }

    /**
     * Setter for <code>userdb.user_employee.sysuser_id</code>. sysuser.id, 用户ID
     */
    public void setSysuserId(Integer value) {
        set(45, value);
    }

    /**
     * Getter for <code>userdb.user_employee.sysuser_id</code>. sysuser.id, 用户ID
     */
    public Integer getSysuserId() {
        return (Integer) get(45);
    }

    /**
     * Setter for <code>userdb.user_employee.position_id</code>. hr_employee_position.id, 职能ID
     */
    public void setPositionId(Integer value) {
        set(46, value);
    }

    /**
     * Getter for <code>userdb.user_employee.position_id</code>. hr_employee_position.id, 职能ID
     */
    public Integer getPositionId() {
        return (Integer) get(46);
    }

    /**
     * Setter for <code>userdb.user_employee.section_id</code>. hr_employee_section.id, 部门ID
     */
    public void setSectionId(Integer value) {
        set(47, value);
    }

    /**
     * Getter for <code>userdb.user_employee.section_id</code>. hr_employee_section.id, 部门ID
     */
    public Integer getSectionId() {
        return (Integer) get(47);
    }

    /**
     * Setter for <code>userdb.user_employee.email_isvalid</code>. 是否认证了1：是, 0：否
     */
    public void setEmailIsvalid(Byte value) {
        set(48, value);
    }

    /**
     * Getter for <code>userdb.user_employee.email_isvalid</code>. 是否认证了1：是, 0：否
     */
    public Byte getEmailIsvalid() {
        return (Byte) get(48);
    }

    /**
     * Setter for <code>userdb.user_employee.auth_method</code>. 员工认证途径 0:使用邮箱认证 1:使用自定义认证 2:使用问答认证 3：企业微信认证
     */
    public void setAuthMethod(Byte value) {
        set(49, value);
    }

    /**
     * Getter for <code>userdb.user_employee.auth_method</code>. 员工认证途径 0:使用邮箱认证 1:使用自定义认证 2:使用问答认证 3：企业微信认证
     */
    public Byte getAuthMethod() {
        return (Byte) get(49);
    }

    /**
     * Setter for <code>userdb.user_employee.custom_field_values</code>. 自定 义字段键值, 结构[{&lt;id&gt;: "&lt;value&gt;"},{...},...]
     */
    public void setCustomFieldValues(String value) {
        set(50, value);
    }

    /**
     * Getter for <code>userdb.user_employee.custom_field_values</code>. 自定 义字段键值, 结构[{&lt;id&gt;: "&lt;value&gt;"},{...},...]
     */
    public String getCustomFieldValues() {
        return (String) get(50);
    }

    /**
     * Setter for <code>userdb.user_employee.team_id</code>. 团队id
     */
    public void setTeamId(Integer value) {
        set(51, value);
    }

    /**
     * Getter for <code>userdb.user_employee.team_id</code>. 团队id
     */
    public Integer getTeamId() {
        return (Integer) get(51);
    }

    /**
     * Setter for <code>userdb.user_employee.job_grade</code>. 职级
     */
    public void setJobGrade(Byte value) {
        set(52, value);
    }

    /**
     * Getter for <code>userdb.user_employee.job_grade</code>. 职级
     */
    public Byte getJobGrade() {
        return (Byte) get(52);
    }

    /**
     * Setter for <code>userdb.user_employee.city_code</code>. 城市code
     */
    public void setCityCode(Integer value) {
        set(53, value);
    }

    /**
     * Getter for <code>userdb.user_employee.city_code</code>. 城市code
     */
    public Integer getCityCode() {
        return (Integer) get(53);
    }

    /**
     * Setter for <code>userdb.user_employee.degree</code>. 学历
     */
    public void setDegree(Byte value) {
        set(54, value);
    }

    /**
     * Getter for <code>userdb.user_employee.degree</code>. 学历
     */
    public Byte getDegree() {
        return (Byte) get(54);
    }

    /**
     * Setter for <code>userdb.user_employee.bonus</code>. 员工当前的奖金总额
     */
    public void setBonus(Integer value) {
        set(55, value);
    }

    /**
     * Getter for <code>userdb.user_employee.bonus</code>. 员工当前的奖金总额
     */
    public Integer getBonus() {
        return (Integer) get(55);
    }

    /**
     * Setter for <code>userdb.user_employee.unbind_time</code>. 取消认证的时间
     */
    public void setUnbindTime(Timestamp value) {
        set(56, value);
    }

    /**
     * Getter for <code>userdb.user_employee.unbind_time</code>. 取消认证的时间
     */
    public Timestamp getUnbindTime() {
        return (Timestamp) get(56);
    }

    /**
     * Setter for <code>userdb.user_employee.import_time</code>. 导入时间
     */
    public void setImportTime(Timestamp value) {
        set(57, value);
    }

    /**
     * Getter for <code>userdb.user_employee.import_time</code>. 导入时间
     */
    public Timestamp getImportTime() {
        return (Timestamp) get(57);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached UserEmployeeRecord
     */
    public UserEmployeeRecord() {
        super(UserEmployee.USER_EMPLOYEE);
    }

    /**
     * Create a detached, initialised UserEmployeeRecord
     */
    public UserEmployeeRecord(Integer id, String employeeid, Integer companyId, Integer roleId, Integer wxuserId, Byte sex, String ename, String efname, String cname, String cfname, String password, Byte isAdmin, Integer status, String companybody, String departmentname, String groupname, String position, Date employdate, String managername, String city, Date birthday, Date retiredate, String education, String address, String idcard, String mobile, Integer award, Timestamp bindingTime, String email, Byte activation, String activationCode, Byte disable, Timestamp createTime, Timestamp updateTime, Byte authLevel, Timestamp registerTime, String registerIp, Timestamp lastLoginTime, String lastLoginIp, Long loginCount, Byte source, String downloadToken, Integer hrWxuserId, String customField, Byte isRpSent, Integer sysuserId, Integer positionId, Integer sectionId, Byte emailIsvalid, Byte authMethod, String customFieldValues, Integer teamId, Byte jobGrade, Integer cityCode, Byte degree, Integer bonus, Timestamp unbindTime, Timestamp importTime) {
        super(UserEmployee.USER_EMPLOYEE);

        set(0, id);
        set(1, employeeid);
        set(2, companyId);
        set(3, roleId);
        set(4, wxuserId);
        set(5, sex);
        set(6, ename);
        set(7, efname);
        set(8, cname);
        set(9, cfname);
        set(10, password);
        set(11, isAdmin);
        set(12, status);
        set(13, companybody);
        set(14, departmentname);
        set(15, groupname);
        set(16, position);
        set(17, employdate);
        set(18, managername);
        set(19, city);
        set(20, birthday);
        set(21, retiredate);
        set(22, education);
        set(23, address);
        set(24, idcard);
        set(25, mobile);
        set(26, award);
        set(27, bindingTime);
        set(28, email);
        set(29, activation);
        set(30, activationCode);
        set(31, disable);
        set(32, createTime);
        set(33, updateTime);
        set(34, authLevel);
        set(35, registerTime);
        set(36, registerIp);
        set(37, lastLoginTime);
        set(38, lastLoginIp);
        set(39, loginCount);
        set(40, source);
        set(41, downloadToken);
        set(42, hrWxuserId);
        set(43, customField);
        set(44, isRpSent);
        set(45, sysuserId);
        set(46, positionId);
        set(47, sectionId);
        set(48, emailIsvalid);
        set(49, authMethod);
        set(50, customFieldValues);
        set(51, teamId);
        set(52, jobGrade);
        set(53, cityCode);
        set(54, degree);
        set(55, bonus);
        set(56, unbindTime);
        set(57, importTime);
    }
}
