package com.moseeker.baseorm.Thriftservice;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.user.UserDao;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.service.UserDao.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.User;

@Service
public class UserDaoThriftService implements Iface {
	
	Logger logger = LoggerFactory.getLogger(UserDaoThriftService.class);
	
	@Autowired
	UserDao userDao;

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

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User saveUser(User user) throws TException {
		if(user.getPassword() == null) {
			user.setPassword("");
		}
		return userDao.saveUser(user);
	}
}
