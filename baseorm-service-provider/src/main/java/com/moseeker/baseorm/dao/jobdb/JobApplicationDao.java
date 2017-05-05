package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysPointsConfTpl;
import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.common.dbutils.DBConnHelper;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.db.userdb.tables.UserUser;
import com.moseeker.thrift.gen.application.struct.ApplicationAts;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.dao.struct.JobApplicationDO;
import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装申请表基本操作
 *
 * @author jack
 * date : Jan 22, 2017
 * email: wengjianfei@moseeker.com
 */
@Service
public class JobApplicationDao extends JooqCrudImpl<JobApplicationDO, JobApplicationRecord> {

	public JobApplicationDao(TableImpl<JobApplicationRecord> table, Class<JobApplicationDO> jobApplicationDOClass) {
		super(table, jobApplicationDOClass);
	}
	/**
	 * 查询申请数据
	 * @param query
	 * @return
	 */
	public List<JobApplicationDO> getApplications(Query query) {
		return getDatas(query);
	}

	public List<ProcessValidationStruct> getAuth(List<Integer> appIds,Integer companyId,Integer progressStatus) throws Exception{
		List<ProcessValidationStruct> list=new ArrayList<ProcessValidationStruct>();
		SelectJoinStep<Record9<Integer, Integer, Integer, Integer, Integer, String, Integer, Integer, String>> table=create.select(
				JobApplication.JOB_APPLICATION.ID,
				JobApplication.JOB_APPLICATION.COMPANY_ID,
				JobApplication.JOB_APPLICATION.RECOMMENDER_ID,
				JobApplication.JOB_APPLICATION.RECOMMENDER_USER_ID,
				JobApplication.JOB_APPLICATION.APPLIER_ID,
				UserUser.USER_USER.NAME,
//					UserWxUser.USER_WX_USER.SYSUSER_ID,
				ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.ID ,
				ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.RECRUIT_ORDER,
				JobPosition.JOB_POSITION.TITLE
				).from(JobApplication.JOB_APPLICATION);
		table.leftJoin(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL)
		.on("jobdb.job_application.app_tpl_id=configdb.config_sys_points_conf_tpl.id");
		table.leftJoin(JobPosition.JOB_POSITION).on("jobdb.job_application.position_id=jobdb.job_position.id");
//			table.leftJoin(UserWxUser.USER_WX_USER).on("jobdb.job_application.recommender_id=userdb.user_wx_user.id");
		table.leftJoin(UserUser.USER_USER).on("jobdb.job_application.applier_id=userdb.user_user.id");
		table.where(JobApplication.JOB_APPLICATION.ID.in(appIds)
				.and(JobApplication.JOB_APPLICATION.COMPANY_ID.eq((int)(companyId))));
		if(progressStatus==13){
			table.where().and(JobApplication.JOB_APPLICATION.APP_TPL_ID.notEqual((int)(4)));
		}else if(progressStatus==99){
			table.where().and(JobApplication.JOB_APPLICATION.APP_TPL_ID.equal((int)(4)));
		}
		Result<Record9<Integer, Integer, Integer, Integer, Integer, String, Integer, Integer, String>> result=table.fetch();
		if(result!=null&&result.size()>0){
			ProcessValidationStruct data= null;
			for(Record9<Integer, Integer, Integer, Integer, Integer, String, Integer, Integer, String> record:result){
				data=new ProcessValidationStruct();
				data.setCompany_id(record.getValue(JobApplication.JOB_APPLICATION.COMPANY_ID).intValue());
				data.setId(record.getValue(JobApplication.JOB_APPLICATION.ID).intValue());
				data.setRecommender_id(record.getValue(JobApplication.JOB_APPLICATION.RECOMMENDER_ID).intValue());
				data.setRecommender_user_id(record.getValue(JobApplication.JOB_APPLICATION.RECOMMENDER_USER_ID).intValue());
				data.setRecruit_order(record.getValue(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.RECRUIT_ORDER).intValue());
				data.setTemplate_id(record.getValue(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.ID).intValue());
				data.setApplier_id(record.getValue(JobApplication.JOB_APPLICATION.APPLIER_ID).intValue());
				data.setApplier_name(record.getValue(UserUser.USER_USER.NAME));
				data.setPosition_name(record.getValue(JobPosition.JOB_POSITION.TITLE));
				list.add(data);
			}
		}
		return list;
	}
	public List<ApplicationAts> getApplicationByLApId(List<Integer> lists) throws Exception{
		List<ApplicationAts> list=new ArrayList<ApplicationAts>();
		SelectConditionStep<Record3<Integer, Integer, Integer>> table =create.select(
				JobApplication.JOB_APPLICATION.COMPANY_ID,
				JobApplication.JOB_APPLICATION.ID,
				JobPosition.JOB_POSITION.PUBLISHER
				).from(JobApplication.JOB_APPLICATION)
				.innerJoin(JobPosition.JOB_POSITION)
				.on("jobdb.job_application.position_id=jobdb.job_position.id")
				.where(JobApplication.JOB_APPLICATION.L_APPLICATION_ID.in(lists));
		Result<Record3<Integer, Integer, Integer>> result=table.fetch();
		if(result!=null&&result.size()>0){
			ApplicationAts ats=null;
			for(Record3<Integer, Integer, Integer> r:result){
				ats=new ApplicationAts();
				ats.setAccount_id(r.getValue(JobPosition.JOB_POSITION.PUBLISHER).intValue());
				ats.setApplication_id(r.getValue(JobApplication.JOB_APPLICATION.ID).intValue());
				ats.setCompany_id(r.getValue(JobApplication.JOB_APPLICATION.COMPANY_ID).intValue());
				list.add(ats);
			}
		}
		return list;
	}
}
