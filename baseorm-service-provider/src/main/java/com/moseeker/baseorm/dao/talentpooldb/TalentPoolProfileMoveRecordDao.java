package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileMove;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileMoveRecord;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecordRecord;
import com.moseeker.thrift.gen.dao.struct.talentpooldb.TalentPoolProfileMoveDO;
import com.moseeker.thrift.gen.dao.struct.talentpooldb.TalentPoolProfileMoveRecordDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 分页获取简历搬家操作记录
     * @param   hrId  hr.id
     * @param   startIndex 下标起始值
     * @param   pageSize  页面大小
     * @author  cjm
     * @date  2018/7/18
     * @return   List<ProfileMoveDO> 操作记录list列表
     */
    public List<TalentPoolProfileMoveDO> getMoveOperationList(int hrId, int startIndex, int pageSize) {
        return create.selectFrom(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE)
                .where(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.HR_ID.eq(hrId))
                .orderBy(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.CREATE_TIME.desc())
                .limit(startIndex, pageSize)
                .fetchInto(TalentPoolProfileMoveDO.class);
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
    public int updateRecordWithPositiveLock(TalentpoolProfileMoveRecordRecord record) {
        return create.update(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD)
                .set(TalentpoolProfileMoveRecord.TALENTPOOL_PROFILE_MOVE_RECORD.CRAWL_NUM, record.getCrawlNum())
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
}
