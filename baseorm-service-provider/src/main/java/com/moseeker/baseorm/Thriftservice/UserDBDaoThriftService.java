package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.userdb.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.service.UserEmployeeDaoService;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.userdb.tables.records.UserUserRecord;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.common.struct.CURDException;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.UserDBDao.Iface;
import com.moseeker.thrift.gen.dao.struct.*;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointStruct;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeeStruct;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDBDaoThriftService implements Iface {

	private Logger logger = LoggerFactory.getLogger(UserDBDaoThriftService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserFavPositionDao favPositionDao;
	
	@Autowired
	private com.moseeker.baseorm.dao.userdb.UserEmployeeDao employeeDao;

	@Autowired
	private UserEmployeeDaoService userEmployeeDaoService;

	@Autowired
	private UserHRAccountDao userHRAccountDao;

	@Autowired
	private WxUserDao wxUserDao;

	@Autowired
	private UserEmployeePointsDao userEmployeePointsDao;

	@Override
	public UserUserDO getUser(CommonQuery query) throws TException {
		UserUserDO user = new UserUserDO();
		try {
			UserUserRecord record = userDao.getResource(QueryConvert.commonQueryConvertToQuery(query));
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
		return userDao.listResources(QueryConvert.commonQueryConvertToQuery(query));
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
	public List<UserHrAccountDO> listUserHrAccount(CommonQuery query) throws CURDException, TException {
		return userHRAccountDao.listResources(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public UserHrAccountDO getUserHrAccount(CommonQuery query) throws CURDException, TException {
		return userHRAccountDao.findResource(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public UserHrAccountDO updateUserHrAccount(UserHrAccountDO userHrAccountDO) throws CURDException, TException {
		return userHRAccountDao.updateResource(userHrAccountDO);
	}

	@Override
	public int deleteUserHrAccount(int id) throws CURDException, TException {
		return userHRAccountDao.deleteUserHrAccount(id);
	}

	@Override
	public List<UserFavPositionDO> getUserFavPositions(CommonQuery query) throws TException {
		return favPositionDao.getUserFavPositions(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public UserEmployeeDO getEmployee(CommonQuery query) throws TException {
		return employeeDao.getEmployee(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public Response putUserEmployee(UserEmployeePointsRecordDO employeeDo) throws TException {
		return userEmployeeDaoService.putUserEmployee(employeeDo);
	}

	/*
	 * @auth zzt
	 * 获取推荐者列表
	 * (non-Javadoc)
	 * @see com.moseeker.thrift.gen.dao.service.UserDao.Iface#getUserEmployee(int, java.util.List)
	 */
	@Override
	public Response getUserEmployee(int companyId, List<Integer> weChatIds) throws TException {
		return userEmployeeDaoService.getUserEmployeeByWeChats(companyId, weChatIds);
	}

	@Override
	public Response postUserEmployeePoints(List<UserEmployeePointStruct> records) throws TException {
		return userEmployeeDaoService.postUserEmployeePointsRecords(records);
	}

	@Override
	public int updateUserEmployeePoint(int id) throws BIZException, TException {
		return employeeDao.updateUserEmployeePoint(id);
	}

	@Override
	public Response getPointSum(List<Long> record) throws TException {
		return userEmployeeDaoService.getSumPoint(record);
	}

	@Override
	public Response putUserEmployees(List<UserEmployeeStruct> records) throws TException {
		return userEmployeeDaoService.putUserEmployees(records);
	}

	@Override
	public Response putUserEmployeePoints(List<UserEmployeePointStruct> records) throws TException {
		return userEmployeeDaoService.putUserEmployeePointsRecords(records);
	}

	@Override
	public List<UserEmployeePointsRecordDO> getUserEmployeePoints(int employeeId) throws TException {
		return userEmployeeDaoService.getUserEmployeePoints(employeeId);
	}

	@Override
	public UserEmployeePointsRecordDO saveUserEmployeePoints(UserEmployeePointsRecordDO employeePoint) throws BIZException, TException {
		return userEmployeePointsDao.saveResource(employeePoint);
	}

	@Override
	public List<UserEmployeeDO> getUserEmployeesDO(CommonQuery query) throws TException {
		return userEmployeeDaoService.getEmployeesDO(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public Response putUserEmployeesDO(List<UserEmployeeDO> employeeDoList) throws TException {
		return userEmployeeDaoService.putEmployeesDO(employeeDoList);
	}

	@Override
	public List<UserWxUserDO> listUserWxUserDO(CommonQuery query) throws CURDException, TException {
		return wxUserDao.listResources(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public UserWxUserDO getUserWxUserDO(CommonQuery query) throws CURDException, TException {
		return wxUserDao.findResource(QueryConvert.commonQueryConvertToQuery(query));
	}
	
	@Override
	public int postUserEmployeeDO(UserEmployeeDO userEmployee)
			throws TException {
		if (userEmployee != null) {
			UserEmployeeRecord ueRecord = BeanUtils.structToDB(userEmployee, UserEmployeeRecord.class);
			try {
				return employeeDao.addRecord(ueRecord);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return 0;
	}

	@Override
	public int delUserEmployeeDO(UserEmployeeDO userEmployee) throws TException {
		return employeeDao.deleteData(userEmployee);
	}

	@Override
	public UserEmployeeDO getUserEmployeeDO(CommonQuery query) throws TException {
		return employeeDao.getEmployee(QueryConvert.commonQueryConvertToQuery(query));
	}
}
