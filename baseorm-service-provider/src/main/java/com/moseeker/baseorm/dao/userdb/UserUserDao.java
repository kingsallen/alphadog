package com.moseeker.baseorm.dao.userdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserDO;

/**
* @author xxx
* UserUserDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserUserDao extends StructDaoImpl<UserUserDO, UserUserRecord, UserUser> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = UserUser.USER_USER;
   }
}
