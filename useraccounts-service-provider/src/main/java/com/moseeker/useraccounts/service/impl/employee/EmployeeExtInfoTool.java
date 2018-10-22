package com.moseeker.useraccounts.service.impl.employee;

import com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Author: jack
 * @Date: 2018/10/22
 */
public class EmployeeExtInfoTool {

    public static boolean verifyCustomFieldInfo(Map<Integer, List<String>> customValues, List<HrEmployeeCustomFields> confs) {
        for (Map.Entry<Integer, List<String>> entry : customValues.entrySet()) {
            Optional<HrEmployeeCustomFields> hrEmployeeCustomFieldsOptional = confs
                    .stream()
                    .filter(hrEmployeeCustomFields -> hrEmployeeCustomFields.getId().equals(entry.getKey()))
                    .findAny();
            if (hrEmployeeCustomFieldsOptional.isPresent()) {
                if (StringUtils.isNotBlank(hrEmployeeCustomFieldsOptional.get().getFvalues())
                        && !hrEmployeeCustomFieldsOptional.get().getFvalues().trim().equals("[]")) {
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
}
