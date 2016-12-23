package com.moseeker.baseorm.dao.hr;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import com.moseeker.baseorm.db.hrdb.tables.HrOperationRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrOperationRecordRecord;
import com.moseeker.baseorm.util.BaseDaoImpl;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.thrift.gen.dao.struct.HistoryOperate;
@Service
public class HrOperationRecordDao extends BaseDaoImpl<HrOperationRecordRecord, HrOperationRecord>{

	@Override
	protected void initJOOQEntity() {
		// TODO Auto-generated method stub
		this.tableLike=HrOperationRecord.HR_OPERATION_RECORD;
	}
	public List<HistoryOperate> getHistoryOperate(String employeeIds) throws Exception{
		List<HistoryOperate> list=new ArrayList<HistoryOperate>();
		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);
			StringBuffer sb=new StringBuffer();
			sb.append(" select op.id, op.app_id, op.operate_tpl_id, template.recruit_order from ( ");
			sb.append(" select r.id, r.app_id, r.operate_tpl_id from ( ");
			sb.append(" select max(id) id from hr_operation_record where operate_tpl_id !=4 and app_id in ("+employeeIds);
			sb.append("  ) group by app_id) op, hr_operation_record r where r.id=op.id ) as op ");
			sb.append(" left join config_sys_points_conf_tpl template on op.operate_tpl_id = template.id ");
			String sql=sb.toString();
			Result<Record> result=create.fetch(sql);
			if(result!=null&&result.size()>0){
				HistoryOperate his=null;
				for(Record r:result){
					his=new HistoryOperate();
					his.setId((int)r.getValue("id"));
					his.setApp_id((long)r.getValue("app_id"));
					his.setOperate_tpl_id((int)r.getValue("operate_tpl_id"));
					his.setRecruit_order((int)r.getValue("recruit_order"));
					list.add(his);
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
