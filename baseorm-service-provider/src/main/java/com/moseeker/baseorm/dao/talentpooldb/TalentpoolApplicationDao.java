package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolApplication;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolApplicationRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by zztaiwll on 17/12/5.
 */
@Service
public class TalentpoolApplicationDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolApplication,TalentpoolApplicationRecord> {

    public  TalentpoolApplicationDao(){
        super(TalentpoolApplication.TALENTPOOL_APPLICATION,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolApplication.class);
    }

    public TalentpoolApplicationDao(TableImpl<TalentpoolApplicationRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolApplication> talentpoolApplicationClass) {
        super(table, talentpoolApplicationClass);
    }

    public int inserOrUpdateTalentPoolApplication(int hrId, int companyId){
        int result=create.insertInto(TalentpoolApplication.TALENTPOOL_APPLICATION,TalentpoolApplication.TALENTPOOL_APPLICATION.HR_ID ,
                TalentpoolApplication.TALENTPOOL_APPLICATION.COMPANY_ID)
                .values(hrId, companyId)
                .onDuplicateKeyUpdate()
                .set(TalentpoolApplication.TALENTPOOL_APPLICATION.UPDATE_TIME,new Timestamp(System.currentTimeMillis()))
                .execute();
        return result;

    }
}
