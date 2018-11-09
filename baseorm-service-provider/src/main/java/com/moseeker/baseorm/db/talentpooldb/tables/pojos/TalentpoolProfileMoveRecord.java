/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.talentpooldb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 简历搬家操作记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TalentpoolProfileMoveRecord implements Serializable {

    private static final long serialVersionUID = -1141313915;

    private Integer   id;
    private Integer   profileMoveId;
    private Byte      crawlType;
    private Integer   crawlNum;
    private Byte      status;
    private Integer   currentEmailNum;
    private Integer   totalEmailNum;
    private Timestamp createTime;
    private Timestamp updateTime;

    public TalentpoolProfileMoveRecord() {}

    public TalentpoolProfileMoveRecord(TalentpoolProfileMoveRecord value) {
        this.id = value.id;
        this.profileMoveId = value.profileMoveId;
        this.crawlType = value.crawlType;
        this.crawlNum = value.crawlNum;
        this.status = value.status;
        this.currentEmailNum = value.currentEmailNum;
        this.totalEmailNum = value.totalEmailNum;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public TalentpoolProfileMoveRecord(
        Integer   id,
        Integer   profileMoveId,
        Byte      crawlType,
        Integer   crawlNum,
        Byte      status,
        Integer   currentEmailNum,
        Integer   totalEmailNum,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.profileMoveId = profileMoveId;
        this.crawlType = crawlType;
        this.crawlNum = crawlNum;
        this.status = status;
        this.currentEmailNum = currentEmailNum;
        this.totalEmailNum = totalEmailNum;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProfileMoveId() {
        return this.profileMoveId;
    }

    public void setProfileMoveId(Integer profileMoveId) {
        this.profileMoveId = profileMoveId;
    }

    public Byte getCrawlType() {
        return this.crawlType;
    }

    public void setCrawlType(Byte crawlType) {
        this.crawlType = crawlType;
    }

    public Integer getCrawlNum() {
        return this.crawlNum;
    }

    public void setCrawlNum(Integer crawlNum) {
        this.crawlNum = crawlNum;
    }

    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getCurrentEmailNum() {
        return this.currentEmailNum;
    }

    public void setCurrentEmailNum(Integer currentEmailNum) {
        this.currentEmailNum = currentEmailNum;
    }

    public Integer getTotalEmailNum() {
        return this.totalEmailNum;
    }

    public void setTotalEmailNum(Integer totalEmailNum) {
        this.totalEmailNum = totalEmailNum;
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
        StringBuilder sb = new StringBuilder("TalentpoolProfileMoveRecord (");

        sb.append(id);
        sb.append(", ").append(profileMoveId);
        sb.append(", ").append(crawlType);
        sb.append(", ").append(crawlNum);
        sb.append(", ").append(status);
        sb.append(", ").append(currentEmailNum);
        sb.append(", ").append(totalEmailNum);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
