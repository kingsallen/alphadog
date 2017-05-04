package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileIntentionIndustryRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileIntentionIndustryDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileIntentionIndustryDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileIntentionIndustryDao extends JooqCrudImpl<ProfileIntentionIndustryDO, ProfileIntentionIndustryRecord> {


    public ProfileIntentionIndustryDao(TableImpl<ProfileIntentionIndustryRecord> table, Class<ProfileIntentionIndustryDO> profileIntentionIndustryDOClass) {
        super(table, profileIntentionIndustryDOClass);
    }
}
