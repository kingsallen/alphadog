/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.pojos;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.annotation.Generated;


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
public class UserEmployee implements Serializable {

    private static final long serialVersionUID = -993404041;

    private Integer   id;
    private String    employeeid;
    private Integer   companyId;
    private Integer   roleId;
    private Integer   wxuserId;
    private Byte      sex;
    private String    ename;
    private String    efname;
    private String    cname;
    private String    cfname;
    private String    password;
    private Byte      isAdmin;
    private Integer   status;
    private String    companybody;
    private String    departmentname;
    private String    groupname;
    private String    position;
    private Date      employdate;
    private String    managername;
    private String    city;
    private Date      birthday;
    private Date      retiredate;
    private String    education;
    private String    address;
    private String    idcard;
    private String    mobile;
    private Integer   award;
    private Timestamp bindingTime;
    private String    email;
    private Byte      activation;
    private String    activationCode;
    private Byte      disable;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte      authLevel;
    private Timestamp registerTime;
    private String    registerIp;
    private Timestamp lastLoginTime;
    private String    lastLoginIp;
    private Long      loginCount;
    private Byte      source;
    private String    downloadToken;
    private Integer   hrWxuserId;
    private String    customField;
    private Byte      isRpSent;
    private Integer   sysuserId;
    private Integer   positionId;
    private Integer   sectionId;
    private Byte      emailIsvalid;
    private Byte      authMethod;
    private String    customFieldValues;
    private Integer   teamId;
    private Byte      jobGrade;
    private Integer   cityCode;
    private Byte      degree;

    public UserEmployee() {}

    public UserEmployee(UserEmployee value) {
        this.id = value.id;
        this.employeeid = value.employeeid;
        this.companyId = value.companyId;
        this.roleId = value.roleId;
        this.wxuserId = value.wxuserId;
        this.sex = value.sex;
        this.ename = value.ename;
        this.efname = value.efname;
        this.cname = value.cname;
        this.cfname = value.cfname;
        this.password = value.password;
        this.isAdmin = value.isAdmin;
        this.status = value.status;
        this.companybody = value.companybody;
        this.departmentname = value.departmentname;
        this.groupname = value.groupname;
        this.position = value.position;
        this.employdate = value.employdate;
        this.managername = value.managername;
        this.city = value.city;
        this.birthday = value.birthday;
        this.retiredate = value.retiredate;
        this.education = value.education;
        this.address = value.address;
        this.idcard = value.idcard;
        this.mobile = value.mobile;
        this.award = value.award;
        this.bindingTime = value.bindingTime;
        this.email = value.email;
        this.activation = value.activation;
        this.activationCode = value.activationCode;
        this.disable = value.disable;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.authLevel = value.authLevel;
        this.registerTime = value.registerTime;
        this.registerIp = value.registerIp;
        this.lastLoginTime = value.lastLoginTime;
        this.lastLoginIp = value.lastLoginIp;
        this.loginCount = value.loginCount;
        this.source = value.source;
        this.downloadToken = value.downloadToken;
        this.hrWxuserId = value.hrWxuserId;
        this.customField = value.customField;
        this.isRpSent = value.isRpSent;
        this.sysuserId = value.sysuserId;
        this.positionId = value.positionId;
        this.sectionId = value.sectionId;
        this.emailIsvalid = value.emailIsvalid;
        this.authMethod = value.authMethod;
        this.customFieldValues = value.customFieldValues;
        this.teamId = value.teamId;
        this.jobGrade = value.jobGrade;
        this.cityCode = value.cityCode;
        this.degree = value.degree;
    }

