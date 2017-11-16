/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * 用户浏览者记录
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserWxViewer implements Serializable {

    private static final long serialVersionUID = -185509420;

    private Integer id;
    private Integer sysuserId;
    private String  idcode;
    private Integer clientType;

    public UserWxViewer() {}

    public UserWxViewer(UserWxViewer value) {
        this.id = value.id;
        this.sysuserId = value.sysuserId;
        this.idcode = value.idcode;
        this.clientType = value.clientType;
    }

    public UserWxViewer(
        Integer id,
        Integer sysuserId,
        String  idcode,
        Integer clientType
    ) {
        this.id = id;
        this.sysuserId = sysuserId;
        this.idcode = idcode;
        this.clientType = clientType;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSysuserId() {
        return this.sysuserId;
    }

    public void setSysuserId(Integer sysuserId) {
        this.sysuserId = sysuserId;
    }

    public String getIdcode() {
        return this.idcode;
    }

    public void setIdcode(String idcode) {
        this.idcode = idcode;
    }

    public Integer getClientType() {
        return this.clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserWxViewer (");

        sb.append(id);
        sb.append(", ").append(sysuserId);
        sb.append(", ").append(idcode);
        sb.append(", ").append(clientType);

        sb.append(")");
        return sb.toString();
    }
}
