package moseeker.baseorm.dao.crud;

import com.moseeker.baseorm.crud.CommonCondition;
import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.crud.QueryCreator;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangdi on 2017/3/20.
 */
public class UserDao extends JooqCrudImpl<User, UserUserRecord> {

    public UserDao() {
        super(UserUser.USER_USER);
    }

    public User getUserById(int id) throws SQLException {
        return getData(QueryCreator.select().where(CommonCondition.equal("id", id)).getCommonQuery(), User.class);
    }

    public Map<String,Object> getUserProfileById(int id) throws SQLException {
        Map<String,Object> resultMap = new HashMap();
        resultMap.put("user",getMaps(QueryCreator.select().where(CommonCondition.equal("id", id)).getCommonQuery()));
        resultMap.put("profile",new ProfileDao().getMaps(QueryCreator.select().where(CommonCondition.equal("user_id", id)).getCommonQuery()));
        return resultMap;
    }

    public boolean updateUserAndProfile(int id,int disable) throws SQLException {
        Connection connection = newConnection();
        DSL.using(connection).transaction(configuration -> {
            updateData(connection,new User());
            new ProfileDao().delete(connection,QueryCreator.where(CommonCondition.equal("user_id", id)).getCommonQuery());
        });
        return true;
    }

    public boolean deleteUserAndProfile(int id) throws SQLException {
        Connection connection = newConnection();
        DSL.using(connection).transaction(configuration -> {
            delete(connection,QueryCreator.where(CommonCondition.equal("id", id)).getCommonQuery());
            new ProfileDao().delete(connection,QueryCreator.where(CommonCondition.equal("user_id", id)).getCommonQuery());
        });
        return true;
    }

}
