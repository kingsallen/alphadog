package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidatePosition;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionRecord;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidatePositionDO;

import org.jooq.Condition;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
}
