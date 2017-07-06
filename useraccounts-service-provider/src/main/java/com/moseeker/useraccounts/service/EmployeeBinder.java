package com.moseeker.useraccounts.service;

import com.moseeker.baseorm.dao.hrdb.HrEmployeeCertConfDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.entity.UserWxEntity;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.thrift.gen.employee.struct.Result;
import com.moseeker.thrift.gen.mq.service.MqService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by lucky8987 on 17/6/29.
 */
@Component
public abstract class EmployeeBinder {

    private static final Logger log = LoggerFactory.getLogger(EmployeeBinder.class);

    protected MqService.Iface mqService = ServiceManager.SERVICEMANAGER.getService(MqService.Iface.class);

    @Autowired
    protected UserUserDao userDao;

    @Autowired
    protected UserEmployeeDao employeeDao;

    @Autowired
    protected UserWxEntity wxEntity;

    @Autowired
    protected EmployeeEntity employeeEntity;

    @Autowired
    protected UserAccountEntity userAccountEntity;

    @Autowired
    protected HrEmployeeCertConfDao hrEmployeeCertConfDao;

    protected ThreadLocal<UserEmployeeDO> userEmployeeDOThreadLocal = new ThreadLocal<>();

    public Result bind(BindingParams bindingParams) {
        log.info("bind param: BindingParams={}", bindingParams);
        Result response = new Result();
        Query.QueryBuilder query = new Query.QueryBuilder();
        try {
            userEmployeeDOThreadLocal.set(employeeEntity.getCompanyEmployee(bindingParams.getUserId(), bindingParams.getCompanyId()));
            if (userEmployeeDOThreadLocal.get() != null && userEmployeeDOThreadLocal.get().getId() > 0 && userEmployeeDOThreadLocal.get().getActivation() == 0) {
                throw new RuntimeException("该员工已绑定");
            }
            query.where("company_id", String.valueOf(bindingParams.getCompanyId())).and("disable", String.valueOf(0));
            HrEmployeeCertConfDO certConf = hrEmployeeCertConfDao.getData(query.buildQuery());
            if(certConf == null || certConf.getCompanyId() == 0) {
                throw new RuntimeException("暂时不接受员工认证");
            }
            paramCheck(bindingParams, certConf);
            int userEmployeeId = createEmployee(bindingParams);
            response = doneBind(bindingParams, userEmployeeId);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    protected int createEmployee(BindingParams bindingParams) {
        if (userEmployeeDOThreadLocal.get() == null || userEmployeeDOThreadLocal.get().getId() == 0) {
            UserEmployeeDO userEmployee = new UserEmployeeDO();
            userEmployee.setCompanyId(bindingParams.getCompanyId());
            userEmployee.setEmployeeid(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), ""));
            userEmployee.setSysuserId(bindingParams.getUserId());
            userEmployee.setCname(bindingParams.getName());
            userEmployee.setMobile(bindingParams.getMobile());
            userEmployee.setEmail(bindingParams.getEmail());
            userEmployee.setWxuserId(wxEntity.getWxuserId(bindingParams.getUserId(), bindingParams.getCompanyId()));
            userEmployee.setAuthMethod((byte)bindingParams.getType().getValue());
            userEmployee.setActivation((byte)3);
            userEmployee.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            int primaryKey = employeeDao.addData(userEmployee).getId();
            if( primaryKey == 0) {
                log.info("员工邮箱认证，保存员工信息失败 employee={}", userEmployee);
                throw new RuntimeException("认证失败，请检查员工信息");
            }
            userEmployee.setId(primaryKey);
            userEmployeeDOThreadLocal.set(userEmployee);
            return primaryKey;
        }
        return userEmployeeDOThreadLocal.get().getId();
    }


    /**
     *  认证数据校验
     * @param bindingParams
     * @param certConf
     * @throws Exception
     */
    protected abstract void paramCheck(BindingParams bindingParams, HrEmployeeCertConfDO certConf) throws Exception;


    /**
     * step 1: 认证当前员工   step 2: 将其他公司的该用户员工设为未认证
     * @param bindingParams
     * @return
     * @throws TException
     */
    protected Result doneBind(BindingParams bindingParams, int employeeId) throws TException {
        log.info("updateEmployee param: BindingParams={}", bindingParams);
        Result response = new Result();
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("sysuser_id", String.valueOf(bindingParams.getUserId())).and("disable", "0");
        List<UserEmployeeDO> employees = employeeDao.getDatas(query.buildQuery());
        log.info("select employees by: {}, result = {}", query, Arrays.toString(employees.toArray()));
        if (!StringUtils.isEmptyList(employees)) {
            employees.forEach(e -> {
                if (e.getId() == employeeId) {
                    e.setActivation((byte)0);
                    e.setAuthMethod((byte)bindingParams.getType().getValue());
                    e.setWxuserId(wxEntity.getWxuserId(bindingParams.getUserId(), bindingParams.getCompanyId()));
                    e.setEmail(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getEmail(), e.getEmail()));
                    e.setBindingTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    e.setUpdateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    e.setCname(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getName(), e.getCname()));
                    e.setMobile(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), e.getMobile()));
                    e.setCustomField(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getCustomField(), e.getCustomField()));
                } else {
                    e.setEmailIsvalid((byte)0);
                    e.setActivation((byte)1);
                }
            });
        }
        log.info("update employess = {}", Arrays.toString(employees.toArray()));
        int[] updateResult = employeeDao.updateDatas(employees);
        if (Arrays.stream(updateResult).allMatch(m -> m == 1)){
            response.setSuccess(true);
            response.setMessage("success");
        } else {
            response.setSuccess(false);
            response.setMessage("fail");
        }
        log.info("updateEmployee response : {}", response);
        return response;
    }

}
