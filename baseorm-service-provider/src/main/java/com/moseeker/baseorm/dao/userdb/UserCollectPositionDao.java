package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.userdb.tables.UserCollectPosition;
import com.moseeker.baseorm.db.userdb.tables.records.UserCollectPositionRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserCollectPositionDO;

/**
* @author xxx
* UserCollectPositionDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class UserCollectPositionDao extends JooqCrudImpl<UserCollectPositionDO, UserCollectPositionRecord> {

    public UserCollectPositionDao() {
        super(UserCollectPosition.USER_COLLECT_POSITION, UserCollectPositionDO.class);
    }

}
