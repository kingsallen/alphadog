package com.moseeker.useraccounts.service.impl.vo;

import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateShareChainDO;

import java.util.Objects;

/**
 * @author cjm
 * @date 2018-12-10 19:13
 **/
public class RecommendCard {

    private Integer id;
    private Integer positionId;
    private Integer rootUserId;
    private Integer root2UserId;
    private Integer recomUserId;
    private Integer presenteeUserId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendCard that = (RecommendCard) o;
        return Objects.equals(positionId, that.positionId) &&
                Objects.equals(rootUserId, that.rootUserId) &&
                Objects.equals(root2UserId, that.root2UserId) &&
                Objects.equals(recomUserId, that.recomUserId) &&
                Objects.equals(presenteeUserId, that.presenteeUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionId, rootUserId, root2UserId, recomUserId, presenteeUserId);
    }

    public void cloneFromCandidateShareChainDO(CandidateShareChainDO candidateShareChainDO){
        this.positionId = candidateShareChainDO.getPositionId();
        this.presenteeUserId = candidateShareChainDO.getPresenteeUserId();
        this.recomUserId = candidateShareChainDO.getRecomUserId();
        this.root2UserId = candidateShareChainDO.getRoot2RecomUserId();
        this.rootUserId = candidateShareChainDO.getRootRecomUserId();
    }
}
