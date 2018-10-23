package com.moseeker.useraccounts.service.impl.employee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @Author: jack
 * @Date: 2018/10/22
 */
public class EmployeeExtInfoTool {

    /**
     * 校验填写的员工补填信息是否符合配置的要求
     * @param customValues 用户补填的员工信息
     * @param confs 公司配置的员工补填信息
     * @return true 符合配置要求；false 不符合配置要求
     */
    public static boolean verifyCustomFieldInfo(Map<Integer, List<String>> customValues, List<HrEmployeeCustomFields> confs) {
            for (Map.Entry<Integer, List<String>> entry : customValues.entrySet()) {
            Optional<HrEmployeeCustomFields> hrEmployeeCustomFieldsOptional = confs
                    .stream()
                    .filter(hrEmployeeCustomFields -> hrEmployeeCustomFields.getId().equals(entry.getKey()))
                    .findAny();
            if (hrEmployeeCustomFieldsOptional.isPresent()) {
                if (StringUtils.isNotBlank(hrEmployeeCustomFieldsOptional.get().getFvalues())) {
                    if (hrEmployeeCustomFieldsOptional.get().getOptionType() == 1) {
                        continue;
                    }
                    String[] confList = hrEmployeeCustomFieldsOptional.get()
                            .getFvalues()
                            .substring(1, hrEmployeeCustomFieldsOptional.get().getFvalues().length()-1)
                            .replace("\"", "")
                            .split(",");
                    for (String option : entry.getValue()) {
                        boolean flag = false;
                        for (String optionConf : confList) {
                            if (option.equals(optionConf.trim())) {
                                flag = true;
                                break;
                            }
                        }
                        if (!flag) {
                            return flag;
                        }
                    }
                } else {
                    if (customValues.get(hrEmployeeCustomFieldsOptional.get().getId()) != null
                            && customValues.get(hrEmployeeCustomFieldsOptional.get().getId()).size() > 0) {
                        return false;
                    } else {
                        continue;
                    }
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 更新员工补填信息
     * 如果不存在则添加；如果存在并且不为空数据，则修改；如果存在并且数据是空数据，则删除
     * @param customFieldValues 员工已经填写的补填信息
     * @param customValues 本次提交的补填信息
     * @return
     */
    public static String mergeCustomFieldValue(String customFieldValues, Map<Integer, List<String>> customValues) {
        JSONArray array = JSONArray.parseArray(customFieldValues);
        if (array == null) {
            array = new JSONArray();
        }

        for (Map.Entry<Integer, List<String>> entry : customValues.entrySet()) {
            boolean addFlag = true;
            Iterator<Object> iterator = array.iterator();
            while (iterator.hasNext()) {
                JSONObject object = (JSONObject)iterator.next();
                Map.Entry<String, List<String>> currentEntry = (Map.Entry<String, List<String>>)(object.entrySet().toArray()[0]);
                if (customValues.get(Integer.valueOf(currentEntry.getKey())) != null) {
                    if (customValues.get(Integer.valueOf(currentEntry.getKey())).size() > 0) {
                        object.put(currentEntry.getKey(), customValues.get(Integer.valueOf(currentEntry.getKey())));
                        addFlag = false;
                        break;
                    } else {
                        addFlag = false;
                        customValues.remove(Integer.valueOf(currentEntry.getKey()));
                        iterator.remove();
                    }

                }
            }
            if (addFlag) {
                JSONObject newCustomField = new JSONObject();
                newCustomField.put(entry.getKey().toString(), entry.getValue());
                array.add(newCustomField);
            }

        }

        return array.toJSONString();
    }
}
