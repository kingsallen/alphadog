package com.moseeker.baseorm.Thriftservice;

import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.user.UserDao;
import com.moseeker.baseorm.service.UserEmployeeService;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.UserDao.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.User;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;

@Service
public class UserDaoThriftService implements Iface {
	
	Logger logger = LoggerFactory.getLogger(UserDaoThriftService.class);
	
	@Autowired
	UserDao userDao;
	@Autowired
	private UserEmployeeService userEmployeeService; 

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
	/*
	 * @auth zzt
	 * 获取推荐者列表
	 * (non-Javadoc)
	 * @see com.moseeker.thrift.gen.dao.service.UserDao.Iface#getUserEmployee(int, java.util.List)
	 */
	@Override
	public Response getUserEmployee(int companyId, List<Integer> weChatIds) throws TException {
		// TODO Auto-generated method stub
		return userEmployeeService.getUserEmployeeByWeChats(companyId, weChatIds);
	}

	@Override
	public Response postUserEmployeePoints(List<UserEmployeePointStruct> records) throws TException {
		// TODO Auto-generated method stub
		return userEmployeeService.postUserEmployeePointsRecords(records);
	}

	@Override
	public Response getPointSum(List<Long> record) throws TException {
		// TODO Auto-generated method stub
		return userEmployeeService.getSumPoint(record);
	}

	@Override
	public Response putUserEmployees(List<UserEmployeeStruct> records) throws TException {
		// TODO Auto-generated method stub
		return userEmployeeService.putUserEmployees(records);
	}
}
