package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.candidatedb.tables.CandidateShareChain;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysPointsConfTpl;
import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.pojo.ApplicationSaveResultVO;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.application.struct.ApplicationAts;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.jooq.impl.DSL.count;

/**
 * 封装申请表基本操作
 *
 * @author jack
 * date : Jan 22, 2017
 * email: wengjianfei@moseeker.com
 */
@Service
public class JobApplicationDao extends JooqCrudImpl<JobApplicationDO, JobApplicationRecord> {

    public JobApplicationDao() {
        super(JobApplication.JOB_APPLICATION, JobApplicationDO.class);
    }

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
		SelectJoinStep<Record11<Integer, Integer, Integer, Integer, Integer, String, Integer, Integer, String, Integer, Integer>> table=create.select(
				JobApplication.JOB_APPLICATION.ID,
				JobApplication.JOB_APPLICATION.COMPANY_ID,
				JobApplication.JOB_APPLICATION.RECOMMENDER_ID,
				JobApplication.JOB_APPLICATION.RECOMMENDER_USER_ID,
				JobApplication.JOB_APPLICATION.APPLIER_ID,
				UserUser.USER_USER.NAME,
				ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.ID ,
				ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.RECRUIT_ORDER,
				JobPosition.JOB_POSITION.TITLE,
                JobPosition.JOB_POSITION.PUBLISHER,
                JobApplication.JOB_APPLICATION.POSITION_ID
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
		Result<Record11<Integer, Integer, Integer, Integer, Integer, String, Integer, Integer, String, Integer, Integer>> result=table.fetch();
		if(result!=null&&result.size()>0){
			ProcessValidationStruct data= null;
			for(Record11<Integer, Integer, Integer, Integer, Integer, String, Integer, Integer, String, Integer, Integer> record:result){
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
				data.setPublisher(record.getValue(JobPosition.JOB_POSITION.PUBLISHER));
				data.setPosition_id(record.getValue(JobApplication.JOB_APPLICATION.POSITION_ID));
				list.add(data);
			}
		}
		return list;
	}
	public List<ApplicationAts> getApplicationByLApId(List<Integer> lists) throws Exception{
		List<ApplicationAts> list=new ArrayList<>();
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

    /**
     * insert 判断是否已存在
     * @param record
     * @return
     */
	public ApplicationSaveResultVO addIfNotExists(JobApplicationRecord record) {
        List<Field<?>> changedFieldList = Arrays.stream(record.fields()).filter(f -> record.changed(f)).collect(Collectors.toList());
        String insertSql = " insert into jobdb.job_application ".concat(changedFieldList.stream().map(m -> m.getName()).collect(Collectors.joining(",", " (", ") ")))
                .concat(" select ").concat(Stream.generate(() -> "?").limit(changedFieldList.size()).collect(Collectors.joining(",")))
                .concat(" from dual where not exists ( ")
                .concat(" select id from jobdb.job_application where ")
                .concat(JobApplication.JOB_APPLICATION.DISABLE.getName()).concat(" = 0 and ")
                .concat(JobApplication.JOB_APPLICATION.APPLIER_ID.getName()).concat(" = ").concat(record.getApplierId().toString()).concat(" and ")
                .concat(JobApplication.JOB_APPLICATION.POSITION_ID.getName()).concat(" = ").concat(record.getPositionId().toString())
                .concat(" ) ");
        logger.info("addIfNotExisits job_application sql: {}", insertSql);
		ApplicationSaveResultVO resultVO = new ApplicationSaveResultVO();
        int result = create.execute(insertSql, changedFieldList.stream().map(m -> record.getValue(m)).collect(Collectors.toList()).toArray());
        if (result == 0) {
            logger.info("用户:{}已申请过职位:{}, 无需重复投递", record.getApplierId(), record.getPositionId());
			resultVO.setCreate(false);
        } else {
        	resultVO.setCreate(true);
		}

        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(JobApplication.JOB_APPLICATION.DISABLE.getName(), 0)
				.and(JobApplication.JOB_APPLICATION.APPLIER_ID.getName(), record.getApplierId())
        .and(JobApplication.JOB_APPLICATION.POSITION_ID.getName(), record.getPositionId());
        JobApplicationDO applicationDO = getData(queryBuilder.buildQuery());
		resultVO.setApplicationId(applicationDO.getId());
		resultVO.setPositionId(applicationDO.getPositionId());
		resultVO.setApplierId(applicationDO.getApplierId());

        //如果不是新增申请，那么合并申请来源
		logger.info("addIfNotExists applicationDO:{}", applicationDO);
		logger.info("addIfNotExists result:{}", result);
		if (result == 0 && record.getOrigin() != null && record.getOrigin() > 0
					&& record.getOrigin().intValue() != applicationDO.getOrigin()) {

				logger.info("addIfNotExists record.origin:{}", record.getOrigin());

				logger.info("addIfNotExists applicationDO.origin:{}", applicationDO.getOrigin());
				int origin = record.getOrigin().intValue() | applicationDO.getOrigin();
				logger.info("addIfNotExists origin:{}", origin);
				create.update(JobApplication.JOB_APPLICATION)
						.set(JobApplication.JOB_APPLICATION.ORIGIN, origin)
                        .set(JobApplication.JOB_APPLICATION.UPDATE_TIME, new Timestamp(new Date().getTime()))
						.where(JobApplication.JOB_APPLICATION.ID.eq(applicationDO.getId()))
						.execute();
		}

        return resultVO;
    }

	/**
	 * 计算给定时间内的员工转发带来的投递次数
	 * @param userIdList 员工编号集合
	 * @param positionIdList 职位编号集合
	 * @param lastFriday 开始时间 （大于开始时间）
	 * @param currentFriday 结束时间 （小于等于结束时间）
	 * @return 投递次数
	 */
	public Result<Record2<Integer,Integer>> countEmployeeApply(List<Integer> userIdList, List<Integer> positionIdList,
															   LocalDateTime lastFriday, LocalDateTime currentFriday) {

		System.out.println(create.select(
				JobApplication.JOB_APPLICATION.RECOMMENDER_USER_ID,
				count(JobApplication.JOB_APPLICATION.ID).as("count")
		)
				.from(JobApplication.JOB_APPLICATION)
				.where(JobApplication.JOB_APPLICATION.RECOMMENDER_USER_ID.in(userIdList))
				.and(JobApplication.JOB_APPLICATION.POSITION_ID.in(positionIdList))
				.and(JobApplication.JOB_APPLICATION.SUBMIT_TIME.gt(
						new Timestamp(lastFriday.atZone(ZoneId.systemDefault()).toInstant()
								.toEpochMilli())))
				.and(JobApplication.JOB_APPLICATION.SUBMIT_TIME.le(
						new Timestamp(currentFriday.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
				.groupBy(JobApplication.JOB_APPLICATION.RECOMMENDER_USER_ID).getSQL());
		return create.select(
					JobApplication.JOB_APPLICATION.RECOMMENDER_USER_ID,
					count(JobApplication.JOB_APPLICATION.ID).as("count")
				)
				.from(JobApplication.JOB_APPLICATION)
				.where(JobApplication.JOB_APPLICATION.RECOMMENDER_USER_ID.in(userIdList))
				.and(JobApplication.JOB_APPLICATION.POSITION_ID.in(positionIdList))
				.and(JobApplication.JOB_APPLICATION.SUBMIT_TIME.gt(
						new Timestamp(lastFriday.atZone(ZoneId.systemDefault()).toInstant()
								.toEpochMilli())))
				.and(JobApplication.JOB_APPLICATION.SUBMIT_TIME.le(
						new Timestamp(currentFriday.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
				.groupBy(JobApplication.JOB_APPLICATION.RECOMMENDER_USER_ID)
				.fetch();
	}
}
