package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileIntentionPosition;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionPositionRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionPositionDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

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
}
