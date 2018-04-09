package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolCompanyTag;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolCompanyTagUser;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolCompanyTagRecord;
import java.util.List;
import java.util.Map;

import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolCompanyTagDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag,TalentpoolCompanyTagRecord> {

    public TalentpoolCompanyTagDao(){
        super(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag.class);
    }
    public TalentpoolCompanyTagDao(TableImpl<TalentpoolCompanyTagRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag> talentpoolCompanyTagClass) {
        super(table, talentpoolCompanyTagClass);
    }

    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag> getCompanyTagByCompanyId(int companyId, int pageNum, int pageSize ){
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag> result=create.selectFrom(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG)
                .where(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.COMPANY_ID.eq(companyId))
                .orderBy(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.UPDATE_TIME.desc())
                .limit(pageSize).offset(pageNum)
                .fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag.class);
        return result;
    }

    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag> getCompanyTagByTagIdAndCompanyId(int companyId, int company_tag_id){
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag> result=create.selectFrom(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG)
                .where(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.COMPANY_ID.eq(companyId))
                .and(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.ID.eq(company_tag_id))
                .fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag.class);
        return result;
    }

    /*
         获取单个标签的map
         */
    public Map<String,Object> getTagById(int tagId){
        Map<String,Object> result= create.selectFrom(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG)
                .where(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.ID.eq(tagId)).fetchAnyMap();
        return result;
    }
}
