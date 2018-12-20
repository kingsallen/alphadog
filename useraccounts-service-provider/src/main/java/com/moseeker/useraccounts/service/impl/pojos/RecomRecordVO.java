package com.moseeker.useraccounts.service.impl.pojos;

import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateRecomRecordDO;

import java.util.Objects;

/**
 * @author cjm
 * @date 2018-12-20 22:30
 **/
public class RecomRecordVO {
    private Integer rootUserId;
    private Integer presenteeUserId;
    private Integer positionId;
    private String createTime;

    public Integer getRootUserId() {
        return rootUserId;
    }

    public void setRootUserId(Integer rootUserId) {
        this.rootUserId = rootUserId;
    }

    public Integer getPresenteeUserId() {
        return presenteeUserId;
    }

    public void setPresenteeUserId(Integer presenteeUserId) {
        this.presenteeUserId = presenteeUserId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecomRecordVO that = (RecomRecordVO) o;
        return Objects.equals(getRootUserId(), that.getRootUserId()) &&
                Objects.equals(getPresenteeUserId(), that.getPresenteeUserId()) &&
                Objects.equals(getPositionId(), that.getPositionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRootUserId(), getPresenteeUserId(), getPositionId());
    }

    public RecomRecordVO initFromCandidateRecomDO(CandidateRecomRecordDO recomRecordDO){
        this.setCreateTime(recomRecordDO.getCreateTime());
        this.setPositionId(recomRecordDO.getPositionId());
        this.setPresenteeUserId(recomRecordDO.getPresenteeUserId());
        this.setRootUserId(recomRecordDO.getPostUserId());
        return this;
    }
}
