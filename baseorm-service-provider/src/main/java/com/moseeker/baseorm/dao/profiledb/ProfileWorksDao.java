package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileWorks;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileWorksRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileWorksDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileWorksDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileWorksDao extends JooqCrudImpl<ProfileWorksDO, ProfileWorksRecord> {

    public ProfileWorksDao() {
        super(ProfileWorks.PROFILE_WORKS, ProfileWorksDO.class);
    }

    public ProfileWorksDao(TableImpl<ProfileWorksRecord> table, Class<ProfileWorksDO> profileWorksDOClass) {
        super(table, profileWorksDOClass);
    }
}
