package com.moseeker.entity.pojo.company;/**
 * Created by zztaiwll on 19/3/7.
 */

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * @version 1.0
 * @className HrOperationAllRecord
 * @Description TODO
 * @Author zztaiwll
 * @DATE 19/3/7 下午6:09
 **/
public class HrOperationAllRecord {
    private Integer   id;
    private Integer   operatorId;
    private Integer   companyId;
    private Integer   userId;
    private String    title;
    private String    description;
    private Integer   opertaionType;
    private Integer   operatorType;
    private String optTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOpertaionType() {
        return opertaionType;
    }

    public void setOpertaionType(Integer opertaionType) {
        this.opertaionType = opertaionType;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }
}
