package com.moseeker.useraccounts.dao.impl;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseJooqDaoImpl;
import com.moseeker.db.userdb.tables.UserFavPosition;
import com.moseeker.db.userdb.tables.records.UserFavPositionRecord;
import com.moseeker.useraccounts.dao.UserFavoritePositionDao;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 我感兴趣 / 职位收藏
 * <p>
 *
 * Created by zzh on 16/5/25.
 */
@Repository
public class UserFavoritePositionDaoImpl extends BaseJooqDaoImpl<UserFavPositionRecord, UserFavPosition> implements UserFavoritePositionDao {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void initJOOQEntity() {
        this.tableLike = UserFavPosition.USER_FAV_POSITION;
    }

    /**
     *  判断用户是否我感兴趣
     *  <p>
     *
     *  @param favorite 0:收藏, 1:取消收藏, 2:感兴趣
     */
    @Override
    public int getUserFavPositionCountByUserIdAndPositionId(int userId, int positionId, byte favorite) throws Exception{

        Connection conn = null;
        Integer count = 0;

        try {
            initJOOQEntity();
            conn = DBConnHelper.DBConn.getConn();
            DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

            Condition condition = UserFavPosition.USER_FAV_POSITION.SYSUSER_ID.equal(userId).
                    and(UserFavPosition.USER_FAV_POSITION.POSITION_ID.equal(positionId)).
                    and(UserFavPosition.USER_FAV_POSITION.FAVORITE.equal(favorite));

            Record record = create.selectCount().from(tableLike).where(condition).fetchOne();
            count = (Integer)record.getValue(0);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);

        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }
}
