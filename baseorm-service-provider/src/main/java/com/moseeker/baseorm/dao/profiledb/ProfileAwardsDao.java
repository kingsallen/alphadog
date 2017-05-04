package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileAwardsRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileAwardsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileAwardsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileAwardsDao extends JooqCrudImpl<ProfileAwardsDO, ProfileAwardsRecord> {


    public ProfileAwardsDao(TableImpl<ProfileAwardsRecord> table, Class<ProfileAwardsDO> profileAwardsDOClass) {
        super(table, profileAwardsDOClass);
    }
}
