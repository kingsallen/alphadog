package com.moseeker.useraccounts.thrift;

import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.thrift.gen.employee.struct.Result;
import com.moseeker.thrift.gen.useraccounts.service.McdUatService;
import com.moseeker.thrift.gen.useraccounts.struct.McdUserTypeDO;
import com.moseeker.useraccounts.constant.EmployeeSource;
import com.moseeker.useraccounts.service.impl.EmployeeBindAndUpdateByMcdUatSysUserId;
import com.moseeker.useraccounts.service.impl.McdUatServiceImpl;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by eddie on 2017/3/9.
 */
@Service
public class McdUatEmployeeThriftService implements McdUatService.Iface {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    McdUatServiceImpl mcdUatService;

    @Autowired
    EmployeeBindAndUpdateByMcdUatSysUserId employeeBindAndUpdateByMcdUatSysUserId;


    @Override
    public Response getUserEmployeeInfoByUserType(McdUserTypeDO userTypeDO) throws TException {
        logger.info("McdUatEmployeeThriftService getUserEmployeeInfoByUserType userTypeDO:{}", userTypeDO);
        try {
            BindingParams bindingParams = new BindingParams();

            bindingParams.setUserId(userTypeDO.getUser_id());
            bindingParams.setCompanyId(userTypeDO.getCompany_id());
            bindingParams.setEmail(userTypeDO.getEmail());
            bindingParams.setMobile(userTypeDO.getMobile());
            bindingParams.setCustomField(userTypeDO.getCustom_field());
            bindingParams.setName(userTypeDO.getCname());
            //这必须是12代表是来自joywork;
            bindingParams.setSource(EmployeeSource.Joywork.getValue());
            //注册来源joywork;
            int bindSource = EmployeeSource.Joywork.getValue();
            Result result1 = employeeBindAndUpdateByMcdUatSysUserId.bind(bindingParams, bindSource);
            logger.info("McdUatEmployeeThriftService getUserEmployeeInfoByUserType result1:{}", result1);
            if (result1.isSuccess()) {
                return ResponseUtils.success(true);
            } else {
                return ResponseUtils.fail(result1.getEmployeeId(), result1.getMessage(), false);
            }

        } catch (Exception e) {
            throw ExceptionUtils.convertException(e);
        }
    }

    @Override
    public McdUserTypeDO getUserEmployeeByuserId(int userId) {

        McdUserTypeDO mcdUserTypeDO = new McdUserTypeDO();

        return mcdUserTypeDO;

    }

}
