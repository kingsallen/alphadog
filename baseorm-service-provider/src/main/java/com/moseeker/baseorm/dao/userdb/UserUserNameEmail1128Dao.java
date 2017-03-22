package com.moseeker.baseorm.dao.userdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.userdb.tables.UserUserNameEmail1128;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserNameEmail1128Record;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserNameEmail1128DO;

/**
* @author xxx
* UserUserNameEmail1128Dao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserUserNameEmail1128Dao extends StructDaoImpl<UserUserNameEmail1128DO, UserUserNameEmail1128Record, UserUserNameEmail1128> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = UserUserNameEmail1128.USER_USER_NAME_EMAIL1128;
   }
}
