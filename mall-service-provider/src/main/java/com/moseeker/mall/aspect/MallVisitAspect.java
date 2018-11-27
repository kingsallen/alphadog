package com.moseeker.mall.aspect;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.malldb.MallGoodsInfoDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 商城各操作拦截
 *
 * @author cjm
 * @date 2018-10-17 10:15
 **/
@Aspect
@Component
public class MallVisitAspect {

    @Autowired
    private UserEmployeeDao userEmployeeDao;


    @Pointcut("@annotation(com.moseeker.mall.annotation.OnlyEmployee)")
    private void cut() {
    }

    @Before("cut()")
    public void beforeCall(JoinPoint joinPoint) throws BIZException {
        if(joinPoint.getArgs()[0] != null){
            String objStr = JSONObject.toJSONString(joinPoint.getArgs()[0]);
            JSONObject jsonObject = JSONObject.parseObject(objStr);
            Integer employeeId = jsonObject.getIntValue("employee_id");
            Integer companyId = jsonObject.getIntValue("company_id");
            checkEmployeeInfo(employeeId, companyId);
        }
    }

    /**
     * 非主账号无法操作积分商城
     * @param   employeeId hrId
     * @param   companyId companyId
     * @author  cjm
     * @date  2018/10/12
     */
    private void checkEmployeeInfo(Integer employeeId, Integer companyId) throws BIZException {
        UserEmployeeDO userEmployeeDO = userEmployeeDao.getUserEmployeeForUpdate(employeeId);
        if(userEmployeeDO == null){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_VISIT_LIMIT);
        }
        if(userEmployeeDO.getCompanyId() != companyId){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
    }
}
