package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveDetailRecord;
import com.moseeker.thrift.gen.dao.struct.talentpooldb.TalentPoolProfileMoveDetailDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import static org.jooq.impl.DSL.*;

import static com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileMoveDetail.TALENTPOOL_PROFILE_MOVE_DETAIL;
import static com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD;

/**
 * @author cjm
 * @date 2018-09-07 15:59
 **/
@Repository
public class TalentPoolProfileMoveDetailDao extends JooqCrudImpl<TalentPoolProfileMoveDetailDO, TalentpoolProfileMoveDetailRecord> {

    public TalentPoolProfileMoveDetailDao() {
        super(TALENTPOOL_PROFILE_MOVE_DETAIL, TalentPoolProfileMoveDetailDO.class);
    }

    public TalentPoolProfileMoveDetailDao(TableImpl<TalentpoolProfileMoveDetailRecord> table, Class<TalentPoolProfileMoveDetailDO> talentPoolProfileMoveDetailDOClass) {
        super(table, talentPoolProfileMoveDetailDOClass);
    }

    public TalentpoolProfileMoveDetailRecord getByMobileAndState(long mobile, byte state) {
        return create.selectFrom(TALENTPOOL_PROFILE_MOVE_DETAIL)
                .where(TALENTPOOL_PROFILE_MOVE_DETAIL.MOBILE.eq(mobile))
                .and(TALENTPOOL_PROFILE_MOVE_DETAIL.PROFILE_MOVE_STATUS.eq(state))
                .fetchOne();
    }

    /**
     * 条件插入，如果简历搬家detail中的搬家状态与简历搬家主表中的搬家状态相同时，执行插入操作
     * @param record TalentpoolProfileMoveDetailRecord
     * @author  cjm
     * @date  2018/9/7
     * @return 成功的条数
     */
    public int addWhereExistStatus(TalentpoolProfileMoveDetailRecord record) {
        return create.insertInto(TALENTPOOL_PROFILE_MOVE_DETAIL,
                TALENTPOOL_PROFILE_MOVE_DETAIL.MOBILE,
                TALENTPOOL_PROFILE_MOVE_DETAIL.PROFILE_MOVE_ID,
                TALENTPOOL_PROFILE_MOVE_DETAIL.PROFILE_MOVE_STATUS)
                .select(
                        select(
                                param(record.getMobile()+"", TALENTPOOL_PROFILE_MOVE_DETAIL.MOBILE.getType()),
                                param(record.getProfileMoveId()+"", TALENTPOOL_PROFILE_MOVE_DETAIL.PROFILE_MOVE_ID.getType()),
                                param(record.getProfileMoveStatus()+"", TALENTPOOL_PROFILE_MOVE_DETAIL.PROFILE_MOVE_STATUS.getType())
                        ).whereExists(
                                create.selectOne()
                                        .from(TALENTPOOL_PROFILE_MOVE_RECORD)
                                        .where(TALENTPOOL_PROFILE_MOVE_RECORD.STATUS.eq(record.getProfileMoveStatus())
                                                .and(TALENTPOOL_PROFILE_MOVE_RECORD.PROFILE_MOVE_ID.eq(record.getProfileMoveId()))
                                        )
                        ))
                .execute();
    }
}
