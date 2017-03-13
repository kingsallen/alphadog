package com.moseeker.position.service.fundationbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.moseeker.common.annotation.iface.CounterIface;
import com.moseeker.common.constants.AccountSync;
import com.moseeker.common.constants.AppId;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.constants.KeyIdentifier;
import com.moseeker.common.constants.PositionSync;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.common.providerutils.bzutils.JOOQBaseServiceImpl;
import com.moseeker.common.redis.RedisClientFactory;
import com.moseeker.common.util.BeanUtils;
import com.moseeker.common.util.DateUtils;
import com.moseeker.db.dictdb.tables.records.DictCityRecord;
import com.moseeker.db.hrdb.tables.records.HrCompanyAccountRecord;
import com.moseeker.db.jobdb.tables.records.JobCustomRecord;
import com.moseeker.db.jobdb.tables.records.JobOccupationRecord;
import com.moseeker.db.jobdb.tables.records.JobPositionExtRecord;
import com.moseeker.db.jobdb.tables.records.JobPositionRecord;
import com.moseeker.position.dao.DictConstantDao;
import com.moseeker.position.dao.JobCustomDao;
import com.moseeker.position.dao.JobOccupationDao;
import com.moseeker.position.dao.JobPositionDao;
import com.moseeker.position.dao.JobPositonExtDao;
import com.moseeker.position.dao.PositionDao;
import com.moseeker.position.pojo.DictConstantPojo;
import com.moseeker.position.pojo.JobPositionPojo;
import com.moseeker.position.pojo.PositionForSynchronizationPojo;
import com.moseeker.position.pojo.RecommendedPositonPojo;
import com.moseeker.position.service.position.PositionChangeUtil;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.apps.positionbs.struct.ThirdPartyPosition;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.CompanyDao;
import com.moseeker.thrift.gen.dao.service.HrDBDao;
import com.moseeker.thrift.gen.dao.struct.HrTeamStruct;
import com.moseeker.thrift.gen.dao.struct.ThirdPartAccountData;
import com.moseeker.thrift.gen.dao.struct.ThirdPartyPositionData;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import com.mysql.jdbc.StringUtils;

