package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileCredentialsRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileCredentialsDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileCredentialsDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileCredentialsDao extends JooqCrudImpl<ProfileCredentialsDO, ProfileCredentialsRecord> {

    public ProfileCredentialsDao(TableImpl<ProfileCredentialsRecord> table, Class<ProfileCredentialsDO> profileCredentialsDOClass) {
        super(table, profileCredentialsDOClass);
    }
}