    public UserEmployee(
        Integer   id,
        String    employeeid,
        Integer   companyId,
        Integer   roleId,
        Integer   wxuserId,
        Byte      sex,
        String    ename,
        String    efname,
        String    cname,
        String    cfname,
        String    password,
        Byte      isAdmin,
        Integer   status,
        String    companybody,
        String    departmentname,
        String    groupname,
        String    position,
        Date      employdate,
        String    managername,
        String    city,
        Date      birthday,
        Date      retiredate,
        String    education,
        String    address,
        String    idcard,
        String    mobile,
        Integer   award,
        Timestamp bindingTime,
        String    email,
        Byte      activation,
        String    activationCode,
        Byte      disable,
        Timestamp createTime,
        Timestamp updateTime,
        Byte      authLevel,
        Timestamp registerTime,
        String    registerIp,
        Timestamp lastLoginTime,
        String    lastLoginIp,
        Long      loginCount,
        Byte      source,
        String    downloadToken,
        Integer   hrWxuserId,
        String    customField,
        Byte      isRpSent,
        Integer   sysuserId,
        Integer   positionId,
        Integer   sectionId,
        Byte      emailIsvalid,
        Byte      authMethod,
        String    customFieldValues,
        Integer   teamId,
        Byte      jobGrade,
        Integer   cityCode,
        Byte      degree
    ) {
        this.id = id;
        this.employeeid = employeeid;
        this.companyId = companyId;
        this.roleId = roleId;
        this.wxuserId = wxuserId;
        this.sex = sex;
        this.ename = ename;
        this.efname = efname;
        this.cname = cname;
        this.cfname = cfname;
        this.password = password;
        this.isAdmin = isAdmin;
        this.status = status;
        this.companybody = companybody;
        this.departmentname = departmentname;
        this.groupname = groupname;
        this.position = position;
        this.employdate = employdate;
        this.managername = managername;
        this.city = city;
        this.birthday = birthday;
        this.retiredate = retiredate;
        this.education = education;
        this.address = address;
        this.idcard = idcard;
        this.mobile = mobile;
        this.award = award;
        this.bindingTime = bindingTime;
        this.email = email;
        this.activation = activation;
        this.activationCode = activationCode;
        this.disable = disable;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.authLevel = authLevel;
        this.registerTime = registerTime;
        this.registerIp = registerIp;
        this.lastLoginTime = lastLoginTime;
        this.lastLoginIp = lastLoginIp;
        this.loginCount = loginCount;
        this.source = source;
        this.downloadToken = downloadToken;
        this.hrWxuserId = hrWxuserId;
        this.customField = customField;
        this.isRpSent = isRpSent;
        this.sysuserId = sysuserId;
        this.positionId = positionId;
        this.sectionId = sectionId;
        this.emailIsvalid = emailIsvalid;
        this.authMethod = authMethod;
        this.customFieldValues = customFieldValues;
        this.teamId = teamId;
        this.jobGrade = jobGrade;
        this.cityCode = cityCode;
        this.degree = degree;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeid() {
        return this.employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getRoleId() {
        return this.roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getWxuserId() {
        return this.wxuserId;
    }

    public void setWxuserId(Integer wxuserId) {
        this.wxuserId = wxuserId;
    }

    public Byte getSex() {
        return this.sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getEname() {
        return this.ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEfname() {
        return this.efname;
    }

    public void setEfname(String efname) {
        this.efname = efname;
    }

    public String getCname() {
        return this.cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCfname() {
        return this.cfname;
    }

    public void setCfname(String cfname) {
        this.cfname = cfname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getIsAdmin() {
        return this.isAdmin;
    }

    public void setIsAdmin(Byte isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCompanybody() {
        return this.companybody;
    }

    public void setCompanybody(String companybody) {
        this.companybody = companybody;
    }

    public String getDepartmentname() {
        return this.departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getPosition() {
        return this.position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getEmploydate() {
        return this.employdate;
    }

    public void setEmploydate(Date employdate) {
        this.employdate = employdate;
    }

    public String getManagername() {
        return this.managername;
    }

    public void setManagername(String managername) {
        this.managername = managername;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getRetiredate() {
        return this.retiredate;
    }

    public void setRetiredate(Date retiredate) {
        this.retiredate = retiredate;
    }

    public String getEducation() {
        return this.education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdcard() {
        return this.idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getAward() {
        return this.award;
    }

    public void setAward(Integer award) {
        this.award = award;
    }

    public Timestamp getBindingTime() {
        return this.bindingTime;
    }

    public void setBindingTime(Timestamp bindingTime) {
        this.bindingTime = bindingTime;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getActivation() {
        return this.activation;
    }

    public void setActivation(Byte activation) {
        this.activation = activation;
    }

    public String getActivationCode() {
        return this.activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public Byte getDisable() {
        return this.disable;
    }

    public void setDisable(Byte disable) {
        this.disable = disable;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getAuthLevel() {
        return this.authLevel;
    }

    public void setAuthLevel(Byte authLevel) {
        this.authLevel = authLevel;
    }

    public Timestamp getRegisterTime() {
        return this.registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterIp() {
        return this.registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public Timestamp getLastLoginTime() {
        return this.lastLoginTime;
    }

    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return this.lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Long getLoginCount() {
        return this.loginCount;
    }

    public void setLoginCount(Long loginCount) {
        this.loginCount = loginCount;
    }

    public Byte getSource() {
        return this.source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }

    public String getDownloadToken() {
        return this.downloadToken;
    }

    public void setDownloadToken(String downloadToken) {
        this.downloadToken = downloadToken;
    }

    public Integer getHrWxuserId() {
        return this.hrWxuserId;
    }

    public void setHrWxuserId(Integer hrWxuserId) {
        this.hrWxuserId = hrWxuserId;
    }

    public String getCustomField() {
        return this.customField;
    }

    public void setCustomField(String customField) {
        this.customField = customField;
    }

    public Byte getIsRpSent() {
        return this.isRpSent;
    }

    public void setIsRpSent(Byte isRpSent) {
        this.isRpSent = isRpSent;
    }

    public Integer getSysuserId() {
        return this.sysuserId;
    }

    public void setSysuserId(Integer sysuserId) {
        this.sysuserId = sysuserId;
    }

    public Integer getPositionId() {
        return this.positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public Byte getEmailIsvalid() {
        return this.emailIsvalid;
    }

    public void setEmailIsvalid(Byte emailIsvalid) {
        this.emailIsvalid = emailIsvalid;
    }

    public Byte getAuthMethod() {
        return this.authMethod;
    }

    public void setAuthMethod(Byte authMethod) {
        this.authMethod = authMethod;
    }

    public String getCustomFieldValues() {
        return this.customFieldValues;
    }

    public void setCustomFieldValues(String customFieldValues) {
        this.customFieldValues = customFieldValues;
    }

    public Integer getTeamId() {
        return this.teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Byte getJobGrade() {
        return this.jobGrade;
    }

    public void setJobGrade(Byte jobGrade) {
        this.jobGrade = jobGrade;
    }

    public Integer getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    public Byte getDegree() {
        return this.degree;
    }

    public void setDegree(Byte degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserEmployee (");

        sb.append(id);
        sb.append(", ").append(employeeid);
        sb.append(", ").append(companyId);
        sb.append(", ").append(roleId);
        sb.append(", ").append(wxuserId);
        sb.append(", ").append(sex);
        sb.append(", ").append(ename);
        sb.append(", ").append(efname);
        sb.append(", ").append(cname);
        sb.append(", ").append(cfname);
        sb.append(", ").append(password);
        sb.append(", ").append(isAdmin);
        sb.append(", ").append(status);
        sb.append(", ").append(companybody);
        sb.append(", ").append(departmentname);
        sb.append(", ").append(groupname);
        sb.append(", ").append(position);
        sb.append(", ").append(employdate);
        sb.append(", ").append(managername);
        sb.append(", ").append(city);
        sb.append(", ").append(birthday);
        sb.append(", ").append(retiredate);
        sb.append(", ").append(education);
        sb.append(", ").append(address);
        sb.append(", ").append(idcard);
        sb.append(", ").append(mobile);
        sb.append(", ").append(award);
        sb.append(", ").append(bindingTime);
        sb.append(", ").append(email);
        sb.append(", ").append(activation);
        sb.append(", ").append(activationCode);
        sb.append(", ").append(disable);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(authLevel);
        sb.append(", ").append(registerTime);
        sb.append(", ").append(registerIp);
        sb.append(", ").append(lastLoginTime);
        sb.append(", ").append(lastLoginIp);
        sb.append(", ").append(loginCount);
        sb.append(", ").append(source);
        sb.append(", ").append(downloadToken);
        sb.append(", ").append(hrWxuserId);
        sb.append(", ").append(customField);
        sb.append(", ").append(isRpSent);
        sb.append(", ").append(sysuserId);
        sb.append(", ").append(positionId);
        sb.append(", ").append(sectionId);
        sb.append(", ").append(emailIsvalid);
        sb.append(", ").append(authMethod);
        sb.append(", ").append(customFieldValues);
        sb.append(", ").append(teamId);
        sb.append(", ").append(jobGrade);
        sb.append(", ").append(cityCode);
        sb.append(", ").append(degree);

        sb.append(")");
        return sb.toString();
    }
}
