package com.moseeker.servicemanager.web.interceptor.mall;

import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 商城权限拦截器
 *
 * @author cjm
 * @date 2018-10-12 11:57
 **/
@Component
public class MallShowInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Integer employeeId = getValidEmployeeId(request);
        Integer companyId = getValidCompanyId(request);
        if(employeeId != 0 && companyId != 0){
            checkEmployeeInfo(employeeId, companyId);
            return true;
        }else {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
    }

    private void checkEmployeeInfo(Integer employeeId, Integer companyId) throws BIZException {
        UserEmployeeDO userEmployeeDO = userEmployeeDao.getUserEmployeeForUpdate(employeeId);
        if(userEmployeeDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_VISIT_LIMIT);
        }
        if(userEmployeeDO.getCompanyId() != companyId){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
    }

    private Integer getValidEmployeeId(HttpServletRequest request) throws BIZException {
        Integer employeeId;
        try{
            employeeId = Integer.parseInt(request.getParameter("employee_id"));
        }catch (Exception e){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
        return employeeId;
    }

    private Integer getValidCompanyId(HttpServletRequest request) throws BIZException {
        Integer companyId;
        try{
            companyId = Integer.parseInt(request.getParameter("company_id"));
        }catch (Exception e){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
        return companyId;
    }

}
