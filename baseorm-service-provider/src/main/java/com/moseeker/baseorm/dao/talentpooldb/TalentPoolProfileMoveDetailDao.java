package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveDetailRecord;
import com.moseeker.thrift.gen.dao.struct.talentpooldb.TalentPoolProfileMoveDetailDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import java.util.List;

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

    public TalentpoolProfileMoveDetailRecord getByHrIdAndMobile(int hrId, long mobile) {
        return create.selectFrom(TALENTPOOL_PROFILE_MOVE_DETAIL)
                .where(TALENTPOOL_PROFILE_MOVE_DETAIL.MOBILE.eq(mobile))
                .and(TALENTPOOL_PROFILE_MOVE_DETAIL.HR_ID.eq(hrId))
                .fetchOne();
    }

    /**
     * 条件插入，如果简历搬家detail中的搬家状态与简历搬家主表中的搬家状态相同时，执行插入操作
     * @param record TalentpoolProfileMoveDetailRecord
     * @param value 简历搬家状态
     * @author  cjm
     * @date  2018/9/7
     * @return 成功的条数
     */
    public int addWhereExistStatus(TalentpoolProfileMoveDetailRecord record, byte value) {
        return create.insertInto(TALENTPOOL_PROFILE_MOVE_DETAIL,
                TALENTPOOL_PROFILE_MOVE_DETAIL.MOBILE,
                TALENTPOOL_PROFILE_MOVE_DETAIL.PROFILE_MOVE_RECORD_ID,
                TALENTPOOL_PROFILE_MOVE_DETAIL.PROFILE_MOVE_STATUS)
                .select(
                        select(
                                value(record.getMobile()),
                                value(record.getProfileMoveRecordId()),
                                value(record.getProfileMoveStatus())
                        )
                        .whereExists(
                                create.selectOne()
                                        .from(TALENTPOOL_PROFILE_MOVE_RECORD)
                                        .where(TALENTPOOL_PROFILE_MOVE_RECORD.ID.eq(record.getProfileMoveRecordId()))
                                        .and(TALENTPOOL_PROFILE_MOVE_RECORD.STATUS.eq(value))
                                        ))
                .execute();
    }

    public int updateWhereExist(TalentpoolProfileMoveDetailRecord profileMoveDetailRecord, int profileMoveRecordId, byte status) {
        return create.update(TALENTPOOL_PROFILE_MOVE_DETAIL)
                .set(TALENTPOOL_PROFILE_MOVE_DETAIL.PROFILE_MOVE_STATUS, profileMoveDetailRecord.getProfileMoveStatus())
                .set(TALENTPOOL_PROFILE_MOVE_DETAIL.PROFILE_MOVE_RECORD_ID, profileMoveRecordId)
                .where(TALENTPOOL_PROFILE_MOVE_DETAIL.ID.eq(profileMoveDetailRecord.getId()))
                .andExists(create.selectOne()
                        .from(TALENTPOOL_PROFILE_MOVE_RECORD)
                        .where(TALENTPOOL_PROFILE_MOVE_RECORD.ID.eq(profileMoveRecordId))
                        .and(TALENTPOOL_PROFILE_MOVE_RECORD.STATUS.eq(status))
                )
                .execute();
    }

    public void batchUpdateStatus(List<Integer> failedIdList, byte status) {
        create.update(TALENTPOOL_PROFILE_MOVE_DETAIL)
                .set(TALENTPOOL_PROFILE_MOVE_DETAIL.PROFILE_MOVE_STATUS, status)
                .where(TALENTPOOL_PROFILE_MOVE_DETAIL.PROFILE_MOVE_RECORD_ID.in(failedIdList))
                .execute();
    }
}
