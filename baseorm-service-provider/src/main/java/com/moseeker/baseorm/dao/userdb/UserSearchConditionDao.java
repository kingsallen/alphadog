package com.moseeker.baseorm.dao.userdb;

import org.springframework.stereotype.Repository;

import com.moseeker.baseorm.db.userdb.tables.UserSearchCondition;
import com.moseeker.baseorm.db.userdb.tables.records.UserSearchConditionRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.thrift.gen.dao.struct.userdb.UserSearchConditionDO;

/**
* @author xxx
* UserSearchConditionDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class UserSearchConditionDao extends StructDaoImpl<UserSearchConditionDO, UserSearchConditionRecord, UserSearchCondition> {


   @Override
   protected void initJOOQEntity() {
        this.tableLike = UserSearchCondition.USER_SEARCH_CONDITION;
   }
}
