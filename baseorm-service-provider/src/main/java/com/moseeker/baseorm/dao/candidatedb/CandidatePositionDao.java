package com.moseeker.baseorm.dao.candidatedb;

import com.moseeker.baseorm.db.candidatedb.tables.CandidatePosition;
import com.moseeker.baseorm.db.candidatedb.tables.records.CandidatePositionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.CURDException;
import com.moseeker.thrift.gen.dao.struct.CandidatePositionDO;
import com.moseeker.thrift.gen.profile.struct.Intention;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SelectJoinStep;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jack on 15/02/2017.
 */
@Component
public class CandidatePositionDao extends StructDaoImpl<CandidatePositionDO, CandidatePositionRecord, CandidatePosition> {


    @Override
    protected void initJOOQEntity() {
        this.tableLike = CandidatePosition.CANDIDATE_POSITION;
    }

    public void deleteCandidatePosition(int userId, int positionId) throws CURDException {
        CandidatePositionDO p = new CandidatePositionDO();
        p.setUserId(userId);
        p.setPositionId(positionId);
        this.deleteResource(p);
    }

    /**
     * 根据职位编号和用户编号查找候选人浏览职位信息
     * @param positionIdAndUserId 职位编号和用户编号的集合
     * @return 候选人浏览职位记录集合
     */
    public List<CandidatePositionDO> listCandidatePositionsByPositionIDUserID(List<Map<Integer, Integer>> positionIdAndUserId) {
        List<CandidatePositionDO> positionDOList = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

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


        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }


        return positionDOList;
    }
}
