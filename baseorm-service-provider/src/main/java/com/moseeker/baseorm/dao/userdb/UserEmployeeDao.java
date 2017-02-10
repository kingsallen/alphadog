package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.db.userdb.tables.UserEmployee;
import com.moseeker.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.dao.struct.UserEmployeeDTO;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserEmployeeDao extends BaseDaoImpl<UserEmployeeRecord, UserEmployee> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected void initJOOQEntity() {
		this.tableLike = UserEmployee.USER_EMPLOYEE;
	}

	public UserEmployeeDTO getEmployee(CommonQuery query)  {
		UserEmployeeDTO employee = new UserEmployeeDTO();
		
		try {
			UserEmployeeRecord record = this.getResource(query);
			if(record != null) {
				employee = BeanUtils.DBToStruct(UserEmployeeDTO.class, record);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
		
		return employee;
	}

	public List<UserEmployeeRecord> getEmployeeByWeChat(Integer companyId, List<Integer> weChatIds) throws Exception{
		List<UserEmployeeRecord> list=new ArrayList<UserEmployeeRecord>();
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			SelectJoinStep<Record> table = create.select().from(UserEmployee.USER_EMPLOYEE);
			table.where(UserEmployee.USER_EMPLOYEE.COMPANY_ID.eq(companyId))
					.and(UserEmployee.USER_EMPLOYEE.DISABLE.eq((byte)0)
							.and(UserEmployee.USER_EMPLOYEE.WXUSER_ID.notEqual(0))
							.and(UserEmployee.USER_EMPLOYEE.WXUSER_ID.in(weChatIds))
					);
			Result<Record> result=table.fetch();
			if(result!=null&&result.size()>0){
				for(Record r:result){
					list.add((UserEmployeeRecord) r);
				}
			}
		}catch(Exception e){
			logger.error("error", e);
			throw new Exception(e);
		}finally{
			if(conn!=null&&!conn.isClosed()){
				conn.close();
				conn=null;
			}
		}
		return list;
	}
}
