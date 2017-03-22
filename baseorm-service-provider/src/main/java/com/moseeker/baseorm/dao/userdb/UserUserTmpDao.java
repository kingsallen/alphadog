package com.moseeker.baseorm.dao.userdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.userdb.tables.UserUserTmp;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserTmpRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.userdb.UserUserTmpDO;

/**
* @author xxx
* UserUserTmpDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserUserTmpDao extends StructDaoImpl<UserUserTmpDO, UserUserTmpRecord, UserUserTmp> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = UserUserTmp.USER_USER_TMP;
   }
}
