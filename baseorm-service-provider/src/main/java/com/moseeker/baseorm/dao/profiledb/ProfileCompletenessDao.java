package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileCompleteness;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileCompletenessRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileCompletenessDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileCompletenessDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileCompletenessDao extends JooqCrudImpl<ProfileCompletenessDO, ProfileCompletenessRecord> {

    public ProfileCompletenessDao() {
        super(ProfileCompleteness.PROFILE_COMPLETENESS, ProfileCompletenessDO.class);
    }

    public ProfileCompletenessDao(TableImpl<ProfileCompletenessRecord> table, Class<ProfileCompletenessDO> profileCompletenessDOClass) {
        super(table, profileCompletenessDOClass);
    }
}
