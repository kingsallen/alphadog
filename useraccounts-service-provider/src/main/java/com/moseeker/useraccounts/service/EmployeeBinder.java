package com.moseeker.useraccounts.service;

import com.alibaba.fastjson.JSON;
import com.moseeker.baseorm.dao.candidatedb.CandidateCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrEmployeeCertConfDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserUserDao;
import com.moseeker.baseorm.redis.RedisClient;
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.entity.EmployeeEntity;
import com.moseeker.entity.SearchengineEntity;
import com.moseeker.entity.UserAccountEntity;
import com.moseeker.entity.UserWxEntity;
import com.moseeker.rpccenter.client.ServiceManager;
import com.moseeker.thrift.gen.dao.struct.candidatedb.CandidateCompanyDO;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.thrift.gen.employee.struct.Result;
import com.moseeker.thrift.gen.mq.service.MqService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Resource;
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

    @Resource(name = "cacheClient")
    protected RedisClient client;

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

    @Autowired
    protected SearchengineEntity searchengineEntity;

    @Autowired
    protected CandidateCompanyDao candidateCompanyDao;

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
            UserEmployeeDO userEmployee = createEmployee(bindingParams);
            response = doneBind(userEmployee);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        log.info("bind response: {}", response);
        return response;
    }

    /**
     * 创建员工记录
     * @param bindingParams
     * @return
     */
    protected UserEmployeeDO createEmployee(BindingParams bindingParams) {
        UserEmployeeDO userEmployee = userEmployeeDOThreadLocal.get() == null ? new UserEmployeeDO() : userEmployeeDOThreadLocal.get();
        userEmployee.setCompanyId(bindingParams.getCompanyId());
        userEmployee.setEmployeeid(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), ""));
        userEmployee.setSysuserId(bindingParams.getUserId());
        userEmployee.setCname(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getName(), userEmployee.getCname()));
        userEmployee.setMobile(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getMobile(), userEmployee.getMobile()));
        userEmployee.setEmail(org.apache.commons.lang.StringUtils.defaultIfBlank(bindingParams.getEmail(), userEmployee.getEmail()));
        userEmployee.setWxuserId(wxEntity.getWxuserId(bindingParams.getUserId(), bindingParams.getCompanyId()));
        userEmployee.setAuthMethod((byte)bindingParams.getType().getValue());
        userEmployee.setActivation((byte)0);
        userEmployee.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        userEmployee.setBindingTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        userEmployeeDOThreadLocal.set(userEmployee);
        return userEmployee;
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
     * @param useremployee
     * @return
     * @throws TException
     */
    protected Result doneBind(UserEmployeeDO useremployee) throws TException {
        log.info("doneBind param: useremployee={}", useremployee);
        Result response = new Result();
        int employeeId;
        if (useremployee.getId() == 0) {
            employeeId = employeeDao.addData(useremployee).getId();
            useremployee.setId(employeeId);
        } else {
            employeeDao.updateData(useremployee);
            employeeId = useremployee.getId();
        }
        //将属于本公司的潜在候选人设置为无效
        cancelCandidate(useremployee.getSysuserId(),useremployee.getCompanyId());
        // 将其他公司的员工认证记录设为未认证
        Query.QueryBuilder query = new Query.QueryBuilder();
        query.where("sysuser_id", String.valueOf(useremployee.getSysuserId())).and("disable", "0");
        List<UserEmployeeDO> employees = employeeDao.getDatas(query.buildQuery());
        log.info("select employees by: {}, result = {}", query, Arrays.toString(employees.toArray()));
        if (!StringUtils.isEmptyList(employees)) {
            employees.forEach(e -> {
                if (e.getId() != employeeId && e.getActivation() == 0) {
                    e.setEmailIsvalid((byte)0);
                    e.setActivation((byte)1);
                    e.setCustomFieldValues("[]");
                }
            });
            log.info("employees========"+JSON.toJSONString(employees));
            if(!StringUtils.isEmptyList(employees)){
                for(UserEmployeeDO DO:employees){
                    int userId=DO.getSysuserId();
                    int companyId=DO.getCompanyId();
                    convertCandidatePerson(userId,companyId);
                }
            }

        }
        log.info("update employess = {}", Arrays.toString(employees.toArray()));
        int[] updateResult = employeeDao.updateDatas(employees);
        if (Arrays.stream(updateResult).allMatch(m -> m == 1)){
            response.setSuccess(true);
            response.setMessage("success");
            if (StringUtils.isNotNullOrEmpty(useremployee.getActivationCode())) {
                client.del(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_CODE, useremployee.getActivationCode());
                client.del(Constant.APPID_ALPHADOG, Constant.EMPLOYEE_AUTH_INFO, employeeEntity.getAuthInfoKey(useremployee.getSysuserId(), useremployee.getCompanyId()));
            }
            // 更新ES中useremployee信息
            searchengineEntity.updateEmployeeDOAwards(employees);
        } else {
            response.setSuccess(false);
            response.setMessage("fail");
        }
        log.info("updateEmployee response : {}", response);
        return response;
    }
/*
    员工认证成功时，需要将潜在候选人置为无效
 */
    public void cancelCandidate(int userId,int companyId) {
        Query query = new Query.QueryBuilder().where("sys_user_id", userId).and("company_id", companyId).and("status", 1).buildQuery();
        List<CandidateCompanyDO> list = candidateCompanyDao.getDatas(query);
        log.info("CandidateCompanyDO1====="+JSON.toJSONString(list));
        if (!StringUtils.isEmptyList(list)) {
            log.info(JSON.toJSONString(list));
            for (CandidateCompanyDO DO : list) {
                DO.setStatus(0);
                candidateCompanyDao.updateData(DO);
            }
            log.info("CandidateCompanyDO3====="+JSON.toJSONString(list));

        }
    }
    /*
        员工取消后，需要将潜在候选人置为有效
     */
     public void convertCandidatePerson(int userId,int companyId){
         Query query=new Query.QueryBuilder().where("sys_user_id",userId).and("company_id",companyId).and("status",0).buildQuery();
         List<CandidateCompanyDO> list=candidateCompanyDao.getDatas(query);
         log.info("CandidateCompanyDO2====="+JSON.toJSONString(list));
         if(!StringUtils.isEmptyList(list)){
             for(CandidateCompanyDO DO:list){
                 DO.setStatus(1);
                 candidateCompanyDao.updateData(DO);
             }
             log.info("CandidateCompanyDO2====="+JSON.toJSONString(list));
         }
    }

}
