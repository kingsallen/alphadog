package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileIntentionIndustry;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionIndustryRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionIndustryDO;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
* @author xxx
* ProfileIntentionIndustryDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileIntentionIndustryDao extends JooqCrudImpl<ProfileIntentionIndustryDO, ProfileIntentionIndustryRecord> {

    public ProfileIntentionIndustryDao() {
        super(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY, ProfileIntentionIndustryDO.class);
    }

    public ProfileIntentionIndustryDao(TableImpl<ProfileIntentionIndustryRecord> table, Class<ProfileIntentionIndustryDO> profileIntentionIndustryDOClass) {
        super(table, profileIntentionIndustryDOClass);
    }

    public List<ProfileIntentionIndustryRecord> getIntentionIndustries(List<Integer> intentionIds) {
        List<ProfileIntentionIndustryRecord> records = new ArrayList<>();
        SelectWhereStep<ProfileIntentionIndustryRecord> select = create.selectFrom(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY);
        SelectConditionStep<ProfileIntentionIndustryRecord> selectCondition = null;
        if(intentionIds != null && intentionIds.size() > 0) {
            for(int i=0; i<intentionIds.size(); i++) {
                if(i == 0) {
                    selectCondition = select.where(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY.PROFILE_INTENTION_ID.equal((int)(intentionIds.get(i))));
                } else {
                    selectCondition.or(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY.PROFILE_INTENTION_ID.equal((int)(intentionIds.get(i))));
                }
            }
        }
        records = selectCondition.fetch();
        return records;
    }

    public void deleteByIntentionId(int intentionId) {
        create.deleteFrom(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY)
                .where(ProfileIntentionIndustry.PROFILE_INTENTION_INDUSTRY.PROFILE_INTENTION_ID.eq(intentionId))
                .execute();
    }
}
