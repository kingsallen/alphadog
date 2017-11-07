package com.moseeker.baseorm.dao.campaigndb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.campaigndb.tables.CampaignPersonaRecom;
import com.moseeker.baseorm.db.campaigndb.tables.records.CampaignPersonaRecomRecord;
import com.moseeker.baseorm.db.userdb.tables.UserPositionEmail;
import com.moseeker.baseorm.pojo.CampaignPersonaRecomPojo;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/11/3.
 */
@Service
public class CampaignPersonaRecomDao extends JooqCrudImpl<CampaignPersonaRecomPojo,CampaignPersonaRecomRecord>{
    public CampaignPersonaRecomDao(){
        super(CampaignPersonaRecom.CAMPAIGN_PERSONA_RECOM,CampaignPersonaRecomPojo.class);
    }
    public CampaignPersonaRecomDao(TableImpl<CampaignPersonaRecomRecord> table, Class<CampaignPersonaRecomPojo> campaignPersonaRecomPojoClass) {
        super(table, campaignPersonaRecomPojoClass);
    }

    /*
        根据唯一索引擦如或者更新数据
     */
    public int inserOrUpdatePersonaRecom(int userId,int positionId){
        int result=create.insertInto(CampaignPersonaRecom.CAMPAIGN_PERSONA_RECOM, CampaignPersonaRecom.CAMPAIGN_PERSONA_RECOM.USER_ID, CampaignPersonaRecom.CAMPAIGN_PERSONA_RECOM.POSITION_ID)
                .values(userId, positionId)
                .onDuplicateKeyUpdate()
                .set(CampaignPersonaRecom.CAMPAIGN_PERSONA_RECOM.USER_ID, userId)
                .set(CampaignPersonaRecom.CAMPAIGN_PERSONA_RECOM.POSITION_ID,positionId)
                .execute();
        return result;

    }
}
