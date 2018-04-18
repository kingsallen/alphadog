package com.moseeker.baseorm.dao.talentpooldb;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileFilter;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileFilterRecord;
import com.moseeker.common.util.query.Condition;
import com.moseeker.common.util.query.Query;
import com.moseeker.common.util.query.ValueOp;
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



}
