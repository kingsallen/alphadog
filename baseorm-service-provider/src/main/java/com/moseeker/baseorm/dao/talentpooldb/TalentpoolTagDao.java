package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolTag;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolTalent;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolTagRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolTagDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolTag,TalentpoolTagRecord>{
    public TalentpoolTagDao(){
        super(TalentpoolTag.TALENTPOOL_TAG,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolTag.class);
    }
    public TalentpoolTagDao(TableImpl<TalentpoolTagRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolTag> talentpoolTagClass) {
        super(table, talentpoolTagClass);
    }
    public int updateTagNum(int id,int tagNum ){
        int result=create.update(TalentpoolTag.TALENTPOOL_TAG)
                .set(TalentpoolTag.TALENTPOOL_TAG.TALENT_NUM,TalentpoolTag.TALENTPOOL_TAG.TALENT_NUM.add(tagNum))
                .where(TalentpoolTag.TALENTPOOL_TAG.ID.eq(id))
                .execute();
        return result;
    }
}
