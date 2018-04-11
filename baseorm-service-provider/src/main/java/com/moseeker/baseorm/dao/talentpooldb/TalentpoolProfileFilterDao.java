package com.moseeker.baseorm.dao.talentpooldb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.talentpooldb.tables.TalentpoolProfileFilter;
import com.moseeker.baseorm.db.talentpooldb.tables.records.TalentpoolProfileFilterRecord;
import java.util.List;
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
    public TalentpoolProfileFilterDao(TableImpl<TalentpoolProfileFilterRecord> table, Class<com.moseeker.baseorm.db.talentpooldb.tables.pojos.TalentpoolProfileFilter> talentpoolCompanyTagClass) {
        super(table, talentpoolCompanyTagClass);
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

}
