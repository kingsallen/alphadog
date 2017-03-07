package com.moseeker.useraccounts.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.common.util.StringUtils;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.common.struct.CommonQuery;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.dao.service.ConfigDBDao;
import com.moseeker.thrift.gen.dao.service.HrDBDao;
import com.moseeker.thrift.gen.dao.service.JobDBDao;
import com.moseeker.thrift.gen.dao.service.UserDBDao;
import com.moseeker.thrift.gen.dao.service.WxUserDao;
import com.moseeker.thrift.gen.dao.struct.ConfigSysPointConfTplDO;
import com.moseeker.thrift.gen.dao.struct.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.HrEmployeeCustomFieldsDO;
import com.moseeker.thrift.gen.dao.struct.HrPointsConfDO;
import com.moseeker.thrift.gen.dao.struct.JobApplicationDO;
import com.moseeker.thrift.gen.dao.struct.JobPositionDO;
import com.moseeker.thrift.gen.dao.struct.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.UserEmployeePointsRecordDO;
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

	public EmployeeResponse getEmployee(int userId, int companyId) throws TException {
		CommonQuery query = new CommonQuery();
		query.setEqualFilter(new HashMap<String, String>());
		UserEmployeeDO employee;
		EmployeeResponse response = new EmployeeResponse();
		try {
			query.getEqualFilter().put("sysuser_id", String.valueOf(userId));
			query.getEqualFilter().put("company_id", String.valueOf(companyId));
			employee = userDao.getEmployee(query);
			if (employee != null) {
			    // 根据user_id获取用户wxuserId
				query.getEqualFilter().remove("company_id");
			    Response wxResult = wxUserDao.getResource(query);
			    int wxuserId = 0;
			    if (wxResult.getStatus() == 0) {
			    		wxuserId = JSONObject.parseObject(wxResult.getData()).getIntValue("id");
			    }
			    response.setEmployee(new Employee(employee.getId(), employee.getEmployeeid(), employee.getCompanyId(), employee.getSysuserId(), employee.getMobile(), wxuserId, employee.getCname(), employee.getAward(), employee.getIsRpSent() == 0 ? false : true, employee.getCustomFieldValues()));
			    response.setExists(true);
			} else {
				response.setExists(false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}

	public EmployeeVerificationConfResponse getEmployeeVerificationConf(int companyId)
			throws TException {
		CommonQuery query = new CommonQuery();
		Map<String, String> eqf = new HashMap<String, String>();
		query.setEqualFilter(eqf);
		EmployeeVerificationConfResponse response = new EmployeeVerificationConfResponse();
		try {
			eqf.put("company_id", String.valueOf(companyId));
			HrEmployeeCertConfDO employeeCertConf = hrDBDao.getEmployeeCertConf(query);
			if (employeeCertConf != null) {
			    EmployeeVerificationConf evc = new EmployeeVerificationConf();
			    evc.setCompanyId(employeeCertConf.getCompany_id());
			    evc.setEmailSuffix(JSONObject.parseArray(employeeCertConf.getEmail_suffix()).stream().map(m -> String.valueOf(m)).collect(Collectors.toList()));
			    evc.setAuthMode((short)employeeCertConf.getAuth_mode());
			    evc.setAuthCode(employeeCertConf.getAuth_code());
			    evc.setCustom(employeeCertConf.getCustom());
			    evc.setQuestions(JSONObject.parseArray(employeeCertConf.getQuestions()).stream().map(m -> JSONObject.parseObject(String.valueOf(m), Map.class)).collect(Collectors.toList()));
			    evc.setCustomHint(employeeCertConf.getCustom_hint());
			    response.setEmployeeVerificationConf(evc);
			    response.setExits(true);
			} else {
				response.setExits(false);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return response;
	}
	
	public Result bind(BindingParams bindingParams) throws TException {
		Result response = new Result();
		CommonQuery query = new CommonQuery();
		query.setEqualFilter(new HashMap<String, String>());
		query.getEqualFilter().put("company_id", String.valueOf(bindingParams.getCompanyId()));
		query.getEqualFilter().put("auth_mode", String.valueOf(bindingParams.getType().getValue()));
		HrEmployeeCertConfDO certConf = hrDBDao.getEmployeeCertConf(query);
		if(certConf == null) {
			response.setSuccess(false);
			response.setMessage("暂时不接受员工认证");
			return response;
		}
		query.setEqualFilter(new HashMap<String, String>());
		switch(bindingParams.getType()){
			case EMAIL:
				// 邮箱校验后缀
				if (!bindingParams.getEmail().endsWith(certConf.email_suffix)) {
					response.setSuccess(false);
					response.setMessage("员工认证信息不正确");
					break;
				}
				// 验证员工是否已认证
				query.getEqualFilter().clear();
				query.getEqualFilter().put("company_id", String.valueOf(bindingParams.getCompanyId()));
				query.getEqualFilter().put("email", bindingParams.getEmail());
				query.getEqualFilter().put("activation", "0");
				query.getEqualFilter().put("disable", "0");
				query.getEqualFilter().put("status", "0");
				UserEmployeeDO employee = userDao.getEmployee(query);
				if (employee != null) {
					response.setSuccess(false);
					response.setMessage("该员工已绑定");
					break;
				}
				// TODO: 将信息放入redis
				break;
			case CUSTOMFIELD:
				// 员工信息验证
				query.getEqualFilter().clear();
				query.getEqualFilter().put("company_id", String.valueOf(bindingParams.getCompanyId()));
				query.getEqualFilter().put("sysuser_id", String.valueOf(bindingParams.getUserId()));
				query.getEqualFilter().put("cname", bindingParams.getName());
				query.getEqualFilter().put("custom_field", bindingParams.getCustomField());
				employee = userDao.getEmployee(query);
				if (employee == null) {
					response.setSuccess(false);
					response.setMessage("您提供的员工认证信息不正确");
				} else if (employee.getActivation() == 0) {
					response.setSuccess(false);
					response.setMessage("该员工已绑定");
				} else {
					// step 1: 认证当前员工   step 2: 将其他公司的该用户员工设为未认证
					UserEmployeeDO userEmployeeDO = new UserEmployeeDO();
					userEmployeeDO.setId(employee.getId());
					userEmployeeDO.setActivation((byte)0);
					userEmployeeDO.setAuthMethod((byte)bindingParams.getType().getValue());
					userEmployeeDO.setBindingTime(LocalDateTime.now().withNano(0).toString().replace('T', ' '));
					userEmployeeDO.setCname(bindingParams.getName());
					userEmployeeDO.setMobile(bindingParams.getMobile());
					Response updateResult = userDao.putUserEmployeesDO(Arrays.asList(userEmployeeDO));
					if (updateResult.getStatus() == 0) {
						response.setSuccess(true);
						response.setMessage(updateResult.getMessage());
						query.getEqualFilter().clear();
						query.getEqualFilter().put("sysuser_id", String.valueOf(bindingParams.getUserId()));
						query.getEqualFilter().put("activation", "0");
						query.getEqualFilter().put("disable", "0");
						query.getEqualFilter().put("status", "0");
						List<UserEmployeeDO> employees = userDao.getUserEmployeesDO(query);
						if (!StringUtils.isEmptyList(employees)) {
							List<UserEmployeeDO> updateList = employees.stream().filter(f -> f.getCompanyId() != bindingParams.getCompanyId()).map(m -> m.setActivation((byte)0)).collect(Collectors.toList());
							userDao.putUserEmployeesDO(updateList);
						}
					} else {
						response.setSuccess(false);
						response.setMessage(updateResult.getMessage());
					}
				}
				break;
			case QUESTIONS:
				// 问题校验
				List<String> answers = JSONObject.parseArray(certConf.getQuestions()).stream().flatMap(m -> JSONObject.parseObject(String.valueOf(m)).values().stream()).map(m -> String.valueOf(m)).collect(Collectors.toList());
				String[] replys = {bindingParams.getAnswer1().trim(), bindingParams.getAnswer2().trim()};
				boolean bool = true;
				if (!StringUtils.isEmptyList(answers) && answers.size() == replys.length) {
					for (int i = 0; i < answers.size(); i++) {
						if (!org.apache.commons.lang.StringUtils.defaultString(answers.get(i), " ").equals(replys[i])) {
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
				query.getEqualFilter().clear();
				query.getEqualFilter().put("sysuser_id", String.valueOf(bindingParams.getUserId()));
				query.getEqualFilter().put("disable", "0");
				query.getEqualFilter().put("status", "0");
				List<UserEmployeeDO> employees = userDao.getUserEmployeesDO(query);
				if (!StringUtils.isEmptyList(employees)) {
					employees.forEach(e -> {
						if (e.getCompanyId() == bindingParams.getCompanyId()) {
							e.setActivation((byte)0);
							e.setUpdateTime(LocalDateTime.now().withNano(0).toString().replace('T', ' '));
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
				break;
			default:
				break;
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
		if (employee == null) {
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
		Map<String, String> eqf = new HashMap<String, String>();
		query.setEqualFilter(eqf);
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
				response.setSuccess(false);
				response.setMessage(result.getMessage());
			} else {
				response.setSuccess(true);
				response.setMessage(result.getMessage());
			}
		}
		return response;
	}
	
}
