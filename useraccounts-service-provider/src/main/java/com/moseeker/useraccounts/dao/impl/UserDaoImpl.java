package com.moseeker.useraccounts.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.useraccounts.dao.UserDao;
import com.moseeker.useraccounts.pojo.User;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户数据接口
 *
 *
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<UserUserRecord, UserUser> implements UserDao {

    @Override
    protected void initJOOQEntity() {
        this.tableLike = UserUser.USER_USER;
    }

    @Override
    public void combineAccount(List<String> tables, String column, int orig, int dest) throws Exception {
        Connection conn = null;
        try {
            conn = DBConnHelper.DBConn.getConn();
            for(String table : tables) {
                PreparedStatement pstmt = conn.prepareStatement(COMBINE_ACCOUNT.replace("{table}", table).replace("{column}", column));
                pstmt.setInt(0, orig);
                pstmt.setInt(1, dest);
                pstmt.executeUpdate();
                pstmt.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        } finally {
            if(conn != null && !conn.isClosed()) {
                conn.isClosed();
            }
        }
        
    }
    /**
     * 获取用户数据
     * <p>
     *
     * @param userId 用户ID
     *
     * */
    @Override
    public User getUserById(long userId) throws Exception{
        Connection conn = null;
        Condition condition = null;
        User user = null;
        try{
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
            condition = UserUser.USER_USER.ID.equal(UInteger.valueOf(userId));

            user = create.select().from(UserUser.USER_USER).where(condition).fetchOne().into(User.class);

        }catch (Exception e){
            logger.error(e.getMessage(), e);
        }finally {
            if(conn != null && !conn.isClosed()) {
                conn.isClosed();
            }
        }
        return user;
    }

    private static final String COMBINE_ACCOUNT = "update {table} set {column} = ? where {column} = ?";
}
