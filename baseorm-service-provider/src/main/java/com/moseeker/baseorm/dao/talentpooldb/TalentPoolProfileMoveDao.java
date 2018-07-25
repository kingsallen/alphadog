package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileMove;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecord;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.talentpooldb.TalentPoolProfileMoveDO;
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
public class TalentPoolProfileMoveDao extends JooqCrudImpl<TalentPoolProfileMoveDO, TalentpoolProfileMoveRecord> {

    public TalentPoolProfileMoveDao() {
        super(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE, TalentPoolProfileMoveDO.class);
    }

    public TalentPoolProfileMoveDao(TableImpl<TalentpoolProfileMoveRecord> table, Class<TalentPoolProfileMoveDO> talentPoolProfileMoveDOClass) {
        super(table, talentPoolProfileMoveDOClass);
    }

    /**
     * 插入一条数据
     * @param  record 简历搬家所需参数
     * @author  cjm
     * @date  2018/7/18
     * @return  TalentPoolProfileMoveDO
     */
    @Transactional(rollbackFor = Exception.class)
    public TalentpoolProfileMoveRecord insertInitDO(TalentpoolProfileMoveRecord record) {
        record.setStatus((byte)0);
        return addRecord(record);
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

    public TalentpoolProfileMoveRecord getProfileMoveById(int operationId) {
        return create.selectFrom(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE)
                .where(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.ID.eq(operationId))
                .fetchOne();
    }

    public List<TalentPoolProfileMoveDO> getProfileMoveByStatusAndDate(byte status, Timestamp date) {
        return create.selectFrom(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE)
                .where(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.STATUS.eq(status))
                .and(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.CREATE_TIME.le(date))
                .fetchInto(TalentPoolProfileMoveDO.class);
    }

    /**
     * 批量更新简历搬家状态
     * @param   idList id集合
     * @param   status 要更新的状态
     * @author  cjm
     * @date  2018/7/24
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchUpdateStatus(List<Integer> idList, byte status) throws BIZException {
        int rows = create.update(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE)
                .set(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.STATUS, status)
                .where(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.ID.in(idList))
                .execute();
        if(rows != idList.size()){
            throw new BIZException(-1, "数据库更新失败");
        }
    }

    /**
     * 更新record，乐观锁
     * @param   record 简历搬家记录
     * @author  cjm
     * @date  2018/7/24
     * @return 成功更新的行数
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateRecordWithPositiveLock(TalentpoolProfileMoveRecord record) {
        return create.update(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE)
                .set(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.CRAWL_NUM, record.getCrawlNum() + 1)
                .set(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.CURRENT_EMAIL_NUM, record.getCurrentEmailNum())
                .set(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.STATUS, record.getStatus())
                .where(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.ID.eq(record.getId()))
                .and(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.CRAWL_NUM.eq(record.getCrawlNum()))
                .execute();
    }

    public int getTotalCount(int hrId) {
        return create.selectCount()
                .from(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE)
                .where(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.HR_ID.eq(hrId))
                .fetchOne(0, int.class);
    }
}
