package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.baseorm.db.userdb.tables.UserFavPosition;
import com.moseeker.baseorm.db.userdb.tables.records.UserFavPositionRecord;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.userdb.UserFavPositionDO;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserFavPositionDao extends JooqCrudImpl<UserFavPositionDO, UserFavPositionRecord> {

	public UserFavPositionDao() {
		super(UserFavPosition.USER_FAV_POSITION, UserFavPositionDO.class);
	}

	public UserFavPositionDao(TableImpl<UserFavPositionRecord> table, Class<UserFavPositionDO> userFavPositionDOClass) {
		super(table, userFavPositionDOClass);
	}

	/**
	 * 查询用户感兴趣职位的记录
	 * @param query
	 * @return
	 */
	public List<UserFavPositionDO> getUserFavPositions(Query query) {
		return getDatas(query);
	}

    /**
     * 判断用户是否我感兴趣
     * <p>
     *
     * @param favorite
     *            0:收藏, 1:取消收藏, 2:感兴趣
     */
    public int getUserFavPositionCountByUserIdAndPositionId(int userId, int positionId, byte favorite)
            throws Exception {

        Condition condition = UserFavPosition.USER_FAV_POSITION.SYSUSER_ID.equal(userId)
                .and(UserFavPosition.USER_FAV_POSITION.POSITION_ID.equal(positionId))
                .and(UserFavPosition.USER_FAV_POSITION.FAVORITE.equal(favorite));

        Record record = create.selectCount().from(UserFavPosition.USER_FAV_POSITION).where(condition).limit(1).fetchOne();
        Integer count = (Integer) record.getValue(0);
        return count;
    }

    public JobPositionRecord getUserFavPositiond(int positionId) {
        JobPositionRecord record = create.selectFrom(JobPosition.JOB_POSITION)
                .where(JobPosition.JOB_POSITION.ID.equal(positionId)).fetchOne();
        return record;
    }

}
