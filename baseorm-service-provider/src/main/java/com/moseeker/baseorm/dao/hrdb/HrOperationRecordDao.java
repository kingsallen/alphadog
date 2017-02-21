package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.db.hrdb.tables.HrOperationRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrOperationRecordRecord;
import com.moseeker.baseorm.util.StructDaoImpl;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.thrift.gen.dao.struct.HistoryOperate;
import com.moseeker.thrift.gen.dao.struct.HrOperationrecordDO;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class HrOperationRecordDao extends StructDaoImpl<HrOperationrecordDO, HrOperationRecordRecord, HrOperationRecord> {

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
			sb.append(" select max(id) id from hrdb.hr_operation_record where operate_tpl_id !=4 and app_id in "+employeeIds);
			sb.append(" group by app_id) op, hrdb.hr_operation_record r where r.id=op.id ) as op ");
			sb.append(" left join configdb.config_sys_points_conf_tpl template on op.operate_tpl_id = template.id ");
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

	/**
	 * 查找拒绝前的最近一条记录
	 * @param appidSet
	 * @return
	 */
	public List<HrOperationrecordDO> listLatestOperationRecordByAppIdSet(Set<Integer> appidSet) {
		List<HrOperationrecordDO> operationrecordDOList = new ArrayList<>();

		Connection conn = null;
		try {
			conn = DBConnHelper.DBConn.getConn();
			DSLContext create = DBConnHelper.DBConn.getJooqDSL(conn);

			operationrecordDOList = create.select(HrOperationRecord.HR_OPERATION_RECORD.ID, HrOperationRecord.HR_OPERATION_RECORD.APP_ID,
					HrOperationRecord.HR_OPERATION_RECORD.OPERATE_TPL_ID)
					.from(
							create.select().from(HrOperationRecord.HR_OPERATION_RECORD)
									.where(HrOperationRecord.HR_OPERATION_RECORD.APP_ID.in(appidSet))
									.and(HrOperationRecord.HR_OPERATION_RECORD.OPERATE_TPL_ID.notEqual(Constant.RECRUIT_STATUS_REJECT))
									.orderBy(HrOperationRecord.HR_OPERATION_RECORD.OPT_TIME.desc())
					)
					.where(HrOperationRecord.HR_OPERATION_RECORD.OPERATE_TPL_ID.notEqual(Constant.RECRUIT_STATUS_REJECT))
					.groupBy(HrOperationRecord.HR_OPERATION_RECORD.APP_ID)
					.fetch().into(HrOperationrecordDO.class);

		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if(conn!=null && !conn.isClosed()){
                    conn.close();
                }
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return operationrecordDOList;
	}
}
