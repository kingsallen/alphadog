package com.moseeker.baseorm.dao.userdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.userdb.tables.UserIntention;
import com.moseeker.baseorm.db.userdb.tables.records.UserIntentionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.userdb.UserIntentionDO;

/**
* @author xxx
* UserIntentionDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserIntentionDao extends StructDaoImpl<UserIntentionDO, UserIntentionRecord, UserIntention> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = UserIntention.USER_INTENTION;
   }
}
