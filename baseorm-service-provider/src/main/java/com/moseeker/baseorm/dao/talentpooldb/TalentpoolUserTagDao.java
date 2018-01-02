package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolUserTag;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolUserTagRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolUserTagDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolUserTag,TalentpoolUserTagRecord> {

    public TalentpoolUserTagDao(){
        super(TalentpoolUserTag.TALENTPOOL_USER_TAG,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolUserTag.class);
    }

    public TalentpoolUserTagDao(TableImpl<TalentpoolUserTagRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolUserTag> talentpoolUserTagClass) {
        super(table, talentpoolUserTagClass);
    }
}

