package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileMove;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileMoveRecord;
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

    public List<TalentPoolProfileMoveDO> getListByHrIdAndChannel(int hrId, int channel){
        return create.selectFrom(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE)
                .where(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.HR_ID.eq(hrId))
                .and(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.CHANNEL.eq((byte)channel))
                .fetchInto(TalentPoolProfileMoveDO.class);
    }

    public int getTotalCount(int hrId) {
        return create.selectCount()
                .from(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE)
                .where(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.HR_ID.eq(hrId))
                .fetchOne(0, int.class);
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

    public TalentpoolProfileMoveRecord fetchRecordById(int profileMoveId) {
        return create.selectFrom(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE)
                .where(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.ID.eq(profileMoveId))
                .fetchOne();
    }

    public List<TalentPoolProfileMoveDO> getProfileMoveDOByHrId(int hrId) {
        return create.selectFrom(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE)
                .where(TalentpoolProfileMove.TALENTPOOL_PROFILE_MOVE.HR_ID.eq(hrId))
                .fetchInto(TalentPoolProfileMoveDO.class);
    }
}
