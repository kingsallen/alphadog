package com.moseeker.baseorm.dao.historydb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.historydb.tables.HistoryCampaignPersonaRecom;
import com.moseeker.baseorm.db.historydb.tables.records.HistoryCampaignPersonaRecomRecord;
import com.moseeker.baseorm.pojo.HistoryCampaignPersonaRecomPojo;
import org.jooq.impl.TableImpl;

/**
 * Created by zztaiwll on 17/11/3.
 */
public class HistoryCampaignPersonaRecomDao extends JooqCrudImpl<HistoryCampaignPersonaRecomPojo,HistoryCampaignPersonaRecomRecord> {

    public HistoryCampaignPersonaRecomDao(){
        super(HistoryCampaignPersonaRecom.HISTORY_CAMPAIGN_PERSONA_RECOM,HistoryCampaignPersonaRecomPojo.class);
    }
    public HistoryCampaignPersonaRecomDao(TableImpl<HistoryCampaignPersonaRecomRecord> table, Class<HistoryCampaignPersonaRecomPojo> historyCampaignPersonaRecomPojoClass) {
        super(table, historyCampaignPersonaRecomPojoClass);
    }

}
