/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * 员工部门表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrEmployeeSection implements Serializable {

    private static final long serialVersionUID = 2106880673;

    private Integer id;
    private Integer companyId;
    private String  name;
    private Integer priority;
    private Byte    status;

    public HrEmployeeSection() {}

    public HrEmployeeSection(HrEmployeeSection value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.name = value.name;
        this.priority = value.priority;
        this.status = value.status;
    }

    public HrEmployeeSection(
        Integer id,
        Integer companyId,
        String  name,
        Integer priority,
        Byte    status
    ) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.priority = priority;
        this.status = status;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrEmployeeSection (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(name);
        sb.append(", ").append(priority);
        sb.append(", ").append(status);

        sb.append(")");
        return sb.toString();
    }
}
