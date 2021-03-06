/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * 账号类型-角色映射表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserAccountTypeRoleMap implements Serializable {

    private static final long serialVersionUID = 449094452;

    private Integer id;
    private Integer accountType;
    private Byte    roleType;

    public UserAccountTypeRoleMap() {}

    public UserAccountTypeRoleMap(UserAccountTypeRoleMap value) {
        this.id = value.id;
        this.accountType = value.accountType;
        this.roleType = value.roleType;
    }

    public UserAccountTypeRoleMap(
        Integer id,
        Integer accountType,
        Byte    roleType
    ) {
        this.id = id;
        this.accountType = accountType;
        this.roleType = roleType;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountType() {
        return this.accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Byte getRoleType() {
        return this.roleType;
    }

    public void setRoleType(Byte roleType) {
        this.roleType = roleType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserAccountTypeRoleMap (");

        sb.append(id);
        sb.append(", ").append(accountType);
        sb.append(", ").append(roleType);

        sb.append(")");
        return sb.toString();
    }
}
