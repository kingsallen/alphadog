/*
 * This file is generated by jOOQ.
*/
package com.moseeker.baseorm.db.userdb.tables.pojos;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.annotation.Generated;


/**
 * VIEW
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class CandidateVJobPositionRecom implements Serializable {

    private static final long serialVersionUID = 1024245645;

    private Long      positionId;
    private Long      recomId;
    private Long      presenteeId;
    private Timestamp createTime;
    private String    nickname;

    public CandidateVJobPositionRecom() {}

    public CandidateVJobPositionRecom(CandidateVJobPositionRecom value) {
        this.positionId = value.positionId;
        this.recomId = value.recomId;
        this.presenteeId = value.presenteeId;
        this.createTime = value.createTime;
        this.nickname = value.nickname;
    }

    public CandidateVJobPositionRecom(
        Long      positionId,
        Long      recomId,
        Long      presenteeId,
        Timestamp createTime,
        String    nickname
    ) {
        this.positionId = positionId;
        this.recomId = recomId;
        this.presenteeId = presenteeId;
        this.createTime = createTime;
        this.nickname = nickname;
    }

    public Long getPositionId() {
        return this.positionId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public Long getRecomId() {
        return this.recomId;
    }

    public void setRecomId(Long recomId) {
        this.recomId = recomId;
    }

    public Long getPresenteeId() {
        return this.presenteeId;
    }

    public void setPresenteeId(Long presenteeId) {
        this.presenteeId = presenteeId;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CandidateVJobPositionRecom (");

        sb.append(positionId);
        sb.append(", ").append(recomId);
        sb.append(", ").append(presenteeId);
        sb.append(", ").append(createTime);
        sb.append(", ").append(nickname);

        sb.append(")");
        return sb.toString();
    }
}
