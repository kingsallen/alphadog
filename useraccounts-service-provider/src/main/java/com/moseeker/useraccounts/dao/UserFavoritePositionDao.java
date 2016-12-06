package com.moseeker.useraccounts.dao;

import com.moseeker.common.providerutils.daoutils.BaseDao;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.db.userdb.tables.records.UserFavPositionRecord;

/**
 * Created by zzh on 16/5/25.
 */
public interface UserFavoritePositionDao extends BaseDao<UserFavPositionRecord> {

    public int getUserFavPositionCountByUserIdAndPositionId(int userId, int positionId, byte favorite) throws Exception;

	public JobPositionRecord getUserFavPositiond(int positionId);

}
