package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileOther;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileOtherRecord;
import com.moseeker.thrift.gen.dao.struct.ProfileOtherDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by jack on 15/03/2017.
 */
@Repository
public class ProfileOtherDao extends JooqCrudImpl<ProfileOtherDO, ProfileOtherRecord> {

    public ProfileOtherDao() {
        super(ProfileOther.PROFILE_OTHER, ProfileOtherDO.class);
    }

    public ProfileOtherDao(TableImpl<ProfileOtherRecord> table, Class<ProfileOtherDO> profileOtherDOClass) {
        super(table, profileOtherDOClass);
    }
}
