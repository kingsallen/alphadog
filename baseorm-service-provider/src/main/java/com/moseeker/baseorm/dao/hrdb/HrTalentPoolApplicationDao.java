package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrTalentpoolApplication;
import com.moseeker.baseorm.db.hrdb.tables.records.HrTalentpoolApplicationRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class HrTalentPoolApplicationDao extends JooqCrudImpl<com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpoolApplication,HrTalentpoolApplicationRecord> {
    public  HrTalentPoolApplicationDao(){
        super(HrTalentpoolApplication.HR_TALENTPOOL_APPLICATION,com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpoolApplication.class);
    }
    public HrTalentPoolApplicationDao(TableImpl<HrTalentpoolApplicationRecord> table, Class<com.moseeker.baseorm.db.hrdb.tables.pojos.HrTalentpoolApplication> hrTalentpoolApplicationClass) {
        super(table, hrTalentpoolApplicationClass);
    }

    public int inserOrUpdateTalentPoolApplication(int hrId,int companyId){
        int result=create.insertInto(HrTalentpoolApplication.HR_TALENTPOOL_APPLICATION, HrTalentpoolApplication.HR_TALENTPOOL_APPLICATION.HR_ID, HrTalentpoolApplication.HR_TALENTPOOL_APPLICATION
                .COMPANY_ID)
                .values(hrId, companyId)
                .onDuplicateKeyUpdate()
                .set(HrTalentpoolApplication.HR_TALENTPOOL_APPLICATION.UPDATE_TIME,new Timestamp(System.currentTimeMillis()))
                .execute();
        return result;

    }
}
