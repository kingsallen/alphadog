/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 用户上传文件记录表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserUploadFiles implements Serializable {

    private static final long serialVersionUID = -1665119761;

    private Integer   id;
    private String    sceneId;
    private String    uniname;
    private String    sysuserId;
    private Integer   source;
    private String    filename;
    private String    url;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Byte      status;

    public UserUploadFiles() {}

    public UserUploadFiles(UserUploadFiles value) {
        this.id = value.id;
        this.sceneId = value.sceneId;
        this.uniname = value.uniname;
        this.sysuserId = value.sysuserId;
        this.source = value.source;
        this.filename = value.filename;
        this.url = value.url;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.status = value.status;
    }

    public UserUploadFiles(
        Integer   id,
        String    sceneId,
        String    uniname,
        String    sysuserId,
        Integer   source,
        String    filename,
        String    url,
        Timestamp createTime,
        Timestamp updateTime,
        Byte      status
    ) {
        this.id = id;
        this.sceneId = sceneId;
        this.uniname = uniname;
        this.sysuserId = sysuserId;
        this.source = source;
        this.filename = filename;
        this.url = url;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSceneId() {
        return this.sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getUniname() {
        return this.uniname;
    }

    public void setUniname(String uniname) {
        this.uniname = uniname;
    }

    public String getSysuserId() {
        return this.sysuserId;
    }

    public void setSysuserId(String sysuserId) {
        this.sysuserId = sysuserId;
    }

    public Integer getSource() {
        return this.source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public Byte getStatus() {
        return this.status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserUploadFiles (");

        sb.append(id);
        sb.append(", ").append(sceneId);
        sb.append(", ").append(uniname);
        sb.append(", ").append(sysuserId);
        sb.append(", ").append(source);
        sb.append(", ").append(filename);
        sb.append(", ").append(url);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(status);

        sb.append(")");
        return sb.toString();
    }
}
