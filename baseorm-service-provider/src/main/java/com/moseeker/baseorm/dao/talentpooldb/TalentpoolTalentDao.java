package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolTalent;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolTalentRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

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
}
