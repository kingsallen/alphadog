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
import org.neo4j.register.Register;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
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

    /**
     * 通过企业微信信息更新已认证员工信息
     * @param userids
     * @param companyId
     */
    public void updateWorkWxAuthedEmployee(List<Integer> userids,int companyId) {
        List<UserWorkwxRecord> workwxRecords = new ArrayList<>(userids.size());
        List<UserEmployeeRecord> employeeRecords = new ArrayList<>(userids.size());
        List<Boolean> diffList = new ArrayList<>(userids.size());

        for(int userid : userids){
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
            workwxRecords.add(workwxRecord);
            employeeRecords.add(employeeRecord);
            diffList.add(diff);
        }

        List<Map<Integer,String>> customFieldsList = getCustomFields(companyId,workwxRecords);
        List<Integer> diffEmployeeIds = new ArrayList<>(userids.size());
        for(int i=0;i<userids.size();i++){
            String customFieldValues = getCustomFieldsAsJsonString(customFieldsList.get(i));
            boolean diff = diffList.get(i);
            UserEmployeeRecord employeeRecord = employeeRecords.get(i);
            if(!Objects.equals(customFieldValues,employeeRecord.getCustomFieldValues())){
                employeeRecord.setCustomFieldValues(customFieldValues);
                diff = true ;
            }
            if(diff){
                try {
                    //先查询原数据，在手机号，姓名没有传值的时候，不予更新
                    employeeDao.updateRecord(employeeRecord);
                    diffEmployeeIds.add(employeeRecord.getId());
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    //throw CommonException.PROGRAM_EXCEPTION;
                }
            }
        }

        try {
            searchengineEntity.updateEmployeeAwards(diffEmployeeIds, false);
        } catch (Exception e) {
            log.error("更新searchengine员工信息失败", e);
        }
    }


    protected String getCustomFieldsAsJsonString(Map<Integer,String> customFieldValues){
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
        return getCustomFields(record.getCompanyId(),Arrays.asList(record)).get(0);
    }

    protected List<Map<Integer,String>> getCustomFields(int companyId,List<UserWorkwxRecord> records){
        List<Map<Integer,String>> result = new ArrayList<>();
        Map<Byte,HrEmployeeCustomFields> fieldsMap = customFieldsDao.listSystemCustomFieldByCompanyIdList(Arrays.asList(
                companyId)).stream().collect(Collectors.toMap(HrEmployeeCustomFields::getFieldType, f->f));
        HrEmployeeCustomFields positionField = fieldsMap.get(HrEmployeeCustomFieldsDao.FIELD_TYPE_POSITION);
        HrEmployeeCustomFields cityField = fieldsMap.get(HrEmployeeCustomFieldsDao.FIELD_TYPE_CITY);
        Map<Integer,String> cityOptions = customOptionJooqDao.listFieldOptions(cityField.getId());
        Map<Integer,String> positionOptions = customOptionJooqDao.listFieldOptions(positionField.getId());

        records.forEach(record->{
            String position = org.apache.commons.lang3.StringUtils.trim(record.getPosition());
            String address = org.apache.commons.lang3.StringUtils.trim(record.getAddress());

            Map<Integer,String> customFieldValues = new HashMap<>(2);
            if(!StringUtils.isNullOrEmpty(position) && positionField != null){
                Integer positionId = match(position,positionOptions,(value)->position.equals(value)) ;

                // 如果职位下拉框没有企业微信设置的职位，添加下拉选项
                if(positionId == null) {  // 如果positionField.getFvalues()为空，
                    positionId = customOptionJooqDao.addCustomOption(position,positionField.getId());
                    positionOptions.put(positionId,position);
                }
                if( positionId != null){
                    customFieldValues.put(positionField.getId(),String.valueOf(positionId));
                }
            }

            // 城市下拉框智能匹配
            if(!StringUtils.isNullOrEmpty(address) && cityOptions != null && !cityOptions.isEmpty()){
                Integer selectedCity = match(address,cityOptions,(value)->address.contains(value.replaceAll("[省,市,县,区]","")));
                // 如果匹配到相应城市
                if(selectedCity != null){
                    customFieldValues.put(cityField.getId(),String.valueOf(selectedCity));
                }
            }
            result.add(customFieldValues);
        });

        return result;
    }
    /**
     * 匹配下拉框
     * @param address 具体地址
     * @param cityOptions 城市下拉选项
     * @return 配置项ID
     */
    protected static Integer match(String address, Map<Integer,String> cityOptions, Predicate<String> predicate){
        if(StringUtils.isNullOrEmpty(address) || cityOptions.isEmpty()) return null ;

        for(Map.Entry<Integer,String> entry : cityOptions.entrySet()){
            if(entry.getKey() == 0 || StringUtils.isNullOrEmpty(entry.getValue())) continue;

            if(address.contains(entry.getValue().replaceAll("[省,市,县,区]","")) ){
                return entry.getKey();
            }
        }
        return null;
    }

}
