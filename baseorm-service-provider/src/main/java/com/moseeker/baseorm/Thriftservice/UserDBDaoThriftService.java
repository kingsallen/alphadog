package com.moseeker.baseorm.Thriftservice;

import com.moseeker.baseorm.dao.userdb.*;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserUserRecord;
import com.moseeker.baseorm.service.UserEmployeeDaoService;
import com.moseeker.baseorm.tool.QueryConvert;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.util.BeanUtils;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDBDaoThriftService implements Iface {

	private Logger logger = LoggerFactory.getLogger(UserDBDaoThriftService.class);
	
	@Autowired
	private UserUserDao userDao;
	
	@Autowired
	private UserFavPositionDao favPositionDao;
	
	@Autowired
	private com.moseeker.baseorm.dao.userdb.UserEmployeeDao employeeDao;

	@Autowired
	private UserEmployeeDaoService userEmployeeDaoService;

	@Autowired
	private UserHRAccountDao userHRAccountDao;

	@Autowired
	private UserWxUserDao wxUserDao;

	@Autowired
	private UserEmployeePointsDao userEmployeePointsDao;

	@Override
	public UserUserDO getUser(CommonQuery query) throws TException {
		UserUserDO user = new UserUserDO();
		try {
			UserUserRecord record = userDao.getRecord(QueryConvert.commonQueryConvertToQuery(query));
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
		return new ArrayList<>();
	}

	@Override
	public UserUserDO saveUser(UserUserDO user) throws TException {
		if(user.getPassword() == null) {
			user.setPassword("");
		}
		return user;
	}

	@Override
	public List<UserHrAccountDO> listHRFromCompany(int comanyId) throws TException {
		return null;
	}

	@Override
	public List<UserHrAccountDO> listUserHrAccount(CommonQuery query) throws CURDException, TException {
		return new ArrayList<>();
	}

	@Override
	public UserHrAccountDO getUserHrAccount(CommonQuery query) throws CURDException, TException {
		return new UserHrAccountDO();
	}

	@Override
	public UserHrAccountDO updateUserHrAccount(UserHrAccountDO userHrAccountDO) throws CURDException, TException {
		com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO u = new com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO();
		userHRAccountDao.updateData(u);
		return userHrAccountDO;
	}

	@Override
	public int deleteUserHrAccount(int id) throws CURDException, TException {
		return userHRAccountDao.deleteUserHrAccount(id);
	}

	@Override
	public List<UserFavPositionDO> getUserFavPositions(CommonQuery query) throws TException {
//		return favPositionDao.getUserFavPositions(QueryConvert.commonQueryConvertToQuery(query));
        return null;
	}

	@Override
	public UserEmployeeDO getEmployee(CommonQuery query) throws TException {
		return employeeDao.getEmployee(QueryConvert.commonQueryConvertToQuery(query));
	}

	@Override
	public Response putUserEmployee(UserEmployeePointsRecordDO employeeDo) throws TException {
		return userEmployeeDaoService.putUserEmployee(new com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO());
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
		return new ArrayList<>();
	}

	@Override
	public UserEmployeePointsRecordDO saveUserEmployeePoints(UserEmployeePointsRecordDO employeePoint) throws BIZException, TException {
		userEmployeePointsDao.addData(employeePoint);
		return employeePoint;
	}

	@Override
	public List<UserEmployeeDO> getUserEmployeesDO(CommonQuery query) throws TException {
		return new ArrayList<>();
	}

	@Override
	public Response putUserEmployeesDO(List<UserEmployeeDO> employeeDoList) throws TException {
		return ResponseUtils.success(employeeDoList);
	}

	@Override
	public List<UserWxUserDO> listUserWxUserDO(CommonQuery query) throws CURDException, TException {
		return wxUserDao.getDatas(QueryConvert.commonQueryConvertToQuery(query),UserWxUserDO.class);
	}

	@Override
	public UserWxUserDO getUserWxUserDO(CommonQuery query) throws CURDException, TException {
		return wxUserDao.getData(QueryConvert.commonQueryConvertToQuery(query),UserWxUserDO.class);
	}
	
	@Override
	public int postUserEmployeeDO(UserEmployeeDO userEmployee)
			throws TException {
		if (userEmployee != null) {
			UserEmployeeRecord ueRecord = BeanUtils.structToDB(userEmployee, UserEmployeeRecord.class);
			try {
				return employeeDao.addRecord(ueRecord).getId();
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
