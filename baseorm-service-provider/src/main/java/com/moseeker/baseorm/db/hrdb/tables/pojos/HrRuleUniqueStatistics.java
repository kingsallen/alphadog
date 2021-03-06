/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Date;

import javax.annotation.Generated;


/**
 * 微信图文传播去重信息统计表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrRuleUniqueStatistics implements Serializable {

    private static final long serialVersionUID = 185510585;

    private Integer id;
    private Integer wxruleId;
    private String  menuName;
    private Integer type;
    private Integer companyId;
    private Integer viewNumUv;
    private Date    createDate;
    private Integer infoType;

    public HrRuleUniqueStatistics() {}

    public HrRuleUniqueStatistics(HrRuleUniqueStatistics value) {
        this.id = value.id;
        this.wxruleId = value.wxruleId;
        this.menuName = value.menuName;
        this.type = value.type;
        this.companyId = value.companyId;
        this.viewNumUv = value.viewNumUv;
        this.createDate = value.createDate;
        this.infoType = value.infoType;
    }

    public HrRuleUniqueStatistics(
        Integer id,
        Integer wxruleId,
        String  menuName,
        Integer type,
        Integer companyId,
        Integer viewNumUv,
        Date    createDate,
        Integer infoType
    ) {
        this.id = id;
        this.wxruleId = wxruleId;
        this.menuName = menuName;
        this.type = type;
        this.companyId = companyId;
        this.viewNumUv = viewNumUv;
        this.createDate = createDate;
        this.infoType = infoType;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWxruleId() {
        return this.wxruleId;
    }

    public void setWxruleId(Integer wxruleId) {
        this.wxruleId = wxruleId;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getViewNumUv() {
        return this.viewNumUv;
    }

    public void setViewNumUv(Integer viewNumUv) {
        this.viewNumUv = viewNumUv;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getInfoType() {
        return this.infoType;
    }

    public void setInfoType(Integer infoType) {
        this.infoType = infoType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrRuleUniqueStatistics (");

        sb.append(id);
        sb.append(", ").append(wxruleId);
        sb.append(", ").append(menuName);
        sb.append(", ").append(type);
        sb.append(", ").append(companyId);
        sb.append(", ").append(viewNumUv);
        sb.append(", ").append(createDate);
        sb.append(", ").append(infoType);

        sb.append(")");
        return sb.toString();
    }
}
