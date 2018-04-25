/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.dictdb.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


/**
 * 行业一级分类字典表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.1"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DictIndustryType implements Serializable {

    private static final long serialVersionUID = -955103181;

    private Integer code;
    private String  name;
    private String  companyImg;
    private String  jobImg;
    private String  teamImg;
    private String  pcImg;

    public DictIndustryType() {}

    public DictIndustryType(DictIndustryType value) {
        this.code = value.code;
        this.name = value.name;
        this.companyImg = value.companyImg;
        this.jobImg = value.jobImg;
        this.teamImg = value.teamImg;
        this.pcImg = value.pcImg;
    }

    public DictIndustryType(
        Integer code,
        String  name,
        String  companyImg,
        String  jobImg,
        String  teamImg,
        String  pcImg
    ) {
        this.code = code;
        this.name = name;
        this.companyImg = companyImg;
        this.jobImg = jobImg;
        this.teamImg = teamImg;
        this.pcImg = pcImg;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyImg() {
        return this.companyImg;
    }

    public void setCompanyImg(String companyImg) {
        this.companyImg = companyImg;
    }

    public String getJobImg() {
        return this.jobImg;
    }

    public void setJobImg(String jobImg) {
        this.jobImg = jobImg;
    }

    public String getTeamImg() {
        return this.teamImg;
    }

    public void setTeamImg(String teamImg) {
        this.teamImg = teamImg;
    }

    public String getPcImg() {
        return this.pcImg;
    }

    public void setPcImg(String pcImg) {
        this.pcImg = pcImg;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("DictIndustryType (");

        sb.append(code);
        sb.append(", ").append(name);
        sb.append(", ").append(companyImg);
        sb.append(", ").append(jobImg);
        sb.append(", ").append(teamImg);
        sb.append(", ").append(pcImg);

        sb.append(")");
        return sb.toString();
    }
}
