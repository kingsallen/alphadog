/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.configdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


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
public class ConfigOmsSwitchManagement implements Serializable {

    private static final long serialVersionUID = 1634456874;

    private Integer   id;
    private Integer   companyId;
    private Integer   moduleName;
    private String    moduleParam;
    private Byte      isValid;
    private Integer   version;
    private Timestamp createTime;
    private Timestamp updateTime;

    public ConfigOmsSwitchManagement() {}

    public ConfigOmsSwitchManagement(ConfigOmsSwitchManagement value) {
        this.id = value.id;
        this.companyId = value.companyId;
        this.moduleName = value.moduleName;
        this.moduleParam = value.moduleParam;
        this.isValid = value.isValid;
        this.version = value.version;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public ConfigOmsSwitchManagement(
        Integer   id,
        Integer   companyId,
        Integer   moduleName,
        String    moduleParam,
        Byte      isValid,
        Integer   version,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.companyId = companyId;
        this.moduleName = moduleName;
        this.moduleParam = moduleParam;
        this.isValid = isValid;
        this.version = version;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    public Integer getModuleName() {
        return this.moduleName;
    }

    public void setModuleName(Integer moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleParam() {
        return this.moduleParam;
    }

    public void setModuleParam(String moduleParam) {
        this.moduleParam = moduleParam;
    }

    public Byte getIsValid() {
        return this.isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ConfigOmsSwitchManagement (");

        sb.append(id);
        sb.append(", ").append(companyId);
        sb.append(", ").append(moduleName);
        sb.append(", ").append(moduleParam);
        sb.append(", ").append(isValid);
        sb.append(", ").append(version);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
