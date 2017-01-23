package com.moseeker.baseorm.dao.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.userdb.tables.UserFavPosition;
import com.moseeker.db.userdb.tables.records.UserFavPositionRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.UserFavPositionPojo;

@Service
public class UserFavPositionDao extends BaseDaoImpl<UserFavPositionRecord, UserFavPosition> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserFavPosition.USER_FAV_POSITION;
	}

	/**
	 * 查询用户感兴趣职位的记录
	 * @param query
	 * @return
	 */
	public List<UserFavPositionPojo> getUserFavPositions(CommonQuery query) {
		List<UserFavPositionPojo> favPositions = new ArrayList<>();
		
		try {
			List<UserFavPositionRecord> records = getResources(query);
			if(records != null && records.size() > 0) {
				favPositions = BeanUtils.DBToStruct(UserFavPositionPojo.class, records);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		
		return favPositions;
	}

}
