package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionCityRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionCityDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileIntentionCityDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileIntentionCityDao extends JooqCrudImpl<ProfileIntentionCityDO, ProfileIntentionCityRecord> {


    public ProfileIntentionCityDao(TableImpl<ProfileIntentionCityRecord> table, Class<ProfileIntentionCityDO> profileIntentionCityDOClass) {
        super(table, profileIntentionCityDOClass);
    }
}
