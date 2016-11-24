package com.moseeker.baseorm.Thriftservice;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.position.JobPositionDao;
import com.moseeker.baseorm.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.PositionDao.Iface;
import com.moseeker.thrift.gen.position.struct.Position;

@Service
public class PositionDaoThriftService implements Iface {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	JobPositionDao positionDao;

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

	public JobPositionDao getPositionDao() {
		return positionDao;
	}

	public void setPositionDao(JobPositionDao positionDao) {
		this.positionDao = positionDao;
	}

}
