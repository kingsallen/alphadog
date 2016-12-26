package com.moseeker.baseorm.dao.user;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import static org.jooq.impl.DSL.sum;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointSum;
@Service
public class UserEmployeePointsRecordDao extends BaseDaoImpl<UserEmployeePointsRecordRecord, UserEmployeePointsRecord>{

	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD;
	}
	public List<UserEmployeePointSum> getSumRecord(List<Long> list) throws Exception{
		Connection conn = null;
		List<UserEmployeePointSum> points=new ArrayList<UserEmployeePointSum>();
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			Result<Record2<BigDecimal, Long>> result=create.select(
					sum(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.AWARD),
					UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.EMPLOYEE_ID)
			.from(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD)
			.where(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.EMPLOYEE_ID.in(list))
			.fetch();
			if(result!=null&&result.size()>0){
				UserEmployeePointSum point=null;
				for(Record2<BigDecimal, Long> r:result){
					point=new UserEmployeePointSum();
					point.setAward((long)r.getValue(1));
					point.setEmployee_id((long)r.getValue(2));
					points.add(point);
				}
			}
			
		}catch (Exception e) {
			logger.error("error", e);
			throw new Exception(e);
		} finally {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		}
		
		return points;
	}
}
