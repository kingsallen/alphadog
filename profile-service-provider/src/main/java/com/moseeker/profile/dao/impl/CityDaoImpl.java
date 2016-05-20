package com.moseeker.profile.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.SelectWhereStep;
import org.jooq.types.UInteger;
import org.springframework.stereotype.Repository;

import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.db.dictdb.tables.DictCity;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.profile.dao.CityDao;

@Repository
public class CityDaoImpl extends
		BaseDaoImpl<DictCityRecord, DictCity> implements
		CityDao {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = DictCity.DICT_CITY;
	}

	@Override
	public List<DictCityRecord> getCitiesByCodes(List<Integer> cityCodes) {
		
		List<DictCityRecord> records = new ArrayList<>();
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			SelectWhereStep<DictCityRecord> select = create.selectFrom(DictCity.DICT_CITY);
			SelectConditionStep<DictCityRecord> selectCondition = null;
			if(cityCodes != null && cityCodes.size() > 0) {
				for(int i=0; i<cityCodes.size(); i++) {
					if(i == 0) {
						selectCondition = select.where(DictCity.DICT_CITY.CODE.equal(UInteger.valueOf(cityCodes.get(i))));
					} else {
						selectCondition.or(DictCity.DICT_CITY.CODE.equal(UInteger.valueOf(cityCodes.get(i))));
					}
				}
			}
			records = selectCondition.fetch();
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
		
		return records;
	}

	@Override
	public DictCityRecord getCityByCode(int city_code) {
		DictCityRecord record = null;
		Connection conn = null;
		try {
			if(city_code > 0) {
				conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
				Result<DictCityRecord> result = create.selectFrom(DictCity.DICT_CITY)
						.where(DictCity.DICT_CITY.CODE.equal(UInteger.valueOf(city_code)))
						.limit(1).fetch();
				if(result != null && result.size() > 0) {
					record = result.get(0);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
		return record;
	}
}
