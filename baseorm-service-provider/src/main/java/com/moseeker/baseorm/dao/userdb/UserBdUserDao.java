package com.moseeker.baseorm.dao.userdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.userdb.tables.UserBdUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserBdUserRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.userdb.UserBdUserDO;

/**
* @author xxx
* UserBdUserDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserBdUserDao extends StructDaoImpl<UserBdUserDO, UserBdUserRecord, UserBdUser> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = UserBdUser.USER_BD_USER;
   }
}
