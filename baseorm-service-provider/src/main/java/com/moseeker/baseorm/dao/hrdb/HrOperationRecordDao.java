package com.moseeker.baseorm.dao.hrdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.hrdb.tables.HrOperationRecord;
import com.moseeker.baseorm.db.hrdb.tables.records.HrOperationRecordRecord;
import com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication;
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

		return operationrecordDOList;
	}

    public int addRecord(JobApplication application, int appTplId, int companyId, int hrId) {
		HrOperationRecordRecord recordRecord = create.insertInto(HrOperationRecord.HR_OPERATION_RECORD)
				.columns(HrOperationRecord.HR_OPERATION_RECORD.APP_ID,
						HrOperationRecord.HR_OPERATION_RECORD.OPERATE_TPL_ID,
						HrOperationRecord.HR_OPERATION_RECORD.ADMIN_ID,
						HrOperationRecord.HR_OPERATION_RECORD.COMPANY_ID,
						HrOperationRecord.HR_OPERATION_RECORD.OPT_TIME)
				.values((long)application.getId(), appTplId, (long)hrId, (long)companyId,
						new Timestamp(application.getSubmitTime().getTime()-1))
				.returning()
				.fetchOne();
		return recordRecord != null ? recordRecord.getId():0;
    }

	public List<com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord> fetchLastOperationByAppIdListAndSate(
			List<Integer> applicationIdList, int recruitStatus) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select op.id, op.app_id, op.operate_tpl_id ");
		sb.append(" from (select operation.id, operation.app_id, operation.operate_tpl_id ");
		sb.append(" from hrdb.hr_operation_record operation ");
		sb.append(" where operation.app_id in ");
		sb.append(StringUtils.converToStr(applicationIdList));
		sb.append(" and operation.operate_tpl_id = ");
		sb.append(recruitStatus);
		sb.append(" order by operation.opt_time desc) as op ");
		sb.append(" group by op.app_id");

		return create.fetch(sb.toString()).into(com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord.class);
	}

    public com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord getCurentOperation(int applicationId) {
		HrOperationRecordRecord recordRecord = create.selectFrom(HrOperationRecord.HR_OPERATION_RECORD)
				.where(HrOperationRecord.HR_OPERATION_RECORD.APP_ID.eq(Long.valueOf(applicationId)))
				.orderBy(HrOperationRecord.HR_OPERATION_RECORD.OPT_TIME.desc())
				.limit(1)
				.fetchOne();
		if (recordRecord != null) {
			return recordRecord.into(com.moseeker.baseorm.db.hrdb.tables.pojos.HrOperationRecord.class);
		} else {
			return null;
		}

    }
}
