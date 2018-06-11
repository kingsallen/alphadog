package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserWxUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserWxUserRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.userdb.UserWxUserDO;
import java.sql.SQLException;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import static com.moseeker.baseorm.db.userdb.tables.UserWxUser.USER_WX_USER;
/**
* @author xxx
* UserWxUserDao 实现类 （groovy 生成）
* 2017-03-21
*/
@Repository
public class UserWxUserDao extends JooqCrudImpl<UserWxUserDO, UserWxUserRecord> {

    public UserWxUserDao() {
        super(UserWxUser.USER_WX_USER, UserWxUserDO.class);
    }

    public UserWxUserDao(TableImpl<UserWxUserRecord> table, Class<UserWxUserDO> userWxUserDOClass) {
        super(table, userWxUserDOClass);
    }

    public UserWxUserRecord getWXUserByUserId(int userId) throws SQLException {
        UserWxUserRecord wxuser = null;
        if(userId > 0) {
            wxuser = create.selectFrom(UserWxUser.USER_WX_USER).where(UserWxUser.USER_WX_USER.SYSUSER_ID.equal(userId)).limit(1).fetchOne();
        }
        return wxuser;
    }

    public UserWxUserDO getWXUserById(int id) {
        Query query=new Query.QueryBuilder()
                .where(UserWxUser.USER_WX_USER.ID.getName(),id)
                .buildQuery();
        return getData(query);
    }

    /**
     * 合并两个C端账号时，把废弃的账号的微信用户的sysuser_id全指向有效的账号
     * @param vaildUserId   有效的C端账号ID
     * @param beDelUserId   废弃的C端账号ID
     * @return
     */
    public int combineWxUser(int vaildUserId, int beDelUserId){
        return create.update(USER_WX_USER)
                .set(USER_WX_USER.SYSUSER_ID,vaildUserId)
                .where(USER_WX_USER.SYSUSER_ID.eq(beDelUserId))
                .execute();
    }
}
