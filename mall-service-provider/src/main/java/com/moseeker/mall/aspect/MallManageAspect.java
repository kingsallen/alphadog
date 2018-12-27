package com.moseeker.mall.aspect;

import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 商城各操作拦截
 *
 * @author cjm
 * @date 2018-10-17 10:15
 **/
@Aspect
@Component
public class MallManageAspect {

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Autowired
    private HrCompanyDao hrCompanyDao;

    @Pointcut("@annotation(com.moseeker.mall.annotation.OnlySuperAccount)")
    private void cut() {
    }

    @Before("cut()")
    public void beforeCall(JoinPoint joinPoint) throws BIZException {
        String objStr = JSONObject.toJSONString(joinPoint.getArgs()[0]);
        JSONObject jsonObject = JSONObject.parseObject(objStr);
        Integer hrId = jsonObject.getIntValue("hr_id");
        Integer companyId = jsonObject.getIntValue("company_id");
        checkSuperAccount(hrId, companyId);
    }

    /**
     * 非主账号无法操作积分商城
     *
     * @param hrId hrId
     * @author cjm
     * @date 2018/10/12
     */
    private void checkSuperAccount(int hrId, int companyId) throws BIZException {
        HrCompanyDO hrCompanyDO = hrCompanyDao.getCompanyById(companyId);
        if (hrCompanyDO == null || hrCompanyDO.getType() != 0) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_LIMIT_SUPER_ACCOUNT);
        }
        UserHrAccountDO userHrAccountDO = userHrAccountDao.getValidAccount(hrId);
        if (userHrAccountDO == null || userHrAccountDO.getCompanyId() != companyId
                || userHrAccountDO.getAccountType() != 0 || userHrAccountDO.getAccountType() != 2) {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_LIMIT_SUPER_ACCOUNT);
        }

    }
}
