package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.userdb.tables.UserFavPosition;
import com.moseeker.db.userdb.tables.records.UserFavPositionRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.UserFavPositionDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
	public List<UserFavPositionDTO> getUserFavPositions(CommonQuery query) {
		List<UserFavPositionDTO> favPositions = new ArrayList<>();
		
		try {
			List<UserFavPositionRecord> records = getResources(query);
			if(records != null && records.size() > 0) {
				favPositions = BeanUtils.DBToStruct(UserFavPositionDTO.class, records);
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
