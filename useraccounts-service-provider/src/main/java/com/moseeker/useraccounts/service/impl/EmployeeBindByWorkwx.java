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
        String position = org.apache.commons.lang3.StringUtils.trim(record.getPosition());
        String address = org.apache.commons.lang3.StringUtils.trim(record.getAddress());

        Map<Byte,HrEmployeeCustomFields> fieldsMap = customFieldsDao.listSystemCustomFieldByCompanyIdList(Arrays.asList(
                bindingParams.getCompanyId())).stream().collect(Collectors.toMap(HrEmployeeCustomFields::getFieldType,f->f));
        Map<Integer,String> customFieldValues = new HashMap<>(2);

        HrEmployeeCustomFields positionField = fieldsMap.get(HrEmployeeCustomFieldsDao.FIELD_TYPE_POSITION);
        HrEmployeeCustomFields cityField = fieldsMap.get(HrEmployeeCustomFieldsDao.FIELD_TYPE_CITY);
        if(!StringUtils.isNullOrEmpty(position) && positionField != null){
            List<String> options = StringUtils.isNullOrEmpty(record.getPosition())?new ArrayList<>(1)
                    : customOptionJooqDao.listFieldOptions(positionField.getId());
            // 如果职位下拉框没有企业微信设置的职位，添加下拉选项
            if(options == null) {  // 如果positionField.getFvalues()为空，
                options = new ArrayList<>(1);
            }
            if(!options.contains(position)){
                options.add(position);
                customOptionJooqDao.addCustomOption(Arrays.asList(position),positionField.getId());
            }
            customFieldValues.put(positionField.getId(),position);
        }

        // 城市下拉框智能匹配
        List<String> cityOptions = customOptionJooqDao.listFieldOptions(cityField.getId());
        if(!StringUtils.isNullOrEmpty(address) && cityOptions != null && !cityOptions.isEmpty()){
            String select = matchCity(address,cityOptions);
            if(!StringUtils.isNullOrEmpty(select)){
                customFieldValues.put(cityField.getId(),select);
            }
        }

        bindingParams.setCustomFieldValues(customFieldValues);

        UserEmployeeDO userEmployee = super.createEmployee(bindingParams);
        userEmployee.setActivation(0);
        if(bindingParams.getSource() == 0 ){
            bindingParams.setSource(13); //13:企业微信
        }

        return userEmployee;
    }

    /**
     * 智能匹配城市（例如选项中有"上海市"，而输入"上海"，则匹配成功输出"上海市"）
     * @param address 具体地址
     * @param cityOptions 城市下拉选项
     * @return
     */
    protected static String matchCity(String address,List<String> cityOptions){
        if(StringUtils.isNullOrEmpty(address) || StringUtils.isEmptyList(cityOptions)) return null ;

        for(String option : cityOptions){
            if(!StringUtils.isNullOrEmpty(option) && address.contains(option.replaceAll("[省,市,县,区]","")) ){
                return option;
            }
        }
        return null;

    }
    /**
     * 校验自定义信息
     * @param bindingParams 认证参数
     */
    protected void validateCustomFieldValues(BindingParams bindingParams) {
        // 不校验自定义信息
    }

}
