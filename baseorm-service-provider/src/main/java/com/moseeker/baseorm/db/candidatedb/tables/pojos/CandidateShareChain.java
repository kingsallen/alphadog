/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.candidatedb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * 链路信息表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CandidateShareChain implements Serializable {

    private static final long serialVersionUID = 1399862835;

    private Integer   id;
    private Integer   positionId;
    private Integer   rootRecomUserId;
    private Integer   root2RecomUserId;
    private Integer   recomUserId;
    private Integer   presenteeUserId;
    private Integer   depth;
    private Integer   parentId;
    private Timestamp clickTime;
    private Timestamp createTime;
    private Byte      type;
    private String    forwardId;

    public CandidateShareChain() {}

    public CandidateShareChain(CandidateShareChain value) {
        this.id = value.id;
        this.positionId = value.positionId;
        this.rootRecomUserId = value.rootRecomUserId;
        this.root2RecomUserId = value.root2RecomUserId;
        this.recomUserId = value.recomUserId;
        this.presenteeUserId = value.presenteeUserId;
        this.depth = value.depth;
        this.parentId = value.parentId;
        this.clickTime = value.clickTime;
        this.createTime = value.createTime;
        this.type = value.type;
        this.forwardId = value.forwardId;
    }

    public CandidateShareChain(
        Integer   id,
        Integer   positionId,
        Integer   rootRecomUserId,
        Integer   root2RecomUserId,
        Integer   recomUserId,
        Integer   presenteeUserId,
        Integer   depth,
        Integer   parentId,
        Timestamp clickTime,
        Timestamp createTime,
        Byte      type,
        String    forwardId
    ) {
        this.id = id;
        this.positionId = positionId;
        this.rootRecomUserId = rootRecomUserId;
        this.root2RecomUserId = root2RecomUserId;
        this.recomUserId = recomUserId;
        this.presenteeUserId = presenteeUserId;
        this.depth = depth;
        this.parentId = parentId;
        this.clickTime = clickTime;
        this.createTime = createTime;
        this.type = type;
        this.forwardId = forwardId;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPositionId() {
        return this.positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getRootRecomUserId() {
        return this.rootRecomUserId;
    }

    public void setRootRecomUserId(Integer rootRecomUserId) {
        this.rootRecomUserId = rootRecomUserId;
    }

    public Integer getRoot2RecomUserId() {
        return this.root2RecomUserId;
    }

    public void setRoot2RecomUserId(Integer root2RecomUserId) {
        this.root2RecomUserId = root2RecomUserId;
    }

    public Integer getRecomUserId() {
        return this.recomUserId;
    }

    public void setRecomUserId(Integer recomUserId) {
        this.recomUserId = recomUserId;
    }

    public Integer getPresenteeUserId() {
        return this.presenteeUserId;
    }

    public void setPresenteeUserId(Integer presenteeUserId) {
        this.presenteeUserId = presenteeUserId;
    }

    public Integer getDepth() {
        return this.depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getParentId() {
        return this.parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Timestamp getClickTime() {
        return this.clickTime;
    }

    public void setClickTime(Timestamp clickTime) {
        this.clickTime = clickTime;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Byte getType() {
        return this.type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getForwardId() {
        return this.forwardId;
    }

    public void setForwardId(String forwardId) {
        this.forwardId = forwardId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CandidateShareChain (");

        sb.append(id);
        sb.append(", ").append(positionId);
        sb.append(", ").append(rootRecomUserId);
        sb.append(", ").append(root2RecomUserId);
        sb.append(", ").append(recomUserId);
        sb.append(", ").append(presenteeUserId);
        sb.append(", ").append(depth);
        sb.append(", ").append(parentId);
        sb.append(", ").append(clickTime);
        sb.append(", ").append(createTime);
        sb.append(", ").append(type);
        sb.append(", ").append(forwardId);

        sb.append(")");
        return sb.toString();
    }
}
