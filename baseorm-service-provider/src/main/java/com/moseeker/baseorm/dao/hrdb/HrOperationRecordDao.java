package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrOperationRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrOperationRecordRecord;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.thrift.gen.dao.struct.HistoryOperate;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrOperationRecordDO;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class HrOperationRecordDao extends JooqCrudImpl<HrOperationRecordDO, HrOperationRecordRecord> {

    public HrOperationRecordDao() {
        super(HrOperationRecord.HR_OPERATION_RECORD, HrOperationRecordDO.class);
    }

	public HrOperationRecordDao(TableImpl<HrOperationRecordRecord> table, Class<HrOperationRecordDO> hrOperationRecordDOClass) {
		super(table, hrOperationRecordDOClass);
	}

	public List<HistoryOperate> getHistoryOperate(String employeeIds) throws Exception{
		List<HistoryOperate> list=new ArrayList<HistoryOperate>();
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
		return list;
	}

	/**
	 * 查找拒绝前的最近一条记录
	 * @param appidSet
	 * @return
	 */
	public List<HrOperationRecordDO> listLatestOperationRecordByAppIdSet(Set<Integer> appidSet) {
		List<HrOperationRecordDO> operationrecordDOList = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" select op.id, op.app_id, op.operate_tpl_id ");
		sb.append(" from (select operation.id, operation.app_id, operation.operate_tpl_id ");
		sb.append(" from hrdb.hr_operation_record operation ");
		sb.append(" where operation.app_id in ");
		sb.append(StringUtils.converToStr(appidSet));
		sb.append(" and operation.operate_tpl_id <> ");
		sb.append(Constant.RECRUIT_STATUS_REJECT);
		sb.append(" order by operation.opt_time desc) as op ");
		sb.append(" group by op.app_id");

		operationrecordDOList = create.fetch(sb.toString()).into(HrOperationRecordDO.class);

		/*TableLike<?> e = create.select(HrOperationRecord.HR_OPERATION_RECORD.ID, HrOperationRecord.HR_OPERATION_RECORD.APP_ID,
				HrOperationRecord.HR_OPERATION_RECORD.OPERATE_TPL_ID).from(HrOperationRecord.HR_OPERATION_RECORD)
				.where(HrOperationRecord.HR_OPERATION_RECORD.APP_ID.in(appidSet))
				.and(HrOperationRecord.HR_OPERATION_RECORD.OPERATE_TPL_ID.notEqual(Constant.RECRUIT_STATUS_REJECT))
				.orderBy(HrOperationRecord.HR_OPERATION_RECORD.OPT_TIME.desc());

		HrOperationRecord.HR_OPERATION_RECORD.as("s");
		Field<Integer> ID = HrOperationRecord.HR_OPERATION_RECORD.ID.as("id");
		Field<Long> APP_ID = HrOperationRecord.HR_OPERATION_RECORD.APP_ID.as("app_id");
		Field<Integer> OPERATE_TPL_ID = HrOperationRecord.HR_OPERATION_RECORD.OPERATE_TPL_ID.as("operate_tpl_id");


		operationrecordDOList = create.select(ID, APP_ID, OPERATE_TPL_ID)
				.from(e)
				.where(e.field(OPERATE_TPL_ID).notEqual(Constant.RECRUIT_STATUS_REJECT))
				.groupBy(HrOperationRecord.HR_OPERATION_RECORD.APP_ID)
				.fetch().into(HrOperationRecordDO.class);*/

		return operationrecordDOList;
	}

    public int addRecord(int applicationId, int appTplId, int companyId, int hrId) {
		HrOperationRecordRecord recordRecord = create.insertInto(HrOperationRecord.HR_OPERATION_RECORD)
				.columns(HrOperationRecord.HR_OPERATION_RECORD.APP_ID,
						HrOperationRecord.HR_OPERATION_RECORD.OPERATE_TPL_ID,
						HrOperationRecord.HR_OPERATION_RECORD.ADMIN_ID,
						HrOperationRecord.HR_OPERATION_RECORD.COMPANY_ID,
						HrOperationRecord.HR_OPERATION_RECORD.OPT_TIME)
				.values((long)applicationId, appTplId, (long)hrId, (long)companyId,
						new Timestamp(System.currentTimeMillis()))
				.returning()
				.fetchOne();
		return recordRecord != null ? recordRecord.getId():0;
    }
}
