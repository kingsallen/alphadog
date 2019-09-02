package com.moseeker.baseorm.dao.jobdb;

import com.moseeker.baseorm.crud.JooqCrudImpl;
import com.moseeker.baseorm.db.configdb.tables.ConfigSysPointsConfTpl;
import com.moseeker.baseorm.db.jobdb.tables.JobApplication;
import static com.moseeker.baseorm.db.jobdb.tables.JobApplication.JOB_APPLICATION;
import com.moseeker.baseorm.db.jobdb.tables.JobPosition;
import com.moseeker.baseorm.db.jobdb.tables.records.JobApplicationRecord;
import com.moseeker.baseorm.db.userdb.tables.UserUser;
import com.moseeker.baseorm.pojo.ApplicationSaveResultVO;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.AbleFlag;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.application.struct.ApplicationAts;
import com.moseeker.thrift.gen.application.struct.ProcessValidationStruct;
import com.moseeker.thrift.gen.dao.struct.jobdb.JobApplicationDO;
import org.jooq.*;
import org.jooq.impl.TableImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.jooq.impl.DSL.*;

/**
 * 封装申请表基本操作
 *
 * @author jack
 * date : Jan 22, 2017
 * email: wengjianfei@moseeker.com
 */
@Service
public class JobApplicationDao extends JooqCrudImpl<JobApplicationDO, JobApplicationRecord> {

	@Resource(name="cacheClient")
	private RedisClient redisClient;

