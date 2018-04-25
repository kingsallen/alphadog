package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolEmail;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolEmailRecord;
import java.util.List;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolEmailDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail,TalentpoolEmailRecord> {

    public TalentpoolEmailDao(){
        super(TalentpoolEmail.TALENTPOOL_EMAIL,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail.class);
    }

    public TalentpoolEmailDao(TableImpl<TalentpoolEmailRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail> talentpoolEmailClass) {
        super(table, talentpoolEmailClass);
    }

    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail> getTalentpoolEmailByCompanyId(int company_id){
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail> result= create.selectFrom(TalentpoolEmail.TALENTPOOL_EMAIL)
                .where(TalentpoolEmail.TALENTPOOL_EMAIL.COMPANY_ID.eq(company_id))
                .fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail.class);
        return result;
    }

    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail> getTalentpoolEmailByCompanyIdAndConfigId(int company_id, int config_id){
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail> result= create.selectFrom(TalentpoolEmail.TALENTPOOL_EMAIL)
                .where(TalentpoolEmail.TALENTPOOL_EMAIL.COMPANY_ID.eq(company_id))
                .and(TalentpoolEmail.TALENTPOOL_EMAIL.CONFIG_ID.eq(config_id))
                .fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolEmail.class);
        return result;
    }
}
