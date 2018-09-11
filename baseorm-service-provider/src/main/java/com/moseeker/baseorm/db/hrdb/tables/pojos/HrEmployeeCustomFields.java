/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * 员工认证自定义字段表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrEmployeeCustomFields implements Serializable {

    private static final long serialVersionUID = -579414064;

    private Integer id;
    private Integer companyId;
    private String  fname;
    private String  fvalues;
    private Integer forder;
    private Byte    disable;
    private Integer mandatory;
    private Integer status;
    private Integer optionType;

    public HrEmployeeCustomFields() {}

    public HrEmployeeCustomFields(HrEmployeeCustomFields value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.fname = value.fname;
        this.fvalues = value.fvalues;
        this.forder = value.forder;
        this.disable = value.disable;
        this.mandatory = value.mandatory;
        this.status = value.status;
        this.optionType = value.optionType;
    }

    public HrEmployeeCustomFields(
        Integer id,
        Integer companyId,
        String  fname,
        String  fvalues,
        Integer forder,
        Byte    disable,
        Integer mandatory,
        Integer status,
        Integer optionType
    ) {
        this.id = id;
        this.companyId = companyId;
        this.fname = fname;
        this.fvalues = fvalues;
        this.forder = forder;
        this.disable = disable;
        this.mandatory = mandatory;
        this.status = status;
        this.optionType = optionType;
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

    public String getFname() {
        return this.fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFvalues() {
        return this.fvalues;
    }

    public void setFvalues(String fvalues) {
        this.fvalues = fvalues;
    }

    public Integer getForder() {
        return this.forder;
    }

    public void setForder(Integer forder) {
        this.forder = forder;
    }

    public Byte getDisable() {
        return this.disable;
    }

    public void setDisable(Byte disable) {
        this.disable = disable;
    }

    public Integer getMandatory() {
        return this.mandatory;
    }

    public void setMandatory(Integer mandatory) {
        this.mandatory = mandatory;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOptionType() {
        return this.optionType;
    }

    public void setOptionType(Integer optionType) {
        this.optionType = optionType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrEmployeeCustomFields (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(fname);
        sb.append(", ").append(fvalues);
        sb.append(", ").append(forder);
        sb.append(", ").append(disable);
        sb.append(", ").append(mandatory);
        sb.append(", ").append(status);
        sb.append(", ").append(optionType);

        sb.append(")");
        return sb.toString();
    }
}
