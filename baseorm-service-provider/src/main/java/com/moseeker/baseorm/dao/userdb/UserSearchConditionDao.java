package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserSearchCondition;
import com.moseeker.baseorm.db.userdb.tables.records.UserSearchConditionRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserSearchConditionDO;
import org.springframework.stereotype.Repository;

/**
* @author xxx
* UserSearchConditionDao 实现类 （groovy 生成）
* 2017-04-12
*/
@Repository
public class UserSearchConditionDao extends JooqCrudImpl<UserSearchConditionDO, UserSearchConditionRecord> {

    public UserSearchConditionDao() {
        super(UserSearchCondition.USER_SEARCH_CONDITION, UserSearchConditionDO.class);
    }

}
