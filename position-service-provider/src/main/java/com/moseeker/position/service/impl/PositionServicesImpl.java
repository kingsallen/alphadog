package com.moseeker.position.service.impl;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.ConstantErrorCodeMessage;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.position.dao.JobPositionDao;
import com.moseeker.position.dao.PositionDao;
import com.moseeker.position.dao.UserDao;
import com.moseeker.position.pojo.RecommendedPositonPojo;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.position.service.PositionServices.Iface;
import com.moseeker.thrift.gen.position.struct.Position;

@Service
public class PositionServicesImpl extends JOOQBaseServiceImpl<Position, JobPositionRecord> implements Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected PositionDao dao;
    
    @Autowired
    protected UserDao userDao;
    
    @Autowired
    protected JobPositionDao jobPositionDao;

    @Override
    protected void initDao() {
        super.dao = this.dao;
    }

    @Override
    protected Position DBToStruct(JobPositionRecord r) {
        return (Position) BeanUtils.DBToStruct(Position.class, r);
    }

    @Override
    protected JobPositionRecord structToDB(Position p) {
        return (JobPositionRecord) BeanUtils.structToDB(p, JobPositionRecord.class);
    }

    @Override
    public Response getRecommendedPositions(int pid) {

        List<RecommendedPositonPojo> recommendPositons = this.dao.getRecommendedPositions(pid);

        return ResponseUtils.successWithoutStringify(JSON.toJSONString(recommendPositons, new ValueFilter() {
            @Override
            public Object process(Object object, String name, Object value) {
                if (value == null) {
                    return "";
                }
                return value;
            }
        }));

    }

	@Override
	public Response verifyCustomize(int userId, int positionId) throws TException {
		UserUserRecord userRecord = userDao.getUserById(userId);
		JobPositionRecord positionRecord = jobPositionDao.getPositionById(positionId);
		if(userRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_USER_NOTEXIST);
		}
		if(positionRecord == null) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_POSITION_NOTEXIST);
		}
		if(positionRecord.getAppCvConfigId() != null && positionRecord.getAppCvConfigId().intValue() > 0) {
			return ResponseUtils.success(true);
		} else {
			return ResponseUtils.success(false);
		}
	}

	public PositionDao getDao() {
		return dao;
	}

	public void setDao(PositionDao dao) {
		this.dao = dao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public JobPositionDao getJobPositionDao() {
		return jobPositionDao;
	}

	public void setJobPositionDao(JobPositionDao jobPositionDao) {
		this.jobPositionDao = jobPositionDao;
	}
}