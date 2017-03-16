package com.moseeker.useraccounts.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.providerutils.QueryUtil;
import com.moseeker.common.redis.RedisClient;
import com.moseeker.common.redis.cache.CacheClient;
import com.moseeker.common.util.ConfigPropertiesUtil;
import com.moseeker.common.util.DESCoder;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.CompanyDao;
import com.moseeker.thrift.gen.dao.service.ConfigDBDao;
import com.moseeker.thrift.gen.dao.service.HrDBDao;
import com.moseeker.thrift.gen.dao.service.JobDBDao;
import com.moseeker.thrift.gen.dao.service.UserDBDao;
import com.moseeker.thrift.gen.dao.service.WxUserDao;
import com.moseeker.thrift.gen.dao.struct.ConfigSysPointConfTplDO;
import com.moseeker.thrift.gen.dao.struct.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.HrEmployeeCustomFieldsDO;
import com.moseeker.thrift.gen.dao.struct.HrPointsConfDO;
import com.moseeker.thrift.gen.dao.struct.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.UserEmployeePointsRecordDO;
import com.moseeker.thrift.gen.dao.struct.UserUserDO;
import com.moseeker.thrift.gen.employee.struct.BindStatus;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.thrift.gen.employee.struct.Employee;
import com.moseeker.thrift.gen.employee.struct.EmployeeCustomFieldsConf;
import com.moseeker.thrift.gen.employee.struct.EmployeeResponse;
import com.moseeker.thrift.gen.employee.struct.EmployeeVerificationConf;
import com.moseeker.thrift.gen.employee.struct.EmployeeVerificationConfResponse;
import com.moseeker.thrift.gen.employee.struct.Result;
import com.moseeker.thrift.gen.employee.struct.Reward;
import com.moseeker.thrift.gen.employee.struct.RewardConfig;
import com.moseeker.thrift.gen.employee.struct.RewardsResponse;
import com.moseeker.thrift.gen.mq.service.MqService;

/**
 * @author ltf
 * 员工服务业务实现
 * 2017年3月3日
 */
@Service
public class EmployeeService {
	
	private Logger log = LoggerFactory.getLogger(EmployeeService.class);
	
