package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrAutomaticTagRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolHrAutomaticTag.TALENTPOOL_HR_AUTOMATIC_TAG;

/**
 * Created by zztaiwll on 18/10/22.
 */
@Repository
public class TalentpoolHrAutomaticTagDao extends JooqCrudImpl<TalentpoolHrAutomaticTag,TalentpoolHrAutomaticTagRecord> {
    public TalentpoolHrAutomaticTagDao(){
        super(TALENTPOOL_HR_AUTOMATIC_TAG,TalentpoolHrAutomaticTag.class);
    }
    public TalentpoolHrAutomaticTagDao(TableImpl<TalentpoolHrAutomaticTagRecord> table, Class<TalentpoolHrAutomaticTag> talentpoolHrAutomaticTagClass) {
        super(table, talentpoolHrAutomaticTagClass);
    }

}
