package com.moseeker.baseorm.dao.talentpooldb;
import com.moseeker.baseorm.crud.JooqCrudImpl;

import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolHrTalent;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrTalentRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

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
}
