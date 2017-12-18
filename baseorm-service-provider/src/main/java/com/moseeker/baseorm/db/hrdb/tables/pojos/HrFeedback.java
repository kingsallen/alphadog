/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * HR反馈表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrFeedback implements Serializable {

    private static final long serialVersionUID = 1063226924;

    private Integer   id;
    private Integer   hraccountId;
    private String    name;
    private String    email;
    private String    images;
    private String    content;
    private Timestamp createTime;
    private Timestamp updateTime;

    public HrFeedback() {}

    public HrFeedback(HrFeedback value) {
        this.id = value.id;
        this.hraccountId = value.hraccountId;
        this.name = value.name;
        this.email = value.email;
        this.images = value.images;
        this.content = value.content;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
    }

    public HrFeedback(
        Integer   id,
        Integer   hraccountId,
        String    name,
        String    email,
        String    images,
        String    content,
        Timestamp createTime,
        Timestamp updateTime
    ) {
        this.id = id;
        this.hraccountId = hraccountId;
        this.name = name;
        this.email = email;
        this.images = images;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHraccountId() {
        return this.hraccountId;
    }

    public void setHraccountId(Integer hraccountId) {
        this.hraccountId = hraccountId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImages() {
        return this.images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
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
        StringBuilder sb = new StringBuilder("HrFeedback (");

        sb.append(id);
        sb.append(", ").append(hraccountId);
        sb.append(", ").append(name);
        sb.append(", ").append(email);
        sb.append(", ").append(images);
        sb.append(", ").append(content);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);

        sb.append(")");
        return sb.toString();
    }
}
