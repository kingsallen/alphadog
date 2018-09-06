/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.hrdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 团队成员信息
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class HrTeamMember implements Serializable {

    private static final long serialVersionUID = -1754682277;

    private Integer   id;
    private String    name;
    private String    title;
    private String    description;
    private Integer   teamId;
    private Integer   userId;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer   resId;
    private Integer   disable;
    private String    resAttrs;
    private Integer   orders;

    public HrTeamMember() {}

    public HrTeamMember(HrTeamMember value) {
        this.id = value.id;
        this.name = value.name;
        this.title = value.title;
        this.description = value.description;
        this.teamId = value.teamId;
        this.userId = value.userId;
        this.createTime = value.createTime;
        this.updateTime = value.updateTime;
        this.resId = value.resId;
        this.disable = value.disable;
        this.resAttrs = value.resAttrs;
        this.orders = value.orders;
    }

    public HrTeamMember(
        Integer   id,
        String    name,
        String    title,
        String    description,
        Integer   teamId,
        Integer   userId,
        Timestamp createTime,
        Timestamp updateTime,
        Integer   resId,
        Integer   disable,
        String    resAttrs,
        Integer   orders
    ) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.description = description;
        this.teamId = teamId;
        this.userId = userId;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.resId = resId;
        this.disable = disable;
        this.resAttrs = resAttrs;
        this.orders = orders;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTeamId() {
        return this.teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getResId() {
        return this.resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getDisable() {
        return this.disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }

    public String getResAttrs() {
        return this.resAttrs;
    }

    public void setResAttrs(String resAttrs) {
        this.resAttrs = resAttrs;
    }

    public Integer getOrders() {
        return this.orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HrTeamMember (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(title);
        sb.append(", ").append(description);
        sb.append(", ").append(teamId);
        sb.append(", ").append(userId);
        sb.append(", ").append(createTime);
        sb.append(", ").append(updateTime);
        sb.append(", ").append(resId);
        sb.append(", ").append(disable);
        sb.append(", ").append(resAttrs);
        sb.append(", ").append(orders);

        sb.append(")");
        return sb.toString();
    }
}
