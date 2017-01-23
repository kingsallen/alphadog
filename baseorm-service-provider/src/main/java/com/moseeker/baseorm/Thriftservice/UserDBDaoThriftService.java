package com.moseeker.baseorm.Thriftservice;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.userdb.UserDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserFavPositionDao;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.UserDBDao.Iface;
import com.moseeker.thrift.gen.dao.struct.UserEmployeePojo;
import com.moseeker.thrift.gen.dao.struct.UserFavPositionPojo;
import com.moseeker.thrift.gen.useraccounts.struct.User;

@Service
public class UserDBDaoThriftService implements Iface {
	
	Logger logger = LoggerFactory.getLogger(UserDBDaoThriftService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserFavPositionDao favPositionDao;
	
	@Autowired
	private UserEmployeeDao employeeDao;

	@Override
	public User getUser(CommonQuery query) throws TException {
		User user = new User();
		try {
			UserUserRecord record = userDao.getResource(query);
			if(record != null) {
				user = record.into(User.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		
		return user;
	}

	@Override
	public User saveUser(User user) throws TException {
		if(user.getPassword() == null) {
			user.setPassword("");
		}
		return userDao.saveUser(user);
	}

	@Override
	public List<UserFavPositionPojo> getUserFavPositions(CommonQuery query) throws TException {
		return favPositionDao.getUserFavPositions(query);
	}

	@Override
	public UserEmployeePojo getEmployee(CommonQuery query) throws TException {
		return employeeDao.getEmployee(query);
	}
}
