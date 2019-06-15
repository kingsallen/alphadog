package com.moseeker.useraccounts.service.impl.employee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ArrayListMultimap;
import com.moseeker.baseorm.dao.employeedb.EmployeeCustomOptionJooqDao;
import com.moseeker.baseorm.dao.hrdb.HrCompanyDao;
import com.moseeker.baseorm.dao.hrdb.HrEmployeeCustomFieldsDao;
import com.moseeker.baseorm.dao.userdb.UserEmployeeDao;
import com.moseeker.baseorm.db.employeedb.tables.pojos.EmployeeOptionValue;
import com.moseeker.baseorm.db.hrdb.tables.HrCompany;
import com.moseeker.baseorm.db.hrdb.tables.pojos.HrEmployeeCustomFields;
import com.moseeker.baseorm.db.userdb.tables.UserEmployee;
import com.moseeker.baseorm.util.BeanUtils;
import com.moseeker.common.thread.ThreadPool;
import com.moseeker.common.util.StringUtils;
import com.moseeker.common.util.query.Query;
import com.moseeker.thrift.gen.dao.struct.hrdb.HrCompanyDO;
import com.moseeker.thrift.gen.dao.struct.userdb.UserEmployeeDO;
import com.moseeker.thrift.gen.useraccounts.struct.ImportErrorUserEmployee;
import com.moseeker.thrift.gen.useraccounts.struct.ImportUserEmployeeStatistic;
import com.moseeker.useraccounts.constant.OptionType;
import com.moseeker.useraccounts.exception.UserAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @Author jack
 * @Date 2019/6/14 1:50 PM
 * @Version 1.0
 */
@Component
public class BatchValidate {

    @Autowired
    private HrCompanyDao hrCompanyDao;

    @Autowired
    private UserEmployeeDao userEmployeeDao;

    @Autowired
    protected HrEmployeeCustomFieldsDao customFieldsDao;

    @Autowired
    protected EmployeeCustomOptionJooqDao customOptionJooqDao;

    ThreadPool threadPool = ThreadPool.Instance;

    public ArrayListMultimap<Integer, EmployeeOptionValue> optionsValues(Map<Integer, UserEmployeeDO> userEmployeeMap,
                                                                         Integer companyId) {
        ArrayListMultimap<Integer, Object> map = ArrayListMultimap.create();

        List<JSONArray> customFieldValuesArray = userEmployeeMap
                .values()
                .parallelStream()
                .map(userEmployeeDO -> {
                    JSONArray array = JSONArray.parseArray(userEmployeeDO.getCustomFieldValues());
                    return array;
                })
                .collect(Collectors.toList());

        for (JSONArray jsonArray : customFieldValuesArray) {
            if (jsonArray != null && jsonArray.size() > 0) {
                for (int i=0; i<jsonArray.size(); i++) {
                    if (jsonArray.get(i) != null) {
                        for (Map.Entry<String, Object> entry : ((JSONObject)jsonArray.get(i)).entrySet()) {
                            map.put(Integer.valueOf(entry.getKey()), entry.getValue());
                        }
                    }
                }
            }
        }
        if (map.size() > 0) {
            List<HrEmployeeCustomFields> fields = customFieldsDao.fetchByIdList(companyId, map.keySet());
            if (fields.size() > 0) {
                for (HrEmployeeCustomFields field : fields) {

                }
            }
            Optional<Boolean> optional = fields.parallelStream()
                    .filter(hrEmployeeCustomFields -> hrEmployeeCustomFields.getOptionType() == OptionType.Select.getValue())
                    .map(field -> {
                        if (map.get(field.getId()) != null) {
                            List<Integer> optionIdList = map.get(field.getId()).parallelStream()
                                    .map(BeanUtils::converToInteger)
                                    .collect(Collectors.toList());
                            int count = customOptionJooqDao.count(field.getId(), optionIdList);
                            return count != optionIdList.size();
                        } else {
                            return false;
                        }

                    })
                    .filter(o -> o)
                    .findAny();
            if (optional.isPresent()) {
                throw UserAccountException.IMPORT_DATA_WRONG;
            }
        }
    }

    public ArrayListMultimap<Integer, CustomOptionRel> chooseOptions(Map<Integer, UserEmployeeDO> userEmployeeMap) {
        ArrayListMultimap<Integer, Object> map = ArrayListMultimap.create();

        List<JSONArray> customFieldValuesArray = userEmployeeMap
                .values()
                .parallelStream()
                .map(userEmployeeDO -> {
                    JSONArray array = JSONArray.parseArray(userEmployeeDO.getCustomFieldValues());
                    return array;
                })
                .collect(Collectors.toList());

        for (JSONArray jsonArray : customFieldValuesArray) {
            if (jsonArray != null && jsonArray.size() > 0) {
                for (int i=0; i<jsonArray.size(); i++) {
                    if (jsonArray.get(i) != null) {
                        for (Map.Entry<String, Object> entry : ((JSONObject)jsonArray.get(i)).entrySet()) {
                            map.put(Integer.valueOf(entry.getKey()), entry.getValue());
                        }
                    }
                }
            }
        }
        return map;
    }

    public List<ImportErrorUserEmployee> batchUpdateEmployee(Integer companyId, Map<Integer, UserEmployeeDO> userEmployeeMap) {
        userEmployeeMap.
    }

    private ArrayListMultimap<Integer, Integer>

    class CustomOptionRel {

        private int customId;
        private String option;

        public int getCustomId() {
            return customId;
        }

        public void setCustomId(int customId) {
            this.customId = customId;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }
    }


}
