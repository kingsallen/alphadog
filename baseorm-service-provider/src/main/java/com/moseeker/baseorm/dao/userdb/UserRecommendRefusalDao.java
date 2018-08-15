package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.records.UserRecommendRefusalRecord;
import com.moseeker.thrift.gen.dao.struct.userdb.UserRecommendRefusalDO;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.moseeker.baseorm.db.userdb.tables.UserRecommendRefusal.USER_RECOMMEND_REFUSAL;

@Repository
public class UserRecommendRefusalDao extends JooqCrudImpl<UserRecommendRefusalDO,UserRecommendRefusalRecord> {

    public UserRecommendRefusalDao(){
        super(USER_RECOMMEND_REFUSAL,UserRecommendRefusalDO.class);
    }
    public UserRecommendRefusalDao(TableImpl<UserRecommendRefusalRecord> table, Class<UserRecommendRefusalDO> sClass) {
        super(table, sClass);
    }

    public UserRecommendRefusalDO getLastestDataByUserId(int userId){
        return create.selectFrom(USER_RECOMMEND_REFUSAL)
                .where(USER_RECOMMEND_REFUSAL.USER_ID.eq(userId))
                .orderBy(USER_RECOMMEND_REFUSAL.REFUSE_TIME.desc())
                .fetchOneInto(UserRecommendRefusalDO.class);
    }

    public int insertData(UserRecommendRefusalRecord record){
        create.insertInto(USER_RECOMMEND_REFUSAL)
                .columns(record.fieldsRow())

    }
}
