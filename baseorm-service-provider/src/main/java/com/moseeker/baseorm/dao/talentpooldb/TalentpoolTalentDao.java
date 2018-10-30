package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.constant.TalentType;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolTalent;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolTalentRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    /**
     * 查找上传的人才信息
     * @param userIdList 用户编号集合
     * @param companyId 公司编号
     * @return 人才集合
     */
    public List<TalentpoolTalentRecord> getTalents(List<Integer> userIdList, int companyId) {

        return create
                .selectFrom(TalentpoolTalent.TALENTPOOL_TALENT)
                .where(TalentpoolTalent.TALENTPOOL_TALENT.UPLOAD.eq(TalentType.Upload.getValue()))
                .and(TalentpoolTalent.TALENTPOOL_TALENT.USER_ID.in(userIdList))
                .and(TalentpoolTalent.TALENTPOOL_TALENT.COMPANY_ID.eq(companyId))
                .fetch();
    }

    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolTalent> getTalents(Set<Integer> userIdList) {

        return create
                .selectFrom(TalentpoolTalent.TALENTPOOL_TALENT)
                .where(TalentpoolTalent.TALENTPOOL_TALENT.UPLOAD.eq(TalentType.Upload.getValue()))
                .and(TalentpoolTalent.TALENTPOOL_TALENT.USER_ID.in(userIdList))
                .fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolTalent.class);
    }
}
