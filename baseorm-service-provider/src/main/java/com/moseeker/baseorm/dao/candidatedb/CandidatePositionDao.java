package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidatePosition;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionRecord;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionDO;

import org.jooq.Condition;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jack on 15/02/2017.
 */
@Repository
public class CandidatePositionDao extends JooqCrudImpl<CandidatePositionDO, CandidatePositionRecord> {

    public CandidatePositionDao() {
        super(CandidatePosition.CANDIDATE_POSITION, CandidatePositionDO.class);
    }

    public CandidatePositionDao(TableImpl<CandidatePositionRecord> table, Class<CandidatePositionDO> candidatePositionDOClass) {
        super(table, candidatePositionDOClass);
    }

    public void deleteCandidatePosition(int userId, int positionId) throws CURDException {
        CandidatePositionDO p = new CandidatePositionDO();
        p.setUserId(userId);
        p.setPositionId(positionId);
        this.deleteData(p);
    }

    /**
     * 根据职位编号和用户编号查找候选人浏览职位信息
     * @param positionIdAndUserId 职位编号和用户编号的集合
     * @return 候选人浏览职位记录集合
     */
    public List<CandidatePositionDO> listCandidatePositionsByPositionIDUserID(List<Map<Integer, Integer>> positionIdAndUserId) {
        List<CandidatePositionDO> positionDOList = new ArrayList<>();

        Condition condition = null;
        for(Map<Integer, Integer> map : positionIdAndUserId) {
            for(Map.Entry<Integer, Integer> entry : map.entrySet()) {
                Condition conditionValue = CandidatePosition.CANDIDATE_POSITION.POSITION_ID.equal(entry.getKey())
                        .and(CandidatePosition.CANDIDATE_POSITION.USER_ID.equal((int)(entry.getValue())));

                if(condition != null) {
                    condition = condition.or(conditionValue);
                } else {
                    condition = conditionValue;
                }
            }
        }

        positionDOList = create.select(CandidatePosition.CANDIDATE_POSITION.POSITION_ID, CandidatePosition.CANDIDATE_POSITION.USER_ID,
                CandidatePosition.CANDIDATE_POSITION.IS_INTERESTED, CandidatePosition.CANDIDATE_POSITION.VIEW_NUMBER)
                .from(CandidatePosition.CANDIDATE_POSITION)
                .where(condition).fetch().into(CandidatePositionDO.class);

        return positionDOList;
    }

    /**
     * 查找最近浏览的职位信息
     * @param userId
     * @param positionIdList
     * @return
     */
    public CandidatePositionRecord fetchRecentViewedPosition(int userId, List<Integer> positionIdList) {
        return create.selectFrom(CandidatePosition.CANDIDATE_POSITION)
                .where(CandidatePosition.CANDIDATE_POSITION.USER_ID.eq(userId))
                .and(CandidatePosition.CANDIDATE_POSITION.POSITION_ID.in(positionIdList))
                .orderBy(CandidatePosition.CANDIDATE_POSITION.UPDATE_TIME.desc())
                .limit(1)
                .fetchOne();
    }

    public int addDataIgnoreDuplicate(CandidatePositionDO cp){
        CandidatePosition T = CandidatePosition.CANDIDATE_POSITION;
        return create.insertInto(T)
                .columns(T.POSITION_ID,T.WXUSER_ID,T.IS_INTERESTED,T.CANDIDATE_COMPANY_ID,T.VIEW_NUMBER,T.SHARED_FROM_EMPLOYEE,T.USER_ID)
                .values(cp.getPositionId(),cp.getWxuserId(),cp.getIsInterested(),cp.getCandidateCompanyId(),cp.getViewNumber(),cp.getSharedFromEmployee(),cp.getUserId())
                .onDuplicateKeyIgnore()
                .execute();
    }

    /**
     * 查找最近浏览的职位信息
     * @param beRecomUserIds 被推荐人ids
     * @param positionIds 职位ids
     * @return list
     */
    public List<CandidatePositionDO> fetchRecentViewedByUserIdsAndPids(Set<Integer> beRecomUserIds, List<Integer> positionIds) {
        return create.selectFrom(CandidatePosition.CANDIDATE_POSITION)
                .where(CandidatePosition.CANDIDATE_POSITION.POSITION_ID.in(positionIds))
                .and(CandidatePosition.CANDIDATE_POSITION.USER_ID.in(beRecomUserIds))
                .orderBy(CandidatePosition.CANDIDATE_POSITION.VIEW_NUMBER)
                .fetchInto(CandidatePositionDO.class);
    }

    /**
     * 查找最近浏览的职位信息
     * @param beRecomUserIds 被推荐人ids
     * @param positionIds 职位ids
     * @return list
     */
    public List<CandidatePositionDO> fetchViewedByUserIdsAndPids(List<Integer> beRecomUserIds, List<Integer> positionIds) {
        return create.selectFrom(CandidatePosition.CANDIDATE_POSITION)
                .where(CandidatePosition.CANDIDATE_POSITION.POSITION_ID.in(positionIds))
                .and(CandidatePosition.CANDIDATE_POSITION.USER_ID.in(beRecomUserIds))
                .orderBy(CandidatePosition.CANDIDATE_POSITION.VIEW_NUMBER.desc())
                .fetchInto(CandidatePositionDO.class);
    }


    /**
     * 查找最近浏览的职位信息
     * @param beRecomUserIds 被推荐人ids
     * @param positionIds 职位ids
     * @return list
     */
    public List<CandidatePositionRecord> fetchViewedByUserIdsAndPidList(List<Integer> beRecomUserIds, List<Integer> positionIds) {
        return create.selectFrom(CandidatePosition.CANDIDATE_POSITION)
                .where(CandidatePosition.CANDIDATE_POSITION.POSITION_ID.in(positionIds))
                .and(CandidatePosition.CANDIDATE_POSITION.USER_ID.in(beRecomUserIds))
                .orderBy(CandidatePosition.CANDIDATE_POSITION.VIEW_NUMBER.desc())
                .fetch();
    }
    public List<CandidatePositionRecord> fetchRecentViewedByCompanyIds(List<Integer> candidateCompanyId) {
        return create.selectFrom(CandidatePosition.CANDIDATE_POSITION)
                .where(CandidatePosition.CANDIDATE_POSITION.CANDIDATE_COMPANY_ID.in(candidateCompanyId))
                .orderBy(CandidatePosition.CANDIDATE_POSITION.VIEW_NUMBER.desc(), CandidatePosition.CANDIDATE_POSITION.UPDATE_TIME.desc())
                .fetch();
    }

    public List<CandidatePositionRecord> fetchRecentViewedByUserIdAndPosition(List<Integer> userIds, List<Integer> positionIds) {
        return create.selectFrom(CandidatePosition.CANDIDATE_POSITION)
                .where(CandidatePosition.CANDIDATE_POSITION.USER_ID.in(userIds))
                .and(CandidatePosition.CANDIDATE_POSITION.POSITION_ID.in(positionIds))
                .fetch();
    }


}
