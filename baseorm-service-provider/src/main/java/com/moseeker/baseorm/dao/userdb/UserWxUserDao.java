package com.moseeker.baseorm.dao.userdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;

/**
* @author xxx
* UserWxUserDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserWxUserDao extends StructDaoImpl<UserWxUserDO, UserWxUserRecord, UserWxUser> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = UserWxUser.USER_WX_USER;
   }
}
