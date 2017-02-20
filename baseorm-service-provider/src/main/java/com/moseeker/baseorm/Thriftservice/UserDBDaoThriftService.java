package com.moseeker.baseorm.Thriftservice;

import java.util.List;

<<<<<<< HEAD
import com.moseeker.baseorm.dao.userdb.UserHRAccountDao;
import com.moseeker.thrift.gen.dao.struct.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.UserFavPositionDO;
import com.moseeker.thrift.gen.dao.struct.UserHrAccountDO;
import com.moseeker.thrift.gen.dao.struct.UserUserDO;
=======
import com.moseeker.thrift.gen.dao.struct.*;
>>>>>>> 4a0393a5704386fc0dd6d1bb63eeee37286fa4d0
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.dao.userdb.UserDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserFavPositionDao;
import com.moseeker.baseorm.service.UserEmployeeService;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.UserDBDao.Iface;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;

@Service
public class UserDBDaoThriftService implements Iface {

	private Logger logger = LoggerFactory.getLogger(UserDBDaoThriftService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserFavPositionDao favPositionDao;
	
	@Autowired
	private UserEmployeeDao employeeDao;
	
	@Autowired
	private UserEmployeeService userEmployeeService;

	@Autowired
	private UserHRAccountDao userHRAccountDao;

	@Override
	public UserUserDO getUser(CommonQuery query) throws TException {
		UserUserDO user = new UserUserDO();
		try {
			UserUserRecord record = userDao.getResource(query);
			if(record != null) {
				user = record.into(UserUserDO.class);
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
	public List<UserUserDO> listUser(CommonQuery query) throws TException {
		return userDao.listResources(query);
	}

	@Override
	public UserUserDO saveUser(UserUserDO user) throws TException {
		if(user.getPassword() == null) {
			user.setPassword("");
		}
		return userDao.saveResource(user);
	}

	@Override
	public List<UserHrAccountDO> listHRFromCompany(int comanyId) throws TException {
		return userHRAccountDao.listHRFromCompany(comanyId);
	}

	@Override
	public List<UserFavPositionDO> getUserFavPositions(CommonQuery query) throws TException {
		return favPositionDao.getUserFavPositions(query);
	}

	@Override
	public UserEmployeeDO getEmployee(CommonQuery query) throws TException {
		return employeeDao.getEmployee(query);
	}
	/*
	 * @auth zzt
	 * 获取推荐者列表
	 * (non-Javadoc)
	 * @see com.moseeker.thrift.gen.dao.service.UserDao.Iface#getUserEmployee(int, java.util.List)
	 */
	@Override
	public Response getUserEmployee(int companyId, List<Integer> weChatIds) throws TException {
		return userEmployeeService.getUserEmployeeByWeChats(companyId, weChatIds);
	}

	@Override
	public Response postUserEmployeePoints(List<UserEmployeePointStruct> records) throws TException {
		return userEmployeeService.postUserEmployeePointsRecords(records);
	}

	@Override
	public Response getPointSum(List<Long> record) throws TException {
		return userEmployeeService.getSumPoint(record);
	}

	@Override
	public Response putUserEmployees(List<UserEmployeeStruct> records) throws TException {
		return userEmployeeService.putUserEmployees(records);
	}

	@Override
	public Response putUserEmployeePoints(List<UserEmployeePointStruct> records) throws TException {
		return userEmployeeService.putUserEmployeePointsRecords(records);
	}

	@Override
	public List<UserEmployeePointsRecordDO> getUserEmployeePoints(int employeeId) throws TException {
		return userEmployeeService.getUserEmployeePoints(employeeId);
	}
}
