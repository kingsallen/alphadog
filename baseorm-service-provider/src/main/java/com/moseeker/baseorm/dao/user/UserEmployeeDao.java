package com.moseeker.baseorm.dao.user;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectJoinStep;
import org.jooq.types.UInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.common.dbutils.DBConnHelper;
@Service
public class UserEmployeeDao extends BaseDaoImpl<UserEmployeeRecord, UserEmployee> {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=UserEmployee.USER_EMPLOYEE;
	}
	public List<UserEmployeeRecord> getEmployeeByWeChat(Integer companyId,List<Integer> weChatIds) throws Exception{
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

	public int delResource(Map<String,String> filter) throws Exception {
		if(filter.size() > 0) {
			CommonQuery commonQuery = new CommonQuery();
			commonQuery.setEqualFilter(filter);
			List<UserEmployeeRecord> records = getResources(commonQuery);
			if(records.size() > 0){
				return delResources(records);
			}
		}
		return 0;
	}

}
