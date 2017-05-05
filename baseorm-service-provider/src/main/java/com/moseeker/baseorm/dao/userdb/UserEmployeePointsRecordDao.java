package com.moseeker.baseorm.dao.userdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.userdb.tables.UserEmployeePointsRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeePointsRecordRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeePointsRecordDO;
import com.moseeker.thrift.gen.useraccounts.struct.UserEmployeePointSum;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.sum;

@Repository
public class UserEmployeePointsRecordDao extends JooqCrudImpl<UserEmployeePointsRecordDO, UserEmployeePointsRecordRecord> {

	public UserEmployeePointsRecordDao(TableImpl<UserEmployeePointsRecordRecord> table, Class<UserEmployeePointsRecordDO> userEmployeePointsRecordDOClass) {
		super(table, userEmployeePointsRecordDOClass);
	}

	public List<UserEmployeePointSum> getSumRecord(List<Long> list) throws Exception{
		List<UserEmployeePointSum> points=new ArrayList<UserEmployeePointSum>();
		SelectConditionStep<Record2<BigDecimal, Long>> table=create.select(
				sum(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.AWARD),
				UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.EMPLOYEE_ID)
		.from(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD)
		.where(UserEmployeePointsRecord.USER_EMPLOYEE_POINTS_RECORD.EMPLOYEE_ID.in(list));
		Result<Record2<BigDecimal, Long>> result=table.fetch();
		if(result!=null&&result.size()>0){
			UserEmployeePointSum point=null;
			for(Record2<BigDecimal, Long> r:result){
				point=new UserEmployeePointSum();
				//由于可能没有记录，所以可能为null
				if(r.getValue(0)==null){
					point.setAward(0L);
				}else{
					point.setAward(Long.parseLong(r.getValue(0)+""));
				}
				//EMPLOYEE_ID不可能为null，所以不判断null
				point.setEmployee_id((long)r.getValue(1));
				points.add(point);
			}
		}
		return points;
	}
}
