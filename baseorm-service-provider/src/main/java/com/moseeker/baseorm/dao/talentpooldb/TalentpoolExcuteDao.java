package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolExecute;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolExecuteRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolExcuteDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolExecute,TalentpoolExecuteRecord> {

    public TalentpoolExcuteDao(){
        super(TalentpoolExecute.TALENTPOOL_EXECUTE,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolExecute.class);
    }
    public TalentpoolExcuteDao(TableImpl<TalentpoolExecuteRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolExecute> talentpoolrExecuteClass) {
        super(table, talentpoolrExecuteClass);
    }



}
