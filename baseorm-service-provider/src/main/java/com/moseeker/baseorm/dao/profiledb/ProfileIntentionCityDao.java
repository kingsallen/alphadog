package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileIntentionCity;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionCityDO;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
* @author xxx
* ProfileIntentionCityDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileIntentionCityDao extends JooqCrudImpl<ProfileIntentionCityDO, ProfileIntentionCityRecord> {

    public ProfileIntentionCityDao() {
        super(ProfileIntentionCity.PROFILE_INTENTION_CITY, ProfileIntentionCityDO.class);
    }

    public ProfileIntentionCityDao(TableImpl<ProfileIntentionCityRecord> table, Class<ProfileIntentionCityDO> profileIntentionCityDOClass) {
        super(table, profileIntentionCityDOClass);
    }

    public List<ProfileIntentionCityRecord> getIntentionCities(List<Integer> intentionIds) {
        List<ProfileIntentionCityRecord> records = new ArrayList<>();
        if(intentionIds != null && intentionIds.size() > 0) {
            SelectWhereStep<ProfileIntentionCityRecord> select = create.selectFrom(ProfileIntentionCity.PROFILE_INTENTION_CITY);
            SelectConditionStep<ProfileIntentionCityRecord> selectCondition = null;
            for(int i=0; i<intentionIds.size(); i++) {
                if(i == 0) {
                    selectCondition = select.where(ProfileIntentionCity.PROFILE_INTENTION_CITY.PROFILE_INTENTION_ID.equal(intentionIds.get(i)));
                } else {
                    selectCondition.or(ProfileIntentionCity.PROFILE_INTENTION_CITY.PROFILE_INTENTION_ID.equal(intentionIds.get(i)));
                }
            }
            records = selectCondition.fetch();
        }

        return records;
    }

    public void deleteByIntentionId(int intentionId) {
        create.deleteFrom(ProfileIntentionCity.PROFILE_INTENTION_CITY)
                .where(ProfileIntentionCity.PROFILE_INTENTION_CITY.PROFILE_INTENTION_ID.eq(intentionId))
                .execute();
    }
}
