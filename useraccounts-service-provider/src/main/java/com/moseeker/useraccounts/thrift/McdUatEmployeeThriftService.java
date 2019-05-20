package com.moseeker.useraccounts.thrift;

import com.moseeker.baseorm.db.userdb.tables.pojos.UserEmployee;
import com.moseeker.common.providerutils.ExceptionUtils;
import com.moseeker.common.providerutils.ResponseUtils;
import com.moseeker.thrift.gen.common.struct.Response;
import com.moseeker.thrift.gen.employee.struct.BindType;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.thrift.gen.useraccounts.service.McdUatService;
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

    @Autowired McdUatServiceImpl mcdUatService;

    @Autowired
    EmployeeBindAndUpdateByMcdUatSysUserId employeeBindAndUpdateByMcdUatSysUserId;

    @Override
    public Response getUserEmployeeByuserId(int userId) throws TException {
        try{
            UserEmployee result=mcdUatService.getSingleUserEmployee(userId);
            BindingParams bindingParams = new BindingParams();
            bindingParams.setUserId(result.getSysuserId());
            bindingParams.setCompanyId(result.getCompanyId());
            bindingParams.setType(null);// BindType 包含 EMAIL(0),CUSTOMFIELD(1),QUESTIONS(2);
            bindingParams.setEmail(result.getEmail());
            bindingParams.setMobile(result.getMobile());
            bindingParams.setCustomField(result.getCustomField());
            bindingParams.setName(result.getCname());
            bindingParams.setSource(result.getSource()); //这必须是11代表是来自joywork;
            bindingParams.setAnswer1(result.getCustomFieldValues());
            bindingParams.setAnswer2(result.getCustomFieldValues());


            if(result !=null){
                logger.info("查到未认证的员工信息"+result+"开始执行员工绑定");
                int bindSource =11;//注册来源joywork
                employeeBindAndUpdateByMcdUatSysUserId.bind(bindingParams,bindSource);

            return ResponseUtils.success(result);
            } else{
                //todo 执行员工认证 查看工号是否能查到员工；

                return ResponseUtils.fail("没有到员工");
            }
        }catch(Exception e){
            throw ExceptionUtils.convertException(e);
        }
    }

}