    public JobApplicationDao() {
        super(JOB_APPLICATION, JobApplicationDO.class);
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


    public List<JobApplicationDO> getApplicationsByApplierAndPosition(List<Integer> positionIds, List<Integer> userIds) {
       if(StringUtils.isEmptyList(positionIds) || StringUtils.isEmptyList(userIds)){
           return new ArrayList<>();
       }
       return create.selectFrom(JOB_APPLICATION)
               .where(JOB_APPLICATION.POSITION_ID.in(positionIds))
               .and(JOB_APPLICATION.APPLIER_ID.in(userIds))
               .fetchInto(JobApplicationDO.class);
    }
    public List<JobApplicationDO> getApplicationsByPosition(List<Integer> positionIds) {
        if(StringUtils.isEmptyList(positionIds)){
            return new ArrayList<>();
        }
        return create.selectFrom(JOB_APPLICATION)
                .where(JOB_APPLICATION.POSITION_ID.in(positionIds))
                .fetchInto(JobApplicationDO.class);
    }

	public List<ProcessValidationStruct> getAuth(List<Integer> appIds,Integer companyId,Integer progressStatus) throws Exception{
		List<ProcessValidationStruct> list=new ArrayList<ProcessValidationStruct>();
		SelectJoinStep<Record11<Integer, Integer, Integer, Integer, Integer, String, Integer, Integer, String, Integer, Integer>> table=create.select(
				JOB_APPLICATION.ID,
				JOB_APPLICATION.COMPANY_ID,
				JOB_APPLICATION.RECOMMENDER_ID,
				JOB_APPLICATION.RECOMMENDER_USER_ID,
				JOB_APPLICATION.APPLIER_ID,
				UserUser.USER_USER.NAME,
				ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.ID ,
				ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.RECRUIT_ORDER,
				JobPosition.JOB_POSITION.TITLE,
                JobPosition.JOB_POSITION.PUBLISHER,
                JOB_APPLICATION.POSITION_ID
				).from(JOB_APPLICATION);
		table.leftJoin(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL)
		.on("jobdb.job_application.app_tpl_id=configdb.config_sys_points_conf_tpl.id");
		table.leftJoin(JobPosition.JOB_POSITION).on("jobdb.job_application.position_id=jobdb.job_position.id");
//			table.leftJoin(UserWxUser.USER_WX_USER).on("jobdb.job_application.recommender_id=userdb.user_wx_user.id");
		table.leftJoin(UserUser.USER_USER).on("jobdb.job_application.applier_id=userdb.user_user.id");
		table.where(JOB_APPLICATION.ID.in(appIds)
				.and(JOB_APPLICATION.COMPANY_ID.eq((int)(companyId))));
		if(progressStatus==13){
			table.where().and(JOB_APPLICATION.APP_TPL_ID.notEqual((int)(4)));
		}else if(progressStatus==99){
			table.where().and(JOB_APPLICATION.APP_TPL_ID.equal((int)(4)));
		}
		Result<Record11<Integer, Integer, Integer, Integer, Integer, String, Integer, Integer, String, Integer, Integer>> result=table.fetch();
		if(result!=null&&result.size()>0){
			ProcessValidationStruct data= null;
			for(Record11<Integer, Integer, Integer, Integer, Integer, String, Integer, Integer, String, Integer, Integer> record:result){
				data=new ProcessValidationStruct();
				data.setCompany_id(record.getValue(JOB_APPLICATION.COMPANY_ID).intValue());
				data.setId(record.getValue(JOB_APPLICATION.ID).intValue());
				data.setRecommender_id(record.getValue(JOB_APPLICATION.RECOMMENDER_ID).intValue());
				data.setRecommender_user_id(record.getValue(JOB_APPLICATION.RECOMMENDER_USER_ID).intValue());
				data.setRecruit_order(record.getValue(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.RECRUIT_ORDER).intValue());
				data.setTemplate_id(record.getValue(ConfigSysPointsConfTpl.CONFIG_SYS_POINTS_CONF_TPL.ID).intValue());
				data.setApplier_id(record.getValue(JOB_APPLICATION.APPLIER_ID).intValue());
				data.setApplier_name(record.getValue(UserUser.USER_USER.NAME));
				data.setPosition_name(record.getValue(JobPosition.JOB_POSITION.TITLE));
				data.setPublisher(record.getValue(JobPosition.JOB_POSITION.PUBLISHER));
				data.setPosition_id(record.getValue(JOB_APPLICATION.POSITION_ID));
				list.add(data);
			}
		}
		return list;
	}
	public List<ApplicationAts> getApplicationByLApId(List<Integer> lists) throws Exception{
		List<ApplicationAts> list=new ArrayList<>();
		SelectConditionStep<Record3<Integer, Integer, Integer>> table =create.select(
				JOB_APPLICATION.COMPANY_ID,
				JOB_APPLICATION.ID,
				JobPosition.JOB_POSITION.PUBLISHER
				).from(JOB_APPLICATION)
				.innerJoin(JobPosition.JOB_POSITION)
				.on("jobdb.job_application.position_id=jobdb.job_position.id")
				.where(JOB_APPLICATION.L_APPLICATION_ID.in(lists));
		Result<Record3<Integer, Integer, Integer>> result=table.fetch();
		if(result!=null&&result.size()>0){
			ApplicationAts ats=null;
			for(Record3<Integer, Integer, Integer> r:result){
				ats=new ApplicationAts();
				ats.setAccount_id(r.getValue(JobPosition.JOB_POSITION.PUBLISHER).intValue());
				ats.setApplication_id(r.getValue(JOB_APPLICATION.ID).intValue());
				ats.setCompany_id(r.getValue(JOB_APPLICATION.COMPANY_ID).intValue());
				list.add(ats);
			}
		}
		return list;
	}

    /** insertIfNotExist 判断是否已存在
     * @param record
     * @return
     */
    @Transactional
	public ApplicationSaveResultVO addIfNotExists(JobApplicationRecord record) {
		// 为防止高并发下的重复申请，先在redis中检验是否申请过，如果没有再去执行sql语句
		long redisFlag = checkRedisApplication(record);
		int result = 0;
		logger.info("addIfNotExisits redisFlag {}", redisFlag);
		if(redisFlag == 1){
			List<Field<?>> changedFieldList = Arrays.stream(record.fields()).filter(f -> record.changed(f) && !f.getName().equals("id")).collect(Collectors.toList());
			String insertSql = " insert into jobdb.job_application ".concat(changedFieldList.stream().map(m -> m.getName()).collect(Collectors.joining(",", " (", ") ")))
					.concat(" select ").concat(Stream.generate(() -> "?").limit(changedFieldList.size()).collect(Collectors.joining(",")))
					.concat(" from dual where not exists ( ")
					.concat(" select id from jobdb.job_application where ")
					.concat(JOB_APPLICATION.DISABLE.getName()).concat(" = 0 and ")
					.concat(JOB_APPLICATION.APPLIER_ID.getName()).concat(" = ").concat(record.getApplierId().toString()).concat(" and ")
					.concat(JOB_APPLICATION.POSITION_ID.getName()).concat(" = ").concat(record.getPositionId().toString())
					.concat(" ) ");
			logger.info("addIfNotExisits job_application sql: {}", insertSql);
			result = create.execute(insertSql, changedFieldList.stream().map(m -> record.getValue(m)).collect(Collectors.toList()).toArray());
		}

		ApplicationSaveResultVO resultVO = new ApplicationSaveResultVO();

        if (result == 0) {
            //logger.info("用户:{}已申请过职位:{}, 无需重复投递", record.getApplierId(), record.getPositionId());
			resultVO.setCreate(false);
        } else {
        	resultVO.setCreate(true);
		}

        Query.QueryBuilder queryBuilder = new Query.QueryBuilder();
        queryBuilder.where(JOB_APPLICATION.DISABLE.getName(), 0)
				.and(JOB_APPLICATION.APPLIER_ID.getName(), record.getApplierId())
        .and(JOB_APPLICATION.POSITION_ID.getName(), record.getPositionId());
        JobApplicationRecord application = getRecord(queryBuilder.buildQuery());
		resultVO.setApplicationId(application.getId());
		resultVO.setPositionId(application.getPositionId());
		resultVO.setApplierId(application.getApplierId());
		resultVO.setSubmitTime(application.getSubmitTime().getTime());

        //如果不是新增申请，那么合并申请来源
		//logger.info("addIfNotExists applicationDO:{}", application);
		//logger.info("addIfNotExists result:{}", result);
		if (result == 0 && record.getOrigin() != null && record.getOrigin() > 0
					&& record.getOrigin().intValue() != application.getOrigin()) {

				//logger.info("addIfNotExists record.origin:{}", record.getOrigin());

				//logger.info("addIfNotExists applicationDO.origin:{}", application.getOrigin());
				int origin = record.getOrigin().intValue() | application.getOrigin();
				//logger.info("addIfNotExists origin:{}", origin);
				create.update(JOB_APPLICATION)
						.set(JOB_APPLICATION.ORIGIN, origin)
                        .set(JOB_APPLICATION.UPDATE_TIME, new Timestamp(System.currentTimeMillis()))
						.where(JOB_APPLICATION.ID.eq(application.getId()))
						.execute();
		}

        return resultVO;
    }

    /**
     * 为防止高并发下的重复申请，先在redis中检验是否申请过，如果没有再去执行sql语句
     * @param  record 申请记录
     * @author  cjm
     * @date  2018/9/28
     * @return  申请过返回0，没申请返回1
     */
	private long checkRedisApplication(JobApplicationRecord record) {
		// 去库里查下，没有的话存入redis
		com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication jobApplication = getByUserIdAndPositionId(record.getApplierId(), record.getPositionId());
		if(jobApplication == null){
            redisClient.setnx(AppId.APPID_ALPHADOG.getValue(), KeyIdentifier.APPLICATION_SINGLETON.toString(),
                    record.getApplierId()+"", record.getPositionId()+"", "1");
            return 1;

		}
		return 0;
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
				JOB_APPLICATION.RECOMMENDER_USER_ID,
				count(JOB_APPLICATION.ID).as("count")
		)
				.from(JOB_APPLICATION)
				.where(JOB_APPLICATION.RECOMMENDER_USER_ID.in(userIdList))
				.and(JOB_APPLICATION.POSITION_ID.in(positionIdList))
				.and(JOB_APPLICATION.SUBMIT_TIME.gt(
						new Timestamp(lastFriday.atZone(ZoneId.systemDefault()).toInstant()
								.toEpochMilli())))
				.and(JOB_APPLICATION.SUBMIT_TIME.le(
						new Timestamp(currentFriday.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
				.groupBy(JOB_APPLICATION.RECOMMENDER_USER_ID).getSQL());
		return create.select(
					JOB_APPLICATION.RECOMMENDER_USER_ID,
					count(JOB_APPLICATION.ID).as("count")
				)
				.from(JOB_APPLICATION)
				.where(JOB_APPLICATION.RECOMMENDER_USER_ID.in(userIdList))
				.and(JOB_APPLICATION.POSITION_ID.in(positionIdList))
				.and(JOB_APPLICATION.SUBMIT_TIME.gt(
						new Timestamp(lastFriday.atZone(ZoneId.systemDefault()).toInstant()
								.toEpochMilli())))
				.and(JOB_APPLICATION.SUBMIT_TIME.le(
						new Timestamp(currentFriday.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())))
				.groupBy(JOB_APPLICATION.RECOMMENDER_USER_ID)
				.fetch();
	}

    public com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication getByUserIdAndPositionId(Integer referenceId, Integer positionId) {
		JobApplicationRecord record = create.selectFrom(JOB_APPLICATION)
				.where(JOB_APPLICATION.APPLIER_ID.eq(referenceId))
				.and(JOB_APPLICATION.POSITION_ID.eq(positionId))
				.orderBy(JOB_APPLICATION.ID.desc())
				.limit(1)
				.fetchOne();
		logger.info("JobApplicationDAO 283 {}",record);
		if (record == null) {
			return null;
		} else {
			return record.into(com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication.class);
		}
    }

	public List<JobApplicationRecord> getByApplierIdAndCompanyId(int userId, int companyId) {

		Condition condition = null;
		if (companyId > 0) {
			condition = JOB_APPLICATION.POSITION_ID
					.in(select(JobPosition.JOB_POSITION.ID)
							.from(JobPosition.JOB_POSITION)
							.where(JobPosition.JOB_POSITION.COMPANY_ID.eq(companyId))
					);
		}
		SelectConditionStep<JobApplicationRecord> selectConditionStep = create
				.selectFrom(JOB_APPLICATION)
				.where(JOB_APPLICATION.APPLIER_ID.eq(userId))
				.and(JOB_APPLICATION.DISABLE.eq(AbleFlag.OLDENABLE.getValue()))
				.and(JOB_APPLICATION.EMAIL_STATUS.eq(AbleFlag.OLDENABLE.getValue()));

		if (condition!= null) {
			selectConditionStep = selectConditionStep.and(condition);
		}

		return selectConditionStep
				.orderBy(JOB_APPLICATION.SUBMIT_TIME.desc())
				.fetch();
	}

    public com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication fetchOneById(int applicationId) {
		JobApplicationRecord record = create.selectFrom(JOB_APPLICATION)
				.where(JOB_APPLICATION.ID.eq(applicationId))
				.fetchOne();
		if (record != null) {
			return record.into(com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication.class);
		} else {
			return null;
		}
    }

	public void deleteById(Integer id) {
		if (id != null) {
			create.deleteFrom(JOB_APPLICATION)
					.where(JOB_APPLICATION.ID.eq(id))
					.execute();
		}
	}

	/**
	 * 修改申请的申请人
	 * @param id 申请编号
	 * @param newApplierId 新申请人
	 * @param positionId 职位名称
	 * @param applierName 新申请人姓名
	 * @param origin 来源
	 * @return 是否修改申请  true 修改成功 false 修改失败
	 */
	public boolean updateIfNotExist(Integer id, Integer positionId, int newApplierId,
									String applierName, int origin) {
		int execute = create.update(JOB_APPLICATION)
				.set(JOB_APPLICATION.APPLIER_ID, newApplierId)
				.set(JOB_APPLICATION.APPLIER_NAME, applierName)
				.where(JOB_APPLICATION.ID.eq(id))
				.andNotExists(
						selectOne()
						.from(
								selectFrom(JOB_APPLICATION)
								.where(JOB_APPLICATION.APPLIER_ID.eq(newApplierId))
								.and(JOB_APPLICATION.ID.eq(positionId))
								.limit(1)
						)
				).execute();
		if (execute == 0) {
			JobApplicationRecord record = create.selectFrom(JOB_APPLICATION)
					.where(JOB_APPLICATION.APPLIER_ID.eq(newApplierId))
					.and(JOB_APPLICATION.POSITION_ID.eq(positionId))
					.limit(1)
					.fetchOne();
			if (record != null) {
				record.setOrigin(record.getOrigin() | origin);
				create.attach(record);
				record.update();
			}
		}
		return execute == 1;
	}

	/**
	 * 根据申请编号集合查找申请记录集合
	 * @param idList 申请编号集合
	 * @return
	 */
    public List<JobApplicationRecord> fetchByIdList(List<Integer> idList) {
		return create
				.selectFrom(JOB_APPLICATION)
				.where(JOB_APPLICATION.ID.in(idList))
				.fetch();
	}

	public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication> getAppdataByApplierIdListAndCompanyId(Set<Integer> idList, int companyId) {
		List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication> list=create.select(JOB_APPLICATION.POSITION_ID, JOB_APPLICATION.APPLIER_ID).from(JOB_APPLICATION)
				.where(JOB_APPLICATION.APPLIER_ID.in(idList)).and(JOB_APPLICATION.COMPANY_ID.eq(companyId))
				.fetchInto(com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication.class);
		return list;
	}



	public List<com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication> fetchByApplierId(List<Integer> userIds, int companyId) {
		Result<JobApplicationRecord> result = create
				.selectFrom(JOB_APPLICATION)
				.where(JOB_APPLICATION.APPLIER_ID.in(userIds))
				.and(JOB_APPLICATION.COMPANY_ID.eq(companyId))
				.fetch();
		if (result != null && result.size() > 0) {
			return result.into(com.moseeker.baseorm.db.jobdb.tables.pojos.JobApplication.class);
		} else {
			return new ArrayList<>();
		}
	}

	public int validateAppid(int appId, int companyId) {
		return create.selectCount().from(JobApplication.JOB_APPLICATION)
				.where(JobApplication.JOB_APPLICATION.COMPANY_ID.eq(companyId))
				.and(JobApplication.JOB_APPLICATION.ID.eq(appId))
				.fetchOneInto(Integer.class);
	}

    public List<JobApplicationDO> getApplyByRecomUserIdAndCompanyId(int userId, int companyId) {
    	return create.selectFrom(JOB_APPLICATION)
				.where(JOB_APPLICATION.RECOMMENDER_USER_ID.eq(userId))
				.and(JOB_APPLICATION.COMPANY_ID.eq(companyId))
				.and(JOB_APPLICATION.APPLY_TYPE.eq(0)
						.or(JOB_APPLICATION.APPLY_TYPE.eq(1)
								.and(JOB_APPLICATION.EMAIL_STATUS.eq(0))))
				.orderBy(JOB_APPLICATION.UPDATE_TIME.desc())
				.fetchInto(JobApplicationDO.class);
    }

	public List<JobApplicationDO> getApplyByRecomUserIdAndCompanyId(int userId, int companyId, List<Integer> progress) {
    	return create.selectFrom(JOB_APPLICATION)
				.where(JOB_APPLICATION.RECOMMENDER_USER_ID.eq(userId))
				.and(JOB_APPLICATION.COMPANY_ID.eq(companyId))
				.and(JOB_APPLICATION.APP_TPL_ID.in(progress))
				.and(JOB_APPLICATION.APPLY_TYPE.eq(0)
						.or(JOB_APPLICATION.APPLY_TYPE.eq(1)
								.and(JOB_APPLICATION.EMAIL_STATUS.eq(0))))
				.orderBy(JOB_APPLICATION.UPDATE_TIME.desc())
				.fetchInto(JobApplicationDO.class);
	}

	public List<JobApplicationDO> getNewAtsApplyByRecomUserIdAndCompanyId(int userId, int companyId) {
		List<JobApplicationDO> applicationDOS = create.fetch("select t1.* from (select * from jobdb.job_application a where a.recommender_user_id=? and a.company_id=? " +
				"and (a.apply_type=0 or (a.apply_type=1 and a.email_status=0))) t1 " +
				"join jobdb.job_application_ats_process t2 on t1.id = t2.app_id", userId, companyId).into(JobApplicationDO.class);
		return applicationDOS;
	}

	public List<JobApplicationDO> getApplyByRecomUserIdAndCompanyIdAndAppliers(int userId, int companyId, List<Integer> applierIds) {
		return create.selectFrom(JOB_APPLICATION)
				.where(JOB_APPLICATION.RECOMMENDER_USER_ID.eq(userId))
				.and(JOB_APPLICATION.COMPANY_ID.eq(companyId))
				.and(JOB_APPLICATION.APPLIER_ID.in(applierIds))
				.and(JOB_APPLICATION.APPLY_TYPE.eq(0)
						.or(JOB_APPLICATION.APPLY_TYPE.eq(1)
								.and(JOB_APPLICATION.EMAIL_STATUS.eq(0))))
				.orderBy(JOB_APPLICATION.UPDATE_TIME.desc())
				.fetchInto(JobApplicationDO.class);
	}

	public List<JobApplicationDO> getApplyByRecomUserIdAndCompanyId(int userId, int companyId, List<Integer> applierIds, List<Integer> progress) {
		return create.selectFrom(JOB_APPLICATION)
				.where(JOB_APPLICATION.RECOMMENDER_USER_ID.eq(userId))
				.and(JOB_APPLICATION.COMPANY_ID.eq(companyId))
				.and(JOB_APPLICATION.APP_TPL_ID.in(progress))
				.and(JOB_APPLICATION.APPLIER_ID.in(applierIds))
				.and(JOB_APPLICATION.APPLY_TYPE.eq(0)
						.or(JOB_APPLICATION.APPLY_TYPE.eq(1)
								.and(JOB_APPLICATION.EMAIL_STATUS.eq(0))))
				.orderBy(JOB_APPLICATION.UPDATE_TIME.desc())
				.fetchInto(JobApplicationDO.class);
	}

	public List<JobApplicationDO> getNewAtsApplyByRecomUserIdAndCompanyId(int userId, int companyId, List<Integer> queryNameIds) {
		String queryStr = "";
		if(queryNameIds != null && queryNameIds.size()>0){
			queryStr = " and applier_id in ("+String.join(",",queryNameIds.stream().map(a->a.toString()).collect(Collectors.toSet()))+")";
		}
		List<JobApplicationDO> applicationDOS = create.fetch("select t1.* from (select * from jobdb.job_application a where a.recommender_user_id=? and a.company_id=? " +
				queryStr+" and (a.apply_type=0 or (a.apply_type=1 and a.email_status=0))) t1 " +
				"join jobdb.job_application_ats_process t2 on t1.id = t2.app_id", userId, companyId).into(JobApplicationDO.class);
		return applicationDOS;
	}

    public List<JobApplicationDO> getApplyByaApplierAndPositionIds(List<Integer> positionIds, List<Integer> applierIds) {
        return create.selectFrom(JOB_APPLICATION)
                .where(JOB_APPLICATION.POSITION_ID.in(positionIds))
                .and(JOB_APPLICATION.APPLIER_ID.in(applierIds))
                .and(JOB_APPLICATION.APPLY_TYPE.eq(0)
                        .or(JOB_APPLICATION.APPLY_TYPE.eq(1)
                        .and(JOB_APPLICATION.EMAIL_STATUS.eq(0))))
                .fetchInto(JobApplicationDO.class);
    }

	 public List<Integer> getApplierIdListByIdList(List<Integer> appIdlist){
		 List<Integer> result= create.selectDistinct(JOB_APPLICATION.APPLIER_ID).from(JOB_APPLICATION).where(JOB_APPLICATION.ID.in(appIdlist)).fetchInto(Integer.class);
		 return result;
	 }

}
