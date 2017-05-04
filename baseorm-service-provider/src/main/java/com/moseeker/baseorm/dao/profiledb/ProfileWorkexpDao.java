package com.moseeker.baseorm.dao.profiledb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.profiledb.tables.records.ProfileWorkexpRecord;
import com.moseeker.thrift.gen.dao.struct.profiledb.ProfileWorkexpDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* ProfileWorkexpDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class ProfileWorkexpDao extends JooqCrudImpl<ProfileWorkexpDO, ProfileWorkexpRecord> {


    public ProfileWorkexpDao(TableImpl<ProfileWorkexpRecord> table, Class<ProfileWorkexpDO> profileWorkexpDOClass) {
        super(table, profileWorkexpDOClass);
    }
}
