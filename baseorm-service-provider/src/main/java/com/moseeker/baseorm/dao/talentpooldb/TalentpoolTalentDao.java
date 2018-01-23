package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolTalent;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolTalentRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolTalentDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolTalent,TalentpoolTalentRecord> {

    public TalentpoolTalentDao(){
        super(TalentpoolTalent.TALENTPOOL_TALENT,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolTalent.class);
    }
    public TalentpoolTalentDao(TableImpl<TalentpoolTalentRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolTalent> talentpoolTalentClass) {
        super(table, talentpoolTalentClass);
    }

    public int updateNum(int userId,int companyId,int pubNum,int collectNum ){
        int result=create.update(TalentpoolTalent.TALENTPOOL_TALENT)
                .set(TalentpoolTalent.TALENTPOOL_TALENT.PUBLIC_NUM,TalentpoolTalent.TALENTPOOL_TALENT.PUBLIC_NUM.add(pubNum))
                .set(TalentpoolTalent.TALENTPOOL_TALENT.COLLECT_NUM,TalentpoolTalent.TALENTPOOL_TALENT.COLLECT_NUM.add(collectNum))
                .where(TalentpoolTalent.TALENTPOOL_TALENT.USER_ID.eq(userId))
                .and(TalentpoolTalent.TALENTPOOL_TALENT.COMPANY_ID.eq(companyId))
                .execute();
        return result;
    }

    public int batchUpdateNum(List<Integer> userIdList, int companyId, int pubNum, int collectNum ){
        int result=create.update(TalentpoolTalent.TALENTPOOL_TALENT)
                .set(TalentpoolTalent.TALENTPOOL_TALENT.PUBLIC_NUM,TalentpoolTalent.TALENTPOOL_TALENT.PUBLIC_NUM.add(pubNum))
                .set(TalentpoolTalent.TALENTPOOL_TALENT.COLLECT_NUM,TalentpoolTalent.TALENTPOOL_TALENT.COLLECT_NUM.add(collectNum))
                .where(TalentpoolTalent.TALENTPOOL_TALENT.USER_ID.in(userIdList))
                .and(TalentpoolTalent.TALENTPOOL_TALENT.COMPANY_ID.eq(companyId))
                .execute();
        return result;
    }
}
