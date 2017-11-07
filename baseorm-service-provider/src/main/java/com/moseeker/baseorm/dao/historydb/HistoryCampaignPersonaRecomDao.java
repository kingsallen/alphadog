package com.moseeker.baseorm.dao.historydb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.historydb.tables.HistoryCampaignPersonaRecom;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryCampaignPersonaRecomRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/11/3.
 */
@Service
public class HistoryCampaignPersonaRecomDao extends JooqCrudImpl<com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom,HistoryCampaignPersonaRecomRecord> {

    public HistoryCampaignPersonaRecomDao(){
        super(HistoryCampaignPersonaRecom.HISTORY_CAMPAIGN_PERSONA_RECOM,com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom.class);
    }
    public HistoryCampaignPersonaRecomDao(TableImpl<HistoryCampaignPersonaRecomRecord> table, Class<com.moseeker.baseorm.db.historydb.tables.pojos.HistoryCampaignPersonaRecom> historyCampaignPersonaRecomPojoClass) {
        super(table, historyCampaignPersonaRecomPojoClass);
    }

}
