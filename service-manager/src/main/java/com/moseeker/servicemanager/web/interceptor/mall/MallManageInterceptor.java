package com.moseeker.servicemanager.web.interceptor.mall;

import com.moseeker.baseorm.dao.userdb.UserHrAccountDao;
import com.moseeker.common.constants.ConstantErrorCodeMessage;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.thrift.gen.common.struct.BIZException;
import com.moseeker.thrift.gen.dao.struct.userdb.UserHrAccountDO;
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
public class MallManageInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserHrAccountDao userHrAccountDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Integer hrId = getValidHrId(request);
        Integer companyId = getValidCompanyId(request);
        if(hrId != 0 && companyId != 0){
            checkSuperAccount(hrId, companyId);
            return true;
        }else {
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
    }

    private Integer getValidHrId(HttpServletRequest request) throws BIZException {
        Integer hrId;
        try{
            hrId = Integer.parseInt(request.getParameter("hr_id"));
        }catch (Exception e){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.PROGRAM_PARAM_NOTEXIST);
        }
        return hrId;
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
    /**
     * 非主账号无法操作积分商城
     * @param   hrId hrId
     * @author  cjm
     * @date  2018/10/12
     */
    private void checkSuperAccount(int hrId, int companyId) throws BIZException {
        UserHrAccountDO userHrAccountDO = userHrAccountDao.getValidAccount(hrId);
        if(userHrAccountDO == null || userHrAccountDO.getCompanyId() != companyId || userHrAccountDO.getAccountType() != 0){
            throw ExceptionUtils.getBizException(ConstantErrorCodeMessage.MALL_LIMIT_SUPER_ACCOUNT);
        }
    }

}
