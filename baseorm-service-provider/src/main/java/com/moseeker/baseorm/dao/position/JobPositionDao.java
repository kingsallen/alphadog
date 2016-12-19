package com.moseeker.baseorm.dao.position;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.dictdb.tables.DictCity;
import com.moseeker.baseorm.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.JobPositionCity;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionCityRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.providerutils.daoutils.BaseDaoImpl;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.position.struct.Position;

@Service
public class JobPositionDao extends BaseDaoImpl<JobPositionRecord, JobPosition> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = JobPosition.JOB_POSITION;

	}

	public Position getPositionWithCityCode(CommonQuery query) {
		
		logger.info("JobPositionDao getPositionWithCityCode");
		
		Position position = new Position();
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);) {

			JobPositionRecord record = this.getResource(query);
			if (record != null) {
				position = record.into(position);
				Map<Integer, String> citiesParam = new HashMap<Integer, String>();
				List<Integer> cityCodes = new ArrayList<>();
				Result<JobPositionCityRecord> cities = create.selectFrom(JobPositionCity.JOB_POSITION_CITY)
						.where(JobPositionCity.JOB_POSITION_CITY.PID.equal(record.getId())).fetch();
				if (cities != null && cities.size() > 0) {
					cities.forEach(city -> {
						logger.info("code:{}", city.getCode());
						if(city.getCode() != null) {
							citiesParam.put(city.getCode(), null);
							cityCodes.add(city.getCode());
						}
					});
					logger.info("cityCodes:{}", cityCodes);
					Result<DictCityRecord> dictDicties = create.selectFrom(DictCity.DICT_CITY).where(DictCity.DICT_CITY.CODE.in(cityCodes)).fetch();
					if(dictDicties != null && dictDicties.size() > 0) {
						dictDicties.forEach(dictCity -> {
							citiesParam.entrySet().forEach(entry -> {
								if(entry.getKey().intValue() == dictCity.getCode().intValue()) {
									logger.info("cityName:{}", dictCity.getName());
									entry.setValue(dictCity.getName());
								}
							});
						});
					}
				}
				
				position.setCompany_id(record.getCompanyId().intValue());
				position.setCities(citiesParam);
				citiesParam.forEach((cityCode, cityName) -> {
					logger.info("cityCode:{}, cityName:{}", cityCode, cityName);
				});
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}

		return position;
	}
}
