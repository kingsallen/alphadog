package com.moseeker.baseorm.dao.talentpooldb;
import com.moseeker.baseorm.crud.JooqCrudImpl;

import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolHrTalent;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrTalentRecord;
import com.moseeker.common.util.StringUtils;
import org.jooq.Record;
import org.jooq.SelectOnConditionStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolHrTalentDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrTalent,TalentpoolHrTalentRecord> {

    public TalentpoolHrTalentDao() {
        super(TalentpoolHrTalent.TALENTPOOL_HR_TALENT, com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrTalent.class);
    }

    public TalentpoolHrTalentDao(TableImpl<TalentpoolHrTalentRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrTalent> talentpoolHrTalentClass) {
        super(table, talentpoolHrTalentClass);
    }

    public int upserTalpoolHrTalent(int userId, int hrId) {
        int result = create.insertInto(TalentpoolHrTalent.TALENTPOOL_HR_TALENT, TalentpoolHrTalent.TALENTPOOL_HR_TALENT.HR_ID,
                TalentpoolHrTalent.TALENTPOOL_HR_TALENT.USER_ID)
                .values(hrId, userId)
                .onDuplicateKeyUpdate()
                .set(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.UPDATE_TIME, new Timestamp(System.currentTimeMillis()))
                .execute();
        return result;
    }

    public int getPublicCount(Set<Integer> hrIdSet) {
        if (StringUtils.isEmptySet(hrIdSet)) {
            return 0;
        }
        List<Map<String, Object>> records = create.selectDistinct(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.USER_ID).from(TalentpoolHrTalent.TALENTPOOL_HR_TALENT)
                .where(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.HR_ID.in(hrIdSet)).and(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.PUBLIC.eq((byte) 1)).fetchMaps();
        if (!StringUtils.isEmptyList(records)) {
            return records.size();
        }
        return 0;
    }
    public int getAllTalentCount(Set<Integer> hrIdSet) {
        if (StringUtils.isEmptySet(hrIdSet)) {
            return 0;
        }
        List<Map<String, Object>> records = create.selectDistinct(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.USER_ID).from(TalentpoolHrTalent.TALENTPOOL_HR_TALENT)
                .where(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.HR_ID.in(hrIdSet)).fetchMaps();
        if (!StringUtils.isEmptyList(records)) {
            return records.size();
        }
        return 0;
    }
    public  List<Map<String, Object>> getAllPublicTalent(Set<Integer> hrIdSet,int pageNum,int pageSize) {
        if (StringUtils.isEmptySet(hrIdSet)) {
            return null;
        }
        List<Map<String, Object>> records = create.selectDistinct(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.USER_ID).from(TalentpoolHrTalent.TALENTPOOL_HR_TALENT)
                .where(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.HR_ID.in(hrIdSet)).limit((pageNum-1)*pageSize,pageSize).fetchMaps();

        return records;
    }

    public void updateTalentpoolHrTalentPublic(Integer userId, int hrId) {
        create.update(TalentpoolHrTalent.TALENTPOOL_HR_TALENT)
                .set(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.PUBLIC, (byte)1)
                .where(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.HR_ID.eq(hrId))
                .and(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.USER_ID.eq(userId))
                .execute();
    }

    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrTalent> getDataByUserId(Set<Integer> userIdSet){
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrTalent> list=create.selectFrom(TalentpoolHrTalent.TALENTPOOL_HR_TALENT)
                .where(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.USER_ID.in(userIdSet)).fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrTalent.class);
        return list;
    }

    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrTalent> getDataByUserIdAndHrId(Set<Integer> userIdSet,Set<Integer> hrIdSet){
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrTalent> list=create.selectFrom(TalentpoolHrTalent.TALENTPOOL_HR_TALENT)
                .where(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.USER_ID.in(userIdSet))
                .and(TalentpoolHrTalent.TALENTPOOL_HR_TALENT.HR_ID.in(hrIdSet))
                .fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrTalent.class);
        return list;
    }
}
