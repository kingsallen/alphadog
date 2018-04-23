package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileFilter;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileFilterRecord;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

/**
 * Created by zztaiwll on 17/12/1.
 */
@Service
public class TalentpoolProfileFilterDao extends JooqCrudImpl<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter,TalentpoolProfileFilterRecord> {

    public TalentpoolProfileFilterDao(){
        super(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter.class);
    }
    public TalentpoolProfileFilterDao(TableImpl<TalentpoolProfileFilterRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter> talentpoolProfileFilterClass) {
        super(table, talentpoolProfileFilterClass);
    }

    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter> getProfileFilterByCompanyId(int companyId, List<Integer> disable,  int pageNum, int pageSize ){
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter> result=create.selectFrom(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER)
                .where(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.COMPANY_ID.eq(companyId))
                .and(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.DISABLE.in(disable))
                .orderBy(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.UPDATE_TIME.desc())
                .limit(pageSize).offset(pageNum)
                .fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter.class);
        return result;
    }



    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter> getTalentpoolProfileFilterByIdAndCompanyId(int companyId, List<Integer> disable,  int id ){
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter> result=create.selectFrom(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER)
                .where(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.COMPANY_ID.eq(companyId))
                .and(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.DISABLE.in(disable))
                .and(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.ID.eq(id))
                .fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter.class);
        return result;
    }

    /**
     *查询有效的简历筛选项
     * @param companyId
     * @param disable
     * @param idList
     * @return
     */
    public List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter> getTalentpoolProfileFilterByIdListAndCompanyId(int companyId, Integer disable,  List<Integer> idList ){
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter> result=create.selectFrom(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER)
                .where(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.COMPANY_ID.eq(companyId))
                .and(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.DISABLE.eq(disable))
                .and(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.ID.in(idList))
                .fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter.class);
        return result;
    }

    /**
     *查询有效的简历筛选项
     * @param companyId
     * @param disable
     * @param idList
     * @return
     */
    public Map<Integer,com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter> getTalentpoolProfileFilterMapByIdListAndCompanyId(int companyId, Integer disable, List<Integer> idList ){
        List<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter> result=create.selectFrom(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER)
                .where(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.COMPANY_ID.eq(companyId))
                .and(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.DISABLE.eq(disable))
                .and(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.ID.in(idList))
                .fetchInto(com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter.class);
        if(result != null && result.size()>0) {
            Map<Integer, com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter> filterMap = result
                    .stream()
                    .collect(Collectors.toMap(k -> k.getId(), v -> v, (oldKey, newKey) -> newKey));
           return filterMap;
        }
        return null;
    }


    public int inserOrUpdateTalentPoolProfileFilter(TalentpoolProfileFilterRecord record){
        int result=create.insertInto(TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER,TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.NAME, TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.COMPANY_ID,
                TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.ORIGINS,TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.WORK_YEARS,
                TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.CITY_NAME,TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.DEGREE,
                TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.PAST_POSITION,TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.IN_LAST_JOB_SEARCH_POSITION,
                TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.MIN_AGE,TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.MAX_AGE,
                TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.INTENTION_CITY_NAME,TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.INTENTION_SALARY_CODE,
                TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.SEX, TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.IS_RECOMMEND,
                TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.COMPANY_NAME,TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.IN_LAST_JOB_SEARCH_COMPANY,
                TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.CREATE_TIME, TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.UPDATE_TIME,
                TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.CITY_CODE,TalentpoolProfileFilter.TALENTPOOL_PROFILE_FILTER.INTENTION_CITY_CODE)
                .values(record.getName(), record.getCompanyId(), record.getOrigins(), record.getWorkYears(), record.getCityName(), record.getDegree(), record.getPastPosition(),
                        record.getInLastJobSearchPosition(), record.getMinAge(), record.getMaxAge(), record.getIntentionCityName(), record.getIntentionSalaryCode(),
                        record.getSex(), record.getIsRecommend(), record.getCompanyName(), record.getInLastJobSearchCompany(), new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()),
                        record.getCityCode(),record.getIntentionCityCode())
                .onDuplicateKeyIgnore()
                .execute();
        return result;

    }

}
