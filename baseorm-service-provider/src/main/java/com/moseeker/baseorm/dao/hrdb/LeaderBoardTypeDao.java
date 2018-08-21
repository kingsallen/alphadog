package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.daos.HrLeaderBoardDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrLeaderBoard;
import com.moseeker.baseorm.db.hrdb.tables.records.HrLeaderBoardRecord;
import org.jooq.Configuration;
import org.jooq.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.hrdb.tables.HrLeaderBoard.HR_LEADER_BOARD;
import static org.jooq.impl.DSL.*;

/**
 * @Author: jack
 * @Date: 2018/8/20
 */
@Repository
public class LeaderBoardTypeDao extends HrLeaderBoardDao {

    @Autowired
    public LeaderBoardTypeDao(Configuration configuration) {
        super(configuration);
    }

    public HrLeaderBoard fetchOneByCompanyId(int companyId) {

        HrLeaderBoardRecord record = using(configuration())
                .selectFrom(HR_LEADER_BOARD)
                .where(HR_LEADER_BOARD.COMPANY_ID.eq(companyId))
                .fetchOne();
        if (record != null) {
            return record.into(HrLeaderBoard.class);
        } else {
            return null;
        }
    }

    public void upsert(int companyId, byte type) {
        HrLeaderBoardRecord record = using(configuration())
                .selectFrom(HR_LEADER_BOARD)
                .where(HR_LEADER_BOARD.COMPANY_ID.eq(companyId))
                .fetchOne();
        if (record != null) {
            if (type != record.getType().byteValue()) {
                record.setType(type);
                using(configuration()).attach(record);
                record.update();
            }
        } else {
            Param<Integer> companyIdParam = param(HR_LEADER_BOARD.COMPANY_ID.getName(), companyId);
            Param<Byte> typeParam = param(HR_LEADER_BOARD.TYPE.getName(), type);
            using(configuration())
                    .insertInto(HR_LEADER_BOARD,
                            HR_LEADER_BOARD.COMPANY_ID,
                            HR_LEADER_BOARD.TYPE)
                    .select(
                            select(
                                    companyIdParam,
                                    typeParam
                            )
                            .whereNotExists(
                                    selectOne()
                                    .from(HR_LEADER_BOARD)
                                    .where(HR_LEADER_BOARD.COMPANY_ID.eq(companyId))
                            )
                    )
                    .execute();

        }
    }
}
