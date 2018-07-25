package com.moseeker.application.infrastructure;


import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import java.util.ArrayList;
import java.util.List;
import org.jooq.Configuration;
import static org.jooq.impl.DSL.using;

/**
 * 由于历史遗留问题导致 UserHrAccountDao 使用的是struct对应的类。此类对于jooq并非天然支持，所以尝试使用jooq自带生成的数据访问类替换
 * Created by jack on 16/01/2018.
 */
public class UserWxUserJOOQDao extends com.moseeker.baseorm.db.userdb.tables.daos.UserWxUserDao {

    public UserWxUserJOOQDao(Configuration configuration) {
        super(configuration);
    }

    public List<com.moseeker.baseorm.db.userdb.tables.pojos.UserWxUser> getWxUserByUserIdList(List<Integer> userIDList){
        List<com.moseeker.baseorm.db.userdb.tables.pojos.UserWxUser> result = using(configuration()).selectFrom(UserWxUser.USER_WX_USER)
                .where(UserWxUser.USER_WX_USER.SYSUSER_ID.in(userIDList))
                .fetchInto(com.moseeker.baseorm.db.userdb.tables.pojos.UserWxUser .class);
        if(result != null ){
            return result;
        }
        return new ArrayList<>();
    }


}
