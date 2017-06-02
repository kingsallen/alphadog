package com.moseeker.baseorm.dao.userdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.userdb.tables.UserViewedPosition;
import com.moseeker.baseorm.db.userdb.tables.records.UserViewedPositionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.userdb.UserViewedPositionDO;

/**
* @author xxx
* UserViewedPositionDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class UserViewedPositionDao extends StructDaoImpl<UserViewedPositionDO, UserViewedPositionRecord, UserViewedPosition> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = UserViewedPosition.USER_VIEWED_POSITION;
   }
}
