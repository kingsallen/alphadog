package com.moseeker.useraccounts.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.moseeker.baseorm.dao.employeedb.EmployeeCustomOptionJooqDao;
import com.moseeker.baseorm.dao.hrdb.HrEmployeeCustomFieldsDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.dao.userdb.UserWorkwxDao;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields;
import com.moseeker.baseorm.db.userdb.tables.records.UserEmployeeRecord;
import com.moseeker.baseorm.db.userdb.tables.records.UserWorkwxRecord;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.SearchengineEntity;
import com.moseeker.useraccounts.exception.UserAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by qiancheng on 2019/8/12.
 */
@Service
public class UserWorkwxService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeBindByWorkwx.class);


    @Autowired
    private UserWorkwxDao workwxDao ;

    @Autowired
    private HrEmployeeCustomFieldsDao customFieldsDao ;

    @Autowired
    private EmployeeCustomOptionJooqDao customOptionJooqDao ;
    @Autowired
    private SearchengineEntity searchengineEntity;
    @Autowired
    private UserEmployeeDao employeeDao;

    public void updateWorkWxAuthedEmployee(List<Integer> userids,int companyId) {
        if(userids != null && !userids.isEmpty()){
            userids.forEach(userid->updateWorkWxAuthedEmployee(userid,companyId));
        }
    }
    /**
     * 通过企业微信信息更新已认证员工信息
     * @param userid
     * @param companyId
     * @return
     */
    protected void updateWorkWxAuthedEmployee(int userid,int companyId) {
        UserWorkwxRecord workwxRecord = workwxDao.getWorkwxByCompanyIdAndUserId(userid,companyId);
        UserEmployeeRecord employeeRecord = employeeDao.getActiveEmployee(userid,companyId);
        boolean diff = false ;
        if(workwxRecord == null || employeeRecord == null){
            throw UserAccountException.USER_WORKWX_NOT_EXIST ;
        }
        if(StringUtils.isNotNullOrEmpty(workwxRecord.getName()) && !Objects.equals(workwxRecord.getName(),employeeRecord.getCname())){
            employeeRecord.setCname(workwxRecord.getName());
            diff = true ;
        }
        if(StringUtils.isNotNullOrEmpty(workwxRecord.getEmail()) &&!Objects.equals(workwxRecord.getEmail(),employeeRecord.getEmail())){
            employeeRecord.setEmail(workwxRecord.getEmail());
            diff = true ;
        }
        if(StringUtils.isNotNullOrEmpty(workwxRecord.getMobile()) && !Objects.equals(workwxRecord.getMobile(),employeeRecord.getMobile())){
            employeeRecord.setMobile(workwxRecord.getMobile());
            diff = true ;
        }
        if(workwxRecord.getGender() != null && workwxRecord.getGender() > 0 && !Objects.equals(workwxRecord.getGender(),employeeRecord.getSex())){
            employeeRecord.setSex(workwxRecord.getGender());
            diff = true ;
        }

        String customFieldValues = getCustomFieldsAsJsonString(workwxRecord);
        if(!Objects.equals(customFieldValues,employeeRecord.getCustomFieldValues())){
            employeeRecord.setCustomFieldValues(customFieldValues);
            diff = true ;
        }
        if(diff){
            try {
                //先查询原数据，在手机号，姓名没有传值的时候，不予更新
                employeeDao.updateRecord(employeeRecord);
                searchengineEntity.updateEmployeeAwards(Lists.newArrayList(workwxRecord.getId()), false);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                //throw CommonException.PROGRAM_EXCEPTION;
            }
        }
    }

    protected String getCustomFieldsAsJsonString(UserWorkwxRecord record){
        Map<Integer,String> customFieldValues = getCustomFields(record);
        JSONArray jsonArray = new JSONArray();
        if (customFieldValues != null && customFieldValues.size() > 0) {
            customFieldValues.forEach((key, value) -> {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(key.toString(), value);
                jsonArray.add(jsonObject);
            });
        }
        return jsonArray.toJSONString() ;
    }
    protected Map<Integer,String> getCustomFields(UserWorkwxRecord record){
        String position = org.apache.commons.lang3.StringUtils.trim(record.getPosition());
        String address = org.apache.commons.lang3.StringUtils.trim(record.getAddress());

        Map<Byte,HrEmployeeCustomFields> fieldsMap = customFieldsDao.listSystemCustomFieldByCompanyIdList(Arrays.asList(
                record.getCompanyId())).stream().collect(Collectors.toMap(HrEmployeeCustomFields::getFieldType, f->f));
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
            String selectedCity = matchCity(address,cityOptions);
            if(!StringUtils.isNullOrEmpty(selectedCity)){
                customFieldValues.put(cityField.getId(),selectedCity);
            }
        }
        return customFieldValues;
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

}
