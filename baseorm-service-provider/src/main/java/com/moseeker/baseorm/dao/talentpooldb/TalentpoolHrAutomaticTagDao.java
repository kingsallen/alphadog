package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolHrAutomaticTag;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolHrAutomaticTagRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    /*
     按照id列表删除标签
     */
    public  int deletebyIdList(List<Integer> idList){
        int result=create.deleteFrom(TALENTPOOL_HR_AUTOMATIC_TAG).where(TALENTPOOL_HR_AUTOMATIC_TAG.ID.in(idList)).execute();
        return result;
    }

}
