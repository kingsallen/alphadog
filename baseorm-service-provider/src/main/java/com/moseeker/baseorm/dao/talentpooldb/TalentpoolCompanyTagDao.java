package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolCompanyTag;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolCompanyTagUser;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolCompanyTagRecord;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public List<Map<String, Object>> getCompanyTagByCompanyId(int companyId, int pageNum, int pageSize ){
        List<Map<String, Object>> result=create.selectFrom(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG)
                .where(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.COMPANY_ID.eq(companyId))
                .and(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.DISABLE.eq(1))
                .orderBy(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.UPDATE_TIME.desc())
                .limit(pageSize).offset(pageNum)
                .fetchMaps();
        return result;
    }

    public Map<String, Object> getCompanyTagByTagIdAndCompanyId(int companyId, int company_tag_id){
        Map<String, Object> result=create.selectFrom(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG)
                .where(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.COMPANY_ID.eq(companyId))
                .and(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.ID.eq(company_tag_id))
                .and(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.DISABLE.eq(1))
                .fetchAnyMap();
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


    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag> getCompanyTagByTagIdSet(int companyId,Set<Integer> tagIdSet){
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag> result=create.selectFrom(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG)
                .where(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.COMPANY_ID.eq(companyId))
                .and(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.ID.in(tagIdSet))
                .and(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.DISABLE.eq(1))
                .fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolCompanyTag.class);
        return result;
    }

    public List<Map<String,Object>> getCompanyTagMapByCompanyId(int companyId ){
        List<Map<String,Object>> result= create.selectFrom(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG)
                .where(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.COMPANY_ID.eq(companyId))
                .and(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.DISABLE.eq(1))
                .orderBy(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.UPDATE_TIME.desc())
                .fetchMaps();
        return result;
    }

    public int inserOrUpdateTalentPoolCompanyTag(TalentpoolCompanyTagRecord record){
        int result=create.insertInto(TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG,TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.NAME, TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.COMPANY_ID,
                TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.ORIGINS,TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.WORK_YEARS,
                TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.COLOR,
                TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.CITY_NAME,TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.DEGREE,
                TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.PAST_POSITION,TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.IN_LAST_JOB_SEARCH_POSITION,
                TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.MIN_AGE,TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.MAX_AGE,
                TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.INTENTION_CITY_NAME,TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.INTENTION_SALARY_CODE,
                TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.SEX, TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.IS_RECOMMEND,
                TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.COMPANY_NAME,TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.IN_LAST_JOB_SEARCH_COMPANY,
                TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.CREATE_TIME, TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.UPDATE_TIME,
                TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.CITY_CODE,TalentpoolCompanyTag.TALENTPOOL_COMPANY_TAG.INTENTION_CITY_CODE)
                .values(record.getName(), record.getCompanyId(), record.getOrigins(), record.getWorkYears(), record.getColor(), record.getCityName(), record.getDegree(), record.getPastPosition(),
                        record.getInLastJobSearchPosition(), record.getMinAge(), record.getMaxAge(), record.getIntentionCityName(), record.getIntentionSalaryCode(),
                        record.getSex(), record.getIsRecommend(), record.getCompanyName(), record.getInLastJobSearchCompany(), new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()),
                        record.getCityCode(),record.getIntentionCityCode())
                .onDuplicateKeyIgnore()
                .execute();
        return result;
    }
}
