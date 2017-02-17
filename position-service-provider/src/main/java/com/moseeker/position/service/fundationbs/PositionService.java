package com.moseeker.position.service.fundationbs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import com.moseeker.thrift.gen.company.struct.Hrcompany;
import com.moseeker.thrift.gen.dao.service.CompanyDao;
import com.moseeker.thrift.gen.dao.service.HrDBDao;
import com.moseeker.thrift.gen.dao.service.UserHrAccountDao;
import com.moseeker.thrift.gen.dao.struct.*;
import com.moseeker.thrift.gen.dao.struct.HrHbConfigDO;
import com.moseeker.thrift.gen.position.struct.Position;
import com.moseeker.thrift.gen.position.struct.RpExtInfo;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronization;
import com.moseeker.thrift.gen.position.struct.ThirdPartyPositionForSynchronizationWithAccount;
import com.moseeker.thrift.gen.position.struct.WechatPositionListData;
import com.moseeker.thrift.gen.position.struct.WechatPositionListQuery;
import com.moseeker.thrift.gen.position.struct.WechatRpPositionListData;
import com.moseeker.thrift.gen.position.struct.WechatShareData;
import com.moseeker.thrift.gen.searchengine.service.SearchengineServices;
import com.mysql.jdbc.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.round;
import static java.lang.Math.toIntExact;

