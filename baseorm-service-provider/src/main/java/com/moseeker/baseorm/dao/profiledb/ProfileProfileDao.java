package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileProfile;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProfileRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProfileDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileProfileDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileProfileDao extends JooqCrudImpl<ProfileProfileDO, ProfileProfileRecord> {

    public ProfileProfileDao() {
        super(ProfileProfile.PROFILE_PROFILE, ProfileProfileDO.class);
    }

    public ProfileProfileDao(TableImpl<ProfileProfileRecord> table, Class<ProfileProfileDO> profileProfileDOClass) {
        super(table, profileProfileDOClass);
    }
}
