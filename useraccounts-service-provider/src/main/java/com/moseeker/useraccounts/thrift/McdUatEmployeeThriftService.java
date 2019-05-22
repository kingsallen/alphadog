package com.moseeker.useraccounts.thrift;

import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.employee.struct.BindType;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.thrift.gen.useraccounts.service.McdUatService;
import com.moseeker.thrift.gen.useraccounts.struct.McdUserTypeDO;
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
        UserEmployee result = mcdUatService.getSingleUserEmployee(userTypeDO.getUser_id());
        BindingParams bindingParams = new BindingParams();

        if (result != null) {
            bindingParams.setUserId(result.getSysuserId());
            bindingParams.setCompanyId(result.getCompanyId());
            bindingParams.setType(null);// BindType 包含 EMAIL(0),CUSTOMFIELD(1),QUESTIONS(2);
            bindingParams.setEmail(result.getEmail());
            bindingParams.setMobile(result.getMobile());
            bindingParams.setCustomField(result.getCustomField());
            bindingParams.setName(result.getCname());
            bindingParams.setSource(11); //这必须是11代表是来自joywork;
            bindingParams.setAnswer1(result.getCustomFieldValues());
            bindingParams.setAnswer2(result.getCustomFieldValues());
            logger.info("查到未认证的员工信息" + result + "开始执行员工绑定");
            int bindSource = 11;//注册来源joywork
            employeeBindAndUpdateByMcdUatSysUserId.bind(bindingParams, bindSource);

            return ResponseUtils.success(result);
        } else {
            logger.info("没有查到未认证的员工信息" + result + "开始执行员工绑定");
            bindingParams.setUserId(userTypeDO.getUser_id());
            bindingParams.setCompanyId(userTypeDO.getCompany_id());
            bindingParams.setEmail(userTypeDO.getEmail());
            bindingParams.setMobile(userTypeDO.getMobile());
            bindingParams.setCustomField(userTypeDO.getCustom_field());
            bindingParams.setName(userTypeDO.getCname());
            bindingParams.setSource(11); //这必须是11代表是来自joywork;
            int bindSource = 11;//注册来源joywork
            employeeBindAndUpdateByMcdUatSysUserId.bind(bindingParams, bindSource);
        }

        return ResponseUtils.success(result);

    }

    @Override
    public McdUserTypeDO getUserEmployeeByuserId(int userId) {

        McdUserTypeDO mcdUserTypeDO = new McdUserTypeDO();

        return mcdUserTypeDO;

    }

}
