package com.moseeker.baseorm.dao.position;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.springframework.stereotype.Service;

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
		this.tableLike=JobPosition.JOB_POSITION;
		
	}

	public Position getPositionWithCityCode(CommonQuery query) {
		Position position = new Position();
		try (Connection conn = DBConnHelper.DBConn.getConn();
				DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);) {
			
			JobPositionRecord record = this.getResource(query);
			if(record != null) {
				position = record.into(position);
				List<Integer> cityCodes = new ArrayList<>();
				Result<JobPositionCityRecord> cities = create.selectFrom(JobPositionCity.JOB_POSITION_CITY).where(JobPositionCity.JOB_POSITION_CITY.PID.equal(record.getId())).fetch();
				if(cities != null && cities.size() > 0) {
					cities.forEach(city -> {
						cityCodes.add(city.getCode());
					});
				}
				position.setCompany_id(record.getCompanyId().intValue());
				position.setCities(cityCodes);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		
		return position;
	}

}
