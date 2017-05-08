package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileIntention;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileIntentionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileIntentionDao extends JooqCrudImpl<ProfileIntentionDO, ProfileIntentionRecord> {

    public ProfileIntentionDao() {
        super(ProfileIntention.PROFILE_INTENTION, ProfileIntentionDO.class);
    }

    public ProfileIntentionDao(TableImpl<ProfileIntentionRecord> table, Class<ProfileIntentionDO> profileIntentionDOClass) {
        super(table, profileIntentionDOClass);
    }
}
