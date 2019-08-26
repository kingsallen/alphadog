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
import com.moseeker.common.constants.Constant;
import com.moseeker.common.util.StringUtils;
import com.moseeker.entity.SearchengineEntity;
import com.moseeker.useraccounts.exception.UserAccountException;
import org.neo4j.cypher.internal.compiler.v2_2.ast.In;
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

    /**
     * 获取员工已有系统字段值
     * @param fieldIds
     * @param sysuserid
     * @param companyId
     * @return
     */
    private Map<Integer,String> getEmployeeCustomFieldValues(List<Integer> fieldIds,int sysuserid,int companyId){
        Map<Integer,String> customFieldValues = new HashMap<>(2);
        if ( sysuserid > 0){
            UserEmployeeRecord employeeRecord = employeeDao.getEmployeeIgnoreActivation(sysuserid, companyId);
            if (employeeRecord != null && employeeRecord.getCustomFieldValues() != null){
                JSONArray.parseArray(employeeRecord.getCustomFieldValues(),Map.class).forEach(map->map.forEach((k,v)->{
                    int fieldId = Integer.parseInt(k.toString()) ;
                    if(fieldIds.contains(fieldId)){
                        customFieldValues.put(fieldId,v.toString());
                    }
                }));
            }
        }
        return customFieldValues;
    }

    protected List<Map<Integer,String>> getCustomFields(int companyId,List<UserWorkwxRecord> records){
        List<Map<Integer,String>> result = new ArrayList<>();
        Map<Byte,HrEmployeeCustomFields> fieldsMap = customFieldsDao.listSystemCustomFieldByCompanyIdList(Arrays.asList(
                companyId)).stream().collect(Collectors.toMap(HrEmployeeCustomFields::getFieldType, f->f));
        List<Integer> fieldIds = new ArrayList<>();
        fieldsMap.values().forEach(f->fieldIds.add(f.getId()));

        HrEmployeeCustomFields positionField = fieldsMap.get(HrEmployeeCustomFieldsDao.FIELD_TYPE_POSITION);
        HrEmployeeCustomFields cityField = fieldsMap.get(HrEmployeeCustomFieldsDao.FIELD_TYPE_CITY);
        LinkedHashMap<String,Integer> cityOptions = prepareCitiOptions(customOptionJooqDao.listFieldOptions(cityField.getId()));
        Map<Integer,String> positionOptions = customOptionJooqDao.listFieldOptions(positionField.getId());

        records.forEach(record->{
            Map<Integer,String> customFieldValues = getEmployeeCustomFieldValues(fieldIds,record.getSysuserId(),companyId);

            String position = org.apache.commons.lang3.StringUtils.trim(record.getPosition());
            String address = org.apache.commons.lang3.StringUtils.trim(record.getAddress());


            if(!StringUtils.isNullOrEmpty(position) && positionField != null){
                Integer positionId = null ;
                for(Map.Entry<Integer,String> entry : positionOptions.entrySet()){
                    if(Objects.equals(entry.getValue(),position)){
                        positionId = entry.getKey();
                    }
                }

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
                Integer selectedCity = matchCity(address,cityOptions);
                // 如果匹配到相应城市
                if(selectedCity != null){
                    customFieldValues.put(cityField.getId(),String.valueOf(selectedCity));
                }
            }
            result.add(customFieldValues);
        });
        return result;
    }

    private static LinkedHashMap<String,Integer> prepareCitiOptions(Map<Integer,String> cityOptions){
        if( cityOptions == null || cityOptions.isEmpty()) return new LinkedHashMap<>();

        LinkedHashMap<String,Integer> cityMap = new LinkedHashMap<>(cityOptions.size()); // 保持原先顺序不变
        cityOptions.forEach((k,v)->{
            if( k != null && v != null && (v = v.replaceAll("[省,市,县,区]","").trim()).length()>0){
                cityMap.putIfAbsent(v,k);
            }
        });
        return cityMap;
    }

    /**
     * 匹配城市下拉框
     * 下拉框示例 [上海（市），北京（市），南京（市），西安（市），苏州（市）]，括号（市）表示可选
     * 地址示例：
     * 1 上海（市） -> 匹配'上海'、'上海市'
     * 2 上海市北京西路 -> 匹配'上海'
     * 3 上海市光路 -> 匹配'上海'
     * 算法：
     * 1. (准备)对所有下拉选项进行去除'省'、'市'、'县'、'区'后缀处理，比如'上海（市）'变成'上海'
     * 2. (猜测市名)先尝试截取市名，因为目标城市为xx市的可能性最大。截断的规则为截取'省'（如果有）与'市'之间的内容。
     *    例如"上海市南京西路"截断结果为"上海"，"江苏省苏州市太湖路"截断结果为"苏州"
     * 1. 对地址按'市','县','区'进行分隔，得到序列，依次匹配。匹配方式分为两种，一种是准确匹配，另一种是优先匹配，前者优先级较高。
     * @param address 具体地址
     * @param cityMap 城市下拉选项
     * @return 配置项ID
     */
    protected static Integer matchCity(String address, LinkedHashMap<String,Integer> cityMap){
        // 如果地址未填，返回null
        if(StringUtils.isNullOrEmpty(address) || cityMap.isEmpty()) return null ;


        // 算法步骤2 (猜测)截取市名
        int firstIdxProvinceChar = address.indexOf("省");
        int firstIdxCityChar = address.indexOf("市");
        if(firstIdxCityChar > 0 && firstIdxCityChar > firstIdxProvinceChar +1){
            String city = address.substring(firstIdxProvinceChar>0?firstIdxProvinceChar+1:0 ,firstIdxCityChar );
            Integer cityId = cityMap.get(city);
            if( cityId != null) {
                return cityId;
            }
        }

        // 算法步骤3
        List<String> list = Arrays.asList(address.split("[省,市,县,区]"));
        int firstPosition = address.length() ;
        Integer firstMatch = null ;
        // 准确匹配
        for ( String str : list){
            for(Map.Entry<String,Integer> entry : cityMap.entrySet()){
                // 如'江苏省苏州市太湖路' -> '江苏'匹配失败，'苏州'匹配成功
                if(entry.getKey().equals(str)) {
                    return entry.getValue();
                }
                // 如'江苏苏州市太湖路',包含'苏州'
                int idx = address.indexOf(entry.getKey());
                if(idx != -1 && idx < firstPosition){
                    firstPosition = idx;
                    firstMatch = entry.getValue();
                }
            }
        }
        if(firstMatch != null){
            // 优先获取第一个模糊匹配结果
            return firstMatch ;
        }
        return null;
    }

}