@Service
public class PositionService extends JOOQBaseServiceImpl<Position, JobPositionRecord> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

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

	private UserHrAccountDao.Iface hrAccountDao = ServiceManager.SERVICEMANAGER.getService(UserHrAccountDao.Iface.class);

	private com.moseeker.thrift.gen.dao.service.PositionDao.Iface positionDaoService = ServiceManager.SERVICEMANAGER.getService(com.moseeker.thrift.gen.dao.service.PositionDao.Iface.class);

	private CompanyDao.Iface companyDao = ServiceManager.SERVICEMANAGER.getService(CompanyDao.Iface.class);

	private SearchengineServices.Iface searchEngineService = ServiceManager.SERVICEMANAGER.getService(SearchengineServices.Iface.class);
	
	private HrDBDao.Iface hrDao = ServiceManager.SERVICEMANAGER.getService(HrDBDao.Iface.class);

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
	 * @param pid 当前职位 id
	 * @return 推荐职位列表
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
			if (positionRecord.getAppCvConfigId() != null && positionRecord.getAppCvConfigId() > 0) {
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
	 * @param positionId 当前职位 id
	 * @return 职位信息
	 * @throws TException TException
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

			//子公司Id设置
			if (jobPositionPojo.publisher != 0) {
				HrCompanyAccountRecord hrCompanyAccountRecord = jobPositionDao
						.getHrCompanyAccountByPublisher(jobPositionPojo.publisher);
				// 子公司ID>0
				if (hrCompanyAccountRecord != null && hrCompanyAccountRecord.getCompanyId() > 0) {
					jobPositionPojo.publisher_company_id = hrCompanyAccountRecord.getCompanyId();
				}
			}

			// 常量转换
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
					sb.append(province.getName()).append(",");
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
	 * @return bool
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
				ThirdPartAccountData account = companyDao.getThirdPartyAccount(findThirdPartyAccount);
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
			ThirdPartAccountData accountData = companyDao.getThirdPartyAccount(findAccount);
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

	/**
	 * 获取微信端职位列表
	 * @param query 查询条件
	 * @return 微信端职位列表信息
	 */
	public List<WechatPositionListData> getPositionList(WechatPositionListQuery query) {

		List<WechatPositionListData> dataList = new ArrayList<>();

		try {
			String childCompanyId = "";
			String companyId = "";

			if (query.isSetDid() && query.getCompany_id() != query.getDid()) {
				childCompanyId = String.valueOf(query.getDid());
			}
			else {
				QueryUtil qu = new QueryUtil();
				qu.addEqualFilter("parent_id", String.valueOf(query.getCompany_id()));
				List<Hrcompany> companies = companyDao.getCompanies(qu);

				List<Integer> cIds = new ArrayList<>();
				if (companies.size() > 0) {
					cIds = companies.stream().map(Hrcompany::getId).collect(Collectors.toList());
				}
				cIds.add(query.getCompany_id());
				companyId = org.apache.commons.lang.StringUtils.join(cIds.toArray(), ",");
			}

			//获取 pid list
			Response ret = searchEngineService.query(
					query.getKeywords(),
					query.getCities(),
					query.getIndustries(),
					query.getOccupations(),
					query.getScale(),
					query.getEmployment_type(),
					query.getCandidate_source(),
					query.getExperience(),
					query.getDegree(),
					query.getSalary(),
					companyId,
					query.getPage_from(),
					query.getPage_size(),
					childCompanyId,
					query.getDepartment(),
					query.isOrder_by_priority(),
					query.getCustom());

			if (ret.getStatus() == 0 && !StringUtils.isNullOrEmpty(ret.getData())) {

				// 通过 pid 列表查询 position 信息
                JSONObject jobj = JSON.parseObject(ret.getData());

                JSONArray pidsJson = jobj.getJSONArray("jd_id_list");

                ArrayList<Integer> pids = new ArrayList<Integer>();
                if (pidsJson != null) {
                    int len = pidsJson.size();
                    for (int i =0; i<len;i++) {
                         pids.add(pidsJson.getInteger(i));
                    }
                }


				QueryUtil q = new QueryUtil();
				q.addEqualFilter("id", "["+ org.apache.commons.lang.StringUtils.join(pids.toArray(), ",") + "]");
				q.setSortby("priority");
				q.setOrder("asc");
				List<JobPositionRecord> jobRecords = jobPositionDao.getResources(q);

				// 内容拼装和返回
				for (JobPositionRecord jr : jobRecords) {
					WechatPositionListData e = new WechatPositionListData();

					e.setTitle(jr.getTitle());
					e.setId(jr.getId());
					e.setSalary_top(jr.getSalaryTop());
					e.setSalary_bottom(jr.getSalaryBottom());
					e.setPublish_date(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(jr.getUpdateTime()));
					e.setDepartment(jr.getDepartment());
					e.setVisitnum(jr.getVisitnum());
					e.setIn_hb(jr.getHbStatus() > 0);
					e.setCount(jr.getCount());
					e.setCity(jr.getCity());

					dataList.add(e);
				}

				// 获取公司信息，拼装 company abbr, logo 等信息
				final Hrcompany companyInfo;

				QueryUtil hrm = new QueryUtil();
				if (query.getDid() == 0) {
                    hrm.addEqualFilter("id", String.valueOf(query.getCompany_id()));
                }
                else {
                    hrm.addEqualFilter("id", String.valueOf(query.getDid()));
                }
				Hrcompany mainCompanyInfo = companyDao.getCompany(hrm);


				if (query.isSetDid() && query.getDid() == query.getCompany_id()) {
					// 校验 did 是不是正确的子公司 id 则提前返回空列表

					QueryUtil hrd = new QueryUtil();
					hrd.addEqualFilter("id", String.valueOf(query.getDid()));
					Hrcompany subCompanyInfo = companyDao.getCompany(hrd);

					if (subCompanyInfo.getParent_id() == mainCompanyInfo.getId()) {
						companyInfo = subCompanyInfo;
					}
					else {
						// 错误的 did， 直接返回
						return new ArrayList<>();
					}
				}
				else {
					companyInfo = mainCompanyInfo;
				}

				//拼装 company 相关内容
				dataList = dataList.stream().map(s -> {
					s.setCompany_abbr(companyInfo.getAbbreviation());
					s.setCompany_logo(companyInfo.getLogo());
					s.setCompany_name(companyInfo.getName());
					return s;
				}).collect(Collectors.toList());
			}
			else {
				return new ArrayList<>();
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new ArrayList<>();
		}
		finally {
			// do nothing
		}
		return dataList;
	}
	
	/**
	 * 获得红包活动的分享信息
	 * @param hb_config_id 红包活动id
	 * @return 红包活动分享信息
	 */
	public WechatShareData getShareInfo(int hb_config_id) {
		WechatShareData result = new WechatShareData();
		
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(hb_config_id));
		try {
			HrHbConfigDO hbConfig = this.hrDao.getHbConfig(qu);
			result.setCover(hbConfig.getShare_img());
			result.setTitle(hbConfig.getShare_title());
			result.setDescription(hbConfig.getShare_desc());
			
		} catch (TException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 返回指定 pid 的红包信息
	 * @param pids pids
	 * @return pids 对应职位红包活动的额外信息
	 */
	public List<RpExtInfo> getPositionListRpExt(List<Integer> pids) {
		List<RpExtInfo> result = new ArrayList<>();

		try {
			// 获取 company_id
			int company_id = 0;
			if (pids.size() > 0) {
				QueryUtil qu = new QueryUtil();
				qu.addEqualFilter("id", String.valueOf(pids.get(0)));
				Position p = positionDaoService.getPosition(qu);
				company_id = p.getCompany_id();
			}

			// 获取正在运行的转发类红包活动集合
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("status", "3"); //正在运行
			qu.addEqualFilter("company_id", String.valueOf(company_id));
			qu.addEqualFilter("type", "[2,3]"); //转发类职位
			List<HrHbConfigDO> hbConfigs = hrDao.getHbConfigs(qu);
			List<Integer> hbConfgIds = hbConfigs.stream().map(HrHbConfigDO::getId).collect(Collectors.toList());
			String allHbConfigIdsFilterString = "["+ org.apache.commons.lang.StringUtils.join(hbConfgIds.toArray(), ",") + "]";

			for (Integer pid : pids) {
				//对于每个 pid，先获取 position
				RpExtInfo rpExtInfo = new RpExtInfo();

				qu = new QueryUtil();
				qu.addEqualFilter("id", String.valueOf(pid));
				Position p = this.positionDaoService.getPosition(qu);

				if (p.getHb_status() == 1 || p.getHb_status() == 2) {
					// 该职位参与了一个红包活动

					//获取 binding 记录
					qu = new QueryUtil();
					qu.addEqualFilter("position_id", String.valueOf(p.getId()));
					qu.addEqualFilter("hb_config_id", allHbConfigIdsFilterString);

					List<HrHbPositionBindingDO> bindings = hrDao.getHbPositionBindings(qu);

					// 确认 binding 只有一个，获取binding 对应的红包活动信息
					HrHbConfigDO hbConfig = hbConfigs.stream().filter(c -> c.getId() == bindings.get(0).getHb_config_id())
							.findFirst().orElseGet(null);

					if (hbConfig != null) {
						// 更新红包发送对象
						rpExtInfo.setEmployee_only(hbConfig.getTarget() == 0);
					}
					else {
						logger.warn("查询不到对应的 hbConfig");
						rpExtInfo.setEmployee_only(false);
					}

					// 根据 binding 获取 hb_items 记录
					qu = new QueryUtil();
					qu.addEqualFilter("binding_id", String.valueOf(bindings.get(0).getId()));
					qu.addEqualFilter("wxuser_id", "0"); // 还未发出的
					List<HrHbItemsDO> remainItems = hrDao.getHbItems(qu);

					Double remain = remainItems.stream().mapToDouble(HrHbItemsDO::getAmount).sum();
					Integer remainInt = toIntExact(round(remain));
					if (remainInt < 0) {
						remainInt = 0;
					}

					rpExtInfo.setPid(p.getId());
					rpExtInfo.setRemain(remainInt);

					result.add(rpExtInfo);

				}
				else if (p.getHb_status() == 3) {
					// 该职位参与了两个红包活动

					// 获取 binding 记录
					qu = new QueryUtil();
					qu.addEqualFilter("position_id", String.valueOf(p.getId()));
					qu.addEqualFilter("hb_config_id", allHbConfigIdsFilterString);

					List<HrHbPositionBindingDO> bindings = hrDao.getHbPositionBindings(qu);
					//获取binding ids
					List<Integer> bindingIds = bindings.stream().map(HrHbPositionBindingDO::getId).collect(Collectors.toList());
					//获取binding 所对应的红包活动 id
					List<Integer> hbConfigIds = bindings.stream().map(HrHbPositionBindingDO::getHb_config_id).collect(Collectors.toList());

					// 得到对应的红包活动 pojo （2个）
					List<HrHbConfigDO> configs = hbConfigs.stream().filter(s -> hbConfigIds.contains(s.getId())).collect(Collectors.toList());

					// 如果任意一个对象是非员工，设置成 false
					for (HrHbConfigDO config : configs) {
						if (config.target > 0) {
							rpExtInfo.setEmployee_only(false);
							break;
						}
					}
					// 否则对象设置成 true
					if (!rpExtInfo.isSetEmployee_only()) {
						rpExtInfo.setEmployee_only(true);
					}

					String bindingIdsFilterString = "["+ org.apache.commons.lang.StringUtils.join(bindingIds.toArray(), ",") + "]";
                    // 根据 binding 获取 hb_items 记录
					qu = new QueryUtil();
					qu.addEqualFilter("binding_id", String.valueOf(bindingIdsFilterString));
					qu.addEqualFilter("wxuser_id", "0"); // 未发出
					List<HrHbItemsDO> remainItems = hrDao.getHbItems(qu);

					Double remain = remainItems.stream().mapToDouble(HrHbItemsDO::getAmount).sum();
					Integer remainInt = toIntExact(round(remain));
					if (remainInt < 0) {
						remainInt = 0;
					}

					rpExtInfo.setPid(p.getId());
					rpExtInfo.setRemain(remainInt);
					result.add(rpExtInfo);

				}
				else {
					// 如果该职位已经不属于任何红包活动，这不做任何操作
                    logger.warn("pid: " + p.getId() + " 已经不属于任何红包活动");
				}
			}
		} catch (TException e) {
			e.printStackTrace();
			return result;
		} finally {

		}
		return result;
	}

	/**
	 * 根据 hbConfigId 返回职位列表
	 * @param hbConfigId 红包活动id
	 * @return 红包职位列表
	 */
	public List<WechatRpPositionListData> getRpPositionList(int hbConfigId) {
		List<WechatRpPositionListData> result = new ArrayList<>();
		try {
			QueryUtil qu = new QueryUtil();
			qu.addEqualFilter("hb_config_id", String.valueOf(hbConfigId));
			List<HrHbPositionBindingDO> bindings = hrDao.getHbPositionBindings(qu);
			List<Integer> pids = bindings.stream().map(HrHbPositionBindingDO::getPosition_id).collect(Collectors.toList());
			String pidFilter = "["+ org.apache.commons.lang.StringUtils.join(pids.toArray(), ",") + "]";

			QueryUtil q = new QueryUtil();
			q.addEqualFilter("id", pidFilter);
			q.setSortby("priority");
			q.setOrder("asc");
			List<JobPositionRecord> jobRecords = jobPositionDao.getResources(q);

            // filter 出已经发完红包的职位
            jobRecords = jobRecords.stream().filter(p -> p.getHbStatus() > 0).collect(Collectors.toList());

			// 拼装职位信息
			for (JobPositionRecord jr : jobRecords) {
				WechatRpPositionListData e = new WechatRpPositionListData();
				e.setTitle(jr.getTitle());
				e.setId(jr.getId());
				e.setSalary_top(jr.getSalaryTop());
				e.setSalary_bottom(jr.getSalaryBottom());
				e.setPublish_date(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(jr.getUpdateTime()));
				e.setDepartment(jr.getDepartment());
				e.setVisitnum(jr.getVisitnum());
				e.setIn_hb(true);
				e.setCount(jr.getCount());
				e.setCity(jr.getCity());
				result.add(e);
			}

			// 拼装公司信息
            qu = new QueryUtil();
			qu.addEqualFilter("id", String.valueOf(hbConfigId));
            Integer companyId = hrDao.getHbConfig(qu).getCompany_id();
            qu.addEqualFilter("id", String.valueOf(companyId));
            Hrcompany company = companyDao.getCompany(qu);
            result.forEach(s -> {
                s.setCompany_abbr(company.getAbbreviation());
                s.setCompany_logo(company.getLogo());
                s.setCompany_name(company.getName());
            });

			// 拼装红包信息
            List<RpExtInfo> rpExtInfoList = getPositionListRpExt(pids);

            result.forEach(s -> {
                RpExtInfo rpInfo = rpExtInfoList.stream().filter(e->e.getPid() == s.getId()).findFirst().orElse(
                        null);
                if (rpInfo != null) {
                    s.setRemain(rpInfo.getRemain());
                    s.setEmployee_only(rpInfo.isEmployee_only());
                }
            });

		}
		catch (TException e) {
			e.printStackTrace();
			return result;
		}
		catch (Exception e){
			e.printStackTrace();
			return result;
		}
		finally {
			// do nothing
		}
		// 查询到职位
		return result;
	}
}
