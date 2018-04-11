package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import static com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolTag.TALENTPOOL_TAG;
import com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolTag;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolTagRecord;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolTagDao extends JooqCrudImpl<TalentpoolTag,TalentpoolTagRecord>{
    public TalentpoolTagDao(){
        super(TALENTPOOL_TAG,TalentpoolTag.class);
    }
    public TalentpoolTagDao(TableImpl<TalentpoolTagRecord> table, Class<TalentpoolTag> talentpoolTagClass) {
        super(table, talentpoolTagClass);
    }
    public int updateTagNum(int id,int tagNum ){
        int result=create.update(TALENTPOOL_TAG)
                .set(TALENTPOOL_TAG.TALENT_NUM,TALENTPOOL_TAG.TALENT_NUM.add(tagNum))
                .where(TALENTPOOL_TAG.ID.eq(id))
                .execute();
        return result;
    }

    public int updateTagListNum(Set<Integer> idList, int tagNum ){
        int result=create.update(TALENTPOOL_TAG)
                .set(TALENTPOOL_TAG.TALENT_NUM,TALENTPOOL_TAG.TALENT_NUM.add(tagNum))
                .where(TALENTPOOL_TAG.ID.in(idList))
                .execute();
        return result;
    }
    /*
     分页获取标签
     */
    public List<TalentpoolTag> getTagByPage(int hrId,int pageFrom,int pageSize){
        List<TalentpoolTag> list=create.selectFrom(TALENTPOOL_TAG).where(TALENTPOOL_TAG.HR_ID.eq(hrId))
                .orderBy(TALENTPOOL_TAG.UPDATE_TIME).fetchInto(TalentpoolTag.class);
        return list;
    }
}
