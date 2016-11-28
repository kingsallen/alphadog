package com.moseeker.baseorm.Thriftservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.hr.HRThirdPartyPositionDao;
import com.moseeker.baseorm.dao.position.JobPositionDao;
import com.moseeker.baseorm.db.hrdb.tables.records.HrThirdPartyPositionRecord;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.PositionDao.Iface;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.position.struct.Position;

@Service
public class PositionDaoThriftService implements Iface {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	JobPositionDao positionDao;
	
	@Autowired
	HRThirdPartyPositionDao thirdpartyPositionDao;

	//利用通用方法获取职位数据
	@Override
	public Position getPosition(CommonQuery query) throws TException {
		Position position = new Position();
		try {
			JobPositionRecord record = positionDao.getResource(query);
			if(record != null) {
				record.into(position);
				position.setCompany_id(record.getCompanyId().intValue());
				position.setAccountabilities(record.getAccountabilities());
				return position;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return position;
	}
	
	@Override
	public Position getPositionWithCityCode(CommonQuery query) throws TException {
		// TODO Auto-generated method stub
		return positionDao.getPositionWithCityCode(query);
	}

	public JobPositionDao getPositionDao() {
		return positionDao;
	}

	public void setPositionDao(JobPositionDao positionDao) {
		this.positionDao = positionDao;
	}

	@Override
	public Response getPositionThirdPartyPositions(CommonQuery query) throws TException {
		List<Map<String, Object>> data = new ArrayList<>();
		try {
			List<HrThirdPartyPositionRecord> records = thirdpartyPositionDao.getResources(query);
			if(records != null && records.size() > 0) {
				records.forEach(record -> {
					data.add(record.intoMap());
				});
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return ResponseUtils.success(data);
	}

	@Override
	public int updatePosition(Position position) throws TException {
		int count = 0;
		if(position.getId() > 0) {
			JobPositionRecord record = (JobPositionRecord)BeanUtils.structToDB(position, JobPositionRecord.class);
			try {
				count = positionDao.putResource(record);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} finally {
				//do nothing
			}
		}
		return count;
	}

	@Override
	public ThirdPartyPositionData getThirdPartyPosition(int positionId, int channel) throws TException {
		return thirdpartyPositionDao.getThirdPartyPosition(positionId, channel);
	}

	@Override
	public int upsertThirdPartyPositions(ThirdPartyPositionData position) throws TException {
		
		return thirdpartyPositionDao.upsertThirdPartyPosition(position);
	}
}
