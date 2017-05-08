package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileLanguage;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileLanguageRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileLanguageDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileLanguageDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileLanguageDao extends JooqCrudImpl<ProfileLanguageDO, ProfileLanguageRecord> {

    public ProfileLanguageDao() {
        super(ProfileLanguage.PROFILE_LANGUAGE, ProfileLanguageDO.class);
    }

    public ProfileLanguageDao(TableImpl<ProfileLanguageRecord> table, Class<ProfileLanguageDO> profileLanguageDOClass) {
        super(table, profileLanguageDOClass);
    }
}
