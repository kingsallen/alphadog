package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileMove;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileMoveRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecordRecord;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.dao.struct.talentpooldb.TalentPoolProfileMoveDO;
import com.moseeker.thrift.gen.dao.struct.talentpooldb.TalentPoolProfileMoveRecordDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author cjm
 * @date 2018-07-18 15:59
 **/
@Repository
public class TalentPoolProfileMoveRecordDao extends JooqCrudImpl<TalentPoolProfileMoveRecordDO, TalentpoolProfileMoveRecordRecord> {

    public TalentPoolProfileMoveRecordDao() {
        super(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD, TalentPoolProfileMoveRecordDO.class);
    }

    public TalentPoolProfileMoveRecordDao(TableImpl<TalentpoolProfileMoveRecordRecord> table, Class<TalentPoolProfileMoveRecordDO> talentPoolProfileMoveDOClass) {
        super(table, talentPoolProfileMoveDOClass);
    }


    public TalentpoolProfileMoveRecordRecord getProfileMoveRecordById(int operationId) {
        return create.selectFrom(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD)
                .where(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.ID.eq(operationId))
                .fetchOne();
    }

    /**
     * 更新record，乐观锁
     * @param   record 简历搬家记录
     * @author  cjm
     * @date  2018/7/24
     * @return 成功更新的行数
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateRecordWithPositiveLock(TalentpoolProfileMoveRecordRecord record, int currentCrawlNum) {
        return create.update(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD)
                .set(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CRAWL_NUM, currentCrawlNum)
                .set(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CURRENT_EMAIL_NUM, record.getCurrentEmailNum())
                .set(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.STATUS, record.getStatus())
                .where(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.ID.eq(record.getId()))
                .and(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CRAWL_NUM.eq(record.getCrawlNum()))
                .execute();
    }

    public List<TalentPoolProfileMoveRecordDO> getListByMoveIds(List<Integer> moveIds) {
        return create.selectFrom(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD)
                .where(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.PROFILE_MOVE_ID.in(moveIds))
                .orderBy(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CREATE_TIME.desc())
                .fetchInto(TalentPoolProfileMoveRecordDO.class);
    }

    public int batchUpdateStatus(List<Integer> ids, byte value) {
        return create.update(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD)
                .set(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.STATUS, value)
                .where(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.PROFILE_MOVE_ID.in(ids))
                .execute();
    }

    public List<TalentPoolProfileMoveRecordDO> getProfileMoveByStatusAndDate(byte status, Timestamp timestamp) {
        return create.selectFrom(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD)
                .where(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.STATUS.eq(status))
                .and(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CREATE_TIME.le(timestamp))
                .fetchInto(TalentPoolProfileMoveRecordDO.class);
    }

    public List<TalentPoolProfileMoveRecordDO> getProfileMoveRecordByMoveIds(List<Integer> profileMoveIds) {
        return create.selectFrom(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD)
                .where(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.PROFILE_MOVE_ID.in(profileMoveIds))
                .fetchInto(TalentPoolProfileMoveRecordDO.class);
    }
}
