package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTagUser;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrAutomaticTagUserRecord;
import org.jooq.impl.TableImpl;
import static com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolHrAutomaticTagUser.TALENTPOOL_HR_AUTOMATIC_TAG_USER;

/**
 * Created by zztaiwll on 18/10/22.
 */
public class TalentpoolHrAutomaticTagUserDao  extends JooqCrudImpl<TalentpoolHrAutomaticTagUser,TalentpoolHrAutomaticTagUserRecord> {
    public TalentpoolHrAutomaticTagUserDao(){
        super(TALENTPOOL_HR_AUTOMATIC_TAG_USER,TalentpoolHrAutomaticTagUser.class);
    }
    public TalentpoolHrAutomaticTagUserDao(TableImpl<TalentpoolHrAutomaticTagUserRecord> table, Class<TalentpoolHrAutomaticTagUser> talentpoolHrAutomaticTagUserClass) {
        super(table, talentpoolHrAutomaticTagUserClass);
    }
}
