package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.ProfileImport;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileImportRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileImportDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileImportDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileImportDao extends JooqCrudImpl<ProfileImportDO, ProfileImportRecord> {

    public ProfileImportDao() {
        super(ProfileImport.PROFILE_IMPORT, ProfileImportDO.class);
    }

    public ProfileImportDao(TableImpl<ProfileImportRecord> table, Class<ProfileImportDO> profileImportDOClass) {
        super(table, profileImportDOClass);
    }
}