@Service
public class PositionService extends JOOQBaseServiceImpl<Position, JobPositionRecord> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PositionDao dao;

	@Autowired
	private JobPositionDao jobPositionDao;

	@Autowired
	private DictConstantDao dictConstantDao;

	@Autowired
	private JobCustomDao jobCustomDao;

	@Autowired
	private JobPositonExtDao jobPositonExtDao;
	
	@Autowired
	private JobOccupationDao jobOccupationDao;
	
	

	com.moseeker.thrift.gen.dao.service.PositionDao.Iface positionDaoService = ServiceManager.SERVICEMANAGER
			.getService(com.moseeker.thrift.gen.dao.service.PositionDao.Iface.class);

	CompanyDao.Iface CompanyDao = ServiceManager.SERVICEMANAGER.getService(CompanyDao.Iface.class);
	//获取hrdb库中的内容
	HrDBDao.Iface hrDBDao=ServiceManager.SERVICEMANAGER.getService(HrDBDao.Iface.class);

	@Override
	protected void initDao() {
		super.dao = this.dao;
	}

	@Override
	protected Position DBToStruct(JobPositionRecord r) {
		return (Position) BeanUtils.DBToStruct(Position.class, r);
	}

	@Override
	protected JobPositionRecord structToDB(Position p) {
		return (JobPositionRecord) BeanUtils.structToDB(p, JobPositionRecord.class);
	}

	/**
	 * 获取推荐职位
	 * <p>
	 * </p>
	 *
	 * @param pid
	 * @return
	 */
	@CounterIface
	public Response getRecommendedPositions(int pid) {

		List<RecommendedPositonPojo> recommendPositons = this.dao.getRecommendedPositions(pid);

		return ResponseUtils.successWithoutStringify(JSON.toJSONString(recommendPositons, new ValueFilter() {
			@Override
			public Object process(Object object, String name, Object value) {
				if (value == null) {
					return "";
				}
				return value;
			}
		}));

	}

	@CounterIface
	public Response verifyCustomize(int positionId) throws TException {
		try {
			JobPositionRecord positionRecord = jobPositionDao.getPositionById(positionId);

			if (positionRecord == null) {
				return ResponseUtils.fail(ConstantErrorCodeMessage.PROFILE_POSITION_NOTEXIST);
			}
			if (positionRecord.getAppCvConfigId() != null && positionRecord.getAppCvConfigId().intValue() > 0) {
				return ResponseUtils.success(true);
			} else {
				return ResponseUtils.success(false);
			}
		} catch (Exception e) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}

	/**
	 * 根据职位Id获取当前职位信息
	 *
	 * @param positionId
	 * @return
	 * @throws TException
	 */
	@CounterIface
	public Response getPositionById(int positionId) throws TException {

		try {
			// 必填项校验
			if (positionId == 0) {
				return ResponseUtils
						.fail(ConstantErrorCodeMessage.PROGRAM_VALIDATE_REQUIRED.replace("{0}", "position_id"));
			}

			// NullPoint check
			JobPositionRecord jobPositionRecord = jobPositionDao.getPositionById(positionId);
			if (jobPositionRecord == null) {
 				return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_DATA_EMPTY);
			}

			JobPositionPojo jobPositionPojo = jobPositionDao.getPosition(positionId);
			int team_id=jobPositionPojo.team_id;
			CommonQuery query=new CommonQuery();
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", team_id+"");
			map.put("disable", "0");
			query.setEqualFilter(map);
			Response result=hrDBDao.getHrTeam(query);
			if(result.getStatus()==0&&!StringUtils.isNullOrEmpty(result.getData())){
				HrTeamStruct team=JSONObject.toJavaObject(JSONObject.parseObject(result.getData()), HrTeamStruct.class);
				jobPositionPojo.department=team.getName();
				jobPositionPojo.team_name=team.getName();
			}
			
			/** 子公司Id设置 **/
			if (jobPositionPojo.publisher != 0) {
				HrCompanyAccountRecord hrCompanyAccountRecord = jobPositionDao
						.getHrCompanyAccountByPublisher(jobPositionPojo.publisher);
				// 子公司ID>0
				if (hrCompanyAccountRecord != null && hrCompanyAccountRecord.getCompanyId() > 0) {
					jobPositionPojo.publisher_company_id = hrCompanyAccountRecord.getCompanyId();
				}
			}

			/** 常量转换 **/
			// 性别
			if (jobPositionPojo.gender < 2) {
				jobPositionPojo.gender_name = getDictConstantJson(2102, jobPositionPojo.gender);
			}

			// 学历
			if (jobPositionPojo.degree > 0) {
				jobPositionPojo.degree_name = getDictConstantJson(2101, jobPositionPojo.degree);
			}

			// 工作性质
			jobPositionPojo.employment_type_name = getDictConstantJson(2103, jobPositionPojo.employment_type);

			// 招聘类型
			jobPositionPojo.candidate_source_name = getDictConstantJson(2104, jobPositionPojo.candidate_source);

			// 自定义字段 与 自定义职位职能
			JobPositionExtRecord jobPositionExtRecord = getJobPositionExtRecord(positionId);
			if (jobPositionExtRecord != null ) {
				if(jobPositionExtRecord.getJobCustomId() > 0){
					JobCustomRecord jobCustomRecord = jobCustomDao
							.getJobCustomRecord(jobPositionExtRecord.getJobCustomId());
					if (jobCustomRecord != null && !"".equals(jobCustomRecord.getName())) {
						jobPositionPojo.custom = jobCustomRecord.getName();
					}
				}
				if(jobPositionExtRecord.getJobCustomId() > 0){
					JobOccupationRecord jobOccupationRecord = 
							jobOccupationDao.getJobOccupationRecord(jobPositionExtRecord.getJobOccupationId());
					if(jobOccupationRecord != null && com.moseeker.common.util.StringUtils.isNotNullOrEmpty(jobOccupationRecord.getName())) {
						jobPositionPojo.occupation = jobOccupationRecord.getName();
					}
				}
				
				
			}

			// 修改更新时间
			jobPositionPojo.publish_date_view = DateUtils.dateToPattern(jobPositionPojo.publish_date,
					DateUtils.SHOT_TIME);
			jobPositionPojo.update_time_view = DateUtils.dateToPattern(jobPositionPojo.update_time,
					DateUtils.SHOT_TIME);

			// 省份
			List<DictCityRecord> provinces = dao.getProvincesByPositionID(positionId);
			if (provinces != null && provinces.size() > 0) {
				StringBuffer sb = new StringBuffer();
				provinces.forEach(province -> {
					sb.append(province.getName() + ",");
				});
				sb.deleteCharAt(sb.length() - 1);
				jobPositionPojo.province = sb.toString();
			}

			return ResponseUtils.success(jobPositionPojo);
		} catch (Exception e) {
			return ResponseUtils.fail(ConstantErrorCodeMessage.PROGRAM_EXCEPTION);
		}
	}

	/**
	 * 获取常量字典一条记录
	 *
	 * @param parentCode
	 * @param code
	 * @return
	 * @throws Exception
	 */
	private String getDictConstantJson(Integer parentCode, Integer code) throws Exception {
		DictConstantPojo dictConstantPojo = dictConstantDao.getDictConstantJson(parentCode, code);
		return dictConstantPojo != null ? dictConstantPojo.getName() : "";
	}

	private JobPositionExtRecord getJobPositionExtRecord(int positionId) {
		return jobPositonExtDao.getJobPositonExtRecordByPositionId(positionId);
	}

	/**
	 * 校验同步职位必填信息
	 * 
	 * @param position
	 * @return
	 */
	public boolean verifySynchronizePosition(PositionForSynchronizationPojo position) {
		return false;
	}

	/**
	 * 转成第三方渠道职位
	 * 
	 * @param forms
	 * @param position
	 * @return
	 */
	public List<ThirdPartyPositionForSynchronization> changeToThirdPartyPosition(List<ThirdPartyPosition> forms,
			Position position) {
		List<ThirdPartyPositionForSynchronization> positions = new ArrayList<>();
		if (forms != null && forms.size() > 0 && position != null && position.getId() > 0) {
			forms.forEach(form -> {
				ThirdPartyPositionForSynchronization p = PositionChangeUtil.changeToThirdPartyPosition(form, position);
				positions.add(p);
			});
		}
		return positions;
	}

	/**
	 * 该职位是否可以刷新
	 * @param positionId 职位编号
	 * @param channel 渠道编号
	 * @return
	 */
	public boolean ifAllowRefresh(int positionId, int channel) {
		boolean permission = false;
		try {
			logger.info("ifAllowRefresh");
			QueryUtil findPositionById = new QueryUtil();
			findPositionById.addEqualFilter("id", String.valueOf(positionId));
			logger.info("search position");
			Position position = positionDaoService.getPosition(findPositionById);
			logger.info("position:"+JSON.toJSONString(position));
			if (position.getId() > 0) {
				QueryUtil findThirdPartyAccount = new QueryUtil();
				findThirdPartyAccount.addEqualFilter("company_id", String.valueOf(position.getCompany_id()));
				findThirdPartyAccount.addEqualFilter("channel", String.valueOf(channel));
				
				logger.info("search company");
				ThirdPartAccountData account = CompanyDao.getThirdPartyAccount(findThirdPartyAccount);
				logger.info("company:"+JSON.toJSONString(account));

				QueryUtil findThirdPartyPosition = new QueryUtil();
				findThirdPartyPosition.addEqualFilter("position_id", String.valueOf(positionId));
				findThirdPartyPosition.addEqualFilter("channel", String.valueOf(channel));
				
				logger.info("search thirdparyposition");
				ThirdPartyPositionData p = positionDaoService.getThirdPartyPosition(positionId, channel);
				logger.info("thirdparyposition"+JSON.toJSONString(p));
				if (account != null && account.getBinding() == AccountSync.bound.getValue() && p.getId() > 0
						&& p.getIs_synchronization() == PositionSync.bound.getValue()) {
					logger.info("data allow");
					String str = RedisClientFactory.getCacheClient().get(AppId.APPID_ALPHADOG.getValue(),
							KeyIdentifier.THIRD_PARTY_POSITION_REFRESH.toString(), String.valueOf(positionId), String.valueOf(channel));
					if(StringUtils.isNullOrEmpty(str)) {
						logger.info("cache allow");
						permission = true;
					}
				}
			}
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			// do nothing
		}
		return permission;
	}

	/**
	 * 创建刷新职位数据
	 * @param positionId 职位编号
	 * @param channel 渠道编号
	 * @return
	 */
	public ThirdPartyPositionForSynchronizationWithAccount createRefreshPosition(int positionId, int channel) {
		
		ThirdPartyPositionForSynchronizationWithAccount account = new ThirdPartyPositionForSynchronizationWithAccount();
		try {
			ThirdPartyPosition form = new ThirdPartyPosition();
			QueryUtil findPosition = new QueryUtil();
			findPosition.addEqualFilter("id", String.valueOf(positionId));
			Position position = positionDaoService.getPosition(findPosition);
			
			ThirdPartyPositionData thirdPartyPosition = positionDaoService.getThirdPartyPosition(positionId, channel);
			
			QueryUtil findAccount = new QueryUtil();
			findAccount.addEqualFilter("company_id", String.valueOf(position.getCompany_id()));
			findAccount.addEqualFilter("channel", String.valueOf(channel));
			ThirdPartAccountData accountData = CompanyDao.getThirdPartyAccount(findAccount);
			account.setUser_name(accountData.getUsername());
			account.setMember_name(accountData.getMembername());
			account.setPassword(accountData.getPassword());
			account.setChannel(String.valueOf(channel));
			account.setPosition_id(String.valueOf(positionId));
			
			form.setChannel((byte)channel);
			if(position.getId() > 0 && thirdPartyPosition.getId() > 0) {
				ThirdPartyPositionForSynchronization p = PositionChangeUtil.changeToThirdPartyPosition(form, position);
				p.setJob_id(thirdPartyPosition.getThird_part_position_id());
				account.setPosition_info(p);
			}
		} catch (TException e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		} finally {
			//do nothing
		}
		return account;
	}

	public JobOccupationDao getJobOccupationDao() {
		return jobOccupationDao;
	}

	public void setJobOccupationDao(JobOccupationDao jobOccupationDao) {
		this.jobOccupationDao = jobOccupationDao;
	}

	public List<ThirdPartyPositionData> getThirdPartyPositions(CommonQuery query) {
		List<ThirdPartyPositionData> datas = new ArrayList<>();
		try {
//			if(query.getEqualFilter() != null) {
//				query.getEqualFilter().put("channel", "1");
//			} else {
//				Map<String, String> equalFilter = new HashMap<>();
//				equalFilter.put("channel", "1");
//				query.setEqualFilter(equalFilter);
//			}
			datas = positionDaoService.getPositionThirdPartyPositions(query);
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			//do nothing
		}
		return datas;
	}
}