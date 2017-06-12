package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.userdb.tables.UserViewedPosition;
import com.moseeker.baseorm.db.userdb.tables.records.UserViewedPositionRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserViewedPositionDO;

/**
* @author xxx
* UserViewedPositionDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class UserViewedPositionDao extends JooqCrudImpl<UserViewedPositionDO, UserViewedPositionRecord> {

    public UserViewedPositionDao() {
        super(UserViewedPosition.USER_VIEWED_POSITION, UserViewedPositionDO.class);
    }
}
