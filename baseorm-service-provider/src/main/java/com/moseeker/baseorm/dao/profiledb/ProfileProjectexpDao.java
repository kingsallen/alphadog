package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileProjectexpRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileProjectexpDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileProjectexpDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileProjectexpDao extends JooqCrudImpl<ProfileProjectexpDO, ProfileProjectexpRecord> {


    public ProfileProjectexpDao(TableImpl<ProfileProjectexpRecord> table, Class<ProfileProjectexpDO> profileProjectexpDOClass) {
        super(table, profileProjectexpDOClass);
    }
}
