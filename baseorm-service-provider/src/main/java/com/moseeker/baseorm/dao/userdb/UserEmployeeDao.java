package com.moseeker.baseorm.dao.userdb;

import org.springframework.stereotype.Service;

import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.userdb.tables.UserEmployee;
import com.moseeker.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.UserEmployeePojo;

@Service
public class UserEmployeeDao extends BaseDaoImpl<UserEmployeeRecord, UserEmployee> {

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserEmployee.USER_EMPLOYEE;
	}

	public UserEmployeePojo getEmployee(CommonQuery query)  {
		UserEmployeePojo employee = new UserEmployeePojo();
		
		try {
			UserEmployeeRecord record = this.getResource(query);
			if(record != null) {
				employee = BeanUtils.DBToStruct(UserEmployeePojo.class, record);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		
		return employee;
	}
}
