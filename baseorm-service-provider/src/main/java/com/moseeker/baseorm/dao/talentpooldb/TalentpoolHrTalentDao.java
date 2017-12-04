package com.moseeker.baseorm.dao.talentpooldb;
import com.moseeker.baseorm.crud.JooqCrudImpl;

import com.moseeker.baseorm.db.hrdb.tables.HrTalentpoolApplication;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolHrTalent;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrTalentRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolHrTalentDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrTalent,TalentpoolHrTalentRecord> {

    public TalentpoolHrTalentDao(){
        super(TalentpoolHrTalent.TALENTPOOL_HR_TALENT,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrTalent.class);
    }

    public TalentpoolHrTalentDao(TableImpl<TalentpoolHrTalentRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrTalent> talentpoolHrTalentClass) {
        super(table, talentpoolHrTalentClass);
    }

    public int upserTalpoolHrTalent(int userId,int hrId){
        int result=create.insertInto(TalentpoolHrTalent.TALENTPOOL_HR_TALENT, TalentpoolHrTalent.TALENTPOOL_HR_TALENT.HR_ID,
                TalentpoolHrTalent.TALENTPOOL_HR_TALENT.USER_ID)
                .values(hrId, userId)
                .onDuplicateKeyUpdate()
                .set(HrTalentpoolApplication.HR_TALENTPOOL_APPLICATION.UPDATE_TIME,new Timestamp(System.currentTimeMillis()))
                .execute();
        return result;
    }
}
