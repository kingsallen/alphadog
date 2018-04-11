package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileFilterExecute;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileFilterExecuteRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolProfileFilterExcuteDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilterExecute,TalentpoolProfileFilterExecuteRecord> {

    public TalentpoolProfileFilterExcuteDao(){
        super(TalentpoolProfileFilterExecute.TALENTPOOL_PROFILE_FILTER_EXECUTE,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilterExecute.class);
    }
    public TalentpoolProfileFilterExcuteDao(TableImpl<TalentpoolProfileFilterExecuteRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilterExecute> talentpoolProfileFilterExecuteClass) {
        super(table, talentpoolProfileFilterExecuteClass);
    }



}
