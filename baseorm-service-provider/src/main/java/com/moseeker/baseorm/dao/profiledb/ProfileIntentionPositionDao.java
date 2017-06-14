package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileIntentionPosition;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionPositionDO;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
* @author xxx
* ProfileIntentionPositionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileIntentionPositionDao extends JooqCrudImpl<ProfileIntentionPositionDO, ProfileIntentionPositionRecord> {

    public ProfileIntentionPositionDao() {
        super(ProfileIntentionPosition.PROFILE_INTENTION_POSITION, ProfileIntentionPositionDO.class);
    }

    public ProfileIntentionPositionDao(TableImpl<ProfileIntentionPositionRecord> table, Class<ProfileIntentionPositionDO> profileIntentionPositionDOClass) {
        super(table, profileIntentionPositionDOClass);
    }

    public List<ProfileIntentionPositionRecord> getIntentionPositions(List<Integer> cityCodes) {

    List<ProfileIntentionPositionRecord> records = new ArrayList<>();
        SelectWhereStep<ProfileIntentionPositionRecord> select = create.selectFrom(ProfileIntentionPosition.PROFILE_INTENTION_POSITION);
        SelectConditionStep<ProfileIntentionPositionRecord> selectCondition = null;
        if(cityCodes != null && cityCodes.size() > 0) {
            for(int i=0; i<cityCodes.size(); i++) {
                if(i == 0) {
                    selectCondition = select.where(ProfileIntentionPosition.PROFILE_INTENTION_POSITION.PROFILE_INTENTION_ID.equal((int)(cityCodes.get(i))));
                } else {
                    selectCondition.or(ProfileIntentionPosition.PROFILE_INTENTION_POSITION.PROFILE_INTENTION_ID.equal((int)(cityCodes.get(i))));
                }
            }
        }
        records = selectCondition.fetch();

    return records;
    }

    public void deleteByIntentionId(int intentionId) {
        create.deleteFrom(ProfileIntentionPosition.PROFILE_INTENTION_POSITION)
                .where(ProfileIntentionPosition.PROFILE_INTENTION_POSITION.PROFILE_INTENTION_ID.eq(intentionId))
                .execute();
    }
}