	UserDBDao.Iface userDao = ServiceManager.SERVICEMANAGER.getService(UserDBDao.Iface.class);
	WxUserDao.Iface wxUserDao = ServiceManager.SERVICEMANAGER.getService(WxUserDao.Iface.class);
	HrDBDao.Iface hrDBDao = ServiceManager.SERVICEMANAGER.getService(HrDBDao.Iface.class);
	ConfigDBDao.Iface configDBDao = ServiceManager.SERVICEMANAGER.getService(ConfigDBDao.Iface.class);
	JobDBDao.Iface jobDBDao = ServiceManager.SERVICEMANAGER.getService(JobDBDao.Iface.class);
	MqService.Iface mqService = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);
	CompanyDao.Iface companyDao = ServiceManager.SERVICEMANAGER.getService(CompanyDao.Iface.class);
	RedisClient client = CacheClient.getInstance();

	public EmployeeResponse getEmployee(int userId, int companyId) throws TException {
		CommonQuery query = new CommonQuery();
		query.setEqualFilter(new HashMap<String, String>());
		UserEmployeeDO employee;
		EmployeeResponse response = new EmployeeResponse();
		try {
			query.getEqualFilter().put("sysuser_id", String.valueOf(userId));
			query.getEqualFilter().put("company_id", String.valueOf(companyId));
			employee = userDao.getEmployee(query);
			if (employee != null && employee.getId() != 0) {
			    // 根据user_id获取用户wxuserId
				query.getEqualFilter().remove("company_id");
			    Response wxResult = wxUserDao.getResource(query);
			    int wxuserId = 0;
			    if (wxResult.getStatus() == 0) {
			    		wxuserId = JSONObject.parseObject(wxResult.getData()).getIntValue("id");
			    }
			    response.setEmployee(new Employee(employee.getId(), employee.getEmployeeid(), employee.getCompanyId(), employee.getSysuserId(), employee.getMobile(), wxuserId, employee.getCname(), employee.getAward(), employee.getIsRpSent() == 0 ? false : true, employee.getCustomFieldValues()));

			    if (employee.getActivation() == 0) {
			    		response.setBindStatus(BindStatus.BINDED);
				} else if (StringUtils.isNotNullOrEmpty(client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, String.valueOf(employee.getId())))) {
					response.setBindStatus(BindStatus.PENDING);
				} else {
					response.setBindStatus(BindStatus.UNBIND);
				}
			} else {
				response.setBindStatus(BindStatus.UNBIND);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			response.setBindStatus(BindStatus.UNBIND);
		}
		return response;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public EmployeeVerificationConfResponse getEmployeeVerificationConf(int companyId)
			throws TException {
		CommonQuery query = new CommonQuery();
		Map<String, String> eqf = new HashMap<String, String>();
		query.setEqualFilter(eqf);
		EmployeeVerificationConfResponse response = new EmployeeVerificationConfResponse();
		try {
			eqf.put("company_id", String.valueOf(companyId));
			HrEmployeeCertConfDO employeeCertConf = hrDBDao.getEmployeeCertConf(query);
			log.info("HrEmployeeCertConfDO: {}", employeeCertConf);
			if (employeeCertConf != null && employeeCertConf.getEmail_suffix() != null && employeeCertConf.getQuestions() != null) {
			    EmployeeVerificationConf evc = new EmployeeVerificationConf();
			    evc.setCompanyId(employeeCertConf.getCompany_id());
			    evc.setEmailSuffix(JSONObject.parseArray(employeeCertConf.getEmail_suffix()).stream().map(m -> String.valueOf(m)).collect(Collectors.toList()));
			    evc.setAuthMode((short)employeeCertConf.getAuth_mode());
			    evc.setAuthCode(employeeCertConf.getAuth_code());
			    evc.setCustom(employeeCertConf.getCustom());
			    // 为解决gradle build时无法完成类推导的问题，顾list不指定类型
			    List questions = JSONObject.parseArray(employeeCertConf.getQuestions()).stream().map(m -> JSONObject.parseObject(String.valueOf(m), Map.class)).collect(Collectors.toList());
				evc.setQuestions(questions);
			    evc.setCustomHint(employeeCertConf.getCustom_hint());
			    Response hrCompanyConfig = companyDao.getHrCompanyConfig(query);
			    if (hrCompanyConfig.status == 0 && StringUtils.isNotNullOrEmpty(hrCompanyConfig.getData())) {
			    		evc.setBindSuccessMessage(JSONObject.parseObject(hrCompanyConfig.getData()).getString("employee_binding"));
				}
			    response.setEmployeeVerificationConf(evc);
			    log.info("EmployeeVerificationConfResponse: {}", response.getEmployeeVerificationConf());
			    response.setExists(true);
			} else {
				response.setExists(false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}
	
	public Result bind(BindingParams bindingParams) throws TException {
		log.info("BindingParams: {}", bindingParams);
		Result response = new Result();
		CommonQuery query = new CommonQuery();
		query.setEqualFilter(new HashMap<String, String>());
		query.getEqualFilter().put("company_id", String.valueOf(bindingParams.getCompanyId()));
		HrEmployeeCertConfDO certConf = hrDBDao.getEmployeeCertConf(query);
		if(certConf == null || certConf.getCompany_id() == 0) {
			response.setSuccess(false);
			response.setMessage("暂时不接受员工认证");
			return response;
		}
		query.setEqualFilter(new HashMap<String, String>());
		switch(bindingParams.getType()){
			case EMAIL:
				// 邮箱校验后缀
				if (JSONObject.parseArray(certConf.getEmail_suffix()).stream().noneMatch(m -> bindingParams.getEmail().endsWith(String.valueOf(m)))) {
					response.setSuccess(false);
					response.setMessage("员工认证信息不正确");
					break;
				}
				// 验证员工是否已认证
				query.getEqualFilter().clear();
				query.getEqualFilter().put("company_id", String.valueOf(bindingParams.getCompanyId()));
				query.getEqualFilter().put("email", bindingParams.getEmail());
				query.getEqualFilter().put("disable", "0");
				query.getEqualFilter().put("status", "0");
				UserEmployeeDO employee = userDao.getEmployee(query);
				if (employee == null || employee.getId() == 0) {
					response.setSuccess(false);
					response.setMessage("您提供的员工认证信息不正确");
					break;
				}
				
				if (employee.getActivation() == 0) {
					response.setSuccess(false);
					response.setMessage("该员工已绑定");
					break;
				}
				
				// TODO: 将信息放入redis
				query.getEqualFilter().clear();
				query.getEqualFilter().put("company_id", String.valueOf(bindingParams.getCompanyId()));
				HrCompanyDO companyDO = hrDBDao.getCompany(query);
				Response hrwechatResult = hrDBDao.getHrWxWechat(query);
				if (companyDO != null && hrwechatResult.getStatus() == 0 && StringUtils.isNotNullOrEmpty(hrwechatResult.getData())) {
					JSONObject hrWxWechatJson = JSONObject.parseObject(hrwechatResult.getData());
					String activationCode = DESCoder.encrypt(String.valueOf(employee.getId()));
					Map<String, String> mesBody = new HashMap<String, String>();
					mesBody.put("#company_log#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(companyDO.getLogo(), ""));
					mesBody.put("#employee_name#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(employee.getCname(), genUsername(employee.getSysuserId())));
					mesBody.put("#company_abbr#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(companyDO.getName(), ""));
					mesBody.put("#official_account_name#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(hrWxWechatJson.getString("name"), ""));
					mesBody.put("#official_account_qrcode#",  org.apache.commons.lang.StringUtils.defaultIfEmpty(hrWxWechatJson.getString("qrcode"), ""));
					mesBody.put("#date_today#",  LocalDate.now().toString());
					mesBody.put("#auth_url#", ConfigPropertiesUtil.getInstance().get("platform.url", String.class).concat("m/bindemail?activation_code=").concat(activationCode));
					// 发送认证邮件
					Response mailResponse = mqService.sendAuthEMail(mesBody, Constant.EVENT_TYPE_EMPLOYEE_AUTH, bindingParams.getEmail(), "员工认证");
					// 邮件发送成功
					if (mailResponse.getStatus() == 0) {
						client.set(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, String.valueOf(employee.getId()), JSONObject.toJSONString(bindingParams));
						response.setSuccess(true);
						response.setMessage("发送激活邮件成功");
					} else {
						response.setMessage("发送激活邮件失败");
					}
				}
				break;
			case CUSTOMFIELD:
				// 员工信息验证
				query.getEqualFilter().clear();
				query.getEqualFilter().put("company_id", String.valueOf(bindingParams.getCompanyId()));
				query.getEqualFilter().put("sysuser_id", String.valueOf(bindingParams.getUserId()));
				query.getEqualFilter().put("cname", bindingParams.getName());
				query.getEqualFilter().put("custom_field", bindingParams.getCustomField());
				employee = userDao.getEmployee(query);
				if (employee == null || employee.getId() == 0) {
					response.setSuccess(false);
					response.setMessage("您提供的员工认证信息不正确");
				} else if (employee.getActivation() == 0) {
					response.setSuccess(false);
					response.setMessage("该员工已绑定");
				} else {
					response = updateEmployee(bindingParams);
				}
				break;
			case QUESTIONS:
				// 问题校验
				List<String> answers = JSONObject.parseArray(certConf.getQuestions()).stream().flatMap(m -> JSONObject.parseObject(String.valueOf(m)).values().stream()).map(m -> String.valueOf(m)).collect(Collectors.toList());
				log.info("answers: {}", answers);
				String[] replys = {bindingParams.getAnswer1().trim(), bindingParams.getAnswer2().trim()};
				boolean bool = true;
				if (!StringUtils.isEmptyList(answers) && answers.size() == replys.length) {
					for (int i = 0; i < answers.size(); i++) {
						if (!org.apache.commons.lang.StringUtils.defaultString(answers.get(i), "").equals(replys[i])) {
							bool = false;
							break;
						}
					}
				} else {
					bool = false;
				}
				if (!bool) {
					response.setSuccess(false);
					response.setMessage("员工认证信息不正确");
					break;
				}
				response = updateEmployee(bindingParams);
				/* 
				 * TODO 员工认证发送消息模板
	             *  a: 认证成功以后发送消息模板,点击消息模板填写自定义字段 (company_id 为奇数)
	             *  b: 直接填写自定义字段 (company_id 为偶数)
				 * */
				break;
			default:
				break;
		}
		return response;
	}
	

	/**
	 * step 1: 认证当前员工   step 2: 将其他公司的该用户员工设为未认证
	 * @param bindingParams
	 * @return
	 * @throws TException
	 */
	private Result updateEmployee(BindingParams bindingParams) throws TException {
		Result response = new Result();
		CommonQuery query = new CommonQuery();
		query.setEqualFilter(new HashMap<String, String>());
		query.getEqualFilter().put("sysuser_id", String.valueOf(bindingParams.getUserId()));
		query.getEqualFilter().put("disable", "0");
		query.getEqualFilter().put("status", "0");
		List<UserEmployeeDO> employees = userDao.getUserEmployeesDO(query);
		if (!StringUtils.isEmptyList(employees)) {
			employees.forEach(e -> {
				if (e.getCompanyId() == bindingParams.getCompanyId()) {
					e.setActivation((byte)0);
					e.setAuthMethod((byte)bindingParams.getType().getValue());
					e.setUpdateTime(LocalDateTime.now().withNano(0).toString().replace('T', ' '));
					if (StringUtils.isNotNullOrEmpty(bindingParams.getName())) e.setCname(bindingParams.getName());
					if (StringUtils.isNotNullOrEmpty(bindingParams.getMobile())) e.setMobile(bindingParams.getMobile());
				} else {
					e.setActivation((byte)1);
				}
			});
		}
		Response updateResult = userDao.putUserEmployeesDO(employees);
		if (updateResult.getStatus() == 0){
			response.setSuccess(true);
			response.setMessage(updateResult.getMessage());
		} else {
			response.setSuccess(false);
			response.setMessage(updateResult.getMessage());
		}
		return response;
	}
	
	public Result unbind(int employeeId, int companyId, int userId)
			throws TException {
		Result response = new Result();
		CommonQuery query = new CommonQuery();
		query.setEqualFilter(new HashMap<String, String>());
		query.getEqualFilter().put("sysuser_id", String.valueOf(userId));
		query.getEqualFilter().put("company_id", String.valueOf(companyId));
		query.getEqualFilter().put("employeeid", String.valueOf(employeeId));
		UserEmployeeDO employee = userDao.getEmployee(query);
		if (employee == null && employee.getId() == 0) {
			response.setSuccess(false);
			response.setMessage("员工信息不存在");
		} else {
			employee.setActivation((byte)1);
			employee.setEmailIsvalid((byte)0);
			Response result = userDao.putUserEmployeesDO(Arrays.asList(employee));
			if (result.getStatus() == 0){
				response.setSuccess(true);
				response.setMessage(result.getMessage());
			} else {
				response.setSuccess(false);
				response.setMessage(result.getMessage());
			}
		}
		return response;
	}

	public List<EmployeeCustomFieldsConf> getEmployeeCustomFieldsConf(int companyId)
			throws TException {
		CommonQuery query = new CommonQuery();
		query.setEqualFilter(new HashMap<String, String>());
		query.getEqualFilter().put("company_id", String.valueOf(companyId));
		List<HrEmployeeCustomFieldsDO> customFields = new ArrayList<HrEmployeeCustomFieldsDO>();
		List<EmployeeCustomFieldsConf> response = new ArrayList<EmployeeCustomFieldsConf>();
		try {
			customFields = hrDBDao.getEmployeeCustomFields(query);
			if(!StringUtils.isEmptyList(customFields)) {
				customFields.forEach(m -> {
					EmployeeCustomFieldsConf efc = new EmployeeCustomFieldsConf();
					efc.setId(m.getId());
					efc.setCompanyId(m.getCompany_id());
					efc.setFieldValues(JSONObject.parseArray(m.getFvalues()).stream().map(value -> String.valueOf(value)).collect(Collectors.toList()));
					efc.setMandatory(m.getMandatory() == 0 ? false : true);
					efc.setOrder(m.getForder());
					efc.setFieldName(m.getFname());
					response.add(efc);
				});
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}
	

	public RewardsResponse getEmployeeRewards(int employeeId, int companyId)
			throws TException {
		RewardsResponse response = new RewardsResponse();
		CommonQuery query = new CommonQuery();
		query.setEqualFilter(new HashMap<String, String>());
		query.getEqualFilter().put("company_id", String.valueOf(companyId));
		try {
			/*
			 * 开始查询积分规则：
			 */
			List<RewardConfig> pcfList = new ArrayList<>();
			List<HrPointsConfDO> pointsConfs = hrDBDao.getPointsConfs(query);
			if (!StringUtils.isEmptyList(pointsConfs)) {
				List<Integer> tpIds = pointsConfs.stream().map(m -> m.getTemplate_id()).collect(Collectors.toList());
				query.getEqualFilter().clear();
				query.getEqualFilter().put("id", Arrays.toString(tpIds.toArray()));
				List<ConfigSysPointConfTplDO> configTpls = configDBDao.getAwardConfigTpls(query);
				if (!StringUtils.isEmptyList(configTpls)) {
					Set<Integer> ctpIds = configTpls.stream().map(m -> m.getId()).collect(Collectors.toSet());
					for (HrPointsConfDO pcf : pointsConfs) {
						if (pcf.getReward() != 0 && ctpIds.contains(pcf.getTemplate_id())) {
							RewardConfig rewardConfig = new RewardConfig();
							rewardConfig.setId(pcf.getId());
							rewardConfig.setPoints(pcf.getReward());
							rewardConfig.setStatusName(pcf.getStatus_name());
							pcfList.add(rewardConfig);
						}
					}
				}
			}
			response.setRewardConfigs(pcfList);
			// 查询申请职位list
			List<UserEmployeePointsRecordDO> points = userDao.getUserEmployeePoints(employeeId);
			if (!StringUtils.isEmptyList(points)) {
				List<Integer> aids = points.stream().map(m -> m.getApplication_id()).collect(Collectors.toList());
				query.getEqualFilter().clear();
				query.getEqualFilter().put("id", Arrays.toString(aids.toArray()));
				List<JobApplicationDO> applications = jobDBDao.getApplications(query);
				final Map<Integer, Integer>  appMap = new HashMap<Integer, Integer>();
				final Map<Integer, String> positionMap = new HashMap<Integer, String>();
				// 转成map -> k: applicationId, v: positionId
				if (!StringUtils.isEmptyList(applications)) {
					appMap.putAll(applications.stream().collect(Collectors.toMap(JobApplicationDO::getId, JobApplicationDO::getPositionId)));
					query.getEqualFilter().clear();
					query.getEqualFilter().put("id", Arrays.toString(appMap.values().toArray()));
					List<JobPositionDO> positions = jobDBDao.getPositions(query);
					// 转成map -> k: positionId, v: positionTitle
					if (!StringUtils.isEmptyList(points)){
						positionMap.putAll(positions.stream().collect(Collectors.toMap(JobPositionDO::getId, JobPositionDO::getTitle)));
					}
				}
				
				// 计算积分总合
				int total = points.stream().mapToInt(m -> m.getAward()).filter(f -> f > 0).sum();
				// 用户积分记录：
				List<Reward> rewards = new ArrayList<Reward>();
				points.forEach(point -> {
					Reward reward = new Reward();
					reward.setReason(point.getReason());
					reward.setPoints(point.getAward());
					reward.setUpdateTime(point.getUpdate_time());
					reward.setTitle(positionMap.getOrDefault(appMap.get(point.getApplication_id()), ""));
					rewards.add(reward);
				});
				response.setTotal(total);
				response.setRewards(rewards);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}

	public Result setEmployeeCustomInfo(int employeeId, String customValues)
			throws TException {
		Result response = new Result();
		CommonQuery query = new CommonQuery();
		query.setEqualFilter(new HashMap<String, String>());
		query.getEqualFilter().put("employeeid", String.valueOf(employeeId));
		List<UserEmployeeDO> userEmployeesDO = userDao.getUserEmployeesDO(query);
		if(StringUtils.isEmptyList(userEmployeesDO)) {
			response.setSuccess(false);
			response.setMessage("员工信息不存在");
		} else {
			userEmployeesDO.get(0).setCustomFieldValues(customValues);
			Response result = userDao.putUserEmployeesDO(userEmployeesDO.subList(0, 1));
			if (result.getStatus() == 0) {
				response.setSuccess(true);
				response.setMessage(result.getMessage());
			} else {
				response.setSuccess(false);
				response.setMessage(result.getMessage());
			}
		}
		return response;
	}
	
	
	public Result emailActivation(String activationCodee) throws TException {
		Result response = new Result();
		response.setSuccess(false);
		response.setMessage("激活信息不正确");
		String employeeId = DESCoder.decrypt(activationCodee);
		if (StringUtils.isNotNullOrEmpty(employeeId)) {
			String value = client.get(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, employeeId);
			if (StringUtils.isNotNullOrEmpty(value)) {
				BindingParams bindingParams = JSONObject.parseObject(value, BindingParams.class);
				response = updateEmployee(bindingParams);
				if (response.success) {
					client.del(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, employeeId);
				}
			} 
		}
		return response;
	}
	
	/**
	 * 获取用户的称呼
	 * @param userId 用户id
	 * @return
	 */
	public String genUsername(int userId) {
		QueryUtil qu = new QueryUtil();
		qu.addEqualFilter("id", String.valueOf(userId));
		UserUserDO user;
		String username = "";
		try {
			user = userDao.getUser(qu);
			if(user != null && user.getUsername() != null) {
				if(StringUtils.isNotNullOrEmpty(user.getName())) {
					username = user.getName();
				} else if(StringUtils.isNotNullOrEmpty(user.getNickname())) {
					username = user.getNickname();
				} else {
					username = user.getUsername();
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return username;
	}
}
