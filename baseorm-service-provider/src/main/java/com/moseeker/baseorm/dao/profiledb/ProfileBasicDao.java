package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileBasicRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileBasicDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileBasicDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileBasicDao extends JooqCrudImpl<ProfileBasicDO, ProfileBasicRecord> {


    public ProfileBasicDao(TableImpl<ProfileBasicRecord> table, Class<ProfileBasicDO> profileBasicDOClass) {
        super(table, profileBasicDOClass);
    }
}
