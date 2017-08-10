package com.moseeker.useraccounts.thrift;

import com.moseeker.thrift.gen.employee.struct.*;
import com.moseeker.useraccounts.service.impl.EmployeeBindByEmail;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moseeker.thrift.gen.employee.service.EmployeeService.Iface;
import com.moseeker.useraccounts.service.impl.EmployeeService;

/**
 * @author ltf
 * 员工服务thrift--实现
 * 2017年3月3日
 */
@Service
public class EmployeeServiceImpl implements Iface {
	
	@Autowired
	private EmployeeService service;

	@Autowired
    private EmployeeBindByEmail employeeBindByEmail;

	/* 
	 * 获取用户员工信息
	 */
	@Override
	public EmployeeResponse getEmployee(int userId, int companyId) throws TException {
		return service.getEmployee(userId, companyId);
	}

	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#getEmployeeVerificationConf(int)
	 * 获取公司员工认证配置信息
	 */
	@Override
	public EmployeeVerificationConfResponse getEmployeeVerificationConf(int companyId)
			throws TException {

		return service.getEmployeeVerificationConf(companyId);
	}

	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#bind(com.moseeker.thrift.gen.employee.struct.BindingParams)
	 * 员工绑定
	 */
	@Override
	public Result bind(BindingParams bindingParams) throws TException {

		return service.bind(bindingParams);
	}

	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#unbind(int, int, int)
	 * 员工解绑
	 */
	@Override
	public Result unbind(int employeeId, int companyId, int userId)
			throws TException {

		return service.unbind(employeeId, companyId, userId);
	}

	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#getEmployeeCustomFieldsConf(int)
	 * 获取员工认证自定义字段配置信息
	 */
	@Override
	public List<EmployeeCustomFieldsConf> getEmployeeCustomFieldsConf(int companyId)
			throws TException {

		return service.getEmployeeCustomFieldsConf(companyId);
	}


	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#getEmployeeRewards(int, int)
	 * 获取员工积分
	 */
	@Override
	public RewardsResponse getEmployeeRewards(int employeeId, int companyId)
			throws TException {
		
		return service.getEmployeeRewards(employeeId, companyId);
	}

	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#getEmployeeRecoms(int)
	 * 推荐记录
	 */
	@Override
	public List<RecomInfo> getEmployeeRecoms(int recomId) throws TException {

		return null;
	}

	/* 
	 * @see com.moseeker.thrift.gen.employee.service.EmployeeService.Iface#setEmployeeCustomInfo(int, java.lang.String)
	 * 员工填写认证自定义字段
	 */
	@Override
	public Result setEmployeeCustomInfo(int employeeId, String customValues)
			throws TException {
		
		return service.setEmployeeCustomInfo(employeeId, customValues);
	}

	@Override
	public Result emailActivation(String activationCode) throws TException {
		return employeeBindByEmail.emailActivation(activationCode);
	}

    @Override
    public List<EmployeeAward> awardRanking(int employeeId, int companyId, Timespan timespan) throws TException {
        String timeStr;
        if (timespan == Timespan.year) {
            timeStr = String.valueOf(LocalDate.now().getYear());
        } else if(timespan == Timespan.quarter) {
            timeStr = String.valueOf(LocalDate.now().getYear()).concat(String.valueOf((LocalDate.now().getMonthValue()+2)/3));
        } else {
            timeStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        }
        return service.awardRanking(employeeId, companyId, timeStr);
    }
}
