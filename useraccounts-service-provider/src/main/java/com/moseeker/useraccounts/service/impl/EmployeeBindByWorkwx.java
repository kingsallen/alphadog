package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.dao.employeedb.EmployeeCustomOptionJooqDao;
import com.moseeker.baseorm.dao.hrdb.HrEmployeeCustomFieldsDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields;
import com.moseeker.baseorm.db.userdb.tables.UserWorkwx;
import com.moseeker.baseorm.dao.userdb.UserWorkwxDao;
import com.moseeker.baseorm.db.userdb.tables.records.UserWorkwxRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrEmployeeCertConfDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.employee.struct.BindingParams;
import com.moseeker.useraccounts.exception.UserAccountException;
import com.moseeker.useraccounts.service.EmployeeBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by qiancheng on 19/8/7.
 */
@Service("auth_method_workwx")
public class EmployeeBindByWorkwx extends EmployeeBinder{

    private static final Logger log = LoggerFactory.getLogger(EmployeeBindByWorkwx.class);


    @Autowired
    private UserWorkwxDao workwxDao ;

    @Autowired
    private HrEmployeeCustomFieldsDao customFieldsDao ;

    @Autowired
    private EmployeeCustomOptionJooqDao customOptionJooqDao ;

    @Autowired
    private UserWorkwxService workwxService;
    /**
     * 创建员工记录
     * @param bindingParams
     * @return
     */
    protected UserEmployeeDO createEmployee(BindingParams bindingParams) {
        UserWorkwxRecord record = workwxDao.getWorkwxByCompanyIdAndUserId(bindingParams.getUserId(),bindingParams.getCompanyId());
        if(record == null){
            throw UserAccountException.USER_WORKWX_NOT_EXIST ;
        }
        bindingParams.setName(record.getName());
        // 头像（如已有，用企业微信获取信息覆盖）
        bindingParams.setMobile(record.getMobile());
        bindingParams.setEmail(record.getEmail());

        Map<Integer,String> customFieldValues = workwxService.getCustomFields(record);

        bindingParams.setCustomFieldValues(customFieldValues);

        UserEmployeeDO userEmployee = super.createEmployee(bindingParams);
        userEmployee.setActivation(0);
        if(bindingParams.getSource() == 0 ){
            bindingParams.setSource(13); //13:企业微信
        }

        return userEmployee;
    }

    /**
     * 校验自定义信息
     * @param bindingParams 认证参数
     */
    protected void validateCustomFieldValues(BindingParams bindingParams) {
        // 不校验自定义信息
    }

}
